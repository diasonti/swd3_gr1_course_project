package com.diasonti.descriptiontinder.service.exceptions;

public class ProfileNotFilledException extends MatchmakingException {

    public ProfileNotFilledException() {
        super("profile.not.filled");
    }
}
