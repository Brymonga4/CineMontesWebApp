package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import domain.*;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;
/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Usuario) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Usuario
 */
public class UsuarioDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para insertar un Usuario
	* @param user Objeto Usuario que queremos insertar
	* @throws DAOException Si el Usuario ya existe
	*/	
	public void crearUsuario(Usuario user) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;
		try {
			
			st = con.prepareStatement(DbQuery.getEscribirUsuario());
			
			st.setString(1, user.getAlias());		
			st.setString(2, user.getPass());
			st.setString(3, user.getNombre());
			st.setString(4, user.getApellidos());
			st.setString(5, user.getEmail());
			st.setString(6, user.getTelefono());
			st.setInt(7, user.getPuntos());
			st.setBoolean(8, user.isPremium());		
			st.setBoolean(9, user.isAdmin());
			
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Ese usuario ya existe");
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
	* Metodo para recuperar un Usuario por su Alias
	* @param user Objeto Usuario con su Alias definido
	* @return Devuelve el Usuario recuperado
	* @throws DAOException 
	*/
	public Usuario recuperarUsuario(Usuario user) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Usuario u = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarUsuario());
			st.setString(1,user.getAlias());
			rs=st.executeQuery();
			if (rs.next()){	
				          		  
			u = new Usuario(
					  rs.getString("alias"),
					  rs.getString("password"),
					  rs.getString("nombre"),
					  rs.getString("apellidos"),
					  rs.getString("email"),
					  rs.getString("telefono"),
					  rs.getInt("puntos"),
					  rs.getBoolean("premium"),
					  rs.getBoolean("admin")
	                  );
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return u;	
	}
	/**
	* Metodo para comprobar si el Alias y la Password de un Usuario coincide con el de la BBDD
	* @param user Objeto Usuario con su Alias y Password definido
	* @return Devuelve el Usuario recuperado si coinciden y null si no
	* @throws DAOException 
	*/	
	public Usuario validarUsuario(Usuario user) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Usuario u = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarUsuarioyPass());
			st.setString(1,user.getAlias());
			st.setString(2, user.getPass());
			rs=st.executeQuery();
			if (rs.next()){	
				          		  
			u = new Usuario(
					  rs.getString("alias"),
					  rs.getString("password"),
					  rs.getString("nombre"),
					  rs.getString("apellidos"),
					  rs.getString("email"),
					  rs.getString("telefono"),
					  rs.getInt("puntos"),
					  rs.getBoolean("premium"),
					  rs.getBoolean("admin")
	                  );
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return u;	
	}
	
	/**
	* Metodo para recuperar todos los Usuarios de la BBDD
	* @return Devuelve una lista de todos los Usuarios de la BBDD
	* @throws DAOException 
	*/	
	public List<Usuario> recuperarTodosLosUsuarios()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Usuario> list = new ArrayList<Usuario>();
		Usuario user = null;
		try {
			st = con.prepareStatement(DbQuery.getTodosLosUsuarios());
			rs = st.executeQuery();
			while (rs.next()) {
				user = new Usuario(
						  rs.getString("alias"),
						  rs.getString("password"),
						  rs.getString("nombre"),
						  rs.getString("apellidos"),
						  rs.getString("email"),
						  rs.getString("telefono"),
						  rs.getInt("puntos"),
						  rs.getBoolean("premium"),
						  rs.getBoolean("admin")
						);				
				list.add(user);
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
	* Metodo para modificar un Usuario por su Alias, solo permite modificar Nombre, Apellidos y Telefono
	* @param u Objeto Usuario con su Alias, Nombre, Apellidos y Telefono  definido que queremos insertar ahora
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws DAOException 
	*/	
	public int modificarUsuario(Usuario u)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getModificarUsuario());	
			st.setString(1, u.getNombre());
			st.setString(2, u.getApellidos());
			if (u.getTelefono()==null)
				st.setNull(3, Types.NULL);
			else
				st.setString(3, u.getTelefono());
			st.setString(4, u.getAlias());


			// ejecutamos el update.			
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
	* Metodo para generar un nuevo codigo de Reseteo en el Usuario con un email especifico
	* @param email String con el email del Usuario
	* @param code String con el nuevo codigo random que queremos insertar
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws DAOException 
	*/	
	public int generarNuevoCodReseteo(String email, String code)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getGenerarCodigoReseteoEnUsuario());	
			st.setString(2, email);
			st.setInt(1, Integer.parseInt(code) );
	
			// ejecutamos el update.			
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
	* Metodo para modificar la Password de un Usuario con un email especifico, que coincida con el codigo insertado
	* @param pass String con la potencial Nueva Password
	* @param email String con el email del Usuario
	* @param code String con el codigo de reseteo
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws DAOException 
	*/		
	public int cambiarPass(String pass, String email, String code)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getCambiarPasswordUsuario());		
			st.setString(1, pass );
			st.setString(2, email);
			st.setInt(3,Integer.parseInt(code));
	
			// ejecutamos el update.			
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
	* Metodo para saber si un Alias esta disponible (no se repite) en la BBDD
	* @param alias String con el alias del Usuario
	* @return Devuelve True si esta disponible y False si no
	* @throws DAOException 
	*/	
	public boolean aliasDisponible(String alias) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		int count = 0; boolean disponible;
		try {
			st = con.prepareStatement(DbQuery.getAliasDisponible());
			st.setString(1,alias);
			rs=st.executeQuery();
			if (rs.next()){	
				count = rs.getInt(1);
			}
			
			if (count>0) disponible = true;
			 else disponible = false;
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return disponible;	
	}
	/**
	* Metodo para saber si un Email esta disponible (no se repite) en la BBDD
	* @param email String con el email del Usuario
	* @return Devuelve True si esta disponible y False si no
	* @throws DAOException 
	*/	
	public boolean emailDisponible(String email) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		int count = 0; boolean disponible;
		try {
			st = con.prepareStatement(DbQuery.getEmailDisponible());
			st.setString(1, email);
			rs=st.executeQuery();
			if (rs.next()){	
				count = rs.getInt(1);
			}
			
			if (count>0) disponible = true;
			 else disponible = false;
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return disponible;	
	}
	
	
	
	
}
