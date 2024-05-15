package listeners;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import exceptions.ServiceException;
import servicios.ServicioPeliculas;

/**
 * Application Lifecycle Listener implementation class ContextoListas
 *
 */
@WebListener
public class ContextoPeliculas implements ServletContextListener, ServletContextAttributeListener {

    /**
     * Default constructor. 
     */
    public ContextoPeliculas() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	ServletContext contexto = arg0.getServletContext();
    	ServicioPeliculas schp = null;
    	schp = new ServicioPeliculas();
    	List lista = null;
    		try {
    			lista=schp.todasPeliculas();
    			
				contexto.setAttribute("listaPeliculas", lista);
			} catch (ServiceException e) {
				contexto.setAttribute("error", "Error interno");
				e.printStackTrace();
			}
    		
    	
    	System.out.println("Lista de pelicula añadidas al contexto");
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0) {

    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0) {
       System.out.println("Atributo modificado");
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0) {
        System.out.println("Atributo borrado");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
