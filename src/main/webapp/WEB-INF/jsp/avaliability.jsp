<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.citius.reservas.controllers.customModel.ReservationInstanceCustom" %>
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
        <link rel="stylesheet" href="<c:url value="/resources/css/external/fullcalendar.css" />" type="text/css" />
        <link rel="stylesheet" href="<c:url value="/resources/css/monthly_avaliability.css" />" type="text/css" />
        <script type="text/javascript" src="<c:url value="/resources/js/viewHelpers/ResourceTree.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery/fullcalendar.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/viewHelpers/MyCalendar.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/CalendarAvaliabilityController.js" />"></script>


        <script type="text/javascript">
            $(function() {
                var year, month, week, view;
                <c:choose>
                    <c:when test="${not empty month}" >
                        year =${year};
                        month =${month} - 1;
                    </c:when>
                    <c:when test="${not empty week}" >
                            year =${year};
                            month =${week} - 1;
                    </c:when>
                </c:choose>
                <c:if test="${not empty view}" >
                    view = "${view}";
                </c:if>

                CalendarAvaliability.onLoad(view, year, month, week);
            });
        </script>

    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/avaliability_nav.jsp" flush="true"/>
                <section id="resources" class="inline">
                    <header>
                        <h1><s:message code="resources.list.label"/></h1>
                    </header>
                    <div id="resourcesTree"></div>
                </section>
                <section id="content">
                    <div id='calendar'></div>
                </section>    
            </div>   
        </div>      
    </body>
</html>