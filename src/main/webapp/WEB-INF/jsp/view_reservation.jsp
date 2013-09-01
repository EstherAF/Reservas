<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript" src='<c:url value="/resources/js/ViewReservationController.js"/>'></script>

<!DOCTYPE html>
<html>
    <head>
        <title><s:message code="reservation.view.title"/></title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" />
        <link rel="stylesheet" href="<c:url value="/resources/css/new_reservation.css" />" type="text/css" /> 

        <script type="text/javascript">
            $(function() {
                ResourcePopUp.onLoad();
                var controller = new ViewReservationController();
            });
        </script>
    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/view_reservation_nav.jsp" flush="true"/>
                <section id="content">

                    <div class="expandFloats">

                        <section class="formSection width30pc">

                            <!-- Information -->
                            <fieldset class="information_fieldset">
                                <legend><s:message code="reservation.form.information.legend" /></legend>
                                <p>
                                    <span class="label"><s:message code="form.textName" /></span>
                                    <span class="data">${reservation.name}</span>
                                </p>
                                <p>
                                    <span class="label"><s:message code="form.description" /></span>
                                    <span class="data">${reservation.description}</span>
                                </p>
                                <p>
                                    <span class="label"><s:message code="reservation.view.owner" /></span>
                                    <span class="data">${reservation.owner.fullName} <span class="email">(${reservation.owner.email})</span></span>
                                </p>
                            </fieldset>

                            <!-- Date and time -->
                            <fieldset class="date_fieldset">
                                <legend><s:message code="reservation.form.date.legend" /></legend>
                                <p class="time">
                                    <span class="label"><s:message code="reservation.form.start" /></span>
                                    <span class="data">
                                        <fmt:formatDate dateStyle="medium" timeStyle="short" type="both"
                                                        value="${reservation.start}" />
                                    </span>
                                </p>
                                <p class="time">
                                    <span class="label"><s:message code="reservation.form.end" /></span>
                                    <span class="data">
                                        <fmt:formatDate dateStyle="medium" timeStyle="short" type="both"
                                                        value="${reservation.end}" />
                                    </span>
                                </p>

                                <!--Repetition-->
                                <p id="repetition">    
                                    <span class="label"><s:message code="reservation.form.repetition.type.label"/></span>
                                    <span class="data widthAuto">
                                        <c:choose>
                                            <c:when test="${reservation.repetition.type ne 'MONTHLY_RELATIVE'}">
                                                <s:message code="reservation.form.repetition.type.${fn:toLowerCase(reservation.repetition.type)}" />
                                            </c:when>
                                            <c:otherwise>
                                                <s:message code="reservation.form.repetition.type.monthly" />
                                            </c:otherwise>
                                        </c:choose>
                                    </span>

                                    <c:if test="${reservation.repetition.type ne 'ONCE'}">
                                    <p>
                                        <span class="label"><s:message code="reservation.view.interval" /></span><!--Cada-->
                                        <span class="data inline">${reservation.repetition.interval}</span>

                                        <span class="label inline">
                                            <c:choose>
                                                <c:when test="${reservation.repetition.type ne 'MONTHLY_RELATIVE'}">
                                                    <s:message code="reservation.view.interval.${fn:toLowerCase(reservation.repetition.type)}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <s:message code="reservation.view.interval.monthly" />
                                                </c:otherwise>
                                            </c:choose>
                                        </span><!--días-->
                                    </p>
                                    <p>
                                        <c:if test="${reservation.repetition.type eq 'MONTHLY' || reservation.repetition.type eq 'MONTHLY_RELATIVE'}">
                                            <span class="label"><s:message code="reservation.view.monthly.label" /></span><!--según-->
                                            <span class="data"><s:message code="reservation.view.monthly.${fn:toLowerCase(reservation.repetition.type)}" /></span>
                                        </c:if>
                                    </p><p>
                                        <c:if test="${reservation.repetition.type eq 'WEEKLY'}">
                                            <span class="label"><s:message code="reservation.view.weekly.label" /></span><!--hasta-->
                                            <c:forEach var="day" items="${reservation.repetition.weekDays}" varStatus="status">

                                                <span class="data"><s:message code="date.week.day.${day.number}" /></span>
                                                <c:if test="${not status.first && not status.last}"><c:out value=", " /></c:if>
                                            </c:forEach>
                                        </c:if>
                                    </p><p>
                                        <span class="label"><s:message code="reservation.view.endDate" /></span><!--hasta-->
                                        <span class="data"><fmt:formatDate dateStyle="medium" type="date" value="${reservation.repetition.endDate}" /></span>
                                    </p>
                                </c:if>

                                </p>
                            </fieldset>
                            <!--Invitados-->
                            <fieldset>
                                <legend><s:message code="reservation.form.guests.label" /></legend>
                                <p>
                                    <c:choose>
                                        <c:when test="${empty reservation.invitations}">
                                            <span class="label"><s:message code="reservation.view.guests.empty" /></span>
                                        </c:when>
                                        <c:otherwise>
                                        <table class="guests">
                                            <span class="i18n" type="invitations_icon" 
                                                  ACCEPTED="<s:message code="invitation.state.ACCEPTED" />"
                                                  REJECTED="<s:message code="invitation.state.REJECTED" />"
                                                  WAITING="<s:message code="invitation.state.WAITING" />"
                                                  NEW="<s:message code="invitation.state.NEW" />"></span>
                                            <c:forEach items="${reservation.invitations}" var="invitation">
                                                <tr uniqueName='${invitation.guest.uniqueName}' 
                                                    Title="<s:message code="invitation.state.${invitation.state}" />">
                                                    <td class="icon-invitation-${invitation.state}"></td>
                                                    <td class="content">${invitation.guest.fullName}
                                                        <span class="email">(${invitation.guest.email})</span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>	
                                    </c:otherwise>
                                </c:choose>
                                </p>
                            </fieldset>

                            <!--resources-->
                            <fieldset>
                                <legend><s:message code="reservation.form.resources.label" /></legend>
                                <span id="i18n" type="popup_resource" 
                                      title="<s:message code="resource.popup.title"/>" 
                                      name="<s:message code="resource.popup.name"/>" 
                                      description="<s:message code="resource.popup.description"/>"
                                      some_selected='<s:message code="reservation.form.resources.some"/>'
                                      no_selected='<s:message code="reservation.form.resources.empty"/>'
                                      ></span>
                                <p>
                                    <c:forEach items="${reservation.resources}" var="resource" varStatus="status">
                                        <span class="link underline popup" type="resource_popup" 
                                              id="${resource.id}" description="${resource.description}">
                                            ${resource.name}
                                        </span>
                                        <c:if test="${not status.first && not status.last}"> <c:out value=", " /> </c:if>
                                    </c:forEach>
                                </p>
                            </fieldset>

                        </section>    
                    </div>   
            </div>      
    </body>
</html>