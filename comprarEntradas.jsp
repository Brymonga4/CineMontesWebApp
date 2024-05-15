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
		
		<title>Comprar Entradas</title>

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
	</head>


	<body>
		

		<div id="site-content">
			<!-- include del header -->
			<%@ include file="includes/header.jsp"%>

<%  user = (Usuario)sesion.getAttribute("usuario"); 

	Funcion fun = new Funcion();
	List<Butaca> listB = new ArrayList<Butaca>();
	Double precioTotal = 0.0;
	
	fun =(Funcion) sesion.getAttribute("funcion");
	listB = (List <Butaca>) sesion.getAttribute("listaB");	
	precioTotal = (Double) sesion.getAttribute("precioTotal");
	 	 
	 if (listB == null || fun == null || user == null || precioTotal == null){
		request.setAttribute("msgError", "Error en la sesión del Usuario");
		request.getRequestDispatcher("index.jsp").forward(request, response); 
	 }else{
	 

		Date horaF = new Date(fun.getFecha().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String horaFunstr = sdf.format(horaF);
	 
%>	

			
			<main class="main-content">
				<div class="container">
				<form action="GenerarEntrada" method="post" >
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<a href="peli.jsp?id=<%=fun.getPeli().getIdPelicula() %>"><%=fun.getPeli().getTitulo() %></a>
							<a href="butacasFuncion.jsp?idFun=<%=fun.getId()%>">Escoger Butaca</a>								
							<span>Comprar Entradas</span>
						</div>

						<h2 style="display: inline">Datos del Usuario</h2>
						<h2 style="display: inline;text-align:right;float:right" >Butacas</h2>					
						

						<br><br>
							<div class="row">
							
								<div class="col-md-8">
							
									<div class="contact-form">
									<%if (user != null){ %>
										<label for="alias">Alias</label>
										<input type="text" class="name" placeholder="Alias*" name="alias" id="alias"  value="<%=user.getAlias()%>" readonly>
										<label for="email">Email</label> 
										<input type="text" class="email" placeholder="Tu @email*" name="email" id="email" value="<%=user.getEmail()%>" readonly>  
									<%}else{ %>
										<input type="text" class="name" placeholder="Alias*" name="alias"> 
										<input type="text" class="email" placeholder="Tu @email*" name="email">  											
									<%} %>																		
									</div>
									
								<h2> Pago </h2>
									<div class="contact-form">
										<label for="nombre">Nombre Completo</label>
										<input type="text" class="genero" placeholder="Nombre Completo En La Tarjeta" id="nombre" name="nombreTarjeta"
										pattern=".{3,40}" required title="Entre 3 y 40 caracteres"> 
										<label for="numeroT">Número de Tarjeta</label>
										<input type="text" class="genero" placeholder="Número de Tarjeta" name="numTarjeta" id="numeroT"
										pattern=".{13,16}" required title="Solo aceptamos VISA, 13-16 caracteres">
										<label for="caducidad">Fecha de Caducidad MM/YY</label>
										<input type="text" class="genero" placeholder="Caducidad de Tarjeta" name="caducidad" id="caducidad"
										pattern="(?:0[1-9]|1[0-2])/[0-9]{2}" required title="Introduce fecha en formato MM/YY">
										<label for="cvv">CVV</label>
										<input type="text" class="genero" placeholder="CVV" name="CVV" id="cvv"
										pattern=".{3,4}" required title="Introduzca un CVV válido">  	
									</div>						
								</div>

								<div class="col-md-4">

									<div class="contact-form">
										<p class="form-check-label text-center" style="font-size:medium; font-weight: bold;" >
										Función de las <%=horaFunstr%>,  
										el <%=Fecha.convertirAString(fun.getFecha() , "dd-MM-YYYY")%>
										</p>
																			
										<p class="form-check-label text-center" style="font-size:medium; font-weight: bold;" ><%=fun.getPeli().getTitulo() %> - en la Sala <%= fun.getSala().getIdSala() %></p>
										<img class="center-block" src="images/peliculas/<%=Recursos.limpiarString(fun.getPeli().getTitulo())%>.jpg"  style="width: 300px; height: 200px;"/> <br>
										<div class="image-upload text-center">
											<div class="form-check">
											
											<%for (Butaca b : listB){ 
												double d = b.getTipo() * fun.getPrecio();
												d = Math.round(d*100.0)/100.0;
											%>
											  <p class="form-check-label" style="font-size:medium; font-weight: bold;" >
											  <%=Recursos.tipoButacaEnString(b)%>  - Fila <%=b.getFila()%> - Butaca <%=b.getButaca()%> &nbsp;&nbsp; <%=d%> €
											  </p>									  
											  <%}%>
											  <p class="form-check-label" style="font-size:medium; font-weight: bold;">
											    Total : <%=precioTotal%> €
											  </p>
											</div>
									 	</div>
									 	<%} %>
									 	
									</div>					
									
								</div>								
																															
								</div>
										<!-- Row -->	
							<input type="submit" value="Finalizar Compra">	
															
							</div>
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