/**
 * 
 */
package util;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import exceptions.ServiceException;

/**
 * @author  ANGEL (28/12/2014)
 * 
 /
  /**
  *	Gestiona la peticion de un formulario  enctype=multipart/form-data
 *  incluido en un objeto request, es decir, el formulario incluye campos
 *  type=file
 */
public class FormMultiPart {
	private  FileItem  [] b ;
	private File destino;
	
	
	
	/**
	 * 
	 * @param path  ruta donde vamos a almacenar los ficheros que se van a subir
	 *          al servidor
	 * @param request objeto  HttpServletRequest 
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException 
	 */
	public FormMultiPart(String path,HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException   {	
		// por si no existe la ruta
		
		request.setCharacterEncoding("UTF-8");
		destino=new File(path);
		
		//Si no existe la ruta, la crea
		if (!destino.exists()) 
			destino.mkdir();
		
		ServletRequestContext src=new ServletRequestContext(request);
		
		//Si el formulario es enviado con Multipart
		if(ServletFileUpload.isMultipartContent(src)){
		//Necesario para evitar errores de NullPointerException
			
		DiskFileItemFactory factory = new DiskFileItemFactory((1024*1024),destino);
		//Creamos un FileUpload
		ServletFileUpload upload=new  ServletFileUpload(factory);
	//	//Procesamos el request para que nos devuelva una lista
		//con los parametros y ficheros.
			
		   List lista;
		   lista = upload.parseRequest(src);
			
			b =new FileItem  [lista.size()];
			Iterator it = lista.iterator();
			int contador=0;// contador de ficheros subidos
			int i=0;
			while(it.hasNext()){
		//		//Rescatamos el fileItem y los metemos en el array
				// eliminamos los file nulos
							 
				b[i]=(FileItem)it.next();
									
				i++;
			}	// metemos en el array
			 
		}	
			
	} // fin del constructor

/**
 * 
 * @param campo  campo de un formulario Multipart
 * @return	valor del campo .null si no existe el campo
 */

public  String getCampoForm(String campo){
	int i=0;
	while(i<b.length){
	//Comprobamos si es un campo de formulario
	
	   if( b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0) 
    	return 	b[i].getString();
	   else
		   i=i+1;
	}
	
	
	return null;
	
}
/**
 * 
 * @param campo  nombre de campo que se repite en un formulario Multipart
 * @return   Valores de los campos. null si no existe el campo
 */
public  String [] getCampoFormValues(String campo){
	int i=0,j=0;
	// j para saber cuantos hay con el mismo nombre de campo
	while(i<b.length){
	//Comprobamos si es un campo de formulario
	
	   if( b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0) 
    	j=j+1;
	  
	   
		   i=i+1;
	}
	// ya sabemos cuantos hay recorremos nuevamente el array para enviarlos
	i=0;
	if(j==0) 
		return null;
	else {
		i=0;
		String datos[]=new String[j];
		j=0;
		while(i<b.length){
			//Comprobamos si es un campo de formulario
			
			   if( b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0){ 
		    	datos[j]=b[i].getString();
		    	j=j+1;
			   }
			 
				   i=i+1;
			}
		return datos;
	}
	
}

/**
  * @return numero  de ficheros subios al servidor
 * @throws ServiceException 
 * @throws Exception
 */
public int  SubirFicheros(String titulo) throws Exception  {
	int i=0,j=0;
//	File file=null;
	File fileRenamed=null;
	while(i<b.length){
		
		if( !b[i].isFormField()){
			// si no tiene valor el campo del fichero no subimos nada
			System.out.println(b[i]);
			if (b[i].getName().compareTo("")!=0){			
//				file=new File(b[i].getName());
				
				
				fileRenamed = new File (titulo);
				
				b[i].write(new File(destino,fileRenamed.getName()));
			j=j+1;
			}
		}
		i=i+1;
	}
	
	return j;
}

public int  SubirFicheros(String banner, String main) throws Exception  {
	int i=0,j=0;
//	File file=null;
	File fileRenamedBanner=null; File fileRenamedMain=null;
	while(i<b.length){
		
		if( !b[i].isFormField()){
			// si no tiene valor el campo del fichero no subimos nada
			//System.out.println(b[i]);
			if (b[i].getName().compareTo("")!=0){			
				//System.out.println(b[i].getFieldName());
				
				if (j==0) {
					fileRenamedBanner = new File (banner);
					b[i].write(new File(destino,fileRenamedBanner.getName()));
				}else {  
					fileRenamedMain = new File (main);
					b[i].write(new File(destino,fileRenamedMain.getName()));
				}	
				
			j=j+1;
			}
		}
		i=i+1;
	}
	
	return j;
}

/**
 * 
 * @param campo nombre del campo tipe File de un formulario multipar
 * @return ruta absoluta y fichero  donde se subio el fichero.
 *         null si  campo  no existe
 */
public  String getCampoFile(String campo){
	int i=0;
	File file;
	while(i<b.length){
	//Comprobamos si es un campo de formulario
	
	   if( !b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0 ){ 
		    if(b[i].getName().compareTo("")!=0){
		       file = new File(b[i].getName());
	           return 	destino+File.separator+file.getName();
	         } 
		       else return "";
	        	 
	   }else
		   i=i+1;

	   }
	return null;
	
}
/**
 * 
 * @param campo nombre del campo tipe File que se repite en  un formulario multipar
 * @return ruta absoluta y ficheros  donde se subieron los ficheros.
 *        null si el campo no existe
 */
public  String [] getCampoFileValues(String campo){
	int i=0, j=0;
	while(i<b.length){
	//Comprobamos si es un campo de formulario
	
	   if( !b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0 ){ 
		  j=j+1;
	   } 
	  
		   i=i+1;
	}
	// ya sabemos cuantos ficheros se han subido j
	if (j==0)
	
	return null;
	else{
	// los buscamos nuevamnete para meterlos en el array de retorno	
	i=0;
	String datos[]=new String[j];
	j=0;
	File file;
	while(i<b.length){
		//Comprobamos si es un campo de formulario
		
		if( !b[i].isFormField() &&  b[i].getFieldName().compareTo(campo)==0 ){ 
			if( b[i].getName().compareTo("")!=0 ){
			  file = new File(b[i].getName());
			datos[j]= destino+File.separator+file.getName();
			
		   } 
			else
				datos[j]="";
		 j=j+1;
		}
			   i=i+1;
		}
	return datos;
  }// fin del else
}// fin del metodo
} // fin de la clase
