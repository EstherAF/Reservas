var ResourceTree = function(resources, option, prechecked) {
    //var self = this;
    this.tree;

    if (resources != null) {
        jsonTree = Resource.serializeListToTree(resources);

        var jstree = {"themes": {"icons": true},
            "json_data": {"data": jsonTree},
            "plugins": ["themes", "json_data", "ui", "crrm", "types", "sort"],
            "types": {
                "type_attr": "type_jstree",
                "types": {
                    "closed": {"open_node": false},
                    "no_select": {"select_node": false}
                }
            }
        };

        switch (option) {
            case 'multiselect':
                jstree.ui = {"select_multiple_modifier": "on"};

                $("#resourcesTree").bind("before.jstree", function(e, data) {
                    if (data.func === "select_node")
                        if (!data.inst.is_leaf(data.args[0]) || $(data.args[0]).parent().attr('rel')==='group')
                        {
//                            data.inst.toggle_node(data.args[0]);
                            $('#resourcesTree').jstree('toggle_node', data.args[0]);
                            e.preventDefault();
                            return false;
                        }
                });

                break;

            case 'checkbox':
                jstree.plugins.push("checkbox");
                //jstree.checkbox={"two_state": true}
                jstree.types.types.default = {"select_node": false, "hover_node": false};

                //Pre
                for (var i = 0; i < prechecked.length; i++) {
                    var node;
                    var index = jsonTree.search('attr.id', prechecked[i].resource.id);
                    if (!index) {
                        for (var j = 0; j < jsonTree.length; j++) {
                            var childrens = jsonTree[j].children;
                            if (childrens.length > 0) {
                                var index = childrens.search('attr.id', prechecked[i].resource.id);
                                if (index) {
                                    node = childrens[index];
                                    jsonTree[j].attr.class = "jstree-open";
                                }
                            }
                        }
                    } else
                        node = jsonTree[index];

                    node.attr.class = "jstree-checked";
                }
                break;
        }

        this.tree = $("#resourcesTree").jstree(jstree);
    }

};

ResourceTree.prototype.addResourceNode = function(resource) {
    var parent = "#g" + resource.group.id;
    var data = Resource.serializeResourceToTree(resource);
    data.attr.rel = "resource";

    var position = (parent === '#g1') ? "inside" : "last";

    this.tree.jstree("create", parent, position, data, false, true);
};

ResourceTree.prototype.addGroupNode = function(resource) {
    var data = Resource.serializeResourceToTree(resource);
    this.tree.jstree("create", "#resourcesTree", "last", data, false, true);
};

ResourceTree.prototype.updateNode = function(resource) {
    var node = this.getSelectedNode();
    node.attr('name', resource.name);
    node.attr('description', resource.description);
    this.tree.jstree('set_text', node, resource.name);
};

ResourceTree.prototype.deselectNode = function() {
    this.tree.jstree("deselect_node", this.getSelectedNode());
};

ResourceTree.prototype.removeNode = function() {
    this.tree.jstree("remove", this.getSelectedNode());
};

ResourceTree.prototype.getSelectedNode = function() {
    return this.tree.jstree('get_selected');
};

ResourceTree.prototype.getSelectedNodeParent = function() {
    var parent = this.getSelectedNode().parents('li[id^="g"]');
    return parent;
};

ResourceTree.prototype.toggleCheckBox = function(node) {
    var is_checked = this.tree.jstree('is_checked', node);
    if (is_checked)
        this.tree.jstree('uncheck_node', node);
    else
        this.tree.jstree('check_node', node);
};

ResourceTree.prototype.getNode = function(atributeName, atributeValue) {
    return this.tree.find('li[' + atributeName + '="' + atributeValue + '"]');
};