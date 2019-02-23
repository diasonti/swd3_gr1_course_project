package com.diasonti.descriptiontinder.data.form;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.Gender;

public class UserProfileForm extends BaseForm {

    private String name;
    private Gender gender;
    private int age;
    private String location;
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
