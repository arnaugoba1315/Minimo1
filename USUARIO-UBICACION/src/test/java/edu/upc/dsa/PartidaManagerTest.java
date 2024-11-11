package edu.upc.dsa;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.ElementType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PartidaManagerTest {
    private Manager manager;

    @Before
    public void setUp() {
        manager = ManagerImpl.getInstance();
        manager.addUser("1", "John", "Doe", "john.doe@example.com", "12-03-1980");
        manager.addUser("2", "Jane", "Smith", "jane.smith@example.com", "13-09-1981");
        manager.addPointOfInterest(0, 0, ElementType.DOOR);
        manager.addPointOfInterest(1, 1, ElementType.COIN);
    }

    @After
    public void tearDown() {
        manager.clear();
    }

    @Test
    public void testAddUser() {
        User user = manager.getUser("1");
        assertNotNull(user);
        assertEquals("John", user.getName());
    }

    @Test
    public void testListUsers() {
        List<User> users = manager.listUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testAddPointOfInterest() {
        manager.addPointOfInterest(2, 2, ElementType.GRASS);
        List<Ubication> points = manager.getPointsOfInterestByType(ElementType.GRASS);
        assertEquals(1, points.size());
    }

    @Test
    public void testRegisterUserAtPointOfInterest() {
        manager.registerUserAtPointOfInterest("1", 0, 0);
        List<Ubication> points = manager.getUserPointsOfInterest("1");
        assertEquals(1, points.size());
    }

    @Test
    public void testGetUsersAtPointOfInterest() {
        manager.registerUserAtPointOfInterest("1", 0, 0);
        List<User> users = manager.getUsersAtPointOfInterest(0, 0);
        assertEquals(1, users.size());
    }
}