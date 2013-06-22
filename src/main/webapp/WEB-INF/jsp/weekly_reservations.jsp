<%@page import="com.citius.reservas.controllers.customModel.ReservationInstanceCustom"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp"/>
        <link rel="stylesheet" href="<c:url value="/resources/css/reservations.css" />" type="text/css" /> 
        <script type="text/javascript" class="source">
            var reservations = ${reservationsJson};

            $(function() {
//                createTree(resources);
//
//                $('input[name="is_group"]').click(function() {
//                    is_group = $(this).attr('value');
//                    element = $('#group_selector');
//                    if (is_group === "true"){
//                        element.css("display", "none");
//                    } else {
//                        element.css("display", "block");
//                    }
//                });
//
//                $('input[name="create_resource"]').click(function() {
//                    createResource();
//                });
            });

        </script>
    </head>
    <body>
        <div id="body">
            
            <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>

            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <c:choose>
                        <c:when test="${empty reservationInstances}">
                            <p>No hay reservas</p>
                        </c:when>
                        <c:otherwise>
                            <table class="weekly">  
                                <c:set var="before" />
                                <c:forEach var="instance" items="${reservationInstances}">
                                    <c:if test="${before ne instance.date}">
                                        <tr>
                                            <th colspan='5'>
                                                <c:out value="${instance.date}" />
                                            </th>
                                        </tr>
                                        <c:set var="before" value="${instance.date}" />
                                    </c:if>    

                                    <tr>
                                        <td></td>
                                        <td><c:out value="${instance.reservation.name}" /></td>

                                        <%
                                            String recursos = new String();
                                            ReservationInstanceCustom ins = (ReservationInstanceCustom) pageContext.getAttribute("instance");
                                            for (int i = 0; i < ins.getReservation().getResources().size(); i++) {
                                                if (i != 0) {
                                                    recursos += ", ";
                                                }
                                                recursos += ins.getReservation().getResources().get(i).getName();
                                            }
                                            pageContext.setAttribute("recursos", recursos);
                                        %>
                                        <td><c:out value="${recursos}" /></td>
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