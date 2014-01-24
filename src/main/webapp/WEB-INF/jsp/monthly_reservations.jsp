<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.citius.reservas.controllers.controllerModel.ReservationInstanceCustom" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <title>
            <s:message code="reservations.title"/>
        </title>
        
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />
        <link rel="stylesheet" href="<c:url value="/resources/css/external/fullcalendar.css" />" type="text/css" />
        <link rel="stylesheet" href="<c:url value="/resources/css/monthly_reservations.css" />" type="text/css" />
        <script type="text/javascript" src="<c:url value="/resources/js/jquery/fullcalendar.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/libs/MyCalendar.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/viewHelpers/GeneralCalendar.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/MonthlyCalendarByOwnController.js" />"></script>


        <script type="text/javascript">
            $(function() {
//                ResourcePopUp.onLoad();
//                
                var year;
                var month;
                <c:if test="${not empty year}" >
                    year=${year};
                    month=${month}-1;
                </c:if>
                
                new MonthlyCalendarByOwn(year,month);
            });
        </script>

    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/monthly_reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <div id='calendar'></div>
                </section>    
            </div>   
        </div>      
    </body>
</html>