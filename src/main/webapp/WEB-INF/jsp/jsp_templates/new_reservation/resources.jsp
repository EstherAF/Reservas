<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

                        <fieldset>
                            <legend><s:message code="reservation.form.resources.label" /></legend>
                            <span id="i18n" type="popup_resource" 
                                  title="<s:message code="resource.popup.title"/>" 
                                  name="<s:message code="resource.popup.name"/>" 
                                  description="<s:message code="resource.popup.description"/>"
                                  some_selected='<s:message code="reservation.form.resources.some"/>'
                                  no_selected='<s:message code="reservation.form.resources.empty"/>'
                                  ></span>
                            <p>
                                <label id="selectedDescription"><s:message code="reservation.form.resources.empty"/></label>
                                <span id='selectedResources'></span>
                            </p>
                            <div id="resourcesTree"></div>
                            <p for="resources" class="containsFormError"></p>
                        </fieldset>