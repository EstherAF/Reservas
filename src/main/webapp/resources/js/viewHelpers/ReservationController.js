var ReservationController = function(users, resources, precheckedResources) {
    this.resourcesId = [];
    this.resources = (precheckedResources) ?
            ReservedResource.fromResourceList(precheckedResources) : [];

    this.guests = [];
    this.resourceTree = new ResourceTree(resources, 'checkbox', this.resources);

    this.autoComplete = new Autocomplete(users,
            'input[type="text"].autocomplete.users',
            'input[type="button"].autocomplete');

    this.form = new GenericForm(
            {
                selector: "form.new_reservation_form",
                properties: [
                    {name: 'name',
                        selector: "input[name='name']",
                        selectorError: "p[for='name']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'max_length', args: [50]},
                            {type: 'min_length', args: [1]}
                        ]
                    },
                    {name: 'description',
                        selector: "textarea[name='description']",
                    },
                    {name: 'startDate',
                        selector: function() {
                            return $("input[name='startDate']").datepicker("getDate");
                        },
                        selectorError: "p[for='startDate']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'date'}
                        ]
                    },
                    {name: 'startHour',
                        selector: 'select[name="startTimeHour"]'
                    },
                    {name: 'startMinute',
                        selector: 'select[name="startTimeMinute"]'
                    },
                    {name: 'endDate',
                        selector: function() {
                            return $("input[name='endDate']").datepicker("getDate");
                        },
                        selectorError: "p[for='endDate']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'date'},
                            {type: 'myRestriction',
                                validation: function(value) {
                                    return (value >= this.values.startDate);
                                },
                                messages: {
                                    es: 'La fecha de fin debe ser posterior a la de inicio',
                                    en: 'End date must be greater than start date',
                                    ga: 'A data de fin debe ser maior ca de inicio'}
                            }
                        ]
                    },
                    {name: 'endHour',
                        selector: 'select[name="endTimeHour"]'
                    },
                    {name: 'endMinute',
                        selector: 'select[name="endTimeMinute"]',
                        selectorError: "p[for='endDate']",
                        restrictions: [
                            {type: 'myRestriction',
                                validation: function(value) {
                                    var start = new Date(this.values.startDate);
                                    start.setHours(this.values.startHour, value);
                                    var now = new Date();
                                    return (start >= now);
                                },
                                messages: {
                                    es: 'No se pueden hacer reservas antes de la fecha y hora actuales',
                                    en: 'You can\'t make a reservation before the current date',
                                    ga: 'Non se poden realizar reservas antes da fecha e hora actuales'}
                            },
                            {type: 'myRestriction',
                                validation: function(value) {
                                    var start = new Date(this.values.startDate);
                                    start.setHours(this.values.startHour, this.values.startMinute);
                                    var end = new Date(this.values.endDate);
                                    end.setHours(this.values.endHour, value);
                                    return (end > start);
                                },
                                messages: {
                                    es: 'La fecha de fin debe ser posterior a la de inicio',
                                    en: 'End date must be greater than start date',
                                    ga: 'A data de fin debe ser maior ca de inicio'}
                            }
                        ]
                    },
                    {name: 'type',
                        selector: 'select[name="repetition_type"]'
                    },
                    {name: 'resources',
                        selector: function() {
                            return this.argumentsArray[0];
                        },
                        selectorError: "p[for='resources']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'min_selected_length', args: [1]}
                        ]
                    },
                    {name: 'invitations',
                        selector: function() {
                            return this.argumentsArray[1];
                        },
                    }
                ]});

    this.repetition = new Object();
    this.repetition.general = new GenericForm(
            {
                selector: "form.new_reservation_form",
                properties: [
                    {name: 'interval',
                        selector: "input[name='interval']",
                        selectorError: "p[for='interval']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'numeric'},
                            {type: 'gt', args: [0]}
                        ]
                    },
                    {name: 'endRepetitionDate',
                        selector: function() {
                            return $("input[name='endRepetitionDate']").datepicker("getDate");
                        },
                        selectorError: "p[for='endRepetitionDate']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'date'},
                            {type: 'myRestriction',
                                validation: function(value) {
                                    return (value >= this.argumentsArray[0]);
                                },
                                messages: {es: 'La fecha de fin de la repetición debe ser posterior a la de fin de la reserva', en: 'End repetition date must be greater than reservation end date', ga: 'A data de fin da repetición debe ser maior ca de fin da reserva'}
                            }
                        ]
                    }
                ]});
    this.repetition.weekly = new GenericForm(
            {
                selector: "form.new_reservation_form",
                properties: [
                    {name: 'weekly_days',
                        selector: function() {
                            var values = [];
                            $('input[name="weekly_days"]:checked').each(function() {
                                values.push(this.value);
                            });
                            return values;
                        },
                        selectorError: "p[for='weekly_days']",
                        restrictions: [
                            {type: 'required'},
                            {type: 'min_selected_length', args: [1]}
                        ]
                    },
                ]});

    this.repetition.monthly = new GenericForm(
            {
                selector: "form.new_reservation_form",
                properties: [
                    {name: 'monthlyRepetition',
                        selector: "input[name='monthlyRepetition']:checked",
                        selectorError: "p[for='monthlyRepetition']",
                        restrictions: [
                            {type: 'selected_required'},
                        ]
                    }
                ]});
};

