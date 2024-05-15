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
		

<title>Crear Cuenta</title>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

function passwords(){
	var error = document.getElementById('errores');	
	var pass1 = document.getElementById('pass');
	var pass2 = document.getElementById('passRep');

	if (pass1.value != pass2.value){
		error.innerHTML  = "Las contraseñas no coinciden";
		return false;
	}else{
		error.innerHTML  = "";
		return true;
	}
}

function emailDisponible(){
	var emailD = document.getElementById("email"); 
	var error = document.getElementById('errores');	
	var button = document.getElementById("buttSubmit");
	
	$.get('${pageContext.request.contextPath}/UsuarioJSON', {email : emailD.value} , function(data){
		if (!data.disponible){
			error.innerHTML  = "";
			button.style.display='';
		}else{
			error.innerHTML  = "Este correo ya está asociado a una cuenta existente";
			button.style.display='none';		
		}
	});	
}

function aliasDisponible(){
	var aliasD = document.getElementById("alias"); 
	var error = document.getElementById('errores');	
	var button = document.getElementById("buttSubmit");
	
	$.get('${pageContext.request.contextPath}/AliasJSON', {alias : aliasD.value} , function(data){
		if (!data.disponible){
			error.innerHTML  = "";
			button.style.display='';
		}else{
			error.innerHTML  = "Este alias ya está asociado a una cuenta existente";
			button.style.display='none';		
		}
	});	
}

</script>
</head>
<body>

<!-- <button class="btn"><i class="fa fa-wheelchair fa-3x" aria-hidden="true"></i></button>-->

<div id="site-content">
<%@ include file="includes/header.jsp"%>
<main class="main-content">
	<div class="container" >
	<div class="page">
	<form action="CrearNuevoUsuario" method=post onsubmit="return passwords();">
		<div class="row">
		<div class="col-md-6">
		<div class="contact-form">
			<p id="errores" style="color:red"></p>
		</div>
		</div>
		</div>
		
		<div class="row">
		<div class="col-md-6">
			<div class="contact-form">
				<label for="nombre">Nombre </label>
				<input type="text" class="name" placeholder="Nombre*" name="nombre" id="nombre" pattern=".{1,40}" required title="Nombre entre 1 y 40 caracteres"> 
				
				<label for="apellidos">Apellidos</label>
				<input type="text" class="name" placeholder="Apellidos*" name="apellidos" id="apellidos" pattern=".{1,40}" required title="Apellidos entre 1 y 40 caracteres" >
				
				<label for="pass">Contraseña</label>
				<input type="password" class="message" placeholder="Contraseña*" id="pass" name="pass" 
					onmouseout="(this.type='password')" onmouseover="(this.type='text')" 
					pattern=".{5,50}" required title="Contraseña entre 5 y 50 caracteres">
					  
				<label for="passRep">Confirme Contraseña</label>
				<input type="password" class="message" placeholder="Confirme Contraseña*" id="passRep" name="passRep"  
				onmouseout="(this.type='password');" onmouseover="(this.type='text')" 
					pattern=".{5,50}" required title="Contraseña entre 5 y 50 caracteres"> 
			</div>				
		</div>
		<div class="col-md-6">
			<div class="contact-form">
				<label for="alias">Alias</label>
				<input type="text" class="name" placeholder="Alias*" name="alias" id="alias" pattern=".{1,12}" required title="Alias entre 1 y 12 caracteres" onchange="aliasDisponible()"> 
				<label for="email">Email</label>
				<input type="email" class="email" placeholder="Tu @email*" name="email" id="email" required title="Debe ser un email válido" onchange="emailDisponible()"> 
				<label for="tel">Teléfono</label> 
				<input type="tel" class="email" placeholder="Teléfono" name="tel" id="tel" value="" pattern=".{0}|.{6,}" onchange="" required title="Introduzca un Teléfono Válido"> 
				<input type="submit" id="buttSubmit" value="Registrarse">
			</div>				
		</div>		
		</div>
	</form>	
	</div>
	</div>
</main>


<%@ include file="includes/footer.jsp"%>
</div>

		<!--  Modal Login -->						
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	


</body>


</html>