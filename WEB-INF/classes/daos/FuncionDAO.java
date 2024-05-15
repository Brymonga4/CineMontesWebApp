package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.*;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;

/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Funcion) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Funcion
 */
public class FuncionDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public FuncionDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para insertar una Funcion
	* @param f Objeto Funcion que queremos insertar
	* @throws DAOException Si la funcion ya existe, si la Sala no existe o si la pelicula no existe
	*/		
	public void insertarFuncion (Funcion f) throws DAOException {
		PreparedStatement st = null; 
		ResultSet rs = null; PreparedStatement sti = null;
		
		try {
			
			st = con.prepareStatement(DbQuery.getInsertarFuncion());
			
			//st.setString(1, null);		
			st.setInt(1, f.getPeli().getIdPelicula());
			st.setInt(2, f.getSala().getIdSala());
			st.setTimestamp(3, f.getFecha());
			st.setString(4, f.getAudio());
			st.setDouble(5, f.getPrecio());
					
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarSalaId());
				  sti.setInt(1, f.getSala().getIdSala());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esta sala no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
				  sti.setInt(1, f.getPeli().getIdPelicula());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esta pelicula no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }				
			
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Esta butaca ya existe");
			}else if (e.getErrorCode() ==ORACLE_FALLO_FK ){
			   throw new DAOException("Operacion no disponible temporalmente,repita proceso");
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
			    throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		
	}
	
	/**
	* Metodo para recuperar una lista de Funciones de una Pelicula especifica, en una Fecha determinada
	* Si fecha es nula, recuperara las Funciones disponibles (con hora de inicio posterior al momento actual) de una Pelicula especifica, en su lugar
	* @param peli Objeto Pelicula
	* @param fecha Objeto java.util.Date de una fecha
	* @return Devuelve la lista de Funciones disponibles de una Pelicula especifica, en una Fecha determinada
	* @throws DAOException
	*/		
	public List<Funcion> recuperarTodasFuncionesFechaDePeli(Pelicula peli, java.util.Date fecha)throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null;
		Sala sala = null; 
		try {
			if(fecha==null) {
				//System.out.println("Soy nula");
				//st = con.prepareStatement(DbQuery.getRecuperarFuncionesDeHoyDePelicula());
				st = con.prepareStatement(DbQuery.getRecuperarFuncionesDisponibles());
				st.setInt(1, peli.getIdPelicula());				
			}else {
				java.sql.Date sqlDate = new java.sql.Date (fecha.getTime());
				
				st = con.prepareStatement(DbQuery.getRecuperarFuncionesDeFechaDePelicula());
				st.setDate(1,  sqlDate);
				st.setInt(2, peli.getIdPelicula());				
			}
				
			rs = st.executeQuery();
					
			
			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			  rs.close();
			  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
			  sti.setInt(1, peli.getIdPelicula());
			  
			  rs=sti.executeQuery();
			  if(rs.next())	
				  peli.setDuracion(new Integer(rs.getInt("duracion")));			
		
			  for (Funcion f: list) {
				  f.getPeli().setDuracion(peli.getDuracion());
			  }

									  
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}
	
	/**
	* Metodo para recuperar una lista de Funciones disponibles (con hora de inicio posterior al momento actual) de una Pelicula especifica
	* @param peli Objeto Pelicula
	* @return Devuelve la lista de Funciones disponibles(con hora de inicio posterior al momento actual) de una Pelicula especifica
	* @throws DAOException
	*/		
	public List<Funcion> recuperarTodasFuncionesDisponiblesDePeli(Pelicula peli)throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null;
		Sala sala = null; 
		
		try {
		
				st = con.prepareStatement(DbQuery.getRecuperarFuncionesDisponibles());
				st.setInt(1, peli.getIdPelicula());
			
			rs = st.executeQuery();
					
			
			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			  rs.close();
			  
			  if  (list!=null && list.size()>0) {  
				  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
				  sti.setInt(1, peli.getIdPelicula());
				  
				  rs=sti.executeQuery();
				  if(rs.next())	
					  peli.setDuracion(new Integer(rs.getInt("duracion")));			
			
				  for (Funcion f: list) {
					  f.getPeli().setDuracion(peli.getDuracion());
				  }

			  }					  
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}
	
	/**
	* Metodo para recuperar una lista de Funciones disponibles (con hora de inicio posterior al momento actual), en la fecha de Hoy
	* @return Devuelve la lista de Funciones disponibles (con hora de inicio posterior al momento actual), en la fecha de Hoy
	* @throws DAOException
	*/		
	public List<Funcion> recuperarTodasFuncionesDisponiblesHoy()throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null;
		Sala sala = null; Pelicula peli = null;
		
		try {
			st = con.prepareStatement(DbQuery.getRecuperarFuncionesDisponiblesHoyAhora());
			rs = st.executeQuery();

			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			  rs.close();
			  
			  if  (list!=null && list.size()>0) {
		  		  
			  for (Funcion f: list) {				  
				  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
				  sti.setInt(1, f.getPeli().getIdPelicula());
				  
				  rs=sti.executeQuery();
				  if(rs.next())	{
					  peli.setDuracion(new Integer(rs.getInt("duracion")));	
					  peli.setTitulo(rs.getString("titulo"));
				  }					  
				  
				  f.getPeli().setTitulo(peli.getTitulo());
				  f.getPeli().setDuracion(peli.getDuracion());			  
			  }
		  }
			  
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}	
	
	/**
	* Metodo para recuperar una lista de Funciones, en la fecha de el dia siguiente a Hoy
	* @return Devuelve la lista de Funciones en la fecha de el dia siguiente a Hoy
	* @throws DAOException
	*/	
	public List<Funcion> recuperarTodasFuncionesDisponiblesHoyMasUno()throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null;
		Sala sala = null; Pelicula peli = null;
		
		try {
			st = con.prepareStatement(DbQuery.getRecuperarFuncionesDisponiblesHoyMasUno());
			rs = st.executeQuery();

			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			  rs.close();			  
			  
				  if  (list!=null && list.size()>0) {
					  		  
				  for (Funcion f: list) {				  
					  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
					  sti.setInt(1, f.getPeli().getIdPelicula());
					  
					  rs=sti.executeQuery();
					  if(rs.next())	{
						  peli.setDuracion(new Integer(rs.getInt("duracion")));	
						  peli.setTitulo(rs.getString("titulo"));
					  }					  
					  
					  f.getPeli().setTitulo(peli.getTitulo());
					  f.getPeli().setDuracion(peli.getDuracion());			  
				  }
			  }
				  
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}
	
	/**
	* Metodo para recuperar una lista de Funciones, de una Pelicula especifica, en una Sala especifica, en una Fecha determinada
	* @param peli Objeto Pelicula
	* @param fecha Objeto java.util.Date
	* @param sala Objeto Sala
	* @return Devuelve la lista de Funciones de una Pelicula especifica, en una Sala especifica, en una Fecha determinada
	* @throws DAOException
	*/		
	public List<Funcion> recuperarTodasFuncionesFechaDePelideSala(Pelicula peli, java.util.Date fecha, Sala sala)throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null;
		
		try {

				java.sql.Date sqlDate = new java.sql.Date (fecha.getTime());
				
				st = con.prepareStatement(DbQuery.getRecuperarFuncionesDeFechaDePeliculaDeSala());
				st.setDate(1,  sqlDate);
				st.setInt(2, peli.getIdPelicula());				
				st.setInt(3, sala.getIdSala());
				
			rs = st.executeQuery();
					
			
			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}	
	
	/**
	* Metodo para recuperar una lista de Funciones, en una Sala especifica, en una Fecha determinada
	* @param fecha Objeto java.util.Date
	* @param sala Objeto Sala
	* @return Devuelve la lista de Funciones en una Sala especifica, en una Fecha determinada
	* @throws DAOException
	*/	
	public List<Funcion> recuperarTodasFuncionesDeFechaDeSala(java.util.Date fecha, Sala sala)throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs = null;
		List<Funcion> list = new ArrayList<Funcion>();
		Funcion funcion = null; Integer i1 = null; Pelicula peli = null;
		
		try {

				java.sql.Date sqlDate = new java.sql.Date (fecha.getTime());
				
				st = con.prepareStatement(DbQuery.getRecuperarFuncionesDeFechaDeSala());
				st.setDate(1,  sqlDate);		
				st.setInt(2, sala.getIdSala());
				
			rs = st.executeQuery();
					
			
			while (rs.next()) {
			  			
				funcion = new Funcion(
								  i1 = new Integer(rs.getInt("idfuncion")),
								  peli = new Pelicula (rs.getInt("idpelicula")),
								  sala = new Sala(rs.getInt("idsala")),
								  rs.getTimestamp("fecha"),
								  rs.getString("audio"),
				                  rs.getDouble("precio")
						);	

				list.add(funcion);
			}
			
			rs.close();
			
			  if  (list!=null && list.size()>0) {
		  		  
			  for (Funcion f: list) {				  
				  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
				  sti.setInt(1, f.getPeli().getIdPelicula());
				  
				  rs=sti.executeQuery();
				  if(rs.next())	{
					  peli.setDuracion(new Integer(rs.getInt("duracion")));	
					  peli.setTitulo(rs.getString("titulo"));
				  }					  
				  
				  f.getPeli().setTitulo(peli.getTitulo());
				  f.getPeli().setDuracion(peli.getDuracion());			  
			  }
		  }
			  
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}
		return list;

	}	
	
	/**
	* Metodo para borrar una funcion a partir de su Id
	* @param fun Objeto Funcion que queremos borrar
	* @return Devuelve 1 si se ha borrado correctamente y 0 si no se ha podido borrar
	* @throws DAOException Si se intenta borrar una clave ajena
	*/		
	public int borrarFuncion(Funcion fun) throws DAOException{
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarFuncion());
			st.setString(1, fun.getId().toString());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar la funcion");
				
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
			    throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closePreparedStatement(st);
		}
		return borrado;
	}
	
	/**
	* Metodo para recuperar una funcion a partir de su Id
	* @param fun Objeto Funcion que queremos recuperar
	* @return Devuelve la Funcion recuperada
	* @throws DAOException
	*/	
	public Funcion recuperarFuncion (Funcion fun) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Sala sala = null; Pelicula peli = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarFuncionId());
			st.setInt(1, fun.getId());
			rs=st.executeQuery();
			if (rs.next()){					          		  
				fun = new Funcion(
						rs.getInt("idfuncion"),
						peli = new Pelicula(rs.getInt("idpelicula")),
						sala = new Sala (rs.getInt("idsala")),
						rs.getTimestamp("fecha"),
						rs.getString("audio"),
		                rs.getDouble("precio")
						);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}		
		return fun ;	
	}
	
	
	
	
	
	
}
