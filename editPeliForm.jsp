<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page
	import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, exceptions.*, util.*"%>    
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<% 		
		ServletContext sc =config.getServletContext();
		ServicioPeliculas sp = new ServicioPeliculas();
		String id = request.getParameter("id");
		Pelicula peli = null;
		
		if (id!=null){
		    peli =sp.peliculaContexto(Integer.parseInt(id), (List <Pelicula>)(sc.getAttribute("listaPeliculas")));
		    if (peli==null){
		    	response.sendRedirect("listadoPeliculas.jsp?mensaje=Esa pelicula no existe.");
		    	return;
		    }
		}else{	
			response.sendRedirect("listadoPeliculas.jsp?mensaje=Esa pelicula no existe.");
			return;
		}	
		
		%>
		
		
		<title>Editar Película</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />
		
		<script type="text/javascript" src="js/cines.js"></script>
		<style type="text/css">
		.image-upload>input, .movie-poster>input {
		  display: none;
		}	
		</style>
		
		<script type="text/javascript">
		function minSipnopsis(){
			var sipnopsis = document.getElementById("sipnopsis");
			if (sipnopsis.value.length<10){
				return false;
			}
		}
		</script>	
		
	</head>


	<body>
		

		<div id="site-content">
			<!-- include del header -->
			<%@ include file="includes/header.jsp"%>
			
			<main class="main-content">
				<div class="container">
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<span>Editar Película</span>
						</div>

						<h2>Editar película</h2>
						
