<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>  
<body>
<fmt:setLocale value="${pageContext.request.locale.language}" scope="session" />
<c:if test="${param.locale!=null}">
<p>Entrando</p>
      <fmt:setLocale value="en" />
   </c:if>

    <fmt:setBundle basename="com.rolecar.bundle.storefrontMsj" />
<p>AAAA::${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}----<c:out value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}"/></p>
<p>BBB:::: ${pageContext.request.locale.language}</p>
<p>El saludo en el idioma del navegador : <fmt:message key="saludo"/></p>
<c:set var="dk" value="Joooooolaaaaa"/>
<h1>${dk}-${fn:length(dk)}</h1> 
</body>
</html>