UpdateReservationController = function(users, resources, reservation) {
    //parent
    ReservationController.call(this, users, resources, reservation.resources);

    this.initialGuests = reservation.invitations;
    this.guests = clone(this.initialGuests);
    this.reservation = reservation;

};

//Inheritance from ReservationController
UpdateReservationController.prototype = Object.create(ReservationController.prototype);

UpdateReservationController.prototype.loadDefaultData = function(form) {
    var reservation = this.reservation;
    var form = $(form);
    form.find('[name="name"]').val(reservation.name);
    form.find('[name="description"]').val(reservation.description);

    var startDate = reservation.start;
    var endDate = reservation.end;
    if (!(startDate instanceof Date)) {
        startDate = new Date(startDate);
        endDate = new Date(endDate);
    }

    form.find('[name="startDate"]').datepicker("setDate", startDate);
    form.find('[name="endDate"]').datepicker("setDate", endDate);
    form.find('[name="startTimeHour"] > option[value="' + startDate.getHours() + '"]').prop('selected', true);
    form.find('[name="endTimeHour"] > option[value="' + endDate.getHours() + '"]').prop('selected', true);
    form.find('[name="startTimeMinute"] > option[value="' + startDate.getMinutes() + '"]').prop('selected', true);
    form.find('[name="endTimeMinute"] > option[value="' + endDate.getMinutes() + '"]').prop('selected', true);

    //Repetition
    var type;
    if (reservation.repetition.type === Repetition.type.monthly_relative)
        type = Repetition.type.monthly;
    else
        type = reservation.repetition.type;

    form.find('[name="repetition_type"] > option[value="' + type + '"]').prop('selected', true);
    if (reservation.repetition.type !== Repetition.type.once) {
        $('.repetition').show();
        var repetition = reservation.repetition;
        form.find('[name="interval"]').val(repetition.interval);
        form.find('[name="endRepetitionDate"]').datepicker('setDate', new Date(repetition.endDate));
        var textInterval = $("#textInterval");
        textInterval.html("");
        if (repetition.type === Repetition.type.weekly) {
            $('#weekly_repetition').show();
            textInterval.text(textInterval.attr('weekly'));
            repetition.weekDays.forEach(function(day) {
                form.find('[name="weekly_days"][value="' + day + '"]').prop('checked', true);
            });
        } else if (repetition.type !== Repetition.type.daily) {
            textInterval.text(textInterval.attr('monthly'));
            $('#monthly_repetition').show();
            form.find('[name="monthlyRepetition"][value="' + repetition.type + '"]').prop('checked', true);
        } else {
            textInterval.text(textInterval.attr('daily'));
        }
    }

    //Invitations
    var invitations = this.reservation.invitations;
    for (var i = 0; i < invitations.length; i++) {
        this.addGuest(invitations[i]);
    }
    
    //Resources
    for(var i=0; i< this.reservation.resources; i++){
        var node = $("#resourcesTree ul li #"+this.reservation.resources[i].id);
        this.resouceTree.toggleCheckBox(node);
    }
};

UpdateReservationController.prototype.loadButtons = function() {
    var submitBtn = $('[name="submit"]');
    submitBtn.val(submitBtn.attr('update'));

    var deleteBtn = $('[name="delete"]');
    deleteBtn.show();
};

UpdateReservationController.prototype.addGuestUpdating = function() {
    $("[for=errorGuestList]").hide();

    var user = this.autoComplete.getSelected();
    if (!user) {
        $(".errorGuestList").show();
    } else {
        var invitation = new Invitation(user);

        //Did it exist before?
        if (this.initialGuests.length > 0) {
            var index = this.initialGuests.search('guest.uniqueName', user.uniqueName);
            var state = (index) ? this.initialGuests[index].state : undefined;
            invitation.state = state;
        }

        //Create new row
        this.addGuest(invitation);
    }
};

UpdateReservationController.prototype.updateReservation = function() {
    var reservation = this.getReservationFromForm();
    if (reservation) {
        reservation.owner = this.reservation.owner;
        reservation.id = this.reservation.id;
        NewReservation.updateReservation(
                reservation,
                function(response) {
                    ReservationNavigation.goToWeek(response.start,
                            function() {
                                Notifications.showMessage(Notifications.messages[locale]["update_reservation_ok"]);
                            });
                },
                ajaxError
        );
    }
};

//TO-DO
UpdateReservationController.prototype.removeReservation = function() {
    var reservation = this.getReservationFromForm();
    if (reservation) {
        NewReservation.updateReservation(
                reservation,
                function(response) {
                    ReservationNavigation.goToWeek(response.start);
                },
                function(error) {
                    Notifications.showError(error);
                }
        );
    }
};


//On load
UpdateReservationController.onLoad = function(users, resources, reservation) {
    var viewController = new UpdateReservationController(users, resources, reservation);
    ReservationController.load.call(viewController, viewController.resourceTree, viewController.addGuestUpdating);
    viewController.loadDefaultData(viewController.form.form.selector, reservation);
    viewController.loadButtons();

    $('[name="submit"]').click(function() {
        viewController.updateReservation.call(viewController);
    });

    $('[name="delete"]').click(function() {
        viewController.updateReservation.call(viewController);
    });
    viewController.refreshHTMLResourcesList();
};