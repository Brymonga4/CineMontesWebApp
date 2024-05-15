package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import exceptions.ServiceException;
import servicios.ServicioUsuarios;

/**
 * Este Servlet analiza la request del tipo Get que le llega y mediante el uso de servicios, determina si un String Alias esta disponible o no en la BBDD
 * Imprime finalmente su respuesta mediante un Objeto JSON, para no tener que recargar la pagina cuando se haga la consulta a la BBDD
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

@WebServlet("/AliasJSON")
public class AliasJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServicioUsuarios su = null; 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String strAlias = request.getParameter("alias");
		boolean disponible;

		try {
			su = new ServicioUsuarios();
			
			disponible = su.aliasDisponible(strAlias);
//			System.out.println(disponible);
//			jObj = new JSONObject(disponible);
			
			JSONObject jObj2 = new JSONObject();
			jObj2.put("disponible", disponible);
			

			String booleanJsonString2 = jObj2.toString();

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(booleanJsonString2);
			out.flush();			

			
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
