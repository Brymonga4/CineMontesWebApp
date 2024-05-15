<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page
	import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*"%>
	
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Todas las peliculas</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />

	</head>


	<body>
		

		<div id="site-content">

			<%@ include file="includes/header.jsp"%>

			<main class="main-content">
				<div class="container">
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<span>Movie Review</span>
						</div>

						<div class="filters">		
							<% if(request.getParameter("mensaje")!=null){  %>					
							<div align="center">
								<h3><%= request.getParameter("mensaje") %></h3>
							</div>
							<%}%>							
						</div>
		
					
						<div class="movie-list">
								<%
								List<Pelicula> listaP = null;
								listaP = (List <Pelicula>) config.getServletContext().getAttribute("listaPeliculas");
								%>
								
								<%for (Pelicula p : listaP){
								String titulo = p.getTitulo();	
								titulo=Recursos.limpiarString(titulo);							
								%>
								<div class="movie">
								<figure class="movie-poster"><img src="images/peliculas/<%=titulo%>.jpg" alt="#"></figure>
								<div class="movie-title"><a href="peli.jsp?id=<%=p.getIdPelicula()%>"><%=p.getTitulo()%></a></div>
								</div>
							
								<%};%>
									
						</div> <!-- .movie-list -->
				
					</div>
				</div> <!-- .container -->
			</main>

		<%@ include file="includes/footer.jsp"%>
		
		</div>
		
		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>			
		
		
		<!-- Default snippet for navigation -->
		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>

