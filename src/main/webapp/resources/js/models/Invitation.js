
var Invitation = function(user, state, reservationId){
    this.guest = user;
    this.state = (state) ? state : Invitation.states.new;
    this.reservation = new Object();
    this.reservation.id = reservationId;
};

Invitation.states = new Object();
Invitation.states.new = '';
Invitation.states.waiting = 'WAITING';
Invitation.states.rejected = 'REJECTED';
Invitation.states.accepted = 'ACCEPTED';

// serializa a JSON
Invitation.prototype.serialize = function(){
    return JSON.stringify(this);
};

Invitation.getInvitations = Ajax.requestInUrl("GET", "/Reservas/invitations/", 'json');
Invitation.changeStateInvitation = Ajax.requestInUrl("PUT", "/Reservas/invitations/");