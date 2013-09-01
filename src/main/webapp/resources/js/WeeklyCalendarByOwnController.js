var WeeklyCalendarByOwnControler = function() {

};

WeeklyCalendarByOwnControler.updateMonthlyViewLink = function() {
    var actualDate = WeeklyCalendarByOwnControler.getActualDate();
    if (actualDate)
        WeeklyCalendarByOwnControler.setMonthlyViewLink(actualDate);
};

WeeklyCalendarByOwnControler.getActualDate = function() {
    var url = document.URL.split('/');

    //If last element is empty
    if (!url[url.length - 1])
        url.remove(url.length - 1);

    var week = url[url.length - 1];
    var year = url[url.length - 2];

    if (isNaN(week) || isNaN(year))
        return null;

    return dateHelper.firstDayOfWeek(week, year);
};

WeeklyCalendarByOwnControler.setMonthlyViewLink = function(date) {
    if (! (date instanceof Date))
        date = new Date(date);

    var year = date.getFullYear();
    var month = date.getMonth();
    var URL = applicationPath + 'reservations/month/' + year + '/' + (month+1);

    $('[name="monthlyView"]').attr('href', URL);
};