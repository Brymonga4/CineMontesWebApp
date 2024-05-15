package domain;

import exceptions.DomainException;
import util.Validator;

public class Reserva {
	private String id;//10
	private String email; //100
	
	public Reserva() {
	}

	public Reserva(String id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	
	public Reserva( String id) {
		this.id = id;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id!=null)
			this.id = id;
		else
			throw new DomainException("La id no puede ser nula");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (Validator.length(email, 3, 254)&&Validator.EmailAddress(email)) {
			this.email = email.trim();		
		} else {
			throw new DomainException("El formato del email no es válido.");
		}
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", email=" + email + "]";
	}
	
	
	
	

}
