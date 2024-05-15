package servicios;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import daos.*;
import domain.*;
import exceptions.DAOException;
import exceptions.ServiceException;

/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase FuncionDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see FuncionDAO
 */
public class ServicioFunciones {
	
	public ServicioFunciones () {}
	

	/**
	* Servicio para recuperar todas las Funciones de una Pelicula especifia, en una Fecha determinada
	* @param peli Objeto Pelicula
	* @param fecha Objeto java.util.Date de una fecha
	* @return Devuelve la lista de Funciones disponibles de una Pelicula especifica, en una Fecha determinada
	* @throws ServiceException
	*/	
	public List<Funcion> recuperarTodasFuncionesFechaDePeli(Pelicula peli, Date fecha) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesFechaDePeli(peli, fecha);
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
	* Servicio para recuperar una lista de Funciones disponibles (con hora de inicio posterior al momento actual) de una Pelicula especifica en la fecha de Hoy
	* @param peli Objeto Pelicula
	* @return Devuelve la lista de Funciones disponibles(con hora de inicio posterior al momento actual) de una Pelicula especifica en la fecha de Hoy
	* @throws ServiceException
	*/		
	public List<Funcion> recuperarTodasFuncionesDisponiblesDePeli(Pelicula peli) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesDisponiblesDePeli(peli);
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
	* Servicio para recuperar una lista de Funciones disponibles (con hora de inicio posterior al momento actual), en la fecha de Hoy
	* @return Devuelve la lista de Funciones disponibles (con hora de inicio posterior al momento actual), en la fecha de Hoy
	* @throws ServiceException
	*/	
	public List<Funcion> recuperarTodasFuncionesDisponiblesHoy() throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesDisponiblesHoy();
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
	* Servicio para recuperar una lista de Funciones, en la fecha de el dia siguiente a Hoy
	* @return Devuelve la lista de Funciones en la fecha de el dia siguiente a Hoy
	* @throws ServiceException
	*/		
	public List<Funcion> recuperarTodasFuncionesDisponiblesHoyMasUno() throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesDisponiblesHoyMasUno();
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
	* Servicio para recuperar una lista de Funciones, de una Pelicula especifica, en una Sala especifica, en una Fecha determinada
	* @param peli Objeto Pelicula
	* @param fecha Objeto java.util.Date
	* @param sala Objeto Sala
	* @return Devuelve la lista de Funciones de una Pelicula especifica, en una Sala especifica, en una Fecha determinada
	* @throws ServiceException
	*/		
	public List<Funcion> recuperarTodasFuncionesFechaDePeliDeSala(Pelicula peli, Date fecha, Sala sala) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesFechaDePelideSala(peli, fecha, sala);
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
	* Servicio para recuperar una lista de Funciones, en una Sala especifica, en una Fecha determinada
	* @param fecha Objeto java.util.Date
	* @param sala Objeto Sala
	* @return Devuelve la lista de Funciones en una Sala especifica, en una Fecha determinada
	* @throws ServiceException
	*/		
	public List<Funcion> recuperarTodasFuncionesDeFechaDeSala(Date fecha, Sala sala) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		List<Funcion> list = new ArrayList<Funcion>();

