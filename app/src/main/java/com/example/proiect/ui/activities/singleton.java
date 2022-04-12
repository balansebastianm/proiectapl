package com.example.proiect.ui.activities;

public class singleton {

    private static singleton instance;

    public static singleton getInstance() {
        if (instance == null)
            instance = new singleton();
        return instance;
    }

    private singleton() {
    }

    private String id;
    private String username;
    private String last_name;
    private String first_name;
    private String email;
    private String isDoctor;
    public String getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(String isDoctor) {
        this.isDoctor = isDoctor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}