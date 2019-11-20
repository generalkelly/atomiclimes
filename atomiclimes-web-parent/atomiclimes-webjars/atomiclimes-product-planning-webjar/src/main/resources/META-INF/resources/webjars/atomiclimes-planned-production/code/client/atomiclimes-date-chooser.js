import $ from 'jquery'
import feather from 'feather-icons'
import './atomiclimes-date-chooser.css'
import AtomicLimesEvent from './atomiclimes-event.js'
var Calendar = require('calendar').Calendar



export default class AtomicLimesDateChooser {

  constructor(day, month, year, plannedProduction) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.plannedProduction = plannedProduction;

    this.cal = new Calendar(1);

    this.WEEK_LONG_NAME_DE = ['Montag', 'Dienstag', 'Mittwoch', 'Donerstag', 'Freitag', 'Samstag', 'Sonntag'];
    this.WEEK_SHORT_NAME_DE = ['Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa', 'So'];

    this.WEEK_LONG_NAME_ENG = ['Monday', 'Tuesday', 'Wedndesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
    this.WEEK_SHORT_NAME_ENG = ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'];

    this.MONTH_LONG_NAME_DE = ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'];
    this.MONTH_LONG_SHORT_DE = ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'];

    this.MONTH_LONG_NAME_ENG = ['January', 'February', 'March', 'April', 'Mai', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    this.MONTH_LONG_SHORT_ENG = ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    this.MONTH_NUMERIC = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

    this.FREE_DAY = [false, false, false, false, false, true, true];

    this.nextMonth = this.nextMonth.bind(this);
    this.lastMonth = this.lastMonth.bind(this);
    this.show = this.show.bind(this);
    this.createCalendar = this.createCalendar.bind(this);
    this.callback;
  }



  nextMonth() {
    if (this.month == 11) {
      this.year++;
    }
    this.month = ((this.month + 12 + 1) % 12);
    $(".month").fadeOut(1000).remove();
    $(this.createCalendar()).insertAfter(".daysOfWeek").hide().fadeIn(1000);
    $(".monthAndYear").empty();
    $(".monthAndYear").append(this.MONTH_LONG_NAME_DE[this.month] + " " + this.year).hide().fadeIn(200);
  }

  lastMonth() {
    if (this.month == 0) {
      this.year--;
    }
    this.month = ((this.month + 12 - 1) % 12);
    $(".month").fadeOut(1000).remove();
    $(this.createCalendar()).insertAfter(".daysOfWeek").hide().fadeIn(1000);
    $(".monthAndYear").empty();
    $(".monthAndYear").append(this.MONTH_LONG_NAME_DE[this.month] + " " + this.year).hide().fadeIn(200);
  }

  show(node) {
    var self = this;

    var calendarHeader = $("<div class=\"calendarHeader\"></div>");
    var backwards = $(feather.icons["chevron-left"].toSvg({
      class: 'backwards'
    }));
    var monthAndYear = $("<div class=\"monthAndYear\"></div>");
    monthAndYear.append(self.MONTH_LONG_NAME_DE[self.month] + " " + self.year);
    $(backwards).click(function(monthAndYear) {
      self.lastMonth()
    });
    var forward = $(feather.icons["chevron-right"].toSvg({
      class: 'forward'
    }));
    $(forward).click(function(monthAndYear) {
      self.nextMonth()
    });
    calendarHeader.append(backwards);
    calendarHeader.append(monthAndYear);
    calendarHeader.append(forward);
    $(node).append(calendarHeader);

    var daysOfWeek = $("<div class=\"daysOfWeek\"></div>");
    for (var d = 0; d < self.WEEK_SHORT_NAME_DE.length; d++) {
      var weekDay = $("<div class=\"weekDay\"></div>");
      $(weekDay).append(self.WEEK_SHORT_NAME_DE[d]);
      $(daysOfWeek).append(weekDay);
    }
    $(node).append(daysOfWeek);
    $(node).append(this.createCalendar());
  }

  createCalendar() {

    var self = this;

    var daysOfMonth = this.cal.monthDays(this.year, this.month);
    var daysOfLastMonth;
    var daysOfNextMonth;

    var lastMonth = (this.month + 12 - 1) % 12;
    if (lastMonth != 11) {
      daysOfLastMonth = this.cal.monthDays(this.year, lastMonth);
    } else {
      daysOfLastMonth = this.cal.monthDays((this.year - 1), lastMonth);
    }
    var nextMonth = (this.month + 12 + 1) % 12;
    if (nextMonth != 0) {
      daysOfNextMonth = this.cal.monthDays(this.year, nextMonth);
    } else {
      daysOfNextMonth = this.cal.monthDays(this.year + 1, nextMonth);
    }
    var monthContainer = $("<div class=\"month\"></div>");

    var numberOfWeeksLastMonth = daysOfLastMonth.length;
    var lastWeekOfLastMonth = daysOfLastMonth[numberOfWeeksLastMonth - 1];
    var firstWeekOfNextMonth = daysOfNextMonth[0];



    for (var w = 0; w < daysOfMonth.length; w++) {
      var week = daysOfMonth[w];


      var weekContainer = $("<div class=\"week\"></div>");
      for (var d = 0; d < week.length; d++) {
        var dayContainer;
        if (week[d] == 0) {
          if (w == 0) {
            dayContainer = $("<div class=\"day lastMonth\">" + lastWeekOfLastMonth[d] + "</div>");
          } else {
            dayContainer = $("<div class=\"day nextMonth\">" + firstWeekOfNextMonth[d] + "</div>");
          }
        } else {
          dayContainer = $("<div class=\"day\">" + week[d] + "</div>");
        }

        if (this.FREE_DAY[d] == true) {
          $(dayContainer).addClass("free").removeClass("day");
        } else {
          $(dayContainer).click(function() {
            $('.day-selected').removeClass('day-selected');
            $(this).addClass('day-selected');

            if (self.day !== $(this).text()) {
              self.day = $(this).text()

              var event = new AtomicLimesEvent('selectedDateEvent', {
                detail: {
                  day: self.day,
                  month: self.month,
                  year: self.year
                }
              })
              event.dispatch()
            }

            // self.plannedProduction.plannedProductionTime.day = self.day;
            // self.plannedProduction.plannedProductionTime.month = self.MONTH_NUMERIC[self.month];
            // self.plannedProduction.plannedProductionTime.year = self.year;
            // self.callback();
          });
        }
        $(weekContainer).append(dayContainer);
      };
      $(monthContainer).append(weekContainer);
    }
    return monthContainer;
  }
}
