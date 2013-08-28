var Modal = function(type, content) {
    var modal = $('<div></div>').attr({
        "class": "simple-modal " + this.type
    });
    modal.append(content);

    var closeModalBtn = $('<span></span>').addClass("close-modal icon-close");
    modal.append(closeModalBtn);

    $('#modal-wrapper').append(modal);
};

Modal.BuildFromData = function(type, message, developerMessage) {
    var content = $('<p></p>');
    if(developerMessage)
        content.attr("developerMessage", developerMessage);
    
    content.append(message);
    Modal(type, content);
};

Modal.BuildFromError = function(error) {
    Modal.BuildFromData(Modal.type.error, error.message, error.developerMessage);
};

Modal.BuildFromErrorList = function(error) {
    
    var content='';
    
    for(var i=0; i<error.length; i++){
        var line = $('<p></p>').attr("developerMessage", error[i].developerMessage);
        line.append(error[i].message);
        content+=line[0].outerHTML;
    }
    
    Modal(Modal.type.error, content);
};

Modal.type = new Object();
Modal.type.error = "fail";
Modal.type.ok = "ok";




