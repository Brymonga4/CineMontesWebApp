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
		
		<title>Contacto</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
	</head>
	

	<body>
		

		<div id="site-content">
		
			<!-- include del header -->
	<%@ include file="includes/header.jsp"%>
								<%if (request.getParameter("msg")!= null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:green"><%=request.getParameter("msg") %></h4>
								</div>	
								<%}%>	
			<main class="main-content">
				<div class="container">
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<span>Contacto</span>
						</div>

						<div class="content">
							<div class="row">
								<div class="col-md-4">
									<h2>Contacto</h2>
									<ul class="contact-detail">
										<li>
											<img src="images/icon-contact-map.png" alt="#">
											<address><span>Cine Montes SA</span> <br>Calle José Grollo, Químismo</address>
										</li>
										<li>
											<img src="images/icon-contact-phone.png" alt="">
											<a href="tel:1590912831">+34 902 20 21 22</a>
										</li>
										<li>
											<img src="images/icon-contact-message.png" alt="">
											<a href="mailto:contact@companyname.com">contacto@montes.com</a>
										</li>
									</ul>
									<form action="EnviarMensajeContacto">
									<div class="contact-form" >
										<input type="text" class="name" name ="name" placeholder="Nombre.." required>
										<input type="email" class="email" name = "email" placeholder="Email..." required>
										<input type="text" class="website"  placeholder="Website...">
										<textarea class="message" placeholder="Mensaje..." name ="message" required></textarea>
										<input type="submit" value="Envíanos un mensaje">
									
									</div>
								   </form>
								</div>
								
								<div class="col-md-7 col-md-offset-1">
									<div class="map"><iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3079.0231946287754!2d-0.3913848969343489!3d39.49139085269459!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd6045f376295bcf%3A0x9dacf5e4b312fab5!2sCarrer%20de%20Josep%20Grollo%2C%2046025%20Val%C3%A8ncia!5e0!3m2!1sen!2ses!4v1589742574694!5m2!1sen!2ses" width="600" height="450" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe></div>
								</div>
								
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

	</body>

</html>