/*---------------Model of Repetition--------------*/
var Repetition = function(interval, endDate, type, weekDays) {
    this.interval = interval;
    this.endDate = endDate;
    this.type = type;
    if(weekDays)
        this.weekDays = weekDays;
};

Repetition.type = {
    once: 'ONCE',
    weekly: 'WEEKLY',
    monthly: 'MONTHLY',
    monthly_relative: 'MONTHLY_RELATIVE',
    daily: 'DAILY'
};
