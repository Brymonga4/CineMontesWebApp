package domain;

import exceptions.DomainException;

public class Entrada {
	private Funcion funcion;
	private Butaca butaca;
	private Reserva reserva;
	private boolean disponible = true;
	public Entrada() {
		
	}

	public Entrada(Funcion funcion, Butaca butaca, Reserva reserva) {
		super();
		this.funcion = funcion;
		this.butaca = butaca;
		this.reserva = reserva;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		if(funcion!=null)
			this.funcion = funcion;
		else
			throw new DomainException("La entrada debe estar asociada a una función existente.");
	}

	public Butaca getButaca() {
		return butaca;
	}

	public void setButaca(Butaca butaca) {
		if(butaca!=null)
			this.butaca = butaca;
		else
			throw new DomainException("La entrada debe estar asociada a una butaca existente.");
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		if(reserva!=null)
			this.reserva = reserva;
		else
			throw new DomainException("La entrada debe estar asociada a una reserva existente.");
	}
	
	
	public boolean isdisponible() {
		return disponible;
	}
	public void setdisponible(int disponible) {
		if(disponible==0)
			this.disponible = false;
		else
			this.disponible = true;
	}	
	

	@Override
	public String toString() {
		return "Entrada [funcion=" + funcion + ", butaca=" + butaca.getIdButaca() + ", reserva=" + reserva.getId() + "]";
	}
	
	

	
	
}
