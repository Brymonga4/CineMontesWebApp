package servicios;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import daos.EntradaDAO;
import daos.ReservaDAO;
import daos.TransaccionesManager;
import domain.Entrada;

import domain.Reserva;
import domain.Usuario;
import exceptions.DAOException;
import exceptions.ServiceException;
import objetos.EntradaCompleta;
import recursos.Recursos;
import util.Fecha;

/**
 * Esta clase contiene los diferentes Servicios (relacionados con la clase EntradaDAO) usados para utilizar los diferentes DAOS
 * @author Bryan Montesdeoca 
 * @version 1.0
 * @see EntradaDAO
 */
public class ServicioEntradas {

	public ServicioEntradas (){}
	
	/**
	* Servicio para canjear una Entrada, primero se asegura que la entrada esta disponible 
	* Despues tambien se asegura que se ha podido canjear para finalmente realizar la transaccion
	* @param idFuncion Identificador de la Funcion a la que pertenece la Entrada
	* @param identificador Identificador de la Entrada
	* @return Devuelve 1 si ha podido canjear la entrada y 0 si no ha podido
	* @throws ServiceException
	*/	
	public int canjearEntrada(int idFuncion, String identificador) throws ServiceException {
		TransaccionesManager trans = null;
		EntradaDAO entradadao;
		int updateado = 0; boolean disponible;
		
		try {

			trans = new TransaccionesManager();	
			entradadao = trans.getEntradaDAO();			
			
			disponible = entradadao.entradaDisponible(idFuncion, identificador);

			if (disponible) {
				updateado = entradadao.canjearEntrada(identificador);
				if (updateado>0)
					trans.closeCommit();
			}else {
				trans.closeRollback();
				//throw new DAOException ("Esa entrada ya ha sido canjeada o no existe.");
			}
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lî‰›ico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}		
		
		}	
		
