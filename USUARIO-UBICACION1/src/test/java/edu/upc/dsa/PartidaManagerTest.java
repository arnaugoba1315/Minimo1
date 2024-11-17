package edu.upc.dsa;

import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class PartidaManagerTest {
    private Manager manager;

    @Before
    public void setUp() {
        // Se obtiene la instancia del manager antes de cada test
        this.manager = ManagerImpl.getInstance();
    }

    @After
    public void tearDown() {
        // Se limpia el manager después de cada test
        this.manager.clear();
    }

    @Test
    public void testAddUser() {
        // Test para añadir un usuario y verificar que se ha añadido correctamente
        Ubication position = new Ubication(1, 1, ElementType.DOOR);
        User user = this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), position);

        Assert.assertNotNull(user);
        Assert.assertEquals("1", user.getId());
        Assert.assertEquals("John", user.getName());
        Assert.assertEquals("Doe", user.getSurname());
        Assert.assertEquals("john@upc.edu", user.getEmail());
        Assert.assertNotNull(user.getCurrentPosition());
    }

    @Test
    public void testListUsersOrdered() {
        // Test para verificar que los usuarios se listan ordenados alfabéticamente
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), null);
        this.manager.addUser("2", "Alice", "Smith", "alice@upc.edu", new Date(), null);
        this.manager.addUser("3", "Bob", "Johnson", "bob@upc.edu", new Date(), null);

        List<User> users = this.manager.listUsers();

        Assert.assertEquals(3, users.size());
        // Verificar orden por apellido: Doe, Johnson, Smith
        Assert.assertEquals("Doe", users.get(0).getSurname());
        Assert.assertEquals("Johnson", users.get(1).getSurname());
        Assert.assertEquals("Smith", users.get(2).getSurname());
    }

    @Test
    public void testGetUser() {
        // Test para obtener un usuario por su ID
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), null);

        User user = this.manager.getUser("1");
        Assert.assertNotNull(user);
        Assert.assertEquals("John", user.getName());

        // Probar obtener un usuario que no existe
        User nonExistentUser = this.manager.getUser("999");
        Assert.assertNull(nonExistentUser);
    }

    @Test
    public void testAddPointOfInterest() {
        // Test para añadir y verificar puntos de interés
        this.manager.addPointOfInterest(1, 1, ElementType.DOOR);
        this.manager.addPointOfInterest(2, 2, ElementType.POTION);

        List<Ubication> doors = this.manager.getPointsOfInterestByType(ElementType.DOOR);
        List<Ubication> potions = this.manager.getPointsOfInterestByType(ElementType.POTION);

        Assert.assertEquals(1, doors.size());
        Assert.assertEquals(1, potions.size());
    }

    @Test
    public void testRegisterUserAtPointOfInterest() {
        // Test para registrar un usuario en un punto de interés
        Ubication position = new Ubication(1, 1, ElementType.DOOR);
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), position);
        this.manager.addPointOfInterest(1, 1, ElementType.DOOR);

        this.manager.registerUserAtPointOfInterest("1", 1, 1);

        List<Ubication> userPoints = this.manager.getUserPointsOfInterest("1");
        Assert.assertEquals(1, userPoints.size());
        Assert.assertEquals(1, userPoints.get(0).getX());
        Assert.assertEquals(1, userPoints.get(0).getY());
    }

    @Test
    public void testGetUsersAtPointOfInterest() {
        // Test para obtener usuarios en un punto de interés específico
        this.manager.addPointOfInterest(1, 1, ElementType.DOOR);

        Ubication position = new Ubication(1, 1, ElementType.DOOR);
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), position);
        this.manager.addUser("2", "Alice", "Smith", "alice@upc.edu", new Date(), position);

        this.manager.registerUserAtPointOfInterest("1", 1, 1);
        this.manager.registerUserAtPointOfInterest("2", 1, 1);

        List<User> usersAtPoint = this.manager.getUsersAtPointOfInterest(1, 1);
        Assert.assertEquals(2, usersAtPoint.size());
    }

    @Test
    public void testGetPointsOfInterestByType() {
        // Test para obtener puntos de interés por tipo
        this.manager.addPointOfInterest(1, 1, ElementType.DOOR);
        this.manager.addPointOfInterest(2, 2, ElementType.DOOR);
        this.manager.addPointOfInterest(3, 3, ElementType.POTION);

        List<Ubication> doors = this.manager.getPointsOfInterestByType(ElementType.DOOR);
        List<Ubication> potions = this.manager.getPointsOfInterestByType(ElementType.POTION);

        Assert.assertEquals(2, doors.size());
        Assert.assertEquals(1, potions.size());
    }

    @Test
    public void testUpdateUserPosition() {
        // Test para actualizar la posición de un usuario
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), new Ubication(1, 1, ElementType.DOOR));

        this.manager.updateUserPosition("1", 2, 2);

        Ubication newPosition = this.manager.getUserCurrentPosition("1");
        Assert.assertNotNull(newPosition);
        Assert.assertEquals(2, newPosition.getX());
        Assert.assertEquals(2, newPosition.getY());
    }

    @Test
    public void testClear() {
        // Test para verificar que el método clear funciona correctamente
        this.manager.addUser("1", "John", "Doe", "john@upc.edu", new Date(), null);
        this.manager.addPointOfInterest(1, 1, ElementType.DOOR);

        Assert.assertNotEquals(0, this.manager.size());

        this.manager.clear();

        Assert.assertEquals(0, this.manager.size());
        Assert.assertTrue(this.manager.listUsers().isEmpty());
    }
}