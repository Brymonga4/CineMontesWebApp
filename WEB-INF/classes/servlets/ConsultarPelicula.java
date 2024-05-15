package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Pelicula;
import servicios.ServicioPeliculas;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, consulta la Existencia de una Pelicula en la Lista del Contexto de la App
 * Si la encuentra, añade la Pelicula como atributo al request y lo redirige a "peli.jsp"
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
/**
 * Servlet implementation class ConsultarPelicula
 */
@WebServlet("/ConsultarPelicula")
public class ConsultarPelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id=null;
	private ServicioPeliculas sp = null;
	private Pelicula peli;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		ServletContext sc=request.getServletContext();
		String salida ="peli.jsp";
		sp = new ServicioPeliculas();
		id=request.getParameter("id");

		peli=sp.peliculaContexto(Integer.parseInt(id), (List <Pelicula>)(sc.getAttribute("listaPeliculas")));
		request.setAttribute("pelicula", peli);
		
		request.getRequestDispatcher(response.encodeURL(salida)).forward(request, response);
		//System.out.println("Jsp al que me estoy redirigiendo "+salida);
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	

}
