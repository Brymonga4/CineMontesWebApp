package servlets;

import java.io.IOException;
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
import servicios.ServicioFunciones;


/**
 * Este Servlet analiza la request del tipo Post que le llega y va introduciendo atributos en el request para poder utilizarlos despues en otro Servlet
 * Crea una Funcion a partir de los datos introducidos en el formulario y recuperamos la Funcion completa de la BBDD
 * Tambien creamos una lista de Butacas a partir del Array de Strings que hemos recuperado 
 * Finalmente calculamos el valor total de todas las butacas
 * Añadimos todos estos valores como Atributos al request y lo redirigimos a "comprarEntradas.jsp" donde con otro Servlet ya trataremos estos atributos
 * Lanza los errores a "error.jsp" 
 * @author Bryan Montesdeoca 
 * @version 1.0
 */

/**
 * Servlet implementation class 
 */
@WebServlet("/PreCompra")
public class PreCompra extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	private String idFuncion;
	private String[] liststrB; private String[] strB;
	private Funcion fun; private Butaca butaca;
	private List<Butaca> listB = null;
	private ServicioFunciones sf = null;
	private Double precioTotal;
    public PreCompra() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false); 
		
		liststrB = request.getParameterValues("reservado");
		idFuncion = request.getParameter("idFun");	
		
		fun = (Funcion)session.getAttribute("funcion");
		
		//System.out.println("SERVLET PRECROMPA");
	if (liststrB != null && idFuncion!= null && fun!=null) {
		try{
			if (fun==null) {
				fun = new Funcion (Integer.parseInt(idFuncion));
				sf = new ServicioFunciones();
				fun = sf.recuperarFun(fun);
			}
			
			listB = new ArrayList<Butaca>();
			precioTotal = 0.0;
			
			for (String s: liststrB) {
				strB = s.split("-");
				butaca = new Butaca(Integer.parseInt(strB[0]),fun.getSala(),Integer.parseInt(strB[2]),Integer.parseInt(strB[1]),Double.parseDouble(strB[3]));
				precioTotal += butaca.getTipo();
				listB.add(butaca);
			}
			
			precioTotal = precioTotal * fun.getPrecio();
			precioTotal = Math.round(precioTotal*100.0)/100.0;
			
			session.setAttribute("listaB", listB);
			session.setAttribute("funcion", fun);
			session.setAttribute("precioTotal", precioTotal);
		
			request.getRequestDispatcher("comprarEntradas.jsp").forward(request, response);
			
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
        
        
		//response.sendRedirect("butacasFuncion.jsp?"+"idFun="+fun.getId());
		//response.sendRedirect("comprarEntradas.jsp");
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}	
	

}
