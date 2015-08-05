 $.datepicker.regional['es'] = {
 closeText: 'Cerrar',
 prevText: '<Ant',
 nextText: 'Sig>',
 currentText: 'Hoy',
 monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
 monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
 dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi\u00e9rcoles', 'Jueves', 'Viernes', 'S\u00e1bado'],
 dayNamesShort: ['Dom','Lun','Mar','Mi�','Juv','Vie','S\u00e1b'],
 dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','S\u00e1'],
 weekHeader: 'Sm',
 dateFormat: 'dd/mm/yy',
 firstDay: 1,
 isRTL: false,
 showMonthAfterYear: false,
 yearSuffix: ''
 };
 $.datepicker.setDefaults($.datepicker.regional['es']);

jQuery(function() {

	var myName = jQuery('#name');
	myName.focus(function() { if (jQuery(this).val() == 'NAME ...') {jQuery(this).val('');} });
	myName.blur(function() { if (jQuery(this).val() == '') {jQuery(this).val('NAME ...');} });		
	var myEmail = jQuery('#email');
	myEmail.focus(function() { if (jQuery(this).val() == 'EMAIL ...') {jQuery(this).val('');} });
	myEmail.blur(function() { if (jQuery(this).val() == '') {jQuery(this).val('EMAIL ...');} });			
	jQuery('.top').click(function () {
		jQuery('body,html').animate({
			scrollTop: 0
		}, 800);
		return false;
	});
	jQuery('.header nav ul ul li a').append('<span>&nbsp;</span>');
	jQuery('.featured_list li a').append('<span class="zoom">&nbsp;</span>');
	
	jQuery('.top_title, .text_bar2').prepend('<div class="text_bar_shadow"></div>').append('<div class="text_bar_shadow2"></div>');
	jQuery('.features_block ul li, .features2_block ul li, .bc_list ul li, .features5_block ul li, .services_option4 ul li, .small_icons ul li, .medium_icons ul li, .large_icons ul li').prepend('<span class="circle"></span>');
	
	jQuery('#tabs > div').hide();
	jQuery('#tabs > div:first').show();
	jQuery('#tabs > ul li:first').addClass('active');
	jQuery('#tabs > ul li > a').click(function(){
		jQuery('#tabs > ul li').removeClass('active');
		jQuery(this).parent().addClass('active');
		var currentTab = jQuery(this).attr('href');
		jQuery('#tabs > div').hide();
		jQuery(currentTab).show();
		return false;
	});
	
	jQuery('#h_tabs > div').hide();
	jQuery('#h_tabs > div:first').show();
	jQuery('#h_tabs > ul li:first').addClass('active');
	jQuery('#h_tabs > ul li a').click(function(){
		jQuery('#h_tabs > ul li').removeClass('active');
		jQuery(this).parent().addClass('active');
		var currentTab2 = jQuery(this).attr('href');
		jQuery('#h_tabs > div').hide();
		jQuery(currentTab2).show();
		return false;
	});
	
	jQuery('#s_tabs div').hide();
	jQuery('#s_tabs div:first').show();
	jQuery('#s_tabs ul li:first').addClass('active');
	jQuery('#s_tabs ul li a').click(function(){
		jQuery('#s_tabs ul li').removeClass('active');
		jQuery(this).parent().addClass('active');
		var currentTab3 = jQuery(this).attr('href');
		jQuery('#s_tabs div').hide();
		jQuery(currentTab3).show();
		return false;
	});
	
}); 

//jQuery('#accordions').dltoggle();

jQuery(document).ready(function() {
	jQuery.each(jQuery(".close"), function(index, element) {
		jQuery(this).click(function() {
			jQuery(this).parent().hide();
			return false;
		});
	});
	jQuery.each(jQuery(".slider"), function(index, element) {
		jQuery(element).bxSlider();
	});
});

