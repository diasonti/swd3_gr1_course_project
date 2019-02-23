package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.interfaces.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public UserProfileForm getUserProfile(Long userId) {
        return UserProfileForm.of(userAccountRepository.findById(userId).orElse(null));
    }

    @Transactional
    public List<String> updateUserProfile(UserProfileForm form) {
        final UserAccount userAccount = userAccountRepository.findById(form.getId()).orElse(null);
        return Collections.emptyList();
    }
}
