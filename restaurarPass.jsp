<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, exceptions.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		
		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<script src="https://kit.fontawesome.com/75b83eafd7.js" crossorigin="anonymous"></script>
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		
				<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<title>Editar Cuenta</title>

<script type="text/javascript">

function emailDisponible(){
	var emailD = document.getElementById("email"); 
	var genCodButton = document.getElementById("genCod");
	var error = document.getElementById('errores');	

	$.get('${pageContext.request.contextPath}/UsuarioJSON', {email : emailD.value} , function(data){
		if (!data.disponible){
			error.innerHTML  = "El correo no está asociado con ninguna cuenta";
			genCodButton.style.display="none";
		}else{
			error.innerHTML  = "";
			genCodButton.style.display="";
			
		}
	});
}

function codigoEnviado(){
	var emailD = document.getElementById("email"); 
	var error = document.getElementById('errores');	
	var emailHidden = document.getElementById("emailhidden"); 
	
	$.get('${pageContext.request.contextPath}/CodigoResetearJSON', {email : emailD.value} , function(data){
		if (!data.enviado){		
			error.innerHTML  = "Ha habido un error al enviar el código a su email";
			hideResetear();
		}else{
			emailHidden.value = emailD.value;
			error.innerHTML  = "";
			showResetear();
		}
	});
}

function showResetear(){
	 $(".hide-me > *").css('display','');
}

 function hideResetear(){
	 $(".hide-me > *").css('display','none');
 }
 
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
 
</script>
</head>

<body onload="hideResetear()">

<div id="site-content">
<%@ include file="includes/header.jsp"%>
<main class="main-content">
	<div class="container" >
	
	<% 						
	%>	
		<div class="row">
		<div class="col-md-6">
		<div class="contact-form">
			<p id="errores" style="color:red"></p>
		</div>
		</div>
		</div>	
		
		<div class="row">
		
		<div class="col-md-8 col-md-offset-2">
			<div class="contact-form" >
				<label for="email">Email: </label>
				<input type="email" class="email" placeholder="Tu @email*" name="email" id="email" value="" onchange="emailDisponible()" required> 
				<p> Introduzca su email y le enviaremos un código a su correo para resetear la contraseña.</p> 
				<br>				
			</div>	
			
			<input type="button" class="btn btn-primary pull-right" id="genCod" value="Generar Codigo" style="display: none;" onclick="codigoEnviado()">
		
			</div>	
			
			<div class="row hide-me">
			<div class="col-md-8 col-md-offset-2">
			<form action="ResetearPass" method=post onsubmit="return passwords()">
				<div class="contact-form">
				
				<input type="text" class="message" placeholder="Codigo Recibido" name="codigo" id="codigo" value="" required>  				
				<input type="hidden" name="emailhidden" id="emailhidden" value=""> 	
				<label for="pass">Contraseña Nueva</label>
				<input type="password" class="message" placeholder="Contraseña Nueva*" id="pass" name="pass" 
					onmouseout="(this.type='password')" onmouseover="(this.type='text')" 
					pattern=".{5,50}" required title="Contraseña entre 5 y 50 caracteres">
					  
				<label for="passRep">Confirme Contraseña</label>
				<input type="password" class="message" placeholder="Confirme Contraseña*" id="passRep" name="passRep"  
				onmouseout="(this.type='password');" onmouseover="(this.type='text')" 
					pattern=".{5,50}" required title="Contraseña entre 5 y 50 caracteres"> 				
				
				<input type="submit" class="btn btn-primary pull-right" value="Resetear Contraseña">
				</div>
			</form>
			</div>		
		</div>
				
		</div>
	
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