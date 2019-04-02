package com.diasonti.descriptiontinder.service.exceptions;

public class IllegalVoteException extends MatchmakingException {

    public IllegalVoteException() {
        super("illegal.vote");
    }
}
