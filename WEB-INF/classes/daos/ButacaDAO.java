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
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Butaca) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Butaca
 */
public class ButacaDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public ButacaDAO(Connection con) {
		this.con = con;
	}
	
	
	/**
	* Metodo para insertar una Butaca
	* @param b Objeto Butaca que queremos insertar
	* @throws DAOException Si la Butaca ya existe o si la Sala no existe
	*/		
	public void insertarButaca (Butaca b) throws DAOException {
		PreparedStatement st = null; 
		ResultSet rs = null; PreparedStatement sti = null;
		
		try {
			
			st = con.prepareStatement(DbQuery.getInsertarButaca());
			
			//st.setString(1, null);		
			st.setInt(1, b.getSala().getIdSala());
			st.setInt(2, b.getButaca());
			st.setInt(3, b.getFila());
			st.setDouble(4, b.getTipo());
					
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarSalaId());
				  sti.setInt(1, b.getSala().getIdSala());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esta sala no existe");
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
		}		
	}

	/**
	* Metodo para recuperar una Lista de Butacas de una Sala especifica
	* @param sala Objeto Sala de la que queremos recuperar todas sus butacas
	* @return Devuelve una lista con todas las Butacas de una Sala específica
	* @throws DAOException
	*/		
	public List<Butaca> recuperarTodasButacasdeSala(Sala sala)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Butaca> list = new ArrayList<Butaca>();
		Butaca butaca = null; Integer i1 = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarButacasDeSala());
			st.setInt(1, sala.getIdSala());
			rs = st.executeQuery();
			while (rs.next()) {
				butaca = new Butaca(
								  i1 = new Integer(rs.getInt("id")),
								  sala = new Sala(rs.getInt("sala")),
								  rs.getInt("butaca"),
								  rs.getInt("fila"),
				                  rs.getDouble("tipo")
						);				
				list.add(butaca);
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
	* Metodo para recuperar una Lista de Butacas, que contiene todas las butacas YA ocupadas de una funcion en concreto
	* @param f Objeto Funcion del que queremos recuperar todas las butacas ocupadas
	* @return Devuelve una lista con las butacas ocupadas de una funcion
	* @throws DAOException
	*/	
	public List<Butaca> recuperarButacasOcupadasDeFuncion(Funcion f)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Butaca> list = new ArrayList<Butaca>();
		Butaca butt = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarButacasOcupadasDeFuncion());
			st.setInt(1, f.getId());
			rs = st.executeQuery();
			while (rs.next()) {
				butt = new Butaca(rs.getInt("idbutaca"));				
				list.add(butt);
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
	* Metodo para recuperar una Butaca a partir de su Id
	* @param but Objeto Butaca con su IdButaca definida
	* @return Devuelve la Butaca recuperada de la BBDD
	* @throws DAOException
	*/		
	public Butaca recuperarButaca(Butaca but) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs =null;
		Butaca b=null; Sala s =null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarButaca());
			st.setInt(1,but.getIdButaca());
			rs=st.executeQuery();
			if (rs.next()){	
				          		  
			b = new Butaca(
					  rs.getInt("id"),
					  s = new Sala(rs.getInt("sala")),
					  rs.getInt("butaca"),
					  rs.getInt("fila"),
					  rs.getDouble("tipo")
	                  );
			}
			
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return b;	
	}	


	
	
}
