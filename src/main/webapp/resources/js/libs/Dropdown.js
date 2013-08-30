/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

Dropdown = new Object();

Dropdown.create = function(wrapper, list, operation) {
    if (list.length > 0) {
        $(wrapper + ' > ul.dropdown').show();
        
        //Add handler for click on dropdown event
        $(wrapper + '> .dropdown').click(function(e){
            e.preventDefault();
            Dropdown.toggleDropDown.call(this);
        });
        var length = Dropdown.populateList(list, operation);
        $('.number_alert').text(length);
    } else {
        $(wrapper).hide();
    }
};

Dropdown.populateList = function(list, operation) {
    var menu = $('.dropdown_child');
    var number = 0;
    list.forEach(function(element) {
        var html = operation(element);
        menu.append(html);
        number += 1;
    });
    return number;
};

Dropdown.setNumber = function(number) {
    $(NavigationBar.dropDownWrapper + ' number_alert').text(number);
};

Dropdown.removeFromList = function(selector) {
    $('.dropdown_child').find(selector).remove();
    var number = $('.number_alert');

    if (number.text() > 1)
        number.text(number.text() - 1);
    else {
        number.hide();
//        $(".dropdown").trigger('click');
        
        Dropdown.toggleDropDown.call($('.dropdown_wrapper > .dropdown'));
    }
};

Dropdown.hideDropdown = function(e) {
    var wrapper = $(".dropdown_wrapper");

    if (!wrapper.is(e.target) &&
            wrapper.has(e.target).length === 0) {
        Dropdown.toggleDropDown.call(wrapper.find('.dropdown'));
        return;
    }
};

Dropdown.toggleDropDown = function() {
    var dropdownButton = $(this);
    var dropdownMenu = $(this).parent().children('.dropdown_child');
    dropdownMenu.toggle();
    dropdownButton.toggleClass('show');
    if (dropdownMenu.css('display') != 'none') {
        //Add handler for click outside of dropdown event
        $('html').bind('click', function(e) {Dropdown.hideDropdown(e);});
    } else {
        //Remove handler for click outside of dropdown event
        $('html').unbind('click', function(e) {Dropdown.hideDropdown(e);});
    }
};