<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/jsp_templates/generic_head.jsp"/>
        <script type="text/javascript" src="<c:url value="/resources/js/models/resource.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/resourcesView.js" />"></script>
        <link rel="stylesheet" href="<c:url value="/resources/css/resources.css" />" type="text/css" /> 

        <script type="text/javascript" class="source">
            var resources = ${resourcesJson};

            $(function() {
                createTree(resources);

                $('input[name="is_group"]').click(function() {
                    is_group = $(this).attr('value');
                    element = $('#group_selector');
                    if (is_group === "true"){
                        element.css("display", "none");
                    } else {
                        element.css("display", "block");
                    }
                });

                $('input[name="create_resource"]').click(function() {
                    createResource();
                });

            });

        </script>
    </head>
    <body>
        <div id="body">

            <jsp:include page="/WEB-INF/jsp/jsp_templates/simple_header.jsp" flush="true"/>

            <div id="main_body">
                <section id="resources">
                    <header>
                        <h1>Lista de recursos</h1>
                    </header>
                    <div id="resourcesTree"></div>
                    <span class="button" id="create_resource">Añadir</span>
                </section>
                
                <section id="content">
                    <header>
                        <h1>Añadir nuevo grupo o recurso</h1>
                    </header>
                    
                    <form name="new_resource" action="/Reservas/j_spring_security_check" method="POST">
                        <p>
                            <label for="is_group">Tipo:</label>
                            <span class="radio">
                                <input type="radio" name="is_group" value="true" />Grupo
                                <input type="radio" name="is_group" value="false" />Recurso
                            </span>
                        </p><p id="group_selector" style="display: none;">
                                <label for="group">Grupo:</label>
                                <select name="group">
                                    <c:forEach var="res" items="${resources}">
                                        <option value="${res.id}">${res.name}</option>
                                    </c:forEach>
                                </select>
                        </p><p>
                            <label for="name">Nombre:</label>
                            <input type="text" name="name" autofocus required>
                        </p><p>
                            <label for="description">Descripción:</label>
                            <textarea name="description" required></textarea>
                        </p><p>
                            <input name="create_resource" type="button" value="Crear">
                        </p>
                    </form>
                </section>    
            </div>   
        </div>      
    </body>
</html>