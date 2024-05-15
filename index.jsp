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
		
		<title>Bienvenido</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Slider -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.2/tiny-slider.css">
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />
		
		
		
					
	</head>
	

	<body>
		

		<div id="site-content">
		
			<!-- include del header -->
	<%@ include file="includes/header.jsp"%>
								<%if (request.getAttribute("msgError")!= null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:red"><%=request.getAttribute("msgError") %></h4>
								</div>	
								<%}%>
								<%if (request.getAttribute("msg")!= null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:green"><%=request.getAttribute("msg") %></h4>
								</div>	
								<%}%>									
										
			<main class="main-content">
				<div class="container">
					<div class="page">
						<!-- Estrenos -->
						<div class="row">
							<div class="col-md-12">
				
								<div class="feature">
									<h3 class="feature-title" align="center">Estrenos</h3>
								</div>
								<!-- Servicio pelis fecha estreno =< (semana actual + 7 dias) -->
								<div class="slider">
									<ul class="slides">
																		
									<%
									ServicioPeliculas sp = new ServicioPeliculas();
									List<Pelicula> listaP = null;
									listaP = (List <Pelicula>) config.getServletContext().getAttribute("listaPeliculas");
									//Películas que se estrenaron hace como mucho 2 semanas
									listaP = sp.peliculasEstreno(listaP);
									%>
									
									<%
									if (listaP.size()>0){
										for (Pelicula p : listaP){
										String titulo = p.getTitulo();	
										titulo=Recursos.limpiarString(titulo);							
									%>
										<li><a href="peli.jsp?id=<%=p.getIdPelicula()%>"><img src="images/peliculas/<%=titulo%>.jpg" alt="Slide <%=titulo%>"></a></li>

									<%}}else{%>
										<li><a href="#"><img src="images/NoEstrenosDisponibles.jpg" alt="Slide NoInfo"></a></li>
										<%} %>
									</ul>
								</div>
							</div>

						</div> 
						</div>
						<!-- .row -->

						<!-- Peliculas -->
						
					<div class="feature">
						<h3 class="feature-title" align="center">Cartelera</h3>
					</div>
					
  					<div class="slide-wrapper ">

  						<%List<Pelicula> listaPSlider = null;
  						//Todas las películas, recuperadas del contexto
  						//listaPSlider=(List <Pelicula>) config.getServletContext().getAttribute("listaPeliculas");
  						
  						//Cartelera de estas 2 semanas siguientes (pelis que tienen funciones disponibles desde hoy hasta 2 semanas después)
  						listaPSlider = sp.carteleraSemanas();
  						%>
						<%
						if (listaPSlider!=null&&listaPSlider.size()>0){
							for (Pelicula ps : listaPSlider){
								String tituloS = ps.getTitulo();	
								tituloS=Recursos.limpiarString(tituloS);							
						%>
					    <div>
    						<div class="slide-item"><a href="peli.jsp?id=<%=ps.getIdPelicula()%>"><img src="images/peliculas/estrenos_<%=tituloS%>.jpg" alt="Banner <%=tituloS%>"  style="width: 200px; height: 280px;" onerror="this.onerror=null;this.src='images/peliculas/imagen-no-disponible.jpg';"></a></div>
    					</div>
						<%	}
						}%>	
  					</div>
					
						
					<% if (listaPSlider==null||listaPSlider.size()<=0){%>	
				  		<div class="slider">
							<ul class="slides">
								<li><a href="#"><img src="images/NoPeliculasDisponibles.jpg" alt="Slide NoInfo"></a></li>
							</ul>
						</div>	
				  		<%}%>	
											
					
					<!-- .row -->
						<!-- Fin peliculas -->
