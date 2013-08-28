var ResourcePopUp = new Object();

ResourcePopUp.buildResourceDialog = function(selected) {
    var i18n = $('#i18n');
    var title_i18n = i18n.attr('title');
    var name_i18n = i18n.attr('name');
    
    var description_i18n = i18n.attr('description');
    var selector_i18n = "Selecciona las unidades que deseas";

    var id = selected.attr('id');
    var description = selected.attr('description');
    var type = selected.attr('type');
    var name = selected.text().split('(')[0];

    var html = '<div><p><span>' + name_i18n + '</span>' + name + '</p>';
    if (description) {
        html += '<p><span>' + description_i18n + '</span>' + description + '</p>';
    }
    if (type === 'group_popup') {
        //Create selector
        var max_number = selected.children.length;
        var selector = $("<select></select>").attr({
            "id": id,
            "class": "resource_group_selector"
        });
        
        var quantity = selected.attr('quantity');
        if(!quantity)
            quantity = 1;
        for (var i = 1; i <= max_number; i++) {
            var option = $("<option/>", {value: i, text: i});
            if (i == quantity)
                option.attr('selected', "selected");
            option.appendTo(selector);
        }

        html += '<p>' + selector_i18n + '</p>';
        html += '<p>' + selector[0].outerHTML + '</p>';
    }

    html += '</div>';

    return html;
};

ResourcePopUp.buildGroupDialog = function(selected) {
    var i18n = $('#i18n');
    var title_i18n = i18n.attr('title');
    var name_i18n = i18n.attr('name');
    var description_i18n = i18n.attr('description');
    var selector_i18n = "Selecciona las unidades que deseas";

    var id = selected.attr('id');
    var description = selected.attr('description');
    var name = selected.attr('name');
    var max_number = selected.children.length;

    //Create selector
    var selector = $("<select></select>").attr({
        "id": id,
        "class": "resource_group_selector"
    });
    for (var i = 1; i <= max_number; i++) {
        var option = $("<option/>", {value: i, text: i});
        if (i === 1)
            option.attr('selected', "selected");
        option.appendTo(selector);
    }


    var html = '<div><p><span>' + name_i18n + '</span>' + name + '</p>';
    if (description) {
        html += '<p><span>' + description_i18n + '</span>' + description + '</p>';
    }
    html += '<p><span>' + selector_i18n + '</span>' + selector + '</p>';

    html += '</div>';

    return html;
};


ResourcePopUp.htmlResourcePop = function(reservedResource) {
    var resource = reservedResource.resource;
    
    var type = (resource.group) ? 'resource' : 'group';

    html = $('<span></span>').attr({
        class: 'link underline popup',
        type: type + '_popup',
        id: resource.id,
        description: resource.description
    });
    
    
    html.text(resource.name);
    if(type === 'group'){
        var quantity = (reservedResource.quantity) ? reservedResource.quantity : 1;
        html.attr('quantity',quantity);
        html.append('<span style="font-style:italic"> ('+quantity+")</span>");
    }
    
    return html;
};

ResourcePopUp.buildPopUps = function(element) {
    element.popover({
        html: true,
        trigger: 'click',
        content: function() {
            return ResourcePopUp.buildResourceDialog($(this));
        },
        title: $('#i18n').attr('title')
    });
};

ResourcePopUp.buildGroupPopUps = function(element) {
    element.popover({
        html: true,
        trigger: 'manual',
        content: function() {
            return ResourcePopUp.builResourceDialog($(this));
        },
        title: $('#i18n').attr('title')
    });
};


ResourcePopUp.onLoad = function() {
    var popups = $(".popup");
    var groupPopups = $('#resourcesTree > ul > li[rel="group"] > a > ins.jstree-checkbox');
    ResourcePopUp.buildPopUps(popups);
    ResourcePopUp.buildGroupPopUps(groupPopups);
    $('html').on('click', function(e) {
        $(".popup").each(function() {
            if (!$(this).is(e.target) && 
                !$('ins.jstree-checkbox').is(e.target.firstChild)  && 
                    $(this).has(e.target).length === 0 && 
                    $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
                return;
            }
        });
    });
};

