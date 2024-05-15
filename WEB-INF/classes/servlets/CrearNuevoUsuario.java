package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import domain.Usuario;
import exceptions.ServiceException;
import recursos.Recursos;
import servicios.ServicioUsuarios;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, crea un nuevo Usuario en la BBDD
 * Crea un Usuario a partir de los datos introducidos en el formulario y lo intenta crear
 * Pasa la Password por MD5 para insertarla de esta manera en la BBDD
 * Una vez insertada, redirige el usuario a "index.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class BorrarPelicula
 */
@WebServlet("/CrearNuevoUsuario")
public class CrearNuevoUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String alias, pass, nombre, apellidos, email, telefono = null;
	private boolean premium, admin; private Integer puntos; private Usuario user; 
	private byte[] bytesOfPass; private byte[] passDigested;  private MessageDigest md = null; private String hashPass = null;
	private ServicioUsuarios su = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(); 
		ServletContext sc=request.getServletContext();
		String salida ="index.jsp", mensaje = "";

		alias = request.getParameter("alias"); 
		pass = request.getParameter("pass"); 
		nombre = request.getParameter("nombre"); 
		apellidos = request.getParameter("apellidos"); 
		email = request.getParameter("email"); 
		telefono = request.getParameter("tel"); 
		
		puntos = new Integer(Recursos.randomEntero(5)); premium = true; admin = false;		
		
		try {
			bytesOfPass = pass.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			passDigested = md.digest(bytesOfPass);
			BigInteger bigInt = new BigInteger(1,passDigested);
			hashPass = bigInt.toString(16);
			
			while(hashPass.length() < 32 ){
				hashPass = "0"+hashPass;
				}

		} catch (NoSuchAlgorithmException e1) {		
			e1.printStackTrace();
			response.sendRedirect("error.jsp?mensaje= Error interno");
		}
		

		user = new Usuario(alias, hashPass, nombre, apellidos, email, telefono, puntos, premium, admin);

		
		try {
			su = new ServicioUsuarios();
			su.crearusuario(user);
			mensaje = "Usuario Creado correctamente";
			request.setAttribute("msg", mensaje);
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
