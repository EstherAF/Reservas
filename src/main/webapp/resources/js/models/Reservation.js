/*---------------Model of Reservation--------------*/
var Reservation = function(id, name, description, start, end, type, interval, endDate, weekDays) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.start = start;
    this.end = end;
    this.repetition = new Object();
    this.repetition.interval = interval;
    this.repetition.endDate = endDate;
    this.repetition.type = type;
    this.repetition.weekDays = (weekDays) ? weekDays : [];
    this.resources = [];
    this.invitations = [];
};
// serializa a JSON
Reservation.prototype.addResource = function(resource) {
    this.resources.push(resource);
};

Reservation.prototype.serialize = function() {
    return JSON.stringify(this);
};

/*---------------Model of NewReservation, or ReservationCustom--------------*/
var NewReservation = function(id, name, description, start, end, type, interval, endDate, weekDays) {
    Reservation.call(this, id, name, description, start, end, type, interval, endDate, weekDays);
    this.groups = [];
};

NewReservation.prototype = Object.create(Reservation.prototype);

NewReservation.prototype.addReservedResource = function(reservedResource) {
    if (!reservedResource.quantity) {
        this.addResource(reservedResource.resource);
    } else {
        if (reservedResource.resource.id[0] == 'g')
            reservedResource.resource.id = reservedResource.resource.id.substr(1);
        this.groups.push(reservedResource);
    }
};

NewReservation.prototype.setResources = function(resources) {
    for (var i = 0; i < resources.length; i++) {
        var reservedResource = resources[i];
        this.addReservedResource(reservedResource);
    }
};

NewReservation.prototype.serialize = function() {
    return JSON.stringify(this);
};


/*---------------AjaxFunctions--------------*/
NewReservation.createReservation = Ajax.requestInBody("POST", "/Reservas/reservations/");
NewReservation.updateReservation = Ajax.requestInBody("PUT", "/Reservas/reservations/");

Reservation.getReservationSync = Ajax.requestInUrl("GET", "/Reservas/reservations/", "sync");
Reservation.getReservation = Ajax.requestInUrl("GET", "/Reservas/reservations/");
Reservation.deleteReservation = Ajax.requestInUrl("DELETE", "/Reservas/reservations/");
Reservation.loadUpdateReservationView = Ajax.requestInUrl("PUT", "/Reservas/reservations/", "html");
Reservation.getOwnReservationsByMonth = function(year, month, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/Reservas/reservations/owns/month/", "json")(year+'/'+month, success_cb, error_cb);
};
Reservation.getReservationsByMonth = function(year, month, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/Reservas/reservations/month/", "json")(year+'/'+month, success_cb, error_cb);
};

Reservation.getReservationsByMonthResource = function(year, month, resourceId, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/Reservas/reservations/month/", "json")(year+'/'+month+'/resource/'+resourceId, success_cb, error_cb);
};

Reservation.getReservationsByWeekResource = function(year, month, resourceId, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/Reservas/reservations/week/", "json")(year+'/'+month+'/resource/'+resourceId, success_cb, error_cb);
};

Reservation.delete = function(id, success_cb, error_cb){
    Ajax.requestInUrl("DELETE", "/Reservas/reservations/", "json")(id, success_cb, error_cb);
};
    
    
    