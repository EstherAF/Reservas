var applicationPath = location.protocol + "//" + location.host+"/Reservas/";

Array.prototype.remove = function(index) {
    this.splice(index, 1);
};

Array.prototype.removeItem = function(item) {
    var index = this.indexOf(item);

    if (index > 0) {
        this.remove(index);
        index = this.indexOf(item);
    }

    return index;
};

Array.prototype.pushArray = function(arr) {
    this.push.apply(this, arr);
};

Array.prototype.search = function(atributeName, atributeValue) {
    var i = 0;
    var atributeNames = atributeName.split('.');
    for (i in this) {
        var element = this[i];

        for (var j = 0; j < atributeNames.length; j++) {
            if (!element)
                return null;
            element = element[atributeNames[j]];
        }

        if (element == atributeValue)
            return i;
    }
    return null;
};

var clone = function(o) {
    return JSON.parse(JSON.stringify(o));
};

var extend = function(proto) {
    function intermediario() {
    }
    intermediario.prototype = proto;
    return new intermediario;
};

var isFunction = function(o) {
    return (getClass.call(o) == '[object Function]');
};

var ajaxError = function(error) {
    Notifications.showError(error);
};