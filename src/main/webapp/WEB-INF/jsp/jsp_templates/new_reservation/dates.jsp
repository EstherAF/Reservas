<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>

                        
                                <fieldset class="date_fieldset">
                                    <legend><s:message code="reservation.form.date.legend"/></legend>
                                    <div class="time">
                                            <label for="startDate"><s:message code="reservation.form.start"/></label>
                                            <input type="text" class="dateUI" name="startDate" required/>  
                                            <select name="startTimeHour" class="hour" required></select> :
                                            <select name="startTimeMinute" class="minute" required></select>
                                            <p for="startDate" style="display:none;" class="containsFormError"></p>
                                    </div>
                                    <div class="time">
                                            <label for="endDate"><s:message code="reservation.form.end"/></label>
                                            <input type="text" class="dateUI" name="endDate" required>  
                                            <select name="endTimeHour" class="hour" required></select> :
                                            <select name="endTimeMinute" class="minute" required></select>
                                            <p for="endDate" style="display:none;" class="containsFormError"></p>                                            
                                    </div>

                                    <!--Repetition-->
                                    <div id="repetition_selector" class="inline">    
                                        <label for="repetition_type"><s:message code="reservation.form.repetition.type.label"/></label>
                                        <select name="repetition_type" required>
                                            <option value="ONCE" selected><s:message code="reservation.form.repetition.type.once" /></option>				
                                            <option value="DAILY"><s:message code="reservation.form.repetition.type.daily" /></option>
                                            <option value="WEEKLY"><s:message code="reservation.form.repetition.type.weekly" /></option>
                                            <option value="MONTHLY"><s:message code="reservation.form.repetition.type.monthly" /></option>
                                        </select>
                                    </div>

                                    <!--Dynamic-->
                                    <div class="repetition" style="display:none;">
                                        <div>
                                            <div class="inline">
                                                <label for="inteval"><s:message code="reservation.form.interval" /></label>
                                                <input name="interval" type="number" value="1" /> 
                                                <span id="textInterval" 
                                                      daily="<s:message code="reservation.form.interval.daily" />" 
                                                      weekly="<s:message code="reservation.form.interval.weekly" />" 
                                                      monthly="<s:message code="reservation.form.interval.monthly" />"></span>
                                            </div>
                                            <div class="inline">
                                                <label for="endRepetitionDate"><s:message code="reservation.form.endDate" /></label>
                                                <input type="text" class="dateUI" name="endRepetitionDate" />

                                            </div>
                                            <p for="interval" style="display:none;" class="containsFormError"></p>
                                            <p for="endRepetitionDate" style="display:none;" class="containsFormError"></p>
                                        </div>
                                        
                                    </div>
                                    <!--Weekly-->
                                    <div id="weekly_repetition" style="display:none;">
                                        <label for="weekly_days">
                                            <s:message code="reservation.form.weekly.label" />
                                        </label>
                                        <c:forEach begin="1" end="7" var="i">
                                            <span class="checkbox horizontal">
                                                <input type="checkbox" name="weekly_days" value="${i}">
                                                <label><s:message code="date.week.day.short.${i}" /></label>
                                            </span>
                                        </c:forEach>
                                        <p for="weekly_days" style="display:none;" class="containsFormError"></p>
                                    </div>
                                    <!--Monthly-->
                                    <div id="monthly_repetition" style="display:none;">
                                        <label for="monthlyRepetition"><s:message code="reservation.form.monthly.label" /></label>
                                        <span type='radio'>
                                            <span class="radio">
                                                <input type="radio" name="monthlyRepetition" value="MONTHLY" selected>
                                                <label><s:message code="reservation.form.monthly.byMonth" /></label>
                                            </span><span class="radio">
                                                <input type="radio" name="monthlyRepetition" value="MONTHLY_RELATIVE">
                                                <label><s:message code="reservation.form.monthly.byWeek" /></label>
                                            </span>
                                        </span>
                                        <p for="monthlyRepetition" style="display:none;" class="containsFormError"></p>
                                    </div>
                                </fieldset>