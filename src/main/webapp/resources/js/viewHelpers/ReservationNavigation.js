//var reservationNavView = new Object();
var ReservationNavigation = Object();

ReservationNavigation.goToWeek = function(date){
    if(! (date instanceof Date))
        date = new Date(date);
    
    var year = date.getFullYear();
    var week = $.datepicker.iso8601Week(date);
    var urlRedirect = applicationPath+'reservations/week/' + year + '/' + week;
    window.location.href = urlRedirect;
};


$(function() {
    if (locale === 'en')
        locale = '';

    $.datepicker.setDefaults($.datepicker.regional[locale]);
    $("#calendarIcon").datepicker({
        showOn: "button",
        buttonImage: "/Reservas/resources/img/calendarIcon.png",
        buttonImageOnly: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        showWeek: true
    });

    $("#calendarIcon").change(function() {
        var date = $("#calendarIcon").datepicker('getDate');

        var year = date.getFullYear();
        var week = $.datepicker.iso8601Week(date);

        date.setDate(date.getDate() + 7);
        if (date.getFullYear() > year)
            year = date.getFullYear();

        //load new url
        var url = '../' + year + '/' + week;
        window.location.replace(url);
    });
});