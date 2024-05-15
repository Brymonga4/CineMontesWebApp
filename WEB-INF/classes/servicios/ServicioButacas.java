package servicios;

import java.util.ArrayList;
import java.util.List;

import daos.*;
import domain.*;
import exceptions.DAOException;
import exceptions.ServiceException;
/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase ButacaDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see ButacaDAO
 */
public class ServicioButacas {
	
	public ServicioButacas () {}
	/**
	* Servicio para insertar Butacas automaticamente a partir de la distribucion de una Sala predeterminada
	* @throws ServiceException
	*/		
	public void insertarButacasenSalas() throws ServiceException {
		List <Sala> listaSalas = null;
		TransaccionesManager trans = null;
		String distribucion = ""; //String [] filas = null;
		SalaDAO saladao; ButacaDAO butacadao;
		int ctdFila = 1, ctdButaca = 1;
		Butaca butaca; 
		Sala sala = new Sala(2);
		
		try {

			trans = new TransaccionesManager();			
			saladao = trans.getSalaDAO();
			butacadao = trans.getButacaDAO();		
			listaSalas = saladao.recuperarTodasSalas();
			
			for (Sala s : listaSalas) {
				distribucion=s.getDistribucion();
				//System.out.println(distribucion);	
				
				//NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#
				//NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#
				//NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#
				//EEE-EEEEE-EEE#
				
				for (int i = 0; i < distribucion.length()&&ctdFila<=12; i++) {
					Character butacaChar = distribucion.charAt(i);
					butaca = null;			
					switch(butacaChar) {
					case 'N' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 1.0); break;
					case 'P' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 1.75); break;
					case 'E' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 0.5); break;
					case '#' : System.out.println("Fila "+ctdF	ila); ctdButaca = 1; ctdFila++;  break;
					}
					if (butaca!=null) {					
						butacadao.insertarButaca(butaca);
						ctdButaca++;
						trans.commit();
						//System.out.println("Butaca insertada " + butaca);
					}
				}
					
			}
			
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
		
	}

	/**
	* Servicio para insertar Butacas a partir de la distribucion de una Sala especifica
	* Usa un for y un switch para navegar por el String de la Distribucion de la Sala
	* Solo se ejecutara una vez por cada vez que se cree una Sala nueva
	* Hace una transaccion por cada butaca insertada
	* @param sala Objeto Sala del que queremos insertar las Butacas
	* @throws ServiceException
	*/		
	public void insertarButacasenSala(Sala sala) throws ServiceException {
		TransaccionesManager trans = null;
		String distribucion = "";
		ButacaDAO butacadao;
		int ctdFila = 1, ctdButaca = 1;
		Butaca butaca; 
		
		try {

			trans = new TransaccionesManager();			
			butacadao = trans.getButacaDAO();		
		
				distribucion=sala.getDistribucion();
				//System.out.println(distribucion);				
				//NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#
				//NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#
				//NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#
				//EEE-EEEEE-EEE#
				
				for (int i = 0; i < distribucion.length()&&ctdFila<=12; i++) {
					Character butacaChar = distribucion.charAt(i);
					butaca = null;			
					switch(butacaChar) {
					case 'N' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 1.0); break;
					case 'P' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 1.75); break;
					case 'E' : 	butaca = new Butaca (null, sala, ctdButaca, ctdFila, 0.5); break;
					case '#' : System.out.println("Fila "+ctdFila); ctdButaca = 1; ctdFila++;  break;					
					}
					if (butaca!=null) {					
						butacadao.insertarButaca(butaca);
						ctdButaca++;
						trans.commit();
						//System.out.println("Butaca insertada " + butaca);
					}
				}

			
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
		
	}	
	
	/**
	* Servicio para generar una Distribucion Especifica, defininiendo los tipos de fila que queremos insertar
	* Usando "N" para butaca Normal, "P" para butaca Premium y "E" para butaca para minusvalidos
	* Definimos las veces que queremos que se repitan cada una de las filas, y el numero de filas maximo (Todavia en desarrollo)
	* El caracter "#" significara final de la fila y "-" espacio entre grupo de butacas de una fila
	* @return Un String con la Distribucion generada
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
			
			//System.out.println(distribucion);
		}

		return distribucion;
		
	}
		
	public String recuperarDistribuciondeSala() throws ServiceException {
		TransaccionesManager trans = null;
		SalaDAO saladao = null; 
		Sala sala = null;
		
		try {
			trans = new TransaccionesManager();			
			saladao = trans.getSalaDAO();
			
			sala=saladao.recuperarSala(1);
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
		
		return sala.getDistribucion();
	}

	/**
	* Servicio para recuperar una Lista de todas las Butacas de una Sala en especifico
	* @param sala Objeto Sala del que queremos recuperar las Butacas
	* @return Una Lista de todas las Butacas de una Sala
	* @throws ServiceException
	*/	
	public List<Butaca> recuperarButacasSala(Sala sala) throws ServiceException{
		TransaccionesManager trans = null;
		ButacaDAO butacadao = null;
		List<Butaca> list = new ArrayList<Butaca>();
		
		try {
			trans = new TransaccionesManager();			
			butacadao = trans.getButacaDAO();
			
			list = butacadao.recuperarTodasButacasdeSala(sala);
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
		return list;
	}

	/**
	* Servicio que recupera una Lista con las butacas ocupadas de una Funcion en especifico
	* @param fun Objeto Funcion del que queremos recuperar las Butacas ocupadas
	* @return Una Lista de todas las Butacas Ocupadas de una Funcion
	* @throws ServiceException
	*/		
	public List<Butaca> recuperarButacasOcupadasDeFuncion(Funcion fun) throws ServiceException{
		TransaccionesManager trans = null;
		ButacaDAO butacadao = null;
		List<Butaca> list = new ArrayList<Butaca>();
		
		try {
			trans = new TransaccionesManager();			
			butacadao = trans.getButacaDAO();
			
			list = butacadao.recuperarButacasOcupadasDeFuncion(fun);
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
		return list;
	}
	
	/**
	* Servicio que recupera una Butaca por su Id
	* @param id Identificador de la Butaca
	* @return La Butaca recuperada
	* @throws ServiceException
	*/	
	public Butaca recuperarButaca(int id) throws ServiceException {
		Butaca but;
		TransaccionesManager trans = null;
		ButacaDAO butacadao = null;


		try {

			trans = new TransaccionesManager();
			
			butacadao = trans.getButacaDAO();
			but = new Butaca (id);
			but= butacadao.recuperarButaca(but);

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
		return but;

	}
	
	/**
	* Servicio para insertar una Sala
	* @param s Objeto Sala que queremos insertar
	* @throws ServiceException
	*/		
	public void insertarSala(Sala s) throws ServiceException{
		TransaccionesManager trans = null;
		SalaDAO saladao;
		
		try {

			trans = new TransaccionesManager();
			
			saladao = trans.getSalaDAO();
			
			saladao.crearSala(s);
			
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
	

}
