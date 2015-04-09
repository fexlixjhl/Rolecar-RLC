<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ include file="language.jsp" %>

<!-- Aqui añadir el header -->
<%@page import="com.rolecar.utils.ConversoresFecha"%>
<%@page import="com.rolecar.utils.CarComparator"%>
<%@page import="com.rolecar.beans.*"%>
<%@page import="com.rolecar.utils.Formatea"%>
<%@page import="java.util.Vector"%>
<%@ include file="header.jsp" %>
<script type="text/javascript">
$( document ).ready(function() {
	  $(":button").click(function() {
		  var id = $(this).attr("id");
		  if($(this).attr("id").indexOf("bReserva")>-1){
			  //alert("Reserva:: " + $(this).attr("code"));
			  var code = $(this).attr("code");
			  $("#reservaVehiculo"+code).submit();
		  }
	});
});

</script>
<%
	String cero="";
	String selec="";
	String origen ="";
	String codorigen="";
	String coddestino="";
	String destino = "";
	String fecharec ="";
	String fechaent = "";
	String horarec = "";
	String horaent = "";
	String provincia = "";
	String estacionorigen = "";
	String estaciondestino = "";
	String chequeadoonline = "";
	String fuentegrandeonline = "";
	String fuentegrandeoficina = "";
	String ocultoonline = "";
	String chequeadooficina = "";
	String ocultooficina = "";
	String paisrecogida="";
	String paisentrega="";
	
	//Parte del seguro
	String proteccion="";
	String robo="";
	String colision="";
	String accidentesmej="";
	String accidentes="";
	String mercancias="";
	String multas="";
	String adicional="";
		
	
	//Vectores
	Vector<Station> vstat =null;
	Vector<Car> vcar= null;
	Vector<Insurance> vseguro =null;
	Reservation reser=null;
	String cartype="CR";//valor por defecto
	Country pais=null;
	int tama=0;
	
	boolean esMismaSucursal = true;
	
	//Muestra el mensaje de que no hay vehiculos si no se encuentra ninguno
	String mensaje = "";
	boolean haycoches = false;

	if(request.getAttribute("mensaje")!=null)
	{
		mensaje = (String) request.getAttribute("mensaje");
		if(mensaje.length()>0)
		{
	 	     request.setAttribute("mens",mensaje);
		}
	}
	
	if(request.getAttribute("haycoches")!=null)
	{
		haycoches = ((Boolean)request.getAttribute("haycoches")).booleanValue();
	}
	
	vstat=(Vector<Station>)request.getSession(false).getAttribute("vStations");
	if(vstat!=null)
		tama=vstat.size();
	
	if(haycoches)
	{	
		//Parte de la busqueda dentro del listado
		
		
		
		//final de la parte de busqueda
		
		
		//Obtenemos el resultado (Listado de coches) de la busqueda por fecha y estación
		
		if(request.getAttribute("Vehiculos")!=null)
		{	
		vcar=(Vector<Car>)request.getAttribute("Vehiculos");
		request.getSession(false).removeAttribute("Vehiculossesion");
		request.getSession(false).setAttribute("Vehiculossesion", vcar);
		}
	}	
	if(request.getAttribute("Reservation")!=null)
	{
		reser=(Reservation)request.getAttribute("Reservation");
 		codorigen=reser.getCheckoutstationId();
 		coddestino=reser.getCheckinstationId();
		fecharec =  reser.getCheckoutdate();
		fechaent = reser.getCheckindate();
		horarec = reser.getCheckouttime();
		horarec = ConversoresFecha.formateahora(horarec);
		horaent = reser.getCheckintime();
		horaent = ConversoresFecha.formateahora(horaent);
		fecharec=ConversoresFecha.formateaFechaBarras(fecharec);
		fechaent=ConversoresFecha.formateaFechaBarras(fechaent);
		//request.setAttribute("Reservation", reser);
	}	
	if(request.getAttribute("Country")!=null)
	{
		pais=(Country)request.getAttribute("Country");
		//request.setAttribute("Country", pais);
	}
	
	if(request.getAttribute("CarType")!=null)
	{
		cartype=(String)request.getAttribute("CarType");
		//request.setAttribute("CarType",cartype);
	}
	if(request.getAttribute("sucursal")!=null)
	{
		esMismaSucursal = (Boolean)request.getAttribute("sucursal");
		//request.setAttribute("sucursal",esMismaSucursal);
	}
	
	if(request.getAttribute("oficina")!=null)
	{
		origen = (String)request.getAttribute("oficina");
		//TODO ISSUE A
		//request.setAttribute("oficina",origen);
	}
	
	if(request.getAttribute("oficinaorigen")!=null)
	{
		origen = (String)request.getAttribute("oficinaorigen");
		//TODO ISSUE B
	}
	
	if(request.getAttribute("oficinadestino")!=null)
	{
		destino = (String)request.getAttribute("oficinadestino");
		
	}
	if(request.getAttribute("provincia")!=null)
	{
		provincia = (String)request.getAttribute("provincia");
		
	}
	
	if(request.getAttribute("stationorigid")!=null)
	{
		estacionorigen = (String)request.getAttribute("stationorigid");
		
	}
	
	if(request.getAttribute("stationdestid")!=null)
	{
		estaciondestino = (String)request.getAttribute("stationdestid");
		
	}
	
	if(request.getAttribute("paisrecogida")!=null)
	{
		paisrecogida = (String)request.getAttribute("paisrecogida");
		
	}
	
	if(request.getAttribute("paisentrega")!=null)
	{
		paisentrega = (String)request.getAttribute("paisentrega");
		
	}
	
