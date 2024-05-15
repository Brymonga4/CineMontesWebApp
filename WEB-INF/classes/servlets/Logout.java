package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Este Servlet analiza la request del tipo Get que le llega y mediante el uso de servicios, invalida la sesion existente y hace un Logout al Usuario
 * A continuacion lo redirige a "index.jsp"
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session=request.getSession(false); 		
		String salida ="index.jsp";
		
		if(session!=null) {
			session.invalidate(); //Para que la sesion sea nueva
			System.out.println("Invalidada " + session);
		}
		
		//System.out.println(session);
		response.sendRedirect(salida);
		
	}
	


}
