package servlets;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import domain.Cine;
import domain.Sala;
import exceptions.ServiceException;
import servicios.ServicioButacas;
import servicios.ServicioInsertarButacasSalas;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, crea un nueva Sala en la BBDD
 * Crea una Sala a partir de los datos introducidos en el formulario y lo intenta crear
 * Al ser un poco compleja la distribucion, de momento es predeterminada y no se le permite al Usuario insertarla
 * Los servicio se encargan de crear la Sala y a su vez, introducir las Butacas pertinentes en la BBDD
 * Una vez insertada, redirige el usuario a "crearSala.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/CrearSala")
public class CrearSala extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String nombreSala, distribucion = null;
	private boolean soporteDigital = false,  soporte3D  = false; 
	private Sala sala; private Integer i1=null;
	private ServicioButacas sb = null; private ServicioInsertarButacasSalas sibs = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc=request.getServletContext();
		String salida ="crearSala.jsp", mensaje = "";

		nombreSala = request.getParameter("nombreSala");
		distribucion = request.getParameter("distribucion");
		
		if (request.getParameter("soporteDigital")==null) {
			soporteDigital = true;
		}
		if (request.getParameter("soporte3D")!=null) {
			soporte3D = true;	
		}
		
		if (nombreSala!=null && distribucion!=null) {
			Cine cine = new Cine(1);
			sala = new Sala(0,cine, nombreSala, distribucion,soporteDigital,soporte3D);
			try {
				sb = new ServicioButacas(); sibs = new ServicioInsertarButacasSalas();
				sb.insertarSala(sala);
				sala = sibs.recuperarSalaPorNombre(sala);
				sb.insertarButacasenSala(sala);		
				mensaje = "Sala Creada correctamente";
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
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
