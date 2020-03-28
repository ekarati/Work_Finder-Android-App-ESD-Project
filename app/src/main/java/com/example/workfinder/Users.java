package com.example.workfinder;

public class Users {
    public String name, email, role;

    public Users(){

    }

    public Users(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public  String getUserName(){return  name;}

    public  String getUserEmail(){return  email;}

    public  String getRole(){return  role;}

    public void setUserName(String name) {
        this.name = name;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}