<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

	<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
	
		<!-- Loading third party fonts -->
	<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
	<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
	<link rel="stylesheet" href="css/style.css">
			
		<!--  Solucion 404 icon -->	
	<link rel="shortcut icon" href="#" />	
	<title>Listado Usuarios</title>
	</head>
	
<body>

		<div id="site-content">
			<%@ include file="includes/header.jsp"%>

											<%ServicioUsuarios su = new ServicioUsuarios(); 
											  List <Usuario> listaU = null;
											  listaU = su.todasLosUsuarios();
											%>	
			
			<main class="main-content">
				<div class="container">
				<div class="page">				
						<div class="feature">
							<h4 class="feature-title" align="center" >Listado Usuarios</h4>
						</div>	
						<%for (Usuario u : listaU){%>	
						<div class="row">
						<div class="col-md-2" >
						<div class="team">
						<figure class="team-image" style="margin: 0 auto -5px; width: 80px;height: 80px;"><img src="images/avatares/<%=u.getAlias()%>-avatar.jpg" alt="Avatar" id="uploadPreviewMain" onerror="this.onerror=null;this.src='images/avatares/generic_avatar_cineMontesD.png';">
						</figure>
						</div>
						</div>
						<div class="col-md-10" >
						<div class="team">
						<figure class="team-name" style="padding-top: 25px; width: 100%;height: 80px;font-size: 16px;">
						Alias: <strong><%=u.getAlias()%></strong>&nbsp;&nbsp;&nbsp;&nbsp;Nombre y Apellidos: <strong><%=u.getNombre()%></strong> <strong><%=u.getApellidos()%></strong>&nbsp;&nbsp;&nbsp;&nbsp; 
						Email: <strong><%=u.getEmail()%></strong>&nbsp;&nbsp;&nbsp;&nbsp;Teléfono: <strong><% if (u.getTelefono()==null){%>No asignado<%}else{%><%=u.getTelefono()%><%}%></strong> 
						</figure>
						</div>
						</div>
						</div>	
						<%}%>					
				</div>	
				</div>
			</main>
			<%@ include file="includes/footer.jsp"%>
		</div>
		<!-- Default snippet for navigation -->
		

		<script src="js/jquery-1.11.1.min.js"></script>
		<script src="js/plugins.js"></script>
		<script src="js/app.js"></script>
		

</body>
</html>