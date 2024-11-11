package edu.upc.dsa.services;

import edu.upc.dsa.Manager;
import edu.upc.dsa.ManagerImpl;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.Ubication;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Api(value = "/services", description = "Endpoint to Track Service")
@Path("/services")

public class GameService {
    private Manager manager = ManagerImpl.getInstance();

    @POST
    @ApiOperation(value = "Crea usuarios")
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User user) {
        manager.addUser(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getBirthDate());
    }

    @GET
    @ApiOperation(value = "Devuelve la lista de Usuarios")
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() {
        return manager.listUsers();
    }

    @GET
    @ApiOperation(value = "Devuelve el id del nombre que introduzcamos")
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") String id) {
        return manager.getUser(id);
    }

    @POST
    @ApiOperation(value = "Crea una ubicacion x y type")
    @Path("/points")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPointOfInterest(Ubication point) {
        manager.addPointOfInterest(point.getX(), point.getY(), point.getType());
    }

    @POST
    @ApiOperation(value = "Crea una ubicacion para un determinado usuario  ")
    @Path("/users/{userId}/points")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registerUserAtPointOfInterest(@PathParam("userId") String userId, Ubication point) {
        manager.registerUserAtPointOfInterest(userId, point.getX(), point.getY());
    }

    @GET
    @ApiOperation(value = "Devuelve la posion de cierto Usuario")
    @Path("/users/{userId}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ubication> getUserPointsOfInterest(@PathParam("userId") String userId) {
        return manager.getUserPointsOfInterest(userId);
    }

    @GET

    @ApiOperation(value = "Devuelve la posicion de los usuarios")
    @Path("/points")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ubication> getPointsOfInterestByType(@QueryParam("type") ElementType type) {
        return manager.getPointsOfInterestByType(type);
    }
    @PUT
    @ApiOperation(value = "Pone una posicion a cierto usuario")
    @Path("/users/{userId}/position")
    public void updateUserPosition(@PathParam("userId") String userId, @QueryParam("x") int x, @QueryParam("y") int y) {
        manager.updateUserPosition(userId, x, y);
    }
    @GET
    @ApiOperation(value = "Devuelve la posion actual de cierto Usuario")
    @Path("/users/{userId}/position")
    @Produces(MediaType.APPLICATION_JSON)
    public Ubication getUserCurrentPosition(@PathParam("userId") String userId) {
        return manager.getUserCurrentPosition(userId);
    }
}

