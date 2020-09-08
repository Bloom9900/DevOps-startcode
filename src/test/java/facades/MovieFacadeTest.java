package facades;

import dto.MovieDTO;
import utils.EMF_Creator;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    private static String[] actors1 = new String[]{"Tim Robbins", "Morgan Freeman", "Bob Gunton", "William Sadler"};
    private static String[] actors2 = new String[]{"Marlon Brando", "Al Pacino", "James Caan", "Diane Keaton"};
    private static String[] actors3 = new String[]{"Christian Bale", "Heath Ledger", "Aaron Eckhart", "Michael Caine"};
    private static Movie movie1 = new Movie(1994, "The Shawshank Redemption", actors1);
    private static Movie movie2 = new Movie(1972, "The Godfather", actors2);
    private static Movie movie3 = new Movie(2008, "The Dark Knight", actors3);

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(movie1);
            em.persist(movie2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetMovieByID() {
        long id = 1;
        MovieDTO expected = new MovieDTO(movie1);
        MovieDTO result = facade.getMovieByID(id);
        assertEquals(expected.getTitle(), result.getTitle());
    }
    
    @Test
    public void testGetAll() {
        List<MovieDTO> result = facade.getAll();
        assertThat(result, hasSize(2));
    }

//    @Test
//    public void testAddMovie() {
//        MovieDTO expected = new MovieDTO(movie3);
//        MovieDTO result = facade.addMovie(movie3);
//        assertEquals(expected, result);
//    }
}
