package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.config.security.UserAccountHolder;
import com.diasonti.descriptiontinder.data.entity.ChatMessage;
import com.diasonti.descriptiontinder.data.entity.MmMatch;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.ChatMessageForm;
import com.diasonti.descriptiontinder.repository.ChatMessageRepository;
import com.diasonti.descriptiontinder.repository.MatchmakingMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MatchmakingMatchRepository matchRepository;

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatService(MatchmakingMatchRepository matchRepository, ChatMessageRepository chatMessageRepository) {
        this.matchRepository = matchRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional(readOnly = true)
    public List<ChatMessageForm> getMessages(Long matchId, int from, int to) {
        final MmMatch match = matchRepository.findById(matchId).orElse(null);
        if(match == null || !match.hasUserWithId(UserAccountHolder.getCurrentUser().getId()))
            return Collections.emptyList();
        final int size = to - from;
        final int page = from / size;
        final List<ChatMessage> messages = chatMessageRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Order.desc("sentAt")))).getContent();
        return messages.stream().map(ChatMessageForm::of).collect(Collectors.toList());
    }

    @Transactional
    public boolean saveMessage(Long matchId, String messageText) {
        final MmMatch match = matchRepository.findById(matchId).orElse(null);
        final UserAccount sender = UserAccountHolder.getCurrentUser();
        if(match == null || !match.hasUserWithId(sender.getId()))
            return false;
        final UserAccount receiver = match.getFirstUser() != sender ? match.getFirstUser() : match.getSecondUser();
        final ChatMessage message = new ChatMessage();
        message.setMatch(match);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(messageText);
        message.setSentAt(LocalDateTime.now());
        chatMessageRepository.save(message);
        return true;
    }

}
