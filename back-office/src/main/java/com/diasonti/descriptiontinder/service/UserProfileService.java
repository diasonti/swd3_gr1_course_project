package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.config.security.UserAccountHolder;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.MmPreferenceForm;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserProfileService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileForm getUserProfile(Long userId) {
        return UserProfileForm.of(userAccountRepository.findById(userId).orElse(null));
    }

    @Transactional(readOnly = true)
    public MmPreferenceForm getMatchmakingPreferences(Long userId) {
        return MmPreferenceForm.of(userAccountRepository.findById(userId).orElse(null));
    }

    @Transactional
    public void updateUserProfile(UserProfileForm form) {
        userAccountRepository.findById(UserAccountHolder.getCurrentUserId()).ifPresent(user -> user.updateWithForm(form));
    }

    @Transactional
    public void updateMatchmakingPreferences(MmPreferenceForm form) {
        userAccountRepository.findById(UserAccountHolder.getCurrentUserId()).ifPresent(user -> user.updateWithForm(form));
    }
}
