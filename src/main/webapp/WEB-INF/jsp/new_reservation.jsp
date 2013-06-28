<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="reservation.form.title"/></title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />
        <link rel="stylesheet" href="<c:url value="/resources/css/new_reservation.css" />" type="text/css" /> 
        <script type="text/javascript" src='<c:url value="/resources/js/resourceTree.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/models/resource.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/resourcePopUp.js"/>'></script>
        <script type="text/javascript" src='<c:url value="/resources/js/newReservationView.js"/>'></script>
        <script type="text/javascript">
            var resources = ${resourcesJson};
        </script>
    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <section id="content">
                    <form>
                        <section id="formSection">
                            <fieldset>
                                <legend><s:message code="reservation.form.information.legend"/></legend>
                                <p>
                                    <label for="name"><s:message code="form.textName"/></label>
                                    <input type="text" id="name" required>
                                </p>
                                <p>
                                    <label for="description"><s:message code="form.description"/></label>
                                    <textarea id="description"></textarea>		  
                                </p>
                            </fieldset>
                            <fieldset>
                                <legend><s:message code="reservation.form.date.legend"/></legend>
                                <p>
                                    <label for="date"><s:message code="reservation.form.date"/></label>
                                    <input type="date" id="date" required>
                                </p><p class="time">
                                    <label for="startTime"><s:message code="reservation.form.startTime"/></label>
                                    <select id="startTimeHour" class="hour" required></select>:
                                    <select id="startTimeMinute" class="minute" required></select>
                                </p><p class="time">
                                    <label for="endTime"><s:message code="reservation.form.endTime"/></label>
                                    <select id="endTimeHour" class="hour" required></select>:
                                    <select id="endTimeMinute" class="minute" required></select>
                                </p>

                                <!--Repetition-->
                                <p>    
                                    <label for="repetition_type"><s:message code="reservation.form.repetition.type.label"/></label>
                                    <select id="repetition_type" required>
                                        <option value="once" selected><s:message code="reservation.form.repetition.type.once" /></option>				
                                        <option value="daily"><s:message code="reservation.form.repetition.type.daily" /></option>
                                        <option value="weekly"><s:message code="reservation.form.repetition.type.weekly" /></option>
                                        <option value="monthly"><s:message code="reservation.form.repetition.type.monthly" /></option>
                                    </select>
                                </p>

                                <!--Dynamic-->
                                <div class="repetition" style="display:none;">
                                    <p>
                                        <label for="inteval"><s:message code="reservation.form.interval" /></label>
                                        <input type="number" id="interval" value="1"> 
                                        <span id="textInterval" 
                                              daily="<s:message code="reservation.form.interval.daily" />" 
                                              weekly="<s:message code="reservation.form.interval.weekly" />" 
                                              monthly="<s:message code="reservation.form.interval.monthly" />"></span>
                                    </p><p>
                                        <label for="endDate"><s:message code="reservation.form.endDate" /></label>
                                        <input type="date" id="endDate">
                                    </p>
                                    <!--Weekly-->
                                    <p id="weekly_repetition" style="display:none;">
                                        <label for="weekly_repetition">
                                            <s:message code="reservation.form.weekly.label" />
                                        </label>
                                        <c:forEach begin="1" end="7" var="i">
                                            <input type="checkbox" name="${i}" value="${i}">
                                            <s:message code="date.week.day.short.${i}" />
                                        </c:forEach>
                                    </p>
                                    <!--Monthly-->
                                    <p id="monthly_repetition" style="display:none;">
                                        <label for="monthlyRepetition"><s:message code="reservation.form.monthly.label" /></label>
                                        <span type='radio'>
                                            <input type="radio" name="monthlyRepetition" value="month" selected>
                                            <label><s:message code="reservation.form.monthly.byMonth" /></label>
                                            <input type="radio" name="monthlyRepetition" value="week">
                                            <label><s:message code="reservation.form.monthly.byWeek" /></label>
                                        </span>
                                    </p>
                                </div>
                            </fieldset>
                        </section>
                        <section id="resourcesSection">
                            <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/resources.jsp" flush="true"/>
                        </section>
                        <section id="inviteSection">
                            <jsp:include page="/WEB-INF/jsp/jsp_templates/new_reservation/guests.jsp" flush="true"/>
                        </section>

                    </form>
                </section>    
            </div>   
        </div>      
    </body>
</html>