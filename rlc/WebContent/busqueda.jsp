<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<%@ include file="language.jsp" %>

<!-- Aqui añadir el header -->
<%@page import="com.rolecar.beans.Station"%>
<%@page import="java.util.Vector"%>
<%@ include file="header.jsp" %>

<%
int tama=0;
String cero="";
String selec=""; 
String mensaje="";
//Vector<Station> vstat = (Vector<Station>) request.getSession().getAttribute("vStations");
Vector<Station> vstat =null;
//Control de sesiones
vstat = (Vector<Station>) request.getAttribute("vStations");
if (vstat == null ){
	vstat=(Vector<Station>) request.getSession(false).getAttribute("vStations");
}
else{
	request.getSession(true).setAttribute("vStations", vstat);
}
if(vstat!=null){
	tama=vstat.size();  
}

if(request.getAttribute("mensa")!=null)
	mensaje =(String)request.getAttribute("mensa");
if (request.getSession(false) != null){
	request.getSession(false).removeAttribute("Vehiculossesion");
}

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



<div class="wraper homeone amp-space-down-30">
    <div id="formularioBuscador" class="oneByOne_item amp-clear-floats"  >
			<form id="formBuscador" action="servletRolecar?accion=buscar" method="post">
			 	<div id="tvhiculo" class="amp-contenedor-botones amp-space-down-30" style="display: none" >
					<input type="radio" id="tvhiculo1" name="tvhiculo" checked="checked" value="CR"><label for="tvhiculo1"><fmt:message key="busqueda.coches"/></label>
					<input type="radio" id="tvhiculo2" name="tvhiculo" value="TR" ><label for="tvhiculo2"><fmt:message key="busqueda.furgonetas"/></label>
				</div>
				<div id="tsucursal" style="" class="amp-contenedor-botones amp-space-down-10 ">
					<input type="radio" id="tsucursal1" name="tsucursal" checked="checked" value="tsucursal1"><label for="tsucursal1"><fmt:message key="busqueda.misma.oficina"/></label>
					<input type="radio" id="tsucursal2" name="tsucursal" value="tsucursal2"><label for="tsucursal2"><fmt:message key="busqueda.distinta.oficina"/></label>
					
				</div>	
				<div id="Campos">
				    <div class="campoleft visible">
						<input id="station"  name="station" type="text" placeholder="<fmt:message key='busqueda.station.text'/>" />
						<input type="hidden" id="stationid" name="stationid" />
						
					</div>
					<div class="campoleft oculta">
						<input id="stationOrig"  name="stationOrig" type="text"   placeholder="<fmt:message key='busqueda.stationOrig.text'/>" />
						<input type="hidden" id="stationOrigid" name="stationOrigid" />
					</div>
					<div class="campoleft oculta">
						<input id="stationDest"  name="stationDest" type="text"   placeholder="<fmt:message key='busqueda.stationDest.text'/>" />
						<input type="hidden" id="stationDestid" name="stationDestid" />
					</div>
					<div class="campoleft amp-space-left-30">
	 
						  <span id="fechainicial" class="input-group amp-boton-icono "><i class="fa fa-arrow-up fa-fw"></i></span>
				    </div>
				    <div class="campoleft amp-space-left-10">
						  <input id="fini"  name="fini" class="control-fecha amp-width-100" type="text"  readonly >
					</div>
					<div class="campoleft amp-space-left-10">
					   		
					   	<span id="horainicial" class="input-group amp-boton-icono "><i class="fa fa-clock-o fa-fw"></i></span>
					</div>
					<div class="campoleft sinborde amp-space-left-10" >		
						<select id="hinis" class="seleccion horas" name="hinis" class="control-fecha " style="border:none;height:200px;" >
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
						    	      <option value="<%=cero+hor%>:00" <%=selec%>><%=cero+hor%>:00</option>
						    	      <option value="<%=cero+hor%>:30"><%=cero+hor%>:30</option>
						    	   <%
						    	    	   
							    }
						    %>
										
						</select>
						<input type="hidden" id="hini" name="hini" />
						<input type="hidden" id="mini" name="mini" />
					</div>
					
					<div class="campoleft amp-space-left-30">
					        <span id="fechafinal" class="input-group amp-boton-icono"><i class="fa fa-arrow-down fa-fw"></i></span>
					</div>
					<div class="campoleft amp-space-left-10">
							<input id="ffin" name="ffin" type="text"  class="control-fecha amp-width-100" readonly />
					</div>
					<div class="campoleft amp-space-left-10">
					    <span id="horafinal" class="input-group amp-boton-icono"><i class="fa fa-clock-o fa-fw"></i></span>
					</div>
					<div class="campoleft sinborde amp-space-left-10">
						<select id="hfins" class="seleccion" name="hfins" class="control-fecha" style="border:none;height:200px;" >
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
						    	      <option value="<%=cero+hor%>:00" <%=selec%>><%=cero+hor%>:00</option>
						    	      <option value="<%=cero+hor%>:30"><%=cero+hor%>:30</option>
						    	   <%
						    	    	   
							    }
						    %>
						</select>
						<input type="hidden" id="hfin" name="hfin" />
					    <input type="hidden" id="mfin" name="mfin" />
					</div>
					<div class="campoleft ajustar ">
						<input type="button" class="boton amp-width-100 amp-space-left-30" id="buscar" name="buscar" value="<fmt:message key='busqueda.buscar'/>"/>
