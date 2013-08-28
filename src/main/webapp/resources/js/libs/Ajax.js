//Función que hace request REST con los datos en la URL
//modifier possible values: "sync", "async"
Ajax = new Object();

Ajax.handleModifier = function(request, modifier){
    if (modifier != undefined) {
        var array = Array.isArray(modifier) ? modifier : [modifier];
        
        array.forEach(function(mod){
            switch (mod) {
                case "sync":
                    request.async = false;
                    break;
                case "async":
                    request.async = true;
                    break;
                case 'json':
                    request.dataType= 'json';
                    break;
                case 'html':
                    request.dataType= 'html';
                    break;
            }
        });
    }
};

Ajax.requestInUrl = function(method, path, modifier)
{

    return function(id, success_cb, error_cb)
    {
        var request = {
            url: path + id,
            type: method,
            success: success_cb,
            error: error_cb
        };

        Ajax.handleModifier(request,modifier);
        
        $.ajax(request);
    };
};

//Función que hace request REST con los datos en el body
//modifier possible values: "sync", "async"
Ajax.requestInBody = function(method, path, modifier)
{
    return function(entity, success_cb, error_cb)
    {
        var request = {
            url: path,
            type: method,
            contentType: "application/json",
            dataType: "json",
            data: entity.serialize(),
            success: success_cb,
            error: error_cb
        };
        
        Ajax.handleModifier(request,modifier);

        $.ajax(request);
    };
};


//Ajax.prueba = function(resources){
//    var jsonTree = [];
//    $.each(resources, function(index, elem) {
//        //Default group
//        if (index === 0) {
//            $.each(elem.resources, function(index2, elem2) {
//                var res = Resource.serializeResourceToTree(elem2);
//                res.attr.rel = "resource";
//                jsonTree[index2] = res;
//            });
//        } else {
//            var res = Resource.serializeResourceToTree(elem);
//            res.attr.id = "g" + res.attr.id;
//            if (elem.resources !== null && elem.resources.length !== 0) {
//                var child;
//                $.each(elem.resources, function(index2, elem2) {
//                    child = Resource.serializeResourceToTree(elem2);
//                    child.attr.rel = "resource";
//                    res.children[index2] = child;
//                });
//            }
//            jsonTree[jsonTree.length] = res;
//        }
//    });
//    return jsonTree;
//};