		return updateado;
	}
	
	
	/**
	* Servicio para generar una Entrada nueva, a partir de una Reserva con el email definido
	* Primero inserta una Reserva nueva a partir del email proveido
	* Despues con esa Reserva Generada, inserta finalmente la Nueva Entrada con una reserva ya unica
	* Si al insertar la entrada, el valor de insertado es mayor de 0, realizara la transaccion
	* Si el valor de insertado es 0, por alguna razon alguien compro la entrada con la misma Butaca en la misma Funcion, lanzara una Excepcion y no realizara la transaccion
	* @param entrada Objeto Entrada de la Funcion a la que pertenece la Entrada
	* @param re Objeto Reserva con el email definido
	* @return Devuelve la Entrada generada e insertada
	* @throws ServiceException
	*/	
	public Entrada generarEntrada(Entrada entrada, Reserva re) throws ServiceException {
		TransaccionesManager trans = null;
		EntradaDAO entradadao; ReservaDAO reservadao;
		int insertado = 0;
		Reserva reservaG; Entrada en = null;
		
		try {

			trans = new TransaccionesManager();	
			reservadao = trans.getReservaDAO();
			entradadao = trans.getEntradaDAO();			
			
			reservaG = reservadao.insertarReserva(re);
			//System.out.println(reservaG);
			entrada.setReserva(reservaG);
			//System.out.println(entrada);
			insertado = entradadao.grabarEntrada(entrada);
	
			if (insertado>0) {
				en = new Entrada(entrada.getFuncion(), entrada.getButaca(), entrada.getReserva());
				trans.closeCommit();
			}else {
				trans.closeRollback();
				throw new DAOException ("No se ha podido comprar esta entrada");
			}
		} catch (DAOException e) {

			try{
				trans.closeRollback();
			}catch (DAOException e1){
				throw new ServiceException(e.getMessage(),e1);//Error interno
			}

			if(e.getCause()==null){
				throw new ServiceException(e.getMessage());//Error Lî‰›ico
			}else{

				throw new ServiceException(e.getMessage(),e);//Error interno
			}		
		
		}	
		return en;
	}
	
	/**
	* Servicio para recuperar una Lista con las Entradas de un Usuario en especifico
	* @param user Objeto Usuario del que queremos recuperar las entradas
	* @return Devuelve la lista de Entradas compradas por el Usuario
	* @throws ServiceException
	*/
	public List<Entrada> recuperarEntradasDeUsuario(Usuario user) throws ServiceException{
		TransaccionesManager trans = null;
		EntradaDAO entradadao = null;
		List<Entrada> list = new ArrayList<Entrada>();

		try {
			trans = new TransaccionesManager();			
			entradadao = trans.getEntradaDAO();
			
			list = entradadao.recuperarEntradasDeUsuario(user);
			trans.close();
			
		} catch (DAOException e) {

		try{
			trans.closeRollback();
		}catch (DAOException e1){
			throw new ServiceException(e.getMessage(),e1);//Error interno
		}

		if(e.getCause()==null){
			throw new ServiceException(e.getMessage());//Error Lî‰›ico
		}else{

			throw new ServiceException(e.getMessage(),e);//Error interno
		}		
		
		}
		return list;
	}	
	
	
	/**
	* Metodo para generar un codigo QR a partir de un identificador, y el Path donde guardara el QR generado
	* @param idreserva String del Identificador
	* @param pathQR Objeto tipo Path con el path relativo donde guardara el QR generado
	* @throws ServiceException
	*/
	public void generarQR(String idreserva, Path pathQR) throws ServiceException {
	
		try {
			QRCodeWriter write = new QRCodeWriter();
			BitMatrix bitMatrix = write.encode(idreserva, BarcodeFormat.QR_CODE, 350, 350);
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", pathQR);
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}		 
		 
	}
	
	/**
	* Metodo para generar un PDF con varias hojas, a partir de una varias/o una Entrada y varios String con paths determinados
	* @param en Objeto especial EntradaCompleta que contiene una lista de Entradas y su Precio Total
	* @param strpathQR String del path relativo donde esta guardado el QR de la entrada
	* @param strpathImage String del path relativo donde esta guardada la imagen de la pelicula de la Entrada
	* @param strpathPDF String del path relativo donde se guardara el PDF que vamos a generar
	* @return Devuelve el Path definitivo donde se ha guardado el PDF que acabamos de generar
	* @throws ServiceException
	*/	
	public String generarPDFs (EntradaCompleta en, String strpathQR, String strpathImage, String strpathPDF) throws ServiceException {
		
		String identificador = en.getListEntradas().get(0).getReserva().getId();
		String pdfId = identificador+"-entrada.pdf";
		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(strpathPDF+"\\"+pdfId));
			document.open();
		for (int i = 0; i < en.getListEntradas().size(); i++) {						
        	
//        	String identificador = en.getListEntradas().get(i).getReserva().getId();
        	String tituloPeli = en.getListEntradas().get(i).getFuncion().getPeli().getTitulo();
        	String tituloPeliEstrenos = Recursos.limpiarString(tituloPeli);
        	tituloPeli = Recursos.limpiarStringSoloSimbolos(tituloPeli);
        	
    		Date horaF = new Date(en.getListEntradas().get(i).getFuncion().getFecha().getTime());
    		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    		String sesionHora = sdf.format(horaF);  	
//        	String sesionHora = en.getListEntradas().get(i).getFuncion().getFecha().getHours()+":"
//        	+en.getListEntradas().get(i).getFuncion().getFecha().getMinutes();        	
        	String diaFun = Fecha.convertirAString(en.getListEntradas().get(i).getFuncion().getFecha(), "dd-MM-YYYY");
        	String salaInfo = ""+en.getListEntradas().get(i).getFuncion().getSala().getIdSala();
        	String cineNombre = "CINE MONTES";
        	String butacaFB = "F "+en.getListEntradas().get(i).getButaca().getFila()+
        					  " B "+en.getListEntradas().get(i).getButaca().getButaca();
        	

        	
			double d = en.getListEntradas().get(i).getButaca().getTipo() * 
					   en.getListEntradas().get(i).getFuncion().getPrecio();
			d = Math.round(d*100.0)/100.0;
        	String precioButaca = ""+d;
        	
        	String idDeEntrada = en.getListEntradas().get(i).getReserva().getId();
        	     			
			document.setMargins(50, 50, 50, 50);
			
			//Estilo de fuentes
			Font bold = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLDITALIC);
			Font fcine = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
			Font predatos = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL); predatos.setColor(new BaseColor(15,72,122));
			Font datos = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD); 			
			
			//Tabla de cabecera
			PdfPTable cabecera = new PdfPTable(2);
			
			PdfPCell cine = new PdfPCell(new Phrase("CINE MONTES", fcine));
			PdfPCell titulo = new PdfPCell(new Phrase("ENTRADA VÁLIDA PARA EL ACCESO DIRECTO A LA SALA", bold));
			
			cabecera.setWidths(new float[] { 25, 75 });
			cabecera.setWidthPercentage(90);

			cine.setBackgroundColor(new BaseColor(255,170,60));
			cine.setBorder(Rectangle.NO_BORDER); cine.setVerticalAlignment(Element.ALIGN_MIDDLE);
			titulo.setBorder(Rectangle.NO_BORDER); titulo.setVerticalAlignment(Element.ALIGN_MIDDLE); 
			titulo.setHorizontalAlignment(Element.ALIGN_MIDDLE); titulo.setPadding(15);
			cabecera.addCell(cine); cabecera.addCell(titulo);
			
			//Imágenes de QR e imagen banner peli
			Image img1 = Image.getInstance(strpathQR+"\\"+idDeEntrada+"-QRCODE.png");	
			Image img2 = Image.getInstance(strpathImage+"\\estrenos_"+tituloPeliEstrenos+".jpg");
			
			//Parrafo con identificador
			Phrase identificadorPhrase = new Phrase(); identificadorPhrase.add(new Chunk(idDeEntrada));
			Paragraph idP = new Paragraph(); idP.add(identificadorPhrase); idP.setAlignment(Element.ALIGN_CENTER);
			
					
			//Tabla de QR e imagen banner peli
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100); 
			PdfPCell cell1 = new PdfPCell(img1);
			PdfPCell cell2 = new PdfPCell();
			PdfPCell cell4 = new PdfPCell(img2, true);
			
			cell1.setBorder(Rectangle.NO_BORDER); cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); cell1.setHorizontalAlignment(Element.ALIGN_MIDDLE); cell1.setPadding(0);
			cell2.setBorder(Rectangle.NO_BORDER); cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setBorder(Rectangle.NO_BORDER); cell4.setVerticalAlignment(Element.ALIGN_MIDDLE); cell4.setHorizontalAlignment(Element.ALIGN_MIDDLE); cell4.setPadding(0);
						
			table.addCell(cell1); table.addCell(cell2); table.addCell(cell4); 
			
			//Primera tabla de datos
			PdfPTable datosEntradaT1 = new PdfPTable(2);
			datosEntradaT1.setWidthPercentage(90); 
			datosEntradaT1.setSpacingBefore(5); datosEntradaT1.setSpacingAfter(5);
			
			//Frases 1
			String strpelicula = tituloPeli; strpelicula = strpelicula.toUpperCase();
			Phrase frasePeli = new Phrase(); 
			frasePeli.add(new Chunk("Película:   ", predatos));
			frasePeli.add(new Chunk(strpelicula, datos));
			
			String strsesion = sesionHora+" h"; 
			Phrase fraseSesion = new Phrase();
			fraseSesion.add(new Chunk("Sesión:   ", predatos));
			fraseSesion.add(new Chunk(strsesion, datos));
			
			String strsala = salaInfo;
			Phrase fraseSala = new Phrase();
			fraseSala.add(new Chunk("Sala:   ", predatos));
			fraseSala.add(new Chunk(strsala, datos));			
			
			//Lista 1
			com.itextpdf.text.List lista1 = new com.itextpdf.text.List();
			lista1.setListSymbol("");
			lista1.add(new ListItem(frasePeli));
			lista1.add(new ListItem(fraseSesion));
			lista1.add(new ListItem(fraseSala));
			
			
			//Frases 2
			String strdia = diaFun;
			Phrase fraseDia = new Phrase(); 
			fraseDia.add(new Chunk("Día:   ", predatos));
			fraseDia.add(new Chunk(strdia, datos));
			
			String cinestr = cineNombre;
			Phrase fraseCine = new Phrase(); 
			fraseCine.add(new Chunk("Cine:   ", predatos));
			fraseCine.add(new Chunk(cinestr, datos));
			
			//Lista 2
			com.itextpdf.text.List lista2 = new com.itextpdf.text.List();
			lista2.setListSymbol("");
			lista2.add(new ListItem(fraseDia));
			lista2.add(new ListItem(fraseCine));
			
			//Primera celda
			PdfPCell listE1 = new PdfPCell(); listE1.addElement(lista1); listE1.setBorder(Rectangle.NO_BORDER);
			datosEntradaT1.addCell(listE1);
			//Segunda celda
			PdfPCell listE2 = new PdfPCell(); listE2.addElement(lista2); listE2.setBorder(Rectangle.NO_BORDER);
			datosEntradaT1.addCell(listE2);
			
			
			//Segunda tabla de datos con entrda y precio
			
			PdfPTable datosEntradaT2 = new PdfPTable(1);
			datosEntradaT2.setWidthPercentage(90); 
			datosEntradaT2.setSpacingBefore(5); datosEntradaT2.setSpacingAfter(5);			
			
			
			String strbutaca = butacaFB; strbutaca = strbutaca.toUpperCase(); 
			Phrase frasebut = new Phrase(); 
			frasebut.add(new Chunk("Butacas:   ", predatos));
			frasebut.add(new Chunk(strbutaca, datos));
			
			String strPrecio = precioButaca+" €"; 
			Phrase fraseprecio = new Phrase(); 
			fraseprecio.add(new Chunk("Precio:   ", predatos));
			fraseprecio.add(new Chunk(strPrecio, datos));		
			
			com.itextpdf.text.List lista3 = new com.itextpdf.text.List();
			lista3.setListSymbol("");
			lista3.add(new ListItem(frasebut));
			lista3.add(new ListItem(fraseprecio));
		
			PdfPCell listE3 = new PdfPCell(); listE3.addElement(lista3); listE3.setBorder(Rectangle.NO_BORDER);
			datosEntradaT2.addCell(listE3);
			

			document.add(cabecera);
			document.add(table);
			
			document.add(idP);

			document.add(datosEntradaT1);
			document.add(datosEntradaT2);
			
			//Si no estoy en la última iteración, hago una nueva página
				if ( i != en.getListEntradas().size()-1) {
					document.newPage();
				}
