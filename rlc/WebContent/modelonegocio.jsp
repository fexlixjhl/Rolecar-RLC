<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="language.jsp" %>

<!-- Aqui añadir el header -->
<%@page import="com.rolecar.beans.Station"%>
<%@ include file="header.jsp" %>
<div class="content_block">
 <!-- top_title -->
 <div class="top_title"><div class="text_bar_shadow"></div>
  <div class="wraper">
   <h2><fmt:message key="mnegocio.titulo"/><span></span></h2>
  
  </div>
 <div class="text_bar_shadow2"></div></div>
 <!-- /top_title -->
 <div class="wraper">
  <!-- full width -->
  <div class="post-38 page type-page status-publish hentry full_width post">
   <h2 style="text-align: justify;"><fmt:message key="mnegocio.text1"/></h2>
<hr>
<p style="text-align: justify;"><fmt:message key="mnegocio.text2"/></p>
<p style="text-align: justify;"><a href="http://www.rolecar.com/Rolecar/images/compa.jpg"><img width="269" height="120" alt="compañías" src="http://www.rolecar.com/wp-content/uploads/2014/06/compa.jpg" class="alignleft wp-image-45 size-full"></a><fmt:message key="mnegocio.text3"/></p>
<p style="text-align: justify;"><fmt:message key="mnegocio.text4"/></p>
<p style="text-align: justify;"><fmt:message key="mnegocio.text5"/></p>
  </div>
 </div>
</div>
<!-- Aqui añadir el footer -->
<%@ include file="footer.jsp" %>