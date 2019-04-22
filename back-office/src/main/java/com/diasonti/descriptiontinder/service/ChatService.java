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
        final Long currentUserId = UserAccountHolder.getCurrentUserId();
        final MmMatch match = matchRepository.findById(matchId).orElse(null);
        if(match == null || !match.hasUserWithId(currentUserId))
            return Collections.emptyList();
        final int size = to - from;
        final int page = from / size;
        final List<ChatMessage> messages = chatMessageRepository.findByMatch(match, PageRequest.of(page, size, Sort.by(Sort.Order.asc("sentAt"))));
        return messages.stream().map(message -> {
            ChatMessageForm form = ChatMessageForm.of(message);
            form.setMine(form.getSender().getId().equals(currentUserId));
            return form;
        }).collect(Collectors.toList());
    }

    @Transactional
    public String saveMessage(Long matchId, String messageText) {
        if(messageText.trim().isEmpty()) return "message.is.blank";
        final MmMatch match = matchRepository.findById(matchId).orElse(null);
        final UserAccount sender = UserAccountHolder.getCurrentUser();
        if(match == null || !match.hasUserWithId(sender.getId())) return "invalid.match.id";
        final UserAccount receiver = match.getFirstUser().equals(sender) ? match.getSecondUser() : match.getFirstUser();
        final ChatMessage message = new ChatMessage();
        message.setMatch(match);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setText(messageText);
        message.setSentAt(LocalDateTime.now());
        chatMessageRepository.save(message);
        return null;
    }

}
