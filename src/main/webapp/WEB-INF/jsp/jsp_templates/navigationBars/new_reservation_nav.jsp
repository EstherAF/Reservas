<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="navigation"/>
<c:set var="uniqueName" value="admin"/>

<!--${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.user.firstName}-->

<c:if test="${not empty navigation}">
    <nav id="reservation_nav">
        <nav class="right">
            <ul>
                <li>
                    <a class="reservas_btn" 
                       name="cancel"
                       href="<c:url value="/reservations/${reservation.id}" />" 
                       id="create_resource">
                        <s:message code="form.deleteBtn" />
                    </a>
                </li>
                <li>
                    <a class="reservas_btn" 
                       name="submit"
                       href="<c:url value="/reservations/${reservation.id}" />" 
                       id="create_resource">
                        <s:message code="form.saveBtn" />
                    </a>
                </li>
                <li>
                    <a class="reservas_btn delete" 
                       href="<c:url value="/reservations/${reservation.id}" />" 
                       id="create_resource">
                        <s:message code="form.deleteBtn" />
                    </a>
                </li>
            </ul>
        </nav>
    </nav>
</c:if>
<%--<jsp:include page="/WEB-INF/jsp/jsp_templates/notifications_errors_section.jsp" flush="true"/>--%>