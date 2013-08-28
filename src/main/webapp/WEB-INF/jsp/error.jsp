<%-- 
    Document   : login
    Created on : 15-may-2013, 19:36:50
    Author     : Esther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<html>
    <head>
        <title>
            <s:message code="error.default"/>
        </title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp" flush="true"/>
    </head>
    <body>
        <div id="body">
            <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
            <div id="main_body">
                <section id="login">
                    
                </section>			
            </div>
        </div>
    </body>
</html>