<!-- 						<input type="reset" class="boton" id="borrar" name="borrar" value="BORRAR"/> -->
			        </div>
				</div>
				
				

				
				
			</form>
		</div>
        
			

	
</div>

<div class="cargandobusqueda"  style="display:none;" >
    <p align="center"><img src="images/busca_files/europcar.gif" alt="" height="58" width="186">
  	<img src="images/busca_files/goldcarbw.gif" alt="" height="58" width="136">
  	<img src="images/busca_files/hertzbw.gif" alt="" height="58" width="148">
  	</p>
	<p align="center"><img src="images/busca_files/rolecar.gif" alt="" height="90" width="300"></p>
	<h2 align="center"><fmt:message key="busqueda.buscando"/></h2>
	<p align="center"><img src="images/busca_files/avisbw.gif" alt="" height="58" width="151"> <img src="images/busca_files/sixtbw.gif" alt="" height="58" width="110"> <img src="images/busca_files/alamobw.gif" alt="" height="58" width="126"></p>
</div>

<!-- slideshow -->
<div class="slider-revolution amp-space-down-50">
	<div id="slider-revolution">
		<ul>
			<li data-transition="cube">
				<img src="images/audio.jpg" alt="">
				<div class="tp-caption title skewfromrightshort tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="80"
					data-y="center"
					data-speed="500"
					data-start="500"
					data-easing="Power3.easeInOut"
					data-splitin="chars"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">Encontramos para ti el mejor precio
				</div>
				<div class="tp-caption sft tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-215"
					data-y="center" 
					data-voffset="-96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3000"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
					<!--<i class="fa fa-clock-o"></i>-->
				</div>
				<div class="tp-caption sfl tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-300"
					data-y="center" 
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3300"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-215"
					data-y="center" 
					data-voffset="96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3600"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="265"
					data-y="center" 
					data-voffset="91"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="4000"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;"><!--<a href="#" class="button-my">Purchase Now</a>-->
				</div>
			</li>
			<li data-transition="cube">
				<img src="images/fiesta.jpg" alt="">
				<div class="tp-caption title skewfromrightshort tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="80"
					data-y="center"
					data-speed="500"
					data-start="500"
					data-easing="Power3.easeInOut"
					data-splitin="chars"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">Sin intermediarios. Grandes descuentos
				</div>
				<div class="tp-caption sft tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-215"
					data-y="center"
					data-voffset="-96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3000"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfl tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-300"
					data-y="center"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3300"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				<div class="tp-caption sfb tp-resizeme rs-parallaxlevel-0"
					data-x="center"
					data-hoffset="-215"
					data-y="center"
					data-voffset="96"
					data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
					data-speed="500"
					data-start="3600"
					data-easing="Power3.easeInOut"
					data-splitin="none"
					data-splitout="none"
					data-elementdelay="0.1"
					data-endelementdelay="0.1"
					data-linktoslide="next"
					style="z-index: 1; max-width: auto; max-height: auto; white-space: nowrap;">
				</div>
				
			</li>
			s
		</ul>
	</div>
</div>
<!--/ slideshow -->
<div class="amp-text-information">
	<div class="amp-text-information-content">
		<div class="amp-text-information-head">Rolecar se adapta a ti<i></i></div>
		<div>
			<h2>Rolecar te ofrece la mejor tarifa! ¡Sin intermediarios!</h2>
			<p>Consigue descuentos de hasta un 70% y reservalo directamente. Negociamos con las mejores compañías de alquiler para ofrecerte el mejor precio.</p>
			<a href="leerMas.html" class="amp-text-information-button">Leer Más</a>
			
		</div>
	</div>
</div>

<!-- 	esta ul vale pa algo? -->
<!-- 	<div class="wraper"> -->
<!-- 		<div class="features_block"> -->
<!-- 			<ul> </ul> -->
<!-- 		</div> -->
<!-- 	</div> -->

<!-- <div id="msgCargando" style="display:none;"> -->
			
<!-- 			<div id="cargando"  style="width:62px;height:77px;"> -->
<!-- 				<div id='floatingBarsG'> -->
<!-- 					<div class='blockG' id='rotateG_01'></div> -->
<!-- 					<div class='blockG' id='rotateG_02'></div> -->
<!-- 					<div class='blockG' id='rotateG_03'></div> -->
<!-- 					<div class='blockG' id='rotateG_04'></div> -->
<!-- 					<div class='blockG' id='rotateG_05'></div> -->
<!-- 					<div class='blockG' id='rotateG_06'></div> -->
<!-- 					<div class='blockG' id='rotateG_07'></div> -->
<!-- 					<div class='blockG' id='rotateG_08'></div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- </div> -->


<%@ include file="footer.jsp" %>

<!-- Aqui añadir el footer -->
