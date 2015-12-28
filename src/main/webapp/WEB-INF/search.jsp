<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/movies.css" />" />
<body>
	<h1>${title}</h1> 
    
	<form method="get" action="/movierama/movies/search">
	 	<div>
	    	<input type="text" id ="txt" name="movie" >
	       <button id="button-id" type="submit">Search</button>
	    </div>
	</form>
	
	<c:if test="${errorHappened == true}">
	 	<p><c:out value="${errorMessage}"/><p>
	</c:if>
	
	<c:if test="${results.size() > 0}">
		<table>   	                                                                                      
		  <c:forEach var="item" items="${results}" varStatus="movieLoopCount">
			<tr class="movie_box">
			<td>
				<h3>${item.title}</h3>
				<h5>${item.productionYear} - Starring: 
					<c:forEach var="actor" items="${item.starringActors}" varStatus="movieLoopCount" begin="0" end="2">
						${actor},
					 </c:forEach>
				</h5>
				<p class="movie_description">${item.description}</p>
				<p class="movie_reviews">${item.countReviews} Reviews</p>
			</td>
		  </c:forEach>
		</table>
	</c:if>

</body>
</html>