<form action=ModificarPelicula method=post enctype=multipart/form-data onsubmit="return minSipnopsis()">

							<div class="row">
							
								<div class="col-md-4">
							
									<div class="contact-form">
										
										<input type="hidden"  name="id" value="<%=id%>">
										<label for="titulo">Titulo </label>
										<input type="text" class="titulo" name=titulo id="titulo" value='<%=peli.getTitulo()%>' 
										pattern=".{3,50}" required title="Titulo entre 3 y 50 caracteres">  
										<label for="tituloOrig">Titulo Original </label>
										<input type="text" class="titulo"  name=titulo_Orig id="tituloOrig" value='<%=peli.getTitulo_orig()%>' 
										pattern=".{3,50}" required title="Titulo Original entre 3 y 50 caracteres"> 
										<label for="fecha_estreno">Fecha Estreno </label>
										<input type="text" class="fecha" name=fecha_Estreno  id="fecha_estreno" value='<%=peli.getEstreno()%>' onfocus="(this.type='date')" required>
										
										<label for=pais>Pais </label>									
										<select class="genero" name="pais" id="pais" required>
										
											<option value="" disabled>País</option>
											<% ServicioGenerosYPaises sgyp = new ServicioGenerosYPaises();
												List<String> listaPais = null;
												listaPais = sgyp.todosPaises();
											%>
											
											<%
											if (listaPais.size()>0)
												for (String pais : listaPais){
													if (pais.equals(peli.getPais().getNombre())){
											%>
												<option value="<%=pais%>" selected><%=pais%></option>		
											<%}else{ %>
												<option value="<%=pais%>" ><%=pais%></option>	
												 <%} 
											    }%>																					

										</select>
										
										<label for="generos">Generos </label>
										<input type="text" class="message" placeholder="Género..."  id="generos" name="generos" value="<%=peli.getGenero()%>" readonly required>
										<select class="genero" name="generosList" id="generosList" onchange="addGenre(this.value)">
										
											<option value="" disabled selected>Género</option>
											<%
											List <String> listaGeneros =null;
											listaGeneros = sgyp.todosGeneros();
											%>											
											<%
											if (listaGeneros.size()>0)
												for (String genero : listaGeneros){
													if (genero.equals(peli.getGenero())){
														%>  <option value="<%=genero%>" selected><%=genero%></option>		
												  <%}else{ %>
															<option value="<%=genero%>"><%=genero%></option>	
													  <%} 
												}%>										
																							
										</select>
										
										<label for="edadRec">Edad Recomendada </label>										
										<select class="genero" name="edadRec" id="edadRec" onchange="" required>
											<option value="" disabled selected>Edad Recomendada</option>
											
											<% if (peli.getEdad_rec().equals("TP")) {%>
												<option value="TP" selected>Todo los Públicos</option>
											<% }else {%>
												<option value="TP" >Todo los Públicos</option>
												<%} %>
												
											<% if (peli.getEdad_rec().equals("7")) {%>
												<option value="7" selected>Mayor de 7</option>
											<% }else {%>
												<option value="7" >Mayor de 7</option>
												<%} %>
											<% if (peli.getEdad_rec().equals("12")){ %>
												<option value="12" selected>Mayor de 12</option>
											<% }else {%>
												<option value="12" >Mayor de 12</option>
												<%} %>
											<% if (peli.getEdad_rec().equals("16")){ %>
												<option value="16" selected>Mayor de 16</option>
											<% }else {%>
												<option value="16" >Mayor de 16</option>
                                                <%} %>
											<% if (peli.getEdad_rec().equals("18")){ %>
												<option value="18" selected>Mayor de 18</option>
											<% }else {%>
												<option value="18" >Mayor de 18</option>
												<%} %>
											<% if (peli.getEdad_rec().equals("X")){ %>
												<option value="X" selected>Golfa</option>
											<% }else {%>
												<option value="X" >Golfa</option>
											  <%} %>
											<% if (peli.getEdad_rec().equals("PC")){ %>
												<option value="PC" selected>Pendiende de Calificación</option>
											<% }else {%>
												<option value="PC" >Pendiende de Calificación</option>
												<%} %>																																																																																																							
										</select>
																	
									<label for="trailer">Trailer Youtube </label>	
									<input type="text" class="message" name=trailerYT id="trailer" value='<%=peli.getTrailer()%>' required> 
									
									</div>
						
								</div>

								<div class="col-md-4">

									<div class="contact-form">
									<label for="actores">Actores </label>	
										<input type="text" class="message" value='<%=peli.getActores()%>' id="actores" name=actores
										pattern=".{3,500}" required title="Actores separados por coma entre 3 y 500 caracteres"> 
										
									<label for="directores">Directores </label>	
										<input type="text" class="message" value='<%=peli.getDirectores()%>' id="directores" name=directores
										pattern=".{8,100}" required title="Directores separados por coma entre 3 y 100 caracteres">
										
									<label for="guionistas">Guionistas </label>		
										<input type="text" class="message" value='<%=peli.getGuionistas()%>' id="guionistas" name=guionistas
										pattern=".{8,500}" required title="Guionistas separados por coma entre 8 y 500 caracteres">
										 
									<label for="productores">Productores </label>		 
										<input type="text" class="message" value='<%=peli.getProductores()%>' id="productores" name=productores
										pattern=".{3,500}" required title="Productores separados por coma entre 3 y 500 caracteres"> 
										
									<label for="duracion">Duración </label>		
										<input type="number" class="message" value='<%=peli.getDuracion()%>' id="duracion" min=1 name=duracion 
										required title="Duracion debe ser más de 0">
										
									<label for="sipnosis">Sipnopsis </label>		 
										<textarea class="message" placeholder="Sipnopsis" name=sipnopsis maxlength=10000 id="sipnopsis" ><%=peli.getSinopsis()%></textarea>									 										 	
									</div>					
									
								</div>								
													
								<div class="col-md-4">
									<div class="image-upload text-center">
										<div class="form-check">
										  <input class="form-check-input" type="checkbox" value="soporte_digital" id="soporteDigital" name=soporteDigital checked disabled >
										  <label class="form-check-label" for="soporteDigital">
										    Soporte Digital
										  </label>
										</div>
										
										<div class="form-check">
										<% if (peli.isSp_3d()){ %>
										  <input class="form-check-input" type="checkbox" value="soporte_3d" id="soporte3D" name=soporte3D checked>
										 <% }else{ %>
										 <input class="form-check-input" type="checkbox" value="soporte_3d" id="soporte3D" name=soporte3D>
										 <%} %>
										  <label class="form-check-label" for="soporte3D">
										    Soporte 3D
										  </label>
										</div>								
	
										<br>
											
										<div class="form-check">
										  <input class="form-check-input" type="checkbox" value="version_esp" id="versionEsp" name=versionEsp checked disabled >
										  <label class="form-check-label" for="versionEsp">
										    Version en Español
										  </label>
										</div>
										
										<div class="form-check">
										<% if (peli.isVo()){ %>
										  <input class="form-check-input" type="checkbox" value="version_original" id="versionOrig" name=versionOrig checked>
										<% }else{ %>
										  <input class="form-check-input" type="checkbox" value="version_original" id="versionOrig" name=versionOrig>
										 <%} %>
										  <label class="form-check-label" for="versionOrig">
										    Version Original
										  </label>
										</div>	
																											
										<br>
										
								<img id="uploadPreviewBanner" style="width: 200px; height: 280px;"  src="images/peliculas/estrenos_<%=Recursos.limpiarString(peli.getTitulo())%>.jpg"/>
								  <label for="uploadImageBanner">
								  Banner 
								    <img src="images/upload-local-button-icon.png" width="20%" height="auto" />
								  </label>
								
								  <input type="file" id="uploadImageBanner" onchange="PreviewImageBanner();" name="fotoBanner" />								
																								
									</div>
								</div>									
							</div>	<!-- Row -->
								
								<div class="row">
								<div class="col-md-6">
								<figure class="movie-poster">
									<img id="uploadPreviewMain"  src="images/peliculas/<%=Recursos.limpiarString(peli.getTitulo())%>.jpg"> 								
								<label for="uploadImageMain">
								  Imagen Principal 
								    <img src="images/upload-local-button-icon.png" width="20%" height="auto" />
								</label>
								<input type="file" id="uploadImageMain" onchange="PreviewImageMain();" name="fotoMain" />
								</figure>								
								

								</div>
								</div>
								<input type="submit" value="Editar Película">									
							
</form>	
					</div>
				</div> <!-- .container -->
				
			</main>
			
			<!-- include footer -->		
			<%@ include file="includes/footer.jsp"%>
			
		</div>
		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>			
		
		<!-- Default snippet for navigation -->
		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false&amp;language=en"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>