package domain;

import util.Validator;
import exceptions.DomainException;

public class Cine {
	private Integer idCine; //10
	private String nombre;//50
	private String direccion;//
	private String cp;//10
	private String telefono; //16, puede ser null
	private String email;//100
	
	

	// constructores  con datos de la base de datos, es decir validados;	
	public Cine() {
		
	}

	public Cine(int idCine) {
		this.idCine = idCine;	
	}

	public Cine(int idCine, String nombre, String direccion, String cp, String telefono, String email) {
		super();
		this.idCine = idCine;
		this.nombre = nombre;
		this.direccion = direccion;
		this.cp = cp;
		this.telefono = telefono;
		this.email = email;
	}



	public int getIdCine() {
		return idCine;
	}



	public void setIdCine(Integer idCine) {
		if(idCine!=null)
			this.idCine = idCine;
		else
			throw new DomainException("La id no puede ser nula");
	
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		if (Validator.length(nombre, 3, 50)) 
			this.nombre = nombre.trim();
		else 
			throw new DomainException("La longitud del nombre no es válida.");
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		if (Validator.length(direccion, 8, 150)) 
			this.direccion = direccion.trim();
		else 
			throw new DomainException("La longitud de la direccion no es válida.");
	}



	public String getCp() {
		return cp;
	}



	public void setCp(String cp) {
		if (Validator.length(cp, 1, 10)) 
			this.cp = cp.trim();
		else 
			throw new DomainException("La longitud del cÃ³digo postal no es válida.");
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		if(telefono==null){
			this.telefono=telefono;
		}
		else {
			if (Validator.telephone(telefono, 1, 9)) {
			this.telefono = telefono.trim();
		    } else {
			throw new DomainException("El teléfono no es válido.");
		    }
		}
	}



	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		if (Validator.email(email, 1, 100)) {
			this.email = email.trim();		
		} else {
			throw new DomainException("El formato del email no es válido.");
		}
	}

}// fin de la clase