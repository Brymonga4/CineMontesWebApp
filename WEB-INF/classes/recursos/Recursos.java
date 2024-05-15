package recursos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.Properties;
import java.util.Random;

import domain.Butaca;
import exceptions.DAOException;


/**
 * Esta clase contiene algunos metodos de Utilidad  (algunos proporcionados por Angel)
 * @author Bryan Montesdeoca 
 * @version 1.0
 */
public class Recursos {

	public static void closeResultSet(ResultSet rs) throws DAOException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error de la base de datos", e);
		}
	}

	public static void closePreparedStatement(PreparedStatement st) throws DAOException {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error de la base de datos", e);
		}
	}
	public static void closeCallableStatement(CallableStatement st) throws DAOException {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error de la base de datos", e);
		}
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String randomString(int len) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	static final String AB1 = "0123456789";

	public static int randomEntero(int len) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB1.charAt(rnd.nextInt(AB1.length())));
		return Integer.valueOf(sb.toString());
	}
	static final String AB2 = "0123456789";

	public static String randomStringNumero(int len) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB2.charAt(rnd.nextInt(AB2.length())));
		return sb.toString();
	}
	
	

	/**
	* Metodo para que quita acentos y caracteres especificos (los prohibidos en los nombres de archivos de windows) de un String
	* Ademas sustituye los espacios por "_" barras_bajas
	* @param str String que queremos limpiar
	* @return Devuelve un String limpio de todos esos caracteres
	*/		
	public static String limpiarString(String str) {
		
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]|:|\\\\|/|\\*|\"|<|>|\\?", "");
		str = str.replaceAll("\\s","_");
		
		return str;
	}

	/**
	* Metodo para que quita acentos y caracteres especificos (los prohibidos en los nombres de archivos de windows) de un String
	* @param str String que queremos limpiar
	* @return Devuelve un String limpio de todos esos caracteres
	*/		
	public static String limpiarStringSoloSimbolos(String str) {
		
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]|:|\\\\|/|\\*|\"|<|>|\\?", "");
		//str = str.replaceAll("\\s","_");
		
		return str;
	}	
	
	/**
	* Metodo que analiza el String pasado como parametro con un Switch para devolver su equivalente en path de Imagenes
	* @param str String que queremos analizar
	* @return Devuelve un String con el Path donde se encuentra la imagen de ese String
	*/		
	public static String edadRecomendada (String str) {
		String edadRechPath= "images/edadRecomendada/";
		
		switch(str) {
		case "TP": str = "all-ages-TP.png"; break;
		case "7": str = "mayores-7.png"; break;
		case "12": str = "mayores-12.png"; break;
		case "16": str = "mayores-16.png"; break;
		case "18": str = "mayores-18.png"; break;
		case "X": str = "golfa.png"; break;
		default: str = "pendiente-calificacion.png"; 
		}
		
		return edadRechPath+str;
	}
	
	/**
	* Metodo que modifica los Url de Youtube para que puedan ser embebidos en un jsp mas facilmente
	* @param str String que queremos analizar
	* @return Devuelve un String con el nuevo enlace listo para ser insertado en un iframe
	*/	
	public static String videoYTembed(String str) {
		
		if (str.indexOf("watch?v=")!=-1)
			str = str.replace("watch?v=", "embed/");
		
		return str;
	}
	
	
	/**
	* Metodo que calcula el Tanto Por Ciento al que equivale el double que se le pasa como parametro
	* @param d Double valor de la nota de una Pelicula
	* @return Devuelve un String con el Tanto Por Ciento adecuado  para ser usado en un estilo
	*/		
	public static String valoracionTantoPorCiento(double d) {		
		double resultado = (d * 100)/ 5 ;
		return ""+resultado+"%";
	}
	
	/**
	* Metodo que analiza la Butaca pasada como parametro y determina de que tipo es
	* @param b Butaca de la que queremos saber el tipo
	* @return Devuelve el String correspondiente con su tipo
	*/	
	public static String tipoButacaEnString (Butaca b) {
		String tipoButaca = "Normal";
		
		if (b.getTipo()>0.9&&b.getTipo()<1.4) {
			tipoButaca = "Normal";
		} else if(b.getTipo()>1.4) {
			tipoButaca = "Premium";
		} else if(b.getTipo()<0.9) {
			tipoButaca = "Minusválido";
		}
		return tipoButaca;
	}
	
	/**
	* Metodo que devuelve las Properties de un Email, listas para ser usados para enviar un Email con Gmail
	* @return Devuelve un Objeto Properties ya definido en el metodo
	*/	
	public static Properties getEmailProp() {
		
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
		
		return properties;
	}
	
	
	
}
