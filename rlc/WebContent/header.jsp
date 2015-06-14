<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%-- <c:set var="local" value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}"/> --%>
<%-- <c:set var="local" value="${cookie.idioma.value}"/> --%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- 		<meta name="viewport" content="width=device-width, initial-scale=1"> -->
		<title>Rolecar</title>
		
		<link rel="stylesheet" href="stylesheets/jquery-ui.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/style.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/flexslider.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/ie8.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/jquery.bxslider.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/flexslider.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/jquery.id.moover-1.5.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/nivoslider.css" type="text/css" media="screen">
		<link rel='stylesheet' id='onebyone-css'  href='stylesheets/jquery.onebyone.css?ver=3.9.1' type='text/css' media='all' />
		<link rel='stylesheet' id='prettyphoto-css'  href='stylesheets/prettyPhoto.css?ver=3.9.1' type='text/css' media='all' />
		<link rel="stylesheet" href="stylesheets/font-awesome.css" type="text/css" media="screen">
		<link rel="stylesheet" href="stylesheets/jquery.alerts.css" type="text/css" media="screen">
		<link rel="stylesheet" href="css/main.css" type="text/css" media="screen">
		<link rel="stylesheet" href="css/jquery.fancybox.css">
		<link rel="stylesheet" href="css/jquery.owl.carousel.css">
		<link rel="stylesheet" href="css/color.css">
		<link rel="stylesheet" href="js/rs-plugin/css/settings.css">
		<link type="text/css" rel="stylesheet" href="jquery.dropdown.css" />
		<script src="js/jquery.owl.carousel.min.js"></script>
		<script src="js/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
		<script src="js/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>	
		<!--  -->
		
		
		<!--  -->
		<link href="stylesheets/lang/polyglot-language-switcher.css" type="text/css" rel="stylesheet">
		<script src="scripts/jquery-1.11.0.min.js" type="text/javascript"></script>
		<script src="scripts/jquery-ui.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="scripts/js_func.js" charset="utf-8"></script>
		<script src="scripts/jquery.bxslider.min.js"></script>
		<script src="scripts/jquery.alerts.js"></script>
		<script src="scripts/lang/jquery.polyglot.language.switcher.js" type="text/javascript"></script>
		<!-- 	Google Analytics -->
		<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-48231009-1', 'auto');
  ga('send', 'pageview');
  $( document ).ready(function() {
// 	  $( "#lengua" ).change(function() {
// 		  $("#idioma").val($( "#lengua option:selected" ).val());
// 		  //alert( "Handler for .change() called: " + $( "#lengua option:selected" ).attr("value") );
// 		  $("#formLang").submit();
// 		});
	  $('#polyglotLanguageSwitcher').polyglotLanguageSwitcher({
			effect: 'fade',
          testMode: true,
          onChange: function(evt){
              //alert("The selected language is: "+evt.selectedItem);
              $("#formLang").attr("action", "servletRolecar?accion=reservar");
        	  $("#idioma").val(evt.selectedItem);
        	  $("#formLang").submit();
          }

		});
	});
	
		</script>
		</head>

	</head>
<body>
		<div class="page">
			<!-- page header top -->
		
			
			<!-- page header bottom -->
			<header id="page-header-bottom" class="page-header-bottom">
				<div id="page-header-top" class="page-header-top">
					<div class="social-nav-header">	
						<a href="#">Inicio</a>
						<a href="#"><i class="fa fa-twitter"></i></a>
							<a href="#"><i class="fa fa-facebook"></i></a>
							<a href="#"><i class="fa fa-google-plus"></i></a>
					</div>
				</div>
				<div class="grid-row">
				
					<!-- logo -->
					<div class="logo">
						<span><a href="inicio.html"><img src="img/logo.png" alt=""></a> TU BUSCADOR DE COCHES DE ALQUILER</span>
					</div>
					<div class="idioma">
					</div>
					<div class="social-nav-header">	
						<a href="#"><i class="fa fa-twitter"></i></a>
							<a href="#"><i class="fa fa-facebook"></i></a>
							<a href="#"><i class="fa fa-google-plus"></i></a>
					</div>
				</div>
				<div id="page-header-top" class="page-header-top-mini"></div>
				

	 
		<form id="formLang" action="servletRolecar?accion=reservar" method="post"><input name="idioma" id="idioma" type="hidden" value="es" /></form>
		