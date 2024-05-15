package servicios;

import java.util.List;

import daos.*;
import domain.*;
import exceptions.DAOException;
import exceptions.ServiceException;
/**
 * Esta clase contiene los diferentes Servicios (relacionados con las clases ButacaDAO y SalaDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see ButacaDAO
 * @see SalaDAO 
 */
public class ServicioInsertarButacasSalas {
	
	public ServicioInsertarButacasSalas () {}
	/**
	* Servicio que recupera todas las Salas de la BBDD
	* @return La lista de todas las Salas de la BBDD
	* @throws ServiceException
	*/	
	public List<Sala> recuperarTodasSalas() throws ServiceException{
		TransaccionesManager trans = null;
		SalaDAO saladao = null; 
		List <Sala> listaSalas = null;
		try {

			trans = new TransaccionesManager();			
			saladao = trans.getSalaDAO();
	
			listaSalas = saladao.recuperarTodasSalas();
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
		return listaSalas;
	}
	
	
	/**
	* Servicio que genera una distribucion predeterminada
	* @return String con la distribucion generada
	* @see ServicioButacas
	*/
	public String generarDistribucion() {
		//Sala con 12 Filas y 253 butacas
		final String FILANORMAL = "NNNNNN-NNNNNNNNNN-NNNNNN#"; //Todas menos las premium y especiales
		final String FILACONPREMIUM = "NNNNNN-PPPPPPPPPP-NNNNNN#"; //Premium en fila 5-7 inclusives, y numeros 7-16 inclusives
		final String FILAESPECIAL = "EEE-EEEEE-EEE#"; //Solo la �ltima fila 12, y adem�s solo de 11 asientos	
		final int NUMFILAS = 12;
		
		String distribucion = "";
		
		for (int i = 1; i<=NUMFILAS; i++) {
			distribucion +=  FILANORMAL;
			if(i>4&&i<=7) 
				distribucion +=  FILACONPREMIUM;
			if(i==12) 
				distribucion +=  FILAESPECIAL;
			
			System.out.println(distribucion);
		}

		return distribucion;
		
	}
	
	/**
	* Servicio que recupera unicamente la distribucion de una Sala predeterminada
	* @return El String con la distribucion
	* @throws ServiceException
	*/		
	public String recuperarDistribuciondeSala() throws ServiceException {
		TransaccionesManager trans = null;
		SalaDAO saladao = null; 
		Sala sala = null;
		
		try {
			trans = new TransaccionesManager();			
			saladao = trans.getSalaDAO();
			
			sala=saladao.recuperarSala(1);
			
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
		
		return sala.getDistribucion();
	}
	
	/**
	* Servicio que recupera una Sala por su Id
	* @param sala Objeto Sala con su Identificador definido
	* @return La Sala recuperada
	* @throws ServiceException
	*/		
	public Sala recuperarSala(Sala sala) throws ServiceException {
		TransaccionesManager trans = null;
		SalaDAO saladao = null; 


		try {

			trans = new TransaccionesManager();
			
			saladao = trans.getSalaDAO();
			sala = saladao.recuperarSala(sala.getIdSala());

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
		return sala;

	}	
	
	/**
	* Servicio que recupera una Sala por su Nombre
	* @param sala Objeto Sala con su Nombre definido
	* @return La Sala recuperada
	* @throws ServiceException
	*/	
	public Sala recuperarSalaPorNombre(Sala sala) throws ServiceException {
		TransaccionesManager trans = null;
		SalaDAO saladao = null; 


		try {

			trans = new TransaccionesManager();
			
			saladao = trans.getSalaDAO();
			sala = saladao.recuperarSala(sala.getNombre());

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
		return sala;

	}

}
