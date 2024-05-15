package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Funcion;
import domain.Pelicula;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;
import objetos.Pais;

/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Pelicula) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Pelicula
 */
public class PeliculaDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public PeliculaDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para recuperar una Pelicula por su Id
	* @param pelicula Objeto Pelicula con su id Definida que queremos recuperar
	* @return Devuelve la Pelicula recuperada
	* @throws DAOException
	*/		
	public Pelicula recuperarPelicula(Pelicula pelicula) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Pelicula p=null; Pais pais=null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarPelicula());
			st.setInt(1,pelicula.getIdPelicula());
			rs=st.executeQuery();
			if (rs.next()){	
				          		  
			p = new Pelicula(
					  rs.getInt("idpelicula"),
					  rs.getString("titulo"),
	                  rs.getString("titulo_orig"),
	                  rs.getDate("estreno"),
	                  pais = new Pais(rs.getString("pais")),
	                  rs.getString("genero"),
	                  rs.getString("actores"),
	                  rs.getString("directores"),
	                  rs.getString("guionistas"),
	                  rs.getString("productores"),
	                  new Integer(rs.getInt("duracion")),
	                  rs.getString("sinopsis"),
	                  rs.getInt("soporte_digital"),
	                  rs.getInt("soporte_3d"),
	                  rs.getInt("version_original"),
	                  rs.getInt("version_esp"),
	                  rs.getString("imagen"),
	                  rs.getString("trailer"),
	                  rs.getString("edad_recom")
	                  );
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return p;	
	}
	
	/**
	* Metodo para solo la duracion de una Pelicula por su Id
	* @param pelicula Objeto Pelicula con su id Definida que queremos recuperar
	* @return Devuelve la Pelicula recuperada
	* @throws DAOException
	*/		
	public Pelicula recuperarDuracionPelicula(Pelicula pelicula) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Pelicula p=null; Pais pais=null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarDuracionPelicula());
			st.setInt(1,pelicula.getIdPelicula());
			rs=st.executeQuery();
			if (rs.next()){					          		  
				pelicula.setDuracion(rs.getInt("duracion"));
			}			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return pelicula;	
	}
		
	/**
	* Metodo para insertar una Pelicula
	* @param p Objeto Pelicula que queremos insertar
	* @throws DAOException Si la Pelicula ya existe o si el Pais no existe
	*/			
	public void insertarPelicula(Pelicula p) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;
		try {
			
			st = con.prepareStatement(DbQuery.getInsertarPelicula());
			
			st.setString(1, null);		
			st.setString(2, p.getTitulo());
			st.setString(3, p.getTitulo_orig());
			st.setDate(4, p.getEstreno());
			st.setString(5, p.getPais().getNombre());
			st.setString(6, p.getGenero());
			st.setString(7, p.getActores());
			st.setString(8, p.getDirectores());
			st.setString(9, p.getGuionistas());
			st.setString(10, p.getProductores());
			st.setInt(11, p.getDuracion());
			st.setString(12, p.getSinopsis());
			//Booleans??
			st.setBoolean(13, p.isSp_digi());
			st.setBoolean(14, p.isSp_3d());
			st.setBoolean(15, p.isVo());
			st.setBoolean(16, p.isEsp());
			
			st.setString(17, p.getImagen());
			st.setString(18, p.getTrailer());
			st.setString(19, p.getEdad_rec());			
			
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarPais());
				  sti.setString(1, p.getPais().getNombre());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Ese país no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }			
			
			
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Esta pelicula ya existe");
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
		}
	}

	
	/**
	* Metodo para recuperar una lista de todas las Peliculas existentes en la BBDD
	* @return Devuelve la lista con todas las Peliculas de la BBDD
	* @throws DAOException
	*/	
	public List<Pelicula> recuperarTodasPelicula()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Pelicula> list = new ArrayList<Pelicula>();
		Pais pais=null; Pelicula pelicula=null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarTodasPeliculas());
			rs = st.executeQuery();
			while (rs.next()) {
				 pelicula = new Pelicula(
								  rs.getInt("idpelicula"),
								  rs.getString("titulo"),
				                  rs.getString("titulo_orig"),
				                  rs.getDate("estreno"),
				                  pais = new Pais(rs.getString("pais")),
				                  rs.getString("genero"),
				                  rs.getString("actores"),
				                  rs.getString("directores"),
				                  rs.getString("guionistas"),
				                  rs.getString("productores"),
				                  new Integer(rs.getInt("duracion")),
				                  rs.getString("sinopsis"),
				                  rs.getInt("soporte_digital"),
				                  rs.getInt("soporte_3d"),
				                  rs.getInt("version_original"),
				                  rs.getInt("version_esp"),
				                  rs.getString("imagen"),
				                  rs.getString("trailer"),
				                  rs.getString("edad_recom")
						);
				
				list.add(pelicula);
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;

	}
	
	/**
	* Metodo para recuperar la cartelera de las proximas 2 semanas
	* @return Devuelve una lista de las Peliculas con Funciones disponibles en los proximos 14 dias
	* @throws DAOException
	*/	
	public List<Pelicula> recuperarCartelera()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Pelicula> list = new ArrayList<Pelicula>();
		Pais pais=null; Pelicula pelicula=null;
		try {
			st = con.prepareStatement(DbQuery.getCarteleraSemana());
			rs = st.executeQuery();
			while (rs.next()) {
				 pelicula = new Pelicula(
								  rs.getInt("idpelicula"),
								  rs.getString("titulo"),
				                  rs.getString("titulo_orig"),
				                  rs.getDate("estreno"),
				                  pais = new Pais(rs.getString("pais")),
				                  rs.getString("genero"),
				                  rs.getString("actores"),
				                  rs.getString("directores"),
				                  rs.getString("guionistas"),
				                  rs.getString("productores"),
				                  new Integer(rs.getInt("duracion")),
				                  rs.getString("sinopsis"),
				                  rs.getInt("soporte_digital"),
				                  rs.getInt("soporte_3d"),
				                  rs.getInt("version_original"),
				                  rs.getInt("version_esp"),
				                  rs.getString("imagen"),
				                  rs.getString("trailer"),
				                  rs.getString("edad_recom")
						);
				
				list.add(pelicula);
			}
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return list;

	}	
	
	/**
	* Metodo para modificar una Pelicula a partir de su Id (Sin concurrencia)
	* @param p Objeto Pelicula que se quiere insertar ahora
	* @return Devuelve 1 si lo ha modificado correctamente y 0 si no ha podido
	* @throws DAOException Si el pais no existe
	*/	
	public int modificarPelicula(Pelicula p)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getModificarPelicula());	
			st.setString(1, p.getTitulo());
			st.setString(2, p.getTitulo_orig());
			st.setDate(3, p.getEstreno());
			st.setString(4, p.getPais().getNombre());
			st.setString(5, p.getGenero());
			st.setString(6, p.getActores());
			st.setString(7, p.getDirectores());
			st.setString(8, p.getGuionistas());
			st.setString(9, p.getProductores());
			st.setInt(10, p.getDuracion());
			st.setString(11, p.getSinopsis());
			st.setBoolean(12, p.isSp_digi());
			st.setBoolean(13, p.isSp_3d());
			st.setBoolean(14, p.isVo());
			st.setBoolean(15, p.isEsp());
			st.setString(16, p.getImagen());
			st.setString(17, p.getTrailer());
			st.setString(18, p.getEdad_rec());	
			
			st.setString(19, p.getIdPelicula().toString());
						
			//para el país
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarPais());
				  sti.setString(1, p.getPais().getNombre());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Ese país no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }						
			
			// ejecutamos el insert.			
			modificado=st.executeUpdate();
		} catch (SQLException e) {
			 if (e.getErrorCode() ==ORACLE_FALLO_FK ){
			   throw new DAOException("Operacion no disponible temporalmente,repita proceso");
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
			    throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}	
		return modificado;
	}
	
	/**
	* Metodo para modificar una Pelicula a partir de su Id (Con concurrencia)
	* @param peliActual Objeto Pelicula que se quiere insertar ahora
	* @param peliInicial Objeto Pelicula que se recupero al inicio de la transaccion
	* @return Devuelve 1 si lo ha modificado correctamente y 0 si no ha podido
	* @throws DAOException Si el pais no existe
	*/		
	public int modificarPeliculaConcurrente(Pelicula peliActual, Pelicula peliInicial)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getModificarPeliculaConcurrente());	
			//Cliente que vamos a insertar
			st.setString(1, peliActual.getTitulo());
			st.setString(2, peliActual.getTitulo_orig());
			st.setDate(3, peliActual.getEstreno());
			st.setString(4, peliActual.getPais().getNombre());
			st.setString(5, peliActual.getGenero());
			st.setString(6, peliActual.getActores());
			st.setString(7, peliActual.getDirectores());
			st.setString(8, peliActual.getGuionistas());
			st.setString(9, peliActual.getProductores());
			st.setInt(10, peliActual.getDuracion());
			st.setString(11, peliActual.getSinopsis());
			st.setBoolean(12, peliActual.isSp_digi());
			st.setBoolean(13, peliActual.isSp_3d());
			st.setBoolean(14, peliActual.isVo());
			st.setBoolean(15, peliActual.isEsp());
			st.setString(16, peliActual.getImagen());
			st.setString(17, peliActual.getTrailer());
			st.setString(18, peliActual.getEdad_rec());	
			
			st.setInt(19, peliActual.getIdPelicula());
			//Cliente que hemos recuperado que inicialmente estaba en la bbdd
			
			st.setString(20, peliInicial.getTitulo());
			st.setString(21, peliInicial.getTitulo_orig());
			st.setDate(22, peliInicial.getEstreno());
			st.setString(23, peliInicial.getPais().getNombre());
			st.setString(24, peliInicial.getGenero());
			st.setString(25, peliInicial.getActores());
			st.setString(26, peliInicial.getDirectores());
			st.setString(27, peliInicial.getGuionistas());
			st.setString(28, peliInicial.getProductores());
			st.setInt(29, peliInicial.getDuracion());
			st.setString(30, peliInicial.getSinopsis());
			st.setBoolean(31, peliInicial.isSp_digi());
			st.setBoolean(32, peliInicial.isSp_3d());
			st.setBoolean(33, peliInicial.isVo());
			st.setBoolean(34, peliInicial.isEsp());
			st.setString(35, peliInicial.getImagen());
			st.setString(36, peliInicial.getTrailer());
			st.setString(37, peliInicial.getEdad_rec());							
			//para el país
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarPais());
				  sti.setString(1, peliActual.getPais().getNombre());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Ese país no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }						
			
			// ejecutamos el insert.			
			modificado=st.executeUpdate();
		} catch (SQLException e) {
			 if (e.getErrorCode() ==ORACLE_FALLO_FK ){
			   throw new DAOException("Operacion no disponible temporalmente,repita proceso");
			}else if  (e.getErrorCode()>=20000 && e.getErrorCode()<=20999){
				String cadena=e.toString().substring(e.toString().indexOf("ORA", 0)+10);
				String cadena1=cadena.substring(0,cadena.indexOf("ORA", 0));
			    throw new DAOException(cadena1);
			} else {
				throw new DAOException(DB_ERR, e);
			}
		} finally {
			Recursos.closePreparedStatement(st);
			Recursos.closePreparedStatement(sti);
		}	
		return modificado;
	}
	
	
	/**
	* Metodo para borrar una Pelicula a partir de su Id
	* @param p Objeto Pelicula con su Id definida
	* @return Devuelve 1 si lo ha borrado correctamente y 0 si no ha podido
	* @throws DAOException Si se intenta borrar una clave ajena
	*/		
	public int borrarPelicula(Pelicula p) throws DAOException{
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarPelicula());
			st.setString(1, p.getIdPelicula().toString());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar la Pelicula");
				
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
		
	


	
	
}
