package com.diasonti.descriptiontinder.config.security;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class TinderUser extends User {

    private final UserAccount userAccount;

    TinderUser(UserAccount userAccount) {
        super(userAccount.getUsername(), userAccount.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userAccount.getRole().name())));
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