<br><br>
						<!-- Inicio schedule -->
						
						<%
						
						ServicioFunciones sf = new ServicioFunciones();
						List<Funcion> listaFuncionesHoy = null; List<Funcion> listaFuncionesHoyMasUno = null;
						listaFuncionesHoy = sf.recuperarTodasFuncionesDisponiblesHoy();
						listaFuncionesHoyMasUno = sf.recuperarTodasFuncionesDisponiblesHoyMasUno();
						final int maxFun = 4;
						%>
						
						<div class="row">
						
						
							<div class="col-md-6">
								<h2 class="section-title">Funciones de Hoy</h2>
								<ul class="movie-schedule">
								<%if(listaFuncionesHoy!=null && listaFuncionesHoy.size()>0){
									for (int i = 0; i < listaFuncionesHoy.size()&& i<=maxFun ; i++) {
							    		Date horaF = new Date(listaFuncionesHoy.get(i).getFecha().getTime());
							    		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
							    		String sesionHora = sdf.format(horaF); 	
								%>
									<li>
										<div class="date"><%=sesionHora%></div>
										<h2 class="entry-title"><a href="butacasFuncion.jsp?idFun=<%=listaFuncionesHoy.get(i).getId()%>">
										<%=listaFuncionesHoy.get(i).getPeli().getTitulo()%></a></h2>
									</li>
									<%}
								 }else{%>
									<li>
										<h2 class="entry-title"><a href="#">No hay funciones disponibles este día</a></h2>
									</li>								 
								 <%} %>
								</ul> 
							</div>
							
							<div class="col-md-6">
								<h2 class="section-title">Funciones de Mañana</h2>
								<ul class="movie-schedule">
								<%if(listaFuncionesHoyMasUno!=null && listaFuncionesHoyMasUno.size()>0){
									for (int i = 0; i < listaFuncionesHoyMasUno.size()&& i<=maxFun ; i++) {
							    		Date horaF = new Date(listaFuncionesHoyMasUno.get(i).getFecha().getTime());
							    		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
							    		String sesionHora = sdf.format(horaF); 	
								%>
									<li>
										<div class="date"><%=sesionHora%></div>
										<h2 class="entry-title"><a href="butacasFuncion.jsp?idFun=<%=listaFuncionesHoyMasUno.get(i).getId()%>">
										<%=listaFuncionesHoyMasUno.get(i).getPeli().getTitulo()%></a></h2>
									</li>
									<%}
								 }else{%>
									<li>
										<h2 class="entry-title"><a href="#">No hay funciones disponibles este día</a></h2>
									</li>								 
								 <%} %>
								</ul> 
							</div>
						</div>
						<!-- Fin schedule -->

					
				</div> <!-- .container -->
			</main>
			
	<!-- include footer -->			
	<%@ include file="includes/footer.jsp"%>
			
		</div>
		
		<!-- Scripts tinyslider -->		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.2/min/tiny-slider.js"></script>
		<script>
		var slider = 
			tns({
				container: '.slide-wrapper',
				mode: 'carousel', // or 'gallery'
				  axis: 'horizontal', // or 'vertical'
				  items: 6,
				  gutter: 2,
				  edgePadding: 1,
				  fixedWidth: false,
				  slideBy: 1,
				  controls: false,
				  controlsText: ['prev', 'next'],
				  controlsContainer: false,
				  nav: false,
				  navContainer: false,
				  navAsThumbnails: false,
				  arrowKeys: false,
				  speed: 300,
				  autoplay: true,
				  autoplayTimeout: 3000,
				  autoplayDirection: 'forward',
				  autoplayText: ['start', 'stop'],
				  autoplayHoverPause: false,
				  autoplayButton: false,
				  autoplayButtonOutput: false,
				  autoplayResetOnVisibility: false,
				  loop: true,
				  rewind: false,
				  autoHeight: false,
				  responsive: false,
				  lazyload: false,
				  touch: true,
				  mouseDrag: true,
				  swipeAngle: 15,
				  nested: false,
				  freezable: true,
				  onInit: false
				});
		</script>
		
		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	

		<!-- Default snippet for navigation -->
		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		
		
	</body>

</html>