package movierama.movies.now_playing;

/**
 * All the REST API paths, used in the movierama web application.
 * @author Maria
 *
 */
public class MovieResources {
	
	/**
	 * REST API path for querying all the movies from the themoviedb.org
	 */
	static final String THEMOVIEDB="http://api.themoviedb.org/3/movie/now_playing?api_key=186b266209c2da50f898b7977e2a44dd";
	
	/** Query parameter for search in the themoviedb.org, using the movie title.*/
	static final String THEMOVIEDB_SEARCH_MOVIE="http://api.themoviedb.org/3/search/movie?api_key=186b266209c2da50f898b7977e2a44dd&query=";
	
	/**
	 * REST API path for querying all the movies from the site Rotten Tomatoes
	 */
	static final String ROTTENTOMATOES="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	
	/** Query parameter for search in the Rotten Tomatoes, using the movie title.*/
	static final String ROTTENTOMATOES_SEARCH_MOVIE="http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=qtqep7qydngcc7grk4r4hyd9&q=";

	/**
	 * REST API path for querying details(the cast and the reviews) for a specific movie.
	 * @param id the id of the movie in the themoviedb.org
	 * @return
	 */
	static String theMovieDBMovieDetails(int id){
		return "http://api.themoviedb.org/3/movie/"+id+"?api_key=186b266209c2da50f898b7977e2a44dd&append_to_response=reviews,casts";
	}
	
	/**
	 * REST API path for querying details(the cast and the reviews) for a specific movie.
	 * @param id the id of the movie in the Rotten Tomatoes
	 * @return
	 */
	static String rottenTomatoesMovieDetails(int id){
		return "http://api.rottentomatoes.com/api/public/v1.0/movies/"+id+"/reviews.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	}
}
