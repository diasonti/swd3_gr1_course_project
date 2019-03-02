package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.ChatMessage;
import com.diasonti.descriptiontinder.data.entity.MmChoice;
import com.diasonti.descriptiontinder.data.entity.MmMatch;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import com.diasonti.descriptiontinder.data.form.ChatMessageForm;
import com.diasonti.descriptiontinder.data.form.MmMatchForm;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.ChatMessageRepository;
import com.diasonti.descriptiontinder.repository.MatchmakingChoiceRepository;
import com.diasonti.descriptiontinder.repository.MatchmakingMatchRepository;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import com.diasonti.descriptiontinder.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchmakingService {

    private final UserAccountRepository userAccountRepository;

    private final MatchmakingChoiceRepository choiceRepository;

    private final MatchmakingMatchRepository matchRepository;

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public MatchmakingService(UserAccountRepository userAccountRepository, MatchmakingChoiceRepository choiceRepository, MatchmakingMatchRepository matchRepository, ChatMessageRepository chatMessageRepository) {
        this.userAccountRepository = userAccountRepository;
        this.choiceRepository = choiceRepository;
        this.matchRepository = matchRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileForm getNextCandidate(Long sourceUserId) {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElse(null);
        if (source == null)
            return null;
        final UserAccount candidate = userAccountRepository.findNextMatchmakingCandidate(sourceUserId,
                source.getGenderPreference(), source.getAgePreferenceMin(), source.getAgePreferenceMax(), -1).orElse(null);
        if (candidate == null)
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
        if (source != null && target != null) {
            MmChoice choice = new MmChoice();
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
        if (source == null || target == null)
            return;
        final MmChoice sourceChoice = choiceRepository.findBySourceAndTargetAndDecision(source, target, MatchmakingDecision.LIKE).orElse(null);
        final MmChoice targetChoice = choiceRepository.findBySourceAndTargetAndDecision(target, source, MatchmakingDecision.LIKE).orElse(null);
        if (sourceChoice == null || targetChoice == null)
            return;
        final MmMatch match = new MmMatch(sourceChoice, targetChoice);
        matchRepository.save(match);
    }

    @Transactional(readOnly = true)
    public boolean isMatched(Long firstUserId, Long secondUserId) {
        final UserAccount first = userAccountRepository.findById(firstUserId).orElse(null);
        final UserAccount second = userAccountRepository.findById(secondUserId).orElse(null);
        if (first == null || second == null)
            return false;
        final MmMatch match = matchRepository.findByFirstUserAndSecondUser(first, second).orElse(null);
        return match != null;
    }

    @Transactional(readOnly = true)
    public List<MmMatchForm> getMatches(Long userId) {
        final UserAccount user = userAccountRepository.findById(userId).orElse(null);
        if (user == null)
            return Collections.emptyList();
        List<MmMatch> matches = matchRepository.findAllByFirstUserOrSecondUser(user, user);
        return matches.stream().map(match -> {
            final MmMatchForm form = new MmMatchForm();
            if (match.getFirstUser().equals(user))
                form.setMatchedUser(match.getSecondUser());
            else
                form.setMatchedUser(match.getFirstUser());
            form.setMatchedAt(match.getCreatedAt());
            form.setType(match.getType());
            final ChatMessage lastMessage = chatMessageRepository.findTopByMatchOrderBySentAtDesc(match).orElse(null);
            form.setLastMessage(ChatMessageForm.of(lastMessage));
            return form;
        }).collect(Collectors.toList());
    }

}
