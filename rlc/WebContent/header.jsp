<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%-- <c:set var="local" value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}"/> --%>
<%-- <c:set var="local" value="${cookie.idioma.value}"/> --%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- 		<meta name="viewport" content="width=device-width, initial-scale=1"> -->
		<title>Rolecar</title>
		<link rel="stylesheet" href="scripts/plugins/rs-plugin/css/settings.css">
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
		<link href="stylesheets/lang/polyglot-language-switcher.css" type="text/css" rel="stylesheet">
		<link rel="stylesheet" href="stylesheets/busqueda.css" type="text/css" media="screen">
		<link href="stylesheets/main.css" type="text/css" rel="stylesheet" media="screen">
		<script src="scripts/jquery-1.11.0.min.js" type="text/javascript"></script>
		<script src="scripts/jquery-ui.min.js" type="text/javascript"></script>
		<script src="scripts/plugins/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
		<script src="scripts/plugins/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>	
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
//          ,afterLoad: function(evt){
//              alert("The selected language has been loaded");
//          },
//          beforeOpen: function(evt){
//              alert("before open");
//          },
//          afterOpen: function(evt){
//              alert("after open");
//          },
//          beforeClose: function(evt){
//              alert("before close");
//          },
//          afterClose: function(evt){
//              alert("after close");
//          }
		});
	});
	
		</script>
		
<!-- 		<link rel="icon" type="image/png" href="images/favicon4.png" /> -->
	</head>
	<body>
		<div class="dockbar">
			<div class="wraper">
				<div class="social-nav-header">	
						<a href="principalinicial.jsp"><fmt:message key="header.inicio"/></a>
						<a href="https://twitter.com/rolecar"><i class="fa fa-twitter"></i></a>
						<a href="https://www.facebook.com/rolecardrive"><i class="fa fa-facebook"></i></a>
						<a href="https://plus.google.com/u/0/100151209502610081833/posts"><i class="fa fa-google-plus"></i></a>
				</div>
				
			</div>
		</div>
		<div id="cabecera" class="wraper amp-space-down-20">
	 		<header class="header">
	  			<a class="logo" href=""><img src="images/logo3d.png" /></a>
	  			<h2 id="title" class="amp-titulo-lengenda">&nbsp;<fmt:message key="header.title"/></h2>
<!-- 	  			<nav id="mobilenav"> -->
<!-- 		     		<ul> -->
<!-- 		       			<li><a href="principalinicial.jsp"><fmt:message key="header.inicio"/></a></li> -->
<!-- 		     		</ul> -->
<!-- 	  			</nav> -->
	    		<nav id="defaultnav">
					<ul>
<!-- 						<li><a href="principalinicial.jsp"><fmt:message key="header.inicio"/></a></li> -->
				        <li style="background: none" class="wimage">
<!-- 				        <fmt:message key="header.idiomas"/> -->
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
				        </li>
					</ul>
				</nav> 
	 		</header>
		</div>
		<hr class="blue-line amp-space-down-30"/>
		<form id="formLang" action="servletRolecar?accion=reservar" method="post"><input name="idioma" id="idioma" type="hidden" value="es" /></form>
		