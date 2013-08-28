<%-- 
    Document   : login
    Created on : 15-may-2013, 19:36:50
    Author     : Esther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>
        <title><s:message code="login.title"/></title>		
        <meta content="text/html" charset="UTF-8" http-equiv="Content-type">
        <meta name="Esther" content="">
        <link rel="stylesheet" href="<c:url value="/resources/css/general.css" />" type="text/css" /> 
        <link rel="stylesheet" href="<c:url value="/resources/css/login.css" />" type="text/css" /> 
    </head>
    <body>
        <div id="body">
            <header id="main_header">
                <figure>
                    <img src="#"> 
                </figure>
                <nav>
                    <ul>
                        <li><a href="?lang=en">en</a></li>
                        <li><a href="?lang=es">es</a></li>
                        <li><a href="?lang=ga">ga</a></li>
                        
                    </ul>
                </nav>
                <a href="j_spring_security_logout">Cerrar sesión</a>
            </header>


            <div id="main_body">			
                <section id="login">
                    <p>You're in a admin section.</p>
                    <p>Role:
                        <sec:authorize access="hasRole('ROLE_TIC')">
                            TIC
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            USER
                        </sec:authorize>
                    </p>
                </section>			
            </div>
        </div>
    </body>
</html>