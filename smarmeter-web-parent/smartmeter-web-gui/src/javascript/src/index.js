import _ from 'lodash';
import $ from 'jquery';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './css/time.css';
import './js/calendar.js'
import './css/calendar.css';
import 'slick-carousel';

//var datetime;
//var date="1988-01-03";
//var time;
//var timezone="+01:00";

//function component() {
//let element = document.createElement('div');
//time="15:45:00";
//datetime=date+'T'+time+timezone;
//element.innerHTML = _.join(['Hello', datetime], ' ');
//return element;
//}

var hours = 0;
var minutes = 0;
var timeout;
var day;
var month;
var year;


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

$('.dateChooser').dateChooser(day, month, year);


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
    hours=(24+hours+1)%24;
    $("#hours").val(printHours(hours));
    return;
}

function hoursDown(){
    hours=(24+hours-1)%24;
    $("#hours").val(printHours(hours));
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
    minutes=(60+minutes+1)%60;
    $("#minutes").val(printMinutes(minutes));
    return;
}

function minutesDown(){
    minutes=(60+minutes-1)%60;
    $("#minutes").val(printMinutes(minutes)) ;
    return;
}

function printMinutes(minutes){
    if (minutes<10){
	return "0"+minutes;
    }else{
	return minutes;
    }
}

//document.body.appendChild(component());
