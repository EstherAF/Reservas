/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var dateHelper =new Object();

dateHelper.selectorHour = function() {
    var selector = $('select[class="hour"]');
    for (n = 0; n < 24; ++n) {
        var text = n;
        if (n < 10)
            text = '0' + n;
        
        var html = "<option value=" + n + ">" + text + "</option>";
        
        selector.append(html);
    }
    
    $('select[name="startTimeHour"]').val(8);
    $('select[name="endTimeHour"]').val(9);
};

dateHelper.selectorMinutes = function() {
    var selector = $('select[class="minute"]');
    for (n = 0; n < 60; n += 15) {
        var text = n;
        if (n < 10)
            text = '0' + n;
        var html = "<option value=" + n + ">" + text + "</option>";
        selector.append(html);
    }
};

dateHelper.selectorTime = function() {
    this.selectorHour();
    this.selectorMinutes();
};

dateHelper.createDatePicker = function() {
    $(".dateUI").datepicker({
        minDate: 0
    });

    if (locale === 'en')
        locale = '';
    $.datepicker.setDefaults($.datepicker.regional[locale]);
};

dateHelper.onLoad = function(){
    $(".radio").click(function() {
        $(this).find('input[type="radio"]').prop('checked', true);
    });

    $(".checkbox").click(function() {
        $(this).find('input[type="radio"]').prop('checked', true);
    });

    dateHelper.createDatePicker();
    dateHelper.selectorTime();
};


/****/

dateHelper.firstDayOfWeek = function(week, year) { 
    
    if (typeof year !== 'undefined') {
        year = (new Date()).getFullYear();
    }

    var date       = dateHelper.firstWeekOfYear(year),
        weekTime   = dateHelper.weeksToMilliseconds(week),
        targetTime = date.getTime() + weekTime;
    
    return date.setTime(targetTime); 
    
};

dateHelper.weeksToMilliseconds = function (weeks) {
    return 1000 * 60 * 60 * 24 * 7 * (weeks - 1);
};

dateHelper.firstWeekOfYear=function (year) {
    var date = new Date();
    date = dateHelper.firstDayOfYear(date,year);
    date = dateHelper.firstWeekday(date);
    return date;
};

dateHelper.firstDayOfYear = function (date, year) {
    date.setYear(year);
    date.setDate(1);
    date.setMonth(0);
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
};

dateHelper.firstWeekday=function(date) {
    
    var day = date.getDay(),
        day = (day === 0) ? 7 : day;
        
    if (day > 3) {

        var remaining = 8 - day,
            target    = remaining + 1;
                
        date.setDate(target);
    }
    
    return date;
};

