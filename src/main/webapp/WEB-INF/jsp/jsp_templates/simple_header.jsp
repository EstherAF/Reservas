<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<header id="main_header">
    <span class="i18n invitations" class="hidden" accept="<s:message code="form.accept"/>" reject="<s:message code="form.reject"/>"></span>

    <nav id="tiny">
        <ul>
            <li><a href="?lang=en">en</a></li>
            <li><a href="?lang=es">es</a></li>
            <li><a href="?lang=gl">gl</a></li>
        </ul>

        <sec:authorize access="isFullyAuthenticated()">
            <span class="login">
                <a href="<c:url value="/logout" />" >
                    <s:message code="nav.logout"/>
                </a>
            </span>            
        </sec:authorize>
    </nav>

    <sec:authorize access="isFullyAuthenticated()">
        <nav id="buttons">
            <ul class="left">
                <li><a href="<c:url value="/reservations/" />"> <s:message code="nav.myCalendarBtn"/></a>
                </li><li><a href="<c:url value="/resources/month/" />"><s:message code="nav.avalResourcesBtn"/></a>
                    </li><sec:authorize access="hasAnyRole('ROLE_TIC')"><li><a href="<c:url value="/resources/admin" />"><s:message code="nav.manageResourcesBtn"/></a></li>
                    </sec:authorize>
            </ul>
            <div class="dropdown_wrapper">
                <ul class="right dropdown" style="display:none;">
                    <li><a href="#"><span class="badge number_alert"></span><span class="icon-user"></span>  <s:message code="nav.invitations"/></a>
                    </li>
                </ul>
                <ul class="dropdown_child">
                </ul>
            </div>
        </nav>
    </sec:authorize>
    <div id="modal-wrapper"></div>
</header>
