package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserProfileForm extends BaseForm {

    @Size(min = 1, max = 255, message = "invalid.name.length")
    private String name;

    @NotNull
    private Gender gender;

    @Size(min = 18, message = "underage")
    private int age;

    @Size(min = 1, max = 255, message = "invalid.location.length")
    private String location;

    @Size(min = 1, max = 255, message = "invalid.description.length")
    private String description;

    public static UserProfileForm of(UserAccount user) {
        UserProfileForm form = null;
        if(user != null) {
            form = new UserProfileForm();
            form.setId(user.getId());
            form.setName(user.getName());
            form.setGender(user.getGender());
            form.setAge(user.getAge());
            form.setLocation(user.getLocation());
            form.setDescription(user.getDescription());
        }
        return form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
