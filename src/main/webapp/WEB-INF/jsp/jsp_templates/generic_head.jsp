<%-- 
    Document   : jsImports
    Created on : 18-jun-2013, 20:31:25
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<meta content="text/html" charset="UTF-8" http-equiv="Content-type">
<meta name="Esther Álvarez Feijoo" content="">


<!------Javascript------>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.js" />"></script>

<!--jsTree-->
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.jstree.js" />"></script>

<!--jQueryUI-->
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-ui.js" />"></script>


<!--Bootstrap-->
<script type="text/javascript" src="<c:url value="/resources/js/jquery/bootstrap.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/js/models/general.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/generalView.js" />"></script>

<!------CSS------->
<link rel="stylesheet" href="<c:url value="/resources/css/external/bootstrap.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/external/jquery-ui.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/general.css" />" type="text/css" /> 