package servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import daos.PeliculaDAO;
import daos.TransaccionesManager;
import domain.Pelicula;
import exceptions.DAOException;
import exceptions.ServiceException;

/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase PeliculaDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see PeliculaDAO
 */
public class ServicioPeliculas {

	public ServicioPeliculas (){}
	/**
	* Servicio para recuperar una lista de todas las Peliculas existentes en la BBDD
	* @return Devuelve la lista con todas las Peliculas de la BBDD
	* @throws ServiceException
	*/		
	public List<Pelicula> todasPeliculas() throws ServiceException {
		List<Pelicula> listaPelis = null;
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;
		try {

			trans = new TransaccionesManager();
			
			pelidao = trans.getPeliculaDAO();
			listaPelis=pelidao.recuperarTodasPelicula();
			//System.out.println(clienteRecuperado);

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
		return listaPelis;
	}
	
	

	/**
	* Servicio para recuperar una Pelicula por su Id
	* @param id Identificador de la pelicula que queremos recuperar
	* @return Devuelve la Pelicula recuperada
	* @throws ServiceException
	*/			
	public Pelicula pelicula(int id) throws ServiceException {
		Pelicula peli;
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;


		try {

			trans = new TransaccionesManager();
			
			pelidao = trans.getPeliculaDAO();
			peli = new Pelicula (id);
			peli= pelidao.recuperarPelicula(peli);


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
		return peli;

	}
	
	/**
	* Servicio para recuperar unicamente la duracion de una Pelicula en especifico
	* @param id Identificador de la pelicula que queremos recuperar
	* @return Devuelve la Pelicula recuperada con su duracion
	* @throws ServiceException
	*/	
	public Pelicula duracionPelicula(int id) throws ServiceException {
		Pelicula peli;
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;

		try {

			trans = new TransaccionesManager();
			
			pelidao = trans.getPeliculaDAO();
			peli = new Pelicula (id);
			peli= pelidao.recuperarDuracionPelicula(peli);


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
		return peli;

	}	
	
	/**
	* Servicio para insertar una Pelicula
	* @param p Objeto Pelicula que queremos insertar
	* @throws ServiceException 
	*/	
	public void grabarPelicula(Pelicula p) throws ServiceException{
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;
		
		try {

			trans = new TransaccionesManager();
			
			pelidao = trans.getPeliculaDAO();
			
			pelidao.insertarPelicula(p);
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
	* Servicio para modificar una Pelicula a partir de su Id (Sin concurrencia)
	* Realiza la transaccion si el valor de modificado es mayor de 0
	* @param p Objeto Pelicula que se quiere insertar ahora
	* @return Devuelve 1 si lo ha modificado correctamente y 0 si no ha podido
	* @throws ServiceException 
	*/		
	public int modificarPelicula(Pelicula p) throws ServiceException{
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;
		int modificado = 0;
		try {

			trans = new TransaccionesManager();			
			pelidao = trans.getPeliculaDAO();			
			modificado=pelidao.modificarPelicula(p);
			
			if (modificado>0) 
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
		
		return modificado;
	}
	/**
	* Servicio para modificar una Pelicula a partir de su Id (Con concurrencia)
	* Realiza la transaccion si el valor de modificado es mayor de 0
	* @param peliActual Objeto Pelicula que se quiere insertar ahora
	* @param peliInicial Objeto Pelicula que se recupero al inicio de la transaccion
	* @return Devuelve 1 si lo ha modificado correctamente y 0 si no ha podido
	* @throws ServiceException 
	*/	
	public int modificarPeliculaConcurrente(Pelicula peliActual, Pelicula peliInicial ) throws ServiceException{
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;
		int modificado = 0;
		try {

			trans = new TransaccionesManager();			
			pelidao = trans.getPeliculaDAO();			
			modificado=pelidao.modificarPeliculaConcurrente(peliActual, peliInicial);
			
			if (modificado>0) 
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
		
		return modificado;
	}	
	
	/**
	* Servicio para borrar una Pelicula a partir de su Id
	* Realiza la transaccion si el valor de borrado es mayor de 0
	* @param p Objeto Pelicula con su Id definida
	* @return Devuelve 1 si lo ha borrado correctamente y 0 si no ha podido
	* @throws ServiceException
	*/		
	public int borrarPelicula(Pelicula p) throws ServiceException{
		TransaccionesManager trans = null;
		PeliculaDAO pelidao;
		int borrado = 0;
		try {

			trans = new TransaccionesManager();			
			pelidao = trans.getPeliculaDAO();			
			borrado=pelidao.borrarPelicula(p);
			
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
	* Metodo para buscar una Pelicula definida por su id determinada en una lista 
	* Usando streams y expresiones lambda para no tener que iterar por la lista
	* @param id Identificador de la Pelicula que queremos recuperar
	* @param listaP Lista de Peliculas en la que buscaremos si existe una Pelicula con ese Id
	* @return Devuelve la Pelicula recuperada si la encuentra o null si no
	*/	
	public Pelicula peliculaContexto(int id, List <Pelicula> listaP){	
		List <Pelicula> listStream = null; Pelicula p=null;
		listStream = (List<Pelicula>) listaP.stream()
				.filter(item -> item.getIdPelicula().equals(id))
				.collect(Collectors.toList());
		if (listStream.size()>0)
			p = listStream.get(0);
		return p;
	}		
	 
	
	/**
	* Metodo para recuperar las Peliculas con fecha de estreno de como mucho, mas de 2 semanas de antiguedad, a partir de una Lista
	* @param listaP Lista de Peliculas en la que buscaremos
	* @return Devuelve una Lista de Peliculas con fecha de estreno de como mucho, mas de 2 semanas de antiguedad
	*/		
	public List <Pelicula> peliculasEstreno(List <Pelicula> listaP){
		
		List <Pelicula> list=new ArrayList<Pelicula>();	

		LocalDate fechaServidorLD = LocalDate.now();
		fechaServidorLD = fechaServidorLD.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)); //Siguiente viernes de la fecha actual
		fechaServidorLD = fechaServidorLD.minusDays(14);
		String formattedDateLD = fechaServidorLD.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
		Date date = Date.from(fechaServidorLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
		

		for (Pelicula p: listaP) 
			if(p.getEstreno().after(date))
				list.add(p);	
		
		return list;
	}
	
	/**
	* Servicio para recuperar la cartelera de las proximas 2 semanas
	* @return Devuelve una lista de las Peliculas con Funciones disponibles en los proximos 14 dias
	* @throws ServiceException
	*/		
	public List<Pelicula> carteleraSemanas() throws ServiceException {
		List<Pelicula> listaPelis = null;
		TransaccionesManager trans = null;
		PeliculaDAO pelidao = null;
		try {

			trans = new TransaccionesManager();
			
			pelidao = trans.getPeliculaDAO();
			
			listaPelis=pelidao.recuperarCartelera();


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
		return listaPelis;
	}		
	
}


