
var Modals = function(type, content, id) {
    var newModal = $('<div></div>').attr({
        'class': 'simple-modal ' + type,
        'id': id
    });
    var modalCloseIcon = $('<span></span>').attr('class', 'close-modal icon-close');
    newModal.append(modalCloseIcon);
    newModal.append(content);
    $("#modal-wrapper").append(newModal);
};


Modals.BuildFromData = function(type, message, developerMessage, id) {
    var content = $('<p></p>');
    if(developerMessage)
        content.attr("developerMessage", developerMessage);
    
    content.append(message);
    Modals(type, content, id);
};

Modals.BuildFromError = function(error) {
    Modals.BuildFromData(Modals.type.fail, error.message, error.developerMessage);
};

Modals.BuildFromServerError = function(error) {
    var message = Notifications.messages[location][error.status];
    Modals.BuildFromData(Modals.type.fail, message, error.responseText);
};

Modals.BuildFromErrorList = function(error) {
    
    var content='';
    
    for(var i=0; i<error.length; i++){
        var line = $('<p></p>').attr("developerMessage", error[i].developerMessage);
        line.append(error[i].message);
        content+=line[0].outerHTML;
    }
    
    Modals(Modals.type.error, content);
};

Modals.removeAll = function() {
    var wrapper = $("#modal-wrapper");
    wrapper.html('');
};

Modals.remove = function(id) {
    var wrapper = $("#modal-wrapper");
    var modal = wrapper.find('[id="' + id + '"]');
    if (modal)
        modal.remove();
};

Modals.type = {
    'ok': 'ok',
    'fail': 'fail'
};


$(function() {
    $('#modal-wrapper').on('click', '.close-modal', function() {
        $(this.parentNode).remove();
    });
});