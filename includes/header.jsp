<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ page
	import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*"%>   
<!-- Include header inicio-->
<% 	HttpSession sesion = request.getSession(false);
	Usuario user = new Usuario();									
	user = (Usuario)sesion.getAttribute("usuario");	
//	System.out.println(user);
//	System.out.println(sesion);
	%>
			<header class="site-header">
				<div class="container">
					<a href="index.jsp" id="branding">
						<!-- Logotipo -->
						<img src="images/logo.png" alt="" class="logo"> 
						<div class="logo-copy">
							<h1 class="site-title">Cine Montes</h1>
							<small class="site-description">Al final el cinéfilo siempre tira pal' Montes</small>
						</div>
					</a> <!-- #branding -->

					<div class="main-navigation">
						<button type="button" class="menu-toggle"><i class="fa fa-bars"></i></button>
						<ul class="menu">
						
							<li class="menu-item"><a href="index.jsp">Home</a></li>
							<li class="menu-item"><a href="listadoPeliculas.jsp">Pelis</a></li>
							
							<%if (user != null && user.isAdmin()){ %>
							<li class="menu-item"><a href="crearSala.jsp">Crear Sala</a></li>
							<li class="menu-item"><a href="peliForm.jsp">Nueva Pelicula</a></li>
							<li class="menu-item"><a href="crearFuncion.jsp">Nueva Funcion</a></li>
							<li class="menu-item"><a href="canjearEntrada.jsp">Entradas</a></li>
							<li class="menu-item"><a href="listadoUsuarios.jsp">Usuarios</a></li>
							<%}%>
							
							<%if (user != null){ %>
							<li class="menu-item "><a href="perfil.jsp">Perfil</a></li>
							<li class="menu-item "><a href="Logout">Cerrar</a></li>
							<%}else{ %>
							<li class="menu-item "><a href="#myModal" data-toggle="modal">Iniciar Sesión</a></li>
							<li class="menu-item "><a href="crearCuenta.jsp">Crear Cuenta</a></li>
							<%}%>
							

														
						</ul> <!-- .menu -->

					</div> 
			<!-- .main-navigation -->
					<div class="mobile-navigation">				
					</div>
				</div>
			</header>
			
<div id="myModal" class="modal fade">
	<div class="modal-dialog modal-login">
		<div class="modal-content">
			<form action="ComprobarUsuario" method="post" >
				<div class="modal-header">				
					<h4 class="modal-title">Iniciar Sesión</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">				
					<div class="form-group">
						<label>Usuario</label>
						<input type="text" class="form-control" required="required" name="alias">
					</div>
					<div class="form-group">
						<div class="clearfix">
							<label>Contraseña</label>
							<a href="restaurarPass.jsp" class="pull-right text-muted" ><small> ¿Contraseña olvidada? </small></a>
						</div>						
						<input type="password" class="form-control" required="required" name="pass">
						
					</div>
				</div>
				<div class="modal-footer">
					<label class="checkbox-inline pull-left"><input type="checkbox"> Recuérdame </label>
					<input type="submit" class="btn btn-primary pull-right" value="Entrar" style="color:white;">				
				</div>
				<div class="modal-footer">
					<label class="checkbox-inline pull-left">¿No tienes cuenta? </label>

					<input type="button" class="btn btn-primary pull-right" value="Crear Cuenta" style="color:white;" onclick="window.location='crearCuenta.jsp';">
					
				</div>				
				
			</form>
		</div>
	</div>
</div> 			