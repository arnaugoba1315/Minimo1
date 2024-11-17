package edu.upc.dsa.models;

import java.util.Date;

public class User {
    private String id;
    private String name;
    private String surname;
    private String email;
    private Date birthDate;  // Cambiado de String a Date
    private Ubication currentPosition;

    public User() {
        // Constructor vacío necesario para JSON
    }

    public User(String id, String name, String surname, String email, Date birthDate, Ubication currentPosition) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.currentPosition = currentPosition;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Ubication getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Ubication currentPosition) {
        this.currentPosition = currentPosition;
    }
}