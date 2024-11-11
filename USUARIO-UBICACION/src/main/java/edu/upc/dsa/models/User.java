package edu.upc.dsa.models;


import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String birthDate;
    private Ubication currentPosition;
    private List<Ubication> pathHistory;

    public Ubication getCurrentPosition() {
        return currentPosition;
    }
    //Constructor with no arguments that allows the serialization of a Drone object

    public User() {
    }
    public User(String id, String name, String surname, String email, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.pathHistory = new ArrayList<>();

    }
    public void setCurrentPosition(Ubication currentPosition) {
        this.currentPosition = currentPosition;
        this.pathHistory.add(currentPosition);
    }
    public List<Ubication> getPathHistory() {
        return pathHistory;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    //Constructor that defines the main characteristics of a Drone

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", Nombre=" + name + ", Apellido=" + surname +", email=" + email + ", Cumpleaños=" + birthDate + "]";
    }
}
