package recursos;

public class DbQuery {
		
	//Peliculas
	
	private static final String RecuperarTodasPeliculas = "SELECT idpelicula, titulo, titulo_orig, estreno, pais, genero, actores, "
			+ "directores, guionistas, productores, duracion, sinopsis, "
			+ "soporte_digital, soporte_3d, version_original, version_esp, imagen, trailer, edad_recom FROM peliculas";
	
	private static final String InsertarPelicula = "INSERT into peliculas (idpelicula, titulo, titulo_orig, estreno, pais, "
			+ "genero, actores, directores, guionistas, productores, duracion, sinopsis, soporte_digital, "
			+ "soporte_3d, version_original, version_esp, imagen, trailer, edad_recom) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String RecuperarPelicula = "SELECT idpelicula, titulo, titulo_orig, estreno, pais, genero, actores, "
			+ "directores, guionistas, productores, duracion, sinopsis, "
			+ "soporte_digital, soporte_3d, version_original, version_esp, imagen, trailer, edad_recom FROM peliculas "
			+ "where idpelicula = ?";
	
	private static final String ModificarPelicula = "UPDATE peliculas set titulo=?, titulo_orig=?, estreno=?, pais=?, genero=?, "
			+ "actores=?, directores=?, guionistas=?, productores=?, duracion=?, sinopsis=?, "
			+ "soporte_digital=?, soporte_3d=?, version_original=?, version_esp=?, imagen=?, trailer=?, edad_recom=? "
			+ "where idpelicula = ?";
	
	private static final String ModificarPeliculaConcurrente = "UPDATE peliculas set titulo=?, titulo_orig=?, estreno=?, pais=?, genero=?, "
			+ "actores=?, directores=?, guionistas=?, productores=?, duracion=?, sinopsis=?, "
			+ "soporte_digital=?, soporte_3d=?, version_original=?, version_esp=?, imagen=?, trailer=?, edad_recom=? "
			
			+ "where idpelicula = ? and titulo=? and titulo_orig=? and estreno=? and pais=? and genero=? "
			+ "and actores=? and directores=? and guionistas=? and productores=? and duracion=? and sinopsis=? "
			+ "and soporte_digital=? and soporte_3d=? and version_original=? and version_esp=? and imagen=? and trailer=? and edad_recom=?";
	
	private static final String RecuperarDuracionPelicula = "SELECT duracion FROM cine.peliculas where idpelicula = ?";
	private static final String BorrarPelicula = "DELETE from peliculas where idpelicula = ? ";
	
	private static final String PeliculasQueElUsuarioHaVisto ="SELECT idpelicula from peliculas where idpelicula in " + 
			"(select idpelicula from funciones where idfuncion in" + 
			"	(select idfuncion from entradas where idreserva in" + 
			"		(select idreserva from reservas where email = ?)))";
	
	private static final String CarteleraSemana = "SELECT idpelicula, titulo, titulo_orig, estreno, pais, genero, actores,directores, "
			+ "guionistas, productores, duracion, sinopsis,soporte_digital, soporte_3d, version_original, "
			+ "version_esp, imagen, trailer, edad_recom FROM peliculas where idpelicula in "
			+ "(select idpelicula FROM funciones WHERE fecha BETWEEN  curdate() AND (curdate() + INTERVAL 14 DAY));";
	
	//Salas
	
