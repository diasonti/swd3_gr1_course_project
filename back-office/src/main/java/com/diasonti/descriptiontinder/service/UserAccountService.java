package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.UserRole;
import com.diasonti.descriptiontinder.data.form.UserRegistrationForm;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class UserAccountService {

    private static final Logger log = LoggerFactory.getLogger(UserAccountService.class);

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public String getAuthToken(String username, String password) {
        String token = null;
        final UserAccount userAccount = userAccountRepository.findByUsername(username).orElse(null);
        if(userAccount != null && passwordEncoder.matches(password, userAccount.getPassword())) {
            final String pair = username + ":" + password;
            token = Base64.getEncoder().encodeToString(pair.getBytes());
            log.info("Auth token generated - {}, token='{}'", userAccount.toString(), token);
        }
        if(token == null) {
            log.info("Failed auth attempt - username='{}', password='{}'", username, password);
        }
        return token;
    }

    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        return !userAccountRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public UserAccount getUserAccountByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public void register(UserRegistrationForm form) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(form.getUsername());
        userAccount.setPassword(passwordEncoder.encode(form.getPassword()));
        userAccount.setRole(UserRole.USER);
        userAccount.setRegisteredAt(LocalDateTime.now());
        log.info("User registered: {}", userAccount.toString());
        userAccountRepository.save(userAccount);
    }
}
