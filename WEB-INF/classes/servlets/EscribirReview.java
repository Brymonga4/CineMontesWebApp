package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.Pelicula;
import domain.Review;
import domain.Usuario;
import exceptions.ServiceException;
import servicios.ServicioReviews;
import util.Fecha;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, escribe una Review de una Pelicula en la BBDD
 * Crea una Review a partir de los datos introducidos en el formulario y lo intenta insertar 
 * Se asegura mediante servicio que el Usuario no haya escrito ninguna Review para la misma peli antes
 * Una vez insertado, redirige el usuario a "peli.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/EscribirReview")
public class EscribirReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id, usuario, titulo, opinion = null;
	private Integer valoracion;
	private Pelicula peli; private Usuario user; private Review r = null; 
	private ServicioReviews sr = null;
	private Timestamp fecha;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String salida ="peli.jsp", mensaje = "";

		id = request.getParameter("id");
		usuario = request.getParameter("usuario"); 
		titulo = request.getParameter("titulo"); 
		opinion = request.getParameter("opinion"); 
		valoracion = Integer.parseInt(request.getParameter("valoracion"));
		//System.out.println(titulo);
		peli = new Pelicula(Integer.parseInt(id));	
		user = new Usuario(usuario);
		
		fecha = Fecha.fechaActual();
		//System.out.println("fecha "+fecha);
		r = new Review (null, peli, user, titulo, opinion, valoracion, fecha);
		//System.out.println("getFecha "+r.getFecha());
		
		try {
			sr = new ServicioReviews();
			if(sr.usuarioEscribioReviewenPeli(user, peli)) {
				mensaje = "Este usuario ya ha escrito una review de esta pelicula";
			}else {
				sr.escribirReview(r);
				mensaje = "Review escrita correctamente";
			}

			//System.out.println(mensaje);
			response.sendRedirect(salida+"?id="+id+"&mensaje="+mensaje);			
			
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
