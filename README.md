# README #

MovieRama is a small web application. It uses two APIs: 
1)api.themoviedb.org and 2)api.rottentomatoes.com
It retrieves data and displays them.
It also provides a basic search mechanism


### How do I get set up and deploy? ###
In a command prompt:
	a) cd my_folder
	b) git clone https://matsarou@bitbucket.org/matsarou/movierama.git 
	c) mvn package
2) copy the movierama.war, which you will find inside the "my_folder\movierama\target"
3) paste in the tomcat folder \webapps
4) start the tomcat
5) In a browser hit the url: http://localhost:8080/movierama/movies/now_playing

* Dependencies
json libs
jersey client and core libs
spring core and web libs
jstl
org.apache.commons.commons-lang3 

* How to run tests
Currently  there are no tests. To be added.