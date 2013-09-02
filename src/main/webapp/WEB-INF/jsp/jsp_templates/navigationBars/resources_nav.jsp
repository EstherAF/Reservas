<%-- 
    Document   : reservation_nav
    Created on : 21-jun-2013, 19:25:15
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<section>
    <nav id="reservation_nav">
        <nav class="left">
            <ul>
                <li>
                    <a class="reservas_btn" href="#" 
                       id="create_resource">
                        <s:message code="resources.form.add"/>
                    </a>
                </li>
            </ul>
        </nav>
        <nav class="right">
            <ul>

                <li><a href="#" class="reservas_btn" name="submit" type="button"
                       create="<s:message code="resources.form.create"/>" 
                       update="<s:message code="resources.form.update"/>">
                        <s:message code="resources.form.create"/>
                    </a></li>
                <li><a href="#" class="reservas_btn" name="delete" type="button"
                       style="display:none;">
                        <s:message code="resources.form.delete"/>
                        <span class="icon-remove"></span>
                    </a></li>
            </ul>
        </nav>
    </nav>
</section>
<%--<jsp:include page="/WEB-INF/jsp/jsp_templates/notifications_errors_section.jsp" flush="true"/>--%>