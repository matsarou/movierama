package movierama.movies.now_playing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Local repository that holds the movies locally stored movies and 
 * also the original data sources from the external APIs.
 * @author Maria
 *
 */
public class MovieRepository {
	private Map<String , Movie> movies;
	
	MovieRepository(){
		movies=new HashMap<String , Movie>();
	}
	
	
	/**
	 * A movie is persisted locally if it was previously loaded from the now playing movies API call 
	 * or if it was in the results of a user search.
	 * @param movie
	 */
	void persist(Movie movie){
		Movie existing =movies.get(movie.getTitle());
		if(existing!=null){
			String existingDescription=existing.getTitle();
			if(existingDescription.length() > movie.getDescription().length()){
				movie.setDescription(existingDescription);
			}
			int mergedCountReviews=existing.getCountReviews()+movie.getCountReviews();
			movie.setCountReviews(mergedCountReviews);
		}
		movies.put(movie.getTitle(), movie);
	}
	
	/**
	 * A list of movies are persisted locally.
	 * @param moviesList
	 */
	void persist(Collection<Movie> moviesList){
		for(Movie movie:moviesList){
			persist(movie);
		}
	}

	/**
	 * Searches for a movie in the local repository.
	 * @param searchTitle
	 * @return
	 * @throws Exception
	 */
	Collection<Movie> searchForMovie(String searchTitle) throws Exception
	{
         Collection<Movie> results = new ArrayList<Movie>();
        	for(Movie movie:retrieveMovies()){
        		if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(movie.getTitle(), searchTitle)){
        			results.add(movie);
        		}
        	}
         return results;
	}
	
	/**
	 * Gets the persisted locally movies.
	 * @return a collection of {@link Movie}s
	 */
	Collection<Movie> retrieveMovies(){
		return movies.values();
	}
}
