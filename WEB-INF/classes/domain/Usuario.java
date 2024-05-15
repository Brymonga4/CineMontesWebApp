package domain;

import util.Validator;
import exceptions.DomainException;

public class Usuario {
	private String alias; //12
	private String pass; //50
	private String nombre; //40
	private String apellidos; //40
	private String email; //100
	private String telefono; //16 puede ser NULL
	private Integer puntos; //10 int, predt 0
	private boolean premium;
	private boolean admin;


//	private static final int NOMBRE_MIN = 1;

	// constructores  con datos de la base de datos, es decir validados;	
	public Usuario() {
	}
	
	public Usuario (String alias) {
		this.alias = alias;
	}

	public Usuario (String alias, String pass) {
		this.setAlias(alias);
		this.setPass(pass);
	}
	
	public Usuario(String alias, String nombre, String apellidos,  String telefono) {
		super();
		this.setAlias(alias);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setTelefono(telefono);
	}
	
	
	public Usuario(String alias, String pass, String nombre, String apellidos, String email, String telefono,
			int puntos, boolean premium, boolean admin) {
		super();
		this.setAlias(alias);
		this.setPass(pass);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.email = email;
		this.setTelefono(telefono);
		this.setPuntos(puntos);
		this.premium = premium;
		this.admin = admin;
	}
	


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		if (Validator.length(alias, 1, 12)) {
			this.alias = alias.trim();
		} else {
			throw new DomainException("La longitud del alias no es válida.");
		}
	}


	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		if (Validator.length(pass, 5, 50)) {
			this.pass = pass.trim();
		} else {
			throw new DomainException("La longitud de la contraseña no es válida.");
		}
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		if (Validator.length(nombre, 1, 40)) {
			this.nombre = nombre.trim();
		} else {
			throw new DomainException("La longitud del nombre no es válida.");
		}
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		if (Validator.length(apellidos, 1, 40)) {
			this.apellidos = apellidos.trim();
		} else {
			throw new DomainException("La longitud de los apellidos no es válida.");
		}
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		if (Validator.EmailAddress(email)) {
			this.email = email.trim();		
		} else {
			throw new DomainException("El formato del email no es válido.");
		}
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		if(telefono==null || telefono.trim() == ""){
			this.telefono=null;
		}
		else {
			if (Validator.telephone(telefono, 1, 9)) {
				this.telefono = telefono.trim();
		    } else {
		    	throw new DomainException("El teléfono no es válido.");
		    }
		}	
	}


	public Integer getPuntos() {
		return puntos;	
	}


	public void setPuntos(Integer puntos) {
		if(puntos!=null) {
			if (puntos>=0)
				this.puntos=puntos;
			else 
				throw new DomainException("Los puntos no pueden ser negativos.");
		}else
			throw new DomainException("Los puntos no pueden ser nulos.");
	}


	public boolean isPremium() {
		return premium;
	}


	public void setPremium(int premium) {
		if(premium==0)
			this.premium = false;
		else
			this.premium = true;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(int admin) {
		if(admin==0)
			this.admin = false;
		else
			this.admin = true;
	}

	@Override
	public String toString() {
		return "Usuario [alias=" + alias + ", pass=" + pass + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", email=" + email + ", telefono=" + telefono + ", puntos=" + puntos + ", premium=" + premium
				+ ", admin=" + admin + "]";
	}
	
	
	
	
}// fin de la clase