<%@ page import="com.citius.reservas.controllers.customModel.ReservationInstanceCustom" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <title>
            <s:message code="reservation.week.title"/>
        </title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />

        <script type="text/javascript" src='<c:url value="/resources/js/models/resource.js" />'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/resourcePopUp.js" />'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/reservationNavView.js" />'></script>

        <link rel="stylesheet" href="<c:url value="/resources/css/weekly_reservations.css" />" type="text/css" /> 
        
        <script type="text/javascript">
            var locale = "${pageContext.response.locale}";
        </script>

    </head>
    <body>
        <div id="body">
            <div id="i18n" type="popup_resource" 
                 title="<s:message code="resource.popup.title"/>" 
                 name="<s:message code="resource.popup.name"/>" 
                 description="<s:message code="resource.popup.description"/>">
            </div>
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <c:choose>
                        <c:when test="${empty reservationInstances}">
                            <p class="test"><s:message code="reservation.empty"/></p>
                        </c:when>
                        <c:otherwise>
                            <table class="weekly">  
                                <tr class="header">
                                    <td></td>
                                    <td><s:message code="reservation.week.name"/></td>
                                    <td><s:message code="reservation.week.reservedResources"/></td>
                                    <td><s:message code="reservation.week.owner"/></td>
                                    <td><s:message code="reservation.week.time"/></td>
                                </tr>
                                <c:set var="before" />
                                <c:forEach var="instance" items="${reservationInstances}">
                                    <c:choose>
                                        <c:when test="${before ne instance.date}">
                                            <tr class="date"><th colspan='5'></th></tr>
                                            <tr><td><c:out value="${instance.date}" /></td>

                                                <c:set var="before" value="${instance.date}" />
                                            </c:when>  
                                            <c:otherwise>    
                                            <tr><td></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td><c:out value="${instance.reservation.name}" /></td>
                                        <td>
                                            <c:forEach var="resource" items="${instance.reservation.resources}" varStatus ="status">
                                                <c:if test="${! status.first}"> ,</c:if>
                                                <span class="link" type="resource_popup" id="${resource.id}" description="${resource.description}">
                                                    ${resource.name}
                                                </span>
                                            </c:forEach>
                                        </td>
                                        <td><c:out value="${instance.reservation.owner}" /></td>
                                        <td><c:out value="${instance.startTime} - ${instance.endTime}" /></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </section>    
            </div>   
        </div>      
    </body>
</html>