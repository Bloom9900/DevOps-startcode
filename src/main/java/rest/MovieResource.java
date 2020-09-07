package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.MovieFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entities.Movie;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movies")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final MovieFacade facade =  MovieFacade.getMovieFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovies() {
        return gson.toJson(facade.getAll());
    }
    
    @Path("/{id}")
    @GET
    @Produces
    public String getMovieByID(@PathParam("id") long id) {
        return gson.toJson(facade.getMovieByID(id));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addMovie(Movie movie) {
        facade.addMovie(movie);
        return gson.toJson(facade.getMovieByID(movie.getId()));
    }
}
