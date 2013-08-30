
var GeneralCalendar = function(eventsSource, year, month, week, path, before, after, view) {

    $('#calendar').fullCalendar({
        header: {left: '', center: '', right: ''},
        firstDay: 1,
        editable: false,
        events: eventsSource,
        dayClick: MyCalendar.dayClick,
        'dayNamesShort': MyCalendar.dayNamesShort[locale],
        eventBackgroundColor: MyCalendar.grey,
        weekMode: 'variable',
        "year": year,
        "month": month,
        "week": week,
        "allDaySlot": false,
        "firstHour": 8,
        "allDayDefault": false,
        "slotMinutes": 30,
        "defaultView": view,
        "axisFormat": 'H:mm'
    });

    if (view == 'agendaWeek')
        $('#calendar').fullCalendar('option', 'aspectRatio', 1 / 16);
    else
        $('#calendar').fullCalendar('option', 'aspectRatio', 2 / 1);


    this.path = path;
    this.before = before;
    this.after = after;
    
    GeneralCalendar.updateLinks(this.path);
};

GeneralCalendar.updateLinks = function(path) {
    var year = MyCalendar.getYear();
    var start = MyCalendar.getStartDate();
    var end = MyCalendar.getEndDate();
    
    var monthly = function(path) {
        var month = MyCalendar.getMonth() + 1;
        var next = (month == 12) ?
                path + (year + 1) + '/' + 1 :
                path + year + '/' + (month + 1);

        var prev = (month == 1) ?
                path + (year - 1) + '/' + 12 :
                path + year + '/' + (month - 1);

        $('a.calendar-next').attr('href', next);
        $('a.calendar-prev').attr('href', prev);

        var date = new Date(start.getTime());
        date.setDate(date.getDate() + 7);
        var label = MyCalendar.monthNames[locale][start.getMonth()] + ' ' + start.getFullYear();

        $('#reservation_nav .label').text(label);
    };

    var weekly = function(path) {
        var week = MyCalendar.getWeek();

        var endYear = MyCalendar.getEndDate().getFullYear();
        var next = (endYear > year) ?
                path + (year + 1) + '/' + 1 :
                path + year + '/' + (week + 1);

        if (week == 1) {
            var date = MyCalendar.getStartDate();
            date.setDate(date.getDate() - 7);
            week = MyCalendar.getWeek(date);
            year -= 1;
        } else
            week -= 1;
        var prev = path + year + '/' + week;

        $('a.calendar-next').attr('href', next);
        $('a.calendar-prev').attr('href', prev);

        var label = start.getDate() + ' ' + MyCalendar.monthNames[locale][start.getMonth()] + ' ' + start.getFullYear();
        label += ' - ';
        label += end.getDate() + ' ' + MyCalendar.monthNames[locale][end.getMonth()] + ' ' + end.getFullYear();

        $('#reservation_nav .label').text(label);
    };

    //Execute function, depending on view type
    var viewName = MyCalendar.getView();
    var path = (viewName) ? path[viewName] : path[MyCalendar.viewName.monthly];

    switch (viewName) {
        case MyCalendar.viewName.monthly:
            monthly(path);
            break;
        case MyCalendar.viewName.weekly:
            weekly(path);
            break;
    }
};

GeneralCalendar.prototype.setState = function() {

    var weekly = function() {
        var year = MyCalendar.getYear();
        var week = MyCalendar.getWeek();
        var date = MyCalendar.getStartDate();
        historyObject = {
            'year': year,
            'month': week,
            'date': date
        };
        var subpath = year + '/' + week;
        window.history.pushState(historyObject, null, path + subpath);
    };

    var monthly = function() {
        var year = MyCalendar.getYear();
        var month = MyCalendar.getMonth() + 1;
        historyObject = {
            'year': year,
            'month': month
        };
        var subpath = year + '/' + month;
        window.history.pushState(historyObject, null, path + subpath);
    };


    //Execute function, depending on view type
    var viewName = MyCalendar.getView();
    var path = this.path[viewName];

    switch (viewName) {
        case MyCalendar.viewName.monthly:
            monthly(path);
            break;
        case MyCalendar.viewName.weekly:
            weekly(path);
            break;
    }
};

GeneralCalendar.prototype.bindEvents = function() {
    //Navigation events
    var self = this;

    $('.calendar-next').click(function(e) {
        e.preventDefault();
        if (self.before)
            self.before();
        $('#calendar').fullCalendar('next');
        GeneralCalendar.updateLinks(self.path);
        self.setState();
        if (self.after)
            self.after();
    });

    $('.calendar-prev').click(function(e) {
        e.preventDefault();
        if (self.before)
            self.before();
        $('#calendar').fullCalendar('prev');
        GeneralCalendar.updateLinks(self.path);
        self.setState();
        if (self.after)
            self.after();
    });

    $('.calendar-today').click(function(e) {
        e.preventDefault();
        if (self.before)
            self.before();
        $('#calendar').fullCalendar('today');
        GeneralCalendar.updateLinks(self.path);
        self.setState();
        if (self.after)
            self.after();
    });

    $('.calendar-view-month').click(function(e) {
        e.preventDefault();
        if (self.before)
            self.before();
        $('#calendar').fullCalendar('changeView', MyCalendar.viewName.monthly);
        $('#calendar').fullCalendar('option', 'aspectRatio', 2 / 1);
        GeneralCalendar.updateLinks(self.path);
        self.setState();
        if (self.after)
            self.after();
    });

    $('.calendar-view-week').click(function(e) {
        e.preventDefault();
        if (self.before)
            self.before();
        $('#calendar').fullCalendar('changeView', MyCalendar.viewName.weekly);
        $('#calendar').fullCalendar('option', 'aspectRatio', 1 / 16);
        GeneralCalendar.updateLinks(self.path);
        self.setState();
        if (self.after)
            self.after();
    });
};