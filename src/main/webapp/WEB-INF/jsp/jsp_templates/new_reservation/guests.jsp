<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

                        
                        <fieldset>
                            <legend><s:message code="reservation.form.guests.label" /></legend>
                            <span class="i18n" type="invitations_icon" 
                                ACCEPTED="<s:message code="invitation.state.ACCEPTED" />"
                                REJECTED="<s:message code="invitation.state.REJECTED" />"
                                WAITING="<s:message code="invitation.state.WAITING" />"
                                NEW="<s:message code="invitation.state.NEW" />"></span>
                            <p>
                                <label for="guest"><s:message code="reservation.form.guests.search.label" /></label>
                                <input type="button" name="add" class="autocomplete" value="<s:message code="form.addBtn" />" style="display:none;"/>
                                <input type="text" name="guest" class="autocomplete users" />
                                <label for="errorGuestList" class="containsFormError" style="display:none;"><span><s:message code="reservation.form.guests.search.error" /></span></label>
                                <label for="guestList" 
                                    empty="<s:message code="reservation.form.guests.list.label.empty" />" 
                                    notEmpty="<s:message code="reservation.form.guests.list.label" />">
                                </label>
                                <table class="guests"></table>		
                            </p>
                        </fieldset>
