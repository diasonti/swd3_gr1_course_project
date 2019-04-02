package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.GenderPreference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MmPreferenceForm extends BaseForm {

    @NotNull(message = "gender.preference.empty")
    @JsonProperty("gender")
    private GenderPreference genderPreference;

    @NotNull(message = "age.preference.min.empty")
    @Min(value = 18, message = "age.preference.min.underage")
    @JsonProperty("ageMin")
    private Integer agePreferenceMin;

    @NotNull(message = "age.preference.max.empty")
    @JsonProperty("ageMax")
    private Integer agePreferenceMax;

    public static MmPreferenceForm of(UserAccount user) {
        MmPreferenceForm form = null;
        if(user != null) {
            form = new MmPreferenceForm();
            form.setId(user.getId());
            form.setGenderPreference(user.getGenderPreference());
            form.setAgePreferenceMax(user.getAgePreferenceMax());
            form.setAgePreferenceMin(user.getAgePreferenceMin());
        }
        return form;
    }

    public GenderPreference getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(GenderPreference genderPreference) {
        this.genderPreference = genderPreference;
    }

    public Integer getAgePreferenceMin() {
        return agePreferenceMin;
    }

    public void setAgePreferenceMin(Integer agePreferenceMin) {
        this.agePreferenceMin = agePreferenceMin;
    }

    public Integer getAgePreferenceMax() {
        return agePreferenceMax;
    }

    public void setAgePreferenceMax(Integer agePreferenceMax) {
        this.agePreferenceMax = agePreferenceMax;
    }
}
