# README #

MovieRama is a small web application. It uses two APIs: 
1. api.themoviedb.org and 
2. api.rottentomatoes.com
It retrieves data and displays them.
It also provides a basic search mechanism


### How do I get set up and deploy? ###
1. In a command prompt:
	1. cd my_folder

	2. git clone https://matsarou@bitbucket.org/matsarou/movierama.git 

	3. mvn package

2) copy the movierama.war, which you will find inside the "my_folder\movierama\target"

3) paste in the tomcat folder \webapps

4) start the tomcat

5) In a browser hit the url: http://localhost:8080/movierama/movies/now_playing



### Dependencies ###
1. json libs
2. jersey client and core libs
3. spring core and web libs
4. jstl
5. org.apache.commons.commons-lang3 


***** How to run tests
Currently  there are no tests. To be added.

### FEW DETAILS ###
I chose the Spring web MVC framework as architecture stack.

The the **interface MovieService.java** includes the two methods of my Rest API.

1. http://localhost:8080/movierama/movies/now_playing
Load the now playing movies from the two external APIs 

2. http://localhost:8080/movierama/movies/search?movie={movie_title}
Search a movie in the local repository or the external APIs. 

The **class MovieResources.java** contains all Rest API paths that i used for the calls to the external APIs.

The **class MovieController.java** is the Controller of my mvc design.  
For the View two jsp files exist:
1. movies.jsp 
2. search.jsp 