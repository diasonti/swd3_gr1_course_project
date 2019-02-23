package com.diasonti.descriptiontinder.data.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum GenderPreference {
    MALE(Gender.MALE),
    FEMALE(Gender.FEMALE),
    ANY(Gender.values());

    private Gender[] genders;

    GenderPreference(Gender... genders) {
        this.genders = genders;
    }

    public Gender[] getGenders() {
        return genders;
    }

    public String getGendersAsString() {
        return Arrays.stream(this.genders).map(Enum::name).collect(Collectors.joining(", "));
    }
}
