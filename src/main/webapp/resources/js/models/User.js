
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

//Resource.getResourceSync = Ajax.requestInUrl("GET", "/resources/", "sync");
//Resource.getResource = Ajax.requestInUrl("GET", "/resources/");
//Resource.getGroup = Ajax.requestInUrl("GET", "/resources/groups/");
//Resource.createGroup = Ajax.requestInBody("POST", "/resources/groups/");
//Resource.createResource = Ajax.requestInBody("POST", "/resources/");
//Resource.updateGroup = Ajax.requestInBody("PUT", "/resources/groups/");
//Resource.updateResource = Ajax.requestInBody("PUT", "/resources/");
//Resource.deleteResource = Ajax.requestInUrl("DELETE", "/resources/");
//Resource.deleteGroup = Ajax.requestInUrl("DELETE", "/resources/groups/");
//