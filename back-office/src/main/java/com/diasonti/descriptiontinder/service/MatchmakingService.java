package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.ChatMessage;
import com.diasonti.descriptiontinder.data.entity.MmChoice;
import com.diasonti.descriptiontinder.data.entity.MmMatch;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import com.diasonti.descriptiontinder.data.form.ChatMessageForm;
import com.diasonti.descriptiontinder.data.form.MmMatchForm;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.*;
import com.diasonti.descriptiontinder.service.exceptions.IllegalVoteException;
import com.diasonti.descriptiontinder.service.exceptions.MatchmakingException;
import com.diasonti.descriptiontinder.service.exceptions.NoCandidateException;
import com.diasonti.descriptiontinder.service.exceptions.ProfileNotFilledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Transactional
    public UserProfileForm getNextCandidate(Long sourceUserId) throws MatchmakingException {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElseThrow(() -> new RuntimeException("UserAccount not found"));

        if(source.getLastCandidate() != null) {
            return UserProfileForm.of(source.getLastCandidate());
        }

        if(!source.isProfileFilled()) {
            throw new ProfileNotFilledException();
        }
        final UserAccount candidate = userAccountRepository.findNextMatchmakingCandidate(sourceUserId,
                source.getGenderPreference(), source.getAgePreferenceMin(), source.getAgePreferenceMax(), -1)
                .orElseThrow(NoCandidateException::new);
        source.setLastCandidate(candidate);
        return UserProfileForm.of(candidate);
    }

    @Transactional
    public void saveLike(Long sourceUserId, Long targetUserId) throws MatchmakingException {
        saveChoice(sourceUserId, targetUserId, MatchmakingDecision.LIKE);
    }

    @Transactional
    public void saveDislike(Long sourceUserId, Long targetUserId) throws MatchmakingException {
        saveChoice(sourceUserId, targetUserId, MatchmakingDecision.DISLIKE);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void saveChoice(Long sourceUserId, Long targetUserId, MatchmakingDecision decision) throws MatchmakingException {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElseThrow(() -> new RuntimeException("UserAccount not found"));
        final UserAccount target = source.getLastCandidate();
        if(target == null || !target.getId().equals(targetUserId)) {
            throw new IllegalVoteException();
        }
        if (!choiceRepository.existsBySourceAndTarget(source, target)) {
            final MmChoice choice = new MmChoice();
            choice.setSource(source);
            choice.setTarget(target);
            choice.setDecision(decision);
            choiceRepository.save(choice);

            source.setLastCandidate(null);
        }
    }

    @Transactional
    public void checkAndSaveMatch(Long sourceUserId, Long targetUserId) {
        final UserAccount source = userAccountRepository.findById(sourceUserId).orElseThrow(() -> new RuntimeException("UserAccount not found"));
        final UserAccount target = userAccountRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("UserAccount not found"));
        final MmChoice sourceChoice = choiceRepository.findBySourceAndTargetAndDecision(source, target, MatchmakingDecision.LIKE).orElse(null);
        if(sourceChoice == null) return;
        final MmChoice targetChoice = choiceRepository.findBySourceAndTargetAndDecision(target, source, MatchmakingDecision.LIKE).orElse(null);
        if(targetChoice == null) return;
        final MmMatch match = new MmMatch(sourceChoice, targetChoice);
        matchRepository.save(match);
    }

    @Transactional(readOnly = true)
    public List<MmMatchForm> getMatches(Long userId) {
        final UserAccount user = userAccountRepository.findById(userId).orElse(null);
        if (user == null)
            return Collections.emptyList();
        List<MmMatch> matches = matchRepository.findAllByFirstUserOrSecondUser(user, user);
        return matches.stream().map(match -> {
            final MmMatchForm form = new MmMatchForm();
            form.setId(match.getId());
            if (match.getFirstUser().equals(user))
                form.setMatchedUser(UserProfileForm.of(match.getSecondUser()));
            else
                form.setMatchedUser(UserProfileForm.of(match.getFirstUser()));
            form.setMatchedAt(match.getCreatedAt());
            form.setType(match.getType());
            final ChatMessage lastMessage = chatMessageRepository.findTopByMatchOrderBySentAtDesc(match).orElse(null);
            form.setLastMessage(ChatMessageForm.of(lastMessage));
            return form;
        }).collect(Collectors.toList());
    }

}
