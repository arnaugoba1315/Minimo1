package edu.upc.dsa.services;

import edu.upc.dsa.Manager;
import edu.upc.dsa.ManagerImpl;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Api(value = "/game", description = "Servicio de gestión del juego EETAC")
@Path("/game")
public class GameService {

    private Manager manager;

    public GameService() {
        this.manager = ManagerImpl.getInstance();
        if (manager.size() == 0) {
            this.initializeGame();
        }
    }

    private void initializeGame() {
        // Inicializar puntos de interés en el mapa
        addInitialPointsOfInterest();
        // Crear usuarios de ejemplo
        createSampleUsers();
        // Registrar algunas interacciones iniciales
        registerInitialInteractions();
    }

    private void addInitialPointsOfInterest() {
        // Entrada principal
        manager.addPointOfInterest(1, 1, ElementType.DOOR);
        // Objetos y recompensas
        manager.addPointOfInterest(2, 2, ElementType.POTION);
        manager.addPointOfInterest(3, 3, ElementType.SWORD);
        manager.addPointOfInterest(4, 4, ElementType.COIN);
        // Estructuras
        manager.addPointOfInterest(5, 5, ElementType.BRIDGE);
        manager.addPointOfInterest(6, 6, ElementType.WALL);
        // Elementos del entorno
        manager.addPointOfInterest(7, 7, ElementType.TREE);
        manager.addPointOfInterest(8, 8, ElementType.GRASS);
    }

    private void createSampleUsers() {
        // Crear usuarios con diferentes posiciones iniciales
        Ubication startPos1 = new Ubication(1, 1, ElementType.DOOR);
        Ubication startPos2 = new Ubication(2, 2, ElementType.POTION);
        Ubication startPos3 = new Ubication(3, 3, ElementType.SWORD);

        // Usuarios estudiantes
        manager.addUser("est001", "Juan", "García", "juan.garcia@estudiantat.upc.edu", new Date(), startPos1);
        manager.addUser("est002", "María", "López", "maria.lopez@estudiantat.upc.edu", new Date(), startPos2);
        manager.addUser("est003", "Carlos", "Martínez", "carlos.martinez@estudiantat.upc.edu", new Date(), startPos3);

        // Usuarios profesores
        manager.addUser("prof001", "Ana", "Rodríguez", "ana.rodriguez@upc.edu", new Date(), new Ubication(4, 4, ElementType.COIN));
        manager.addUser("prof002", "Pedro", "Sánchez", "pedro.sanchez@upc.edu", new Date(), new Ubication(5, 5, ElementType.BRIDGE));
    }

    private void registerInitialInteractions() {
        // Registrar algunas interacciones de usuarios con puntos de interés
        manager.registerUserAtPointOfInterest("est001", 1, 1);
        manager.registerUserAtPointOfInterest("est002", 2, 2);
        manager.registerUserAtPointOfInterest("est003", 3, 3);
    }

    @GET
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Retorna una lista de todos los usuarios ordenada alfabéticamente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = this.manager.listUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @GET
    @ApiOperation(value = "Obtener usuario por ID", notes = "Retorna la información de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        User user = this.manager.getUser(id);
        if (user == null) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }
        return Response.ok(user).build();
    }

    @POST
    @ApiOperation(value = "Crear nuevo usuario", notes = "Añade un nuevo usuario al juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario creado exitosamente"),
            @ApiResponse(code = 400, message = "Datos de usuario inválidos"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (user.getId() == null || user.getName() == null || user.getSurname() == null) {
            return Response.status(400).entity("Faltan datos obligatorios").build();
        }
        User newUser = this.manager.addUser(user.getId(), user.getName(), user.getSurname(),
                user.getEmail(), user.getBirthDate(), user.getCurrentPosition());
        return Response.status(201).entity(newUser).build();
    }

    @GET
    @ApiOperation(value = "Obtener puntos de interés por tipo", notes = "Retorna todos los puntos de un tipo específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso"),
            @ApiResponse(code = 400, message = "Tipo inválido")
    })
    @Path("/points/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPointsByType(@PathParam("type") ElementType type) {
        List<Ubication> points = this.manager.getPointsOfInterestByType(type);
        GenericEntity<List<Ubication>> entity = new GenericEntity<List<Ubication>>(points) {};
        return Response.ok(entity).build();
    }

    @POST
    @ApiOperation(value = "Registrar usuario en punto de interés",
            notes = "Registra que un usuario ha pasado por un punto específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registro exitoso"),
            @ApiResponse(code = 404, message = "Usuario o punto no encontrado")
    })
    @Path("/users/{userId}/points/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUserAtPoint(@PathParam("userId") String userId,
                                        @PathParam("x") int x,
                                        @PathParam("y") int y) {
        try {
            this.manager.registerUserAtPointOfInterest(userId, x, y);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtener historial de usuario",
            notes = "Retorna todos los puntos por los que ha pasado un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/users/{userId}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserHistory(@PathParam("userId") String userId) {
        List<Ubication> history = this.manager.getUserPointsOfInterest(userId);
        if (history == null) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }
        GenericEntity<List<Ubication>> entity = new GenericEntity<List<Ubication>>(history) {};
        return Response.ok(entity).build();
    }

    @GET
    @ApiOperation(value = "Obtener usuarios en punto",
            notes = "Retorna todos los usuarios que están en un punto específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Punto no encontrado")
    })
    @Path("/points/{x}/{y}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersAtPoint(@PathParam("x") int x, @PathParam("y") int y) {
        List<User> users = this.manager.getUsersAtPointOfInterest(x, y);
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @PUT
    @ApiOperation(value = "Actualizar posición de usuario",
            notes = "Actualiza la posición actual de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Posición actualizada"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/users/{userId}/position/{x}/{y}")
    public Response updateUserPosition(@PathParam("userId") String userId,
                                       @PathParam("x") int x,
                                       @PathParam("y") int y) {
        try {
            this.manager.updateUserPosition(userId, x, y);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }
    }
}