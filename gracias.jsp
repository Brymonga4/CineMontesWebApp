<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

	<head>
	<meta charset="UTF-8">
	<meta http-equiv="Refresh" content="5;url=index.jsp">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
	
		<!-- Loading third party fonts -->
	<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
	<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<!-- Loading main css file -->
	<link rel="stylesheet" href="css/style.css">
			
		<!--  Solucion 404 icon -->	
	<link rel="shortcut icon" href="#" />	
	<title>Gracias por su compra</title>
	</head>
	
<body>

		<div id="site-content">
			<%@ include file="includes/header.jsp"%>
			
								<%if (request.getParameter("mensaje")!= null ){ %>
								<div class="feature">
									<h4 class="feature-title" align="center" style="font-style: italic; color:green"><%=request.getParameter("mensaje") %></h4>
								</div>	
								<%}%>

			<main class="main-content">
				<div class="container">
				<div class="page">				
						<div class="row">
						<div class="col-md-12" >
						<figure class="movie-poster"><img src="images/gracias.jpg" class="rounded mx-auto d-block"  alt=""></figure>
						</div>
						</div>					
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