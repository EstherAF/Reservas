
var CalendarAvaliability = function(resources) {
    //create ResourceTree

    this.resourceTree = new ResourceTree(resources, 'multiselect');

    this.resources = [];
    this.resources.addResource = function(id) {
        var indexColor = this.length % MyCalendar.colors.length;

        var resource = {
            "id": id,
            "color": MyCalendar.colors[indexColor].color,
            "text": MyCalendar.colors[indexColor].text,
            "events": []
        };
        this.push(resource);

        return resource;
    };

    this.selected = [];
};

/*Extends of Calendar*/
CalendarAvaliability.prototype = Object.create(MyCalendar.prototype);

CalendarAvaliability.path = {
    'weekly':applicationPath + 'resources/month/',
    'monthly':applicationPath + 'resources/week/'
};


CalendarAvaliability.prototype.events = function(start, end, callback) {

    start.setDate(start.getDate() + 7);
    var year = start.getFullYear();
    var month = start.getMonth() + 1;
    var label = MyCalendar.monthNames[locale][month - 1] + ' ' + year;
    $('#reservation_nav .label').text(label);
    MyCalendar.updateLinks(CalendarAvaliability.path);
    callback([]);
};

//Before change to another month
CalendarAvaliability.prototype.saveState = function() {
    //Remove stored reservations from resources
    for (var i = 0; i < this.resources.length; i++) {
        MyCalendar.removeEventSource(this.resources[i].events);
        this.resources[i].events = [];
    }

//    var year = MyCalendar.getYear();
//    var month = MyCalendar.getMonth() + 1;
//    var historyObject = {
//        "year": year,
//        "month": month,
//        "selected": this.selected,
//        "resources": this.resources
//    };
//    window.history.replaceState(historyObject, null, CalendarAvaliability.path + year + '/' + month);
};

//After change to another month
CalendarAvaliability.prototype.afterChangeDay = function() {
    for (var i = 0; i < this.selected.length; i++) {
        var id = this.selected[i];
        var node = this.resourceTree.getNode('id', id);
        if (!this.resourceTree.tree.jstree('is_selected', node))
            this.resourceTree.tree.jstree('select_node', node);
        else
            this.selectResource(node);
    }

    var year = MyCalendar.getYear();
    var month = MyCalendar.getMonth() + 1;
    
    var cleanSelected = [];
    for(var i=0; i<this.selected;i++)
        cleanSelected.push(this.selected[i]);
    
    var cleanResources = [];
    for(var i=0; i<this.resources;i++)
        cleanResources.push(this.resources[i]);
    
    var historyObject = {
        "year": year,
        "month": month,
        "selected": cleanSelected,
        "resources": cleanResources
    };
    window.history.replaceState(historyObject, null, CalendarAvaliability.path + year + '/' + month);
};

//Show resource in fullCalendar. If it doesn't exist in resources[], get it by AJAX and store it in there
CalendarAvaliability.prototype.selectResource = function(data) {
    var id = data.attr("id");
    var index = this.selected.indexOf(id);
    if(index==null || index<0)
        this.selected.push(id);

    var index = this.resources.search("id", id);


    var resource = (index) ? this.resources[index] : this.resources.addResource(id);
    if (!resource.events ||
            resource.events.length === 0) {

        var viewController = this;
        Reservation.getReservationsByResource(
                MyCalendar.getYear(),
                MyCalendar.getMonth(),
                id,
                function(response) {
                    viewController.serializeToCalendarEvents.call(viewController, response);
                    MyCalendar.addEventSource(resource.events);

                    if (isNaN(data)) {
                        $(data).children('a').css('border-color', resource.color);
                        $(data).children('a').css('background-color', resource.color);
                        $(data).children('a').css('color', resource.text);
                    }
                },ajaxError);
    } else {
        var resource = this.resources[index];
        MyCalendar.addEventSource(resource.events);
        if (isNaN(data)) {
            $(data).children('a').css('border-color', resource.color);
            $(data).children('a').css('background-color', resource.color);
            $(data).children('a').css('color', resource.text);
        }
    }
};

//Hide resource.
CalendarAvaliability.prototype.deselectResource = function(data) {
    var id = data.attr('id');



    $(data).children('a').css('border-color', "");
    $(data).children('a').css('background-color', "");
    $(data).children('a').css('color', "");

    var index = this.selected.indexOf(id);
    this.selected.remove(index);
    //Remove event source
    var index = this.resources.search('id', id);
    MyCalendar.removeEventSource(this.resources[index].events);
};

CalendarAvaliability.prototype.serializeToCalendarEvents = function(instances) {
    for (var i = 0; i < instances.length; i++) {
        var instance = instances[i];

        var resourcesInstance = instance.reservation.resources;

        for (var resourceI = 0; resourceI < resourcesInstance.length; resourceI++) {
            var index = this.resources.search('id', resourcesInstance[resourceI].id);

            //Si no existe el recurso, se crea
            if (!index) {
                var resource = this.resources.addResource(resourcesInstance[resourceI].id);
            }
            else
                var resource = this.resources[index];


            var event = {
                title: instance.reservation.name,
                start: new Date(instance.startTimeDate),
                end: new Date(instance.endTimeDate),
                url: applicationPath + 'reservations/' + instance.reservation.id,
                id: resource.id,
                color: resource.color,
                textColor: resource.text
            };
            resource.events.push(event);
        }
    }
};

CalendarAvaliability.onLoad = function(view, year, month, week) {

    Resource.getAllByGroup(
            function(resources) {
                var viewController = new CalendarAvaliability(resources);

                viewController.resourceTree.tree.bind("select_node.jstree", function(event, data) {
                    viewController.selectResource(data.rslt.obj);
                });

                viewController.resourceTree.tree.bind("deselect_node.jstree", function(event, data) {
                    viewController.deselectResource(data.rslt.obj);
                });

                MyCalendar.call(viewController, viewController.events,
                        year, month, week, CalendarAvaliability.path, viewController.saveState,
                        viewController.afterChangeDay, view);

                $('#calendar').fullCalendar('option', 'aspectRatio', 16 / 8);

                window.addEventListener("popstate", function(e) {
                    var historyObject = e.state;
                    viewController.selected = historyObject.selected;
                    viewController.resources = historyObject.resources;
                    $('#calendar').fullCalendar('gotoDate', historyObject.year, historyObject.month - 1);
                    viewController.afterChangeDay();
                });
            },ajaxError);
};