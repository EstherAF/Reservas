<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/templates/generic_head.jsp"/>
        <link rel="stylesheet" href="<c:url value="/resources/css/reservations.css" />" type="text/css" /> 
        <script type="text/javascript" class="source">

        </script>
    </head>
    <body>
        <div id="body">

            <jsp:include page="/WEB-INF/jsp/templates/simple_header.jsp" flush="true"/>

            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/templates/reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <table>  
                        <c:forEach var="instance" items="${reservationInstances}">
                            <p>ReservationInstances:<c:out value="${instance}" /></p>
                        </c:forEach>
                    </table>
                </section>    
            </div>   
        </div>      
    </body>
</html>