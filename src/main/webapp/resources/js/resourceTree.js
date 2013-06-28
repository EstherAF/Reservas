var jsonTree = [];
var tree;

createCheckTree = function(resources) {
    jsonTree = Resource.serializeListToTree(resources);

     tree = $("#resourcesTree").jstree({
        "themes": {
            "icons": true
        },
        "json_data": {
            "data": jsonTree
        },
        "plugins": ["themes", "json_data", "ui", "checkbox", "crrm", "types"]
    });
};

createTree = function(resources) {
    jsonTree = Resource.serializeListToTree(resources);

     tree = $("#resourcesTree").jstree({
        "themes": {
            "icons": true
        },
        "json_data": {
            "data": jsonTree
        },
        "plugins": ["themes", "json_data", "ui", "crrm", "types"]
    });
};

addResourceNode = function(resource) {
    parent = "#g" + resource.group.id;
    data = Resource.serializeResourceToTree(resource);
    data.attr.rel = "resource";
    $("#resourcesTree").jstree("create", parent, "last", data, false, true);
};

addGroupNode = function(resource) {
    data = Resource.serializeResourceToTree(resource);
    $("#resourcesTree").jstree("create", "#resourcesTree", "last", data, false, true);
};

updateNode = function(node,resource){
    node.attr('name',resource.name);
    node.attr('description',resource.description);
};

deselectNode=function(){
    tree.jstree("deselect_node", selected);
}

