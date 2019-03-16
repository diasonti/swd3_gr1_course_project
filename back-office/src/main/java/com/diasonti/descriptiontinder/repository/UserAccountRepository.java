package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long>, UserAccountRepositoryExtension {

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}
