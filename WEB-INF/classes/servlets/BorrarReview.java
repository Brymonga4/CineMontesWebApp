package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.Review;
import exceptions.ServiceException;
import servicios.ServicioReviews;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, borra una review determinada de la BBDD
 * Si consigue borrar la Review vuelve a "listadoPeliculas.jsp" con un mensaje nuevo
 * Lanza los errores a "error.jsp" si no consigue borrar la Review
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
@WebServlet("/BorrarReview")
public class BorrarReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id=null, titulo=null;
	private ServicioReviews sr = null;
	private Review review;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String salida ="listadoPeliculas.jsp", mensaje = "";
		id=request.getParameter("reviewId");

		review = new Review(Integer.parseInt(id));	
		//C:\Users\Nostal\eclipse-workspace-daw2\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\CineMontesD\images\peliculas

		int borrado = 0;
				
		try {

			sr = new ServicioReviews();
			
			//Borramos esta review en concreto
			borrado = sr.borrarReview(review);
	
			if (borrado>0)
				mensaje = "Se ha borrado la review correctamente";
			else
				mensaje = "No se ha podido borrar la review";
			
			
			
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
		response.sendRedirect(salida+"?mensaje="+mensaje);
		//request.getRequestDispatcher(salida).forward(request, response);
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
