package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MatchmakingChoice;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchmakingChoiceRepository extends CrudRepository<MatchmakingChoice, Long> {

    Optional<MatchmakingChoice> findBySourceAndTargetAndDecision(UserAccount source, UserAccount target, MatchmakingDecision decision);

}
