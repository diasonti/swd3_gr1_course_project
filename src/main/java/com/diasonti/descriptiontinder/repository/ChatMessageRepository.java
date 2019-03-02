package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.ChatMessage;
import com.diasonti.descriptiontinder.data.entity.MmMatch;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMessageRepository extends PagingAndSortingRepository<ChatMessage, Long> {

    Optional<ChatMessage> findTopByMatchOrderBySentAtDesc(MmMatch match);
}
