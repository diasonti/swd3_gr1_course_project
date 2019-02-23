package com.diasonti.descriptiontinder.service;

import com.diasonti.descriptiontinder.config.security.UserAccountHolder;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.repository.interfaces.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public UserProfileForm getUserProfile(Long userId) {
        return UserProfileForm.of(userAccountRepository.findById(userId).orElse(null));
    }

    @Transactional
    public void updateUserProfile(UserProfileForm form) {
        final Long currentUserId = UserAccountHolder.getCurrentUser().getId();
        final UserAccount currentUser = userAccountRepository.findById(currentUserId).orElse(null);
        if (currentUser != null) {
            currentUser.updateWithProfileForm(form);
            userAccountRepository.save(currentUser);
        }
    }
}
