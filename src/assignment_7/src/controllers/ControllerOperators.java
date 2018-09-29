package assignment_7.src.controllers;

import dao.DAOOperators;
import entities.Operators;
import services.IServiceOperators;
import services.ServiceOperators;

import javax.ws.rs.*;
import java.util.List;

@Path("/operators")
public class ControllerOperators {
    private IServiceOperators service;

    public ControllerOperators(){
        this.service = new ServiceOperators(new DAOOperators());
    }

    @GET
    @Produces("text/json")
    public List<Operators> getAll(){
        return this.service.getAll();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean register(Operators operator){
        return this.service.registration(operator);
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean login(Operators operator){
        return this.service.login(operator);
    }


}