%>
<!-- <head> -->
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
    <link rel="stylesheet" href="stylesheets/detalle.css" type="text/css" media="screen">
    
    <script>
	
	    var estacionori = "";
	    var estaciondes = "";
	    var estacionoriid = "";
	    var estaciondesid = "";
	    var codori ="";
	    var coddes="";
	    var fecharec = "";
	    var fechaent = "";
	    var horarec = "";
	    var horaent = "";
	    var mismasucursal = <%=esMismaSucursal%>;
	    var crtype="";
	    var provincia="";
	    var haycoches=<%=haycoches%>;
	    var mensajeveh="<%=mensaje%>";
	    estacionori = "<%=origen%>";
		estaciondes = "<%=destino%>";
		estacionoriid = "<%=estacionorigen%>";
	    estaciondesid = "<%=estaciondestino%>";
		codori="<%=codorigen%>";
		coddes="<%=coddestino%>";
		fecharec = "<%=fecharec%>";
		fechaent = "<%=fechaent%>";  
		horarec ="<%=horarec%>";
		horaent = "<%=horaent%>";
		crtype="<%=cartype%>";
	    provincia="<%=provincia%>";
	    
       
        //Busqueda dentro del listado
        var tam=<%=tama%>;
        
		var arrstations = new Array(tam);
		var dato="";
		<%  int i;//Lo declaramos fuera del bucle, porque sino a veces da problemas
// 		    if(haycoches)
// 		    {	
			    String valoractual="";
				for(i=0;i<tama;i++)
				{
					Station stat= new Station();
					
					//stat.setCodCity(vstat.elementAt(i).getCodCity());
					stat=vstat.elementAt(i);
					valoractual=stat.getDescr().concat(" , ").concat(stat.getDescrCountry());//.concat(" ; ").concat(stat.getDescr());
					valoractual=valoractual.replace("\"", "'");
					%>
					arrstations[<%=i%>] = {"label":"<%=valoractual%>", "value":"<%=valoractual%>","id":"<%=stat.getCodstation()+"::"+stat.getIdprovincia()+"::"+stat.getCodCountry()%>"} ;	
		<%
				}
			//}
			%>
	
	</script>
	<script src="scripts/flistado.js"></script>
	
<!-- </head> -->
<!-- <body> -->
	<br/>
	<div class="cargandobusqueda"  style="display:none;">
<!-- 		    <div class="mensaje"><h1>Reidirigiendo...</h1></div> -->
<!-- 		    <div id="warningGradientOuterBarG"> -->
<!-- 			<div id="warningGradientFrontBarG" class="warningGradientAnimationG"> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			</div> -->
<!-- 			</div>  -->
				<p align="center"><img src="images/redirigiendo_files/redirigiendo.gif" alt="" height="296" width="1000"></p>

	</div>
<!-- 	<div class="busquedaotraslocalidades"  style="display:none;"> -->
<!-- 		    <div class="mensaje"><h1>Reidirigiendo...</h1></div> -->
<!-- 		    <div id="warningGradientOuterBarG"> -->
<!-- 			<div id="warningGradientFrontBarG" class="warningGradientAnimationG"> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			<div class="warningGradientBarLineG"> -->
<!-- 			</div> -->
<!-- 			</div> -->
<!-- 			</div>  -->


