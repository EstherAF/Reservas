/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var resourcesView = function(resources) {
    var self = this;

    var operation = 'create';
    this.resTree;

    this.updateResourceForm = new GenericForm(
            {
                selector: "form[name='new_resource']",
                properties: [
                    {name: 'name',
                        selector: "input[name='name']",
                        selectorError: "p[for='name']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'max_length', args: [100]},
                            {type: 'min_length', args: [1]}
                        ]
                    },
                    {name: 'description',
                        selector: "textarea[name='description']",
                    }
                ]
            }
    );

    this.createResourceForm = new GenericForm(
            {
                selector: "form[name='new_resource']",
                properties: [
                    {name: 'name',
                        selector: "input[name='name']",
                        selectorError: "p[for='name']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'max_length', args: [100]},
                            {type: 'min_length', args: [1]}
                        ]
                    },
                    {name: 'description',
                        selector: "textarea[name='description']",
                    },
                    {name: 'is_group',
                        selector: "input[name='is_group']:checked",
                        selectorError: "p[for='is_group']",
                        restrictions: [
                            {type: 'selected_required'},
                        ]
                    },
                    {name: 'group',
                        selector: "select[name='group']"
                    },
                    {name: 'quantity',
                        selector: "input[name='quantity']",
                        selectorError: "p[for='quantity']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'numeric'},
                            {type: 'ge', args: [1]}
                        ]
                    }
                ]
            }
    );

    /*PRIVATE*/
    var cleanMessages = function(){
        self.updateResourceForm.removeErrors();
        self.createResourceForm.removeErrors();
    };

    var generateSelectorGroup = function(resources) {
        var selector = $('select[name="group"]');
        selector.empty();
        $.each(resources, function(index, element) {
            if (element.name === 'default')
                element.name = '';

            var html = "<option value=" + element.id + ">" + element.name + "</option>";
            selector.append(html);
        });
    };

    var changeInterface = function(op, newTypeResource) {
        //Cambio del título
        cleanMessages();
        var header = $('#content > header > h1');
        var name_attr = op;
        if (op === 'update')
            name_attr += newTypeResource;

        var title = header.attr(name_attr);
        header.text(title);

        $("#group_selector").hide();


        if (operation !== op) {
            //Cambio del botón
            var buttonSubmit = $('[type="button"][name="submit"]');
            var value = buttonSubmit.attr(op);
            buttonSubmit.text(value);

            var buttonDelete = $('[type="button"][name="delete"]');
            if (op === "update") {
                $("#type_selector").hide();
                buttonDelete.show();
            } else if (op === "create") {
                $("#type_selector").show();
                buttonDelete.hide();
            }
        }
        operation = op;
    };

    var createResource = function(resource) {
        if (!resource.group) {
            //Is group
            delete resource.quantity;
            Resource.createGroup(resource,
                    function(response) {
                        self.resTree.addGroupNode(response);
                        $('select[name="group"]').append($('<option>', {
                            value: response.id,
                            text: response.name
                        }));
                        Notifications.showMessage("create_resource_ok");
                    },ajaxError);
        } else {
            Resource.createResource(resource,
                    function(response) {
                        for(var i=0; i<response.length; i++){
                            response[0].group = new Object();
                            response[0].group.id = resource.group.id;
                            self.resTree.addResourceNode(response[0]);
                        }
                        Notifications.showMessage("create_resource_ok");
                    },ajaxError);
        }
    };

    var updateResource = function(resource) {
        var success = function() {
            //self.resTree.updateNode(resource);
            Notifications.showMessage('update_resource_ok'); 
        };

        if (resource.group) {
            delete resource.quantity;
            Resource.updateResource(resource, function() {
                self.resTree.updateNode(resource);
                Notifications.showMessage("update_resource_ok");
            }, ajaxError);
        } else {
            Resource.updateGroup(resource, function() {
                self.resTree.updateNode(resource);
                Notifications.showMessage("update_resource_ok");
            }, ajaxError);
        }
    };

    /*PUBLIC*/

    this.submit = function() {

        //Create resource
        var name = $('input[name="name"]').val();
        var description = $('textarea[name="description"]').val();
        var id_group = undefined;
        var id = undefined;
        cleanMessages();

        var validated;
        if (operation === "update") {
            validated = self.updateResourceForm.validate();
            if(validated){
                var node = self.resTree.getSelectedNode();
                id = node.attr('id');

                var resourceType = node.attr('rel');

                if (resourceType === "resource") {
                    var group = self.resTree.getSelectedNodeParent();
                    id_group = group.attr('id');
                    id_group = (id_group === undefined) ? 1 : id_group.substr(1);
                } else if (resourceType === "group") {
                    id = id.substr(1);
                }
            }
        } else if (operation === "create") {
            validated = self.createResourceForm.validate();
            if(validated){
                var is_group = validated.is_group;
                var resourceType = (is_group === "true") ? "group" : "resource";

                if (resourceType === "resource") {
                    var id_group = validated.group;
                }
            }
        }


        if(validated){
            var resource = new Resource(id, name, description, id_group);
            if (operation === "create") {
                resource.quantity=validated.quantity;
                createResource(resource);
            } else if (operation === "update") {
                updateResource(resource);
            }
        }
    };

    this.remove = function() {
        var selectedNode = self.resTree.getSelectedNode();
        var id = selectedNode.attr('id');
        var resourceType = selectedNode.attr('rel');

        //var is_group = (id.charAt(1) === 'g') ? true : false;
        var messageName = (resourceType === 'group') ? "confirm_remove_group" : "confirm_remove_resource";
        
        var r = confirm(Notifications.getMessage(messageName));
        if(r){
        
            var success = function() {
                self.resTree.removeNode(selectedNode);
                Notifications.showMessage("delete_resource_ok");
            };

            if (resourceType === "group") {
                id = id.substr(1);
                Resource.deleteGroup(id + '/all', success, ajaxError);
            } else {
                Resource.deleteResource(id, success, ajaxError);
            }
        }
    };

    this.loadCreateResourceInterface = function() {
        //Reset formulario
        $('[name="description"]').val('');  //description
        $('[name="name"]').val('');    //name
        $('[name="is_group"]').removeAttr('checked'); //type resource (group or not)
        $("[name='group'] > option").removeAttr('selected'); //no group selected
        $("[name='group'] > option[value=\'" + 1 + "\']").attr('selected', true);   //select default group
        $('#group_selector').hide();
        $('#quantity').val(1);
        $('#quantity').hide();

        changeInterface("create");
        self.resTree.deselectNode();
    };

    this.loadUpdateResourceInterface = function(resource) {
        //name & description
        $('[name="name"]').val(resource.attr('name'));
        $('[name="description"]').val(resource.attr('description'));

        //Type[group, resource]
        var typeResource = resource.attr('rel');
        changeInterface('update', typeResource);
    };

    /*Constructor*/
    if (resources !== null) {
        self.resTree = new ResourceTree(resources);
        generateSelectorGroup(resources);
    }
};

resourcesView.onLoad= function() {
    var resourceView = new resourcesView(resources);

    //Show group selector?
    $('input[name="is_group"]').change(function() {
        var is_group = $(this).attr('value');
        if (is_group === "true"){
            $('#group_selector').hide();
            $('#quantity').val(1);
            $('#quantity').hide();
        }else{
            $('#group_selector').show();
            $('#quantity').show();
        }
    });

    //Execute insert/update
    $('[name="submit"][type="button"]').click(function() {
        resourceView.submit();
    });

    //Execute delete
    $('[name="delete"][type="button"]').click(function() {
        resourceView.remove();
    });

    //Botón Añadir recurso
    $('#create_resource').click(function() {
        resourceView.loadCreateResourceInterface();
    });

    resourceView.resTree.tree.bind("select_node.jstree", function(event, data) {
        resourceView.loadUpdateResourceInterface(data.rslt.obj);
    });

};
