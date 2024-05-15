package objetos;

import java.util.ArrayList;
import java.util.List;

import domain.Entrada;
/**
 * Esta clase contiene los un Objeto personalizado de Entrada, que contiene una Lista de Entradas y el precio Total calculado de la suma de Todas.
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see Entrada
 */
public class EntradaCompleta {
	private List <Entrada> listEntradas;
	Double precioTotal;
	
	public EntradaCompleta() {
		this.listEntradas = new ArrayList<Entrada>();
		this.precioTotal = 0.0;
	}
	
	public EntradaCompleta(List<Entrada> listEntradas, Double precioTotal) {
		super();
		this.listEntradas = listEntradas;
		this.precioTotal = precioTotal;
	}

	public List<Entrada> getListEntradas() {
		return listEntradas;
	}

	public void setListEntradas(List<Entrada> listEntradas) {
		this.listEntradas = listEntradas;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	@Override
	public String toString() {
		return "EntradaCompleta [listEntradas=" + listEntradas + ", precioTotal=" + precioTotal + "]";
	}
	
	
	
	
}