	private static final String RecuperarSalas = "SELECT idsala, idcine, nombre, distribucion, soporte_digital, soporte_3d from salas";
	private static final String RecuperarSalaId = "SELECT idsala, idcine, nombre, distribucion, soporte_digital, soporte_3d from salas where idsala = ? ";
	private static final String RecuperarSalaNombre = "SELECT idsala, idcine, nombre, distribucion, soporte_digital, soporte_3d from salas where BINARY(nombre) = ? ";
	private static final String InsertarSala = "INSERT INTO SALAS (idcine, nombre, distribucion,soporte_digital, soporte_3d) VALUES (?,?,?,?,?)";
	//Butacas
	private static final String RecuperarIdButaca = "SELECT max(id) from butacas";
	private static final String InsertarButaca = "INSERT INTO BUTACAS (sala, butaca, fila, tipo) VALUES (?,?,?,?)";
	private static final String RecuperarButacasDeSala = "SELECT id, sala, butaca, fila, tipo from butacas where sala=? order by fila";
	private static final String RecuperarButacasOcupadasDeFuncion = "SELECT idbutaca FROM entradas where idfuncion = ?";
	private static final String RecuperarButaca = "SELECT id, sala, butaca, fila, tipo from butacas where id= ?";
//	private static final String InsertarButaca = "INSERT INTO BUTACAS (id, sala, butaca, fila, tipo) VALUES (?,?,?,?,?)";
	
	//Genero y Paises	
	private static final String RecuperarGeneros = "SELECT genero from generos";	
	private static final String RecuperarPaises = "SELECT nombre from paises";	
	private static final String RecuperarPais = "SELECT nombre from paises where nombre=?";
	
	//Reviews
	private static final String TodasLasReviews = "SELECT id, pelicula, alias, titulo, opinion, valoracion, fecha from reviews";
	private static final String RecuperarReviewdePeli = "SELECT id, pelicula, alias, titulo, opinion, valoracion, fecha from reviews where pelicula=?";
	private static final String EscribirReview = "INSERT INTO REVIEWS (id, pelicula, alias, titulo, opinion, valoracion, fecha) VALUES (?,?,?,?,?,?,?)";
	private static final String ReviewsDeUsuarioenPeli ="SELECT count(*) from reviews where alias = ? and pelicula = ?";
	private static final String ValoracionPelicula = "SELECT avg(valoracion) from reviews where pelicula = ?";
	private static final String BorrarReviewsDePelicula = "DELETE FROM REVIEWS where pelicula = ?";
	private static final String BorrarReview = "DELETE FROM REVIEWS where id = ?";
	//Usuarios
	private static final String TodosLosUsuarios = "SELECT alias, password, nombre, apellidos, email, telefono, puntos, premium, admin from usuarios";
	private static final String RecuperarUsuario = "SELECT alias, password, nombre, apellidos, email, telefono, puntos, premium, admin from usuarios where alias=?";
	private static final String RecuperarUsuarioyPass = "SELECT alias, password, nombre, apellidos, email, telefono, puntos, premium, admin from usuarios where BINARY(alias)= ? and BINARY(password) = ?";
	private static final String EscribirUsuario = "INSERT INTO USUARIOS (alias, password, nombre, apellidos, email, telefono, puntos, premium, admin) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String ModificarUsuario ="UPDATE usuarios set nombre=?, apellidos=?, telefono=? where alias = ?";
	private static final String GenerarCodigoReseteoEnUsuario ="UPDATE usuarios set puntos=? where email = ?";
	private static final String AliasDisponible ="SELECT count(*) from usuarios where BINARY(alias) = ?";
	private static final String EmailDisponible ="SELECT count(*) from usuarios where BINARY(email) = ?";
	private static final String CambiarPasswordUsuario = "UPDATE usuarios set password=? where BINARY(email) = ? and puntos = ?";
	
