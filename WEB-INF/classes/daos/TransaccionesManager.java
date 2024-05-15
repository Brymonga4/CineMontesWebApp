package daos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bbdd.ConexionMySql;


import exceptions.DAOException;
import exceptions.ServiceException;



public class TransaccionesManager {

	private static final String DB_ERR = "Error de la base de datos";
	private static final String DB_CON_ERR = "Error al conectar con la base de datos";

	private Connection con;

//	 para  octener la conexion del pool de conexiones, ver WEB.xml
		  public TransaccionesManager() throws ServiceException {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/piscinaConexiones");
			con = ds.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new ServiceException(DB_ERR, e);
		} catch (NamingException e) {
			throw new ServiceException(DB_CON_ERR, e);
		}
	}

	// la conexion la establezco con la clase ConexionOracle
//	public TransaccionesManager() throws DAOException {
//
////		con=new ConexionMySql("jdbc:oracle:thin:@alfon-bb574d6f5:1521:oracle", "adrian", "adrian").getConexion();
//		con=new ConexionMySql("jdbc:mysql://localhost:3306/daw2", "bryan", "bryan").getConexion();
//		System.out.println("Conectado");
//		try {	
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			System.out.println("Nope");
//			throw new DAOException( DB_ERR ,e);	
//		}
//	}
//	
		  
		  

	public void closeCommit()throws DAOException  {
		try {
			if(con!=null){
				con.commit();
				con.close();
			}


		} catch (SQLException e) {
			throw new DAOException (DB_ERR, e);
		}
	}
	public void commit()throws DAOException  {
		try {
			if(con!=null){
				con.commit();

			}


		} catch (SQLException e) {
			throw new DAOException (DB_ERR, e);
		}
	}
	public void closeRollback() throws DAOException {
		try {
			if(con!=null){
				con.rollback();
				con.close();
			}

		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
	}
	public void rollback() throws DAOException {
		try {
			if(con!=null){
				con.rollback();

			}

		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
	}
	public void close() throws DAOException {
		try {
			if(con!=null){
				con.close();

			}

		} catch (SQLException e) {
			throw new DAOException(DB_ERR, e);
		}
		// TODO Auto-generated method stub

	}
	public Connection getConexion() {

		return con;
	}

	public PeliculaDAO getPeliculaDAO() {
		return new PeliculaDAO(con);

	}

	public PaisGeneroDAO getPaisGeneroDAO() {
		return new PaisGeneroDAO(con);

	}

	public SalaDAO getSalaDAO() {
		return new SalaDAO(con);
	}
	
	public ButacaDAO getButacaDAO() {
		return new ButacaDAO(con);
	}
	
	
	public ReviewDAO getReviewDAO() {
		return new ReviewDAO(con);
	}
	
	public FuncionDAO getFuncionDAO() {
		return new FuncionDAO(con);
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO(con);
	}
	
	public ReservaDAO getReservaDAO() {
		return new ReservaDAO(con);
	}
	
	public EntradaDAO getEntradaDAO() {
		return new EntradaDAO(con);
	}
	
	

}
