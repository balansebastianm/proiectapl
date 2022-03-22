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
    private String nume;
    private String prenume;
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

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}