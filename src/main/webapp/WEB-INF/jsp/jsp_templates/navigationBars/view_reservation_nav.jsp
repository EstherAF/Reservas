<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="navigation"/>
<c:set var="uniqueName" value="admin"/>

<!--${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.user.firstName}-->

<c:choose>
    <c:when test="${uniqueName eq reservation.owner.uniqueName}">
        <c:set var="navigation" value="owner" />
    </c:when>
    <c:otherwise>
        <c:forEach var="invitation" items="${reservation.invitations}">
            <c:if test="${invitation.guest.uniqueName eq uniqueName}">
                <c:set var="navigation" value="${invitation.state}" />
            </c:if>
        </c:forEach>
    </c:otherwise>
</c:choose>

<c:if test="${not empty navigation}">
    <nav id="reservation_nav">
        <nav class="right">
            <ul>
                <c:choose>
                    <c:when test="${navigation.class.simpleName == 'InvitationState'}">
                        <li><span class="invitation_label"
                                  ACCEPTED='<s:message code="reservation.view.invitation.accepted" />'
                                  REJECTED='<s:message code="reservation.view.invitation.rejected" />'
                                  WAITING='<s:message code="reservation.view.invitation.waiting" />'
                                  ><s:message code="reservation.view.invitation.${fn:toLowerCase(navigation)}" /></span></li>
                        <li state="ACCEPTED"
                            <c:if test="${navigation eq 'ACCEPTED'}">
                                style="display:none;"
                            </c:if>
                            >
                            <a class="link invitation reservas_btn" 
                               reservationId="${reservation.id}"
                               state="ACCEPTED">
                                <s:message code="form.accept" />
                            </a></li>
                        <li state="REJECTED"
                            <c:if test="${navigation eq 'REJECTED'}">
                                style="display:none;"
                            </c:if>
                            >
                            <a class="reservas_btn link invitation" 
                               reservationId="${reservation.id}"
                               state="REJECTED">
                                <s:message code="form.reject" />    
                            </a></li>

                    </c:when>
                    <c:when test="${navigation.class.simpleName == 'String'}">

                        <li>
                            <!--Direct redirect-->
                            <a class="reservas_btn update" 
                               href="<c:url value="/reservations/update/${reservation.id}" />">
                                <s:message code="form.updateBtn" />
                            </a>
                        </li>
                        
                        <!--By javascript-->
                        <li>
                            <a class="reservas_btn delete" 
                               resrevationId="${reservation.id}"
                               href="#"
                               name="delete">
                                <s:message code="form.deleteBtn" />
                            </a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
        </nav>
    </nav>
</c:if>
<%--<jsp:include page="/WEB-INF/jsp/jsp_templates/notifications_errors_section.jsp" flush="true"/>--%>