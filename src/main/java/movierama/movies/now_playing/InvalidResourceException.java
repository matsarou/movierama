package movierama.movies.now_playing;

/**
 * A Runtime exception for the cases that one or more of the external APIs are not responding.
 * @author Maria
 *
 */
public class InvalidResourceException extends RuntimeException{
	
	/**
	 * 
	 */
	InvalidResourceException(){
		super();
	}
	
	/**
	 * The message is displayed in the jsp page.
	 * @param message 
	 */
	InvalidResourceException(String message){
		super(message);
	}
	
}
