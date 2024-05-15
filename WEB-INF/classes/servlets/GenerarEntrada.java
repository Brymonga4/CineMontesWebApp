package servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.*;
import exceptions.ServiceException;
import objetos.EntradaCompleta;
import servicios.ServicioEntradas;
import servicios.ServicioFunciones;

/**
 * Este Servlet analiza la request del tipo Post que le llega y mediante el uso de servicios, escribe genera una Entrada en la BBDD
 * El request llegara al servlet con varios atributos: Una funcion, una Lista de Butacas y un valor Double PrecioTotal, recuperaremos todos estos valores
 * Creamos un Usuario con los datos del formulario que le llega al request
 * Una vez recuperados todos estos datos, creamos un Objeto nuevo llamado EntradaCompleta en el que mas adelante añadiremos todas las entradas que vamos a poder comprarç
 * A continuacion, iteramos por las Butacas de listB y generamos una Entrada por cada Butaca de la lista
 * Si no hay ningun problema y conseguimos generar satisfactoriamente la entrada, la añadimos al Objeto que mencionamos anteriormente, EntradaCompleta
 * Si no hemos conseguido generar la entrada, la añadiremos a otra lista llamada listNoCompradas
 * Despues nos aseguramos que hay al menos 1 elementos en la lista de EntradaCompleta, y a partir de estas entradas es cuando generamos los QR y sus Entradas pertinentes
 * Finalizamos enviando al Usuario el PDF de las entradas y si ha habido alguna que no ha podido comprar, se lo hacemos saber por un mensaje en "gracias.jsp"
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/GenerarEntrada")
public class GenerarEntrada extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	private String idFuncion;
	private String strAlias; private String strEmail;	
	private List<Butaca> listB = null;	private List<Entrada> listNoCompradas = null;	
	private ServicioEntradas se = null;
	private ServicioFunciones sf = null;
	private Reserva reserva; private Entrada entrada;private Funcion fun;
	private Double precioTotal; private EntradaCompleta entradaC;
    public GenerarEntrada() {

    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion=request.getSession(false); 
		
		fun = new Funcion(); listB = new ArrayList<Butaca>(); listNoCompradas  = new ArrayList<Entrada>();
		precioTotal = 0.0; String mensaje ="";
		
		fun =(Funcion) sesion.getAttribute("funcion");
		listB = (List <Butaca>) sesion.getAttribute("listaB");	
		precioTotal = (Double) sesion.getAttribute("precioTotal");
		
		strAlias = request.getParameter("alias");
		strEmail = request.getParameter("email");
		
		entradaC = new EntradaCompleta(); 
		
		//Directorios de imagenes
		final String STRIMAGE = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadFiles"));
		final String STRQRPATH = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadQR"));
		File QRFileDirectory = new File(STRQRPATH); QRFileDirectory.mkdir();
		final String STRPDF = getServletContext().getRealPath(getServletContext().getInitParameter("dirUploadPDFs"));
		File pdfFileDirectory = new File(STRPDF); pdfFileDirectory.mkdir();
		Path pathQR;
	
		//System.out.println("SERVLET GENERAR ENTRADA");
		
	if (strAlias != null && strEmail!= null && fun!=null && listB !=null && precioTotal !=null) {	
		try{
			
			if (fun==null) {
				fun = new Funcion (Integer.parseInt(idFuncion));
				sf = new ServicioFunciones();
				fun = sf.recuperarFun(fun);
			}
			
			se = new ServicioEntradas();
			for (Butaca b: listB) {
				reserva = new Reserva(); reserva.setEmail(strEmail);
			    entrada = new Entrada (); 
			    entrada.setFuncion(fun); entrada.setButaca(b);		    
				
				try {
					entrada = se.generarEntrada(entrada, reserva);
					if (entrada!= null) {					
						System.out.println("Se insertó "+ entrada);
						entradaC.getListEntradas().add(entrada);
					}
				} catch (ServiceException e) {
					System.out.println("Alguien ha comprado la entrada"+ entrada + " antes");
					listNoCompradas.add(entrada);
					e.printStackTrace();
					//throw new ServiceException ("Alguien ");
				}
			
			}
			
			entradaC.setPrecioTotal(precioTotal);
			
			//Entradas que se han logrado comprar realmente
			System.out.println("Entrada Completa");
			
		if (entradaC.getListEntradas().size()>0) {
			
			for (Entrada entC: entradaC.getListEntradas()) {
				//Genero QR en la carpeta	
				String newQR= STRQRPATH +"\\"+ entC.getReserva().getId() +"-QRCODE.png";
				pathQR = FileSystems.getDefault().getPath(newQR);
				se.generarQR(entC.getReserva().getId() , pathQR);	
				//System.out.println(entC); 
			}
			
			//Genero PDF de las entradas que realmente se han comprado
			
				String pdfPath = se.generarPDFs(entradaC, STRQRPATH, STRIMAGE, STRPDF);
				//System.out.println(pdfPath);
				//Destinatario, Asunto, Cuerpo, PathdelPDF que voy a enviar
				se.enviarEmailConEntradas(strEmail, "Entradas Cine Monte", "Gracias por su compra.", pdfPath);	
				
				 mensaje = "Gracias por su compra, se han enviado las entradas a su correo.";
				 if (listNoCompradas.size()>0) {
					 mensaje = "No pudimos efectuar la compra de "+listNoCompradas.size()+" entrada(s). Se han enviado las demas a su correo.";
				 }
				 
			}else {
				mensaje = "Alguien compro la entrada antes que Ud, intentelo de nuevo.";
			}
		response.sendRedirect("gracias.jsp?mensaje="+mensaje);
	        
		} catch (ServiceException e1) {
			if(e1.getCause()==null){
				response.sendRedirect("error.jsp?mensaje="+e1.getMessage());// para usuario final
				//devolverPaginaError(response,e.getMessage());
				//System.out.println(e.getMessage());//Error Lógico para usuario
			}else{
				// error interno
				getServletContext().log("Error  NO ESPERADO  por la aplicacion en el servlet"+
				request.getServletPath());// esto lo escribe en el diario log  localhost
		
				e1.printStackTrace();// esto lo escribe en el diario log  tomcat7-stderr
				response.sendRedirect("error.jsp?mensaje= Error interno");// para usuario final
			}
		}		
        
        
	}   
		//response.sendRedirect("butacasFuncion.jsp?"+"idFun="+fun.getId());
		//response.sendRedirect("comprarEntradas.jsp");
		//request.getRequestDispatcher("comprarEntradas.jsp").forward(request, response);

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}	
	

}
