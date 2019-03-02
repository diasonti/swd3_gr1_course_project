package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.config.controller.BaseController;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.MmPreferenceForm;
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

    private final UserProfileService profileService;

    @Autowired
    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/info")
    public RestMessage getMyUserProfile(UserAccount user) {
        final UserProfileForm form = profileService.getUserProfile(user.getId());
        if (form != null) {
            return RestMessage.ok(form);
        } else {
            return RestMessage.error("profile.not.found");
        }
    }

    @PostMapping("/info")
    public RestMessage updateMyUserProfile(@Valid UserProfileForm form, Errors errors) {
        if (errors.hasErrors()) {
            return RestMessage.error(getErrorMessages(errors));
        } else {
            profileService.updateUserProfile(form);
            return RestMessage.ok();
        }
    }

    @GetMapping("/pref")
    public RestMessage getMyMatchmakingPreferences(UserAccount user) {
        final MmPreferenceForm form = profileService.getMatchmakingPreferences(user.getId());
        if (form != null) {
            return RestMessage.ok(form);
        } else {
            return RestMessage.error("preferences.not.found");
        }
    }

    @PostMapping("/pref")
    public RestMessage updateMyMatchmakingPreferences(@Valid MmPreferenceForm form, Errors errors) {
        if (errors.hasErrors()) {
            return RestMessage.error(getErrorMessages(errors));
        } else {
            profileService.updateMatchmakingPreferences(form);
            return RestMessage.ok();
        }
    }

}
