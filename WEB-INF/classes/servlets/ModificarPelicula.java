package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;

import util.Fecha;
import util.FormMultiPart;
import exceptions.*;
import objetos.Pais;
import recursos.Recursos;
import servicios.ServicioPeliculas;
import domain.*;

/**
 * Este Servlet analiza la request del tipo Get que le llega y mediante el uso de servicios, modifica con concurrencia una Pelicula en la BBDD
 * Crea una Pelicula a partir de los datos introducidos en el formulario y lo intenta insertar en el lugar de la ya existente
 * Al utilizar los atributos del contexto para las peliculas, recargamos el atributo del contexto una vez modifiquemos la pelicula
 * Una vez modificada, redirige el usuario a "listadoPeliculas.jsp" con un mensaje satisfactorio
 * Lanza los errores a "error.jsp"
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/ModificarPelicula")
public class ModificarPelicula extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String titulo, titulo_Orig, fecha_Estreno, pais, generos, edadRec, trailerYT = null;
	private String actores, directores, guionistas, productores, sipnopsis, imagen = null;
	private Integer duracion, idAuto = null; private String mensaje = "";
	private int soporteDigital, soporte3D, versionEsp, versionOrig = 0;
       
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
		ServicioPeliculas schp = null;
		Pelicula peli = null; Date fechaEstreno = null; Pais paisObj = null;
		int modificado=0;
		byte[] bytes = null;																					// /images/peliculas
		String path = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadFiles"));

		try {
			
			FormMultiPart datos;
				try {
					datos = new FormMultiPart(path,request);
				} catch (FileUploadException e) {
					throw new ServiceException("Fallo al leer el request",e);
					}
//Parámetros Form
				
				titulo = datos.getCampoForm("titulo"); bytes = titulo.getBytes(StandardCharsets.ISO_8859_1);	titulo = new String (bytes, StandardCharsets.UTF_8 );
				titulo_Orig = datos.getCampoForm("titulo_Orig");	bytes = titulo_Orig.getBytes(StandardCharsets.ISO_8859_1);	titulo_Orig = new String (bytes, StandardCharsets.UTF_8 );			
				fecha_Estreno = datos.getCampoForm("fecha_Estreno");				
				pais = datos.getCampoForm("pais");	bytes = pais.getBytes(StandardCharsets.ISO_8859_1);	pais = new String (bytes, StandardCharsets.UTF_8 );
				paisObj = new Pais(pais);
				generos = datos.getCampoForm("generos"); bytes = generos.getBytes(StandardCharsets.ISO_8859_1);	generos = new String (bytes, StandardCharsets.UTF_8 );
					
				try {
					fechaEstreno=Fecha.convertirADate(fecha_Estreno, "yyyy-MM-dd");
				} catch (ParseException e1) {	
					e1.printStackTrace();
				}
				
				edadRec = datos.getCampoForm("edadRec");
				trailerYT = datos.getCampoForm("trailerYT");
				imagen = "whatever";
				
				actores = datos.getCampoForm("actores"); bytes = actores.getBytes(StandardCharsets.ISO_8859_1);	actores = new String (bytes, StandardCharsets.UTF_8 );
				directores = datos.getCampoForm("directores"); bytes = directores.getBytes(StandardCharsets.ISO_8859_1);	directores = new String (bytes, StandardCharsets.UTF_8 );
				guionistas = datos.getCampoForm("guionistas"); bytes = guionistas.getBytes(StandardCharsets.ISO_8859_1);	guionistas = new String (bytes, StandardCharsets.UTF_8 );
				productores = datos.getCampoForm("productores"); bytes = productores.getBytes(StandardCharsets.ISO_8859_1);	productores = new String (bytes, StandardCharsets.UTF_8 );
						
				duracion = Integer.parseInt(datos.getCampoForm("duracion"));
				sipnopsis = datos.getCampoForm("sipnopsis"); bytes = sipnopsis.getBytes(StandardCharsets.ISO_8859_1);	sipnopsis = new String (bytes, StandardCharsets.UTF_8 );
							
				if (datos.getCampoForm("soporteDigital")==null)
					soporteDigital = 1;
				if (datos.getCampoForm("soporte3D")!=null)
					soporte3D = 1;
				if (datos.getCampoForm("versionEsp")==null)
					versionEsp = 1;
				if (datos.getCampoForm("versionOrig")!=null)
					versionOrig = 1;		
				
				idAuto = new Integer (datos.getCampoForm("id"));
				
				//Validar Campos

				peli = new Pelicula(idAuto, titulo, titulo_Orig, fechaEstreno, paisObj, generos,
						actores, directores, guionistas, productores, duracion, sipnopsis, 
						soporteDigital, soporte3D, versionOrig, versionEsp, imagen, trailerYT, edadRec );
				
				schp = new ServicioPeliculas();
				//Sin concurrencia
				//modificado = schp.modificarPelicula(peli);				
				//Con concurrencia
				Pelicula peliInicial = schp.pelicula(idAuto);
				modificado = schp.modificarPeliculaConcurrente(peli, peliInicial);
								
				//Recargando el contenido del contexto				
				if (!request.getServletContext().getAttribute("listaPeliculas").equals(schp.todasPeliculas()))
					 request.getServletContext().setAttribute("listaPeliculas", schp.todasPeliculas());
				
//Imágenes
				int numfilesubidos;
				String titulojpg= Recursos.limpiarString(peli.getTitulo())+".jpg";
				try {
					
					numfilesubidos = datos.SubirFicheros("estrenos_"+titulojpg, titulojpg);
					
				} catch (Exception e) {
					throw new ServiceException("Fallo al subir ficheros",e);
				}
				
				
				
				if (modificado>0)
					mensaje = "Se ha modificado la pelicula correctamente";
				else
					mensaje = "No se ha podido modificar la pelicula";
				

				response.sendRedirect("listadoPeliculas.jsp?mensaje="+mensaje);
				
				
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
