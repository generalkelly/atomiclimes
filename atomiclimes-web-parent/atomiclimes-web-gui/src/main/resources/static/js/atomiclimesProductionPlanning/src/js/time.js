import _ from 'lodash';
import $ from 'jquery';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '../css/time.css';
import './calendar.js'
import '../css/calendar.css';
import 'slick-carousel';
import './submitProductionButton.js'

var timeout;

var offsetDateTime = {
	date : {
	    year: 1970,
	    month: 1,
	    day: 1
	},
	time : {
	    hours: 0,
	    minutes: 0,
	    seconds: 0
	},
	offset : "+01:00"
}



$('.productionTimeSettings').slick({});

$("#minute_up").click(function(){minutesUp()});
$("#minute_up").mousedown(function(){pressButton(minutesUp)});
$("#minute_down").click(function(){minutesDown()});
$("#minute_down").mousedown(function(){pressButton(minutesDown)});
$("#hour_up").click(function(){hoursUp()});
$("#hour_up").mousedown(function(){pressButton(hoursUp)});
$("#hour_down").click(function(){hoursDown()});
$("#hour_down").mousedown(function(){pressButton(hoursDown)});

$(document).mouseup(function(){clearButton()});

$('.timeChooser').on('touchstart touchmove mousemove mouseenter', function(e) {
    $('.productionTimeSettings').slick('slickSetOption', 'swipe', false, false);
});

$('.timeChooser').on('touchend mouseover mouseout', function(e) {
    $('.productionTimeSettings').slick('slickSetOption', 'swipe', true, false);
});

$('.dateChooser').dateChooser(offsetDateTime);

function printOffsetDateTime(offsetDateTime){
    var MONTH_NUMERIC = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

    var year;
    var month;
    var day;
    var hours;
    var minutes;
    var seconds;
    var offset = offsetDateTime.offset;
    year = offsetDateTime.date.year;
    month =MONTH_NUMERIC[offsetDateTime.date.month];
    day = printWithLeadingZero(offsetDateTime.date.day);
    hours = printWithLeadingZero(offsetDateTime.time.hours);
    minutes = printWithLeadingZero(offsetDateTime.time.minutes);
    seconds = printWithLeadingZero(offsetDateTime.time.seconds);

    return year+"-"+month+"-"+day+'T'+hours+":"+minutes+":"+seconds+offset;
}

function printWithLeadingZero(num){
    if (num < 10){
	return "0"+num;
    }else{
	return num;
    }
}

var estimatePlannedProduction = function(){

    var timezone="+01:00";

    var plannedProduction = {
	    productionItem: {
		name: ""
	    },
	    plannedProductionTime: "",
	    estimatedProductionDuration: 29
    }    
    plannedProduction.productionItem.name = "Eistee";
    plannedProduction.plannedProductionTime = printOffsetDateTime(offsetDateTime);

    return plannedProduction;
}

$('.submitButton').submitButton(estimatePlannedProduction, "/plannedProduction/addItem");

function pressButton(func){
    timeout = setInterval(function(){
	func();
    }, 100);
    return false;
}

function clearButton(){
    clearInterval(timeout);
    return false;
}


function hoursUp(){
    offsetDateTime.time.hours=(24+offsetDateTime.time.hours+1)%24;
    $("#hours").val(printHours(offsetDateTime.time.hours));
    return;
}

function hoursDown(){
    offsetDateTime.time.hours=(24+offsetDateTime.time.hours-1)%24;
    $("#hours").val(printHours(offsetDateTime.time.hours));
    return;
}

function printHours(hours){
    if (hours<10){
	return "0"+hours;
    }else{
	return hours;
    }
}

function minutesUp(){
    offsetDateTime.time.minutes=(60+offsetDateTime.time.minutes+1)%60;
    $("#minutes").val(printMinutes(offsetDateTime.time.minutes));
    return;
}

function minutesDown(){
    offsetDateTime.time.minutes=(60+offsetDateTime.time.minutes-1)%60;
    $("#minutes").val(printMinutes(offsetDateTime.time.minutes)) ;
    return;
}

function printMinutes(minutes){
    if (minutes<10){
	return "0"+minutes;
    }else{
	return minutes;
    }
}

