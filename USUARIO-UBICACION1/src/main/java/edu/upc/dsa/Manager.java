package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;

import java.util.Date;
import java.util.List;

public interface Manager {
    // Métodos de gestión de usuarios
    User addUser(String id, String name, String surname, String email, Date birthDate, Ubication currentPosition);
    User addUser(String id, String name, String surname, String email);
    List<User> listUsers();
    User getUser(String id);

    // Métodos de gestión de puntos de interés
    void addPointOfInterest(int x, int y, ElementType type);  // Solo mantenemos esta versión
    void registerUserAtPointOfInterest(String userId, int x, int y);
    List<Ubication> getUserPointsOfInterest(String userId);
    List<User> getUsersAtPointOfInterest(int x, int y);
    List<Ubication> getPointsOfInterestByType(ElementType type);

    // Métodos de gestión de posición
    void updateUserPosition(String userId, int x, int y);
    Ubication getUserCurrentPosition(String userId);

    // Métodos de utilidad
    void clear();
    int size();
}