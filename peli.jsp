<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="javax.servlet.*,domain.*, recursos.*,java.util.*, servicios.*, util.*" %>
    
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
		}
		
		
		//ServicioInsertarButacasSalas sibs = new ServicioInsertarButacasSalas();
		//sibs.generarDistribucion();
		//sibs.insertarButacasenSalas();
		%>    
    
    
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
			
		<title><%=peli.getTitulo()%></title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<script src="https://kit.fontawesome.com/75b83eafd7.js" crossorigin="anonymous"></script>
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />
		
		<script type="text/javascript">
		
		function compararFecha(fecha1, fecha2){
			fecha1 = fecha1.getDate()+"-"+fecha1.getMonth();
			fecha2 = fecha2.getDate()+"-"+fecha2.getMonth();

			return fecha1 == fecha2;
		}
		
		function addMinutes(time, minsToAdd) {
			  function D(J){ return (J<10? '0':'') + J;};
			  var piece = time.split(':');
			  var mins = piece[0]*60 + +piece[1] + +minsToAdd;

			  return D(mins%(24*60)/60 | 0) + ':' + D(mins%60);  
			}

		function funcionesDeFecha(daysAdded){

			//Siempre tenemos la fecha de hoy en este element
			var fechaHoy = document.getElementById("fechaHoy");  var dateHoy = new Date();
			fechaHoy.value = dateHoy; 
			//Fecha que usaremos para jugar con las funciones y hoy
			var fechaRel = document.getElementById("fechaRel"); var dateRel;

			//Boton comprar, boton menos y select con options
			var botonC = document.getElementById("btnCompra");
			var botonLA = document.getElementById("removeDia");
			var dropdownFun = document.getElementById("funcionesPeli");
			
			//Peli y funcion
			var idPeli = document.getElementById("idPeli").value;			
			var idFun = document.getElementById('idFun');
			
			var jsonRel = null;
			
			//Guardamos la primera vez, la fecha de Hoy en la fechaRelativa
			if (fechaRel.value =="" || fechaRel.value == null){
				fechaRel.value = fechaHoy.value;
				dateRel = dateHoy;
			}
						
			if (daysAdded != 0)	{
			    dateRel = new Date (fechaRel.value); //Nueva Fecha con el valor de fechaRel		
				dateRel.setDate(dateRel.getDate()+daysAdded); //Añadimos o disminuimos los días a esta fecha que acabamos de crear
				jsonRel = dateRel.toJSON();//Pasa a json formato
				fechaRel.value = jsonRel;//Guardamos este valor en el elemento fechaRelativa
			}
			
			//Si ambas fechas son iguales Ó la dateRel es mayor que la de dateHoy, mostramos el botón de comprar y quitar dias			
			if(compararFecha(dateHoy, dateRel) || dateRel > dateHoy){
				botonC.style.display = "";
			}else{
				botonLA.style.display = "none";
				botonC.style.display = "none";			
			}			
			
			
			if (compararFecha(dateHoy, dateRel)){
				jsonRel = null;
				botonLA.style.display = "none";
			}
			
			if (dateRel > dateHoy)
				botonLA.style.display = "";
			
			//Borramos todas las options del select
	    	for (i = dropdownFun.options.length-1; i > 0; i--) {
	    		dropdownFun.options[i] = null;
	    		}	
			//console.log(jsonRel);
	    	//Si la fecha pasada es null, solo aparecerán las funciones disponibles después de la hora del usuario
			$.get('${pageContext.request.contextPath}/FuncionesPeliJSON', {pelicula : idPeli, fecha : jsonRel}, function(data){
				
			    $.each(data, function(index, element) {
			    	var d = element.fecha; var t = d.split(" "); var horaInicio = t[1]; var dia = t[0];
			    	horaInicio = horaInicio.substring(0, horaInicio.indexOf(":", horaInicio.indexOf(":")+1));
			    	var horaFinal = addMinutes(horaInicio, element.peli.duracion);
			    	var fechaFuncion = new Date(dia); 
			    	var strdia = fechaFuncion.getDate()+"/"+(fechaFuncion.getMonth()+1)+"/"+fechaFuncion.getFullYear();

			    	var textoFun = "Sesion de "+horaInicio +" a "+horaFinal+" en la Sala "+element.sala.idSala +" el "+ strdia;
			    	
			    	dropdownFun = document.getElementById("funcionesPeli");
			    	dropdownFun.options[dropdownFun.options.length] = new Option (textoFun, element.id);

			    });
					 
		    	if ($('#funcionesPeli').children().length == 1){
		    		dropdownFun.options[1] = new Option ("No hay funciones programadas disponibles el "+dateRel.getDate()+"/"+(dateRel.getUTCMonth()+1)+"/"+dateRel.getFullYear(),"");
		    		botonC.style.display = "none";
		    	}
	    	
				});
			
	    	if (dropdownFun.value == ""){
	    		botonC.style.display = "none";
	    	}
	    		 
	    	
		}
	
		function idFuncion(funcion){
			var idFun = document.getElementById('idFun');
			idFun.value = funcion;
		}	
		
		function showButton (){
			var dropdownFun = document.getElementById("funcionesPeli");
			var botonC = document.getElementById("btnCompra");
			
	    	if (dropdownFun.value == ""){
	    		botonC.style.display = "none";
	    	}else{
	    		botonC.style.display = "";
	    	}
	    	
		}
		
		function minOpinion(){
			var opinion = document.getElementById("opinion");
			if (opinion.value.length<3){
				return false;
			}
		}

	
		
		</script>		
		
		
	</head>


	<body onload="funcionesDeFecha(0)">
		

		<div id="site-content">
			<%@ include file="includes/header.jsp"%>
			<main class="main-content">
				<div class="container">
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<a href="listadoPeliculas.jsp">Todas Las Películas</a>
							
							<% user = (Usuario)sesion.getAttribute("usuario"); %>	
												
							<span><%=peli.getTitulo()%></span>
						</div>
								<%if (request.getAttribute("msgError")!= null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:red"> <%=request.getAttribute("msgError")%> </h4>
								</div>	
								<%}%>
								<%if (request.getParameter("mensaje")!=null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:green"> <%=request.getParameter("mensaje")%> </h4>
								</div>	
								<%}%>								
													
						<div class="content">						
							<div class="row">
								<div class="col-md-6">
									<figure class="movie-poster"><img src="images/peliculas/<%=Recursos.limpiarString(peli.getTitulo())%>.jpg" alt=""></figure>
									
								<figure class="movie-poster">																
								
									<iframe width="540" height="315" src="<%=Recursos.videoYTembed(peli.getTrailer())%>" frameborder="5" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>						  		
								
								</figure>
								
								</div>
								<div class="col-md-6">
									<h2 class="movie-title"><%=peli.getTitulo() %></h2>
									
								<%-- Crear Switch para poner edadesRecomendadas --%>
									<figure class="movie-poster"><img class=" float-right" src="<%=Recursos.edadRecomendada(peli.getEdad_rec())%>" alt="#"></figure>
									
									<div class="movie-summary">
										<p><%=peli.getSinopsis()%></p>
										
									</div>
									<ul class="movie-meta">
									
									<%-- Crear Servicio para recuperar valoracion de reviews --%>
											<%ServicioReviews sr = new ServicioReviews(); 
											  List <Review> listaR = null;
											  listaR = sr.todasReviewsdePeli(peli);
											  double valorM=sr.valoracionMediaDePelicula(peli);
											%>									
										<li><strong>Rating:</strong> 
										
											<div class="star-rating" title=""><span style="width:<%=Recursos.valoracionTantoPorCiento(valorM)%>"></span></div> <%=listaR.size()%> Voto(s)
											
										</li>
										<li><strong>Duración:</strong> <%=peli.getDuracion() %> minutos</li>
										<li><strong>Estreno:</strong> <%=Fecha.convertirAString(peli.getEstreno(), "dd/MM/YYYY") %></li>
										<li><strong>Géneros:</strong> <%=peli.getGenero() %></li>
									</ul>

									<ul class="starring">
										<li><strong>Directores:</strong> <%=peli.getDirectores() %></li>
										<li><strong>Guionistas:</strong> <%=peli.getGuionistas()%></li>
										<li><strong>Actores:</strong> <%=peli.getActores() %></li>
									</ul>
									<%if (user != null){ %>
									
									<input type="hidden" id="fechaRel" name="fechaRel" value="">
									<input type="hidden"  id="fechaHoy" name="fechaHoy" value="" >
									<input type="hidden"  id="idPeli" name="idPeli" value="<%=id%>">
									
									<button class="btn" id="removeDia"><i class="fas fa-arrow-left" aria-hidden="true" onclick="funcionesDeFecha(-1)" ></i></button>	
									<select class="genero slct" name="funcionesPeli" id="funcionesPeli" onchange="idFuncion(this.value);showButton()" required>
										<option value="" selected>Funciones</option>									
									</select>
									<button class="btn" id="addDia"><i class="fas fa-arrow-right" aria-hidden="true" onclick="funcionesDeFecha(+1)" ></i></button>
																		
									<form method="get" action="butacasFuncion.jsp" id="butacasFun" >																									
										<input type="hidden"  id="idFun" name="idFun" value="">										
										<br>
										<input type="submit" value="Escoger Asientos" id="btnCompra" style="display: none;margin-bottom: 20px;">
										<br>
									</form>
									
									<%}else{ %>	
									<ul class="starring">
									<li><strong>¡Inicia Sesión para poder comprar entradas!</strong></li>
									</ul>
									<%} %>		
								</div>							
								
								<%if (user != null && user.isAdmin()){ %>
								<form action="BorrarPelicula" onsubmit="return confirm('¿Estas seguro que quieres borrar esta pelicula?');">
									<input type="hidden"  name="id" value="<%=id%>">
									<input type="hidden"  name="titulo" value="<%=peli.getTitulo()%>">
									<input type="submit" value="Borrar Pelicula" />
								</form>	
								<br>
								<form action="editPeliForm.jsp" >
									<input type="hidden"  name="id" value="<%=id%>">
									<input type="submit" value="Editar Pelicula" />
								</form>	
								
								<%} %>							
								
							</div> <!-- .row -->
							
							</br> </br>
							<%-- Crear Servicio para recuperar review contenido --%>
							
							<div class="row">
							
								<div class="col-md-12">
								<% if (listaR.size()>0){%>	
								<h2 class="movie-title"><u>Opiniones de los Usuarios:</u></h2>
								<%}else{%>
								<h2 class="movie-title"><u>Nadie ha opinado sobre esta película todavía, ¡Sé el primero!</u></h2>
								<%}%>	
										
									<%for (Review r : listaR){%>
									<div class="row" style="padding-top: 25px;border-radius: 15px;border-width:3px;border-style:solid;border-color:#ffaa3c;"> 	<!-- div prueba -->																										
									<div class="feature col-md-10">
									<h3 class="feature-title" ><%=r.getTitulo()%></h3>
									<small class="feature-subtitle" ><%=r.getUser().getAlias()%> piensa: <div class="star-rating" title=""><span style="width:<%=Recursos.valoracionTantoPorCiento((double)r.getValoracion())%>"></span></div></small>			
									<p style="font-style: italic;margin-top: -22px;">
									<%=r.getOpinion()%><br> Publicado en : <%=Fecha.convertirAString(r.getFecha() , "dd-MM-YYYY")%></p>
										
										<% if (user != null && r.getUser().getAlias().compareTo(user.getAlias())== 0){%>
										<form action="BorrarReview" >																		
											<input type="hidden"  name="reviewId" value="<%=r.getId()%>">
											<input type="submit" value="Borrar Review" />
										</form>
										<%}%>								
									</div>
									<div class="col-md-2" >
									<div class="team">
									<figure class="team-image" style="margin: 0 auto -5px; width: 80px;height: 80px;"><img src="images/avatares/<%=r.getUser().getAlias()%>-avatar.jpg" alt="Avatar" id="uploadPreviewMain" onerror="this.onerror=null;this.src='images/avatares/generic_avatar_cineMontesD.png';">
									</figure>
									</div>
									</div>
									</div> <!-- div prueba -->
									<br> 
									<%}%>
									
								</div>
						</div>
											
											<% if (user != null && !sr.usuarioEscribioReviewenPeli(user, peli) && sr.haVistoElUsuarioEstaPeli(user, peli)){%>																	
											<form action="EscribirReview" onsubmit="return minOpinion()">
												<div class="contact-form">
													<label for="usuario">Usuario</label>
													<input type="text" class="message" placeholder=""  id="usuario" name="usuario" value="<%=user.getAlias()%>" readonly>
													<label for="titulo">Titulo Review</label>																				
													<input type="text" class="titulo" placeholder="Titulo de la review..." id="titulo" name=titulo 
														pattern=".{3,30}" required title="Titulo entre 3 y 30 caracteres">
													<label for="opinion">Opinion</label> 
													<textarea class="message" placeholder="Crítica..." name=opinion id="opinion" maxlength=2000 required ></textarea>
													<label for="valoracion">Valoracion</label> 
													<select class="genero" name="valoracion" id="valoracion" onchange="" required>
														<option value="" disabled selected>Valoracion</option>
														<option value="1" >Mala</option>										
														<option value="2" >Meh</option>		
														<option value="3" >Esta OK</option>
														<option value="4" >Buena</option>
														<option value="5" >Obra Maestra</option>									
													</select>	
												
													<input type="hidden"  name="id" value="<%=id%>">
													<input type="submit" value="Escribir Review" />
												</div>
												</form>		
											<%}%>																			
																	
														
							
							
							<!-- <img src="${pageContext.request.contextPath}/images/peliculas/<%=Recursos.limpiarString(peli.getTitulo())%>.jpg" id="uploadPreview";" /> -->
							
						
					</div>
				</div> <!-- .container -->
				</div>
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