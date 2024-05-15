package servicios;


import java.util.List;
import daos.TransaccionesManager;
import daos.UsuarioDAO;
import domain.Usuario;
import exceptions.DAOException;
import exceptions.ServiceException;
/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase UsuarioDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see UsuarioDAO
 */
public class ServicioUsuarios {

	public ServicioUsuarios (){}
	
	/**
	* Servicio para insertar un Usuario
	* Realiza una transaccion por cada usuario insertado
	* @param user Objeto Usuario que queremos insertar
	* @throws ServiceException
	*/
	public void crearusuario(Usuario user) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao = null;
		
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			
			usuariodao.crearUsuario(user);
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		
	}
	
	
	/**
	* Servicio para recuperar un Usuario por su Alias
	* @param user Objeto Usuario con su Alias definido
	* @return Devuelve el Usuario recuperado
	* @throws ServiceException 
	*/	
	public Usuario recuperarUsuario(Usuario user) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao = null; Usuario u = null;
		
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			
			u = usuariodao.recuperarUsuario(user);
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return u ;
		
	}
	
	/**
	* Servicio para comprobar si el Alias y la Password de un Usuario coincide con el de la BBDD
	* @param user Objeto Usuario con su Alias y Password definido
	* @return Devuelve el Usuario recuperado si coinciden y null si no
	* @throws ServiceException 
	*/		
	public Usuario validarUsuario(Usuario user) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao = null; Usuario u = null;
		
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			
			u = usuariodao.validarUsuario(user);
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return u ;
		
	}	
	
	/**
	* Servicio para modificar un Usuario por su Alias, solo permite modificar Nombre, Apellidos y Telefono
	* Realiza la transaccion si el valor de modificado es mayor de 0
	* @param u Objeto Usuario con su Alias, Nombre, Apellidos y Telefono definido que queremos insertar ahora
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws ServiceException 
	*/	
	public int modificarUsuario(Usuario u) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao;
		int modificado = 0;
		try {

			trans = new TransaccionesManager();			
			usuariodao = trans.getUsuarioDAO();			
			modificado=usuariodao.modificarUsuario(u);
			
			if (modificado>0) 
				trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		
		return modificado;
	}
	
	/**
	* Servicio para modificar la Password de un Usuario con un email especifico, que coincida con el codigo insertado
	* Realiza la transaccion si el valor de modificado es mayor de 0
	* @param pass String con la potencial Nueva Password
	* @param email String con el email del Usuario
	* @param code String con el codigo de reseteo
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws ServiceException 
	*/		
	public int cambiarPassUsuario(String pass, String email, String code) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao;
		int modificado = 0;
		try {

			trans = new TransaccionesManager();			
			usuariodao = trans.getUsuarioDAO();			
			modificado=usuariodao.cambiarPass(pass, email, code);
			
			if (modificado>0) 
				trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		
		return modificado;
	}	
	
	/**
	* Servicio para generar un nuevo codigo de Reseteo en el Usuario con un email especifico
	* Realiza la transaccion si el valor de modificado es mayor de 0
	* @param email String con el email del Usuario
	* @param code String con el nuevo codigo random que queremos insertar
	* @return Devuelve 1 si lo modifica correctamente y 0 si no 
	* @throws ServiceException 
	*/		
	public int generarCodigoReseteoUsuario(String email, String code) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao;
		int modificado = 0;
		try {

			trans = new TransaccionesManager();			
			usuariodao = trans.getUsuarioDAO();			
			modificado=usuariodao.generarNuevoCodReseteo(email, code);
			
			if (modificado>0) 
				trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}		
		
		return modificado;
	}	
	
	/**
	* Servicio para saber si un Alias esta disponible (no se repite) en la BBDD
	* @param alias String con el alias del Usuario
	* @return Devuelve True si esta disponible y False si no
	* @throws ServiceException 
	*/		
	public boolean aliasDisponible(String alias) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao = null; boolean disponible = false;
		
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			
			disponible = usuariodao.aliasDisponible(alias);
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return disponible ;
		
	}
	
	/**
	* Servicio para saber si un Email esta disponible (no se repite) en la BBDD
	* @param email String con el email del Usuario
	* @return Devuelve True si esta disponible y False si no
	* @throws ServiceException 
	*/	
	public boolean emailDisponible(String email) throws ServiceException{
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao = null; boolean disponible = false;
		
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			
			disponible = usuariodao.emailDisponible(email);
			trans.closeCommit();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return disponible ;
		
	}	
	
	/**
	* Servicio para recuperar todos los Usuarios de la BBDD
	* @return Devuelve una lista de todos los Usuarios de la BBDD
	* @throws ServiceException 
	*/	
	public List<Usuario> todasLosUsuarios() throws ServiceException {
		List<Usuario> lista= null;
		TransaccionesManager trans = null;
		UsuarioDAO usuariodao;
		try {

			trans = new TransaccionesManager();
			
			usuariodao = trans.getUsuarioDAO();
			lista = usuariodao.recuperarTodosLosUsuarios();

			trans.close();
			
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}

		}
		return lista;
	}

	
}


