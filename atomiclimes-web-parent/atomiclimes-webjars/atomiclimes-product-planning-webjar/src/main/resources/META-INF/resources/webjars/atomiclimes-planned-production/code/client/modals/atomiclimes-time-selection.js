// import _ from 'lodash'
import $ from 'jquery'
import feather from 'feather-icons'
import './atomiclimes-time-selection.css'
import AtomicLimesPlannedProduction from '../atomiclimes-planned-production.js'

export default class AtomicLimesTimeChooser {

  constructor(hours, minutes, addedPlannedProduction) {
    const self = this
    self.addedPlannedProduction = addedPlannedProduction
    self.hours = hours
    self.minutes = minutes
    self.timeout
    self.minuteField = $('<div>' + self.printMinutes() + '</div>')
    self.minuteField.attr({
      class: 'inputField'
    })
    self.hourField = $('<div>' + self.printHours() + '</div>')
    self.hourField.attr({
      class: 'inputField'
    })
    self.clearButton = this.clearButton.bind(this)
    self.hoursUp = this.hoursUp.bind(this)
    self.hoursDown = this.hoursDown.bind(this)
    self.minutesUp = this.minutesUp.bind(this)
    self.minutesDown = this.minutesDown.bind(this)
    self.pressButton = this.pressButton.bind(this)
    self._show = this._show.bind(this)
    return self._show()
  }

  clearButton() {
    clearInterval(this.timeout)
    return false
  }

  printHours() {
    const self = this
    self._setCurrentTime()
    if (this.hours < 10) {
      return "0" + this.hours
    } else {
      return this.hours
    }
  }

  hoursUp() {
    const self = this
    self.hours = (24 + self.hours + 1) % 24
    self.hourField.text(self.printHours())
  }

  hoursDown() {
    const self = this
    self.hours = (24 + self.hours - 1) % 24
    self.hourField.text(self.printHours())
  }

  printMinutes() {
    const self = this
    self._setCurrentTime()
    if (this.minutes < 10) {
      return "0" + this.minutes
    } else {
      return this.minutes
    }
  }

  minutesUp() {
    const self = this
    self.minutes = (60 + self.minutes + 1) % 60
    self.minuteField.text(self.printMinutes())
  }

  minutesDown() {
    const self = this
    self.minutes = (60 + self.minutes - 1) % 60
    self.minuteField.text(self.printMinutes())
  }

  _setCurrentTime() {
    const self = this
    var dateTime = AtomicLimesPlannedProduction.currentDate
    dateTime.setHours(self.hours)
    dateTime.setMinutes(self.minutes)
    dateTime.setSeconds(0)
    self.addedPlannedProduction.plannedProductionTime = dateTime.toOffsetDateTime()
  }

  pressButton(func) {
    this.timeout = setInterval(function() {
      func()
    }, 100)
    return false
  }

  _show() {
    const self = this
    var hourTimer = $("<div class=\"timer\"></div>")
    var hourUpButton = $(feather.icons["chevron-up"].toSvg({
      class: 'button',
      id: 'hour_up'
    }))
    var hourDownButton = $(feather.icons["chevron-down"].toSvg({
      class: 'button',
      id: 'hour_down'
    }))

    var minuteTimer = $("<div class=\"timer\"></div>")
    var minuteUpButton = $(feather.icons["chevron-up"].toSvg({
      class: 'button',
      id: 'minute_up'
    }))
    var minuteDownButton = $(feather.icons["chevron-down"].toSvg({
      class: 'button',
      id: 'minute_down'
    }))


    $(hourUpButton).click(function() {
      self.hoursUp()
    })

    $(hourUpButton).mousedown(function() {
      self.pressButton(self.hoursUp)
    })

    $(hourDownButton).click(function() {
      self.hoursDown()
    })

    $(hourDownButton).mousedown(function() {
      self.pressButton(self.hoursDown)
    })

    $(minuteUpButton).click(function() {
      self.minutesUp()
    })

    $(minuteUpButton).mousedown(function() {
      self.pressButton(self.minutesUp)
    })

    $(minuteDownButton).click(function() {
      self.minutesDown()
    })

    $(minuteDownButton).mousedown(function() {
      self.pressButton(self.minutesDown)
    })

    $(document).mouseup(function() {
      self.clearButton()
    })

    var timeChooser = $('<div></div>')
    timeChooser.attr({
      class: 'timeChooser'
    })

    hourTimer.append(hourUpButton)
    hourTimer.append(self.hourField)
    hourTimer.append(hourDownButton)
    $(timeChooser).append(hourTimer)

    minuteTimer.append(minuteUpButton)
    minuteTimer.append(self.minuteField)
    minuteTimer.append(minuteDownButton)
    $(timeChooser).append(minuteTimer)
    return timeChooser
  }
}
