package domain;

import util.Validator;
import exceptions.DomainException;

public class Sala {
	private Integer idSala; //10
	private Cine cine;
	private String nombre;//50
	private String distribucion;//500
	private boolean sp_digi;
	private boolean sp_3d;
	
	// constructores  con datos de la base de datos, es decir validados;	
	public Sala() {
		
	}
	
	public Sala(int idSala) {
		this.idSala = idSala;
	}
	
	public Sala(int idSala, Cine cine, String nombre, String distribucion, boolean sp_digi, boolean sp_3d) {
		super();
		this.idSala = idSala;
		this.cine = cine;
		this.nombre = nombre;
		this.distribucion = distribucion;
		this.sp_digi = sp_digi;
		this.sp_3d = sp_3d;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(Integer idSala) {
		if(idSala!=null)
			this.idSala = idSala;
		else
			throw new DomainException("La id no puede ser nula");
	}

	public Cine getCine() {
		return cine;
	}

	public void setCine(Cine cine) {
		if(cine!=null)
			this.cine = cine;
		else
			throw new DomainException("La sala debe estar asociada a un Cine.");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (Validator.length(nombre, 3, 50)) 
			this.nombre = nombre.trim();
		else 
			throw new DomainException("La longitud del nombre no es valida.");
	}

	public String getDistribucion() {
		return distribucion;
	}

	public void setDistribucion(String distribucion) {
		if(distribucion==null)
			this.distribucion=distribucion;
		else {
			if (Validator.length(distribucion, 1, 500)) 
				this.distribucion = distribucion.trim();
			else 
				throw new DomainException("Esa distribucion no es valida.");
		}
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

	@Override
	public String toString() {
		return "Sala [idSala=" + idSala + ", cine=" + cine + ", nombre=" + nombre + ", distribucion=" + distribucion
				+ ", sp_digi=" + sp_digi + ", sp_3d=" + sp_3d + "]";
	}


	

}// fin de la clase