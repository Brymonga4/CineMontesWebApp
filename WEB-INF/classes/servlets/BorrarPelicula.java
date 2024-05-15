package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.Pelicula;
import exceptions.ServiceException;
import recursos.Recursos;
import servicios.ServicioPeliculas;
import servicios.ServicioReviews;


/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, borra una Pelicula determinada de la BBDD
 * Ademas nos aseguramos de borrar las imagenes relacionadas con esa pelicula, para no tener imagenes que no necesitemos en la carpeta
 * Al estar Usando un atributo del contexto para las Peliculas, recargo este atributo en el contexto, una vez el borrado haya sido exitoso
 * Lanza los errores a "error.jsp" si no consigue borrar la Pelicula
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

@WebServlet("/BorrarPelicula")
public class BorrarPelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id=null, titulo=null;
	private ServicioPeliculas sp = null; private ServicioReviews sr = null;
	private Pelicula peli;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String salida ="listadoPeliculas.jsp", mensaje = "";
		id=request.getParameter("id");
		titulo = request.getParameter("titulo");
		peli = new Pelicula(Integer.parseInt(id));	
		//C:\Users\Nostal\eclipse-workspace-daw2\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\CineMontesD\images\peliculas
		String path = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadFiles"));
		File borrarFile = null;
		int borrado = 0;
				
		try {
			sp = new ServicioPeliculas();
			sr = new ServicioReviews();
			
			//Borramos todas las review de esa peli en concreto
			sr.borrarReviewsDePeli(peli);
			borrado=sp.borrarPelicula(peli);
			
			//Vamos a borrar también las fotos de Main y Banner
			String titulojpg= Recursos.limpiarString(titulo)+".jpg";
			borrarFile = new File (path, titulojpg); borrarFile.delete();			
			borrarFile = new File (path, "estrenos_"+titulojpg); borrarFile.delete();		
			
			
			if (!request.getServletContext().getAttribute("listaPeliculas").equals(sp.todasPeliculas()))
				 request.getServletContext().setAttribute("listaPeliculas", sp.todasPeliculas());
			
			if (borrado>0)
				mensaje = "Se ha borrado la pelicula correctamente";
			else
				mensaje = "No se ha podido borrar la pelicula";
			
			response.sendRedirect(salida+"?mensaje="+mensaje);
			
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
		
		
		//request.getRequestDispatcher(salida).forward(request, response);
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
