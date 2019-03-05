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
    public CheckResult getAuthToken(@RequestParam String username, @RequestParam String password) {
        final CheckResult result = new CheckResult();
        final String authToken = userAccountService.getAuthToken(username, password);
        if(authToken != null) {
            result.setValidCredentials(true);
            result.setAuthToken(authToken);
        }
        return result;
    }

    private class CheckResult {
        private boolean validCredentials = false;
        private String authToken = null;

        public boolean isValidCredentials() {
            return validCredentials;
        }

        public void setValidCredentials(boolean validCredentials) {
            this.validCredentials = validCredentials;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }
    }

}
