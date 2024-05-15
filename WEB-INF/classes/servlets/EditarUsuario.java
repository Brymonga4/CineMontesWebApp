package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import domain.Usuario;
import exceptions.ServiceException;

import servicios.ServicioUsuarios;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, edita un Usuario en la BBDD
 * Crea un Usuario a partir de los datos introducidos en el formulario y lo intenta insertar en el lugar del ya existente
 * Solo permitimos modificar el Nombre, Apellidos y Telefono, debido a que el Alias y el Email son FK en otras tablas
 * Una vez modificado, redirige el usuario a "perfil.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

@WebServlet("/EditarUsuario")
public class EditarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String alias, nombre, apellidos, telefono = null;
	private boolean premium, admin; private Integer puntos; private Usuario user; 
	private ServicioUsuarios su = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(); 
		String salida ="perfil.jsp", mensaje = "";
		int modificado = 0;
		
		alias = request.getParameter("alias"); 
		nombre = request.getParameter("nombre"); 
		apellidos = request.getParameter("apellidos"); 
		telefono = request.getParameter("tel"); 
		
		puntos = new Integer(0); premium = true; admin = false;			
		user = new Usuario(alias, nombre, apellidos, telefono);
		
		try {
			su = new ServicioUsuarios();
			modificado = su.modificarUsuario(user);
			if(modificado>0) {
				mensaje = "Usuario Modificado correctamente";
				user = su.recuperarUsuario(user);
				session.setAttribute("usuario", user);
			}
			else
				mensaje = "No se ha podido modificar el usuario";
			
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
		
		response.sendRedirect(salida);
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
