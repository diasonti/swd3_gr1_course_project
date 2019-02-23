package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.UserAccount;

import java.util.Optional;

public interface CustomUserAccountRepository {

    Optional<UserAccount> findNextMatchmakingCandidate(Long sourceUserId);

}
