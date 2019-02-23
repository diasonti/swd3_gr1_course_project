package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MatchmakingChoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchmakingChoiceRepository extends CrudRepository<MatchmakingChoice, Long> {

}
