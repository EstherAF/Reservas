<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/templates/generic_head.jsp"/>

        <script type="text/javascript" class="source">

        </script>
    </head>
    <body>
        <div id="body">

            <jsp:include page="/WEB-INF/jsp/templates/simple_header.jsp" flush="true"/>

            <div id="main_body">
                <nav>

                </nav>
                <section id="content">
                    <header>
                        <h1>Añadir nuevo recurso o grupo</h1>
                    </header>
                    <table>
                        <tr>
                            <th>Nombre</th>
                            <th>Recursos reservados</th>
                            <th>Propietario</th>
                            <th></th>
                        </tr>
                        <c:forEach var="instance" items="${reservationInstances}">
                            <p></p>
                        </c:forEach>
                        
                    </table>
                </section>    
            </div>   
        </div>      
    </body>
</html>