<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, exceptions.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">		
		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		

<title>Editar Cuenta</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

</script>
</head>
<body>



<div id="site-content">
<%@ include file="includes/header.jsp"%>
<main class="main-content">
	<div class="container" >
	<form action="EditarUsuario" method=post >
	<% 						
	user = (Usuario)sesion.getAttribute("usuario");
	String tlf = user.getTelefono();
	if (tlf==null) tlf = "Teléfono no asignado";	
	%>	
	
		<div class="row">
		<div class="col-md-6">
			<div class="contact-form">
				<label for="nombre">Nombre </label>
				<input type="text" class="name" placeholder="Nombre*" name="nombre"  id="nombre" value="<%= user.getNombre()%>" 
				pattern=".{1,40}" required title="Nombre entre 1 y 40 caracteres"> 
				<label for="apellidos">Apellidos</label>
				<input type="text" class="name" placeholder="Apellidos*" name="apellidos" id="apellidos" value="<%= user.getApellidos()%>" 
				pattern=".{1,40}" required title="Apellidos entre 1 y 40 caracteres"> 
				<label for="tel">Teléfono</label> 
				<input type="tel" class="email" placeholder="Teléfono" name="tel" id="tel"  value="<%=tlf%>"
				pattern=".{0}|.{6,}" required title="Introduzca un Teléfono Válido"> 
			</div>				
		</div>
		<div class="col-md-6">
			<div class="contact-form">
				<label for="alias">Alias</label>
				<input type="text" class="name" placeholder="Alias*" name="alias"  id="alias" value="<%= user.getAlias()%>" readonly> 
				<label for="email">Email</label>
				<input type="text" class="email" placeholder="Tu @email*" name="email" id="email" value="<%= user.getEmail()%>" readonly>  
				<p> Si desea cambiar su Alias o Correo, por favor contacte con el administrador.</p>
				<input type="submit" value="Editar Usuario">
			</div>				
		</div>		
		</div>
	</form>	
	</div>
</main>


<%@ include file="includes/footer.jsp"%>
</div>

		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	


</body>


</html>