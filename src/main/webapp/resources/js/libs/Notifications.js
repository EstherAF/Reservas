var Notifications = function(){};

Notifications.showError = function(error){
    var code = error.code;
    
    if(typeof error == String){
        message=error;
    }else if(error.responseJSON && error.responseJSON.restError){
        var restError = error.responseJSON.restError;
        
        if(restError instanceof Array){
            if(error.length === 0) return false;
            Modals.BuildFromErrorList(restError);
        }else{
            Modals.BuildFromError(error.responseJSON.restError);
        }
    }else{
        //Default server message
        Modals.BuildFromServerError(error);
    }
};

Notifications.showMessage = function(messageName){
    var text = Notifications.messages[locale][messageName];
    Modals.BuildFromData(Modals.type.ok, text, "");
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
        'delete_resource_ok': 'El recurso se ha eliminado correctamente',
        'confirm_remove_reservation':'¿Eliminar esta reserva?',
        'confirm_remove_resource':'¿Eliminar este recurso?\n\
                             Se eliminará de las reservas existentes, y aquellas que\n\
                               sólo reservan este recurso se eliminarán del sistema',
        'confirm_remove_group':'¿Eliminar este grupo de recursos?\n\
                                Los recursos que contiene también se eliminarán,\n\
                                Estos recursos se eliminará de las reservas existentes, \
                                y aquellas que \n sólo reservan este recurso se eliminarán \n\
                                del sistema',
        500: '500: Error interno del servidor'
    },
    'en': {
        'create_reservation_ok': 'La reserva se ha creado correctamente',
        'create_resource_ok': 'El recurso se ha creado correctamente',
        'update_reservation_ok': 'La reserva se ha modificado correctamente',
        'update_resource_ok': 'El recurso se ha modificado correctamente',
        'delete_reservation_ok': 'La reserva se ha eliminado correctamente',
        'delete_resource_ok': 'El recurso se ha eliminado correctamente',
        'confirm_remove_reservation':'¿Eliminar esta reserva?',
        'confirm_remove_resource':'¿Eliminar este recurso?\n\
                             Se eliminará de las reservas existentes, y aquellas que\n\
                               sólo reservan este recurso se eliminarán del sistema',
        'confirm_remove_group':'¿Eliminar este grupo de recursos?\n\
                                Los recursos que contiene también se eliminarán,\n\
                                Estos recursos se eliminará de las reservas existentes, \
                                y aquellas que \n sólo reservan este recurso se eliminarán \n\
                                del sistema',
        500: '500: Error interno del servidor'
    },
    'ga': {
        'create_reservation_ok': 'La reserva se ha creado correctamente',
        'create_resource_ok': 'El recurso se ha creado correctamente',
        'update_reservation_ok': 'La reserva se ha modificado correctamente',
        'update_resource_ok': 'El recurso se ha modificado correctamente',
        'delete_reservation_ok': 'La reserva se ha eliminado correctamente',
        'delete_resource_ok': 'El recurso se ha eliminado correctamente',
        'confirm_remove_reservation':'¿Eliminar esta reserva?',
        'confirm_remove_resource':'¿Eliminar este recurso?\n\
                             Se eliminará de las reservas existentes, y aquellas que\n\
                               sólo reservan este recurso se eliminarán del sistema',
        'confirm_remove_group':'¿Eliminar este grupo de recursos?\n\
                                Los recursos que contiene también se eliminarán,\n\
                                Estos recursos se eliminará de las reservas existentes, \
                                y aquellas que \n sólo reservan este recurso se eliminarán \n\
                                del sistema',
        500: '500: Error interno del servidor'
    }
};

Notifications.getMessage=function(messageName){
    return Notifications.messages[locale][message];
};
