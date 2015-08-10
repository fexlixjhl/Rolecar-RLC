/**
 * ANM. Funciones relacionadas con la busqueda.
 */
var hoy = new Date();

var hora_act= hoy.getHours();
var min_act=hoy.getMinutes();
var totminAct=(hora_act*60)+min_act;


$(function() {
	
		inicializarHoraInicio();
	
		$("#tvhiculo").buttonset();
		$("#tsucursal").buttonset();
		 
		$("#hinis").selectmenu({
			 change: function(event,data){
				 cargarhorasminutosinicial();
			 }
		}).selectmenu( "menuWidget" ).addClass( "overflow" );
		$("#hfins").selectmenu({
			 change: function(event,data){
				 cargarhorasminutosfinal();
			 }
		}).selectmenu( "menuWidget" ).addClass( "overflow" );;
		
		$( "#station").autocomplete({
			source: arrstations,
			minLength:3,
			select: function(event,ui)
			{
				var valorcampo = ui.item.value;
				var idcampo = ui.item.id;
				$("#stationid").val(idcampo);
				//getCliente("NomCliente",valorcampo);
			},
			change: function(event,ui)
	        {
				
//		        if (ui.item==null)
//		        {
//		        	$("#station").val('');
//		        	$("#station").focus();
//		        }
//		        else
//			    {
//		        	$("#fini").focus();	     	
//			    }   	
	        }
		});

		$( "#stationOrig").autocomplete({
			source: arrstations,
			minLength:3,
			select: function(event,ui)
			{
				var valorcampo = ui.item.value;
				var idcampo = ui.item.id;
				$("#stationOrigid").val(idcampo);
				
				//getCliente("NomCliente",valorcampo);
			},
			change: function(event,ui)
	        {
//		        if (ui.item==null)
//		        {
//		        	$("#stationOrig").val('');
//		        	$("#stationOrig").focus();
//		        }
//		        else
//			    {
//		        	$("#stationDest").focus();	     	
//			    }   	
	        }
		});

		$( "#stationDest").autocomplete({
			source: arrstations,
			minLength:3,
			select: function(event,ui)
			{
				var valorcampo = ui.item.value;
				var idcampo = ui.item.id;
				$("#stationDestid").val(idcampo);
				//getCliente("NomCliente",valorcampo);
			},
			change: function(event,ui)
	        {
//		        if (ui.item==null)
//		        {
//		        	$("#stationDest").val('');
//		        	$("#stationDest").focus();
//		        }
//		        else
//			    {
//		        	$("#fini").focus();	     	
//			    }   	
	        }
		});

		 $('input[type=radio][name=tsucursal]').change(function() {
		        if (this.value == 'tsucursal1') {
		           $(".visible").css("display","");
		           $(".oculta").css("display","none");
//		           $("#stationDest").css("display","none");
		        }
		        else if (this.value == 'tsucursal2') {
			           $(".visible").css("display","none");
			           $(".oculta").css("display","inline");
			           //$("#stationDest").css("display","");
		        }
		    });
		 
		 inicializarFechas();
		 
		 $("#borrar").click(function(){
			 $("#fini").val("");
			 $("#fini").datepicker("option","minDate", 0);
			 $("#fini").datepicker("option","maxDate", 6000);
			 $("#ffin").val("");
			 $("#ffin").datepicker("option","minDate", 0);
			 $("#ffin").datepicker("option","maxDate", 6000);			 
			 inicializarFechas();
		 });
		 	 
		 $("#mini").change(function(){
			
			 var fechaIni=formateaFechaYYYYMMDD($("#fini").val());	
			 var fInicio=Date.parse(fechaIni);
			 var fhoy=formateaFechaDateYYYYMMDD(hoy);	
			 var fdhoy=Date.parse(fhoy);
			 if(fInicio==fdhoy)
			 {			
				 compararHoraActual_HInicio();	 
			 }		 
			 
		 });
		 
		 $("#hini").change(function(){
			 var fechaIni=formateaFechaYYYYMMDD($("#fini").val());	
			 var fInicio=Date.parse(fechaIni);
			 var fhoy=formateaFechaDateYYYYMMDD(hoy);
			 var fdhoy=Date.parse(fhoy);
			 if(fInicio==fdhoy)
			 {			
				 compararHoraActual_HInicio();	 
			 }		 
		 });
		 
//		 $("#hinis").change(function(){
//			 cargarhorasminutosinicial();
//		 });
//						 
//		 $("#hfins").change(function(){
//			 cargarhorasminutosfinal();
//		 });
		 
		 $("#buscar").click(function(){
			 
			 
			 //$("#msgCargando").show();
			 $(".wrape").hide();
			 $(".cargandobusqueda").show();
			 if(comprobar())
			 {
				 $("#formBuscador").submit();
			 }
			 else
			 {	 
				 $(".cargandobusqueda").hide();
				 $(".wrape").show();
				 //$("#msgCargando").hide();
			 }
		 });
		 
		 $("#fechainicial").click(function(){
			 $("#fini").datepicker('show');
			 
		 });
		 
		 
		 $("#fechafinal").click(function(){
			 $("#ffin").datepicker('show');
			 
		 });
		 
		 $("#horainicial").click(function(){
			 $("#hinis").selectmenu("open");
		 });
		 
		 $("#horafinal").click(function(){
			 $("#hfins").selectmenu("open");
		 });
		 
		 $(window).on('beforeunload', function(){
			    location.href="servletRolecar?accion=cerrarSesion";
		  });
	});

