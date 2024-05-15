<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page
	import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, exceptions.*"%>    
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">		
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Subir Película</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">

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
					<form action=SubirNuevaPelicula method=post enctype=multipart/form-data onsubmit="return minSipnopsis()">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<span>Nueva Película</span>
						</div>

						<h2>Nueva película</h2>
						


							<div class="row">
							
								<div class="col-md-4">
							
									<div class="contact-form">
									
									<label for="titulo">Título</label>
										<input type="text" class="titulo" placeholder="Titulo..." name=titulo id="titulo" 
										pattern=".{3,50}" required title="Titulo entre 3 y 50 caracteres"> 
										 
										<label for="tituloOrig">Titulo Original </label>
										<input type="text" class="titulo" placeholder="Titulo Original..." id="tituloOrig" name=titulo_Orig 
										pattern=".{3,50}" required title="Titulo Original entre 3 y 50 caracteres"> 
										
										<label for="fecha_estreno">Fecha Estreno </label>
										<input type="text" class="fecha" placeholder="Fecha Estreno..." name=fecha_Estreno  id="fecha_estreno" onfocus="(this.type='date')" required>
										
										<label for=pais>Pais </label>										
										<select class="genero" name="pais" required>										
											<option value="" disabled selected>País</option>
											<% ServicioGenerosYPaises sgyp = new ServicioGenerosYPaises();
												List<String> listaPais = null;
												listaPais = sgyp.todosPaises();
											%>
											
											<%
											if (listaPais.size()>0)
												for (String pais : listaPais){					
											%>
												<option value="<%=pais%>"><%=pais%></option>
		
											<%}	%>										
											

										</select>
										
										<label for="generos">Generos </label>
										<input type="text" class="message" placeholder="Género..."  id="generos" name="generos" value="" readonly required>
										
										<select class="genero" name="generosList" id="generosList" onchange="addGenre(this.value)">
										
											<option value="" disabled selected>Género</option>
											<%
											List <String> listaGeneros =null;
											listaGeneros = sgyp.todosGeneros();
											%>											
											<%
											if (listaGeneros.size()>0)
												for (String genero : listaGeneros){					
											%>
												<option value="<%=genero%>"><%=genero%></option>
		
											<%}	%>												
																							
										</select>
											
										<label for="edadRec">Edad Recomendada </label>											
										<select class="genero" name="edadRec" id="edadRec" onchange="" required>
											<option value="" disabled selected>Edad Recomendada</option>
											<option value="TP" >Todo los Públicos</option>										
											<option value="7" >Mayor de 7</option>		
											<option value="12" >Mayor de 12</option>
											<option value="16" >Mayor de 16</option>
											<option value="18" >Mayor de 18</option>
											<option value="X" >Golfa</option>
											<option value="PC" >Pendiende de Calificación</option>										
										</select>	
																
									<label for="trailer">Trailer Youtube </label>	
									<input type="text" class="message" placeholder="Trailer..." id="trailer" name=trailerYT required> 
									
									</div>
						
								</div>

								<div class="col-md-4">

									<div class="contact-form">
									<label for="actores">Actores </label>	
										<input type="text" class="message" placeholder="Actores..." id="actores" name=actores
										pattern=".{3,500}" required title="Actores separados por coma entre 3 y 500 caracteres"> 
									<label for="directores">Directores </label>	
										<input type="text" class="message" placeholder="Directores..." id="directores" name=directores
										pattern=".{8,100}" required title="Directores separados por coma entre 3 y 100 caracteres">
									<label for="guionistas">Guionistas </label>	
										<input type="text" class="message" placeholder="Guionistas..." id="guionistas" name=guionistas
										pattern=".{8,500}" required title="Guionistas separados por coma entre 8 y 500 caracteres">  
									<label for="productores">Productores </label>	
										<input type="text" class="message" placeholder="Productores..." id="productores" name=productores
										pattern=".{3,500}" required title="Productores separados por coma entre 3 y 500 caracteres"> 
									<label for="duracion">Duración </label>	
										<input type="number" class="message" placeholder="Duración en minutos..." id="duracion" min=1 name=duracion 
										required title="Duracion debe ser más de 0">
									<label for="sipnosis">Sipnopsis </label>	 
										<textarea class="message" placeholder="Sipnopsis" id="sipnopsis" name=sipnopsis maxlength=10000 required ></textarea>
									 	
									 	
									 	
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
									  <input class="form-check-input" type="checkbox" value="soporte_3d" id="soporte3D" name=soporte3D>
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
									  <input class="form-check-input" type="checkbox" value="version_original" id="versionOrig" name=versionOrig>
									  <label class="form-check-label" for="versionOrig">
									    Version Original
									  </label>
									</div>	
																										
									<br>								
								
								<img id="uploadPreviewBanner" style="width: 200px; height: 280px;"/>
								  <label for="uploadImageBanner">
								  Banner 
								    <img src="images/upload-local-button-icon.png" width="20%" height="auto" />
								  </label>
								
								  <input type="file" id="uploadImageBanner" onchange="PreviewImageBanner();" name="fotoBanner" required/>
								  
								</div>
								</div>									
																							
								</div>		<!-- Row -->	
								
								<div class="row">
								<div class="col-md-6">
								<figure class="movie-poster">
									<img id="uploadPreviewMain" style="width: 720px; height: 360px;" > 								
								<label for="uploadImageMain">
								  Imagen Principal 
								    <img src="images/upload-local-button-icon.png" width="20%" height="auto" />
								</label>
								<input type="file" id="uploadImageMain" onchange="PreviewImageMain();" name="fotoMain" required />
								</figure>								
								

								</div>
								</div>
								<input type="submit" value="Añadir Película">	
								
							</form>									
							</div>

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