package com.example.scanqr_jokeio.helper;

import java.io.Serializable;

public class User implements Serializable {
    String fullname, email, UID, hashPassword;

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", UID='" + UID + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public User(String fullname, String email, String UID, String hashPassword) {
        this.fullname = fullname;
        this.email = email;
        this.UID = UID;
        this.hashPassword = hashPassword;
    }

    public User() {
    }
}