<!-- 	</div> -->
	<div class="wrape homeone">
	    <%if(haycoches){ %>
	    <div class="capacarrousel">
			    <div class="carrousel">
				    <% 
				    	if(vcar!=null && vcar.size()!=0)
								{
				    		        int coche=0;
				    		       
									for(Car ca: vcar)
									{
										coche++;
										%>
										   <div class="slide">
										     <div class="resumen" id="<%=coche%>">
										      <div><img  src="https://applications.europcar.com/resaweb/carvisuals/<%=ca.getCarCategoryCode()%>_<%=pais.getCodcountry()%>.jpg"></div>
										      <div><p><%=ca.getCarCategorySample()%></p></div>
										     </div>
										   </div>
										   
										<%
										
										
									}
								}
					%>
			    </div>
	    </div>
	    <div id="ordenapor" >
			<br/>
			<fmt:message key="list.order.text"/>
				<a id="precio" href="#" style="color:white;font-weight: bold;">&nbsp;<fmt:message key="list.order.pvp"/></a> |
				<a id="tam" href="#" style="color:white;font-weight: bold;">&nbsp;<fmt:message key="list.order.tamano"/></a> |
				<a id="emi" href="#" style="color:white;font-weight: bold;">&nbsp;<fmt:message key="list.order.co2"/>&nbsp;</a>
				<form id="fordenar" name="fordenar" method="post" 
				action="servletRolecar?accion=ordenar&provincia=<%=provincia%>&stationorigid=<%=estacionorigen%>&stationdestid=<%=estaciondestino %>&codStationIn=<%=reser.getCheckinstationId()%>&checkindate=<%=reser.getCheckindate()%>&checkintime=<%=reser.getCheckintime()%>&codStationOut=<%=reser.getCheckoutstationId()%>&checkoutdate=<%=reser.getCheckoutdate()%>&checkouttime=<%=reser.getCheckouttime()%>&carType=<%=cartype%>&Country=<%=pais.getCodcountry()%>&mismaoficina=<%=esMismaSucursal%>&origen=<%=origen%>&destino=<%=destino%>"></form>
			<br/>
		</div>
		<%} %>
		<div id="buscador"  style="display: block; overflow-y:auto;height: auto;min-width: 1200px;">
		   <div id="otros" class="content contentleft">
			   <h2><fmt:message key="list.otras.localidades"/></h2>
			
		   </div>
		   
		   <div class="contentlistado">
		        <%if(!haycoches){ %>
				<div id="mensajevehiculos" >
    	          <p id="mensajevehiculostexto"></p>
                </div>
                <%} %>
                <%if(haycoches){ %>
				<div class="Listado" >
				<%
					if(vcar!=null && vcar.size()!=0)
					{
						int cont=0;
						for(Car c: vcar)
						{
							String[] catName= c.getCarCategoryName().split(",");
							Reservation res=c.getReservation();
							Quote q= new Quote();
							q=c.getQuote();
							cont++;
							vseguro=c.getVinsurances();
							for(Insurance s:vseguro)
							{
								if(s.getCode().equalsIgnoreCase("SPAI"))
								{
									accidentesmej=s.getPrice();
								}
								else if (s.getCode().equalsIgnoreCase("SLDW"))
								{
									proteccion=s.getExcessWithPOM();
								}
								else if (s.getCode().equalsIgnoreCase("PAI"))
								{
									accidentes=s.getPrice();
								}
								else if (s.getCode().equalsIgnoreCase("GIT"))
								{
									mercancias=s.getPrice();
								}
								else if (s.getCode().equalsIgnoreCase("FAW"))
								{
									multas=s.getPrice();
								}
								else if (s.getCode().equalsIgnoreCase("THW"))
								{
									robo=s.getExcessWithPOM();
								}
								else if (s.getCode().equalsIgnoreCase("CDW"))
								{
									colision=s.getExcessWithPOM();
								}
								
								
							}
							%>
							  <form id="reservaVehiculo<%=cont%>" name="reservaVehiculo<%=cont%>" action="servletRolecar?accion=contratar" method="post">
								<div class="list-detail" >
									<input id="dateco" name="dateco" type="hidden" value="<%=res.getCheckoutdate()%>"/>
									<input id="dateci" name="dateci" type="hidden" value="<%=res.getCheckindate()%>"/>
									<input id="timeco" name="timeco" type="hidden" value="<%=res.getCheckouttime()%>"/>
									<input id="timeci" name="timeci" type="hidden" value="<%=res.getCheckintime()%>"/>
									<input id="stationco" name="stationco" type="hidden" value="<%=res.getCheckoutstationId()%>"/> 
									<input id="stationci" name="stationci" type="hidden" value="<%=res.getCheckinstationId()%>"/> 
									<input id="CTRCT" name="CTRCT" type="hidden" value="<%=c.getContractId()%>"/> 
									<input id="PROMO" name="PROMO" type="hidden" value=""/> 
									<input id="IATA" name="IATA" type="hidden" value="01522830"/> 
									<input id="PERD" name="PERD" type="hidden" value=""/> 
									<input id="CARLIST" name="CARLIST" type="hidden" value=""/> 
									<input id="ACRISS" name="ACRISS" type="hidden" value="<%=c.getCarCategoryCode()%>" /> 
									<input id="ERENTAL" name="ERENTAL" type="hidden" value="" /> 
									<input id="RENTALCNTRY" name="RENTALCNTRY" type="hidden" value="<%=res.getCountryOfReservation() %>" />
									<input id="PAISRECOGIDA" name="PAISRECOGIDA" type="hidden" value="<%=paisrecogida %>" />
									<input id="PAISENTREGA" name="PAISENTREGA" type="hidden" value="<%=paisentrega %>" />
									<input id="CNTRY" name="CNTRY" type="hidden" value="<%=pais.getCodcountry()%>" />
									<input id="KM" name="KM" type="hidden" value="<%=c.getQuote().getIncludedKm()%>" />
									<div class="vehicle-details" id="coche<%=cont%>">
										<div class="padding clear">
										<%--TEST --<%=c.getContractId()%>--<%=c.getCarCategoryCode()%> --%>
											<h2><%=c.getCarCategorySample()%></h2>
											<p class="similar">&nbsp;<fmt:message key="list.similar"/></p>
											
											<div id="iconoscar<%=c.getCarCategoryCode()%>" >
											<%
											  if(c.getCarCategoryType()!=null && c.getCarCategoryType().equalsIgnoreCase("Eco-responsable"))
											  {
											%>
											   <div class="capaiconosfeature">
											    <img src="images/eco.png" alt="asientos" eco="<%=c.getCarCategoryCO2Quantity() %>" class="iconosfeature">
											   </div>
											<%
											  }
											%>
											<%
											  if(c.getCarCategoryBaggageQuantity()!=null && !c.getCarCategoryBaggageQuantity().equalsIgnoreCase(""))
											  {
											%>
											  <div class="capaiconosfeature">
											   <img src="images/maleta.png" alt="asientos" maleta="<%=c.getCarCategoryBaggageQuantity() %>" class="iconosfeature">
											   <%=c.getCarCategoryBaggageQuantity()%>
											  </div>
											<%
											  }
											%>
											<%
											  if(c.getCarCategorySeats()!=null && !c.getCarCategorySeats().equalsIgnoreCase(""))
											  {
											%>
											  <div class="capaiconosfeature">
											   <img src="images/asiento.png" alt="asientos" asiento="<%=c.getCarCategorySeats() %>" class="iconosfeature">
											   <%=c.getCarCategorySeats()%>
											  </div>
											<%}
											%>
											</div>
											
										</div>
										<div class="padding">
										    <div class="fixed-left clear">
										        <div class="features-container">
											         
										             <div class="column fluid">
										                 <h3><b><fmt:message key="list.car.title"/>&nbsp;</b><span class="title-detail"><%=catName[0]%></span></h3>
										                 <div class="clear" id="carac<%=c.getCarCategoryCode()%>">
										                 	<div class="col1">
											                 	<ul class="bullet">
											                 	   <% if(c.getCarCategoryAirCond().equals("Y"))
																		{%>
																		<li><b>&nbsp;&nbsp;<fmt:message key="list.car.aa"/></b></li>
																		<%}%> 												
																		<li><b>&nbsp;&nbsp;<fmt:message key="list.car.puertas"/>&nbsp;</b><%=c.getCarCategoryDoors() %></li>
																		<% if(c.getCarCategoryAutomatic().equalsIgnoreCase("Y"))
																		{%>
																			<li><b>&nbsp;&nbsp;<fmt:message key="list.car.autom"/></b></li>	  													
																		<%}
																		else
																		{%>
																			<li><b>&nbsp;&nbsp;<fmt:message key="list.car.manual"/></b></li>
																		<%}
																		%>	
											                 	</ul>
										                 	</div>
										                 	<div class="col2">
										                 	    <ul class="bullet">
										                 	    <li><b>&nbsp;&nbsp;<fmt:message key="list.car.emision"/>&nbsp;</b><%=c.getCarCategoryCO2Quantity()%>g/Km</li>
																	<%
																		if(res!=null)
																		{%>
																			<li><b>&nbsp;&nbsp;<fmt:message key="list.car.edadm"/>&nbsp;</b><%=res.getMinAgeForCategory()%></li>
																		<%}
																		if(q!=null)
																		{ 
																			if(q.getIncludedKm().contains("UNLIMITED")){%>
																				<li><b>&nbsp;&nbsp;<fmt:message key="list.car.kms"/>&nbsp;<fmt:message key="list.car.kms.ilimitado"/></li> 
																			<%}
																			else{%>
																				<li><b>&nbsp;&nbsp;<fmt:message key="list.car.kms"/>&nbsp;</b><%=q.getIncludedKm()%> Km</li>
																			<%}
																		}
																		else
																		{%>
																			<li><b>&nbsp;&nbsp;<fmt:message key="list.car.kms"/>&nbsp;</b>-- Km</li>  
																		<%}
																	%>	  	
											                 	</ul>
										                 	</div>
										                 </div>
										             </div>
										        </div>
										        <div class="column fixed thumb">
											             <img  id="imagenoriginal" src="https://applications.europcar.com/resaweb/carvisuals/<%=c.getCarCategoryCode()%>_<%=pais.getCodcountry()%>.jpg">
	<!-- 										             <div class="co2-info popin-link" style="position:static; display:inline-block; overflow:auto; float:left; clear:both; border:0px; margin:0px; padding:0px 2px;"></div> -->
	<!-- 													 <div class="popin-link" style="position:static; display:inline-block; overflow:auto; float:left; clear:none; border:0px; margin:0px; padding:0px 2px; font-style:normal; font-weight:bold; text-decoration:underline; color:black;">Más información</div> -->
											    </div>
										    </div>
										    <div class="more clear">
										         <img src="images/logoeuropcar.png" alt="Logo"  class="logoempresa">
										        <a class="rate" href=""  km="<%=c.getQuote().getIncludedKm()%>" titulo="<%=c.getCarCategorySample()%>" codigo="<%=c.getCarCategoryCode()%>" categoria="<%=catName[0]%>" imagen="<%=c.getCarCategoryCode()%>_<%=pais.getCodcountry()%>.jpeg"
										         proteccion="<%=proteccion%>" robo="<%=robo %>" colision="<%=colision %>" accidentesmej="<%=accidentesmej %>" accidentes="<%=accidentes %>" mercancias="<%=mercancias %>" multas="<%=multas %>" adicional="<%=adicional%>">
										         <fmt:message key="list.car.condiciones"/></a>
										    </div>
										</div>
									</div>
									

										<div class="precio" >
										<%  
										    float precioonline=0f;
											float preciooficina=0f;
											chequeadooficina="";
											chequeadoonline="";
											fuentegrandeoficina="";
											fuentegrandeonline="";
											
										    if(Formatea.isNumeric(q.getTotalRateEstimateInBookingCurrency()))
											{
												precioonline=Float.parseFloat(q.getTotalRateEstimateInBookingCurrency());
											}
										    
										    if(Formatea.isNumeric(q.getTotalRateEstimateInRentingCurrency()))
											{
												preciooficina=Float.parseFloat(q.getTotalRateEstimateInRentingCurrency());
											}
										    
										    if(precioonline>preciooficina)
										    {
										    	
										    	if(preciooficina>0)
										    	{
										    		fuentegrandeoficina="style=\"font-size:1.7em\"";
										    		chequeadooficina = "checked=\"checked\"";
										    	}
										    	else
										    	{
										    		fuentegrandeonline="style=\"font-size:1.7em\"";
											    	chequeadoonline = "checked=\"checked\"";
										    	}
										    		
										    }
										    else if(preciooficina>precioonline )
										    {
										    	if(precioonline>0)
										    	{
											    	fuentegrandeonline="style=\"font-size:1.7em\"";
											    	chequeadoonline = "checked=\"checked\"";
										    	}
										    	else
										    	{
										    		fuentegrandeoficina="style=\"font-size:1.7em\"";
										    		chequeadooficina = "checked=\"checked\"";
										    	}
										    }
										    
										
											if(q.getTotalRateEstimateInBookingCurrency()!=null && !q.getTotalRateEstimateInBookingCurrency().equalsIgnoreCase(""))
											{ 
										         
												  //chequeadoonline = "checked=\"checked\"";
										          //isprecioonlineoculto=false;
										          ocultoonline = "";										
											}
											else
											{
												 chequeadoonline = "";
												 //isprecioonlineoculto=true;
												 ocultoonline = "style=\"visibility:hidden;\"";
												
											}
										
										%>
										    <div class="option clear" <%=ocultoonline %> >
															<input id="pp" type="radio" value="Y" name="reservac" <%=chequeadoonline %> >
															<span>
																<span class="pay"><fmt:message key="list.car.pago.online"/></span>
																<span class="no-wrap">
																    <%String moneda=(q.getCurrency().equalsIgnoreCase("EUR"))?q.getCurrency():"EUR"; %>
																    <span class="pago" <%=fuentegrandeonline %>><%=moneda%> <%=Formatea.Importe2Decimales(q.getTotalRateEstimateInBookingCurrency(),2)%></span>
																    <span class="small"><fmt:message key="list.car.pago.total"/></span>
																</span>
															</span>
											</div>
										
										<%  if(q.getTotalRateEstimateInRentingCurrency()!=null && !q.getTotalRateEstimateInRentingCurrency().equalsIgnoreCase(""))
											{ 
// 												if(isprecioonlineoculto)
// 												{	
// 												   chequeadooficina = "checked=\"checked\"";
// 												}
											      ocultooficina = "";										
											}
											else
											{
												 chequeadooficina = "";
												 ocultooficina = "style=\"visibility:hidden;\"";
												
											}
										
										%>
											<div class="option clear" <%=ocultooficina %> >
															<input id="pp" type="radio" value="N" name="reservac" <%=chequeadooficina %> >
															<span>
																<span class="pay"><fmt:message key="list.car.pago.oficina"/></span>
																<span class="no-wrap">
																    <span class="pago" <%=fuentegrandeoficina %>><%=moneda%> <%=Formatea.Importe2Decimales(q.getTotalRateEstimateInRentingCurrency(),2)%></span>
																    <span class="small"><fmt:message key="list.car.pago.total"/></span>
																</span>
															</span>
											</div>
										
<%-- 											<p><b>Pago online: </b><%=q.getCurrency()%> <%=q.getTotalRateEstimateInBookingCurrency()%> </p> --%>
<%-- 											<p><b>Pago en Oficina: </b> <%=q.getCurrency()%> <%=q.getTotalRateEstimateInRentingCurrency()%> </p> --%>
                                            <div class="botones">
                                            	<input id="bReserva<%=cont%>" name="bReserva<%=cont%>" code="<%=cont%>" class="boton reserva" type="button" value="<fmt:message key="list.car.reserva"/>" />
<%--                                                 <button id="bReserva" code="<%=cont%>" class="boton reserva" type="submit"> --%>
<%--                                                 <fmt:message key="list.car.reserva"/> --%>
<!--                                                 </button> -->
                                            </div>
											
										</div>
									<!--Fin de características -->
								</div><!--Fin de slide -->
							</form>	<!-- Fin de formulario -->
						<%}
					}
					%>
					
				</div><!--Fin de slider1 -->
			<%} %>
				
			</div>
			<div id="busquedaestaciones" class="content contentleft">
			   <h2><fmt:message key="list.busca.title"/></h2>
			   <form id="formBuscador" action="servletRolecar?accion=buscar" method="post">
			 	<div id="tvhiculo" >
			 	    <input type="radio" id="tvhiculo1" name="tvhiculo" value="CR"><label for="tvhiculo1"><fmt:message key="list.busca.car"/></label>
					<input type="radio" id="tvhiculo2" name="tvhiculo" value="TR" ><label for="tvhiculo2"><fmt:message key="list.busca.van"/></label>
				</div>
				
				<div id="Campos">
				    <div class="campoleft">
				        
				        <p><fmt:message key="list.busca.of.recogida"/></p>
						<input id="station"  name="station" class="cajas" type="text" placeholder="p.ej., Madrid" />
						<input type="hidden" id="stationid" name="stationid" />
						
					</div>
					<div class="campoleft">
					    <input id="stationOrig"  name="stationOrig" class="cajas" type="text"  style="display: none;" placeholder="Dónde" onclick="borrarContenidoStation('stationOrig')"/>
						<input type="hidden" id="stationOrigid" name="stationOrigid" />
					</div>
					<div class="campoleft">
					    <p id="ofientrega" style="display:none;"><fmt:message key="list.busca.of.entrega"/></p>
						<input id="stationDest"  name="stationDest" class="cajas" type="text"  style="display: none;" placeholder="A" onclick="borrarContenidoStation('stationDest')"/>
						<input type="hidden" id="stationDestid" name="stationDestid" />
					</div>
					<div class="campoleft">
                         
						  <span class="input-group"><i class="fa fa-calendar fa-fw"></i></span>
						  <span><fmt:message key="list.busca.f.recogida"/></span>
						  
				    </div>
				    <div class="campoleft">
						  <input id="fini"  name="fini" class="cajas"  type="text"   readonly >
					</div>
					<div class="campoleft">
					   		
					   	<span class="input-group"><i class="fa fa-clock-o fa-fw"></i></span>
					   	<span ><fmt:message key="list.busca.h.recogida"/></span>
					</div>
					<div class="campoleft">		
						<select id="hinis" class="seleccion horas" name="hinis">
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
					        <span class="input-group"><i class="fa fa-calendar fa-fw"></i></span>
					        <span><fmt:message key="list.busca.f.entrega"/></span>
					</div>
					<div class="campoleft">
							<input id="ffin" name="ffin" type="text"  class="cajas" readonly />
					</div>
					<div class="campoleft">
					    <span class="input-group"><i class="fa fa-clock-o fa-fw"></i></span>
					    <span><fmt:message key="list.busca.h.entrega"/></span>
					</div>
					<div class="campoleft">
						<select id="hfins" class="seleccion" name="hfins">
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
					
					
				</div>
				
				
				<div id="tsucursal" >
				    <p><fmt:message key="list.busca.sucursal"/></p>
				    <input type="radio" id="tsucursal1" name="tsucursal" value="tsucursal1"><label for="tsucursal1"><fmt:message key="list.busca.sucursal.eq"/></label>
					<input type="radio" id="tsucursal2" name="tsucursal" value="tsucursal2"><label for="tsucursal2"><fmt:message key="list.busca.sucursal.ne"/></label>
					
				</div>
				
				<div class="campoleft">
						<input type="button" class="boton" id="buscar" name="buscar" value="<fmt:message key='list.busca.buscar'/>"/>
