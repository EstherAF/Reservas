/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function GenericForm(form) {
    this.form = form;
    this.error = new Error();
    this.messages = this.error.messages;
    this.values = [];
}
;

GenericForm.prototype.validate = function() {
    this.argumentsArray = Array.prototype.slice.apply(arguments);  
    
    var form = $(this.form.selector);
    var failed = false;

    for (var i = 0; i<this.form.properties.length;i++) {
        var property = this.form.properties[i];
        var value = (property.selector instanceof Function) ? property.selector.call(this) : form.find(property.selector).val();
        var restrictions = property.restrictions;

        if (restrictions) {
            try {
                this.checkRestrictions(value, restrictions);
            } catch (error) {
                failed = true;
                if(error.type === 'myRestriction')
                    this.showError(error, property.selectorError, error.messages);
                else
                    this.showError(error, property.selectorError);
                
                continue;
            }
        }

        //add value to values array
        this.values[property.name] = value;
    }

    if (failed === true)
        return false;
    else
        return this.values;
};

GenericForm.prototype.showError = function(failedRestriction, selectorError, messageArray) {
    var message = this.error.getMessage(locale, failedRestriction, messageArray);

    var htmlElement = '<span class="formError">' + message + '</span>';

    var containerMessage = $(selectorError);

    containerMessage.append(htmlElement);
    containerMessage.show();
};

GenericForm.prototype.removeErrors = function(selector) {
    var containerMessage;
    if (!selector)
        containerMessage = $('span.formError');

    containerMessage.empty();
    containerMessage.hide();
};

GenericForm.prototype.checkRestrictions = function(value, restrictions) {
    //If value is empty, and it's not required
    if (value || restrictions.search('type', 'required') ||
            restrictions.search('type', 'selectd_required')) {

        var getArg = function(args, i) {
            if (args)
                return args[i];
            else
                throw 'more arguments are needed';
        };

        for (var i = 0; i<restrictions.length;i++) {
            var restriction = restrictions[i];
            var type = restriction.type;
            var args = (restriction.args != null) ? restriction.args : [];

            switch (type) {
                case 'required':
                case 'selected_required':
                    if (!value && value !== 0)
                        throw restriction;
                    break;
                case 'min_length':
                case 'min_selected_length':
                    var limit = getArg(args, 0);
                    if (value.length < limit)
                        throw restriction;
                    break;
                case 'max_length':
                    var limit = getArg(args, 0);
                    if (value.length > limit)
                        throw restriction;
                    break;

                case 'numeric':
                    if (!$.isNumeric(value))
                        throw restriction;
                    break;
                case 'date':
                    if (!value instanceof Date)
                        throw restriction;
                    break;
                case 'gt':
                    var limit = getArg(args, 0);
                    if (! value > limit)
                        throw restriction;
                    break;
                case 'ge':
                    var limit = getArg(args, 0);
                    if (! value >= limit)
                        throw restriction;
                    break;
                case 'lt':
                    var limit = getArg(args, 0);
                    if ( ! value < limit)
                        throw restriction;
                    break;
                case 'le':
                    var limit = getArg(args, 0);
                    if (! value <= limit)
                        throw restriction;
                    break;
                default:
                    if(!restriction.validation.call(this, value))
                        throw restriction;
                    break;
            }
        }
    }
};


GenericForm.prototype.getValue = function(name) {
    var value = this.values[name];
    if (value)
        return value;
    else
        return null;
};

function Error() {

    this.messages = {
        "es": {
            required: "Este campo es obligatorio",
            selected_required: "Debes seleccionar alguno",
            max_length: function(value) {
                return "La longitud máxima es de " + value[0] + " caracteres";
            },
            min_length: function(value) {
                return "La longitud mínima es de " + value[0] + " caracteres";
            },
            min_selected_length: function(value) {
                return "Debes seleccionar al menos " + value[0] + " opción";
            },
            numeric: "Debe ser un número",
            date: "No es una fecha válida",
            gt:function(value) {return "Debe ser mayor que " + value[0];},
            ge:function(value) {return "Debe ser mayor o igual que " + value[0];},
            lt:function(value) {return "Debe ser menor que " + value[0];},
            ge:function(value) {return "Debe ser menor o igual que " + value[0];}
        },
        "en": {
            required: "This field is required",
            selected_required: "You must select one",
            max_length: function(value) {
                return "Max length is " + value[0] + " characters";
            },
            min_length: function(value) {
                return "Min length is " + value[0] + " characters";
            },
            min_selected_length: function(value) {
                return "You mus select at least " + value[0] + " option";
            },
            numeric: "This field have to be a number",
            date: "Invalid date",
            gt:function(value) {return "Must be greater than " + value[0];},
            ge:function(value) {return "Must be greater or equals " + value[0];},
            lt:function(value) {return "Must be less than " + value[0];},
            ge:function(value) {return "Must be less or equals than" + value[0];}
        },
        "ga": {
            required: "Este campo é obligatorio",
            selected_required: "Debes seleccionar algún",
            max_length: function(value) {
                return "A lonxitude máxima é de " + value[0] + " caracteres";
            },
            min_length: function(value) {
                return "A longitude mínima é de " + value[0] + " caracteres";
            },
            min_selected_length: function(value) {
                return "Debes seleccionar a lo menos " + value[0] + " opción";
            },
            numeric: "Debe ser un número",
            date: "Non é unha fecha válida",
            gt:function(value) {return "Debe ser maior que " + value[0];},
            ge:function(value) {return "Debe ser maior ou igual que " + value[0];},
            lt:function(value) {return "Debe ser menor que " + value[0];},
            ge:function(value) {return "Debe ser menor ou igual que " + value[0];}
        }
    };

}

Error.prototype.getMessage = function(language, restriction, array) {
    if(language === '')
        language='en';
    
    var message = (array)? array[language] : this.messages[language][restriction.type];

    if (message instanceof Function)
        message = message(restriction.args);

    return message;
};

Error.prototype.addMessage = function(name, spanish, english, galician) {
    this.messages.es[name] = spanish;
    this.messages.ga[name] = galician;
    this.messages.en[name] = english;
};