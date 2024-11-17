package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class ManagerImpl implements Manager {
    // Singleton instance
    private static volatile ManagerImpl instance;
    private static final Logger logger = Logger.getLogger(ManagerImpl.class);

    // Data structures
    private final Map<String, User> users;
    private final Map<String, List<Ubication>> userPointsHistory; // Historial de puntos por usuario
    private final Map<String, Ubication> pointsOfInterest;        // Puntos de interés en el mapa
    private final Map<String, Ubication> userCurrentPositions;    // Posiciones actuales de usuarios

    private ManagerImpl() {
        this.users = new HashMap<>();
        this.userPointsHistory = new HashMap<>();
        this.pointsOfInterest = new HashMap<>();
        this.userCurrentPositions = new HashMap<>();
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
    public User addUser(String id, String name, String surname, String email, Date birthDate, Ubication currentPosition) {
        logger.info("Adding user: " + id + ", " + name + " " + surname);

        User user = new User(id, name, surname, email, birthDate, currentPosition);
        users.put(id, user);
        userPointsHistory.put(id, new ArrayList<>());
        if (currentPosition != null) {
            userCurrentPositions.put(id, currentPosition);
        }

        logger.info("User added successfully: " + user);
        return user;
    }

    @Override
    public User addUser(String id, String name, String surname, String email) {
        logger.info("Adding user with basic info: " + id);
        return addUser(id, name, surname, email, null, null);
    }

    @Override
    public List<User> listUsers() {
        logger.info("Listing all users");
        List<User> userList = new ArrayList<>(users.values());

        // Ordenar primero por apellido y luego por nombre
        userList.sort((user1, user2) -> {
            int surnameComparison = user1.getSurname().compareToIgnoreCase(user2.getSurname());
            if (surnameComparison != 0) {
                return surnameComparison;
            }
            return user1.getName().compareToIgnoreCase(user2.getName());
        });

        logger.info("Found " + userList.size() + " users");
        return userList;
    }

    @Override
    public User getUser(String id) {
        logger.info("Getting user with id: " + id);
        User user = users.get(id);
        if (user == null) {
            logger.warn("User not found: " + id);
            return null;
        }
        logger.info("User found: " + user);
        return user;
    }

    @Override
    public void addPointOfInterest(int x, int y, ElementType type) {
        logger.info("Adding point of interest at (" + x + "," + y + ") of type " + type);
        String key = createKey(x, y);
        Ubication point = new Ubication(x, y, type);
        pointsOfInterest.put(key, point);
        logger.info("Point of interest added successfully");
    }

    @Override
    public void registerUserAtPointOfInterest(String userId, int x, int y) {
        logger.info("Registering user " + userId + " at point (" + x + "," + y + ")");

        User user = users.get(userId);
        if (user == null) {
            logger.error("User not found: " + userId);
            return;
        }

        String key = createKey(x, y);
        Ubication point = pointsOfInterest.get(key);
        if (point == null) {
            logger.error("Point of interest not found at: " + key);
            return;
        }

        userPointsHistory.get(userId).add(point);
        userCurrentPositions.put(userId, point);
        logger.info("User registered at point successfully");
    }

    @Override
    public List<Ubication> getUserPointsOfInterest(String userId) {
        logger.info("Getting points of interest for user: " + userId);
        return new ArrayList<>(userPointsHistory.getOrDefault(userId, new ArrayList<>()));
    }

    @Override
    public List<User> getUsersAtPointOfInterest(int x, int y) {
        logger.info("Getting users at point (" + x + "," + y + ")");
        String key = createKey(x, y);

        if (!pointsOfInterest.containsKey(key)) {
            logger.error("Point of interest not found at: " + key);
            return new ArrayList<>();
        }

        return users.values().stream()
                .filter(user -> {
                    Ubication currentPos = userCurrentPositions.get(user.getId());
                    return currentPos != null &&
                            currentPos.getX() == x &&
                            currentPos.getY() == y;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Ubication> getPointsOfInterestByType(ElementType type) {
        logger.info("Getting points of interest of type: " + type);
        return pointsOfInterest.values().stream()
                .filter(point -> {
                    ElementType pointType = point.getType();
                    return pointType != null && pointType.equals(type);
                })
                .collect(Collectors.toList());
    }
    @Override
    public void updateUserPosition(String userId, int x, int y) {
        logger.info("Updating position for user " + userId + " to (" + x + "," + y + ")");
        if (!users.containsKey(userId)) {
            logger.error("User not found: " + userId);
            return;
        }
        userCurrentPositions.put(userId, new Ubication(x, y, (ElementType) null));
    }

    @Override
    public Ubication getUserCurrentPosition(String userId) {
        logger.info("Getting current position for user: " + userId);
        return userCurrentPositions.get(userId);
    }

    @Override
    public void clear() {
        logger.info("Clearing all data");
        users.clear();
        userPointsHistory.clear();
        pointsOfInterest.clear();
        userCurrentPositions.clear();
        instance = null;
    }

    @Override
    public int size() {
        return users.size();
    }

    private String createKey(int x, int y) {
        return x + "," + y;
    }
}