<!-- 						<input type="reset" class="boton" id="borrar" name="borrar" value="BORRAR"/> -->
			    </div>
				
				
			</form>
			
			</div>
			
		</div><!-- Fin buscador -->
	</div><!-- Fin wrap homeone -->
	<div id="infofinal" class="content_block">
		<div class="text_bar">
			<div class="wraper">
				<h2>
					<fmt:message key="list.msj.tarifa"/>
					<span><fmt:message key="list.msj.descuento"/></span>
				</h2>
				<div class="widget">
					<h3><fmt:message key="list.msj.adapta"/></h3>
						<p align="justify"><fmt:message key="list.msj.negocio"/>
							<a href="modelonegocio.jsp"><fmt:message key="comun.leer"/></a>
						</p>
				</div>
			</div>
		</div>
		<div class="wraper">
			<div class="features_block">
				<ul> </ul>
			</div>
		</div>
	</div>
	
	<div id="msgCargando" style="display:none;">
			
			<div id="cargando"  style="width:62px;height:77px;">
				<div id='floatingBarsG'>
					<div class='blockG' id='rotateG_01'></div>
					<div class='blockG' id='rotateG_02'></div>
					<div class='blockG' id='rotateG_03'></div>
					<div class='blockG' id='rotateG_04'></div>
					<div class='blockG' id='rotateG_05'></div>
					<div class='blockG' id='rotateG_06'></div>
					<div class='blockG' id='rotateG_07'></div>
					<div class='blockG' id='rotateG_08'></div>
				</div>
			</div>
    </div>
    
    <div id="dialog-condiciones" title="">
    
       <div class="container" >
          <div class="leftcond">
              <div class="vehicle-details clear detail-dialog" style="min-width:400px;" >
                  <font class="cat">
                  <h3><fmt:message key="list.car.title"/><span id="typeV">rata</span></h3>
                  </font>
                  <div class="imagencond">
                     <img id="imagencondicion" src="">
                  </div>
                  <div class="featurescond">
                   
                  </div>
                  <div>
                      <div class="clear">
                         <h4 class="dialog-title"><fmt:message key="list.cond.title"/></h4>
                      </div>
                  
                      <div class="categoriacond"></div>
                  </div>
                  <span id="text_ilim" style="display: none;"><fmt:message key="list.car.kms.ilimitado"/></span>
                  <span id="text_lim" style="display: none;"><fmt:message key="list.car.kms.limitado"/></span>
               </div>     
          </div>
          <div class="rightcond">
          		<div class="mvi-included clear">
					<h4 class="popin_chapter_title"><fmt:message key="list.cond.incluye"/></h4>
				</div>
				<div class="clear">
					<ul class="bullet bullt">
					
						<li id="textokm" ></li>
						<li><fmt:message key="list.cond.in.aero"/></li>
						<li><fmt:message key="list.cond.in.tren"/></li>
						<li><fmt:message key="list.cond.in.iva"/></li>
						<li id="proteccionli"><fmt:message key="list.cond.in.cobertura"/>&nbsp;<span id="proteccion"></span> EUR</li>
						<li id="roboli"><fmt:message key="list.cond.in.robo"/>&nbsp;<span id="robo"></span> EUR</li>
						<li id="colisionli"><fmt:message key="list.cond.in.exen"/>&nbsp;<span id="colision"></span> EUR</li>
						
