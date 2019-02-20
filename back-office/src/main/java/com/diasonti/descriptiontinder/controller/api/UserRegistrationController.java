package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.data.form.UserRegistrationForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/registration")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService registrationService;

    @GetMapping("/isUsernameTaken")
    public RestMessage checkUsername(@RequestParam String username) {
        return RestMessage.ok(registrationService.isUsernameTaken(username));
    }

    @PostMapping("/submit")
    public RestMessage register(UserRegistrationForm form) {
        List<String> errors = registrationService.register(form);
        if(errors.isEmpty()) {
            return RestMessage.ok();
        }
        return RestMessage.error(errors);
    }

}
