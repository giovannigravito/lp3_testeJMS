<%-- 
    Document   : index
    Created on : 22/09/2015, 08:24:55
    Author     : 31427782
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JMS teste</title>
    </head>
    <body>
        <h1>JMS</h1>
        <form action="Controller" method="POST">
            <p><textarea name ="mensagem"></textarea></p>
            <p><input type="radio" name="tipoJMS" value="sinc" checked/>Sincrono</p>
            <p><input type="radio" name="tipoJMS" value="assinc"/>Assincrono</p>
            <p><input type="submit"  value="Enviar"/></p>
        </form>
        <hr>
        <c:forEach items="${listaMSG}" var="mensagem">
            <p>
                <c:out value="${mensagem}"></c:out>
            </p>
        </c:forEach>
    </body>
</html>
