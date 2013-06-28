<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<nav id="reservation_nav">
    <nav class="left">
        <ul>
            <li><input type="hidden" id="calendarIcon" /></li>
            <li><a href='<c:url value="${previous}" />' >&#9668;</a></li>
            <li><a href='<c:url value="${next}" />' >&#9658;</a></li>
        </ul>
        <span><c:out value="${weekDescription}" /></span>
    </nav>
    <nav class="right">
        <span><s:message code="reservation.nav.as"/></span>

        <ul>
            <li><a href="<c:url value="/reservations/week/" />" class="button reservas_btn">
                    <s:message code="reservation.nav.week" /></a></li>
            <li><a href="<c:url value="/reservations/month/" />" class="button reservas_btn">
                <s:message code="reservation.nav.month" /></a></li>
        </ul>
    </nav>
</nav>
