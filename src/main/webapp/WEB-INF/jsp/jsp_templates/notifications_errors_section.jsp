<%-- 
    Document   : info_header
    Created on : 13-ago-2013, 1:03:31
    Author     : Esther Álvarez Feijoo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section class="info" style="display:none;">
    <c:forEach items="${notification}" var="notification">
        <p>notification</p>
    </c:forEach>
</section>
<section class="error" style="display:none;">
    <c:forEach items="${error}" var="error">
        <p>error</p>
    </c:forEach>
</section>

