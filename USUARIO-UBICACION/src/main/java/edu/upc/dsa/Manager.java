package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;

import java.util.List;

public interface Manager {
    void addUser(String id, String name, String surname, String email, String birthDate);
    List<User> listUsers();
    User getUser(String id);

    void addPointOfInterest(int x, int y, ElementType type);
    void registerUserAtPointOfInterest(String userId, int x, int y);

    List<Ubication> getUserPointsOfInterest(String userId);
    List<User> getUsersAtPointOfInterest(int x, int y);
    List<Ubication> getPointsOfInterestByType(ElementType type);
    void updateUserPosition(String userId, int x, int y);
    Ubication getUserCurrentPosition(String userId);
    void clear();



    void addPointOfInterest(int x, int y, java.lang.annotation.ElementType type);


}