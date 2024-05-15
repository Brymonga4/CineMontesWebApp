package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Funcion;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;

/**
 * Esta clase contiene los diferentes metodos relacionados con las tablas Pais y Genero, sin relacion directa a la BBDD
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
public class PaisGeneroDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public PaisGeneroDAO(Connection con) {
		this.con = con;
	}

	/**
	* Metodo para recuperar todos los Paises
	* @return Devuelve una Lista de Strings con todos los Paises
	* @throws DAOException
	*/		
	public List<String> recuperarPaises() throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		List<String> paisesList = new ArrayList<String>();

		try {
			st = con.prepareStatement(DbQuery.getRecuperarPaises());
			rs=st.executeQuery();
			while (rs.next()){
				paisesList.add(rs.getString("nombre"));
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return paisesList;	
	}
	
	/**
	* Metodo para recuperar todos los Generos
	* @return Devuelve una Lista de Strings con todos los Generos
	* @throws DAOException
	*/		
	public List<String> recuperarGeneros() throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		List<String> genList = new ArrayList<String>();

		try {
			st = con.prepareStatement(DbQuery.getRecuperarGeneros());
			rs=st.executeQuery();
			while (rs.next()){					          		  
				genList.add(rs.getString("genero"));
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return genList;	
	}

	
	
}
