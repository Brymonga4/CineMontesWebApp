package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import domain.Pelicula;
import domain.Review;
import domain.Usuario;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;

/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Review) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Review
 */
public class ReviewDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public ReviewDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para insertar una Review
	* @param r Objeto Review que queremos insertar
	* @throws DAOException Si la Review ya existe, si la Pelicula no existe o si el Usuario no existe
	*/		
	public void escribirReview(Review r) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;
		
		
		try {
			
			st = con.prepareStatement(DbQuery.getEscribirReview());
			//System.out.println(r.getFecha());
			st.setString(1, null);		
			st.setInt(2, r.getPeli().getIdPelicula());
			st.setString(3, r.getUser().getAlias());
			st.setString(4, r.getTitulo());
			st.setString(5, r.getOpinion());
			st.setInt(6, r.getValoracion());			
			//st.setTimestamp(7, r.getFecha());			
			st.setTimestamp(7, r.getFecha(), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarPelicula());
				  sti.setInt(1, r.getPeli().getIdPelicula());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esa pelicula no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }		
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarUsuario());
				  sti.setString(1, r.getUser().getAlias());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Ese Usuario no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }				
			
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Esta review ya existe");
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
	* Metodo para recuperar una lista de todas las Reviews existentes en la BBDD
	* @return Devuelve la lista con todas las Reviews de la BBDD
	* @throws DAOException
	*/		
	public List<Review> recuperarTodasReviews()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Review> list = new ArrayList<Review>();
		Pelicula pelicula=null; Review review = null; Usuario user = null;
		Integer i1, i2;
		try {
			st = con.prepareStatement(DbQuery.getTodasLasReviews());
			rs = st.executeQuery();
			while (rs.next()) {
				review = new Review(
								  i1 = new Integer(rs.getInt("id")),
								  pelicula = new Pelicula (rs.getInt("pelicula")),								  
								  user = new Usuario(rs.getString("alias")),
				                  rs.getString("titulo"),
				                  rs.getString("opinion"),
				                  i2 = new Integer(rs.getInt("valoracion")),
				                  rs.getTimestamp("fecha"));
				
				list.add(review);
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
	* Metodo para recuperar una lista de todas las Reviews de una Pelicula en especifico
	* @param peli Objeto Pelicula 
	* @return Devuelve una lista de todas las Reviews de una Pelicula en especifico
	* @throws DAOException
	*/		
	public List<Review> recuperarReviewsdePeli(Pelicula peli)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Review> list = new ArrayList<Review>();
		Pelicula pelicula=null; Review review = null; Usuario user = null;
		Integer i1, i2;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarReviewdePeli());
			st.setInt(1,peli.getIdPelicula());
			rs = st.executeQuery();
			while (rs.next()) {
				review = new Review(
								  i1 = new Integer(rs.getInt("id")),
								  pelicula = new Pelicula (rs.getInt("pelicula")),								  
								  user = new Usuario(rs.getString("alias")),
				                  rs.getString("titulo"),
				                  rs.getString("opinion"),
				                  i2 = new Integer(rs.getInt("valoracion")),
				                  rs.getTimestamp("fecha"));
				
				list.add(review);
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
	* Metodo para calcular la Valoracion Media de las Reviews de una Pelicula en especifico
	* @param p Objeto Pelicula
	* @return Devuelve un double con la cantidad calculada, y si no hay Reviews escritas aun, devuelve 0.0
	* @throws DAOException
	*/	
	public double valoracionMediaDePelicula(Pelicula p) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		double valoracionMedia=0.0;
		try {
			st = con.prepareStatement(DbQuery.getValoracionPelicula());
			st.setInt(1, p.getIdPelicula());
			rs = st.executeQuery();
						
			if (rs.next())	{
				valoracionMedia=rs.getDouble(1);
			}			
		
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}		
		
		return valoracionMedia;
	}	
	
	/**
	* Metodo para saber si un Usuario ha escrito anteriormente, una Review de una Pelicula en especifico
	* @param user Objeto Usuario
	* @param peli Objeto Pelicula
	* @return Devuelve un True si el Usuario ha escrito ya una Review de esa Pelicula y False si no
	* @throws DAOException
	*/	
	public boolean usuarioEscribioReviewenPeli(Usuario user, Pelicula peli) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		boolean escribio; int count = 0;
		try {
			st = con.prepareStatement(DbQuery.getReviewsDeUsuarioenPeli());
			st.setString(1, user.getAlias());
			st.setInt(2, peli.getIdPelicula());
			rs=st.executeQuery();
			
			if (rs.next())	{
				count=rs.getInt(1);
			}
			
			if (count>0)
				escribio = true;
			else
				escribio = false;
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		
		return escribio;
	}
	
	/**
	* Metodo para borrar todas las Reviews de una Pelicula por su Id
	* @param p Objeto Pelicula con su Id definida
	* @return Devuelve el numero de reviews que ha podido borrar y 0 si no ha podido borrar ninguna
	* @throws DAOException Si se intenta borrar una clave ajena
	*/	
	public int borrarReviewsDePelicula(Pelicula p) throws DAOException{
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarReviewsDePelicula());
			st.setString(1, p.getIdPelicula().toString());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException("No permitido borrar la Review");
				
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
	* Metodo para borrar una Review por su Id
	* @param r Objeto Review con su Id definida
	* @return Devuelve 1 si ha podido borrar la Review y 0 si no
	* @throws DAOException Si se intenta borrar una clave ajena
	*/
	public int borrarReview(Review r) throws DAOException{
		PreparedStatement st = null;
		int borrado = 0;
		try {
			st = con.prepareStatement(DbQuery.getBorrarReview());
			st.setString(1, r.getId().toString());
			borrado = st.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DELETE_FK) {
				throw new DAOException(" No permitido borrar la Review");
				
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
