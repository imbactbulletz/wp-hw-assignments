package assignment_7.src.controllers;

import dao.DAORecipes;
import entities.Recipes;
import services.IServiceRecipes;
import services.ServiceRecipes;

import javax.ws.rs.*;
import java.util.List;

@Path("/recipes")
public class ControllerRecipes {
    private IServiceRecipes service;

    public ControllerRecipes(){
        this.service = new ServiceRecipes(new DAORecipes());
    }

    @GET
    @Produces("text/json")
    public List<Recipes> getAll(){
        return this.service.getAll();
    }

    @POST
    @Consumes("application/json")
    public boolean add(Recipes recipe){
        return this.service.add(recipe);
    }


    @GET
    @Path("/search={username}")
    @Produces("text/json")
    public List<Recipes> searchByName(@PathParam("username") String name){
        return this.service.searchByName(name);
    }

    @GET
    //@Path("/searchPage?pageNumber={pageNumber}&pageSize={pageSize}&searchCriteria={searchCriteria}")
    @Path("/searchPage={pageNumber},{pageSize},{searchCriteria}")
    @Produces("text/json")
    public List<Recipes> getPage(@PathParam("pageNumber") int pageNumber, @PathParam("pageSize") int pageSize,@PathParam("searchCriteria") String searchCriteria){
        return this.service.getPage(pageNumber, pageSize, searchCriteria);
    }

}
