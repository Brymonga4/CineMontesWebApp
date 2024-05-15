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
		
		<title>Trabaja con Nosotros</title>

		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	

		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<!--  Slider -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.2/tiny-slider.css">


	</head>
	

	<body>
		

		<div id="site-content">
		
			<!-- include del header -->
	<%@ include file="includes/header.jsp"%>

			<main class="main-content">
				<div class="container">
					<div class="page">
						<div class="breadcrumbs">
							<a href="index.jsp">Home</a>
							<span>Trabaja con Nosotros</span>
						</div>

						<div class="row">
							<div class="col-md-4">
								<figure><img src="images/cine_Montes_Fachada.jpg" alt="figure image"></figure>
							</div>
							<div class="col-md-8">
								<p class="leading">Cine en decadencia que está a punto de derrumbarse, por favor vengan a visitarnos</p>
								<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit consectetur adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam.</p>
							</div>
						</div>

						<div class="row">
							<div class="col-md-9">
								<h2 class="section-title">Vision &amp; Futuro</h2>
								<p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit consectetur adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam. Con esto de la pandemia y el COVID campando a sus anchas, estamos en la quiebra. </p>

								<p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit consectetur adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam dignissimos ducimus qui blanditiis praesentium voluptatum atque.</p>
							</div>
							<div class="col-md-3">
								<h2 class="section-title">Cines Mejores</h2>
								<ul class="arrow">
									<li><a href="https://www.cinesa.es/">Cinesa</a></li> 
									<li><a href="https://yelmocines.es/">Cines Yelmo</a></li>
								</ul>
							</div>
						</div> <!-- .row -->
						
						<h2 class="section-title">Nuestro Equipo</h2>
						<div class="row">

							<div class="col-md-3">
								<div class="team">
									<figure class="team-image"><img src="images/smugGirls/avatar.jpg" alt=""></figure>
									<h2 class="team-name">Bryan M</h2>
									<small class="team-title">Fundador</small>
									<div class="social-links">
										<a href="" class="facebook"><i class="fa fa-facebook"></i></a>
										<a href="" class="twitter"><i class="fa fa-twitter"></i></a>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="team">
									<figure class="team-image"><img src="images/smugGirls/smug-foodcourt.jpg" alt=""></figure>
									<h2 class="team-name">Smug Cartoon 1</h2>
									<small class="team-title">Director</small>
									<div class="social-links">
										<a href="" class="facebook"><i class="fa fa-facebook"></i></a>
										<a href="" class="twitter"><i class="fa fa-twitter"></i></a>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="team">
									<figure class="team-image"><img src="images/smugGirls/Smug senko.jpg" alt=""></figure>
									<h2 class="team-name">Smug Cartoon 2</h2>
									<small class="team-title">Reviewer</small>
									<div class="social-links">
										<a href="" class="facebook"><i class="fa fa-facebook"></i></a>
										<a href="" class="twitter"><i class="fa fa-twitter"></i></a>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="team">
									<figure class="team-image"><img src="images/smugGirls/smug.jpg" alt=""></figure>
									<h2 class="team-name">Smug Cartoon 3</h2>
									<small class="team-title">Recursos Humanos</small>
									<div class="social-links">
										<a href="" class="facebook"><i class="fa fa-facebook"></i></a>
										<a href="" class="twitter"><i class="fa fa-twitter"></i></a>
									</div>
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