package movierama.movies.now_playing;

import java.util.Collection;
import org.springframework.stereotype.Component;

/**
 * Movie Entity. It is used by the rest services to persist and retrieve the data that are loaded from the external APIs.
 * A Movie has the attributes:
 * <ul>
 * <li>description(merge results from 2 APIs)</li>
 * <li>number of reviews(merge just the number of reviews)</li>
 * <li>year of production</li>
 * <li>starring actors</li>
 * </ul>
 * @author Maria
 *
 */
public class Movie {
	private String title;
	private String description;
	private int countReviews;
	private String productionYear;
	private Collection<String> starringActors;
	
	/**
	 * The default constructor.
	 */
	public Movie(){
		this(null,null,0,null,null);
	}
	
	/**
	 * Constructs a non-empty movie object.
	 * @param title
	 * @param description
	 * @param countReviews
	 * @param productionYear
	 * @param starringActors
	 */
	public Movie(String title,String description,int countReviews,String productionYear,Collection<String> starringActors){
		this.title=title;
		this.description=description;
		this.countReviews=countReviews;
		this.productionYear=productionYear;
		this.starringActors=starringActors;
	}
	
	/**
	 * Get the title of the Movie.
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Get the description of the Movie.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description of the Movie.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description= description;
	}
	
	/**
	 * Get the reviews(only the count) of the Movie.
	 * @return
	 */
	public int getCountReviews() {
		return countReviews;
	}
	
	/**
	 * Set the reviews(only the count) of the Movie.
	 * @param countReviews
	 */
	public void setCountReviews(int countReviews) {
		this.countReviews = countReviews;
	}
	
	/**
	 * Get the year of Production of the Movie.
	 * @return
	 */
	public String getProductionYear() {
		return productionYear;
	}
	
	/**
	 * Get the starringActors of the Movie.
	 * @return
	 */
	public Collection<String> getStarringActors() {
		return starringActors;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Movie){
			Movie other=(Movie)obj;
			return this.title.equals(other.getTitle());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return this.title.hashCode();
	}
}
