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
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, envia un mensaje al email del Administrador
 * El mensaje contiene el formulario de Contacto que acaba de utilizar el Usuario
 * Una vez enviado, redirige el usuario a "contacto.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/EnviarMensajeContacto")
public class EnviarMensajeContacto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String nombre =request.getParameter("name");
		String email = request.getParameter("email");
		String msg = request.getParameter("message");
		
		String destinatario = "hitohitotadano7@gmail.com"; 
		String asunto ="Contactar con "+nombre+" en "+email;
		 	
		try {
			ServicioEntradas se = new ServicioEntradas();
			se.enviarEmail(destinatario, asunto, msg);
			String mensaje = "Nos pondremos en contacto con Ud lo mas pronto posible.";
			response.sendRedirect("contacto.jsp?msg="+mensaje);
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
				return;
			}
		}		
		
		
		
	}
	


}
