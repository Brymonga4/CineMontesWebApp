<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="javax.servlet.*,java.util.*,objetos.*,domain.*,servicios.*, java.text.*, recursos.*, exceptions.*, java.sql.Timestamp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
		
		
		<!-- Loading third party fonts -->
		<link href="http://fonts.googleapis.com/css?family=Roboto:300,400,700|" rel="stylesheet" type="text/css">
		<link href="fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
		<!-- Loading main css file -->
		<link rel="stylesheet" href="css/style.css">
		
		<script src="https://kit.fontawesome.com/75b83eafd7.js" crossorigin="anonymous"></script>
		<script type="text/javascript" src="js/cines.js"></script>
		
		<!--  Solucion 404 icon -->
		<link rel="shortcut icon" href="#" />
		
		<style type="text/css">
		
		#butacasR.th, #butacasR.td {
		  height: 10px;
		  padding: 5px;
		  text-align:center;
		  
		}
		</style>					
										
<title>Butacas Funcion</title>
</head>
<body>

<!-- <button class="btn"><i class="fa fa-wheelchair fa-3x" aria-hidden="true"></i></button>-->



<div id="site-content">
<%@ include file="includes/header.jsp"%>

	<main class="main-content">
	<form action="PreCompra" method="post" >
	<div class="container " style="width: 958px">
	<div class="row">
	<div> <!-- Div tabla -->
		<table>
		<% 	String funcion = request.getParameter("idFun");
			if (funcion=="" || funcion == null){
				request.setAttribute("msgError", "Debe seleccionar una Funcion valida");
				request.getRequestDispatcher("index.jsp").forward(request, response);			
			}else{
				
		   Funcion fun = new Funcion(Integer.parseInt(funcion));
		   
		   ServicioFunciones sf = new ServicioFunciones();
		   fun = sf.recuperarFun(fun);
		   
		   Date date = new Date(); long time = date.getTime(); Timestamp ts = new Timestamp(time);
		   
		   if (fun.getFecha().before(ts) ){
				request.setAttribute("msgError", "Esa función ya ha comenzado o ha terminado");
				request.getRequestDispatcher("peli.jsp?id="+fun.getPeli().getIdPelicula()).forward(request, response);	
		   }
		   
		   ServicioPeliculas sp = new ServicioPeliculas();
		   Pelicula peli = sp.pelicula(fun.getPeli().getIdPelicula());
		   
		   ServicioInsertarButacasSalas sibs = new ServicioInsertarButacasSalas();
		   Sala sala = sibs.recuperarSala(fun.getSala());
		   
		   fun.setPeli(peli); fun.setSala(sala);
		   
		   session.setAttribute("funcion", fun);
		   
		   ServicioButacas sb = new ServicioButacas();
		   List <Butaca> listB = null;
		   listB = sb.recuperarButacasSala(fun.getSala());
		   List <Butaca> listBO =  null;
		   listBO = sb.recuperarButacasOcupadasDeFuncion(fun);
		   
		   if (listB.size()>0){
			   for (Butaca b: listB){
				   				   
				   if (b.getButaca()==1){
					   //  System.out.println("<tr>");
					   %>
					   <tr>
					   <% 
				   }
				   
				   if ( (b.getFila()==12 && b.getButaca()==1) || (b.getFila()==12 && b.getButaca()==9) ){
					   %>
					   <td></td><td></td><td></td>
					   <% 
				   }else if (b.getFila()==12 && b.getButaca()==4){
					   %>
					   <td></td><td></td><td></td><td></td><td></td>
					   <%    
				   }
				   
		
				   if (b.getTipo()>0.9&&b.getTipo()<1.4){
					   // System.out.println(b + " normal");
					   if (listBO.contains(b)|| (b.getButaca() %2) ==0 ){  //Modo Covid || (b.getButaca() %2) ==0
						   %>
						   <td style='LINE-HEIGHT:50px; '><i class="fas fa-chair fa-3x" onclick="addHiddenValues(this)" style="color:#ff573d;pointer-events:none;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>"></i></td>
					   	   <%
					   }else{
						   %>
						   <td style='LINE-HEIGHT:50px; '><i class="fas fa-chair fa-3x" onclick="addHiddenValues(this)" style="color:#ffaa3c;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>-<%= b.getTipo()%>"></i></td>
						   <%						   
					     }

				   }else if (b.getTipo()>1.4){
					   // System.out.println(b + " premium");
					   if (listBO.contains(b)){	   
						   %>
						   <td style="LINE-HEIGHT:50px;"><i class="fas fa-couch fa-2x" onclick="addHiddenValues(this)" style="color:#ff573d;pointer-events:none;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>"></i></td>
						   <% 
					   }else{
						   %>
						   <td style="LINE-HEIGHT:50px;"><i class="fas fa-couch fa-2x" onclick="addHiddenValues(this)" style="color:#ffaa3c;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>"></i></td>
						   <% 					   
					   }
				   }else if (b.getTipo()<0.9){
					   // System.out.println(b + " campeon");
					    if (listBO.contains(b)){	
					   %>
					   <td style="LINE-HEIGHT:50px;"><i class="fas fa-wheelchair fa-2x" onclick="addHiddenValues(this)" style="color:#ff573d;pointer-events:none;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>"></i></td>
					   <% 
					    }else{
							   %>
							   <td style="LINE-HEIGHT:50px;"><i class="fas fa-wheelchair fa-2x" onclick="addHiddenValues(this)" style="color:#ffaa3c;" id="<%= b.getIdButaca()%>-<%= b.getFila()%>-<%= b.getButaca()%>-<%= b.getTipo()%>"></i></td>
							   <% 					    	
					    }
				   }
				   
				   if(b.getTipo()<0.9&&b.getButaca()==3||b.getTipo()<0.9&&b.getButaca()==8){
					   // System.out.println("6 Espacios aqui");
					   %>
					   <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
					   <% 
				   }else if (b.getTipo()>0.9&&b.getButaca()==6||b.getTipo()>0.9&&b.getButaca()==16){
					   // System.out.println("6 Espacios aqui");
					   %>
					   <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
					   <%
				   }else if (b.getButaca()==22 ||( b.getButaca()==11 && b.getTipo()<0.9&&b.getButaca()==8)){			   
					  // System.out.println("Se cierra el </tr> Un <br> siguiente fila aqui");
					   %>
					   </tr>
					   <% 	
				   }
			   }
			}
			}%>

		</table>
	</div> <!-- Div tabla -->
	

</div>
</div>
<br>

	<div class="container" style="width: 450px" >
	<input type="hidden" value="<%=request.getParameter("idFun")%>" name="idFun">
	<input type="hidden" value="" id="butacasValues">
	<div class="row"> 
	<div class="col-md-8">
		<table id="butacasR" style="width: 100%">
	                <tr>
	                  <th><p>Butacas Reservadas</p></th>
	                </tr>		
		</table>
	</div>	
	
	<div class="col-md-4">
		<div class="contact-form">
			<input type="submit" value="Comprar" id="compra" style="display: none">
		</div>
	</div>
	</div>
	</div>

		
	</div>	
	
	</form>

</main>


<%@ include file="includes/footer.jsp"%>
</div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	

</body>


</html>