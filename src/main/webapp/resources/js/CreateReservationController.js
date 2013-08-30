CreateReservationController = function(users, resources) {    
    //parent
    ReservationController.call(this,users, resources);    
};

//Inheritance from ReservationController
CreateReservationController.prototype = Object.create(ReservationController.prototype);

CreateReservationController.prototype.populateDates = function(date){
    $('[name="startDate"]').datepicker("setDate", date);
    $('[name="endDate"]').datepicker("setDate", date);
};

CreateReservationController.prototype.addGuestCreating = function() {
    $("[for=errorGuestList]").hide();

    var user = this.autoComplete.getSelected();
    if (!user) {
        $("[for=errorGuestList]").show();
    } else {
        var invitation = new Invitation(user);
        //Create new row
        this.addGuest(invitation);
    }
};

CreateReservationController.prototype.createReservation = function() {
    var reservation = this.getReservationFromForm();
    if (reservation) {
        NewReservation.createReservation(
            reservation,
            function(response) {
                ReservationNavigation.goToWeek(response.start);
            },
            ajaxError);
    }
};

////On load
CreateReservationController.onLoad = function(users, resources, date) {
    var viewController = new CreateReservationController(users, resources);
    ReservationController.load.call(viewController, viewController.resourceTree, viewController.addGuestCreating);
    if(!date)
        date = new Date();
    viewController.populateDates(date);
    
    $('input[name="submit"]').bind('click',function(){
        viewController.createReservation.call(viewController);
    });
    
    $('[name$="Date"]').bind('change',function(){
        var start = $('[name="startDate"]');
        var end = $('[name="endDate"]');
        var startDate = start.datepicker("getDate");
        var endDate = end.datepicker("getDate");
        
        if(endDate<startDate && $(this).attr('name') == 'startDate')
            end.datepicker("setDate", startDate );
        else if(endDate<startDate && $(this).attr('name') == 'endDate')
            start.datepicker("setDate", endDate );
    });
    
    $('[name="startTimeHour"]').bind('change',function(){
        var value = parseInt($(this).val())+1;
        $('[name="endTimeHour"]').val( value );
    });
    
    $('[name="startTimeMinute"]').bind('change',function(){
        $('[name="endTimeMinute"]').val($(this).val());
    });
    
    $('[name^="endTime"]').bind('change',function(){
        $('[name^="startTime"]').unbind('change');
    });
};