	//Funciones
	private static final String RecuperarTodasFunciones = "SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones order by idsala, fecha asc";
	private static final String RecuperarFuncionId = "SELECT idfuncion, idpelicula, idsala, fecha, audio, precio FROM funciones where idfuncion = ?";
	private static final String RecuperarFuncionesDeHoyDePelicula ="SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date(fecha) = curdate() and idpelicula = ? order by idsala, fecha asc";
	private static final String RecuperarFuncionesDisponibles ="SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date_add(fecha , interval 2 hour) > current_timestamp() and date(fecha) = curdate() and idpelicula = ? order by idsala, fecha asc";
	private static final String RecuperarFuncionesDisponiblesHoyAhora ="SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date_add(fecha , interval 2 hour) > current_timestamp() and date(fecha) = curdate() order by fecha, idsala asc";
	private static final String RecuperarFuncionesDisponiblesHoyMasUno ="SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date(fecha) = (curdate() + INTERVAL 1 DAY) order by fecha, idsala asc";
	private static final String InsertarFuncion = "INSERT INTO FUNCIONES ( idpelicula, idsala, fecha, audio, precio) VALUES (?,?,?,?,?)";
	private static final String RecuperarFuncionesDeFechaDePelicula = "SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date(fecha) = ? and idpelicula = ? order by idsala, fecha asc";
	private static final String RecuperarFuncionesDeFechaDePeliculaDeSala = "SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date(fecha) = ? and idpelicula = ? and idsala = ? order by idsala, fecha asc";
	private static final String RecuperarFuncionesDeFechaDeSala ="SELECT idfuncion, idpelicula, idsala, fecha, audio, precio from funciones where date(fecha) = ? and idsala = ? order by idsala, fecha asc";
	private static final String BorrarFuncion = "DELETE FROM FUNCIONES where idfuncion = ?";

	//Entradas
	private static final String RecuperarEntrada ="SELECT idfuncion, idbutaca, idreserva FROM entradas where idreserva = ?";
	private static final String RecuperarEntradasDeUsuario ="SELECT idfuncion, idbutaca, idreserva FROM entradas where idreserva in (select idreserva from reservas where email = ?) order by idfuncion";
	private static final String HaVistoElUsuarioEstaPeli ="SELECT count(*) FROM entradas where idreserva in (select idreserva from reservas where email = ? "
	+" and idfuncion in (select idfuncion from funciones where idpelicula=?))";
	private static final String GrabarEntrada = "INSERT INTO ENTRADAS (idfuncion, idbutaca, idreserva) VALUES (?,?,?)";
	private static final String EntradaDisponible = "SELECT count(*) FROM entradas where idreserva = ? and idfuncion = ? and disponible =  true";
	private static final String CanjearEntrada ="UPDATE ENTRADAS set disponible=false where idreserva = ?";
	
	//Reservas
	private static final String GrabarReserva = "INSERT INTO RESERVAS (idreserva, email) VALUES (?, ?)";
	private static final String RecuperarReserva = "SELECT idreserva, email from reservas where idreserva = ?";

	public static String getRecuperarFuncionesDeFechaDeSala() {
		return RecuperarFuncionesDeFechaDeSala;
	}
	
	public static String getModificarPeliculaConcurrente() {
		return ModificarPeliculaConcurrente;
	}
		
	public static String getEntradaDisponible() {
		return EntradaDisponible;
	}
	
	public static String getCanjearEntrada() {
		return CanjearEntrada;
	}
	
	public static String getRecuperarSalaNombre() {
		return RecuperarSalaNombre;
	}
	
	public static String getInsertarSala() {
		return InsertarSala;
	}
	
	public static String getCambiarPasswordUsuario() {
		return CambiarPasswordUsuario;
	}
	
	public static String getGenerarCodigoReseteoEnUsuario() {
		return GenerarCodigoReseteoEnUsuario;
	}
	
	public static String getAliasDisponible() {
		return AliasDisponible;
	}
	
	public static String getEmailDisponible() {
		return EmailDisponible;
	}	
	
	public static String getCarteleraSemana() {
		return CarteleraSemana;
	}
	
	public static String getRecuperarFuncionesDisponiblesHoyMasUno() {
		return RecuperarFuncionesDisponiblesHoyMasUno;
	}

	public static String getRecuperarFuncionesDisponiblesHoyAhora() {
		return RecuperarFuncionesDisponiblesHoyAhora;
	}
	
	
	public static String getRecuperarButaca() {
		return RecuperarButaca;
	}	
	
	public static String getModificarUsuario() {
		return ModificarUsuario;
	}	
	
	
	public static String getRecuperarReserva() {
		return RecuperarReserva;
	}	
	
