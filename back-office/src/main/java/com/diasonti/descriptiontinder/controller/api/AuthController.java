package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserAccountService userAccountService;

    @Autowired
    public AuthController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping(path = "/token")
    public String getAuthToken(@RequestParam String username, @RequestParam String password) {
        return userAccountService.getAuthToken(username, password);
    }

}
