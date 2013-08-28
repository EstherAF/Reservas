
var Resource = function(id, name, description, idGroup){
    this.id = id;
    this.name = name;
    this.description = description;
    if(idGroup != undefined){
        this.group = new Object();
        this.group.id = idGroup;
    }
};

Resource.prototype.setGroup = function(idGroup){
    this.group = new Object();
    this.group.id = idGroup;
};
// serializa a JSON
Resource.prototype.serialize = function(){
    return JSON.stringify(this);
};
/*AJAX*/
Resource.serializeResourceToTree = function(resource){
    return {
        "attr": {"id": resource.id,
            "name": resource.name,
            "description": resource.description,
            "rel": "group"},
        "data": resource.name,
        "children": []
    };
};

Resource.serializeNodeToResource = function(data){
    var resource = new Resource(data.attr('id'), data.attr('name'), data.attr('description'));
    if(data.attr('rel') === "resource")
        resource.setGroup('');
    
    return resource;
    
};

Resource.serializeListToTree = function(resources){
    var jsonTree = [];
    $.each(resources, function(index, elem) {
        //Default group
        if (index === 0) {
            $.each(elem.resources, function(index2, elem2) {
                var res = Resource.serializeResourceToTree(elem2);
                res.attr.rel = "resource";
                jsonTree[index2] = res;
            });
        } else {
            var res = Resource.serializeResourceToTree(elem);
            res.attr.id = "g" + res.attr.id;
            if (elem.resources !== null && elem.resources.length !== 0) {
                var child;
                $.each(elem.resources, function(index2, elem2) {
                    child = Resource.serializeResourceToTree(elem2);
                    child.attr.rel = "resource";
                    res.children[index2] = child;
                });
            }
            jsonTree[jsonTree.length] = res;
        }
    });
    return jsonTree;
};

Resource.getResourceSync = Ajax.requestInUrl("GET", "/Reservas/resources/", "sync");
Resource.getResource = Ajax.requestInUrl("GET", "/Reservas/resources/");
Resource.getGroup = Ajax.requestInUrl("GET", "/Reservas/resources/groups/");
Resource.createGroup = Ajax.requestInBody("POST", "/Reservas/resources/groups/");
Resource.createResource = Ajax.requestInBody("POST", "/Reservas/resources/");
Resource.updateGroup = Ajax.requestInBody("PUT", "/Reservas/resources/groups/");
Resource.updateResource = Ajax.requestInBody("PUT", "/Reservas/resources/");
Resource.deleteResource = Ajax.requestInUrl("DELETE", "/Reservas/resources/");
Resource.deleteGroup = Ajax.requestInUrl("DELETE", "/Reservas/resources/groups/");
Resource.deleteAllGroup = Ajax.requestInUrl("DELETE", "/Reservas/resources/groups/");
Resource.getAllByGroup = function(success_cb, error_cb){
    var request = Ajax.requestInUrl("GET", "/Reservas/resources/groups/");
    request("", success_cb, error_cb);
};