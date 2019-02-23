package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.config.controller.BaseController;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController extends BaseController {

    @Autowired
    private UserProfileService profileService;

    @GetMapping
    public RestMessage getMyUserProfile(UserAccount user) {
        final UserProfileForm form = profileService.getUserProfile(user.getId());
        if (form != null) {
            return RestMessage.ok(form);
        } else {
            return RestMessage.error("profile.not.found");
        }
    }

    @PostMapping
    public RestMessage updateMyUserProfile(@Valid UserProfileForm form, Errors errors) {
        if (errors.hasErrors()) {
            return RestMessage.error(getErrorMessages(errors));
        } else {
            profileService.updateUserProfile(form);
            return RestMessage.ok();
        }
    }

}
