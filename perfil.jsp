<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page
	import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, util.*"%>
	
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<title>Area Personal</title>

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
		.image-upload>input {
		  display: none;
		}
		</style>

	</head>
	

	<body>
		

		<div id="site-content">
		
			<!-- include del header -->
	<%@ include file="includes/header.jsp"%>
	
			<main class="main-content">
				<div class="container">
					<div class="page">
						<% //System.out.println(sesion);														
						user = (Usuario)sesion.getAttribute("usuario"); 
						
						%>					
								<div class="feature">
								<%if (user != null){ 
								
									ServicioEntradas se = new ServicioEntradas();
									ServicioFunciones sf = new ServicioFunciones();
									ServicioPeliculas sp = new ServicioPeliculas();	
									ServicioButacas sb = new ServicioButacas();
									
									List <Entrada> list; Funcion funaux; Pelicula peliaux; Butaca butaux;
									list = se.recuperarEntradasDeUsuario(user);
									

								%>
									<h4 class="feature-title" align="center">Hola <%=user.getAlias() %>, bienvenido a tu cuenta</h4>
								</div>

<form action=SubirAvatar method=post enctype=multipart/form-data>
						<div class="row">
							<div class="col-md-4">
								<div class="team"><figure class="team-image" style="margin: 0 auto -5px;"><img src="images/avatares/<%=user.getAlias()%>-avatar.jpg" alt="Avatar-<%=user.getAlias()%>" id="uploadPreviewMain" onerror="this.onerror=null;this.src='images/avatares/generic_avatar_cineMontesD.png';"></figure> 								
								<div class="image-upload">
								  <label for="uploadImageMain"> 
								    <img src="images/upload-local-button-icon.png" width="20%" height="auto" />
								  </label>
								
								  <input type="file" id="uploadImageMain" onchange="PreviewImageMain();" name="avatarFile"/>
								</div>
								</div>

								
								<ul class="movie-schedule">
									<li>
										<div class="date">Alias</div>
										<h2 class="entry-title"><a ><%=user.getAlias()%></a></h2>
										<input type="hidden" value="<%=user.getAlias()%>" name="alias" >
									</li>
									<li>
										<div class="date">Nombre y Apellidos</div>
										<h2 class="entry-title"><a ><%=user.getNombre()%> <%=user.getApellidos()%></a></h2>
									</li>
									<li>
										<div class="date">Email</div>
										<h2 class="entry-title"><a ><%=user.getEmail()%></a></h2>
									</li>
									<li>
										<div class="date">Teléfono</div>
										<h2 class="entry-title"><a >
										<% if (user.getTelefono()==null){%>
											No asignado
										<%}else{ %>	
											<%=user.getTelefono()%>
										<%} %>	
										</a></h2>
									</li>
								</ul>
								
								
							</div>
							
							<div class="col-md-8">
								<h2 class="section-title" align="center"><u>Entradas Compradas</u></h2>
								
								

								
								<table class="table">
								  <thead>
								    <tr>
								      <th scope="col"><h2 align="center">Sesion</h2></th>
								      <th scope="col"><h2 align="center">Cine</h2></th>
								      <th scope="col"><h2 align="center">Pelicula</h2></th>
								      <th scope="col"><h2 align="center">Precio</h2></th>
								    </tr>
								  </thead>
								 
								<%									//Recupero entradas completas
								for (Entrada en : list) {
									funaux = new Funcion();  peliaux = new Pelicula();	butaux = new Butaca();
									butaux = sb.recuperarButaca(en.getButaca().getIdButaca());
									funaux = sf.recuperarFun(en.getFuncion());
									peliaux = sp.pelicula(funaux.getPeli().getIdPelicula());	
									funaux.setPeli(peliaux);
									en.setFuncion(funaux); en.setButaca(butaux);
								}								
								%>
								
								<%
									if (list.size()>0){
										for (Entrada en : list){ 
								    		Date horaF = new Date(en.getFuncion().getFecha().getTime());
								    		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
								    		String sesionHora = sdf.format(horaF); 
								    		
											String diaFun = Fecha.convertirAString(en.getFuncion().getFecha(), "dd-MM-YYYY");
											
											
											double d = en.getButaca().getTipo() * en.getFuncion().getPrecio();
											d = Math.round(d*100.0)/100.0;
											String precioButaca = ""+d+" €";
										%>
										
								<tr>
									<td class="text-center"><h1 class="entry-title" style="font-size: 18px;"><a><%=diaFun%>, <%=sesionHora%> h</a></h1></td>
									<td class="text-center"><h1 class="entry-title" style="font-size: 18px;"><a>Cine Montes </a></h1></td>
									<td class="text-center"><h1 class="entry-title" style="font-size: 18px;"><a><%=en.getFuncion().getPeli().getTitulo()%> </a></h1></td>
									<td class="text-center"><h1 class="entry-title" style="font-size: 18px;"><a><%=precioButaca%></a></h1></td>
								</tr>
										<%}//Fin for
									}else{ %>
								<tr>
									<th class="text-center" colspan="4">
									<h1 class="entry-title" style="font-size: 18px;"><a>No tiene aún ninguna entrada comprada.</a></h1>
									</th>
								</tr>									
									<%}%>
									
								 </table>
									<br><br>
								<%} //Fin if%>

								<input type="submit" class="button pull-left" value="Subir Avatar">
								<input type="button" class="button pull-right" value="Cambiar Nombre o Teléfono" onclick="window.location='editarCuenta.jsp';">
								
							</div>
						</div>
</form>						
<br><br><br><br>

					</div>
				</div> <!-- .container -->
			</main>
			
	<!-- include footer -->			
	<%@ include file="includes/footer.jsp"%>
			
		</div>
		
		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	


	</body>

</html>