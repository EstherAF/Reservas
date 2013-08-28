/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var operation = 'create';
var changed = false;
var idResource = 0;
var selected=null;

createResource = function(resource) {
    if (!resource.group) {
        //Is group
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
    } else {
        Resource.createResource(resource,
                function(response) {
                    response.group=new Object();
                    response.group.id=resource.group.id;
                    addResourceNode(response);
                },
                function() {
                    alert("fail");
                });

    }
};

updateResource = function(resource) {
    if (resource.group) {
        //Pertenece a un grupo: recurso
        Resource.updateResource(resource,
                function(response) {
                    alert('chachi resource');
                },
                function() {
                    alert("fail");
                });
    } else {
        resource = Resource.updateGroup(resource,
                function(response) {
                    alert('chachi group');
                },
                function() {
                    alert("fail");
                });
    }
};

var generateSelectorGroup = function(resources) {
    selector = $('select[name="group"]');
    $.each(resources, function(index, element) {
        html = "<option value=" + element.id + ">" + element.name + "</option>";
        selector.append(html);
    });
};

changeInterface = function(op) {
    if (operation !== op) {
        //Cambio del título
        header = $('#content > header > h1');
        title = header.attr(op);
        header.text(title);

        //Cambio del botón
        button = $('form[name="new_resource"] [type="button"][name="submit"]');
        value = button.attr(op);
        button.val(value);

        operation = op;
    }
    
    b=$('form[name="new_resource"] [type="button"][name="delete"]');
    if(op=='update'){
        b.css('display','inline-block');
        b.val(b.attr('delete'));
    }else{
        b.css('display','none');
    }
    
};

loadCreateResourceInterface = function() {
    
    //Borrar formularios
    $('input').val('');
    $('input[type="radio"]').removeAttr('disabled');
    $('input[value="true"][name="is_group"]').attr("checked", true);
    $('select[name="group"] > option[value="' + 1 + "\"]").attr('selected', true);
    
    changeInterface('create');
    deselectNode();
    
};

loadUpdateResourceInterface = function(resource) {
    changeInterface('update');

    //name
    $('input[name="resourceName"]').val(resource.attr('name'));

    //description
    $('textarea[name="description"]').val(resource.attr('description'));

    //Type[group, resource]
    if (resource.attr('rel') === 'group') {
        $('input[value="true"][name="is_group"]').attr("checked", true);
        $('#group_selector').css('display', 'none');
        idResource = resource.attr('id').substr(1);
    } else if (resource.attr('rel') === 'resource') {
        $('input[value="false"][name="is_group"]').attr("checked", true);
        $('#group_selector').css('display', 'block');
        idResource = resource.attr('id');
        parentId = resource.parents("li").attr('id');
        if (!parentId) {
            parentId = 1;
        } else {
            parentId = parentId.substr(1);
        }
        $('select[name="group"] > option[value="' + parentId + "\"]").attr('selected', true);
    }
    $('input[type="radio"]').attr('disabled', 'disabled');
};

$(function() {
    createTree(resources);
    generateSelectorGroup(resources);

    $('input[name="is_group"]').click(function() {
        is_group = $(this).attr('value');
        element = $('#group_selector');
        if (is_group === "true") {
            element.css("display", "none");
        } else {
            element.css("display", "block");
        }
    });

    $('input[name="submit"]').click(function() {
        name = $('input[name="resourceName"]').val();
        description = $('textarea[name="description"]').val();
        resource = new Resource(idResource, name, description);

        is_group = $('input[name="is_group"]:checked').val();

        if (is_group == "false") {
            id_group = $('select[name="group"]').val();
            Resource.setGroup(resource, id_group);
        } else {
            resource.resources = [];
        }

        if (operation == 'create') {
            createResource(resource);
        } else if (operation == 'update') {
            updateResource(resource);
        }
        
        updateNode(selected, resource);
    });

    //Botón Añadir recurso
    $('#create_resource').click(function() {
        loadCreateResourceInterface();
    });

    tree.bind("select_node.jstree", function(event, data) {
        selected = data.rslt.obj;
        loadUpdateResourceInterface(data.rslt.obj);
    });

});
