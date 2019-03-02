package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MatchmakingMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchmakingMatchRepository extends CrudRepository<MatchmakingMatch, Long> {

}
