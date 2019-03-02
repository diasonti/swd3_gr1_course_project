package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MmMatch;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchmakingMatchRepository extends CrudRepository<MmMatch, Long> {

    Optional<MmMatch> findByFirstUserAndSecondUser(UserAccount firstUser, UserAccount secondUser);

    List<MmMatch> findAllByFirstUserOrSecondUser(UserAccount firstUser, UserAccount secondUser);
}
