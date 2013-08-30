
/*---------------Model of ReservedResource--------------*/
var ReservedResource = function(resource, quantity) {
    this.resource = resource;
    if (!this.resource.group) {
        this.quantity = (quantity) ? quantity : 1;
    }
};

ReservedResource.prototype.setQuantity = function(quantity) {
    this.quantity = quantity;
};

ReservedResource.fromResourceList = function(resourceList) {
    var list = [];
    for(var i = 0; i<resourceList.length; i++){
        var push = new ReservedResource(resourceList[i]);
        delete push.quantity;
        push.resource.group = new Object();
        list.push(push);
    }
    
    return list;
};