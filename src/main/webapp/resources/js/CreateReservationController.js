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
                var success = function(){
                    Notifications.showMessage("create_reservation_ok");
                };
                
                ReservationNavigation.goToWeek(response.start, success);
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
    
    $('[name="submit"]').bind('click',function(){
        viewController.createReservation.call(viewController);
    });
};
