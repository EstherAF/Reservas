/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var jsonTree = [];

createTree = function(resources) {
    jsonTree = Resource.serializeListToTree(resources);

    $("#resourcesTree").jstree({
        "themes": {
            "icons": true
        },
        "json_data": {
            "data": jsonTree
        },
        "plugins": ["themes", "json_data", "ui", "checkbox", "crrm", "types"],
        "types": {
            "types": {
                "resource": {
                    "valid_children": "none",
                    "icon": {"image": 'null'}
                }
            }
        }
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

createResource = function() {
    name = $('input[name="name"]').val();
    description = $('input[name="description"]').val();
    resource = new Resource(null, name, description);

    is_group = $('input[name="is_group"]:checked').val();

    if (is_group == "false") {
        id_group = $('select[name="group"]').val();
        Resource.setGroup(resource, id_group);
        Resource.createResource(resource,
                function(response) {
                    addResourceNode(response);
                },
                function() {
                    alert("fail");
                });
    } else {
        resource.resources = [];
        resource = Resource.createGroup(resource,
                function(response) {
                    addGroupNode(response);
                    $('select[name="group"]').append($('<option>', {
                        value: response.id,
                        text: response.name
                    }));
                },
                function() {
                    alert("fail");
                });
    }
};