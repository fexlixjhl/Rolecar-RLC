//  AHP funciones para el listado y busqueda
var hoy = new Date();

var hora_act= hoy.getHours();
var min_act=hoy.getMinutes();
var totminAct=(hora_act*60)+min_act;

$(document).ready(function(){

		 	

			inicializarFechas();
			inicializarHoraInicio(horarec,horaent);
			
			 if(haycoches)
			 {
				 $("#mensajevehiculostexto").hide();
				 $(".capacarrousel").show();
				 $("#ordenapor").show();
				 $(".Listado").show();
			 }
			 else
		     {
				 $("#mensajevehiculostexto").text(mensajeveh);
				 $("#mensajevehiculostexto").show();
				 $(".capacarrousel").hide();
				 $("#ordenapor").hide();
				 $(".Listado").hide();
		     }
			
		        
		    if(mismasucursal)
	             {
	         	   $("#tsucursal1" ).prop( "checked", true );
	         	   $("#stationOrig").css("display","none");
	         	   $("#ofientrega").css("display","none");
	               $("#stationDest").css("display","none");
	               $("#station").css("display","");
	               //ISSUE ESPANA
	               $("#station").val(estacionori);
	               $("#stationid").val(estacionoriid);
	             }
	        else
	             {
	         	    $("#tsucursal2" ).prop( "checked", true );
	            	$("#stationOrig").css("display","");
	            	$("#ofientrega").css("display","");
	            	$("#stationDest").css("display","");
	            	$("#station").css("display","none");
	            	$("#stationOrig").val(estacionori);
	            	$("#stationDest").val(estaciondes);
	            	$("#stationOrigid").val(estacionoriid);
	            	$("#stationDestid").val(estaciondesid);
		            	
	             }
	                              
	         $("#fini").val(fecharec);
	         $("#hinis").val(horarec);
	         $("#ffin").val(fechaent);
	         $("#hfins").val(horaent);
//	         $("#stationid").val(codori+"::"+provincia);
//	         $("#stationOrigid").val(codori+"::"+provincia);
//	         $("#stationDestid").val(coddes+"::"+provincia);
            
            
            if(crtype=="CR")
                {
            	$( "#tvhiculo1" ).prop( "checked", true );       
                }
            else
                {
            	$( "#tvhiculo2" ).prop( "checked", true );
                }
       
            
			$("#precio").click(function(e){	
				var accion=$("#fordenar").attr('action');
				accion=accion+"&Orden=1";
				$("#fordenar").attr('action',accion);
				$("#fordenar").submit();
//				$('#otros').html('<div style="text-align:center;"><img src="images/iconocarga.gif" /></div>');
//				$.ajax({ //Comunicación jQuery hacia JSP 
//				    type: "POST",
//				    url: "ajax/ordernarporprecio.jsp",
//				    data: "checkoutdate="+fecharec+"&checkouttime="+horarec+"&checkindate="+fechaent+"&checkintime="+horaent+"&carType="+crtype+"&codstation="+codori,
//				    async:false,
//				    success: function(msg)
//				    {
//				    	  msg=jQuery.trim(msg);
//
//				    	if(msg.length>0)
//				    	{	
//				    		$("#otros").fadeIn(1000).html(msg);
//				    	}
//				    	else
//				    	{
//				    		$("#otros").html("<p>NO hay sitios cercanos a esta oficina</p>");	
//				    	}
//				    	
//				    },
//				    error: function()
//				    {
//				    	$("#otros").html("<p>Error en la localización</p>");
//				    }
//				});	
				
				
				
			});
			
			
			
		 	$("#tam").click(function(e){ 
				var accion=$("#fordenar").attr('action');
				accion=accion+"&Orden=3";
				$("#fordenar").attr('action',accion);
				$("#fordenar").submit();
		 	});  
			$("#emi").click(function(e){	
				var accion=$("#fordenar").attr('action');
				accion=accion+"&Orden=5";
				$("#fordenar").attr('action',accion);
				$("#fordenar").submit();
			});

			$(".resumen").click(function(){
			   var num =$(this).attr("id");
               scrollToElement("#coche"+num);

			});

			$('.carrousel').bxSlider({
			    slideWidth: 200,
			    minSlides: 2,
			    maxSlides: 5,
			    slideMargin: 10
			  });


			
			$("#hinis").selectmenu({
				 change: function(event,data){
					 cargarhorasminutosinicial();
				 }
			});
			$("#hfins").selectmenu({
				 change: function(event,data){
					 cargarhorasminutosfinal();
				 }
			});
			
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
				}).val(estacionori);
		
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
				}).val(estacionori);
		
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
//	 			        if (ui.item==null)
//	 			        {
//	 			        	$("#stationDest").val('');
//	 			        	$("#stationDest").focus();
//	 			        }
//	 			        else
//	 				    {
//	 			        	$("#fini").focus();	     	
//	 				    }   	
			        }
				}).val(estaciondes);
				
				
				 $('input[type=radio][name=tsucursal]').change(function() {
				        if (this.value == 'tsucursal1') {
				           $("#station").css("display","");
				           $("#stationOrig").css("display","none");
				           $("#ofientrega").css("display","none");
				           $("#stationDest").css("display","none");
				        }
				        else if (this.value == 'tsucursal2') {
					           $("#station").css("display","none");
					           $("#stationOrig").css("display","");
					           $("#ofientrega").css("display","");
					           $("#stationDest").css("display","");
				        }
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
				 
				 
				 
				//Si quier buscar de nuevo
				$("#buscar").click(function(){
					 
					 
					 $("#msgCargando").show();
					 scrollToElement("body");
					 if(comprobar())
					 {
						 $("#formBuscador").submit();
					 }
					 else
					 {	 
						 $("#msgCargando").hide();
					 }
				 });
				
				//Si quiere reservar
				$(".reserva").click(function(e){
					 
					 e.preventDefault();
					 
					 $(".wrape").hide();
					 $(".cargandobusqueda").show();
					 scrollToElement(".cargandobusqueda");
					 var div1padre = $(this).parent();
					 var div2padre = div1padre.parent();
					 var div3padre = div2padre.parent();
					 var formpadre = div3padre.parent();

					
					 if(comprobar())
					 {
						
						 setTimeout(function(){formpadre.submit();},5000);
						 
					 }
					 else
					 {	 
						 $(".cargandobusqueda").hide();
						 $(".wrape").show();
						// return false;
						 //$("#msgCargando").hide();
					 }
//					 setTimeout(function()
//							 {
//								 if(comprobar())
//								 {
//									 return true;
//									 
//								 }
//								 else
//								 {	 
//									 $(".cargandobusqueda").hide();
//									 $(".wrape").show();
//									 return false;
//									 //$("#msgCargando").hide();
//								 }
//							 },10000);
				 });
				
				$('#otros').append('<div style="text-align:center;"><img src="images/iconocarga.gif" /></div>');
				
				$.ajax({ //Listado de coches mas cercanos 
				    type: "POST",
				    url: "ajax/conseguirlistadooficinas.jsp",
				    data: "checkoutdate="+fecharec+"&checkouttime="+horarec+"&checkindate="+fechaent+"&checkintime="+horaent+"&carType="+crtype+"&codstationori="+codori+"&codstationdes="+coddes+"&provincia="+provincia,
				    async:true,
				    success: function(msg)
				    {
				    	  msg=jQuery.trim(msg);
				    	if(msg.length>0 && msg!="<h2>Otras Localidades:</h2>")
				    	{	
				    		$("#otros").fadeIn(1000).html(msg);
				    	}
				    	else
				    	{
				    		$("#otros").html("<p>NO hay sitios cercanos disponibles</p>");	
				    	}
				    	
				    	$(".buscarloc").on("click",function(){
					    	 
					    	 var dato = $(this).attr("id");
					    	 var descrestacion= $(this).attr("estacion");
					    	 $("#station").val(descrestacion);
					    	 $("#stationOrig").val(descrestacion);
					    	 $("#stationid").val(dato);
					    	 $("#stationOrigid").val(dato);
					    	 //$("#stationDestid").val(dato);
//					    	 $(".wrape").hide();
//					    	 $(".busquedaotraslocalidades").show();
//					    	 
//					    	 if(comprobar())
//							 {
//					    		 $("#formBuscador").submit();
//							 }
//					    	 else
//					    	 {
//					    		 $(".wrape").show();
//						    	 $(".busquedaotraslocalidades").hide();
//					    	 }
					    	 $("#buscar").click();
					     });
				    	
				    },
				    error: function()
				    {
				    	$("#otros").html("<p>Error en la localización</p>");
				    }
				});
				
				 $( document ).tooltip({
					 items: "[maleta],[asiento],[eco]",
					 content: function() {
								 var element = $( this );
								 if ( element.is( "[maleta]" ) ) {
								 //var text = element.text();
								     return "<img src='images/maleta.png' alt='maletas'>capacidad de equipaje:"+element.attr("maleta");
								 }
								 if ( element.is( "[asiento]" ) ) {
									 return "<img src='images/asiento.png' alt='asientos'>capacidad de pasajeros:"+element.attr("asiento");
								 }
								 if ( element.is( "[eco]" ) ) {
									 return "<img src='images/eco.png' alt='ecologico'>Vehiculo ecológico:"+element.attr("eco")+"g/km<p>Nivel A: hasta 100g/km</p>";
								 }
							 },
					 position: {
						 my: "center bottom-20",
						 at: "center top",
						 using: function( position, feedback ) {
								 $( this ).css( position );
								 $( "<div>" )
								 .addClass( "arrow" )
								 .addClass( feedback.vertical )
								 .addClass( feedback.horizontal )
								 .appendTo( this );
								 }
					     }
					 });
				 
				 $( "#dialog-condiciones" ).dialog({
					 autoOpen: false,
					 height: 600,
					 width: 850,
					 modal: true
					 });
				 
				 $( ".rate" ).click(function(event) {
					 
					 event.preventDefault();
					 var titulo = $(this).attr("titulo");
					 var cod = $(this).attr("codigo");
					 var cat = $(this).attr("categoria");
					 var imagen = $(this).attr("imagen");
					 var km = $(this).attr("km");
					 var proteccion =  $(this).attr("proteccion");
					 var robo =  $(this).attr("robo");
					 var colision = $(this).attr("colision");
					 var accidentesmej =  $(this).attr("accidentesmej");
					 var accidentes =  $(this).attr("accidentes");
					 var mercancias = $(this).attr("mercancias");
					 var multas = $(this).attr("multas");
					 var adicional =  $(this).attr("adicional");
					 var htmliconos = $("#iconoscar"+cod).html();
					 var htmlcarac = $("#carac"+cod).html();
					 htmliconos=jQuery.trim(htmliconos);
					 htmlcarac=jQuery.trim(htmlcarac);
					 
					 if(km=="UNLIMITED")
					 {
						 $("#textokm").text($("#text_ilim").text());
					 }else
					 {
						 $("#textokm").text(km+$("#text_lim").text());//. por el alquiler y &#8364; 0.13 ( &#8364; 0.16 IVA incluido ) por km. adicional");
					 }
					 
					 if(proteccion!="")
					 {	 
					    $("#proteccion").text(proteccion);
					 }
					 else
					 {
						 $("#proteccionli").css("display","none");
					 }
					 
					 if(robo!="")
					 {	 
					    $("#robo").text(robo);
					 }
					 else
					 {
						 $("#roboli").css("display","none");
					 }
					 
					 if(colision!="")
					 {	 
					    $("#colision").text(colision);
					 }
					 else
					 {
						 $("#colisionli").css("display","none");
					 }
					 
					 if(accidentesmej!="")
					 {	 
					    $("#accidentesmej").text(accidentesmej);
					 }
					 else
					 {
						 $("#accidentesmejli").css("display","none");
					 }
					 
							 
					 if(accidentes!="")
					 {	 
					    $("#accidentenormal").text(accidentes);
					 }
					 else
					 {
						 $("#accidentesli").css("display","none");
					 }
					
					 if(mercancias!="")
					 {	 
					    $("#mercancias").text(mercancias);
					 }
					 else
					 {
						 $("#mercanciasli").css("display","none");
					 }
					 
					 if(multas!="")
					 {	 
					    $("#multas").text(multas);
					 }
					 else
					 {
						 $("#multasli").css("display","none");
					 }
					 										 
					 
					 //$("#dialog-condiciones").attr("title",titulo);
					 //$(".cat").html("<h3>Categor&iacute;a del veh&iacute;culo:"+cat+"</h3>");
					 $("#typeV").text("");
					 $("#typeV").text(cat);
					 $(".categoriacond").html(htmlcarac);
					 $(".featurescond").html(htmliconos);
					 $("#imagencondicion").attr("src","http://microsite.europcar.com/ECI/mkt/epcarvisuals/600x350/"+imagen);
					 
					 $( "#dialog-condiciones" ).dialog({ title: titulo});
					 $( "#dialog-condiciones" ).dialog( "open" );
					 });
				   
				 //Para que tanto el div de otras localidades y el de busqueda se queden fijos
				 var otrasloca = $('#otros'), posicion = otrasloca.offset(), posicionbuscador = otrasloca.offset();
				 var infofin = $('#infofinal');//, posicioninfofinal = infofin.offset();
				 var infofintop;
				 var margenSuperior = 15;
				 //var alturaotraslocalidades = 300;
				 var alturabusqueda = 455;
				 var alturaslider = 500; //altura inicial de la capa del slider
				 $(window).scroll(function(){
//						 if($(this).scrollTop() > pos.top+otrasloca.height() && otrasloca.hasClass('content')){
//							 		otrasloca.fadeOut('fast', function(){
//							 				$(this).removeClass('').addClass('fixed').fadeIn('fast');
//							 			});
//						 } else if($(this).scrollTop() <= pos.top && otrasloca.hasClass('fixed')){
//							 		menu.fadeOut('fast', function(){
//							 			$(this).removeClass('fixed').addClass('default').fadeIn('fast');
//							 		});
//						 }
					 
					 if ($(window).scrollTop() > posicion.top) {
						 infofintop = infofin.position().top;
						 var posotros= posicion.top;
						 var posicionotros = $("#otros").css("margin-top");
						 posicionotros = posicionotros.substring(0,posicionotros.length-2);
						 posicionotros = parseInt(posicionotros);
						 if((infofintop-alturaslider) > (posicionotros+alturabusqueda))
						 {	 
							 $("#otros").stop().animate({
				                 marginTop: $(window).scrollTop() - posotros + margenSuperior
				             });
						 } else {
				             $("#otros").stop().animate({
				                 marginTop: 0
				             });
						 };
			         } else {
			             $("#otros").stop().animate({
			                 marginTop: 0
			             });
			         };
			         
			         if ($(window).scrollTop() > posicionbuscador.top) {
			        	 infofintop = infofin.position().top;
			        	 var posbuscador = posicionbuscador.top;
			        	 var posicionbusca = $("#busquedaestaciones").css("margin-top");
			        	 
			        	 posicionbusca = posicionbusca.substring(0,posicionbusca.length-2);
			        	 posicionbusca = parseInt(posicionbusca);
			        	 if((infofintop-alturaslider) > (posicionbusca+alturabusqueda))
						 {
				             $("#busquedaestaciones").stop().animate({
				                 marginTop: $(window).scrollTop() - posbuscador + margenSuperior
				             });
						 }else {
							
							
				             $("#busquedaestaciones").stop().animate({
				                 marginTop: 0
				             });
				            
						 };
			         } else {
			             $("#busquedaestaciones").stop().animate({
			                 marginTop: 0
			             });
			         };
				 }); 
				 var estacionorigen = $("#stationid").val();
				 var estaciondistorigen = $("#stationOrigid").val();
				 if(estacionorigen!="")
				 {	 
					 $("#stationOrigid").val(estacionorigen);
					 estaciondistorigen = estacionorigen;
				 }
				 else if(estaciondistorigen!="")
				 {
					 $("#stationid").val(estaciondistorigen);
				 }
				 
				 $(window).on('beforeunload', function(){
						location.href="servletRolecar?accion=cerrarSesion";
				    });
				 
		});//Fin document ready.

		function scrollToElement( target ) {
		    var topoffset = 30;
		    var speed = 800;
		    var destination = jQuery( target ).offset().top - topoffset;
		    jQuery( 'html:not(:animated),body:not(:animated)' ).animate( { scrollTop: destination}, speed, function() {
		        window.location.hash = target;
		    });
		    return false;
		}

		function comprobar()
		{
			var fechaIni=formateaFechaYYYYMMDD($("#fini").val());	
			 var fInicio=Date.parse(fechaIni);
			 var fhoy=formateaFechaDateYYYYMMDD(hoy);
			 var fdhoy=Date.parse(fhoy);
			 var station = $("#station").val();
			 var stationori = $("#stationOrig").val();
			 var stationdes = $("#stationDest").val();
			 
			 
			 if( ($("#station").css('display') != 'none') && (station === undefined || station == ""))
			 {
				 jAlert("Debes introducir una localidad","Alerta");
				 return false;
			 }
			 
			 if( ($("#stationOrig").css('display') != 'none') && (stationori === undefined || stationori == ""))
			 {
				 jAlert("Debes introducir una localidad origen","Alerta");
				 return false;
			 }
			 
			 if( ($("#stationDest").css('display') != 'none') && (stationdes === undefined || stationdes == ""))
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
		        numberOfMonths: 2,
		        onSelect: function(selected) {
		          $("#ffin").datepicker("option","minDate", selected);      
		        }
		    });
		    $("#ffin").datepicker({ 
		    	minDate: 0,
		    	dateFormat:"dd/mm/yy" ,
			 	//dateFormat:"d M, y" ,
		        numberOfMonths: 2,
		        onSelect: function(selected) {
		           $("#fini").datepicker("option","maxDate", selected);
		        }
		    });  
		}
		
		function inicializarHoraInicio(horai,horaf)
		{

			$("#hinis").val(horai);
			$("#hfins").val(horaf);
			cargarhorasminutosinicial();
		    cargarhorasminutosfinal();
		 
			
			
			
		}
		
		function HoraRecogidaValida()
		{
			var h = hora_act+2;
			$("#hinis").val("13:00");
			$("#hfins").val(h+":00");

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
			 else if(dif < 120)
			 {
				 jAlert("Error. La hora mínima de recogida es de 2 horas más, de la hora actual.Se coloca hora mas cercana valida","Error");
				 //inicializarHoraInicio();
				 HoraRecogidaValida();
				 return false;
			 }
			 return true;
		}