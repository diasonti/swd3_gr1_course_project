package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MmChoice;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchmakingChoiceRepository extends CrudRepository<MmChoice, Long> {

    Optional<MmChoice> findBySourceAndTargetAndDecision(UserAccount source, UserAccount target, MatchmakingDecision decision);

    boolean existsBySourceAndTarget(UserAccount source, UserAccount target);

}