function comprobar()
{
	var fechaIni=formateaFechaYYYYMMDD($("#fini").val());	
	 var fInicio=Date.parse(fechaIni);
	 var fhoy=formateaFechaDateYYYYMMDD(hoy);
	 var fdhoy=Date.parse(fhoy);
	 var station = $("#station").val();
	 var stationori = $("#stationOrig").val();
	 var stationdes = $("#stationDest").val();
	 
	 
	 if( ($(".visible").css('display') != 'none') && (station === undefined || station == ""))
	 {
		 jAlert("Debes introducir una localidad","Alerta");
		 return false;
	 }
	 
	 if( ($(".oculta").css('display') != 'none') && (stationori === undefined || stationori == ""))
	 {
		 jAlert("Debes introducir una localidad origen","Alerta");
		 return false;
	 }
	 
	 if( ($(".oculta").css('display') != 'none') && (stationdes === undefined || stationdes == ""))
	 {
		 jAlert("Debes introducir una localidad destino","Alerta");
		 return false;
	 }
	 
	 if(fInicio==fdhoy)
	 {			
		 return compararHoraActual_HInicio();	 
	 }
	 
	 return true;
	
}

function cargarhorasminutosinicial()
{
    var horaminutoinicial=$("#hinis").val();
    if(horaminutoinicial.length<5)
    	{
    	   horaminutoinicial="0"+horaminutoinicial;
    	}
	var arrayinicial =horaminutoinicial.split(":");
	$("#hini").val(arrayinicial[0]);
	$("#mini").val(arrayinicial[1]);
	//comprobar();
}

function cargarhorasminutosfinal()
{
    var horaminutoinicial=$("#hfins").val();
    if(horaminutoinicial.length<5)
	{
	   horaminutoinicial="0"+horaminutoinicial;
	}
	var arrayinicial =horaminutoinicial.split(":");
	$("#hfin").val(arrayinicial[0]);
	$("#mfin").val(arrayinicial[1]);
	//comprobar();
}


