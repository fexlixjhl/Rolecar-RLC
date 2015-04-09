<!-- Aqui añadir el header -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String mensaje = "";

if(request.getAttribute("mensaje")!=null)
{
	mensaje = (String) request.getAttribute("mensaje");
	if(mensaje.length()>0)
	{
 	     request.setAttribute("mens",mensaje);
	}
}



RequestDispatcher dispacher=request.getRequestDispatcher("servletRolecar?accion=reservar");
dispacher.forward(request, response);


%>