	public static String getRecuperarEntrada() {
		return RecuperarEntrada;
	}
		
	
	public static String getGrabarEntrada() {
		return GrabarEntrada;
	}
	
	public static String getPeliculasQueElUsuarioHaVisto() {
		return PeliculasQueElUsuarioHaVisto;
	}		
	
	public static String getRecuperarEntradasDeUsuario() {
		return RecuperarEntradasDeUsuario;
	}		
	
	
	public static String getHaVistoElUsuarioEstaPeli() {
		return HaVistoElUsuarioEstaPeli;
	}		
	
	
	public static String getGrabarReserva() {
		return GrabarReserva;
	}		
	
	public static String getRecuperarFuncionesDisponibles() {
		return RecuperarFuncionesDisponibles;
	}	
	
	public static String getRecuperarButacasOcupadasDeFuncion() {
		return RecuperarButacasOcupadasDeFuncion;
	}		
	public static String getRecuperarFuncionId() {
		return RecuperarFuncionId;
	}		
	
	
	public static String getBorrarReview() {
		return BorrarReview;
	}	
	
	public static String getRecuperarUsuarioyPass() {
		return RecuperarUsuarioyPass;
	}
	
	public static String getBorrarFuncion() {
		return BorrarFuncion;
	}		
	
	
	public static String getRecuperarDuracionPelicula() {
		return RecuperarDuracionPelicula;
	}			
	
	public static String getRecuperarFuncionesDeFechaDePeliculaDeSala() {
		return RecuperarFuncionesDeFechaDePeliculaDeSala;
	}			
	
	public static String getBorrarReviewsDePelicula() {
		return BorrarReviewsDePelicula;
	}		
	
	
	public static String getRecuperarFuncionesDeFechaDePelicula() {
		return RecuperarFuncionesDeFechaDePelicula;
	}	
	
	public static String getInsertarFuncion() {
		return InsertarFuncion;
	}
	
	public static String getRecuperarTodasFunciones() {	
		return RecuperarTodasFunciones;
	}
	
	public static String getRecuperarFuncionesDeHoyDePelicula() {
		
		return RecuperarFuncionesDeHoyDePelicula;
	}	
	
	public static String getRecuperarTodasPeliculas() {
	
		return RecuperarTodasPeliculas;
	}
	
	public static String getRecuperarButacasDeSala() {
		
		return RecuperarButacasDeSala;
	}
	public static String getRecuperarSalas() {
		
		return RecuperarSalas;
	}
	
	public static String getInsertarButaca() {
		
		return InsertarButaca;
	}
		
	
	public static String getRecuperarPelicula() {
		
		return RecuperarPelicula;
	}	
	
	public static String getInsertarPelicula() {
		
		return InsertarPelicula;
	}

	public static String getRecuperarGeneros() {
		
		return RecuperarGeneros;
	}	
		
	public static String getRecuperarPaises() {
		
		return 	RecuperarPaises;
	}	
	
	public static String getRecuperarPais() {
		
		return 	RecuperarPais;
	}	
	
	public static String getRecuperarSalaId() {
		return RecuperarSalaId;
	}
	
	public static String getModificarPelicula() {
		return ModificarPelicula;
	}
	
	public static String getBorrarPelicula() {
		return BorrarPelicula;
	}		
	
	public static String getTodasLasReviews() {
		return TodasLasReviews;
	}
	
	public static String getRecuperarReviewdePeli() {
		return RecuperarReviewdePeli;
	}

	public static String getEscribirReview() {
		return EscribirReview;
	}
	
	public static String getTodosLosUsuarios() {
		return TodosLosUsuarios;
	}
	
	public static String getRecuperarUsuario() {
		return RecuperarUsuario;
	}
	
	public static String getEscribirUsuario() {
		return EscribirUsuario;
	}
	
	public static String getReviewsDeUsuarioenPeli() {
		return ReviewsDeUsuarioenPeli;
	}
	
	public static String getValoracionPelicula() {
		return ValoracionPelicula;
	}
}
