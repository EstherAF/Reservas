<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <c:choose>
            <c:when test="${operation eq 'create'}">
                <title><s:message code="reservation.form.create.title"/></title>
            </c:when>
            <c:otherwise>
                <title><s:message code="reservation.form.update.title"/></title>
            </c:otherwise>

        </c:choose>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />
        <link rel="stylesheet" href="<c:url value="/resources/css/new_reservation.css" />" type="text/css" /> 

        <script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/ResourceTree.js" />'></script>

        <script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/DateHelper.js" />'></script>

        <script type="text/javascript" src='<c:url value="/resources/js/libs/AutoComplete.js" />'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/ReservationController.js" />'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/CreateReservationController.js" />'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/UpdateReservationController.js" />'></script>

        <script type="text/javascript">
            var resources = ${resourcesJson};
            var users = ${usersJson};
            var operation = "${operation}";
            var date;
            <c:if test="${not empty reservationJson}" >
            var reservation = ${reservationJson};
            </c:if>
            <c:if test="${not empty year}">
                var date = new Date(${year}, ${month}, ${day}, 0, 0, 0, 0);
            </c:if>

                $(function() {
                    if (operation == 'create')
                        CreateReservationController.onLoad(users, resources, date);
                    else
                        UpdateReservationController.onLoad(users, resources, reservation);
                });
        </script>
    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/new_reservation_nav.jsp" flush="true"/>
                <section id="content">
                    <form class="new_reservation_form">

                        <div class="expandFloats">
                            <section class="formSection width30pc">
                                <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/information.jsp" flush="true"/>
                                <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/dates.jsp" flush="true"/>
                            </section>
                            <section class="formSection width30pc">
                                <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/resources.jsp" flush="true"/>
                            </section>
                            <section class="formSection width30pc">
                                <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/guests.jsp" flush="true"/>
                            </section>
                        </div>
<!--                        <input name="submit" type="button"
                               create="<s:message code="form.createBtn"/>" 
                               update="<s:message code="form.updateBtn"/>"
                               value="<s:message code="form.createBtn"/>">
                        <input name="delete" type="button" style="display:none;" value="<s:message code="form.deleteBtn"/>">-->
                    </form>
                </section>    
            </div>   
        </div>      
    </body>
</html>
