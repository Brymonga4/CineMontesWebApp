package objetos;


/**
 * Esta clase contiene los atributos de un Objeto Pais, que no tiene relacion directa con la BBDD Cine
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
public class Pais {
	private Integer id; //11
	private String iso; //2, puede ser nulo
	private String nombre;//80, unique
	
	public Pais(Integer id, String iso, String nombre) {
		super();
		this.id = id;
		this.iso = iso;
		this.nombre = nombre;
	}
	
	public Pais (String nombre) {
		this.nombre=nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Pais [id=" + id + ", iso=" + iso + ", nombre=" + nombre + "]";
	}
	
	
	

}
