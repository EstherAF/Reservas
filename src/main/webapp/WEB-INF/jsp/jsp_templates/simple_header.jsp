<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<header id="main_header">
    <nav id="tiny">
        <ul>
            <li><a href="?lang=en">en</a></li>
            <li><a href="?lang=es">es</a></li>
            <li><a href="?lang=ga">ga</a></li>
        </ul>

        <span class="login">
            <a href="j_spring_security_logout">
                <s:message code="nav.logout"/>
            </a>
        </span>
        <!--<sec:authorize access="isFullyAuthenticated()">
            
        </sec:authorize>-->
    </nav>
    <nav id="buttons">
        <ul>
            <li> <a href="<c:url value="/reservations/" />"> <s:message code="nav.myCalendarBtn"/> </a>
            </li><li><a href="<c:url value="#" />"><s:message code="nav.planificationBtn"/></a>
            </li><li><a href="<c:url value="#" />"><s:message code="nav.avalResourcesBtn"/></a>
            </li><li><a href="<c:url value="/resources/" />"><s:message code="nav.manageResourcesBtn"/></a></li>
        </ul>
    </nav>
    <!--<sec:authorize access="isFullyAuthenticated()">-->
        <!--</sec:authorize>-->

</header>