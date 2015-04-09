<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%request.setCharacterEncoding("UTF-8");%>
<fmt:requestEncoding value="UTF-8" />
<%-- <fmt:setLocale value="${pageContext.request.locale.language}" scope="page" /> --%>
<fmt:setLocale value="${cookie.idioma.value}" scope="page" />
<%-- <p>AAA:: ${cookie.idioma.value} -- ${param.idioma}</p> --%>
<c:choose>
	<c:when test="${param.idioma eq null and cookie.idioma.value ne null}">
		<fmt:setLocale value="${cookie.idioma.value}" scope="page"/>
		<c:set var="local" value="${cookie.idioma.value}" scope="page"/>
	</c:when>
	<c:when test="${param.idioma!=null}">
		<%--<p>Idioma ${param.idioma} -- ${pageContext.request.locale.language}</p> --%>
    	<fmt:setLocale value="${param.idioma}" scope="page"/>
      	<c:set var="local" value="${param.idioma}" scope="page"/>
	</c:when>
	
</c:choose>
<fmt:setBundle basename="com.rolecar.bundle.storefrontMsj" />