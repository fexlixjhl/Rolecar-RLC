<%@page import="com.rolecar.utils.CarComparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.rolecar.beans.Car"%>
<%@page import="com.rolecar.utils.Formatea"%>
<%@page import="com.rolecar.data.dao.JdbcCarsDao"%>
<%@page import="java.util.Vector"%>
<%@page import="com.rolecar.data.dao.JdbcStationsDao"%>
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%--     pageEncoding="ISO-8859-1"%> --%>
<%@ include file="../language.jsp" %>
<%
String html="";
String valornuevo="";
String valornuevo2="";
boolean esmismaoficina = true;
//Fecha de entreega al cliente
String checkoutdate = request.getParameter("checkoutdate");

String[] fechaIni=checkoutdate.split("/");
checkoutdate=fechaIni[2].concat(fechaIni[1]).concat(fechaIni[0]);


String checkouttime = request.getParameter("checkouttime");
checkouttime =checkouttime.replace(":", "");


//Fecha de devolución
String checkindate = request.getParameter("checkindate");

String[] fechaFin=checkindate.split("/");
checkindate=fechaFin[2].concat(fechaFin[1]).concat(fechaFin[0]);

String checkintime = request.getParameter("checkintime");
checkintime =checkintime.replace(":", "");


String carType = request.getParameter("carType");

String codstationori = request.getParameter("codstationori");//codigoestacionorigen

String codstationdes = request.getParameter("codstationdes");

if(!codstationori.equalsIgnoreCase(codstationdes))
{
	esmismaoficina=false;
}

String provincia = request.getParameter("provincia");

if((provincia==null || provincia.equalsIgnoreCase("")) && request.getAttribute("provincia")!=null)
{
	provincia = (String)request.getAttribute("provincia");
	
}

//Compruebo que existe la provincia en memoria, para no tener que recargar otras localidades 
String provinciaantigua = (String)request.getSession(false).getAttribute(provincia+codstationori+codstationdes+checkoutdate+checkouttime+checkindate+checkintime+carType);

if(provinciaantigua!=null && !provinciaantigua.equalsIgnoreCase(""))
{
	 html = provinciaantigua;//(String) request.getSession().getAttribute(provincia);	
}
else
{
	Vector<String> listcod = JdbcStationsDao.getClosestation(codstationori, provincia);
	
	Car car;
	Vector<Car> vcars = new Vector<Car>();
	//String precio ="";%>
	<c:set var="otro"><fmt:message key='list.otras.localidades'/></c:set>
	<%html="<h2>"+pageContext.getAttribute("otro")+"</h2>";
	String codigoestacionin="";//codigo de estación de entrega
	
	
	
	for(String cod:listcod)
	{
		String[] coddescr=cod.split("::");
		
		if(!esmismaoficina)
		{
		   codigoestacionin=codstationdes;
		}
		else
		{
		   codigoestacionin=coddescr[0];
		}
		
		car=JdbcCarsDao.recogeprimerVehiculolocalidad(codigoestacionin, checkindate, checkintime, coddescr[0], checkoutdate, checkouttime, carType,1,coddescr[2],coddescr[3],coddescr[4]);
		
		if(car.isHaytarifas())
		{	
				
			vcars.add(car);
			
		}
		
	}
	
	Collections.sort(vcars,new CarComparator(1));
	for(Car c:vcars)
	{
		
		valornuevo=c.getStationcheckout().getCodstation().concat("::").concat(""+c.getStationcheckout().getIdprovincia()).concat("::").concat(c.getStationcheckout().getCodCountry());
		valornuevo2=c.getStationcheckout().getDescr().concat(" , ").concat(c.getStationcheckout().getDescrCountry());
		valornuevo2=valornuevo2.replace("\"", "'");
		html=html+"<div id=\""+valornuevo+"\"  class=\"buscarloc\" estacion=\""+valornuevo2+"\"  ><p>"+c.getStationcheckout().getDescr()+": <strong style=\"float:right;\">"+Formatea.Importe2Decimales(c.getQuote().getTotalRateEstimateInBookingCurrency(),2)+" &#8364;</strong></p>";
		
		html+="</div>";//"<input type=\"hidden\" value=\"".concat(valornuevo).concat("\" ></div>");
		
		
	}
	request.getSession(false).setAttribute(provincia+codstationori+codstationdes+checkoutdate+checkouttime+checkindate+checkintime+carType,html);
}	


out.print(html);
//out.close();

%>