//			document.add(cabecera);
//			document.add(table);
//
//			document.add(datosEntradaT1);
//			document.add(datosEntradaT2);			


			}//End Foreach
		document.close();	
		
		} catch (DocumentException | IOException e) {
			throw new ServiceException(e.getMessage());
		}		

		return strpathPDF+"\\"+pdfId;
	}

	
	/**
	* Metodo para enviar un Email con Archivos adjuntos a un destinatario
	* @param destinatario String con el Email del Destinatario
	* @param Asunto String con el Asunto del mensaje
	* @param Cuerpo String con el Cuerpo del mensaje, su contenido de texto
	* @param PDFpath String con el path relativo del PDF que queremos adjuntar en el email
	* @throws ServiceException
	*/	
	public void enviarEmailConEntradas(String destinatario, String Asunto, String Cuerpo, String PDFpath ) throws ServiceException {
		
        final String USERNAME = "hitohitotadano7@gmail.com";
        final String PASSWORD = "cripplingdep44";
        String fromEmail = "hitohitotadano7@gmail.com";
        String toEmail = destinatario; //Str usuario al que hay que enviarlo
        
        Properties properties;
        properties = Recursos.getEmailProp();

  	  Session session = Session.getInstance(properties , new javax.mail.Authenticator(){
		  protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication(USERNAME, PASSWORD);
		  }
  	  });
      
  	  //Start
  	  MimeMessage msg = new MimeMessage(session);
	  	
  	  	try {

			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			//Str asunto
			msg.setSubject(Asunto);
			
			Multipart emailContent = new MimeMultipart();
			
			//Str Cuerpo Email
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(Cuerpo);
			
			//Attachments
			MimeBodyPart pdfAttachment = new MimeBodyPart();
			pdfAttachment.attachFile(PDFpath);
			
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(pdfAttachment);
			
			msg.setContent(emailContent);
			
			//msg.setText("Gracias Por comprar en Cine Montes");
			Transport.send(msg);
			System.out.println("Mensaje enviado");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			throw new ServiceException (e.getMessage());
		}	
	}


	/**
	* Metodo para enviar un Email a un destinatario
	* @param destinatario String con el Email del Destinatario
	* @param Asunto String con el Asunto del mensaje
	* @param Cuerpo String con el Cuerpo del mensaje, su contenido de texto
	* @throws ServiceException
	*/	
	public void enviarEmail(String destinatario, String Asunto, String Cuerpo) throws ServiceException {
		
        final String USERNAME = "hitohitotadano7@gmail.com";
        final String PASSWORD = "cripplingdep44";
        String fromEmail = "hitohitotadano7@gmail.com";
        String toEmail = destinatario; //Str usuario al que hay que enviarlo
        
        Properties properties;
        properties = Recursos.getEmailProp();

  	  Session session = Session.getInstance(properties , new javax.mail.Authenticator(){
		  protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication(USERNAME, PASSWORD);
		  }
  	  });
      
  	  //Start
  	  MimeMessage msg = new MimeMessage(session);
	  	
  	  	try {

			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			//Str asunto
			msg.setSubject(Asunto);
			
			Multipart emailContent = new MimeMultipart();
			
			//Str Cuerpo Email
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(Cuerpo);
			

			emailContent.addBodyPart(textBodyPart);

			
			msg.setContent(emailContent);
			
			//msg.setText("Gracias Por comprar en Cine Montes");
			Transport.send(msg);
			System.out.println("Mensaje enviado");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			throw new ServiceException (e.getMessage());
		}	
	}	
	
	

}


