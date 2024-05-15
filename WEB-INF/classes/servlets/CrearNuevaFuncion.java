package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.*;
import exceptions.ServiceException;
import servicios.ServicioFunciones;
import servicios.ServicioPeliculas;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, crea una nueva Funcion en la BBDD
 * Crea una Funcion a partir de los datos introducidos en el formulario y la intenta crear
 * Primero se tiene que asegurar que el espacio horario de la Funcion que acabamos de crear no entre en conflicto con las Funciones ya creadas en esa Sala
 * Una vez insertada, redirige el usuario a "crearFuncion.jsp" con un mensaje satisfactorio
 * Si no puede, redirige el usuario a "crearFuncion.jsp" con un mensaje de error por espacio horario
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
@WebServlet("/CrearNuevaFuncion")
public class CrearNuevaFuncion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String idPeli = null , idSala = null, strfecha = null, audio = null, strprecio = null;
	private Double precio; private Integer i1 = null;
	private Pelicula peli;  private Sala sala = null; private Funcion fun = null;
	private ServicioFunciones sf = null; private ServicioPeliculas sp = null;
	private Timestamp fecha; private Date parsedFecha;
	private Timestamp horaFinNF = null; private Timestamp horaInicioNF = null;
	private List<Funcion> list; private boolean puedes = false;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String salida ="crearFuncion.jsp", mensaje = "Funcion creada correctamente";

		idPeli = request.getParameter("pelicula");
		idSala = request.getParameter("salas");
		strfecha = request.getParameter("horaFun");
		audio = request.getParameter("audio");
		strprecio = request.getParameter("precio");

		
		sala = new Sala(Integer.parseInt(idSala));
		peli = new Pelicula(Integer.parseInt(idPeli));
		precio = Double.parseDouble(strprecio);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");	
		
		try {
			if ( !strfecha.equals("") && strfecha != null ) {
				parsedFecha = sdf.parse(strfecha);
				fecha = new java.sql.Timestamp(parsedFecha.getTime());
				
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		fun = new Funcion(i1, peli, sala, fecha, audio, precio);
				  	  	
		try {
			sf = new ServicioFunciones();
			sp = new ServicioPeliculas();
			
			peli = sp.duracionPelicula(peli.getIdPelicula());
			
		  	 long tt = fun.getFecha().getTime(); 
		  	 long mm = peli.getDuracion() * 60 * 1000; 
		  	  
		  	horaInicioNF = fun.getFecha(); //System.out.println("Hora inicio funcion a crear "+horaInicioNF);
		  	horaFinNF = new Timestamp (tt+mm); //z	System.out.println("Hora fin funcion a crear "+horaFinNF);				
					  	
			//list = sf.recuperarTodasFuncionesFechaDePeliDeSala(peli, fecha, sala);
		  	//puedes = sf.comprobarPeriodoDisponible(list, horaInicioNF, horaFinNF, peli);
		  	
		  	list = sf.recuperarTodasFuncionesDeFechaDeSala(fecha, sala);
		  	puedes = sf.comprobarPeriodoDisponible(list, horaInicioNF, horaFinNF);
			
			
//			System.out.println(puedes);
//			System.out.println(list.size());
			
			//Asegurarse de que se puede crear y no coincide con otras funciones
			if (puedes || list.size()==0)
				sf.escribirFuncion(fun);
			else
				mensaje = "Ha habido un problema con el espacio horario de esa funcion";
			
			response.sendRedirect(salida+"?mensaje="+mensaje);
		
		} catch (ServiceException e) {
			if(e.getCause()==null){
				response.sendRedirect("error.jsp?mensaje="+e.getMessage());// para usuario final
				//devolverPaginaError(response,e.getMessage());
				//System.out.println(e.getMessage());//Error Lógico para usuario
			}else{
				// error interno
				getServletContext().log("Error  NO ESPERADO  por la aplicacion en el servlet"+
						request.getServletPath());// esto lo escribe en el diario log  localhost


				e.printStackTrace();// esto lo escribe en el diario log  tomcat7-stderr
				response.sendRedirect("error.jsp?mensaje= Error interno");// para usuario final
			}
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
