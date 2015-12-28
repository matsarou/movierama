package movierama.movies.now_playing;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
/**
 * The Controller class. 
 * It is responsible for preparing a model Map with data and selecting a name for the view(jsp file).
 * @author Maria
 *
 */
@Controller
@RequestMapping("/movies")
public class MovieController {
	private MovieRepository moviesRepository;
	private  MovieService moviesService;
	
	/**
	 * The default constructor.
	 */
	MovieController(){
		moviesRepository=new MovieRepository();
		moviesService = new MovieServiceImpl();
	}
	

	/**
	 * Loads the now playing movies to the data Model Map. 
	 * @param model
	 * @return the name of the jsp file(view) that will display the data model
	 */
	@RequestMapping(value = "/now_playing", method = RequestMethod.GET)
	String nowPlayingMovies(ModelMap model) {
		model.addAttribute("title", "MovieRama");
         
        moviesService.clearThrownExceptions();
        
        moviesRepository.persist(moviesService.nowPlayingMovies());
    	model.addAttribute("repository",moviesRepository.retrieveMovies());
    	
    	exceptionHandling(model);
       
        return "movies";
    }
      
	/**
	 * Searches for a movie in the local repository or in the original data of the external APIs.
	 * @param movie
	 * @param model
	 * @return the name of the jsp file(view) that will display the results of the search
	 * @throws Exception
	 */
    @RequestMapping(value = "/search")
    String search(@RequestParam("movie") String movie, ModelMap model) throws Exception {
    	moviesService.clearThrownExceptions();
    	model.addAttribute("title", "MovieRama");
    	if(movie != null && movie!=""){
    		Collection<Movie> APImoviesFound=moviesService.search(movie);
    		moviesRepository.persist(APImoviesFound);
    		model.addAttribute("results",moviesRepository.searchForMovie(movie));
    		exceptionHandling(model);
    	}

    	return "search";
    }
        
    private void exceptionHandling(ModelMap model){
		List<InvalidResourceException> thrownExceptions=moviesService.getThrownExceptions();
        if(thrownExceptions.isEmpty()){
        	model.addAttribute("errorHappened",false);
		}else{
			StringBuilder message=new StringBuilder("");
			for(InvalidResourceException ex:thrownExceptions){
				message.append(ex.getMessage());
				message.append("\n");
			}
			model.addAttribute("errorHappened",true);
			model.addAttribute("errorMessage",message.toString());
		}
	}
}
