package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.UserRole;
import com.diasonti.descriptiontinder.data.form.UserRegistrationForm;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        return !userAccountRepository.existsByUsername(username);
    }

    @Transactional
    public void register(UserRegistrationForm form) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(form.getUsername());
        userAccount.setPassword(passwordEncoder.encode(form.getPassword()));
        userAccount.setRole(UserRole.USER);
        userAccount.setRegisteredAt(LocalDateTime.now());
        userAccountRepository.save(userAccount);
    }
}
