package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Butaca;
import domain.Entrada;
import domain.Funcion;
import domain.Pelicula;
import domain.Reserva;
import domain.Usuario;
import recursos.DbQuery;
import recursos.Recursos;
import exceptions.DAOException;

/**
 * Esta clase contiene los diferentes metodos (relacionados con el Objeto Entrada) usados para acceder a la base de datos
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Entrada
 */

public class EntradaDAO {
	private static final String DB_ERR = "Error de la base de datos";

	private static final int ORACLE_DUPLICATE_PK = 1;
	private static final int ORACLE_DELETE_FK = 2292;
	private static final int ORACLE_FALLO_FK = 2291;
	
	private Connection con;

	public EntradaDAO(Connection con) {
		this.con = con;
	}
	
	/**
	* Metodo para recuperar una Entrada a partir de su Id
	* @param en Objeto Entrada con su Id definida
	* @return Devuelve un Objeto Entrada recuperado 
	* @throws DAOException
	*/			
	public Entrada recuperarEntrada(Entrada en) throws DAOException {
		PreparedStatement st = null; PreparedStatement sti = null;
		ResultSet rs =null;
		Entrada entrada = null; Reserva re = new Reserva();
		Funcion fun = null; Butaca but = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarEntrada());
			st.setString(1, en.getReserva().getId());
			rs=st.executeQuery();
			if (rs.next()){	
				entrada = new Entrada(
						fun = new Funcion(rs.getInt("idfuncion")),
						but = new Butaca (rs.getInt("idbutaca")),
						re = new Reserva (rs.getString("idreserva")));
			}
				
			
		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		} finally {
			Recursos.closeResultSet(rs);
			Recursos.closePreparedStatement(st);
		}
		return entrada;	
	}
	
	/**
	* Metodo para grabar una Entrada
	* @param en Objeto Entrada que queremos insertar
	* @return Devuelve 1 si ha insertado la entrada y 0 si no
	* @throws DAOException si la Entrada ya existe, si la Funcion no existe, si la Butaca no existe o si la Reserva no existe
	*/	
	public int grabarEntrada(Entrada en) throws DAOException {
		PreparedStatement st = null; 	PreparedStatement sti = null;
		ResultSet rs = null;
		Entrada entrada = null; int grabadas = 0;
		try {
			
			st = con.prepareStatement(DbQuery.getGrabarEntrada());		
			
			st.setInt(1, en.getFuncion().getId());		
			st.setInt(2, en.getButaca().getIdButaca());
			st.setString(3, en.getReserva().getId());

			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarFuncionId());
				  sti.setInt(1, en.getFuncion().getId());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esa Funcion no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }		
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarButaca());
				  sti.setInt(1, en.getButaca().getIdButaca());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esa Butaca no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }				
			
			try{
				  sti = con.prepareStatement(DbQuery.getRecuperarReserva());
				  sti.setString(1, en.getReserva().getId());
				  rs=sti.executeQuery();
				  if(!rs.next())	
				  throw new DAOException("Esa Reserva no existe");
				 }  finally {
					  Recursos.closeResultSet( rs);	
				 }				
			
			
			grabadas = st.executeUpdate();
			
		}catch (SQLException e) {
			if (e.getErrorCode() == ORACLE_DUPLICATE_PK) {
				throw new DAOException("Esta entrada ya existe");	
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
		return grabadas;
	}

	/**
	* Metodo para recuperar una lista de Entradas compradas por un Usuario especifico
	* @param user Objeto Usuario del que queremos las entradas
	* @return Devuelve la lista de Entradas compradas por el Usuario
	* @throws DAOException
	*/			
	public List<Entrada> recuperarEntradasDeUsuario(Usuario user)throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Entrada> list = new ArrayList<Entrada>();
		Entrada entrada = null; Reserva re = new Reserva();
		Funcion fun = null; Butaca but = null;
		try {
			st = con.prepareStatement(DbQuery.getRecuperarEntradasDeUsuario());
			st.setString(1, user.getEmail());
			rs = st.executeQuery();
			while (rs.next()) {
				 entrada = new Entrada(
								  fun = new Funcion(rs.getInt("idfuncion")),
								  but = new Butaca (rs.getInt("idbutaca")),
				                  re = new Reserva(rs.getString("idreserva"))
						);
				
				list.add(entrada);
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
	* Metodo para saber si un Usuario ha visto o no Una pelicula, buscandolo a partir de las entradas que ha comprado
	* @param user Objeto Usuario
	* @param p Objeto Pelicula
	* @return Devuelve True si el Usuario ha visto la pelicula y False si no la ha visto
	* @throws DAOException
	*/		
	public boolean haVistoElUsuarioEstaPeli(Usuario user, Pelicula p)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int count = 0; boolean laHavisto;
		try {
			st = con.prepareStatement(DbQuery.getHaVistoElUsuarioEstaPeli());	
			st.setString(1, user.getEmail());
			st.setInt(2, p.getIdPelicula());
								
			rs=st.executeQuery();
			
			if (rs.next())	{
				count=rs.getInt(1);
			}	
			
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
		
		if (count >0)
		 laHavisto = true;
		else
		 laHavisto = false;
		
		return laHavisto;
	}
	
	/**
	* Metodo para saber si una Entrada esta disponible y canjearla
	* @param idFuncion int de la funcion de la entrada
	* @param identificador String del Identificador de la entrada
	* @return Devuelve True si la Entrada esta disponible y False si no lo esta
	* @throws DAOException
	*/	
	public boolean entradaDisponible(int idFuncion, String identificador)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int count = 0; boolean disponible;
		try {
			st = con.prepareStatement(DbQuery.getEntradaDisponible());	
			st.setString(1, identificador);
			st.setInt(2, idFuncion);
								
			rs=st.executeQuery();
			
			if (rs.next())	{
				count=rs.getInt(1);
			}	
			
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
		
		if (count >0)
			disponible = true;
		else
			disponible = false;
		
		return disponible;
	}
	
	/**
	* Metodo para canjear una Entrada
	* @param identificador String del Identificador de la entrada
	* @return Devuelve 1 si ha canjeado la entrada y 0 si no lo ha podido hacerlo
	* @throws DAOException
	*/		
	
	public int canjearEntrada(String identificador)throws DAOException{
		PreparedStatement st = null;
		PreparedStatement sti = null;
		ResultSet rs=null;
		int modificado = 0;
		try {
			st = con.prepareStatement(DbQuery.getCanjearEntrada());	
			st.setString(1, identificador);

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
		
	


	
	
}