/*Repetitions*/
ReservationController.prototype.hiddeRepetitions = function() {
    //Remove required from attributes
    $('#interval').removeAttr('required');
    $('#endDate').removeAttr('required');

    $('div[id$="_repetition"]').hide();
    $('.repetition').hide();
};
ReservationController.prototype.showRepetitions = function(repetitionType) {
    $('.repetition').show();

    //Set required attributes
    $('#interval').attr('required', true);
    $('#endDate').attr('required', true);

    $('div[id$="_repetition"][id!="' + repetitionType.toLowerCase() + '_repetition"]').hide();

    var repetitionSubsectionId = '#' + repetitionType.toLowerCase() + '_repetition';
    var repetitionSubsection = $(repetitionSubsectionId);

    //Existen más campos
    if (repetitionSubsection) {
        repetitionSubsection.show();

        //Añadir la palabra del 'cada' (dias, meses, semanas)
        word = $('#textInterval').attr(repetitionType);
        $('#textInterval').text(word);
    }
};
ReservationController.prototype.bindSelectRepetition = function(repetitionType) {
    if (repetitionType !== 'ONCE') {
        this.showRepetitions(repetitionType);
    } else {
        this.hiddeRepetitions();
    }
};

/*Resources*/
//Rebuild the selected resources list in DOM
ReservationController.prototype.refreshHTMLResourcesList = function() {

    //Create HTML to build the selected items list, splitted by ', '
    var htmlArray = [];
    this.resources.forEach(function(reservedResource) {
        var html = ResourcePopUp.htmlResourcePop(reservedResource);
        htmlArray.push(html[0].outerHTML);
    });
    var html = htmlArray.join(', ');

    //Insert HTML in DOM
    $('#selectedResources').html(html);

    //Change label's message
    if (html) {
        ResourcePopUp.buildPopUps($('.popup'));
        var description = $('#selectedDescription');
        var some_selected = $('#i18n').attr('some_selected');
        description.text(some_selected);
    } else {
        var description = $('#selectedDescription');
        var no_selected = $('#i18n').attr('no_selected');
        description.text(no_selected);
    }
};
ReservationController.prototype.bindCheckResourceTree = function(data) {
    var node = data.rslt.obj;
    var resource = Resource.serializeNodeToResource(node);

    var reservedResource = new ReservedResource(resource);

    //if it's node, uncheck before, so childrens are going to be unchecked too
    if (node.attr('rel') == 'group')
        this.bindUncheckResourceTree(data);

    this.resources.push(reservedResource);

    //Rebuild selected resources list in DOM
    this.refreshHTMLResourcesList();

    //If its a group, close it so it can´t be opened, and show popover to select quantity
    if (node.attr('rel') == 'group') {
        node.attr("type_jstree", 'closed');
        node.addClass('jstree-leaf');
        this.resourceTree.tree.jstree('close_node', resource);
        $('#selectedResources > span[id="' + resource.id + '"]').popover('show');
    }
};
ReservationController.prototype.bindUncheckResourceTree = function(data) {
    var node = data.rslt.obj;
    var index = this.resources.search('resource.id', node.attr('id'));

    if (index) {
        //remove from arrays
        this.resources.remove(index);
    }
    //if it's a group, uncheck childrens too, and remove them from arrays if exist.
    //after that, close node, but it can be opened again if desired
    if (node.attr('rel') === 'group') {
        var childs = node.find('li');
        for (var i = 0; i < childs.length; i++) {
            var id = $(childs[i]).attr('id');
            var index = this.resources.search('resource.id', id);
            if (index) {
                this.resources.remove(index);
            }
        }
        this.resourceTree.tree.jstree('close_node', node, true);
        node.removeClass('jstree-leaf');
    }

    this.refreshHTMLResourcesList();

    node.removeAttr("type_jstree");
};
ReservationController.prototype.changeResourceQuantity = function(resourceId, quantity) {
    var index = this.resources.search('resource.id', resourceId);

    this.resources[index].quantity = quantity;

    this.refreshHTMLResourcesList();
};

