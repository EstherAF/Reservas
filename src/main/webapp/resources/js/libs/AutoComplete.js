/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Autocomplete = function(items, selectorInput, selectorButton) {
    this.items = items;
    this.itemsText = this.serializeUsersListToAutocomplete();
    this.selectorInput = selectorInput;
    this.selectorButton = selectorButton;
    this.self = this;

    //Initialize plugin
    this.initialize();
};

Autocomplete.prototype.initialize = function() {
    var selector = this.selectorInput;
    var button = this.selectorButton;

    $(this.selectorInput).autocomplete({
        source: this.itemsText,
        minLength: 3,
        select: function(event, ui) {
            $(selector).val(ui.item.value);
            $(button).trigger('click');
            event.stopPropagation();
            $(selector).val("");
        }
    });
};

Autocomplete.prototype.serializeUsersListToAutocomplete = function(users) {
    if (!users)
        users = this.items;

    var jsonList = [];
    $.each(users, function(index, elem) {
        if (elem.uniqueName !== loggedUniqueName) {
            var userLabel = elem.fullName + " (" + elem.email + ")";
            jsonList[jsonList.length] = {label: userLabel, value: elem.uniqueName};
        }
    });

    return jsonList;
};

Autocomplete.prototype.getSelected = function(userText) {
    if (!userText) {
        userText = $(this.selectorInput).val();
    }
    if (!userText)
        return null;

    var index = this.items.search('uniqueName', userText);
    if (!index)
        return null;
    var user = this.items[index];

    return user;
};

Autocomplete.prototype.remove = function(userText) {
    if (!userText) {
        userText = $(this.selectorInput).val();
    }

    var index = (isNaN(userText)) ? this.items.search('uniqueName', userText) : userText;
    this.items.remove(index);
    this.itemsText.remove(index);

    this.initialize();
};

Autocomplete.prototype.add = function(user) {
    this.items.push(user);
    this.itemsText = this.serializeUsersListToAutocomplete();
    this.initialize();
};

$(function() {
    $('this.selector').bind('keypress', function(e) {
        if (e.which === 13)
            $('input[type="button"].autocomplete').trigger('click');
    });
});
