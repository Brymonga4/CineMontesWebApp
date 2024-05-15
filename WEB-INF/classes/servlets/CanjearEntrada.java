package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ServiceException;
import servicios.ServicioEntradas;



/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, canjea una Entrada determinada de la BBDD
 * Si consigue canjearla, o si no tambien, vuelve a "canjearEntrada.jsp" con mensajes diferentes
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
@WebServlet("/CanjearEntrada")
public class CanjearEntrada extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String idFuncion, identificador = null;
	private ServicioEntradas se= null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String salida ="canjearEntrada.jsp", mensaje = "";
		int updateado = 0;
		
		idFuncion = request.getParameter("idfuncion"); 
		identificador = request.getParameter("identificador"); 
		
	if (idFuncion!= null && identificador!=null)	{
		try {
			se = new ServicioEntradas();
			updateado = se.canjearEntrada(Integer.parseInt(idFuncion), identificador);
			if(updateado>0) {
				mensaje = "Entrada Canjeada correctamente";
				response.sendRedirect(salida+"?mensaje="+mensaje);
			}
			else {
				mensaje = "Esa entrada ya ha sido usada o no existe";
				response.sendRedirect(salida+"?msgError="+mensaje);
			}
			
			
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
