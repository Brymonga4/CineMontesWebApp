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
		
		<title>Crear Funcion</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />

		
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>		
		<script type="text/javascript">
		
	    if(!Modernizr.inputtypes['datetime-local']) {
	        $('input[type=datetime-local]').datetimepicker();
	    }
		
		
	    function addMinutes(time, minsToAdd) {
	    	  function D(J){ return (J<10? '0':'') + J;};
	    	  var piece = time.split(':');
	    	  var mins = piece[0]*60 + +piece[1] + +minsToAdd;

	    	  return D(mins%(24*60)/60 | 0) + ':' + D(mins%60);  
	    	} 		
		
	    function recargarAlCambiarFecha(){
	    	var peli = document.getElementById("pelicula");
   	
	    	if (peli.value!= null && peli.value!=""){
	    		funcionesPeli(peli);
	    	}
	    	
	    }
	    
		
	    function funcionesPeli (peli){
	    	var peliculaid = peli.value;
	    	var date = document.getElementById('horaFun').value;
	    	
	    	var text = document.createElement("p"); text.innerHTML = "borra";	    	
	    	
	    	const container = document.getElementById('container');
	    		container.innerHTML = '';
	    	
	    	if (date == "" || date == null)	{
	    		var hoy = new Date();
	    		var json = hoy.toJSON();
	    		date = json;
	    	}
	    	
	    	$.get('${pageContext.request.contextPath}/FuncionesPeliJSON', {pelicula : peliculaid, fecha : date}, function(data){
	    		
	    	    $.each(data, function(index, element) {
	    	    	var d = element.fecha; var t = d.split(" "); var horaInicio = t[1]; var dia = t[0];
	    	    	horaInicio = horaInicio.substring(0, horaInicio.indexOf(":", horaInicio.indexOf(":")+1));
	    	    	var horaFinal = addMinutes(horaInicio, element.peli.duracion);
	    	    	
	    	    	var divP = document.createElement("div"); 
	    	    	divP.innerHTML = "El "+ dia +" de "+horaInicio +" a "+horaFinal+" en la Sala "+element.sala.idSala;
	    	    	
	    	    	var inputazo = document.createElement("input");
	    	    	inputazo.type = "button";
	    	    	inputazo.value = "El "+ dia +" de "+horaInicio +" a "+horaFinal+" en la Sala "+element.sala.idSala;
	    	    	inputazo.name = element.id;
	    	    	inputazo.className = "button";
	    	    	inputazo.style.fontSize = "18px";
	    	    	inputazo.style.width = "100%";
	    	    	inputazo.style.border = "thin solid #ff7d3d";
	    	    	inputazo.onclick = function(){borrarFuncion(element.id, horaInicio, horaFinal, peli)};
	    	    	
	    	    	
	    	        $('#container').append(inputazo);
	    	    });
	    			    
	    			if ($('#container').children().length == 0){
	    				container.innerHTML = "<span style='font-size:20px'>No hay funciones ese dia</span>";
	    			}
	    		});	
	    	

	    }
	    
	    
	    
	    
	    function borrarFuncion(idFuncion, horaInicio, horaFinal, peli){

	    	if (confirm("¿Estas seguro que quieres borrar la funcion de las "+horaInicio+" - "+horaFinal+"?")){	
		    	$.get('${pageContext.request.contextPath}/BorrarFuncion', {id : idFuncion}, function(data){
		    		funcionesPeli(peli);
	    		});	
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
							<span>Crear Función</span>
						</div>

						<h2 style="display: inline">Nueva Función</h2>
						<h2 style="display: inline;text-align:right;float:right" >Funciones existentes <small>(Haga Click para borrar funcion)</small></h2>	
						
						<div class="filters">		
							<% if(request.getParameter("mensaje")!=null){  %>					
							<div align="center">
								<h3><%= request.getParameter("mensaje") %></h3>
							</div>
							<%}%>							
						</div>						


							<div class="row">
							
								<div class="col-md-6">
						<form action=CrearNuevaFuncion method=post>							
									<div class="contact-form">
																												
										<select class="genero" name="pelicula" id="pelicula" onchange="funcionesPeli(this)" required>
										
											<option value="" disabled selected>Película</option>
											<%
											List<Pelicula> listaP = null;
											listaP = (List <Pelicula>) config.getServletContext().getAttribute("listaPeliculas");
											
											%>
											
											<%for (Pelicula p : listaP){
						
											%>
											<option value='<%=p.getIdPelicula()%>'><%=p.getTitulo()%> - Duracion: <%=p.getDuracion()%> min</option>
		
											<%}	%>										
											
										</select>
										
										
										<select class="genero" name="salas" id="salas" required>
										
											<option value="" disabled selected>Salas</option>
											<%
											ServicioInsertarButacasSalas sibs = new ServicioInsertarButacasSalas();
											List <Sala> listaSalas =null;
											listaSalas = sibs.recuperarTodasSalas();
											
											%>											
											<%
											if (listaSalas.size()>0)
												for (Sala s : listaSalas){					
											%>
												<option value="<%=s.getIdSala()%>"><%=s.getNombre()%> <%=s.getIdSala()%> </option>
		
											<%}	%>												
																							
										</select>
										
										<input type="text" class="fecha" placeholder="Hora de la Función..." name="horaFun" id="horaFun" oninput="recargarAlCambiarFecha()" onfocus="(this.type='datetime-local')" required>											
																				
										<select class="genero" name="audio" id="audio" onchange="" required>
											<option value="" disabled selected>Audio</option>
											<option value="Español" >Doblado a Español</option>										
											<option value="Original" >Original</option>										
										</select>							
																				
										<select class="genero" name="precio" id="precio" onchange="" required>
											<option value="" disabled selected>Precio</option>
											<option value="9.19" >Normal</option>										
											<option value="12.5" >Festivo</option>
											<option value="5.65" >Dia del Espectador</option>										
										</select>	
									
									
									<input type="submit" value="Añadir Función">	
									</div>
									</form>							
								</div>							
							
													
								<div class="col-md-6" id="container">
								
								</div>
																											
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
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
	</body>

</html>