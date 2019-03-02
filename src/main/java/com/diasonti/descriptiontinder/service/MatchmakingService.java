package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.MatchmakingChoice;
import com.diasonti.descriptiontinder.data.entity.MatchmakingMatch;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.MatchmakingChoiceRepository;
import com.diasonti.descriptiontinder.repository.MatchmakingMatchRepository;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import com.diasonti.descriptiontinder.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchmakingService {

    private final UserAccountRepository userAccountRepository;

    private final MatchmakingChoiceRepository choiceRepository;

    private final MatchmakingMatchRepository matchRepository;

    @Autowired
    public MatchmakingService(UserAccountRepository userAccountRepository, MatchmakingChoiceRepository choiceRepository, MatchmakingMatchRepository matchRepository) {
        this.userAccountRepository = userAccountRepository;
        this.choiceRepository = choiceRepository;
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileForm getNextCandidate(Long sourceUserId) {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElse(null);
        if(source == null)
            return null;
        final UserAccount candidate = userAccountRepository.findNextMatchmakingCandidate(sourceUserId,
                source.getGenderPreference(), source.getAgePreferenceMin(), source.getAgePreferenceMax(), -1).orElse(null);
        if(candidate == null)
            return null;
        return UserProfileForm.of(candidate);
    }

    @Transactional
    public void saveLike(Long sourceUserId, Long targetUserId) {
        saveChoice(sourceUserId, targetUserId, MatchmakingDecision.LIKE);
        TransactionUtils.afterTransaction(() -> checkAndSaveMatch(sourceUserId, targetUserId));
    }

    @Transactional
    public void saveDislike(Long sourceUserId, Long targetUserId) {
        saveChoice(sourceUserId, targetUserId, MatchmakingDecision.DISLIKE);
    }

    private void saveChoice(Long sourceUserId, Long targetUserId, MatchmakingDecision decision) {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElse(null);
        final UserAccount target = userAccountRepository.findById(targetUserId).orElse(null);
        if(source != null && target != null) {
            MatchmakingChoice choice = new MatchmakingChoice();
            choice.setSource(source);
            choice.setTarget(target);
            choice.setDecision(decision);
            choiceRepository.save(choice);
        }
    }

    @Transactional
    public void checkAndSaveMatch(Long sourceUserId, Long targetUserId) {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElse(null);
        final UserAccount target = userAccountRepository.findById(targetUserId).orElse(null);
        if(source == null || target == null)
            return;
        final MatchmakingChoice sourceChoice = choiceRepository.findBySourceAndTargetAndDecision(source, target, MatchmakingDecision.LIKE).orElse(null);
        final MatchmakingChoice targetChoice = choiceRepository.findBySourceAndTargetAndDecision(target, source, MatchmakingDecision.LIKE).orElse(null);
        if(sourceChoice == null || targetChoice == null)
            return;
        final MatchmakingMatch match = new MatchmakingMatch(sourceChoice, targetChoice);
        matchRepository.save(match);
    }

}
