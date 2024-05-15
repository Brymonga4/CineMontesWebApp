package domain;

import util.Validator;

import java.sql.Timestamp;
import java.util.Date;

import exceptions.DomainException;

public class Funcion {
	
	private Integer id;//10
	private Pelicula peli;
	private Sala sala;
	private Timestamp fecha; //datetime
	private String audio;
	private Double precio;
	

	
	// constructores  con datos de la base de datos, es decir validados;	
	public Funcion() {
		
	}


	

	public Funcion(Integer id, Pelicula peli, Sala sala, Timestamp fecha, String audio, Double precio) {
		
		this.setId(id);
		this.setPeli(peli);
		this.setSala(sala);
		this.setFecha(fecha);
		this.setAudio(audio);
		this.setPrecio(precio);
	}

	
	public Funcion (int id) {
		this.setId(id);
	}




	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		if(id!=null)
			this.id = id;
		else
			this.id = null;
	}



	public Pelicula getPeli() {
		return peli;
	}



	public void setPeli(Pelicula peli) {
		if(peli!=null)
			this.peli = peli;
		else
			throw new DomainException("La funcion debe tener una pelicula asignada existente.");
	}



	public Sala getSala() {
		return sala;
	}



	public void setSala(Sala sala) {
		if(sala!=null)
			this.sala = sala;
		else
			throw new DomainException("La funcion debe tener una sala asignada existente.");
	}



	public Timestamp getFecha() {
		return fecha;
	}



	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}



	public String getAudio() {
		return audio;
	}



	public void setAudio(String audio) {
		if (Validator.length(audio, 3, 30)) {
			this.audio = audio.trim();
		} else {
			throw new DomainException("La longitud del audio no es valida.");
		}
	}



	public Double getPrecio() {
		return precio;
	}



	public void setPrecio(Double precio) {
		if (precio!=null){
			if(precio>0) {
			     if(Validator.lengthDecimal(precio, 10,2))
			    	this.precio=precio;
			    else
			    	throw new DomainException("El precio de la funcionn no pertence al dominio permitido.");		
			}else
				throw new DomainException("El precio de la funcion no puede ser 0 o menos.");	
		}else
			throw new DomainException("El precio de la funcion no puede ser nulo.");
	}




	@Override

	public String toString() {
	return "Funcion [id=" + id + ", peli=" + peli.getTitulo() + ", sala=" + sala.getIdSala() + ", fecha=" + fecha + ", audio=" + audio
			+ ", precio=" + precio + "]";
}	


}// fin de la clase