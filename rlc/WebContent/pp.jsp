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
<link rel="stylesheet" href="stylesheets/busqueda.css" type="text/css" media="screen">
<div class="wrape homeone">
    <div id="formularioBuscador" class="oneByOne_item"  >
			<form id="formBuscador" action="servletRolecar?accion=buscar" method="post">
			 	<div id="tvhiculo" >
					<input type="radio" id="tvhiculo1" name="tvhiculo" checked="checked" value="CR"><label for="tvhiculo1"><fmt:message key="busqueda.coches"/></label>
					<input type="radio" id="tvhiculo2" name="tvhiculo" value="TR" ><label for="tvhiculo2"><fmt:message key="busqueda.furgonetas"/></label>
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
					<div class="campoleft">
	 
						  <span id="fechainicial" class="input-group"><i class="fa fa-calendar fa-fw"></i></span>
				    </div>
				    <div class="campoleft">
						  <input id="fini"  name="fini" class="control-fecha" type="text"  readonly >
					</div>
					<div class="campoleft">
					   		
					   	<span id="horainicial" class="input-group"><i class="fa fa-clock-o fa-fw"></i></span>
					</div>
					<div class="campoleft sinborde" >		
						<select id="hinis" class="seleccion horas" name="hinis" class="control-fecha" style="border:none;height:200px;" >
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
					
					<div class="campoleft">
					        <span id="fechafinal" class="input-group"><i class="fa fa-calendar fa-fw"></i></span>
					</div>
					<div class="campoleft">
							<input id="ffin" name="ffin" type="text"  class="control-fecha" readonly />
					</div>
					<div class="campoleft">
					    <span id="horafinal" class="input-group"><i class="fa fa-clock-o fa-fw"></i></span>
					</div>
					<div class="campoleft sinborde">
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
					<div class="campoleft ajustar">
						<input type="button" class="boton" id="buscar" name="buscar" value="<fmt:message key='busqueda.buscar'/>"/>
<!-- 						<input type="reset" class="boton" id="borrar" name="borrar" value="BORRAR"/> -->
			        </div>
				</div>
				
				
				<div id="tsucursal" style="">
					<input type="radio" id="tsucursal1" name="tsucursal" checked="checked" value="tsucursal1"><label for="tsucursal1"><fmt:message key="busqueda.misma.oficina"/></label>
					<input type="radio" id="tsucursal2" name="tsucursal" value="tsucursal2"><label for="tsucursal2"><fmt:message key="busqueda.distinta.oficina"/></label>
					
				</div>
				
			</form>
				
		</div>
        
			

	
</div>




