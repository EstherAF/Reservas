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

