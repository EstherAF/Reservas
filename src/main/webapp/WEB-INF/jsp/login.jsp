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
		<title><s:message code="login.title"/></title>		
		<meta content="text/html" charset="UTF-8" http-equiv="Content-type">
		<meta name="Esther" content="">
                <link rel="stylesheet" href="<c:url value="/resources/css/general.css" />" type="text/css" /> 
                <link rel="stylesheet" href="<c:url value="/resources/css/login.css" />" type="text/css" /> 
	</head>
	<body>
		<div id="body">
                    
                        <jsp:include page="/WEB-INF/jsp/templates/simple_header.jsp" flush="true"/>
                    
			<div id="main_body">
                                <section class="error">
                                    <p>
                                        <c:if test="${error}">
                                            <s:message code="error.login"/>
                                        </c:if>
                                    </p>
				</section>			
				<section id="login">
                                    <header>
                                        <s:message code="login.form.title"/>
                                    </header>
					<form name="f" action="/Reservas/j_spring_security_check" method="POST">
						<p>
							<label for="user_id"><s:message code="login.form.label.user"/></label>
							<input type="text" name="id" id="user_id" placeholder="<s:message code="login.form.input.user"/>" autofocus required>
						</p><p>
							<label for="user_id"><s:message code="login.form.label.password"/></label>
							<input type="password" name="pass" id="user_pwd" placeholder="<s:message code="login.form.input.password"/>" required>
						</p><p class="center">
							<input name="submit" type="submit" value="Acceder">
						</p><p>
							<input type="checkbox" name="remember_pass" id="remember_pass">
							<label for="remember_pass"><s:message code="login.form.label.remember_password"/></label>
						</p>
					
					</form>
				</section>			
			</div>
		</div>
	</body>
</html>
