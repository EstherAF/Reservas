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

        <!--libs-->
        <script type="text/javascript" src="<c:url value="/resources/js/viewHelpers/ResourceTree.js" />"></script>
        <!--model-->
        <script type="text/javascript" src="<c:url value="/resources/js/models/Resource.js" />"></script>
        <!--controller view-->
        <script type="text/javascript" src="<c:url value="/resources/js/ResourcesController.js" />"></script>
        <link rel="stylesheet" href="<c:url value="/resources/css/resources.css" />" type="text/css" /> 

        <script type="text/javascript">
            var resources = ${resourcesJson};
            resourcesView.onLoad();
        </script>
    </head>
    <body>
        <div id="body">
            <div id="main_body">
                <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>
                <jsp:include page="/WEB-INF/jsp/jsp_templates/navigationBars/resources_nav.jsp" flush="true"/>
                <section id="resources">
                    <header>
                        <h1><s:message code="resources.list.label"/></h1>
                    </header>
                    <div id="resourcesTree"></div>
                </section>

                <section id="content">
                    <header>
                        <h1 create="<s:message code="resources.form.add.label"/>" 
                            updateresource="<s:message code="resources.form.update.resource.label"/>"
                            updategroup="<s:message code="resources.form.update.group.label"/>">
                            <s:message code="resources.form.add.label"/>
                        </h1>
                    </header>
                    <form name="new_resource" action="/Reservas/j_spring_security_check" method="POST">
                        <div id="type_selector">
                            <label for="is_group"><s:message code="resources.form.type.label"/></label>
                            <p class="radio">
                                <input type="radio" name="is_group" value="true" />
                                <label><s:message code="resources.form.type.group"/></label>
                            </p>
                            <p class="radio">
                                <input type="radio" name="is_group" value="false" />
                                <label><s:message code="resources.form.type.resource"/></label>
                            </p>
                            <p for="is_group" style="display:none;" class="containsFormError"></p>
                        </div>
                        <div id="group_selector" style="display: none;" class="inline">
                            <label for="group"><s:message code="resources.form.group.label"/></label>
                            <select name="group">
                                
                            </select>
                        </div>
                        <div id="quantity" style="display: none;" class="inline nopadding">
                            <label for="quantity"><s:message code="resources.form.quantity.label"/></label>
                            <input type="number" name="quantity" value="1">
                            <p for="quantity" style="display:none;" class="containsFormError"></p>
                        </div>
                        <div>
                            <label for="name"><s:message code="resources.form.name.label"/>
                            </label>
                            <input type="text" name="name" autofocus required>
                            <p for="name" style="display:none;" class="containsFormError"></p>
                        </div>
                        <div>
                            <label for="description"><s:message code="resources.form.description.label"/></label>
                            <textarea name="description"></textarea>
                            <p for="description" style="display:none;" class="containsFormError"></p>
                        </div>
                    </form>
                </section>    
            </div>   
        </div>      
    </body>
</html>
