/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var MonthlyCalendar = function(eventsSource, year, month, path, before, after) {

    $('#calendar').fullCalendar({
        header: {left: '', center: '', right: ''},
        firstDay: 1,
        editable: false,
        events: eventsSource,
        dayClick: MonthlyCalendar.dayClick,
        'dayNamesShort': MonthlyCalendar.dayNamesShort[locale],
        eventBackgroundColor: MonthlyCalendar.grey,
        "year": year,
        "month":month
    });
    
    var self=this;
    var path = path;
    //Navigation events
    $('.calendar-next').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('next');
        MonthlyCalendar.updateLinks(path);
        MonthlyCalendar.setState(path);
        if(after) after.call(self);
        return false;
    });

    $('.calendar-prev').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('prev');
        MonthlyCalendar.updateLinks(path);
        MonthlyCalendar.setState(path);
        if(after) after.call(self);
        
        return false;
    });

    $('.calendar-today').click(function(e) {
        e.preventDefault();
        if(before) before.call(self);
        $('#calendar').fullCalendar('today');
        MonthlyCalendar.updateLinks(path);
        MonthlyCalendar.setState(path);
//        if(after) after();
        return false;
    });

};

MonthlyCalendar.orange = '#F36126';
MonthlyCalendar.grey='#666';

MonthlyCalendar.colors=[
    {color:'#5D8AA8', text:'#fff'},
    {color:'#9ACD32', text:'#333'},
    {color:'#FF4500', text:'#fff'},
    {color:'#8B008B', text:'#fff'},
    {color:'#B22222', text:'#fff'},
    {color:'#FFD700', text:'#333'},
    {color:'#4876FF', text:'#fff'}
];

MonthlyCalendar.dayNamesShort = {
    'es': ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'],
    'en': ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    'ga': ['Dom', 'Lun', 'Mar', 'Mér', 'Xov', 'Ven', 'Sáb']
};

MonthlyCalendar.monthNames = {
    'es': ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    'en': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    'ga': ['Xaneiro', 'Febreiro', 'Marzo', 'Abril', 'Maio', 'Xuño', 'Xullo', 'Agosto', 'Septembro', 'Outubro', 'Novembro', 'Decembro']
};

MonthlyCalendar.newReservationPath = applicationPath + 'reservations/new/';

MonthlyCalendar.eventClick = function(event) {
    window.location.replace(event.url);
};

MonthlyCalendar.dayClick = function(date) {
    var url = MonthlyCalendar.newReservationPath + date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate();
    window.location.href = url;
};


MonthlyCalendar.updateLinks=function(path){
    var year = MonthlyCalendar.getYear();
    var month = MonthlyCalendar.getMonth()+1;
    
    var next = (month==12)? 
                path+(year+1)+'/'+1 :
                path+year+'/'+(month+1);
            
    var prev = (month==1)? 
                path+(year-1)+'/'+12 :
                path+year+'/'+(month-1);
    
    $('a.calendar-next').attr('href',next);
    $('a.calendar-prev').attr('href',prev);
};

MonthlyCalendar.setState=function(path){
    var year = MonthlyCalendar.getYear();
    var month = MonthlyCalendar.getMonth()+1;  
    
    historyObject = {
        'year': year,
        'month': month
    };
    
    window.history.pushState(historyObject, null, path+year+'/'+month);
};

MonthlyCalendar.getYear = function(){
    return $('#calendar').fullCalendar('getDate').getFullYear();
};

MonthlyCalendar.getMonth = function(){
    return $('#calendar').fullCalendar('getDate').getMonth();
};

MonthlyCalendar.addEventSource = function(eventSource){
    return $('#calendar').fullCalendar('addEventSource', eventSource);
};

MonthlyCalendar.removeEventSource = function(eventSource){
    $('#calendar').fullCalendar('removeEventSource', eventSource);
};