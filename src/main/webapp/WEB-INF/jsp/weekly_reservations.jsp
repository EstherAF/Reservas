<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page import="com.citius.reservas.controllers.controllerModel.ReservationInstanceCustom" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <title>
            <s:message code="reservations.title"/>
        </title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />

        <link rel="stylesheet" href="<c:url value="/resources/css/weekly_reservations.css" />" type="text/css" /> 
        <script type="text/javascript" src="<c:url value="/resources/js/WeeklyCalendarByOwnController.js" />" /> 
        
        <script type="text/javascript">
        </script>
        
        
        <script type="text/javascript">
            $(function() {
                ResourcePopUp.onLoad();
                ReservationNavigation.onLoad();
                WeeklyCalendarByOwnControler.updateMonthlyViewLink();
            });
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
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/weekly_reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <c:choose>
                        <c:when test="${empty reservationInstances}">
                            <p class="test"><s:message code="reservation.empty"/></p>
                        </c:when>
                        <c:otherwise>
                            <table class="weekly">  
                                <tr class="header">
                                    <td></td>
                                    <td><s:message code="reservation.week.time"/></td>
                                    <td><s:message code="reservation.week.name"/></td>
                                    <td><s:message code="reservation.week.owner"/></td>
                                    <td><s:message code="reservation.week.reservedResources"/></td>
<!--                                    <td><s:message code="reservation.week.start"/></td>-->
<!--                                    <td><s:message code="reservation.week.end"/></td>-->
                                </tr>
                                <c:set var="printLabel" />

                                <%  Calendar start = (Calendar) request.getAttribute("start");
                                    Calendar end = (Calendar) request.getAttribute("end");
                                    Date date;
                                    for (date = start.getTime();
                                            !start.after(end);
                                            start.add(Calendar.DATE, 1), date = start.getTime()) {

                                        start.add(Calendar.DATE, 1);
                                        Date endDate = start.getTime();
                                        start.add(Calendar.DATE, -1);
                                        pageContext.setAttribute("endDateThisDay", endDate);
                                %>
                                <c:set var="printLabel" value="true" />

                                <c:forEach var="instance" items="${reservationInstances}">
                                    <!--Show day label? -->
                                    <% ReservationInstanceCustom instance = (ReservationInstanceCustom) pageContext.getAttribute("instance");
                                        if (instance.isBetweenDates(start.getTime(), endDate)) {%>
                                    <c:choose>
                                        <c:when test="${printLabel}">
                                            <tr class="date"><th colspan='6'></th></tr>
                                            <tr>
                                                <td class="label">
                                                    <% pageContext.setAttribute("dateLabel", instance.weeklyDay(start.getTime()));%>
                                                    <c:out value="${dateLabel}" />
                                                </td>
                                                <c:set var="printLabel" value="false" />
                                            </c:when>  
                                            <c:otherwise>    
                                            <tr><td class="label"></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <!-- Print data of reservation instance ------------------------>

                                        <!--start, end-->
                                        <c:set var="startDate" value="${instance.startWeeklyDay}" />
                                        <c:set var="endDate" value="${instance.endWeeklyDay}" />
                                        <td class="doubletime">
                                            <c:choose>
                                                <c:when test="${ (!(startDate eq dateLabel) && !(endDate eq dateLabel || instance.endTimeDate eq endDateThisDay)) ||
                                                                 (instance.endTimeAsString eq '00:00' && instance.startTimeAsString eq '00:00')}">
                                                        Todo el día
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${startDate eq dateLabel}">
                                                            <c:out value="${instance.startTimeAsString}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="icon-double-angle-left"></span>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <c:if test="${ (startDate eq dateLabel) && (endDate eq dateLabel || instance.endTimeDate eq endDateThisDay)}">
                                                        -
                                                    </c:if>

                                                    <c:choose>
                                                        <c:when test="${endDate eq dateLabel || instance.endTimeDate eq endDateThisDay}">
                                                            <c:out value="${instance.endTimeAsString}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="icon-double-angle-right"></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                        <!--Name of reservation-->
                                        <td class="name"><a href="<c:url value="/reservations/${instance.reservation.id}" />"><c:out value="${instance.reservation.name}" /></a></td>

                                        <!--owner-->
                                        <td class="owner"><c:out value="${instance.reservation.owner.fullName}" /></td>

                                        <!--resources array-->
                                        <td class="resources">
                                            <c:forEach var="resource" items="${instance.reservation.resources}" varStatus ="status"><c:if test="${! status.first}">, </c:if><span class="link underline popup" 
                                                           type="resource_popup" 
                                                           id="${resource.id}" 
                                                    description="${resource.description}">${resource.name}</span></c:forEach>
                                            </td>
                                        </tr>
                                    <%}%>
                                </c:forEach>

                                <%}%>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </section>    
            </div>   
        </div>      
    </body>
</html>
