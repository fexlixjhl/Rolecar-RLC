function formateaFechaYYYYMMDD(fechafmt){var fechaux,arrfecha=fechafmt.split("/"),anyo=arrfecha[2],mes=arrfecha[1],dia=arrfecha[0];return anyo.length<4&&(anyo="0"+anyo),mes.length<2&&(mes="0"+mes),dia.length<2&&(dia="0"+dia),fechaux=anyo+"-"+mes+"-"+dia}function comparaFechas(fecha1,fecha2){var fecha1d=Date.parse(fecha1),fecha2d=Date.parse(fecha2);return fecha1d>fecha2d?1:fecha1d==fecha2d?0:fecha2d>fecha1d?-1:1}function formateaFechaDateYYYYMMDD(fechaDate){var fechaux,dia=fechaDate.getDate(),mes=fechaDate.getMonth()+1,anio=fechaDate.getFullYear();return anio.length<4&&(anyo="0"+anyo),10>mes&&(mes="0"+mes),10>dia&&(dia="0"+dia),fechaux=anio+"-"+mes+"-"+dia}function formateaFechaDateDDMMYYYY(someDate){var dd=someDate.getDate(),mm=someDate.getMonth()+1,y=someDate.getFullYear(),dds=""+dd,mms=""+mm;dds.length<2&&(dds="0"+dds),mms.length<2&&(mms="0"+mms);var someFormattedDate=dds+"/"+mms+"/"+y;return someFormattedDate}$.datepicker.regional.es={closeText:"Cerrar",prevText:"<Ant",nextText:"Sig>",currentText:"Hoy",monthNames:["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],monthNamesShort:["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"],dayNames:["Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"],dayNamesShort:["Dom","Lun","Mar","Mi�","Juv","Vie","Sáb"],dayNamesMin:["Do","Lu","Ma","Mi","Ju","Vi","Sá"],weekHeader:"Sm",dateFormat:"dd/mm/yy",firstDay:1,isRTL:!1,showMonthAfterYear:!1,yearSuffix:""},$.datepicker.setDefaults($.datepicker.regional.es),jQuery(function(){var myName=jQuery("#name");myName.focus(function(){"NAME ..."==jQuery(this).val()&&jQuery(this).val("")}),myName.blur(function(){""==jQuery(this).val()&&jQuery(this).val("NAME ...")});var myEmail=jQuery("#email");myEmail.focus(function(){"EMAIL ..."==jQuery(this).val()&&jQuery(this).val("")}),myEmail.blur(function(){""==jQuery(this).val()&&jQuery(this).val("EMAIL ...")}),jQuery(".top").click(function(){return jQuery("body,html").animate({scrollTop:0},800),!1}),jQuery(".header nav ul ul li a").append("<span>&nbsp;</span>"),jQuery(".featured_list li a").append('<span class="zoom">&nbsp;</span>'),jQuery(".top_title, .text_bar2").prepend('<div class="text_bar_shadow"></div>').append('<div class="text_bar_shadow2"></div>'),jQuery(".features_block ul li, .features2_block ul li, .bc_list ul li, .features5_block ul li, .services_option4 ul li, .small_icons ul li, .medium_icons ul li, .large_icons ul li").prepend('<span class="circle"></span>'),jQuery("#tabs > div").hide(),jQuery("#tabs > div:first").show(),jQuery("#tabs > ul li:first").addClass("active"),jQuery("#tabs > ul li > a").click(function(){jQuery("#tabs > ul li").removeClass("active"),jQuery(this).parent().addClass("active");var currentTab=jQuery(this).attr("href");return jQuery("#tabs > div").hide(),jQuery(currentTab).show(),!1}),jQuery("#h_tabs > div").hide(),jQuery("#h_tabs > div:first").show(),jQuery("#h_tabs > ul li:first").addClass("active"),jQuery("#h_tabs > ul li a").click(function(){jQuery("#h_tabs > ul li").removeClass("active"),jQuery(this).parent().addClass("active");var currentTab2=jQuery(this).attr("href");return jQuery("#h_tabs > div").hide(),jQuery(currentTab2).show(),!1}),jQuery("#s_tabs div").hide(),jQuery("#s_tabs div:first").show(),jQuery("#s_tabs ul li:first").addClass("active"),jQuery("#s_tabs ul li a").click(function(){jQuery("#s_tabs ul li").removeClass("active"),jQuery(this).parent().addClass("active");var currentTab3=jQuery(this).attr("href");return jQuery("#s_tabs div").hide(),jQuery(currentTab3).show(),!1})}),jQuery(document).ready(function(){jQuery.each(jQuery(".close"),function(index,element){jQuery(this).click(function(){return jQuery(this).parent().hide(),!1})}),jQuery.each(jQuery(".slider"),function(index,element){jQuery(element).bxSlider()})}),$("document").ready(function(){if($("#slider-revolution").revolution({dottedOverlay:"none",delay:16e3,startwidth:1170,startheight:500,hideThumbs:200,thumbWidth:100,thumbHeight:50,thumbAmount:5,navigationType:"bullet",navigationArrows:"solo",navigationStyle:"preview1",touchenabled:"on",onHoverStop:"on",swipe_velocity:.7,swipe_min_touches:1,swipe_max_touches:1,drag_block_vertical:!1,parallax:"mouse",parallaxBgFreeze:"on",parallaxLevels:[7,4,3,2,5,4,3,2,1,0],keyboardNavigation:"off",navigationHAlign:"center",navigationVAlign:"bottom",navigationHOffset:0,navigationVOffset:20,soloArrowLeftHalign:"left",soloArrowLeftValign:"center",soloArrowLeftHOffset:20,soloArrowLeftVOffset:0,soloArrowRightHalign:"right",soloArrowRightValign:"center",soloArrowRightHOffset:20,soloArrowRightVOffset:0,shadow:0,fullWidth:"on",fullScreen:"off",spinner:"spinner4",stopLoop:"off",stopAfterLoops:-1,stopAtSlide:-1,shuffle:"off",autoHeight:"off",forceFullWidth:"off",hideThumbsOnMobile:"off",hideNavDelayOnMobile:1500,hideBulletsOnMobile:"off",hideArrowsOnMobile:"off",hideThumbsUnderResolution:0,hideSliderAtLimit:0,hideCaptionAtLimit:0,hideAllCaptionAtLilmit:0,startWithSlide:0,videoJsPath:"rs-plugin/videojs/",fullScreenOffsetContainer:""}),$("#map").length){for(var labels="ABCDEFGHIJKLMNOPQRSTUVWXYZ",labelIndex=0,limites=new google.maps.LatLngBounds,bangalore={lat:12.97,lng:77.59},madrid={lat:23.97,lng:77.59},yokese={lat:26.97,lng:74.59},misMarcadores=[madrid,bangalore,yokese],map=new google.maps.Map(document.getElementById("map"),{}),i=0;i<misMarcadores.length;i++){var marcador=new google.maps.Marker({position:new google.maps.LatLng(misMarcadores[i].lat,misMarcadores[i].lng),label:labels[labelIndex++%labels.length],map:map});limites.extend(marcador.position)}map.fitBounds(limites)}});