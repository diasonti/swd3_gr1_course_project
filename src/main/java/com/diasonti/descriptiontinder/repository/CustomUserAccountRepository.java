package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.GenderPreference;

import java.util.Optional;

public interface CustomUserAccountRepository {

    Optional<UserAccount> findNextMatchmakingCandidate(Long sourceUserId, GenderPreference gender, int ageFrom, int ageTo, int proximity);

}
