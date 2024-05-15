package servicios;


import java.util.List;
import daos.EntradaDAO;
import daos.ReviewDAO;
import daos.TransaccionesManager;
import domain.Pelicula;
import domain.Review;
import domain.Usuario;
import exceptions.DAOException;
import exceptions.ServiceException;

/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase ReviewDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see ReviewDAO
 */
public class ServicioReviews {

	public ServicioReviews (){}
	
	/**
	* Servicio para recuperar una lista de todas las Reviews existentes en la BBDD
	* @return Devuelve la lista con todas las Reviews de la BBDD
	* @throws ServiceException
	*/		
	public List<Review> todasReviews() throws ServiceException {
		List<Review> listaReviews = null;
		TransaccionesManager trans = null;
		ReviewDAO reviewdao;


		try {

			trans = new TransaccionesManager();
			
			reviewdao = trans.getReviewDAO();
			listaReviews = reviewdao.recuperarTodasReviews();

			trans.close();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return listaReviews;
	}
	
	/**
	* Servicio para recuperar una lista de todas las Reviews de una Pelicula en especifico
	* @param peli Objeto Pelicula 
	* @return Devuelve una lista de todas las Reviews de una Pelicula en especifico
	* @throws ServiceException
	*/		
	public List<Review> todasReviewsdePeli(Pelicula peli) throws ServiceException {
		List<Review> listaReviews = null;
		TransaccionesManager trans = null;
		ReviewDAO reviewdao;


		try {

			trans = new TransaccionesManager();
			
			reviewdao = trans.getReviewDAO();
			listaReviews = reviewdao.recuperarReviewsdePeli(peli);

			trans.close();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return listaReviews;

	}	
	
		

	/**
	* Servicio para insertar una Review
	* Realiza una transaccion por Review
	* @param r Objeto Review que queremos insertar
	* @throws ServiceException
	*/	
	public void escribirReview(Review r) throws ServiceException{
		TransaccionesManager trans = null;
		ReviewDAO reviewdao;
		
		try {

			trans = new TransaccionesManager();
			
			reviewdao = trans.getReviewDAO();
			
			reviewdao.escribirReview(r);
			
			//trans.close();
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		
	}
	
	/**
	* Servicio para saber si un Usuario ha escrito anteriormente, una Review de una Pelicula en especifico
	* @param user Objeto Usuario
	* @param peli Objeto Pelicula
	* @return Devuelve un True si el Usuario ha escrito ya una Review de esa Pelicula y False si no
	* @throws ServiceException
	*/	
	public boolean usuarioEscribioReviewenPeli(Usuario user, Pelicula peli) throws ServiceException{
		TransaccionesManager trans = null;
		ReviewDAO reviewdao; boolean escribio;
		
		try {

			trans = new TransaccionesManager();
			
			reviewdao = trans.getReviewDAO();			
			escribio=reviewdao.usuarioEscribioReviewenPeli(user, peli);
			
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		return escribio;
	}
	
	/**
	* Servicio para saber si un Usuario ha visto o no Una pelicula, buscandolo a partir de las entradas que ha comprado
	* @param user Objeto Usuario
	* @param peli Objeto Pelicula
	* @return Devuelve True si el Usuario ha visto la pelicula y False si no la ha visto
	* @throws ServiceException
	*/		
	public boolean haVistoElUsuarioEstaPeli(Usuario user, Pelicula peli) throws ServiceException{
		TransaccionesManager trans = null;
		EntradaDAO entradadao; boolean laHaVisto;
		
		try {

			trans = new TransaccionesManager();
			
			entradadao = trans.getEntradaDAO();			
			laHaVisto=entradadao.haVistoElUsuarioEstaPeli(user, peli);
			
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		return laHaVisto;
	}		
	
	/**
	* Servicio para calcular la Valoracion Media de las Reviews de una Pelicula en especifico
	* @param peli Objeto Pelicula
	* @return Devuelve un double con la cantidad calculada, y si no hay Reviews escritas aun, devuelve 0.0
	* @throws ServiceException
	*/		
	public double valoracionMediaDePelicula(Pelicula peli) throws ServiceException{
		TransaccionesManager trans = null;
		ReviewDAO reviewdao; double valorMedio;
		
		try {

			trans = new TransaccionesManager();
			
			reviewdao = trans.getReviewDAO();			
			valorMedio=reviewdao.valoracionMediaDePelicula(peli);
			
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		return valorMedio;
	}	
	
	/**
	* Servicio para borrar todas las Reviews de una Pelicula por su Id
	* Realiza la transaccion si borrado es mayor que 0
	* @param p Objeto Pelicula con su Id definida
	* @return Devuelve el numero de reviews que ha podido borrar y 0 si no ha podido borrar ninguna
	* @throws ServiceException
	*/		
	public int borrarReviewsDePeli(Pelicula p) throws ServiceException{
		TransaccionesManager trans = null;
		ReviewDAO reviewdao; int borrado = 0;
		try {

			trans = new TransaccionesManager();			
			reviewdao = trans.getReviewDAO();			
			borrado=reviewdao.borrarReviewsDePelicula(p);
			
			if (borrado>0)
				trans.closeCommit();

			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		return borrado;
	}
	
	/**
	* Servicio para borrar una Review por su Id
	* Realiza la transaccion si borrado es mayor que 0
	* @param r Objeto Review con su Id definida
	* @return Devuelve 1 si ha podido borrar la Review y 0 si no
	* @throws ServiceException 
	*/	
	public int borrarReview(Review r) throws ServiceException{
		TransaccionesManager trans = null;
		ReviewDAO reviewdao; int borrado = 0;
		try {

			trans = new TransaccionesManager();			
			reviewdao = trans.getReviewDAO();			
			borrado=reviewdao.borrarReview(r);
			
			if (borrado>0)
				trans.closeCommit();

			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		return borrado;
	}	
		
	

}


