
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
            "events": []};
        this.push(resource);
        return resource;
    };

    this.selected = [];
};

/*Extends of Calendar*/
CalendarAvaliability.prototype = Object.create(GeneralCalendar.prototype);

CalendarAvaliability.path = new Object();
CalendarAvaliability.path[MyCalendar.viewName.monthly] = applicationPath + 'resources/month/';
CalendarAvaliability.path[MyCalendar.viewName.weekly] = applicationPath + 'resources/week/';

CalendarAvaliability.events = function(start, end, callback) {
    GeneralCalendar.updateLinks(CalendarAvaliability.path);
    callback([]);
};

CalendarAvaliability.prototype.removeEvents = function(){
    for (var i = 0; i < this.resources.length; i++) {
        MyCalendar.removeEventSource(this.resources[i].events);
        this.resources[i].events = [];
    }
};

//Before change to another month
CalendarAvaliability.prototype.beforeChangeDate = function() {
    this.removeEvents();
    //Remove stored reservations from resources
    this.saveState();
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

    this.saveState();
};

CalendarAvaliability.prototype.saveState = function() {
    var year = MyCalendar.getYear();
    var month = MyCalendar.getMonth() + 1;
    var week = MyCalendar.getWeek();
    var view = MyCalendar.getView();
    var date = MyCalendar.getStartDate();

    var clonedSelected = [];
    for (var j = 0; j < this.selected.length; j++)
        clonedSelected.push(this.selected[j]);

    var clonedResources = [];
    for (var j = 0; j < this.resources.length; j++)
        clonedResources.push(this.resources[j]);

    var historyObject = {
        "date": date,
        "selected": clonedSelected,
        "resources": clonedResources,
        "view": view
    };

    var subpath;
    switch (view) {
        case MyCalendar.viewName.monthly:
            subpath = year + '/' + month;
            break;
        case MyCalendar.viewName.weekly:
            subpath = year + '/' + week;
            break;
    }

    window.history.replaceState(historyObject, null, CalendarAvaliability.path[MyCalendar.getView()] + subpath);
};

//Show resource in fullCalendar. If it doesn't exist in resources[], get it by AJAX and store it in there
CalendarAvaliability.prototype.selectResource = function(data) {
    if(data.attr('rel')==='group'){
        return false;
    }
    
    var id = data.attr("id");
    var index = this.selected.indexOf(id);
    if (index == null || index < 0)
        this.selected.push(id);

    var index = this.resources.search("id", id);

    var resource = (index) ? this.resources[index] : this.resources.addResource(id);

    if (!resource.events ||
            resource.events.length === 0) {

        var viewController = this;
        var success = function(response) {
            viewController.serializeToCalendarEvents.call(viewController, response, resource);
            MyCalendar.addEventSource(resource.events);

            if (isNaN(data)) {
                $(data).children('a').css('border-color', resource.color);
                $(data).children('a').css('background-color', resource.color);
                $(data).children('a').css('color', resource.text);
            }
        };


        var view = MyCalendar.getView();
        
        
        if (view === MyCalendar.viewName.monthly) {
            Reservation.getReservationsByMonthResource(
                    MyCalendar.getYear(), MyCalendar.getMonth(), id,
                    success, ajaxError);
        } else if (view === MyCalendar.viewName.weekly) {
            
            Reservation.getReservationsByWeekResource(
                    MyCalendar.getYear(), MyCalendar.getWeek() , id,
                    success, ajaxError);
        }

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

CalendarAvaliability.prototype.serializeToCalendarEvents = function(instances, resource) {
    if (!resource)
        var resource = this.resources.addResource(resourcesInstance[resourceI].id);

    if(resource.events.length > 0)
        resource.events = [];

    for (var i = 0; i < instances.length; i++) {
        var instance = instances[i];

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

    return resource;
};

CalendarAvaliability.onLoad = function(view, year, month, week) {

    Resource.getAllByGroup(
            function(resources) {
                //call constructor
                var viewController = new CalendarAvaliability(resources);

                //call parent
                GeneralCalendar.call(viewController, CalendarAvaliability.events,
                        year, month, week, CalendarAvaliability.path, viewController.beforeChangeDate,
                        viewController.afterChangeDay, view);
                //call bindEvents (inherited)
                viewController.bindEvents();

                viewController.resourceTree.tree.bind({
                    "select_node.jstree": function(event, data) {
                        viewController.selectResource(data.rslt.obj);
                    },
                    "deselect_node.jstree": function(event, data) {
                        viewController.deselectResource(data.rslt.obj);
                    }
                });

                window.addEventListener("popstate", function(e) {
                    viewController.removeEvents();
                    var historyObject = e.state;
//                    viewController.saveState();
                    if(historyObject){
                        viewController.selected = historyObject.selected;
                        viewController.resources = historyObject.resources;
                        $('#calendar').fullCalendar('changeView', historyObject.view);
                        $('#calendar').fullCalendar('gotoDate', historyObject.date);
                        switch (historyObject.view) {
                            case MyCalendar.viewName.monthly:
                                $('#calendar').fullCalendar('option', 'aspectRatio', 2 / 1);
                                break;
                            case MyCalendar.viewName.weekly:
                                $('#calendar').fullCalendar('option', 'aspectRatio', 1 / 16);
                                break;
                        }

                        viewController.afterChangeDay();
                    }
                });
            },
            ajaxError
            );
};