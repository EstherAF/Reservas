<%@ page import="com.citius.reservas.controllers.customModel.ReservationInstanceCustom" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />
        <link rel="stylesheet" href="<c:url value="/resources/css/reservations.css" />" type="text/css" /> 
        <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css" />" type="text/css" /> 
        <jsp:include page="/WEB-INF/jsp/js_templates/reservations.jsp" />
    </head>
    <body>
        <div id="body">
            <div type="popup_resource" title="Detalles del recurso"> 
                <p>Name: <span class="name"></span></p>
                <p>Description: <span class="description"></span></p>
            </div>
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <c:choose>
                        <c:when test="${empty reservationInstances}">
                            <p>No hay reservas</p>
                        </c:when>
                        <c:otherwise>
                            <table class="weekly">  
                                <tr class="header">
                                    <td></td>
                                    <td>nombre</td>
                                    <td>recursos reservados</td>
                                    <td>propietario</td>
                                    <td>hora</td>
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
                                            <%
                                                String recursos = new String();
                                                ReservationInstanceCustom ins = (ReservationInstanceCustom) pageContext.getAttribute("instance");
                                                for (int i = 0; i < ins.getReservation().getResources().size(); i++) {
                                                    if (i != 0) {
                                                        recursos += ", ";
                                                    }
                                                    recursos += "<span class=\"link\" type=\"resource_popup\" id=\""
                                                            + ins.getReservation().getResources().get(i).getId() + "\" description=\""
                                                            + ins.getReservation().getResources().get(i).getDescription() + "\" name=\""
                                                            + ins.getReservation().getResources().get(i).getName() + "\"" + ">"
                                                            + ins.getReservation().getResources().get(i).getName() + "</span>";
                                                }
                                            %>
                                            <%= recursos%>
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