function inicializarFechas()
{
	$("#fini").datepicker({
	 	minDate: 0,
	 	dateFormat:"dd/mm/yy" ,
        numberOfMonths: 1,
        onSelect: function(selected) {
          var day = selected.substring(0,2);
          var month = parseInt(selected.substring(3,5))-1;
          var year = selected.substring(6,10);
          var nextDay = new Date(year,month,day);
          var dayweek = nextDay.getDay();
          var addday=1;
          //Cuesti�n de los domingos, inicialmente se a�adian 2 d�as
          if(dayweek==6)
    	  {
    	    addday=1;
    	  }
          nextDay.setDate(nextDay.getDate() + addday);
          var f6 = formateaFechaDateDDMMYYYY(nextDay);
//          if(dayweek==0)
//    	  {
//        	 $("#fini").datepicker("option","minDate", f6);         	  
//    	  }
          $("#ffin").datepicker("option","minDate", f6);      
        }
	//,beforeShowDay:noDomingos
    });
    $("#ffin").datepicker({ 
    	minDate: 0,
    	dateFormat:"dd/mm/yy" ,
	 	//dateFormat:"d M, y" ,
        numberOfMonths: 1,
        onSelect: function(selected) {
        
          
           $("#fini").datepicker("option","maxDate", selected);
        }
      //,beforeShowDay:noDomingos
    });  
}

function inicializarHoraInicio()
{
//	$("#hini").val(hora_act+2);
//	$("#hfin").val(hora_act+2);
//	if(min_act>=46)
//	{
//		$("#mini").val("0");
//		$("#mfin").val("0");
//		h=parseInt($("#hini").val());		
//		$("#hini").val(h+1);
//		$("#ffin").val(h+1);
//	}
//	else if(min_act>=1 && min_act<=15)
//	{
//		$("#mini").val("15");
//		$("#mfin").val("15");
//	}
//	else if(min_act>=16 && min_act<=30)
//	{
//		$("#mini").val("30");
//		$("#mfin").val("30");
//	}
//	else if(min_act>=31 && min_act<=45)
//	{
//		$("#mini").val("45");
//		$("#mfin").val("45");
//	}
	$("#hinis").val("12:00");
	$("#hfins").val("12:00");
	cargarhorasminutosinicial();
    cargarhorasminutosfinal();
 
	
	var unafecha = new Date();
	unafecha.setDate(unafecha.getDate()+3);
	var dia =unafecha.getDay();
	if(dia==0)
		{
		unafecha.setDate(unafecha.getDate()+1);
		}
	var f3 = formateaFechaDateDDMMYYYY(unafecha);
	unafecha.setDate(unafecha.getDate()+3);
	dia =unafecha.getDay();
	if(dia==0)
		{
		unafecha.setDate(unafecha.getDate()+1);
		}
	var f6 = formateaFechaDateDDMMYYYY(unafecha);
	$("#fini").val(f3);
	$("#ffin").val(f6);
	
}


function HoraRecogidaValida()
{
	var h = hora_act+2;
	$("#hinis").val("13:00");
	$("#hfins").val(h+":00");

}

function borrarContenidoStation(id)
{
	var valor=$("#"+id).val();
	if(valor=="D�nde" || valor=="A")
		$("#"+id).val("");
}

function compararHoraActual_HInicio()
{
	var h= parseInt($("#hini").val());
	 var m=parseInt($("#mini").val());
	 var mintot_select=(h*60)+m;
	 var dif=mintot_select-totminAct;
	  
	 if(mintot_select<=totminAct)
	 {
		 jAlert("Error. La hora de recogida no puede ser inferior a la Hora Actual.","Error");
		 //inicializarHoraInicio();
		 HoraRecogidaValida();
		 return false;
	 }
//C�digo que muestra mensaje por si se ha codigo origen con menos de dos horas de antelaci�n
//	 else if(dif < 120)
//	 {
//		 jAlert("Error. La hora m�nima de recogida es de 2 horas m�s, de la hora actual.Se coloca hora mas cercana valida","Error");
//		 //inicializarHoraInicio();
//		 HoraRecogidaValida();
//		 return false;
//	 }
	 return true;
}

function noDomingos(date) {
    var day = date.getDay();

    return [(day != 0),""];

}