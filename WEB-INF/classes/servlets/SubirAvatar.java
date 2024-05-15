package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;

import util.FormMultiPart;
import exceptions.*;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, sube una Imagen con el nombre del Usuario a la carpeta de la App
 * Usaremos esta imagen en "pefil.jsp" como avatar si existe, si no, mostrara una imagen de una avatar generico
 * Si consigue subirla, simplemente redirige al usuario a "perfil.jsp"
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class SubirArchivo
 */
@WebServlet("/SubirAvatar")
public class SubirAvatar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String alias = null;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		

		byte[] bytes = null;
																						// /images/avatar
		String path = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadAvatar"));
		
		
		try {
			
			FormMultiPart datos;
				try {
					datos = new FormMultiPart(path,request);
				} catch (FileUploadException e) {
					throw new ServiceException("Fallo al leer el request",e);
					}
//Parámetros Form

				alias = datos.getCampoForm("alias"); bytes = alias.getBytes(StandardCharsets.ISO_8859_1);	alias = new String (bytes, StandardCharsets.UTF_8 );

				//Validar Campos
//Imágenes
				int numfilesubidos;
				try {
					
					numfilesubidos = datos.SubirFicheros(alias+"-avatar.jpg"); //Ctrl shift G
					
					
				} catch (Exception e) {
					throw new ServiceException("Fallo al subir ficheros",e);
				}
				
				response.sendRedirect("perfil.jsp");
				
				//System.out.println(numfilesubidos); System.out.println(path);
			} catch (ServiceException|DomainException e) {
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
