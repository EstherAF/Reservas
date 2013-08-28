var Notifications = function(){};

Notifications.showError = function(error){
    var code = error.code;
    
    if(typeof error == String)
        message=error;
    else if(typeof error == Array){
        if(error.length === 0)
            return false;
        
        Modal.BuildFromErrorList()
    }else{
        Modal.BuildFromError(error);
    }
};

Notifications.showMessage = function(message){
    var text = this.messages[location][message];
    Modal.BuildFromData(Modal.type.ok, text, "");
};

Notifications.removeAll=function(){
    
};

Notifications.remove=function(object){
    var modal = $(object).parent('.simple-modal');
    modal.remove();
};

Notifications.messages = {
    'es': {
        'create_reservation_ok': 'La reserva se ha creado correctamente',
        'create_resource_ok': 'El recurso se ha creado correctamente',
        'update_reservation_ok': 'La reserva se ha modificado correctamente',
        'update_resource_ok': 'El recurso se ha modificado correctamente',
        'delete_reservation_ok': 'La reserva se ha eliminado correctamente',
        'delete_resource_ok': 'El recurso se ha eliminado correctamente'
    },
    'en': {
        'create_reservation_ok': 'La reserva se ha creado correctamente',
        'create_resource_ok': 'El recurso se ha creado correctamente',
        'update_reservation_ok': 'La reserva se ha modificado correctamente',
        'update_resource_ok': 'El recurso se ha modificado correctamente',
        'delete_reservation_ok': 'La reserva se ha eliminado correctamente',
        'delete_resource_ok': 'El recurso se ha eliminado correctamente'
    },
    'ga': {
        'create_reservation_ok': 'La reserva se ha creado correctamente',
        'create_resource_ok': 'El recurso se ha creado correctamente',
        'update_reservation_ok': 'La reserva se ha modificado correctamente',
        'update_resource_ok': 'El recurso se ha modificado correctamente',
        'delete_reservation_ok': 'La reserva se ha eliminado correctamente',
        'delete_resource_ok': 'El recurso se ha eliminado correctamente'
    }
};

