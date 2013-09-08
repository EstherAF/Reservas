
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