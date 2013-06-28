$(function(){
    if(locale === 'en')
        locale='';
    
    $.datepicker.setDefaults($.datepicker.regional[locale]);
    $( "#calendarIcon" ).datepicker({
      showOn: "button",
      buttonImage: "/Reservas/resources/img/calendarIcon.png",
      buttonImageOnly:true,
      showOtherMonths: true,
      selectOtherMonths: true,
      showWeek: true
    });
    
     $( "#calendarIcon" ).change(function(){
         dateArray = $( "#calendarIcon" ).val().split('/');
         date=new Date();
         date.setDate(dateArray[0]);
         date.setMonth(dateArray[1]-1);
         date.setYear(dateArray[2]);
         
         year=date.getFullYear();
         week=$.datepicker.iso8601Week(date);
         
         url='../'+year+'/'+week
         window.location.replace(url);
         
     });
    
});