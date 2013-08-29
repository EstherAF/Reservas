/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var MyCalendar = function(eventsSource, year, month, week, pathIn, before, after, view) {
    var day;
    if(week){
        var date = dateHelper.firstDayOfWeek(week, year);
        if(! date instanceof Date)
            date = new Date(date);
        day = date.getDate();
    }

    $('#calendar').fullCalendar({
        header: {left: '', center: '', right: ''},
        firstDay: 1,
        editable: false,
        events: eventsSource,
        dayClick: MyCalendar.dayClick,
        'dayNamesShort': MyCalendar.dayNamesShort[locale],
        'eventBackgroundColor': MyCalendar.grey,
        "year": year,
        "month":month,
        "date":day,
        "defaultView":view,
        'weekMode':'variable'
    });
    
    var self=this;
    var path = pathIn;
    //Navigation events
    $('.calendar-next').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('next');
        MyCalendar.updateLinks(path);
        MyCalendar.setState(path);
        if(after) after.call(self);
        return false;
    });

    $('.calendar-prev').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('prev');
        MyCalendar.updateLinks(path);
        MyCalendar.setState(path);
        if(after) after.call(self);
        
        return false;
    });

    $('.calendar-today').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('today');
        MyCalendar.updateLinks(path);
        MyCalendar.setState(path);
//        if(after) after();
        return false;
    });
    
    MyCalendar.updateLinks(pathIn);
};

MyCalendar.view={
    "weekly": "agendaWeek",
    "monthly": "month"
};

MyCalendar.orange = '#F36126';
MyCalendar.grey='#666';

MyCalendar.colors=[
    {color:'#5D8AA8', text:'#fff'},
    {color:'#9ACD32', text:'#333'},
    {color:'#FF4500', text:'#fff'},
    {color:'#8B008B', text:'#fff'},
    {color:'#B22222', text:'#fff'},
    {color:'#FFD700', text:'#333'},
    {color:'#4876FF', text:'#fff'}
];

MyCalendar.dayNamesShort = {
    'es': ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'],
    'en': ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    'ga': ['Dom', 'Lun', 'Mar', 'Mér', 'Xov', 'Ven', 'Sáb']
};

MyCalendar.monthNames = {
    'es': ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    'en': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    'ga': ['Xaneiro', 'Febreiro', 'Marzo', 'Abril', 'Maio', 'Xuño', 'Xullo', 'Agosto', 'Septembro', 'Outubro', 'Novembro', 'Decembro']
};

MyCalendar.newReservationPath = applicationPath + 'reservations/new/';

MyCalendar.eventClick = function(event) {
    window.location.replace(event.url);
};

MyCalendar.dayClick = function(date) {
    var url = MyCalendar.newReservationPath + date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate();
    window.location.href = url;
};


MyCalendar.updateLinks=function(pathIn){
    var viewName = MyCalendar.getViewName();
    
    var next, prev; 
    var date = MyCalendar.getStartDate();
    var year = date.getFullYear();
    
    var label = $('#reservation_nav .label');
    label.html('');
    
    if(viewName == MyCalendar.view.monthly){
    
        var month = date.getMonth()+1;
        
        var path = pathIn['monthly'];

        next = (month==12)? 
                    path+(year+1)+'/'+1 :
                    path+year+'/'+(month+1);

        prev = (month==1)? 
                    path+(year-1)+'/'+12 :
                    path+year+'/'+(month-1);
        
        label.append(MyCalendar.monthNames[locale][month-1]+" "+year);
    }else if(viewName == MyCalendar.view.weekly){
        var week = MyCalendar.getWeek(date);
        
        var path = pathIn['weekly'];
        
        date.setDate(date.getDate()+7);
        var yearNextWeek = date.getFullYear();
        
        date.setDate(date.getDate()-14);
        var prevWeek = MyCalendar.getWeek(date);
        
        next = (yearNextWeek === year)? 
                    path+(year)+'/'+ (week+1) :
                    path+(year+1)+'/'+1;

        prev = (prevWeek === (week-1))? 
                    path+(year)+'/'+prevWeek :
                    path+(year-1)+'/'+(prevWeek);
            
        var start = MyCalendar.getStartDate();
        var end = MyCalendar.getEndDate();
            
        label.append(start.getDate()+" "+MyCalendar.monthNames[locale][start.getMonth()]+" "+start.getFullYear());
        label.append(" - ");
        label.append(end.getDate()+" "+MyCalendar.monthNames[locale][end.getMonth()]+" "+end.getFullYear());
    }
    
    $('a.calendar-next').attr('href',next);
    $('a.calendar-prev').attr('href',prev);
};

MyCalendar.setState=function(pathIn){
    var view = MyCalendar.getViewName();
    var year = MyCalendar.getYear();
    var month = MyCalendar.getMonth()+1;
    var week = MyCalendar.getWeek();
    
    historyObject = {
        'year': year,
        'month': month,
        'week': week,
        'view':view
    };
    
    var path;
    if(view===MyCalendar.view.monthly){
        path = pathIn['monthly']+year+'/'+month;
    }else if(view===MyCalendar.view.weekly){
        path = pathIn['weekly']+year+'/'+week;
    
    window.history.pushState(historyObject, null, path+year+'/'+month);
};

MyCalendar.getYear = function(){
    return MyCalendar.getStartDate().getFullYear();
};

MyCalendar.getMonth = function(){
    return MyCalendar.getStartDate().getMonth();
};

MyCalendar.addEventSource = function(eventSource){
    return $('#calendar').fullCalendar('addEventSource', eventSource);
};

MyCalendar.removeEventSource = function(eventSource){
    $('#calendar').fullCalendar('removeEventSource', eventSource);
};

MyCalendar.getViewName = function(){
    return $('#calendar').fullCalendar('getView').name;
};

MyCalendar.getStartDate = function(){
    return $('#calendar').fullCalendar('getView').start;
};

MyCalendar.getEndDate = function(){
    return $('#calendar').fullCalendar('getView').end;
};

MyCalendar.getWeek=function(date){
    if(!date)
        date = MyCalendar.getStartDate();
    
    return $.datepicker.iso8601Week(date);
};