/*Invitations*/
//Add user to invitations List. Remove it from autocomplete
ReservationController.prototype.addGuest = function(invitation) {
    $('[for="errorGuestList"]').hide();

    //Add row to DOM
    var rowState = invitation.state ? invitation.state : 'NEW';

    var newGuestTable = $('<tr></tr>').attr({
        "uniqueName": invitation.guest.uniqueName,
        "fullname": invitation.guest.fullName,
        "email": invitation.guest.email,
        "Title": $('.i18n[type="invitations_icon"]').attr(rowState)
    });


    var icon = 'icon';
    if (invitation.state)    //If it existed before, set same icon state
        icon += '-invitation-' + invitation.state;

    $('<td></td>').attr('class', icon).appendTo(newGuestTable); //Append icon
    var content = $('<td></td>').attr('class', 'content').append(invitation.guest.fullName);
    $('<span></span>').attr('class', 'email').append(' (' + invitation.guest.email + ')').appendTo(content);
    content.appendTo(newGuestTable);    //Append name and email
    $('<td></td>').attr('class', 'icon-remove').appendTo(newGuestTable); //Append remove icon
    newGuestTable.appendTo('table.guests');

    //Add invitation to ReservationController.guests
    invitation.state = (invitation.state) ? invitation.state : Invitation.states.waiting;
    this.guests.push(invitation);

    //Remove from autocomplete list
    this.autoComplete.remove();

    //Clean input text
    $('input[type="text"].autocomplete').val('');

    //Change label message
    var label = $('label[for="guestList"]');
    label.text(label.attr('notempty'));
};
//Remove user from invitations List. Add it to autocomplete
ReservationController.prototype.removeGuest = function(row) {
    //Build guest to remove
    var user = new User(row.attr('uniqueName'),
            row.attr('fullName'),
            row.attr('email'));
    //var invitation = new Invitation(user, row.attr('state'));

    //Remove it from arrays and DOM
    var index = this.guests.search('user.uniqueName', user.uniqueName);
    this.guests.remove(index);
    row.remove();

    //Add user to autocomplete list
    this.autoComplete.add(user);

    //Change label
    if (this.guests.length === 0) {
        var label = $('label[for="guestList"]');
        label.text(label.attr('empty'));
    }
};

/*Obtain reservation from form*/
ReservationController.prototype.getReservationFromForm = function() {
    this.form.removeErrors();

    var values = this.form.validate(this.resources, this.guests);
    var reservation = new NewReservation();
    if (values) {
        reservation.name = values.name;
        reservation.description = values.description;
        reservation.start = values.startDate;
        reservation.start.setHours(values.startHour, values.startMinute);
        reservation.end = values.endDate;
        reservation.end.setHours(values.endHour, values.endMinute);
        var repetitionType = values.type;
        reservation.repetition.type = repetitionType;
        reservation.invitations = values.invitations;
        var resources = values.resources;
        if (repetitionType !== Repetition.type.once) {
            values = this.repetition.general.validate(reservation.end);
            if (values) {
                reservation.repetition.interval = values.interval;
                reservation.repetition.endDate = values.endRepetitionDate;

                switch (repetitionType) {
                    case Repetition.type.weekly:
                        values = this.repetition.weekly.validate();
                        reservation.repetition.weekDays = values.weekly_days;
                        break;
                    case Repetition.type.monthly:
                        values = this.repetition.monthly.validate();
                        reservation.repetition.type = values.monthlyRepetition;
                        break;
                }
            }
        } else {
            reservation.repetition.endDate = reservation.end;
        }

        //Create
        if (values) { //last validation was successful
            reservation.setResources(resources);
            return reservation;
        } else
            return null;
    }
};

//On load
ReservationController.load = function(resourceTree, selectGuess) {
    dateHelper.onLoad();

    ResourcePopUp.onLoad();

    var viewController = this;

    //Add guess
    $('input[type="button"].autocomplete').click(function() {
        selectGuess.call(viewController);
    });

    //Remove guess
    $("table.guests").on("click", "td.icon-remove", function() {
        viewController.removeGuest.call(viewController, $(this).parent());
    });

    //Check/Uncheck resource
    resourceTree.tree.bind({
        "check_node.jstree": function(event, data) {
            viewController.bindCheckResourceTree.call(viewController, data);
        },
        "uncheck_node.jstree": function(event, data) {
            viewController.bindUncheckResourceTree.call(viewController, data);
        }
    });

    //Check/uncheck resource by clicking on recource name
    resourceTree.tree.on('click', 'ul > li > a', function() {
        resourceTree.toggleCheckBox($(this).parent());
    });

    //Change DOM when repetition_type is changed
    $('select[name="repetition_type"]').change(function() {
        viewController.bindSelectRepetition.call(viewController, $(this).val());
    });

    //Change quantity of a resource group when its selector is changed
    $('html').on('change', '.resource_group_selector', function() {
        viewController.changeResourceQuantity.call(viewController, $(this).attr('id'), $(this).val());
    });
};
