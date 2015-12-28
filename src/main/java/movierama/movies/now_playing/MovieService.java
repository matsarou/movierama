package movierama.movies.now_playing;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

/**
 * Provides all the Rest Services for the Movierama web application.
 * @author Maria
 *
 */
public interface MovieService {
	
	/**
	 * Rest service to retrieve the now playing movies from the two APIs:
	 * <ul>
	 * <li>api.themoviedb.org</li>
	 * <li>api.rottentomatoes.com</li>
	 * </ul>
	 * @return
	 */
	@Cacheable("movies")
	List<Movie> nowPlayingMovies();
	
	/**
	 * Rest service to search for a movie, based on its title, from the two APIs:
	 * <ul>
	 * <li>api.themoviedb.org</li>
	 * <li>api.rottentomatoes.com</li>
	 * </ul>
	 * @param searchTitle
	 * @return
	 */
	@Cacheable("search")
	Collection<Movie> search(String searchTitle);
	
	/**
	 * Gets the list with the thrown exceptions, during the call of a Rest Service.
	 * @return
	 */
	List<InvalidResourceException> getThrownExceptions();
	
	/**
	 * Clean the array with the exceptions each time we load another page, or we call another Rest Service.
	 */
	void clearThrownExceptions();
}
