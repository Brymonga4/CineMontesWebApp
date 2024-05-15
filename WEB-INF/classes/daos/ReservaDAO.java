package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.Pelicula;
import domain.Reserva;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;
import objetos.Pais;

/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Reserva) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Reserva
 */
public class ReservaDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para recuperar una Reserva por su Id
	* @param re Objeto Reserva con su id Definida que queremos recuperar
	* @return Devuelve la Reserva recuperada
	* @throws DAOException
	*/		
	public Reserva recuperarReserva(Reserva re) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Reserva reserva = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarReserva());
			st.setString(1, re.getId());
			rs=st.executeQuery();
			if (rs.next()){				          		  
				reserva = new Reserva(
						rs.getString("idreserva"),
						rs.getString("email"));
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return reserva;	
	}
	
	/**
	* Metodo para insertar una Reserva, genera un identificador Random de 9 caracteres y si por algun casual se repite
	* el metodo se vuelve a llamar e intentar generar otro nuevo
	* @param re Objeto Reserva que queremos insertar con su email definido
	* @throws DAOException 
	*/	
	public Reserva insertarReserva(Reserva re) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;
		String identificadorR;
		try {
			
			st = con.prepareStatement(DbQuery.getGrabarReserva());
			identificadorR = Recursos.randomString(9);
			
			
			st.setString(1, identificadorR);		
			st.setString(2, re.getEmail());
			
			re.setId(identificadorR);
			//System.out.println(identificadorR);
			st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				//System.out.println("Sa repetio");
				re = insertarReserva(re);
				
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
		return re;
	}

	
}
