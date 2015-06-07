<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<%@ include file="language.jsp"%>
<%@page import="com.rolecar.beans.Station"%>
<%@page import="java.util.Vector"%>
<%@ include file="header.jsp"%>

<%
int tama=0;
String cero="";
String selec=""; 
String mensaje="";
//Vector<Station> vstat = (Vector<Station>) request.getSession().getAttribute("vStations");
Vector<Station> vstat =null;
vstat=(Vector<Station>)request.getSession(false).getAttribute("vStations");
if(vstat!=null)
	tama=vstat.size();

if(request.getAttribute("mensa")!=null)
	mensaje =(String)request.getAttribute("mensa");

request.getSession(false).removeAttribute("Vehiculossesion");

%>

<script type="text/javascript">

var mensaj="<%=mensaje%>";
var tam=<%=tama%>;

var arrstations = new Array(tam);
var dato="";

if(mensaj.length>0)
	{
      jAlert(mensaj,"Alerta");
	}

<%  int i;//Lo declaramos fuera del bucle, porque sino a veces da problemas
    String valoractual="";

	for(i=0;i<vstat.size();i++)
	{
		Station stat= new Station();
		
		
		//stat.setCodCity(vstat.elementAt(i).getCodCity());
		stat=vstat.elementAt(i);
		valoractual=stat.getDescr().concat(" , ").concat(stat.getDescrCountry());
		valoractual=valoractual.replace("\"", "'");
		%>
		arrstations[<%=i%>] = {"label":"<%=valoractual%>", "value":"<%=valoractual%>","id":"<%=stat.getCodstation()+"::"+stat.getIdprovincia()+"::"+stat.getCodCountry()%>"} ;	
<%
	}%>
	

</script>
<script src="scripts/fbusqueda.js"></script>

<h3></h3>
<div class="container">
	<form id="formBuscador" action="servletRolecar?accion=buscar"
		method="post">

		<div class="tab-content responsive">
			<ul class="nav nav-tabs" id="myTab">
				          <li class="test-class active"><a class="deco-none misc-class" href="#how-to"><span class="glyphicon glyphicon-cog"></span> <fmt:message key="busqueda.coches"/></a></li>
				          <li class="test-class"><a href="#features"> <fmt:message key="busqueda.furgonetas"/></a></li>
				          
			</ul>
			<div class="tab-pane active" id="how-to">
				<div class="block block-catalog-toolbar clearfix">
					<form>
					
						<div class="input_ciudad">
							<input id="station"  name="station" type="text" placeholder="<fmt:message key='busqueda.station.text'/>">
							<input type="hidden" id="stationid" name="stationid" />
						</div>
						<a href="#" class="direction fa fa-calendar"></a>
						<div id="fechainicial" class="input_fecha">
							<input id="fini"  name="fini" class="control-fecha" type="text"  readonly >
						</div>
						<div class="input_hora">
							<select id="hinis" name="hinis" class="select_hora">
								<%
								      for(int hor=0;hor<24;hor++)
									    {
								    	   if(hor<10)
								    	   {   
										       cero="0";
										       selec="";
								    	   }
								    	   else if(hor==12)
								    	   {
								    			selec="selected='selected'";
								    			cero="";
								    	   }
								    	   else
								    	   {
								    		   cero="";
								    		   selec="";
								    	   }
								    	   
								    	   %>
								<option value="<%=cero+hor%>:00" <%=selec%>><%=cero+hor%>:00
								</option>
								<option value="<%=cero+hor%>:30"><%=cero+hor%>:30
								</option>
								<%
								    	    	   
									    }
								    %>

							</select> 
							<input type="hidden" id="hini" name="hini" /> 
							<input type="hidden" id="mini" name="mini" />
						</div>
						<a href="#" class="direction fa fa-calendar"></a>
						<div id="fechainicial" class="input_fecha">
							<input id="ffin" name="ffin" type="text"  class="control-fecha" readonly />
						</div>
						<a href="#" class="direction fa fa-clock-o"></a>
						<div class="input_hora">
							<select id="hfins" name="hfins" class="select_hora">
								<%
								      for(int hor=0;hor<24;hor++)
									    {
								    	   if(hor<10)
								    	   {   
										       cero="0";
										       selec="";
								    	   }
								    	   else if(hor==12)
								    	   {
								    			selec="selected='selected'";
								    			cero="";
								    	   }
								    	   else
								    	   {
								    		   cero="";
								    		   selec="";
								    	   }
								    	   
								    	   %>
								<option value="<%=cero+hor%>:00" <%=selec%>><%=cero+hor%>:00
								</option>
								<option value="<%=cero+hor%>:30"><%=cero+hor%>:30
								</option>
								<%
								    	    	   
									    }
								    %>
							</select> <input type="hidden" id="hfin" name="hfin" /> <input
								type="hidden" id="mfin" name="mfin" />
						</div>
						<input type="button" class="direction_buscar" id="buscar"
							name="buscar" value="<fmt:message key='busqueda.buscar'/>" />
						<!--   <a href="#" id="buscar" name="buscar" class="direction_buscar" value="<fmt:message key='busqueda.buscar'/>">BUSCAR</a>-->
				</div>
			<div id="tsucursal" style="">
					<input type="radio" id="tsucursal1" name="tsucursal" checked="checked" value="tsucursal1"><label for="tsucursal1"><fmt:message key="busqueda.misma.oficina"/></label>
					<input type="radio" id="tsucursal2" name="tsucursal" value="tsucursal2"><label for="tsucursal2"><fmt:message key="busqueda.distinta.oficina"/></label>
					
				</div>
			</div>
			
			<div class="tab-pane" id="features"></div>

		</div>
	</form>
