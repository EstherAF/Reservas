
var MyCalendar = new Object();
MyCalendar.ref = '#calendar';

MyCalendar.viewName={
    'monthly': 'month',
    'weekly': 'agendaWeek'
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
    'gl': ['Dom', 'Lun', 'Mar', 'Mér', 'Xov', 'Ven', 'Sáb']
};

MyCalendar.monthNames = {
    'es': ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    'en': ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    'gl': ['Xaneiro', 'Febreiro', 'Marzo', 'Abril', 'Maio', 'Xuño', 'Xullo', 'Agosto', 'Septembro', 'Outubro', 'Novembro', 'Decembro']
};

MyCalendar.newReservationPath = applicationPath + 'reservations/new/';

MyCalendar.eventClick = function(event) {
    window.location.replace(event.url);
};

MyCalendar.dayClick = function(date) {
    var url = MyCalendar.newReservationPath + date.getFullYear() + '/' + date.getMonth() + '/' + date.getDate();
    window.location.href = url;
};

MyCalendar.getYear = function(){
    return MyCalendar.getStartDate().getFullYear();
};

MyCalendar.getMonth = function(){
    return MyCalendar.getStartDate().getMonth();
};

MyCalendar.getWeek = function(date){
    if(date == null){
        date = MyCalendar.getStartDate();
    }else if(!date instanceof Date)
        date = new Date(date);
    
    return $.datepicker.iso8601Week(date);
};

MyCalendar.addEventSource = function(eventSource){
    return $(MyCalendar.ref).fullCalendar('addEventSource', eventSource);
};

MyCalendar.removeEventSource = function(eventSource){
    $(MyCalendar.ref).fullCalendar('removeEventSource', eventSource);
};

MyCalendar.getStartDate = function(){
    return $(MyCalendar.ref).fullCalendar('getView').start;
};

MyCalendar.getEndDate = function(){
    return $(MyCalendar.ref).fullCalendar('getView').end;
};

MyCalendar.getView=function(){
    var view = $(MyCalendar.ref).fullCalendar('getView');
    return (view)? view.name : null;
};
