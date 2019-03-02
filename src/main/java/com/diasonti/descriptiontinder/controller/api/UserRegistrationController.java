package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.config.controller.BaseController;
import com.diasonti.descriptiontinder.data.form.UserRegistrationForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class UserRegistrationController extends BaseController {

    private final UserAccountService registrationService;

    @Autowired
    public UserRegistrationController(UserAccountService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/isUsernameAvailable")
    public RestMessage checkUsername(@RequestParam String username) {
        return RestMessage.ok(registrationService.isUsernameAvailable(username));
    }

    @PostMapping("/submit")
    public RestMessage register(@Valid UserRegistrationForm form, Errors errors) {
        if (errors.hasErrors()) {
            return RestMessage.error(getErrorMessages(errors));
        } else {
            registrationService.register(form);
            return RestMessage.ok();
        }
    }

}
