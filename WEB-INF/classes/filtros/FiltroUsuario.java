package filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Usuario;
/**
 * Esta clase contiene el filtro para comprobar si el Usuario que esta visitando los enlaces tiene una Sesion iniciada con un Usuario Valido o no
 * Si no lo tiene, lo redirige al index de la aplicacion
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Usuario
 */
/**
 * Servlet Filter implementation class FiltroAdmin
 */
@WebFilter("/FiltroUsuario")
public class FiltroUsuario implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltroUsuario() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Usuario user = new Usuario();
		user = (Usuario)(request.getSession().getAttribute("usuario"));
				
		if (user!=null) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher(response.encodeRedirectURL("/index.jsp")).forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
