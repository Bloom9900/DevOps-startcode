package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(m) FROM Movie m");
            return (long) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<MovieDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        List<MovieDTO> movieDTOs = new ArrayList();
        try {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
            List<Movie> movies = query.getResultList();
            for (Movie movie : movies) {
                movieDTOs.add(new MovieDTO(movie));
            }
            return movieDTOs;
        } finally {
            em.close();
        }
    }
    
    public MovieDTO getMovieByID(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            return new MovieDTO(movie);
        } finally {
            em.close();
        }
    }
    
    public MovieDTO addMovie(Movie movie) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return new MovieDTO(movie);
        } finally {
            em.close();
        }
    }
    
    public MovieDTO getMovieByTitle(String title) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m WHERE m.title = :title", Movie.class);
            query.setParameter("title", title);
            Movie movie = query.getSingleResult();
            return new MovieDTO(movie);
        } finally {
            em.close();
        }
    }

}
