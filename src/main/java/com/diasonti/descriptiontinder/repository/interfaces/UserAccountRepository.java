package com.diasonti.descriptiontinder.repository.interfaces;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);

}
