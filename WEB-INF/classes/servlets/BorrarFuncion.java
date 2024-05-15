package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.Funcion;
import exceptions.ServiceException;
import servicios.ServicioFunciones;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, borra una Funcion determinada de la BBDD
 * Lanza los errores a "error.jsp" si no consigue borrar la Funcion
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

@WebServlet("/BorrarFuncion")
public class BorrarFuncion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id=null;
	private ServicioFunciones sf = null; 
	private Funcion fun = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String salida ="crearFuncion.jsp", mensaje = "";
		
		id=request.getParameter("id");
		fun = new Funcion (Integer.parseInt(id));	
		int borrado = 0;
		
		try {
			sf = new ServicioFunciones();
			
			sf.borrarFuncion(fun);
			borrado =sf.borrarFuncion(fun);
				
			if (borrado>0)
				mensaje = "Se ha borrado la funcion correctamente";
			else
				mensaje = "No se ha podido borrar la funcion";
			
			response.sendRedirect(salida+"?mensaje="+mensaje);
			return;
		} catch (ServiceException e) {
			if(e.getCause()==null){
				response.sendRedirect("error.jsp?mensaje="+e.getMessage());// para usuario final
				//devolverPaginaError(response,e.getMessage());
				//System.out.println(e.getMessage());//Error Lógico para usuario
			}else{
				// error interno
				System.out.println("Error interno");
				getServletContext().log("Error  NO ESPERADO  por la aplicacion en el servlet"+
						request.getServletPath());// esto lo escribe en el diario log  localhost

				e.printStackTrace();// esto lo escribe en el diario log  tomcat7-stderr
				
				response.sendRedirect("error.jsp?mensaje= Error interno");// para usuario final
				//request.getRequestDispatcher(salida).forward(request, response);
				return;
				
			}
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
