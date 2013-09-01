var ViewReservationController = function(){
    $('a.invitation').click(function(){
        var state = $(this).attr('state');
        var id = $(this).attr('reservationId');
        Invitation.changeStateInvitation(
            id+'/'+state,
            function(changed){
                $('li[state="'+state+'"]').hide();
                $('li[state!="'+state+'"]').show();
                    var label = $('span.invitation_label');
                    label.text(label.attr(state));
                var row = $('tr[uniqueName="'+changed.guest.uniqueName+'"]');
                row.find('td[class^=icon-invitation-]').attr('class','icon-invitation-'+state);
                },
                ajaxError
        );
    });

    $('[name="delete"]').click(function(e) {
        e.stopPropagation();
        
        var id = $(this).attr('reservationId');
        var URL = applicationPath + "reservations/" + id;
        
        var redirectURL = applicationPath + "reservations";

        Reservation.deleteReservation(
                id,
                function(response) {
                    var success = function(){
                        Notifications.showMessage("delete_reservation_ok");
                    };
                    
                    ReservationNavigation.goToWeek(undefined, success);
                },ajaxError
        );
    });
};