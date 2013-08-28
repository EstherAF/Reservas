/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//Función que hace request REST con los datos en la URL
sjaxRequestInUrl = function(method, path)
{
    return function(id, success_cb, error_cb)
    {
        $.ajax({
            url: path + id,
            type: method,
            dataType: "json",
            contentType: "application/json",
            success: success_cb,
            error: error_cb,
            async:false
        });
    };
};



ajaxRequestInUrl = function(method, path)
{
    return function(id, success_cb, error_cb)
    {
        $.ajax({
            url: path + id,
            type: method,
            dataType: "json",
            contentType: "application/json",
            success: success_cb,
            error: error_cb
        });
    };
};

//Función que hace request REST con los datos en el body
ajaxRequestInBody = function(method, path)
{
    return function(entity, success_cb, error_cb)
    {
        $.ajax({
            url: path,
            type: method,
            contentType: "application/json",
            data: entity.serialize(),
            success: success_cb,
            error: error_cb});
    };
};


