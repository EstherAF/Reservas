NavigationBar = function() {
    NavigationBar.invitationMenu();
    NavigationBar.invitationOnReservationView();
    NavigationBar.bindReturn();
};

NavigationBar.dropDownWrapper = '.dropdown_wrapper';


NavigationBar.invitationMenu = function() {

    var htmlRowOfMenu = function(invitation) {

        var row = $('<li></li>');

        var reservationLink = $('<a></a>').attr({
            'class': 'text',
            'href': '/reservas/reservations/' + invitation.reservation.id
        });
        var reservationDescription = invitation.reservation.name + ' ';
        var reservationOwner = $('<span></span>').css('font-style', 'italic').
                append('(' + invitation.reservation.owner + ')');

        reservationLink.append(reservationDescription);
        reservationLink.append(reservationOwner);
        row.append(reservationLink);

        var acceptButton = $('<span></span>').attr({
            'class': 'btn link',
            'reservationId': invitation.reservation.id,
            'state': 'ACCEPTED'
        });
        acceptButton.append($('.i18n.invitations').attr('accept'));
        row.append(acceptButton);

        var rejectButton = $('<span></span>').attr({
            'class': 'btn link',
            'reservationId': invitation.reservation.id,
            'state': 'REJECTED'
        });
        rejectButton.append($('.i18n.invitations').attr('reject'));
        row.append(rejectButton);

        return row;
    };

    var resolveInvitation = function() {
        var id = $(this).attr('reservationId');
        var state = $(this).attr('state');

        var row = this.parentNode;

        Invitation.changeStateInvitation(id + '/' + state,
                Dropdown.removeFromList(row),
                function(e) {
                    console.log(e);
                }
        );
    };

    Invitation.getInvitations('',
            function(response) {
                //create DropDown menu
                Dropdown.create(NavigationBar.dropDownWrapper, response, htmlRowOfMenu);

                //bind accept and reject events
                $('.dropdown_wrapper > .dropdown_child').on('click', 'li > .btn', resolveInvitation);
            },
            ajaxError
    );
};

NavigationBar.invitationOnReservationView = function() {

    $('html').on('click', '.btn.invitation', function() {
        e.stopPropagation();
        
        var id = $(this).attr('reservationId');
        var state = $(this).attr('state');

        var row = this.parentNode;
        var list = row.parentnode;

        Invitation.changeStateInvitation(id + '/' + state,
                function() {

                    row.remove();
                    list.find('[state!="' + state + '"]').each(function(index, element) {
                        element.show();
                    });
                    Dropdown.removeFromList('[reservationId="' + id + '"]');
                    var label = $('.invitation_label');
                    label.text(label.attr(state));

                },
                function(e) {
                    console.log(e);
                }
        );
    });
};

NavigationBar.bindReturn = function(){
    $('html').on('click', '[name="return"]', function(e, a){
        window.history.back();
        return false;
    });
};