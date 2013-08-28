<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

                        
                                <fieldset class="information_fieldset">
                                    <legend><s:message code="reservation.form.information.legend"/></legend>
                                    <p>
                                        <label for="name"><s:message code="form.textName"/></label>
                                        <input type="text" name="name" required>
                                        <p for="name" style="display:none;" class="containsFormError"></p>
                                    </p>
                                    <p>
                                        <label for="description"><s:message code="form.description"/></label>
                                        <textarea name="description"></textarea>		  
                                    </p>
                                </fieldset>