<%-- 
    Document   : jsImports
    Created on : 18-jun-2013, 20:31:25
    Author     : Esther Álvarez Feijoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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

<!--ajax-->
<script type="text/javascript" src="<c:url value="/resources/js/libs/Ajax.js" />"></script>

<!--generic functions-->
<script type="text/javascript" src="<c:url value="/resources/js/libs/Utils.js" />"></script>

<!--form validation-->
<script type="text/javascript" src="<c:url value="/resources/js/libs/GenericForm.js" />"></script>

<script type="text/javascript" src="<c:url value="/resources/js/libs/Modals.js" />"></script>

<!--models-->
<script type="text/javascript" src="<c:url value="/resources/js/models/Resource.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/models/ReservedResource.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/models/Repetition.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/models/Reservation.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/models/Invitation.js" />"></script>
<script type="text/javascript" src='<c:url value="/resources/js/models/User.js"/>'></script>

<!--resourcePopUp-->
<script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/ResourcePopUp.js"/>'></script>

<!--Navigation between reservation views-->
<script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/ReservationNavigation.js"/>'></script>

<!--Invitation dropdown-->
<script type="text/javascript" src='<c:url value="/resources/js/libs/Dropdown.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/viewHelpers/NavigationBar.js"/>'></script>

<!------CSS------->
<link rel="stylesheet" href="<c:url value="/resources/css/external/bootstrap.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/external/jquery-ui.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/general.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/modules/reservation_nav.css" />" type="text/css" /> 
<link rel="stylesheet" href="<c:url value="/resources/css/fonts.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/resources/css/modules/dropdown.css" />" type="text/css" />

<script type="text/javascript">
    var locale = "${pageContext.response.locale}";
    var loggedUniqueName='';
    <c:if test="${pageContext['request'].userPrincipal != null}">
        loggedUniqueName = '<sec:authentication property="principal.username" />';
    </c:if>
        
    <c:if test="${error != null}">
        $(function(){
           var modal = new Modal(Modal.type.fail, "${error.message}", "${error.status}-${error.code}-${error.developerMessage}");
        });
    </c:if>
</script>