import $ from 'jquery';
var Calendar = require('calendar').Calendar;


var cal = new Calendar(1);

var WEEK_LONG_NAME_DE = ['Montag', 'Dienstag', 'Mittwoch', 'Donerstag', 'Freitag', 'Samstag', 'Sonntag'];
var WEEK_SHORT_NAME_DE = ['Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa', 'So'];

var WEEK_LONG_NAME_ENG = ['Monday', 'Tuesday', 'Wedndesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
var WEEK_SHORT_NAME_ENG = ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'];

var MONTH_LONG_NAME_DE = ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'];
var MONTH_LONG_SHORT_DE = ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];

var MONTH_LONG_NAME_ENG = ['January', 'February', 'March', 'April', 'Mai', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
var MONTH_LONG_SHORT_ENG = ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

var MONTH_NUMERIC = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

var FREE_DAY = [false, false, false, false, false, true, true];

function nextMonth(offsetDateTime){ 
    if (offsetDateTime.date.month==11){
	offsetDateTime.date.year++;
    }
    offsetDateTime.date.month = ((offsetDateTime.date.month+12+1)%12);
    $(".month").fadeOut(1000).remove();
    $(createCalendar(offsetDateTime)).insertAfter(".daysOfWeek").hide().fadeIn(1000);
    $(".monthAndYear").empty();
    $(".monthAndYear").append(MONTH_LONG_NAME_DE[offsetDateTime.date.month]+" "+offsetDateTime.date.year).hide().fadeIn(200);
}

function lastMonth(offsetDateTime){
    if (offsetDateTime.date.month==0){
	offsetDateTime.date.year--;
    }
    offsetDateTime.date.month = ((offsetDateTime.date.month+12-1)%12);
    $(".month").fadeOut(1000).remove();
    $(createCalendar(offsetDateTime)).insertAfter(".daysOfWeek").hide().fadeIn(1000);
    $(".monthAndYear").empty();
    $(".monthAndYear").append(MONTH_LONG_NAME_DE[offsetDateTime.date.month]+" "+offsetDateTime.date.year).hide().fadeIn(200);
}

$.fn.dateChooser = function(offsetDateTime){

    this.offsetDateTime = offsetDateTime;
    
    var date = new Date();
    offsetDateTime.date.day = date.getDate();
    offsetDateTime.date.month = date.getMonth();
    offsetDateTime.date.year = date.getFullYear();
    
    var calendarHeader = $("<div class=\"calendarHeader\"></div>"); 
    var backwards = $("<div class=\"backwards\">back</div>"); 
    var monthAndYear = $("<div class=\"monthAndYear\"></div>");
    monthAndYear.append(MONTH_LONG_NAME_DE[offsetDateTime.date.month]+" "+offsetDateTime.date.year);
    $(backwards).click(function(monthAndYear){lastMonth(offsetDateTime)});
    var forward = $("<div class=\"forward\">next</div>"); 
    $(forward).click(function(monthAndYear){nextMonth(offsetDateTime)});
    calendarHeader.append(backwards);
    calendarHeader.append(monthAndYear);
    calendarHeader.append(forward);
    this.append(calendarHeader);

    var daysOfWeek = $("<div class=\"daysOfWeek\"></div>");
    for (var d=0; d<WEEK_SHORT_NAME_DE.length; d++){
	var weekDay = $("<div class=\"weekDay\"></div>");
	$(weekDay).append(WEEK_SHORT_NAME_DE[d]);
	$(daysOfWeek).append(weekDay);
    }
    this.append(daysOfWeek);
    this.append(createCalendar(offsetDateTime));
}

function createCalendar(offsetDateTime){
    var daysOfMonth = cal.monthDays(offsetDateTime.date.year, offsetDateTime.date.month);
    var daysOfLastMonth;
    var daysOfNextMonth;

    var lastMonth = (offsetDateTime.date.month+12-1)%12;
    if (lastMonth!=11){
	daysOfLastMonth = cal.monthDays(offsetDateTime.date.year, lastMonth);
    }else{
	daysOfLastMonth = cal.monthDays((offsetDateTime.date.year-1), lastMonth);
    }
    var nextMonth = (offsetDateTime.date.month+12+1)%12;
    if (nextMonth!=0){
	daysOfNextMonth = cal.monthDays(offsetDateTime.date.year, nextMonth);
    }else{
	daysOfNextMonth = cal.monthDays(offsetDateTime.date.year+1, nextMonth);
    }
    var monthContainer = $("<div class=\"month\"></div>");

    var numberOfWeeksLastMonth = daysOfLastMonth.length;
    var lastWeekOfLastMonth = daysOfLastMonth[numberOfWeeksLastMonth-1];
    var firstWeekOfNextMonth = daysOfNextMonth[0];



    for (var w=0; w<daysOfMonth.length; w++){
	var week = daysOfMonth[w];


	var weekContainer = $("<div class=\"week\"></div>");
	for (var d=0; d<week.length; d++){
	    var dayContainer;
	    if (week[d]==0){
		if(w==0){
		    dayContainer = $("<div class=\"day lastMonth\">"+ lastWeekOfLastMonth[d]+"</div>");
		}else{
		    dayContainer = $("<div class=\"day nextMonth\">"+firstWeekOfNextMonth[d]+"</div>");
		}
	    }else{
		dayContainer = $("<div class=\"day\">"+week[d]+"</div>");
	    }

	    if (FREE_DAY[d]==true){
		$(dayContainer).addClass("free").removeClass("day");
	    }else{
		$(dayContainer).click(function(){
		    $(".day").css("background-color", "white");
		    $(this).css("background-color", "#3ad8ab");
		    offsetDateTime.date.day=$(this).text();
		});
	    }
	    $(weekContainer).append(dayContainer);
	};
	$(monthContainer).append(weekContainer);
    }
    return monthContainer;
}