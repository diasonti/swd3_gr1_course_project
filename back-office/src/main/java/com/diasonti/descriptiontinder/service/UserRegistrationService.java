package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.UserRole;
import com.diasonti.descriptiontinder.data.form.UserRegistrationForm;
import com.diasonti.descriptiontinder.repository.interfaces.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRegistrationService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public boolean isUsernameTaken(String username) {
        return userAccountRepository.existsByUsername(username);
    }

    @Transactional
    public List<String> register(UserRegistrationForm form) {
        final List<String> errors = new ArrayList<>();
        if(!form.getPassword().equals(form.getPasswordVerification())) {
            errors.add("passwords.are.not.matching");
        }
        if(!isPasswordStrong(form.getPassword())) {
            errors.add("passwords.is.too.weak");
        }
        if(isUsernameTaken(form.getUsername())) {
            errors.add("username.is.taken");
        }
        if(errors.isEmpty()) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUsername(form.getUsername());
            userAccount.setPassword(passwordEncoder.encode(form.getPassword()));
            userAccount.setRole(UserRole.USER);
            userAccount.setRegisteredAt(LocalDateTime.now());
            userAccountRepository.save(userAccount);
            return errors;
        } else {
            return errors;
        }
    }

    private boolean isPasswordStrong(String password) {
        return password.length() > 3;
    }
}
