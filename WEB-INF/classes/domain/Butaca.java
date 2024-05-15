package domain;

import util.Validator;
import exceptions.DomainException;

public class Butaca {
	private Integer idButaca; //10
	private Sala sala;
	private int butaca;//2
	private int fila;//2
	private Double tipo;//Multiplicador por el tipo de butaca
	
	// constructores  con datos de la base de datos, es decir validados;	
	public Butaca() {
		
	}

	public Butaca(Integer idButaca, Sala sala, int butaca, int fila, double tipo) {
		super();
		this.idButaca = idButaca;
		this.sala = sala;
		this.butaca = butaca;
		this.fila = fila;
		this.tipo = tipo;
	}
	
	public Butaca (int idButaca) {
		this.idButaca = idButaca;
	}
	
	public Butaca (int idButaca, int butaca, int fila, double tipo) {
		this.idButaca = idButaca;
		this.butaca = butaca;
		this.fila = fila;
		this.tipo = tipo;
	}	

	public int getIdButaca() {
		return idButaca;
	}

	public void setIdButaca(Integer idButaca) {
		if(idButaca!=null)
			this.idButaca = idButaca;
		else
			this.idButaca = null;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		if(sala!=null)
			this.sala = sala;
		else
			throw new DomainException("La butaca debe estar asociada a una Sala existente.");
	}
	
//Número butaca y fila

	public int getButaca() {
		return butaca;
	}

	public void setButaca(int butaca) {
		this.butaca = butaca;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}
	
//Fin buatca y fila
	
	public double getTipo() {
		return tipo;
	}

	public void setTipo(Double tipo) {
		if (tipo!=null){
			if(tipo>0) {
			     if(Validator.lengthDecimal(tipo, 10,2))
			    	this.tipo=tipo;
			    else
			    	throw new DomainException("El tipo de butaca no pertence al dominio permitido.");		
			}else
				throw new DomainException("El tipo de butaca no puede ser 0 o menos.");	
		}else
			throw new DomainException("El tipo de butaca no puede ser nulo.");
	}
	

	@Override
	public String toString() {
		return "Butaca [idButaca=" + idButaca + ", sala=" + sala.getIdSala() + ", butaca=" + butaca + ", fila=" + fila + ", tipo="
				+ tipo + "]";
	}
	
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Butaca or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Butaca)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        final Butaca c = (Butaca) o; 
          
        // Compare the data members and return accordingly  
        if (idButaca.equals(c.idButaca))
        	return true;
        else
        	return false;

        	 
    } 
 		

}// fin de la clase