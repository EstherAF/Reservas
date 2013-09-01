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
    
    if(weekDays || this.repetition.type=="WEEKLY")
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
NewReservation.createReservation = Ajax.requestInBody("POST", "/reservations/");
NewReservation.updateReservation = Ajax.requestInBody("PUT", "/reservations/");

Reservation.getReservationSync = Ajax.requestInUrl("GET", "/reservations/", "sync");
Reservation.getReservation = Ajax.requestInUrl("GET", "/reservations/");
Reservation.deleteReservation = Ajax.requestInUrl("POST", "/reservations/delete/", "json");
Reservation.loadUpdateReservationView = Ajax.requestInUrl("PUT", "/reservations/", "html");
Reservation.getOwnReservationsByMonth = function(year, month, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/reservations/owns/month/", "json")(year+'/'+month, success_cb, error_cb);
};
Reservation.getReservationsByMonth = function(year, month, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/reservations/month/", "json")(year+'/'+month, success_cb, error_cb);
};

Reservation.getReservationsByMonthResource = function(year, month, resourceId, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/reservations/month/", "json")(year+'/'+month+'/resource/'+resourceId, success_cb, error_cb);
};

Reservation.getReservationsByWeekResource = function(year, month, resourceId, success_cb, error_cb){
    Ajax.requestInUrl("GET", "/reservations/week/", "json")(year+'/'+month+'/resource/'+resourceId, success_cb, error_cb);
};
//
//Reservation.deleteReservation = function(id, success_cb, error_cb){
//    Ajax.requestInUrl("DELETE", "/reservations/", "json")(id, success_cb, error_cb);
//};
    