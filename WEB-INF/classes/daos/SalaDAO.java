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
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Sala) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Sala
 */
public class SalaDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public SalaDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para insertar una Sala
	* @param s Objeto Sala que queremos insertar
	* @throws DAOException Si la Sala ya Existe
	*/
	public void crearSala(Sala s) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;				
		try {
			
			st = con.prepareStatement(DbQuery.getInsertarSala());
			
			st.setInt(1, s.getCine().getIdCine());
			st.setString(2, s.getNombre());
			st.setString(3, s.getDistribucion());
			st.setBoolean(4, s.isSp_digi());
			st.setBoolean(5, s.isSp_3d());				
					
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Esta sala ya existe");
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
	* Metodo para recuperar todas las Salas
	* @return Devuelve la lista con todas las Salas de la BBDD
	* @throws DAOException
	*/	
	public List<Sala> recuperarTodasSalas()throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Sala> list = new ArrayList<Sala>();
		Sala sala=null; Cine cine = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarSalas());
			rs = st.executeQuery();
			while (rs.next()) {
				 sala = new Sala(
								  rs.getInt("idsala"),
								  cine = new Cine(rs.getInt("idcine")),
								  rs.getString("nombre"),
								  rs.getString("distribucion"),
								  rs.getBoolean("soporte_digital"),
								  rs.getBoolean("soporte_3d")
						);
				
				list.add(sala);
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
	* Metodo para recuperar una Sala por su Id
	* @param s int Identificador de un Objeto Sala 
	* @return Devuelve la Sala recuperada
	* @throws DAOException
	*/
	public Sala recuperarSala (int s) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Sala sala = null; Cine cine = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarSalaId());
			st.setInt(1, s);
			rs=st.executeQuery();
			if (rs.next()){					          		  
				sala = new Sala(
						rs.getInt("idsala"),
						cine = new Cine(rs.getInt("idcine")),
						rs.getString("nombre"),
						rs.getString("distribucion"),
						rs.getBoolean("soporte_digital"),
						rs.getBoolean("soporte_3d")
						);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}		
		return sala ;	
	}
	
	
	/**
	* Metodo para recuperar una Sala por su Nombre
	* @param nombre String con el Nombre de un Objeto Sala 
	* @return Devuelve la Sala recuperada
	* @throws DAOException
	*/	
	public Sala recuperarSala (String nombre) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Sala sala = null; Cine cine = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarSalaNombre());
			st.setString(1, nombre);
			rs=st.executeQuery();
			if (rs.next()){					          		  
				sala = new Sala(
						rs.getInt("idsala"),
						cine = new Cine(rs.getInt("idcine")),
						rs.getString("nombre"),
						rs.getString("distribucion"),
						rs.getBoolean("soporte_digital"),
						rs.getBoolean("soporte_3d")
						);
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}		
		return sala ;	
	}
	
	
}
