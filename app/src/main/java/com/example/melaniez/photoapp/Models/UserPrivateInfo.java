package com.example.melaniez.photoapp.Models;

public class UserPrivateInfo {
    private String email;
    private String gender;
    private String phone;
    private String username;

    public UserPrivateInfo(String email, String gender, String phone, String username) {
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
    }

    public UserPrivateInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserPrivateInfo{" +
                "email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
