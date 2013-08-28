var ViewReservationController = function() {
    $('a.invitation').click(function() {
        var state = $(this).attr('state');
        var id = $(this).attr('reservationId');
        Invitation.changeStateInvitation(
                id + '/' + state,
                function(changed) {
                    $('li[state="' + state + '"]').hide();
                    $('li[state!="' + state + '"]').show();
                    var label = $('span.invitation_label');
                    label.text(label.attr(state));
                    var row = $('tr[uniqueName="' + changed.guest.uniqueName + '"]');
                    row.find('td[class^=icon-invitation-]').attr('class', 'icon-invitation-' + state);
                },
                function(error) {
                    Notifications.showError(error);
                }
        );
    });

    $('[name="delete"]').click(function() {
        var id = $(this).attr('reservationId');
        var URL = aplicationPath + "reservations/" + id;

        Reservation.delete(
                id,
                function() {
                    $("html").load(
                            URL,
                            function() {
                                Notifications.showMessage("delete_resource_ok");
                                window.history.pushState(null, null, URL);
                            });
                },
                function(error) {
                    Notifications.showError(error);
                }
        );
    });
};