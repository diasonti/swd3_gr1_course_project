package com.diasonti.descriptiontinder.config.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController {

    protected List<String> getErrorMessages(Errors errors) {
        return errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.toList());
    }

}
