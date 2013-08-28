
var User = function(uniqueName, fullName, email, group){
    this.uniqueName = uniqueName;
    this.fullName = fullName;
    this.email = email;
    this.role = group;
};

User.prototype.setGroup = function(group){
    this.role = group;
};
// serializa a JSON
User.prototype.serialize = function(){
    return JSON.stringify(this);
};
/*AJAX*/

//Resource.getResourceSync = Ajax.requestInUrl("GET", "/Reservas/resources/", "sync");
//Resource.getResource = Ajax.requestInUrl("GET", "/Reservas/resources/");
//Resource.getGroup = Ajax.requestInUrl("GET", "/Reservas/resources/groups/");
//Resource.createGroup = Ajax.requestInBody("POST", "/Reservas/resources/groups/");
//Resource.createResource = Ajax.requestInBody("POST", "/Reservas/resources/");
//Resource.updateGroup = Ajax.requestInBody("PUT", "/Reservas/resources/groups/");
//Resource.updateResource = Ajax.requestInBody("PUT", "/Reservas/resources/");
//Resource.deleteResource = Ajax.requestInUrl("DELETE", "/Reservas/resources/");
//Resource.deleteGroup = Ajax.requestInUrl("DELETE", "/Reservas/resources/groups/");
//