<!-- 						<li>Exención parcial de robo (THW) (Cargo de exención de responsabilidad  &#8364; 667.34). impuestos 21.0 % exclusivo)</li> -->
<!-- 						<li>Suplemento de Aeropuerto</li> -->
<!-- 						<li>Exención parcial por colisión (Cargo de exención de responsabilidad &#8364; 667.34). impuestos 21.0 % exclusivo)</li> -->
					
					</ul>
				</div>
				
				<div class="mvi-excluded clear">
					<h4 class="popin_chapter_title"><fmt:message key="list.cond.nincluye"/></h4>
				</div>				
				<div class="clear">
					<ul class="bullet bullt">
					    <li id="accidentesmejli"><fmt:message key="list.cond.nin.mejora"/>&nbsp;<span id="accidentesmej"></span>&nbsp;<fmt:message key="comun.eur.alq"/></li>
					    <li id="accidentesli"><fmt:message key="list.cond.nin.personal"/>&nbsp;<span id="accidentenormal"></span>&nbsp;<fmt:message key="comun.eur.alq"/></li>
					    <li id="mercanciasli"><fmt:message key="list.cond.nin.mercancia"/>&nbsp;<span id="mercancias"></span>&nbsp;<fmt:message key="comun.eur.alq"/></li>
					    <li id="multasli"><fmt:message key="list.cond.nin.multa"/>&nbsp;<span id="multas"></span>&nbsp;<fmt:message key="comun.eur.alq"/></li>
					    <li id="adicional"></li>
					    <li><fmt:message key="list.cond.nin.gas"/></li>
					    
<!-- 						<li>Todas las ventajas de GoZen más un seguro personal para el conductor y los ocupantes del vehículo &#8364; 29.87 por día<br></li><li>Exención de colisión y robo mejorada (SLDW) &#8364; 23.82 por día (Exención de responsabilidad reducida a &#8364; 0)<br></li><li>Translation is managed in the ETM. &#8364; 22.2 por día<br></li><li>Seguro Personal de Accidentes &#8364; 12.27 por día<br></li><li>Todas las ventajas de la cobertura básica y además incluye la exención del pago total de la franquicia en caso de robo y daño al vehículo y la exención de pago de los daños y/o pérdidas causados a las llantas y neumáticos del vehículo, incluyendo el de repuesto, cuando se hayan producido a causa de pinchazos o por golpes contra bordillos con ocasión de maniobras de aparcamiento &#8364; 28.66 por día<br> -->
<!-- 					</li> -->
					</ul>
				</div>
          
          </div>
       </div>
       
    </div>
    
   
    
<%@ include file="footer.jsp" %>