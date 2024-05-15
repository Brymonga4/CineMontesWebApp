package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ServiceException;
import recursos.Recursos;
import servicios.ServicioUsuarios;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, resetea la Password de un Usuario en la BBDD
 * Crea un Usuario a partir de los datos introducidos en el formulario y lo intentamos insertar en el lugar del ya existente
 * El servicio se asegura que el codigo de Reseteo sea correcto, y permite resetear la Password
 * Si el codigo de Reseteo es incorrecto, devolvera al Usuario a "index.jsp" con un mensaje de error y tendra que repetir el proceso de nuevo
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/ResetearPass")
public class ResetearPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String pass, email= null;
	private String codigo; 
	private byte[] bytesOfPass; private byte[] passDigested;  private MessageDigest md = null; private String hashPass = null;
	private ServicioUsuarios su = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mensaje = "";
		int modificado = 0;
		
		email = request.getParameter("emailhidden"); 
		pass = request.getParameter("pass"); 
		codigo = request.getParameter("codigo"); 
		
	if (email!= null && pass!= null  && codigo!=null) {
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
			
		try {
			su = new ServicioUsuarios();
			modificado = su.cambiarPassUsuario(hashPass, email, codigo);			
			
			if(modificado>0) {
				String random = Recursos.randomStringNumero(5);
				//Vuelto a generar uno nuevo, para que el usuario no pueda reusar el anterior
				su.generarCodigoReseteoUsuario(mensaje, random); 				
				mensaje = "Contraseña Modificada Correctamente";
				request.setAttribute("msg", mensaje);
			}
			else {
				mensaje = "Código incorrecto, intentelo nuevamente más tarde.";
				request.setAttribute("msgError", mensaje);
			}
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
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
