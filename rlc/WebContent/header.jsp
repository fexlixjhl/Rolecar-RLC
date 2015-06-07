<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%-- <c:set var="local" value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}"/> --%>
<%-- <c:set var="local" value="${cookie.idioma.value}"/> --%>
<!DOCTYPE html>
<html>
	<head>
		<title>Rolecar</title>
		
		<!-- metas -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
		<!--/ metas -->
		
		<!-- favicon -->
		<link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
		<!--/ favicon -->
		
		<!-- styles -->
		<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="screen">
		<link rel="stylesheet" href="css/font-awesome.css">
		<link rel="stylesheet" href="css/jquery.fancybox.css">
		<link rel="stylesheet" href="css/jquery.owl.carousel.css">
		<link rel="stylesheet" href="js/rs-plugin/css/settings.css">
		<link rel="stylesheet" href="css/main.css">
		<link rel="stylesheet" href="css/color.css">
		<link href="stylesheets/lang/polyglot-language-switcher.css" type="text/css" rel="stylesheet">
		
<!-- 		<script src="scripts/jquery-1.11.0.min.js" type="text/javascript"></script> -->
		<script src="js/jquery.min.js"></script>
		<script src="scripts/jquery-ui.min.js" type="text/javascript"></script>
		
		<script src="scripts/jquery.bxslider.min.js"></script>
		<script src="scripts/jquery.alerts.js"></script>
		<script src="js/jquery.counter.js"></script>
		<script src="js/jquery.knob.min.js"></script>
		<script src="js/jquery.form.min.js"></script>
		<script src="js/jquery.isotope.min.js"></script>
		<script src="js/jquery.validate.min.js"></script>
		<script src="js/jquery.countdown.min.js"></script>
		<script src="js/jquery.fancybox.pack.js"></script>
		<script src="js/jquery.fancybox-media.js"></script>
		<script src="js/jquery.imagesloaded.min.js"></script>
		<script src="js/jquery.owl.carousel.min.js"></script>
		<script src="js/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
		<script src="js/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>	
		<!--[if lt IE 10]><script src="js/jquery.placeholder.min.js"></script><![endif]-->
		<script type="text/javascript" src="scripts/js_func.js" charset="utf-8"></script>
		<script src="scripts/lang/jquery.polyglot.language.switcher.js" type="text/javascript"></script>
		<script src="js/main.js"></script>
<!-- 		<script src="js/bootstrap.min.js"></script> -->

    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-17600125-2', 'openam.github.io');
      ga('send', 'pageview');
      
      $( document ).ready(function() {
//     	  $( "#lengua" ).change(function() {
//     		  $("#idioma").val($( "#lengua option:selected" ).val());
//     		  //alert( "Handler for .change() called: " + $( "#lengua option:selected" ).attr("value") );
//     		  $("#formLang").submit();
//     		});
    	  $('#polyglotLanguageSwitcher').polyglotLanguageSwitcher({
    			effect: 'fade',
              testMode: true,
              onChange: function(evt){
                  //alert("The selected language is: "+evt.selectedItem);
                  $("#formLang").attr("action", "servletRolecar?accion=reservar");
            	  $("#idioma").val(evt.selectedItem);
            	  $("#formLang").submit();
              }
//              ,afterLoad: function(evt){
//                  alert("The selected language has been loaded");
//              },
//              beforeOpen: function(evt){
//                  alert("before open");
//              },
//              afterOpen: function(evt){
//                  alert("after open");
//              },
//              beforeClose: function(evt){
//                  alert("before close");
//              },
//              afterClose: function(evt){
//                  alert("after close");
//              }
    		});
    	});
    </script>
	</head>
	
	<body>
		<div class="page">
			<!-- page header top -->
			<!-- page header bottom -->
			<header id="page-header-bottom" class="page-header-bottom">
				<div id="page-header-top" class="page-header-top">
					<div class="social-nav-header">	
						<a href=""><fmt:message key="header.inicio"/></a>
						<a href="https://twitter.com/rolecar"><i class="fa fa-twitter"></i></a>
						<a href="https://www.facebook.com/rolecardrive"><i class="fa fa-facebook"></i></a>
						<a href="https://plus.google.com/u/0/100151209502610081833/posts"><i class="fa fa-google-plus"></i></a>
					</div>
				</div>
				<div class="grid-row">
				
					<!-- logo -->
					<div class="logo">
						<span><a href="inicio.html"><img src="img/logo.png" alt=""></a> <fmt:message key="header.title"/></span>
					</div>
					<div class="idioma">
					<ul>
						
				        <li style="background: none" class="wimage">

				        <div id="polyglotLanguageSwitcher">
							<form id="lenguaje" action="#">
					        	<select id="polyglot-language-options" >
		  							<option id="es" value="es" data-image="images/flags/spain.png" ${local eq 'es' or local eq null ? 'selected' : ''}><fmt:message key="header.idioma.es"/></option>
		  							<option id="en" value="en" ${local eq 'en' ? 'selected' : ''}><fmt:message key="header.idioma.en"/></option>
		  							<option id="us" value="us" ${local eq 'us' ? 'selected' : ''}><fmt:message key="header.idioma.us"/></option>
		  							<option id="fr" value="fr" ${local eq 'fr' ? 'selected' : ''}><fmt:message key="header.idioma.fr"/></option>
		  							<option id="de" value="de" ${local eq 'de' ? 'selected' : ''}><fmt:message key="header.idioma.de"/></option>
		  							<option id="el" value="el" ${local eq 'el' ? 'selected' : ''}><fmt:message key="header.idioma.gr"/></option>
		  							<option id="sq" value="sq" ${local eq 'sq' ? 'selected' : ''}><fmt:message key="header.idioma.ab"/></option>
		  							<option id="it" value="it" ${local eq 'it' ? 'selected' : ''}><fmt:message key="header.idioma.it"/></option>
		  							<option id="pt" value="pt" ${local eq 'pt' ? 'selected' : ''}><fmt:message key="header.idioma.pt"/></option>
								</select>
							</form>
						</div>
						<form id="formLang" action="servletRolecar?accion=reservar" method="post"><input name="idioma" id="idioma" type="hidden" value="es" /></form>
				        </li>
					</ul>
					</div>
					<div class="social-nav-header">	
						<a href="#"><i class="fa fa-twitter"></i></a>
							<a href="#"><i class="fa fa-facebook"></i></a>
							<a href="#"><i class="fa fa-google-plus"></i></a>
					</div>
				</div>
				<div id="page-header-top" class="page-header-top-mini"></div>
