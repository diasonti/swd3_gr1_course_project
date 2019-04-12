package com.diasonti.descriptiontinder.service;

import com.auth0.jwt.JWT;
import com.diasonti.descriptiontinder.config.security.jwt.JWTConfig;
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
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

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
    public String getAuthToken(String username, String password, boolean noExpiration) {
        String token = null;
        final UserAccount userAccount = userAccountRepository.findByUsername(username).orElse(null);
        if(userAccount != null && passwordEncoder.matches(password, userAccount.getPassword())) {
            token = JWT.create()
                    .withSubject(userAccount.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (noExpiration ? JWTConfig.ONE_YEAR : JWTConfig.TEN_DAYS)))
                    .sign(HMAC512(JWTConfig.SECRET.getBytes()));
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
        userAccount = userAccountRepository.save(userAccount);
        log.info("User registered: {}", userAccount.toString());
    }
}
