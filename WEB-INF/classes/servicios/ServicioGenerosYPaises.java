package servicios;


import java.util.List;
import daos.PaisGeneroDAO;
import daos.TransaccionesManager;
import exceptions.DAOException;
import exceptions.ServiceException;

/**
 * Esta clase contiene los diferentes metodos relacionados con las tablas Pais y Genero, sin relacion directa a la BBDD
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
public class ServicioGenerosYPaises {

	public ServicioGenerosYPaises (){}
	/**
	* Servicio para recuperar todos los Paises
	* @return Devuelve una Lista de Strings con todos los Paises
	* @throws ServiceException
	*/	
	public List<String> todosPaises() throws ServiceException {
		List<String> listaPaises = null;
		TransaccionesManager trans = null;
		PaisGeneroDAO paisgeneroDAO;


		try {

			trans = new TransaccionesManager();
			paisgeneroDAO = trans.getPaisGeneroDAO();			
			listaPaises=paisgeneroDAO.recuperarPaises();

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
		return listaPaises;

	}
	/**
	* Servicio para recuperar todos los Generos
	* @return Devuelve una Lista de Strings con todos los Generos
	* @throws ServiceException
	*/		
	public List<String> todosGeneros() throws ServiceException {
		List<String> listaGen = null;
		TransaccionesManager trans = null;
		PaisGeneroDAO paisgeneroDAO;


		try {

			trans = new TransaccionesManager();
			paisgeneroDAO = trans.getPaisGeneroDAO();			
			listaGen=paisgeneroDAO.recuperarGeneros();

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
		return listaGen;

	}	
	
	
	
}