//CBC Formateo fecha (dd/mm/yyyy) a yyyy-mm-dd rellenando con los d�gitos correspondientes
function formateaFechaYYYYMMDD(fechafmt)
{
	var arrfecha = fechafmt.split("/");
	var anyo = arrfecha[2];
	var mes = arrfecha[1];
	var dia = arrfecha[0];
	var fechaux;
	if (anyo.length<4)
		anyo = "0"+anyo;
	if (mes.length<2)
		mes = "0" + mes;
	if (dia.length<2)
		dia = "0" + dia;
	fechaux = anyo + "-" + mes + "-" + dia;
	return fechaux;
}
//Compara 2 fechas (fecha1,fecha2) en javascript similar al compareTo de Java
//1: fecha1 es mayor que fecha2
//0: fecha1 es igual que fecha2
//-1: fecha1 es menor que fecha2
function comparaFechas(fecha1,fecha2)
{
	//fecha1 = formateaFechaYYYYMMDD(fecha1);
	//fecha2 = formateaFechaYYYYMMDD(fecha2);
	var fecha1d = Date.parse(fecha1);
	var fecha2d = Date.parse(fecha2);
	if (fecha1d>fecha2d)
		return 1;
	else if (fecha1d==fecha2d)
		return 0;
	else if (fecha1d<fecha2d)
		return -1;
	else
		return 1;
}

function formateaFechaDateYYYYMMDD(fechaDate)
{
	var dia=fechaDate.getDate();
	var mes=fechaDate.getMonth()+1;
	var anio=fechaDate.getFullYear();
	var fechaux;
	if (anio.length<4)
		anyo = "0"+anyo;
	if (mes < 10)
		mes = "0" + mes;
	if (dia <10)
		dia = "0" + dia;
	fechaux = anio + "-" + mes + "-" + dia;
	return fechaux;
	
}

function formateaFechaDateDDMMYYYY(someDate)
{
	var dd = someDate.getDate();
	var mm = someDate.getMonth() + 1;
	var y = someDate.getFullYear();
	var dds = ""+dd;
	var mms = ""+mm;
	if(dds.length<2)
		{
		  dds="0"+dds;
		}
	if(mms.length<2)
		{
		  mms = "0"+mms;
		}
	
	var someFormattedDate = dds + '/'+ mms + '/'+ y;
	
	return someFormattedDate;
	
}
/* slider revolution HOME*/
/**/
$("document").ready(function(){
	$('#slider-revolution').revolution(
			{
				dottedOverlay:"none",
				delay:16000,
				startwidth:1170,
				startheight:500,
				hideThumbs:200,
				
				thumbWidth:100,
				thumbHeight:50,
				thumbAmount:5,
				
				navigationType:"bullet",
				navigationArrows:"solo",
				navigationStyle:"preview1",
				
				touchenabled:"on",
				onHoverStop:"on",
				
				swipe_velocity: 0.7,
				swipe_min_touches: 1,
				swipe_max_touches: 1,
				drag_block_vertical: false,
										
				parallax:"mouse",
				parallaxBgFreeze:"on",
				parallaxLevels:[7,4,3,2,5,4,3,2,1,0],
										
				keyboardNavigation:"off",
				
				navigationHAlign:"center",
				navigationVAlign:"bottom",
				navigationHOffset:0,
				navigationVOffset:20,
				
				soloArrowLeftHalign:"left",
				soloArrowLeftValign:"center",
				soloArrowLeftHOffset:20,
				soloArrowLeftVOffset:0,
				
				soloArrowRightHalign:"right",
				soloArrowRightValign:"center",
				soloArrowRightHOffset:20,
				soloArrowRightVOffset:0,
						
				shadow:0,
				fullWidth:"on",
				fullScreen:"off",
				
				spinner:"spinner4",
				
				stopLoop:"off",
				stopAfterLoops:-1,
				stopAtSlide:-1,
				
				shuffle:"off",
				
				autoHeight:"off",						
				forceFullWidth:"off",
				
				hideThumbsOnMobile:"off",
				hideNavDelayOnMobile:1500,						
				hideBulletsOnMobile:"off",
				hideArrowsOnMobile:"off",
				hideThumbsUnderResolution:0,
				
				hideSliderAtLimit:0,
				hideCaptionAtLimit:0,
				hideAllCaptionAtLilmit:0,
				startWithSlide:0,
				videoJsPath:"rs-plugin/videojs/",
				fullScreenOffsetContainer: ""	
			});
});

