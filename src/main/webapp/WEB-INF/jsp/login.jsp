<%-- 
    Document   : login
    Created on : 15-may-2013, 19:36:50
    Author     : Esther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Acceso</title>		
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
			</header>
			<div id="main_body">
                                <section class="error">
                                    <p>
                                        <c:if test="${not empty error}">
                                            ${error}
                                        </c:if>
                                    </p>
				</section>			
				<section id="login">
                                    <header>Acceso a la aplicación</header>
					<form name="f" action="/Reservas/j_spring_security_check" method="POST">
						<p>
							<label for="user_id">Nombre de usuario</label>
							<input type="text" name="id" id="user_id" placeholder="nombre.apellido.apellido" autofocus required>
						</p><p>
							<label for="user_id">Contraseña</label>
							<input type="password" name="pass" id="user_pwd" placeholder="contraseña" required>
						</p><p class="center">
							<input name="submit" type="submit" value="Acceder">
						</p><p>
							<input type="checkbox" name="remember_pass" id="remember_pass">
							<label for="remember_pass">Recordar contraseña</label>
						</p>
					
					</form>
				</section>			
			</div>
		</div>
	</body>
</html>