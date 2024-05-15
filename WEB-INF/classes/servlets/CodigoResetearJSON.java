package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import exceptions.ServiceException;
import recursos.Recursos;
import servicios.ServicioEntradas;
import servicios.ServicioUsuarios;


/**
 * Este Servlet analiza la request del tipo Get que le llega y mediante el uso de servicios, genera un codigo de reseteo para la cuenta del Usuario
 * Si consigue generar el codigo y grabarlo en el Usuario, lo envia satisfactoriamente al correo del Usuario
 * Imprime el resultado de si ha podido enviar o no por un Objeto JSON, para no tener que recargar la pagina
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
@WebServlet("/CodigoResetearJSON")
public class CodigoResetearJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServicioUsuarios su = null; private ServicioEntradas se = null;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
		ServletContext sc=request.getServletContext();
		
		
		String strEmail = request.getParameter("email");
		boolean enviado=false; int generado = 0;
		String codReseteo;

		try {
			su = new ServicioUsuarios();
			se = new ServicioEntradas();
			
			codReseteo =Recursos.randomStringNumero(5);
			generado = su.generarCodigoReseteoUsuario(strEmail, codReseteo);
			if (generado>0) {
				se.enviarEmail(strEmail, "Codigo para resetear cuenta", "Este es su código: "+codReseteo);
				enviado = true;
			}
			JSONObject jObj2 = new JSONObject();
			jObj2.put("enviado", enviado);
			
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
