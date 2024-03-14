package com.project.project.model;
import java.util.Date; // Add this import for the Date class

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Fix this import
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class user {
   @Id
   
    int id ;
    private String UsersName;
    private String UsersEmail;
    private String Usersn;
    private String UsersPwd;
    private String pwdRepeat;
    private String UsersRole;

    // Getters and setters
    public String getUsersName() {
        return UsersName;
    }

    public void setUsersName(String usersName) {
        UsersName = usersName;
    }

    public String getUsersEmail() {
        return UsersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        UsersEmail = usersEmail;
    }

    public String getUsersn() {
        return Usersn;
    }

    public void setUsersn(String usersn) {
        Usersn = usersn;
    }

    public String getUsersPwd() {
        return UsersPwd;
    }

    public void setUsersPwd(String usersPwd) {
        UsersPwd = usersPwd;
    }

    public String getPwdRepeat() {
        return pwdRepeat;
    }

    public void setPwdRepeat(String pwdRepeat) {
        this.pwdRepeat = pwdRepeat;
    }

    public String getUsersRole() {
        return UsersRole;
    }

    public void setUsersRole(String usersRole) {
        UsersRole = usersRole;
    }
}
