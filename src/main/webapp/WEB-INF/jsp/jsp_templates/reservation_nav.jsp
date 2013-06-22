<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav id="reservation_nav">
    <nav class="left">
        <ul>
            <li><a href='<c:out value="${previous}" />' >&#9668;</a></li>
            <li><a href='<c:out value="${next}" />' >&#9658;</a></li>
        </ul>
        <span><c:out value="${weekDescription}" /></span>
    </nav>
    <nav class="right">
        <span>Mostrar como</span>
        <ul>
            <li><a href="#" class="button reservas_btn">semana</a></li>
            <li><a href="#" class="button reservas_btn">mes</a></li>
        </ul>
    </nav>
</nav>
