package movierama.movies.now_playing;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import movierama.movies.now_playing.Movie;

/**
 * Implementation of the {@link MovieService}.
 * @author Maria
 *
 */
public class MovieServiceImpl implements MovieService {
	private Client client;
	private List<InvalidResourceException> thrownExceptions;
	
	MovieServiceImpl(){
		client = Client.create();
		thrownExceptions=new ArrayList<InvalidResourceException>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Movie> nowPlayingMovies() {
		
		List<Movie> movies=new ArrayList<Movie>();
		try{
			movies.addAll(nowPlayingTheMovieDBResource());
		}catch(InvalidResourceException exc1){
			thrownExceptions.add(exc1);
		}finally{
			try{
				movies.addAll(nowPlayingRottenTomatoesResource());
			}catch(InvalidResourceException exc2){
				thrownExceptions.add(exc2);
			}
		}

		return movies;
	}
	
	
	@Cacheable("nowplaying_themoviedb")
	public List<Movie> nowPlayingTheMovieDBResource(){
		List<Movie> movies=new ArrayList<Movie>();
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.THEMOVIEDB);
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException("The api.themoviedb.org returned error: "+e.getMessage()+"\n");
		}
	
		JSONObject json = new JSONObject(response.getEntity(String.class));
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException("The api.themoviedb.org returned error: ".concat(json.getString("status_message")));
		}
	
		 JSONArray results=json.getJSONArray("results");
		 for(int i = 0;i < results.length();i++){
			 Movie movieFromJSON = convertJSONToTheMovieDBMovie(results.getJSONObject(i));
			 movies.add(movieFromJSON);
		 }
		 return movies;
	}
	
	
	@Cacheable("nowplaying_rottentomatoes")
	public List<Movie> nowPlayingRottenTomatoesResource(){
		List<Movie> movies=new ArrayList<Movie>();
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.ROTTENTOMATOES);
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException("The api.rottentomatoes returned error: "+e.getMessage());
		}
		
		JSONObject json = new JSONObject(response.getEntity(String.class));
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException("The api.rottentomatoes returned error: ".concat(json.getString("error")));
		}
	
		 JSONArray results=json.getJSONArray("movies");
		 
		 for(int i=0;i<results.length();i++){
			 Movie movieFromJSON=convertJSONToRottenTomatoesMovie(results.getJSONObject(i));
			 movies.add(movieFromJSON);
		 }
		 return movies;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Collection<Movie> search(String searchTitle) {
		List<Movie> movies=new ArrayList<Movie>();
		try{
			movies.addAll(searchInTheMovieDB(searchTitle));
		}catch(InvalidResourceException exc1){
			thrownExceptions.add(exc1);
		}finally{
			try{
				movies.addAll(searchInRottenTomatoes(searchTitle));
			}catch(InvalidResourceException exc2){
				thrownExceptions.add(exc2);
			}
		}

		return movies;
	}
	
	@Cacheable("search_themoviedb")
	public List<Movie> searchInTheMovieDB(String searchTitle) {
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.THEMOVIEDB_SEARCH_MOVIE.concat(searchTitle));
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException("The api.themoviedb.org returned error: "+e.getMessage()+"\n");
		}
		
		JSONObject json = new JSONObject(response.getEntity(String.class));
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException("The api.themoviedb.org returned error: "+json.getString("status_message"));
		}
		
		JSONArray results=json.getJSONArray("results");
		List<Movie> foundMovies=new ArrayList<Movie>();
		for(int i=0;i<results.length();i++){
			Movie movieFromJSON=convertJSONToTheMovieDBMovie(results.getJSONObject(i));
			foundMovies.add(movieFromJSON);
		}
		
		return foundMovies;
	}
	
	@Cacheable("search_rottentomatoes")
	public List<Movie> searchInRottenTomatoes(String searchTitle) {
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.ROTTENTOMATOES_SEARCH_MOVIE.concat(searchTitle));
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException("The api.rottentomatoes returned error: "+e.getMessage());
		}
		JSONObject json = new JSONObject(response.getEntity(String.class));
		
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException("The api.rottentomatoes returned error: "+json.getString("error"));
		}
	
		 JSONArray results=json.getJSONArray("movies");
		 List<Movie> foundMovies=new ArrayList<Movie>();
		 for(int i=0;i<results.length();i++){
			 Movie movieFromJSON = convertJSONToRottenTomatoesMovie(results.getJSONObject(i));
			 foundMovies.add(movieFromJSON);
		 }
		 
		 return foundMovies;
	}
	
	private Movie convertJSONToTheMovieDBMovie(JSONObject json){
		int movieId = json.getInt("id");
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.theMovieDBMovieDetails(movieId));
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException(e.getMessage());
		}
		
		JSONObject json2 = new JSONObject(response.getEntity(String.class));		
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException(json2.getString("status_message"));
		}
		
		Collection<String> starringActors=new ArrayList<String>();
		JSONArray casting = json2.getJSONObject("casts").getJSONArray("cast");
		for(int i=0;i < casting.length();i++){
			JSONObject actor = casting.getJSONObject(i);
			starringActors.add(actor.getString("name"));
		}
		int reviewsCount = json2.getJSONObject("reviews").getJSONArray("results").length();
		
		return new Movie(json.getString("title"),json.getString("overview"),reviewsCount,json.getString("release_date"),starringActors);
	}
	
	private Movie convertJSONToRottenTomatoesMovie(JSONObject json){
		int movieId=json.getInt("id");
		
		ClientResponse response=null;
		try{
			WebResource webResource = client.resource(MovieResources.rottenTomatoesMovieDetails(movieId));
			response = webResource.accept("application/json").get(ClientResponse.class);
		}catch(RuntimeException e){
			throw new InvalidResourceException(e.getMessage());
		}
		
		JSONObject json2 = new JSONObject(response.getEntity(String.class));		
		if (response.getStatus() != 200) {			
		   throw new InvalidResourceException("The api.rottentomatoes returned error: "+json2.getString("error"));
		}

		 int reviewsCount = json2.getJSONArray("reviews").length();
			 
		 JSONArray casting=json.getJSONArray("abridged_cast");
		 List<String> starringActors=new ArrayList<String>();
		 for(int j=0;j<casting.length();j++){
			 starringActors.add(casting.getJSONObject(j).getString("name"));
		 }
		 return new Movie(json.getString("title"),json.getString("synopsis"),reviewsCount,String.valueOf(json.getInt("year")),starringActors);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public List<InvalidResourceException> getThrownExceptions() {
		return thrownExceptions;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clearThrownExceptions() {
		thrownExceptions.clear();
	}
	
}

