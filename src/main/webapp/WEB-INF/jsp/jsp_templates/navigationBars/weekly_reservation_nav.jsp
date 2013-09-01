<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<nav id="reservation_nav">
    <!--    <nav class="left">
            <span class="button reservas_btn">Añadir</span>
        </nav>-->
    <nav class="left">
        <ul>
            <li><a class="reservas_btn" 
                   href='<c:url value="/reservations/new" />'><s:message code="reservation.nav.create" /></a></li>
            <li><input type="hidden" id="calendarIcon" /></li>
            <li><a name="prev" href='<c:url value="${previous}" />' class="icon-caret-left icon"></a></li>
            <li><a name="next" href='<c:url value="${next}" />' class="icon-caret-right icon"></a></li>
            <li><span><c:out value="${weekDescription}" /></span></li>
        </ul>
    </nav>
    <nav class="right">
        <ul>
            <li><span><s:message code="reservation.nav.as"/></span></li>
            <li><a href="<c:url value="/reservations/week/" />" class="reservas_btn">
                    <s:message code="reservation.nav.week" /></a></li>
            <li><a name="monthlyView" href="<c:url value="/reservations/month/" />" class="reservas_btn">
                    <s:message code="reservation.nav.month" /></a></li>
        </ul>
    </nav>
</nav>
<%--<jsp:include page="/WEB-INF/jsp/jsp_templates/notifications_errors_section.jsp" flush="true"/>--%>
