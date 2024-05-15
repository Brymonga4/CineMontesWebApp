
var inputGenero = document.getElementById('generos'); 
var ctdGeneros;
	if (inputGenero.value!=null){
		if ((inputGenero.value).replace(/[^-]/g, "").length>=0){
			ctdGeneros=(inputGenero.value).replace(/[^-]/g, "").length+1;
		}
	}
	 else
	 ctdGeneros=0;

   function addGenre(genero){
        
     var genero = genero;
     var inputGenero = document.getElementById('generos');
     var listaGeneros = document.getElementById('generosList')
     var maxGen = 4;
     var str;
// var ctd = (inputGenero.value).replace(/[^-]/g, "").length+1;      
      if(ctdGeneros<maxGen){
         if (inputGenero.value !=""){
           if (inputGenero.value.indexOf(genero)<0){
            inputGenero.value = inputGenero.value+" - "+genero;
            ctdGeneros++;
            }
          }else{
          inputGenero.value = ""+genero;
          ctdGeneros++;    
          }
          
      }else{
        ctdGeneros=1;
        inputGenero.value = ""+genero;
      }
    
    listaGeneros.options[0].selected=true;  
// console.log(ctdGeneros);

   }
   
    function addHiddenValues(cosa){
     const maxBC = 5;
     var ih = document.createElement("INPUT");
     ih.setAttribute("type","hidden");
     ih.name = "reservado";
     ih.value = cosa.id;
         
     var butacas = document.getElementById("butacasValues");
     var submitCompra = document.getElementById("compra");
     
     if (cosa.style.color == "rgb(255, 248, 42)"){
    	 cosa.style.color = "#ffaa3c";
    	 cosa.removeChild(cosa.firstChild);
    	 butacas.value = (butacas.value).replace(cosa.id+"#","") 
     }else{
    	 cosa.style.color = "#fff82a";
    	 cosa.appendChild(ih);
    	 butacas.value += ""+cosa.id+"#";
     }

     var str = (butacas.value).split("#");
     
     if (butacas.value != "" && str.length-2<maxBC)
    	 submitCompra.style.display= "";
     else
    	 submitCompra.style.display= "none";     
     
     if (butacas.value != null)
    	 butacayFila(butacas.value);    	 
 } 
    
    
    function butacayFila (idButaca){
    	var filaYButacas = [];
    	var stringFinales = [];
    	var str = idButaca.split("#");
    	for (i = 0; i<str.length; i++){
    		if(str[i]!=""){	
    		filaYButacas=str[i].split("-");
    		stringFinales.push("Fila: "+filaYButacas[1]+" Butaca: "+ filaYButacas[2]);
    		}
    	}
    	
    	drawButacasReservadas(stringFinales);
    	return stringFinales;
    }
    
    function drawButacasReservadas(stringFinales){
    	var tabla = document.getElementById('butacasR');
    	
      	while(tabla.rows.length > 1) {     		
      		tabla.deleteRow(1);
    	}
      	
 	if (stringFinales.length>0){
      	
    	for (i = 0; i<stringFinales.length; i++){  
    		
    		var fila=tabla.insertRow(tabla.length);
    		var celda = fila.insertCell(0);
    		
    		var divTexto = document.createElement("div");
    		var texto = document.createElement("p");
    		texto.innerHTML=""+stringFinales[i];    		

    		divTexto.appendChild(texto);
    		celda.appendChild(divTexto)
    		
    		
    	}
 	}
 	
    }
    

   
	function PreviewImageBanner() {
		var oFReader = new FileReader();
		oFReader
				.readAsDataURL(document.getElementById("uploadImageBanner").files[0]);

		oFReader.onload = function(oFREvent) {
			document.getElementById("uploadPreviewBanner").src = oFREvent.target.result;
		};
	};   
	
	function PreviewImageMain() {
		var oFReader = new FileReader();
		oFReader
				.readAsDataURL(document.getElementById("uploadImageMain").files[0]);

		oFReader.onload = function(oFREvent) {
			document.getElementById("uploadPreviewMain").src = oFREvent.target.result;
		};
	};   
		
	
	