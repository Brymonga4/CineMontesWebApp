package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, valida un Usuario (hace el Login)
 * Compara el Usuario introducido por el formulario con el Usuario que esta en la base de datos
 * Si el Alias y Password son iguales, invalida la sesion si existe, y crea una nueva, esta vez metiendo el Usuario que acabamos de comprobar como atributo de la sesion y volviendo a "index.jsp" con un mensaje satisfactorio
 * Si no son iguales, redirige al Usuario a "index.jsp" con un atributo en el request, indicando que ha introducido el Alias o Password Incorrecto
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class ComprobarUsuario
 */
@WebServlet("/ComprobarUsuario")
public class ComprobarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String alias, pass = null;  private Usuario user; 
	private byte[] bytesOfPass; private byte[] passDigested;  private MessageDigest md = null; private String hashPass = null;
	private ServicioUsuarios su = null;   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false); 

		String salida ="index.jsp";
		alias = request.getParameter("alias"); 
		pass = request.getParameter("pass"); 
		
	if(pass!=null)	{
		try {
			bytesOfPass = pass.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			passDigested = md.digest(bytesOfPass);
			BigInteger bigInt = new BigInteger(1,passDigested);
			hashPass = bigInt.toString(16);
			
			while(hashPass.length() < 32 ){
				hashPass = "0"+hashPass;
				}
			//System.out.println(hashPass);
		} catch (NoSuchAlgorithmException e1) {		
			e1.printStackTrace();
			response.sendRedirect("error.jsp?mensaje= Error interno");
		}
		
		user = new Usuario (alias, hashPass);
	
		try {
			su = new ServicioUsuarios();
			user = su.validarUsuario(user);
			
			
			URL url = new URL("http://example.com");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			
			if (user == null) {
				request.setAttribute("msgError", "Usuario o Contraseña errónea");			
			}else {
				if(session!=null) {
					session.invalidate(); //Para que la sesion sea nueva
					System.out.println("Invalidada " + session);
				}
				session = request.getSession(true);
				session.setAttribute("usuario", user);				
				request.setAttribute("msg", "Usuario logueado correctamente");
				System.out.println("Usuario logueado correctamente");
				}
			
			//request.getRequestDispatcher(URLEncoder.encode(salida, "UTF-8")).forward(request, response);
			//response.sendRedirect(salida);
			request.getRequestDispatcher(salida).forward(request, response);
					
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
	
		//System.out.println(session);

	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}



}
