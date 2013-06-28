<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <title>
            <s:message code="resources.title"/>
        </title>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp"/>
        <script type="text/javascript" src="<c:url value="/resources/js/models/resource.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/resourceTree.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/resourcesView.js" />"></script>
        <link rel="stylesheet" href="<c:url value="/resources/css/resources.css" />" type="text/css" /> 

        <script type="text/javascript">
            var resources = ${resourcesJson};
        </script>
    </head>
    <body>
        <div id="body">

            

            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <section id="resources">
                    <header>
                        <h1><s:message code="resources.list.label"/></h1>
                    </header>
                    <div id="resourcesTree"></div>
                    <span class="button" id="create_resource">
                        <s:message code="form.addBtn"/>
                    </span>
                </section>
                
                <section id="content">
                    <header>
                        <h1 create="<s:message code="resources.form.add.label"/>" 
                            update="<s:message code="resources.form.update.label"/>">
                            <s:message code="resources.form.add.label"/>
                        </h1>
                    </header>
                    <form name="new_resource" action="/Reservas/j_spring_security_check" method="POST">
                        <p>
                            <label for="is_group"><s:message code="resources.form.type.label"/></label>
                            <span class="radio">
                                <input type="radio" name="is_group" value="true" /><label><s:message code="resources.form.type.group"/></label>
                                <input type="radio" name="is_group" value="false" /><label><s:message code="resources.form.type.resource"/></label>
                            </span>
                        </p><p id="group_selector" style="display: none;">
                                <label for="group"><s:message code="resources.form.group.label"/></label>
                                <select name="group">
                                    
                                </select>
                        </p><p>
                            <label for="name"><s:message code="resources.form.name.label"/></label>
                            <input type="text" name="resourceName" autofocus required>
                        </p><p>
                            <label for="description"><s:message code="resources.form.description.label"/></label>
                            <textarea name="description" required></textarea>
                        </p><p>
                            <input name="submit" type="button"
                                   create="<s:message code="form.createBtn"/>" 
                                   update="<s:message code="form.updateBtn"/>"
                                   value="<s:message code="form.createBtn"/>">
                            <input name="delete" type="button" style="display:none;" delete="Eliminar">
                        </p>
                    </form>
                </section>    
            </div>   
        </div>      
    </body>
</html>