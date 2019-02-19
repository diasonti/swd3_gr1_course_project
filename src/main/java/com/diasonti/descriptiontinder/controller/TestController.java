package com.diasonti.descriptiontinder.controller;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/ping")
    public TestReply test() {
        return new TestReply();
    }

    @GetMapping("/test/auth")
    public UserAccount auth(UserAccount userAccount) {
        return userAccount;
    }

    private class TestReply {
        private int code = 0;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}
