var hiddeRepetitions = function() {
    //Remove required from attributes
    $('#interval').removeAttr('required');
    $('#endDate').removeAttr('required');

    $('p[id$="_repetition"]').css('display', 'none');
    $('.repetition').css('display', 'none');
};

var showRepetitions = function(repetitionType) {
    $('.repetition').css("display", "block");

    //Set required attributes
    $('#interval').attr('required', true);
    $('#endDate').attr('required', true);

    $('p[id$="_repetition"][id!="' + repetitionType + '_repetition"]').css('display', 'none');

    subId = '#' + repetitionType + '_repetition';
    sub = $(subId);

    //Existen más campos
    if (sub) {
        sub.css('display', 'block');
        //Añadir la palabra del 'cada' (dias, meses, semanas)
        word = $('#textInterval').attr(repetitionType);
        $('#textInterval').text(word);
    }
};

var selectorHour = function() {
    selector = $('select[class="hour"]');
    for (n = 0; n < 24; ++n) {
        text = n;
        if (n < 10)
            text = '0' + n;
        html = "<option value=" + n + ">" + text + "</option>";
        selector.append(html);
    }
    ;
};

var selectorMinutes = function() {
    selector = $('select[class="minute"]');
    for (n = 0; n < 60; n += 15) {
        text = n;
        if (n < 10)
            text = '0' + n;
        html = "<option value=" + n + ">" + text + "</option>";
        selector.append(html);
    }
    ;
};

var selectorTime = function() {
    selectorHour();
    selectorMinutes();
};

addResourceToList = function(resource) {
    html = htmlResourcePop(resource);

    separator = '';
    if ($('#selectedResources').html()) {
        separator = ', ';
    } else {
        description = $('#selectedDescription');
        description.text($('#i18n').attr('some_selected'));
    }

    $('#selectedResources').append(separator, html);
    buildPopUps(html);
};

deleteResourceFromList = function(resource) {

    id = '#' + resource.attr('id');

    $('span[type="resource_popup"]').remove(id);

    html = $('#selectedResources').html();
    html = html.substring(0, html.length - 2);
    $('#selectedResources').html(html);

    if (!html) {
        description = $('#selectedDescription');
        no_selected = $('#i18n').attr('no_selected');
        description.text(no_selected);
    }
};

//On load
$(function() {
    selectorTime();

    createCheckTree(resources);

    $('input[type="date"]').datepicker();

    $('#repetition_type').change(function() {
        repetitionType = $(this).val();
        if (repetitionType !== 'once') {
            showRepetitions(repetitionType);
        } else {
            hiddeRepetitions();
        }
    });

    tree.bind("check_node.jstree", function(event, data) {
        resource = data.rslt.obj;
        if (resource.attr('rel') === 'group') {
            resource.find("li").each(function(idx, listItem) {
                child = $(listItem); // child object
                addResourceToList(child);
            });
        } else {
            addResourceToList(resource);
        }

    });

    tree.bind("uncheck_node.jstree", function(event, data) {
        resource = data.rslt.obj;
        if (resource.attr('rel') === 'group') {
            resource.find("li").each(function(idx, listItem) {
                child = $(listItem); // child object
                deleteResourceFromList(child);
            });
        } else {
            deleteResourceFromList(resource);
        }
    });
});
