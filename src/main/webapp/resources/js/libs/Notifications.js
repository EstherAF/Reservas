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
        'confirm_remove_resource':'¿Eliminar este recurso?\n'+
                             'Se eliminará de las reservas existentes, y aquellas\n'+
                             'que sólo tienen este recurso se eliminarán del sistema.',
        'confirm_remove_group':'¿Eliminar este grupo de recursos?\n'+
                                'Los recursos que contiene también se eliminarán.',
        500: '500: Error interno del servidor'
    },
    'en': {
        'create_reservation_ok': 'Reservation was successfully created',
        'create_resource_ok': 'Resource was successfully created',
        'update_reservation_ok': 'Reservation was successfully updated',
        'update_resource_ok': 'The resource was successfully updated',
        'delete_reservation_ok': 'The reservation was successfully removed',
        'delete_resource_ok': 'The resource was successfully removed',
        'confirm_remove_reservation':'Do you want to remove this reservation?',
        'confirm_remove_resource':'Do you want to remove this resource?\n'+
                            'I t will be removed from existing reservations, and there that\n'+
                             'only reserve this resource will be removed too.',
        'confirm_remove_group':'Do you want to remove this resource group?\n'+
                                'Child resources will be removed too.',
        500: '500: Internal server error'
    },
    'ga': {
        'create_reservation_ok': 'A reserva creouse correctamente',
        'create_resource_ok': 'O recurso creouse correctamente',
        'update_reservation_ok': 'A reserva modificouse correctamente',
        'update_resource_ok': 'O recurso modificouse correctamente',
        'delete_reservation_ok': 'A reserva eliminouse correctamente',
        'delete_resource_ok': 'O recurso eliminouse correctamente',
        'confirm_remove_reservation':'Desexa eliminar esta reserva?',
        'confirm_remove_resource':'Desexa eliminar este recurso?\n'+
                             'Eliminarase de todas as reservas existentes, e aquelas\n'+
                             'que só ocupan este recurso tamén se eliminarán.',
        'confirm_remove_group':'Desexa eliminar este grupo de recursos?\n'+
                                'Os recursos que contén tamén serán eliminados.',
        500: '500: Erro interno do servidor'
    }
};

Notifications.getMessage=function(messageName){
    return Notifications.messages[locale][messageName];
};
