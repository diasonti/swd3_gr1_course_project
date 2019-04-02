package com.diasonti.descriptiontinder.service.exceptions;

public class NoCandidateException extends MatchmakingException {

    public NoCandidateException() {
        super("no.candidate.available");
    }
}