		try {
			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();
			
			list = funciondao.recuperarTodasFuncionesDeFechaDeSala(fecha, sala);
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
	* Servicio para borrar una funcion a partir de su Id
	* @param fun Objeto Funcion que queremos borrar
	* @return Devuelve 1 si se ha borrado correctamente y 0 si no se ha podido borrar
	* @throws ServiceException 
	*/
	public int borrarFuncion(Funcion fun) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		int borrado = 0;
		try {

			trans = new TransaccionesManager();			
			funciondao = trans.getFuncionDAO();			
			borrado=funciondao.borrarFuncion(fun);
			
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
	* Servicio para insertar una Funcion, realiza la transaccion por cada Funcion
	* @param fun Objeto Funcion que queremos insertar
	* @throws ServiceException
	*/		
	public void escribirFuncion(Funcion fun) throws ServiceException{
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;
		
		try {

			trans = new TransaccionesManager();
			
			funciondao = trans.getFuncionDAO();
			
			funciondao.insertarFuncion(fun);
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
	* Servicio para comprobar la disponiblidad del espacio horario de una potencial Funcion de una especifica Pelicula que queremos insertar, 
	* Iteramos por todos los elementos de la lista y calculamos la horaInicio y horaFin de cada elemento
	* Sabiendo esto, vamos comparando las horas Fin e Inicio de cada elemento con la del elemento que queremos insertar
	* @param list Lista de Funciones en las que queremos determinar un espacio horario disponible
	* @param horaInicioNF Timestamp de la hora de inicio del espacio horario que queremos insertar
	* @param horaFinNF Timestamp de la hora fin del espacio horario que queremos insertar
	* @param peli Objeto Pelicula con la duracion de la pelicula de la funcion que vamos a insertar
	* @return Devuelve True si esta disponible es espacio horario y False si no
	*/	
	
	public boolean comprobarPeriodoDisponible(List <Funcion> list, Timestamp horaInicioNF, Timestamp horaFinNF, Pelicula peli){
		Timestamp horaInicio; Timestamp horaFin; Timestamp horaInicioSig;
		boolean puedes = false;
		
	      for (int i = 0; i < list.size()&&!puedes ; i++) {

	    	  long t1 = list.get(i).getFecha().getTime(); 
	    	  long m1 = peli.getDuracion() * 60 * 1000; 
	    	  
	    	  horaInicio = list.get(i).getFecha(); //System.out.println("Hora inicio "+horaInicio);
	    	  horaFin = new Timestamp (t1+m1); //System.out.println("Hora fin "+horaFin);

	    	  
	    	  if (horaFin.before(horaInicioNF)) {
//	    		  System.out.println(horaFin+ " Hora fin de la funcion es menor que la hora inicio de la nueva funcion " + horaInicioNF);
	    		  if ( i != list.size()-1) {
		    		  horaInicioSig= list.get(i+1).getFecha();		    		  
		    		  if (horaInicioSig.after(horaFinNF)) {
//		    			  System.out.println(horaInicioSig +" Hora inicio de la siguiente funcion es mayour que la hora final de la nueva funcion "+horaFinNF);
		    			  puedes = true;
		    		  }
	    		  } else {
	    			  puedes = true;
	    		  }	    			  
	    		  
	    	  }else {
	    		  if(horaInicio.after(horaFinNF)) {
//	    			  System.out.println(horaInicio+" Hora inicio de la funcion es mayor que la final de la funcion nueva "+horaFinNF);
	    			  puedes = true;
	    		  }else {
	    			  break;
	    		  }
	    	  }	    	  
        	
	        } 

		return puedes;
		
	}
	
	/**
	* Servicio para comprobar la disponiblidad del espacio horario de una potencial Funcion que queremos insertar
	* Iteramos por todos los elementos de la lista y calculamos la horaInicio y horaFin de cada elemento
	* Sabiendo esto, vamos comparando las horas Fin e Inicio de cada elemento con la del elemento que queremos insertar
	* @param list Lista de Funciones en las que queremos determinar un espacio horario disponible
	* @param horaInicioNF Timestamp de la hora de inicio del espacio horario que queremos insertar
	* @param horaFinNF Timestamp de la hora fin del espacio horario que queremos insertar
	* @return Devuelve True si esta disponible ese espacio horario y False si no
	*/	
	public boolean comprobarPeriodoDisponible(List <Funcion> list, Timestamp horaInicioNF, Timestamp horaFinNF){
		Timestamp horaInicio; Timestamp horaFin; Timestamp horaInicioSig;
		boolean puedes = false;
		
	      for (int i = 0; i < list.size()&&!puedes ; i++) {

	    	  long t1 = list.get(i).getFecha().getTime(); 
	    	  long m1 = list.get(i).getPeli().getDuracion()* 60 * 1000; 
	    	  
	    	  horaInicio = list.get(i).getFecha(); //System.out.println("Hora inicio "+horaInicio);
	    	  horaFin = new Timestamp (t1+m1); //System.out.println("Hora fin "+horaFin);

	    	  
	    	  if (horaFin.before(horaInicioNF)) {
//	    		  System.out.println(horaFin+ " Hora fin de la funcion es menor que la hora inicio de la nueva funcion " + horaInicioNF);
	    		  if ( i != list.size()-1) {
		    		  horaInicioSig= list.get(i+1).getFecha();		    		  
		    		  if (horaInicioSig.after(horaFinNF)) {
//		    			  System.out.println(horaInicioSig +" Hora inicio de la siguiente funcion es mayour que la hora final de la nueva funcion "+horaFinNF);
		    			  puedes = true;
		    		  }
	    		  } else {
	    			  puedes = true;
	    		  }	    			  
	    		  
	    	  }else {
	    		  if(horaInicio.after(horaFinNF)) {
//	    			  System.out.println(horaInicio+" Hora inicio de la funcion es mayor que la final de la funcion nueva "+horaFinNF);
	    			  puedes = true;
	    		  }else {
	    			  break;
	    		  }
	    	  }	    	  
        	
	        } 

		return puedes;
		
	}	

	/**
	* Servicio para recuperar una funcion a partir de su Id
	* @param fun Objeto Funcion que queremos recuperar
	* @return Devuelve la Funcion recuperada
	* @throws ServiceException
	*/		
	public Funcion recuperarFun(Funcion fun) throws ServiceException {
		TransaccionesManager trans = null;
		FuncionDAO funciondao = null;

		try {

			trans = new TransaccionesManager();
			
			funciondao = trans.getFuncionDAO();
			fun= funciondao.recuperarFuncion(fun);

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
		return fun;

	}	
	

	
	

}
