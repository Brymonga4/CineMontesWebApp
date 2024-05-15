package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import domain.Funcion;
import domain.Pelicula;
import exceptions.ServiceException;
import servicios.ServicioFunciones;

/**
 * Este Servlet analiza la request del tipo Get que le llega y mediante el uso de servicios, recupera las Funciones disponibles de la BBDD
 * Crea una Pelicula con los datos del Formulario para consultar las Funciones disponibles de esta
 * Hace la consulta mediante servicios e imprime los resultados mediante un Array de Objetos JSON
 * Evitamos asi recargar la pagina al hacer la consulta
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/FuncionesPeliJSON")
public class FuncionesPeliJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServicioFunciones sf = null; 
	private String funcionesJsonString = null; private JSONArray ja = null;
	private Funcion fun = null; private Pelicula peli = null; private List <Funcion> list = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Date fecha = null;		
		String strfecha = request.getParameter("fecha");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			if ( !strfecha.equals("") && strfecha != null ) {
				fecha = sdf.parse(strfecha);			
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		peli = new Pelicula(Integer.parseInt(request.getParameter("pelicula")));		
		
		try {
			sf = new ServicioFunciones();
			
			list = sf.recuperarTodasFuncionesFechaDePeli(peli, fecha);
			
			//list = sf.recuperarTodasFuncionesDisponiblesDePeli(peli);
			
			ja = new JSONArray(list);
			funcionesJsonString = ja.toString();
//			System.out.println(funcionesJsonString);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(funcionesJsonString);
			out.flush();			

			
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

}
