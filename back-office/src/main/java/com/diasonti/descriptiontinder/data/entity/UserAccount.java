package com.diasonti.descriptiontinder.data.entity;

import com.diasonti.descriptiontinder.data.enums.Gender;
import com.diasonti.descriptiontinder.data.enums.GenderPreference;
import com.diasonti.descriptiontinder.data.enums.UserRole;
import com.diasonti.descriptiontinder.data.form.MmPreferenceForm;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserAccount extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "source")
    private List<MmChoice> sourceChoices;

    @JsonIgnore
    @OneToMany(mappedBy = "target")
    private List<MmChoice> targetChoices;

    @Column(name = "gender_preference")
    @Enumerated(EnumType.STRING)
    private GenderPreference genderPreference;

    @Column(name = "age_preference_min")
    private Integer agePreferenceMin;

    @Column(name = "age_preference_max")
    private Integer agePreferenceMax;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "last_candidate_id")
    private UserAccount lastCandidate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public List<MmChoice> getSourceChoices() {
        return sourceChoices;
    }

    public void setSourceChoices(List<MmChoice> sourceChoices) {
        this.sourceChoices = sourceChoices;
    }

    public List<MmChoice> getTargetChoices() {
        return targetChoices;
    }

    public void setTargetChoices(List<MmChoice> targetChoices) {
        this.targetChoices = targetChoices;
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

    public UserAccount getLastCandidate() {
        return lastCandidate;
    }

    public void setLastCandidate(UserAccount lastCandidate) {
        this.lastCandidate = lastCandidate;
    }

    @Transient
    public void updateWithForm(UserProfileForm form) {
        this.setName(form.getName());
        this.setGender(form.getGender());
        this.setAge(form.getAge());
        this.setLocation(form.getLocation());
        this.setDescription(form.getDescription());
    }

    @Transient
    public void updateWithForm(MmPreferenceForm form) {
        this.setGenderPreference(form.getGenderPreference());
        this.setAgePreferenceMin(form.getAgePreferenceMin());
        this.setAgePreferenceMax(form.getAgePreferenceMax());
    }

    @Transient
    public boolean isProfileFilled() {
        if(name == null || name.isEmpty()) return false;
        if(gender == null) return false;
        if(age == null || age < 18) return false;
        if(location == null || location.isEmpty()) return false;
        if(description == null || description.isEmpty()) return false;
        if(genderPreference == null) return false;
        if(agePreferenceMin == null || agePreferenceMin < 18) return false;
        if(agePreferenceMax == null || agePreferenceMax < agePreferenceMin) return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
