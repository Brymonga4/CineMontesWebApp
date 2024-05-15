package domain;

import util.Validator;

import java.sql.Date;

import exceptions.DomainException;
import objetos.Pais;

public class Pelicula {
	private Integer idPelicula; //10
	private String titulo; //50
	private String titulo_orig;//50
	private Date estreno; //dd/mm/yy
	private Pais pais;
	private String genero; //200
	private String actores;//500
	private String directores;//100
	private String guionistas;//500
	private String productores;//100
	private Integer duracion;//10, en minutos
	private String sinopsis; //10000
	private boolean sp_digi;
	private boolean sp_3d;
	private boolean vo;
	private boolean esp;
	private String imagen;
	private String trailer; //100
	private String edad_rec;//2, *TP  7 12  16  18  X*
	// constructores  con datos de la base de datos, es decir validados;	
	public Pelicula() {
		
	}
	

	public Pelicula(Integer idPelicula, String titulo, String titulo_orig, Date estreno, Pais pais, String genero,
			String actores, String directores, String guionistas, String productores, Integer duracion, String sinopsis,
			int sp_digi, int sp_3d, int vo, int esp, String imagen, String trailer, String edad_rec) {

		this.setIdPelicula(idPelicula);
		this.setTitulo(titulo);
		this.setTitulo_orig(titulo_orig);
		this.setEstreno(estreno);
		this.setPais(pais);
		this.setGenero(genero);
		this.setActores(actores);
		this.setDirectores(directores);
		this.setGuionistas(guionistas);
		this.setProductores(productores);
		this.setDuracion(duracion);
		this.setSinopsis(sinopsis);
		this.setSp_digi(sp_digi);
		this.setSp_3d(sp_3d);
		this.setVo(vo);
		this.setEsp(esp);
		this.setImagen(imagen);
		this.setTrailer(trailer);
		this.setEdad_rec(edad_rec);

	}
	
	public Pelicula (int idPelicula) {
		this.setIdPelicula(idPelicula);
	}
	
	
	public Integer getIdPelicula() {
		return idPelicula;
	}
	public void setIdPelicula(Integer idPelicula) {
		if(idPelicula!=null)
			this.idPelicula = idPelicula;
		else
			this.idPelicula= null;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		if (Validator.length(titulo, 3, 50)) 
			this.titulo = titulo.trim();
		else 
			throw new DomainException("La longitud del titulo no es válida.");
	}
	
	public String getTitulo_orig() {
		return titulo_orig;
	}
	public void setTitulo_orig(String titulo_orig) {
		if (Validator.length(titulo_orig, 3, 50)) 
			this.titulo_orig = titulo_orig.trim();
		else 
			throw new DomainException("La longitud del titulo original no es válida.");
	}
	
	public Date getEstreno() {
		return estreno;
	}
	public void setEstreno(Date estreno) {
		this.estreno = estreno;
	}
	
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		if(pais!=null)
			this.pais = pais;
		else
			throw new DomainException("El país de la película no puede ser nulo.");
	}
	
	//Funcion para validar, cadena strings??
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		if (Validator.length(genero, 3, 350)) {
			this.genero = genero.trim();
		}
		else 
			throw new DomainException("La longitud de los generos no es válida.");
	}
	
	
	public String getActores() {
		return actores;
	}
	public void setActores(String actores) {
			if (Validator.length(actores, 3, 500)) 
				this.actores = actores.trim();
			else 
				throw new DomainException("La longitud de los actores no es válida.");
	}
	
	
	public String getDirectores() {
		return directores;
	}
	public void setDirectores(String directores) {
		if (Validator.length(directores, 8, 100)) 
			this.directores = directores.trim();
		else 
			throw new DomainException("La longitud de los directores no es válida.");
	}
	
	
	public String getGuionistas() {
		return guionistas;
	}
	public void setGuionistas(String guionistas) {
		if (Validator.length(guionistas, 8, 500)) 
			this.guionistas = guionistas.trim();
		else 
			throw new DomainException("La longitud de los guionistas no es válida.");
	}
	
	
	public String getProductores() {
		return productores;
	}
	public void setProductores(String productores) {
		if (Validator.length(productores, 3, 500)) 
			this.productores = productores.trim();
		else 
			throw new DomainException("La longitud de los productores no es válida.");
	}
	
	
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		if (duracion!=null){
			if(duracion>0) {
			    	this.duracion=duracion;
			}else
				throw new DomainException("La duración no puede ser 0 o menos.");	
		}else
			throw new DomainException("La duración no puede ser nula.");

	}
	
	
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		if (Validator.length(sinopsis, 10, 10000)) 
			this.sinopsis = sinopsis.trim();
		else 
			throw new DomainException("La longitud de la sipnosis no es válida.");
	}
	
	
	public boolean isSp_digi() {
		return sp_digi;
	}
	public void setSp_digi(int sp_digi) {
		if(sp_digi==0)
			this.sp_digi = false;
		else
			this.sp_digi = true;
	}
	
	
	public boolean isSp_3d() {
		return sp_3d;
	}
	public void setSp_3d(int sp_3d) {
		if(sp_3d==0)
			this.sp_3d = false;
		else
			this.sp_3d = true;
	}
	
	
	public boolean isVo() {
		return vo;
	}
	public void setVo(int vo) {
		if(vo==0)
			this.vo = false;
		else
			this.vo = true;
	}
	
	
	public boolean isEsp() {
		return esp;
	}
	public void setEsp(int esp) {
		if(esp==0)
			this.esp = false;
		else
			this.esp = true;
	}
	
	//Validacion de url, path??
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	
	public String getEdad_rec() {
		return edad_rec;
	}
	public void setEdad_rec(String edad_rec) {
		
		if (edad_rec!=null)
			switch(edad_rec) {
			case "TP": case "7": case "12": case "16": case "18": case "X": this.edad_rec = edad_rec; break;
			default: this.edad_rec = "PC";
			}
		else
			throw new DomainException("La edad recomendada no puede ser nula.");
		
	}
	@Override
	public String toString() {
		return "Pelicula [idPelicula=" + idPelicula + ", titulo=" + titulo + ", titulo_orig=" + titulo_orig
				+ ", estreno=" + estreno + ", pais=" + pais + ", genero=" + genero + ", actores=" + actores
				+ ", directores=" + directores + ", guionistas=" + guionistas + ", productores=" + productores
				+ ", duracion=" + duracion + ", sinopsis=" + sinopsis + ", sp_digi=" + sp_digi + ", sp_3d=" + sp_3d
				+ ", vo=" + vo + ", esp=" + esp + ", imagen=" + imagen + ", trailer=" + trailer + ", edad_rec="
				+ edad_rec + "]";
	}
	
	
	
}// fin de la clase