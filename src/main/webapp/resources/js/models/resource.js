
Resource = function(id, name, description) {
    this.id = id;
    this.name = name;
    this.description = description;
};
Resource.setGroup = function(resource, id) {
    resource.group = new Object();
    resource.group.id = id;
};
// serializa a JSON
Resource.prototype.serialize = function()
{
    return JSON.stringify(this);
};
/*AJAX*/
Resource.getAllByGroup = function(success_cb, error_cb)
{
    request = ajaxRequestInUrl("GET", "/Reservas/resources/groups/");
    request("", success_cb, error_cb);
};

Resource.getResourceSync = sjaxRequestInUrl("GET", "/Reservas/resources/");
Resource.getResource = ajaxRequestInUrl("GET", "/Reservas/resources/");
Resource.getGroup = ajaxRequestInUrl("GET", "/Reservas/resources/groups/");
Resource.createGroup = ajaxRequestInBody("POST", "/Reservas/resources/groups/");
Resource.createResource = ajaxRequestInBody("POST", "/Reservas/resources/");
Resource.updateGroup = ajaxRequestInBody("PUT", "/Reservas/resources/groups/");
Resource.updateResource = ajaxRequestInBody("PUT", "/Reservas/resources/");

Resource.serializeListToTree = function(resources)
{
    jsonTree = [];
    $.each(resources, function(index, elem) {
        //Default group
        if (index === 0) {
            $.each(elem.resources, function(index2, elem2) {
                res = Resource.serializeResourceToTree(elem2);
                res.attr.rel = "resource";
                jsonTree[index2] = res;
            });
        } else {
            res = Resource.serializeResourceToTree(elem);
            res.attr.id = "g" + res.attr.id;
            if (elem.resources !== null && elem.resources.length !== 0) {
                index2 = 0;
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
Resource.serializeResourceToTree = function(resource)
{
    return {
        "attr": {"id": resource.id,
            "name": resource.name,
            "description": resource.description,
            "rel": "group"},
        "data": resource.name,
        "children": []
    };
};