<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<%
//Redir europcar
      String ruta = (String) request.getAttribute("ruta");
%>

<script>
 location.href='<%=ruta%>';
</script>