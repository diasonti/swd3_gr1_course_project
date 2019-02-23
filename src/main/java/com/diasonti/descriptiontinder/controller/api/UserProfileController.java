package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @GetMapping("/my")
    public RestMessage getUserProfile(UserAccount user) {
        final UserProfileForm form = profileService.getUserProfile(user.getId());
        if (form != null) {
            return RestMessage.ok(form);
        } else {
            return RestMessage.error("profile.not.found");
        }
    }

    @PostMapping("/my")
    public RestMessage updateUserProfile(UserAccount user, UserProfileForm form) {
        List<String> errors = profileService.updateUserProfile(form);
        if (errors.isEmpty()) {
            return RestMessage.ok();
        }
        return RestMessage.error(errors);
    }

}
