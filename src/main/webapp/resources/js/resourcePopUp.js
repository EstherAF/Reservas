var buildResourceDialog = function(selected) {
    i18n = $('#i18n');
    title_i18n = i18n.attr('title');
    name_i18n = i18n.attr('name');
    description_i18n = i18n.attr('description');


    id = selected.attr('id');
    description = selected.attr('description');
    name = selected.text();

    html = '<div><p><span>' + name_i18n + '</span>' + name + '</p>';
    if (description) {
        html += '<p><span>' + description_i18n + '</span>' + description + '</p>';
    }
    html += '</div>';

    return html;
};

var htmlResourcePop=function(resource){
      
      html=$('<span></span>').attr({
          class:'link',
          type: 'resource_popup',
          id: resource.attr('id'),
          description: resource.attr('description')
      });
      html.text(resource.attr('name'));
      
      return html;
};

var buildPopUps = function(element){
        element.popover({
        html: true,
        trigger: 'click',
        content: function() {
            return buildResourceDialog($(this));
        },
        title: $('#i18n').attr('title')});
}


$(function() {
    buildPopUps($(".link"));
    $('html').on('click', function(e) {
        $('.link').each(function() {
            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
                return;
            }
        });
    });
});

