package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerImpl implements Manager {
    private static volatile ManagerImpl instance;
    private Map<String, User> users;
    private Map<String, List<Ubication>> Ubacation;
    private Map<String, Ubication> Ubication1;
    private static final Logger logger = Logger.getLogger(ManagerImpl.class);

    private ManagerImpl() {
        this.users = new HashMap<>();
        this.Ubacation = new HashMap<>();
        this.Ubication1 = new HashMap<>();
    }

    public static ManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ManagerImpl.class) {
                if (instance == null) {
                    instance = new ManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void addUser(String id, String name, String surname, String email, String birthDate) {
        logger.info("addUser(" + id + ", " + name + ", " + surname + ", " + email + ", " + birthDate + ")");
        User user = new User(id, name, surname, email, birthDate);
        this.users.put(id, user);
        this.Ubacation.put(id, new ArrayList<>());
        logger.info("User added: " + user);
    }

    @Override
    public List<User> listUsers() {
        logger.info("listUsers()");
        List<User> userList = new ArrayList<>(this.users.values());
        userList.sort(Comparator.comparing(User::getSurname).thenComparing(User::getName));
        logger.info("Users listed: " + userList);
        return userList;
    }

    @Override
    public User getUser(String id) {
        logger.info("getUser(" + id + ")");
        User user = this.users.get(id);
        if (user == null) {
            logger.error("User not found: " + id);
            return null;
        }
        logger.info("User found: " + user);
        return user;
    }

    @Override
    public void addPointOfInterest(int x, int y, ElementType type) {
        logger.info("addPointOfInterest(" + x + ", " + y + ", " + type + ")");
        String key = x + "," + y;
        Ubication point = new Ubication(x, y, type);
        this.Ubication1.put(key, point);
        logger.info("Point of Interest added: " + point);
    }

    @Override
    public void registerUserAtPointOfInterest(String userId, int x, int y) {
        logger.info("registerUserAtPointOfInterest(" + userId + ", " + x + ", " + y + ")");
        User user = this.getUser(userId);
        if (user == null) {
            logger.error("User not found: " + userId);
            return;
        }
        String key = x + "," + y;
        Ubication point = this.Ubication1.get(key);
        if (point == null) {
            logger.error("Point of Interest not found at: " + key);
            return;
        }
        List<Ubication> points = this.Ubacation.get(userId);
        if (points == null) {
            points = new ArrayList<>();
            this.Ubacation.put(userId, points);
        }
        points.add(point);
        logger.info("User " + userId + " registered at Point of Interest: " + point);
    }

    @Override
    public List<Ubication> getUserPointsOfInterest(String userId) {
        logger.info("getUserPointsOfInterest(" + userId + ")");
        return new ArrayList<>(this.Ubacation.getOrDefault(userId, Collections.emptyList()));
    }

    @Override
    public List<User> getUsersAtPointOfInterest(int x, int y) {
        logger.info("getUsersAtPointOfInterest(" + x + ", " + y + ")");
        String key = x + "," + y;
        List<User> usersAtPoint = this.users.values().stream()
                .filter(user -> this.Ubacation.get(user.getId()).stream()
                        .anyMatch(p -> p.getX() == x && p.getY() == y))
                .collect(Collectors.toList());
        return usersAtPoint;
    }

    @Override
    public List<Ubication> getPointsOfInterestByType(ElementType type) {
        logger.info("getPointsOfInterestByType(" + type + ")");
        List<Ubication> collect = this.Ubication1.values().stream()
                .filter(point -> point.getType().equals(type) )
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void updateUserPosition(String userId, int x, int y) {

    }

    @Override
    public Ubication getUserCurrentPosition(String userId) {
        return null;
    }

    @Override
    public void clear() {
        logger.info("clear()");
        instance = null;
        this.users.clear();
        this.Ubacation.clear();
        this.Ubication1.clear();
    }

    @Override
    public void addPointOfInterest(int x, int y, java.lang.annotation.ElementType type) {

    }


}
