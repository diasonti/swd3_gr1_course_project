package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.MatchmakingChoice;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.MatchmakingChoiceRepository;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchmakingService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private MatchmakingChoiceRepository matchmakingChoiceRepository;

    @Transactional(readOnly = true)
    public UserProfileForm getNextCandidate(Long sourceUserId) {
        return UserProfileForm.of(userAccountRepository.findNextMatchmakingCandidate(sourceUserId).orElse(null));
    }

    @Transactional
    public void saveLike(Long sourceUserId, Long targetUserId) {
        saveChoice(sourceUserId, targetUserId, MatchmakingDecision.LIKE);
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
            matchmakingChoiceRepository.save(choice);
        }
    }

}
