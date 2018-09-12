package com.example.melaniez.photoapp.Models;

public class User {
    private String full_name;
    private String profile_description;
    private String profile_photo;
    private int followers;
    private int following;
    private int posts;
    private String username;
    private String website;

    public User(String full_name, String profile_description, String profile_photo, int followers, int following, int posts, String username, String website) {
        this.full_name = full_name;
        this.profile_description = profile_description;
        this.profile_photo = profile_photo;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.username = username;
        this.website = website;
    }

    public User(){
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_description() {
        return profile_description;
    }

    public void setProfile_description(String profile_description) {
        this.profile_description = profile_description;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "User{" +
                "full_name='" + full_name + '\'' +
                ", profile_description='" + profile_description + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                ", username='" + username + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