</div>
<!-- /container -->

</header>
<div></div>
<!--/ page header bottom -->
<!-- slideshow -->
<div class="slider-revolution">
	<div id="slider-revolution">
		<ul>
			<li data-transition="cube"><img src="img/audio.jpg" alt="">
				<div
					class="tp-caption title skewfromrightshort tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="80" data-y="center" data-speed="500"
					data-start="500" data-easing="Power3.easeInOut"
					data-splitin="chars" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">Encontramos
					para ti el mejor precio</div>
				<div class="tp-caption sft tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-215" data-y="center"
					data-voffset="-96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3000" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
					<!--<i class="fa fa-clock-o"></i>-->
				</div>
				<div class="tp-caption sfl tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-300" data-y="center"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3300" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-215" data-y="center"
					data-voffset="96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3600" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="265" data-y="center"
					data-voffset="91"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="4000" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
					<!--<a href="#" class="button-my">Purchase Now</a>-->
				</div></li>
			<li data-transition="cube"><img src="img/fiesta.jpg" alt="">
				<div
					class="tp-caption title skewfromrightshort tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="80" data-y="center" data-speed="500"
					data-start="500" data-easing="Power3.easeInOut"
					data-splitin="chars" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">Sin
					intermediarios. Grandes descuentos</div>
				<div class="tp-caption sft tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-215" data-y="center"
					data-voffset="-96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3000" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfl tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-300" data-y="center"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3300" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center" data-hoffset="-215" data-y="center"
					data-voffset="96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500" data-start="3600" data-easing="Power3.easeInOut"
					data-splitin="none" data-splitout="none" data-elementdelay="0.1"
					data-endelementdelay="0.1" data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div></li>

		</ul>
	</div>
</div>
<!--/ slideshow -->
			<!-- page content -->
			<div class="page-content">
				<!-- page content section -->
				
			<div class="page-content-section">
					<div class="grid-row">
						<!-- about -->
						<div class="block block-about">
							<div class="block-head block-head-2">Rolecar se adapta a ti<i></i></div>
							<div class="block-cont">
								<h2>Rolecar te ofrece la mejor tarifa! ¡Sin intermediarios!</h2>
								<p>Consigue descuentos de hasta un 70% y reservalo directamente. Negociamos con las mejores compañías de alquiler para ofrecerte el mejor precio.</p>
								<a href="leerMas.html" class="button">Leer Más</a>
								
							</div>
						</div>
						<!--/ about -->
					</div>
				</div>
			</div>
			<!--/ page content -->
<%@ include file="footer.jsp"%>