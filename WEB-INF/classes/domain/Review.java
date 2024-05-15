package domain;

import util.Validator;
import java.sql.Timestamp;

import exceptions.DomainException;

public class Review {
	private Integer id;//10
	private Pelicula peli;
	private Usuario user;
	private String titulo;//30
	private String opinion;//2000
	private Integer valoracion;//1,2,3,4,5 estrellas?
	private Timestamp fecha; //datetime
	
	// constructores  con datos de la base de datos, es decir validados;	
	public Review() {
	}

	public Review(Integer id, Pelicula peli, Usuario user, String titulo, String opinion, Integer valoracion,
			Timestamp fecha) {
		this.setId(id); 
		this.setPeli(peli); 
		this.setUser(user); 
		this.setTitulo(titulo); 
		this.setOpinion(opinion);
		this.setValoracion(valoracion);
		this.setFecha(fecha);

	}
	
	public Review (int id) {
		this.id = id;
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
		this.peli = peli;
	}

	
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		if(user!=null)
			this.user = user;
		else
			throw new DomainException("La review debe estar asociada a un usuario existente.");
	}

	
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (Validator.length(titulo, 3, 30)) {
			this.titulo = titulo.trim();
		} else {
			throw new DomainException("La longitud del titulo no es válida.");
		}
	}

	
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		if (Validator.length(opinion, 3, 2000)) {
			this.opinion = opinion.trim();
		} else {
			throw new DomainException("La longitud de la opinion no es válida.");
		}
	}

	
	
	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		if(valoracion!=null) {
			if(valoracion>=1&&valoracion<=5) {
				this.valoracion = valoracion;
			}else
				throw new DomainException("La valoración solo puede tener un valor del 1 al 5.");
		}else
			throw new DomainException("La valoración no puede ser nula.");
	}

	
	
	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}


}// fin de la clase