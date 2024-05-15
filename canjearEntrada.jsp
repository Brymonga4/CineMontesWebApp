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

<title>Canjear Entrada</title>

<script type="text/javascript">

</script>
</head>

<body>

<div id="site-content">
<%@ include file="includes/header.jsp"%>
<main class="main-content">

								<%if (request.getParameter("mensaje")!=null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:green"> <%=request.getParameter("mensaje")%> </h4>
								</div>	
								<%}%>
								
								<%if (request.getParameter("msgError")!=null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:red"> <%=request.getParameter("msgError")%> </h4>
								</div>	
								<%}%>								

	<div class="container" >
	
		<div class="row">
		
		<div class="col-md-10 col-md-offset-1">
		<form action=CanjearEntrada method=post>
			<div class="contact-form" >
				<label for="idfuncion">Id Función: </label>
				<input type="number" class="message" placeholder="Id de la función" min="1" name="idfuncion" id="idfuncion" value="" required> 
				<label for="identificador">Escanear QR: </label>
				<input type="text" class="message" placeholder="MUQFY1Y57" name="identificador" id="identificador" value="" required>
				<p> Esto simula un lector que enviaría el identificador y la función a la base de datos.</p>						
			</div>	
			
				<input type="submit" class="btn btn-primary pull-right" value="Canjear Entrada">					

		</form>					
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