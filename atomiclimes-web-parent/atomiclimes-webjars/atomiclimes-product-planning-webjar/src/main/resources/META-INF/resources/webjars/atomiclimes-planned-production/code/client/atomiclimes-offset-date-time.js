export default class AtomicLimesOffsetDateTime extends Date {

  constructor(year, month, day) {
    super()
    const self = this
    if (arguments.length === 3) {
      super.setDate(day)
      super.setMonth(month)
      super.setYear(year)
    }
    var timezoneOffsetMinutes = self.getTimezoneOffset()
    self.offsetHours = parseInt(Math.abs(timezoneOffsetMinutes / 60))
    self.offsetMinutes = Math.abs(timezoneOffsetMinutes % 60)
    self.offset = ''
    self.currentDay = self.getDate()
    self.currentMonth = self.getMonth() + 1
    self.currentYear = self.getFullYear()

    if (self.offsetHours < 10) {
      self.offsetHours = self._printWithLeadingZero(self.offsetHours)
    }

    if (self.offsetMinutes < 10) {
      self.offsetMinutes = self._printWithLeadingZero(self.offsetMinutes)
    }

    if (timezoneOffsetMinutes < 0) {
      self.offset = '+' + self.offsetHours + ':' + self.offsetMinutes
    } else if (timezoneOffsetMinutes > 0) {
      self.offset = '-' + self.offsetHours + ':' + self.offsetMinutes
    } else if (timezoneOffsetMinutes === 0) {
      self.offset = 'Z'
    }
  }

  setDate(dayValue) {
    const self = this
    var date = super.setDate(dayValue)
    self.currentDay = self.getDate()
    self.currentMonth = self.getMonth() + 1
    self.currentYear = self.getFullYear()
    return date
  }

  toOffsetDateTime() {
    const self = this

    var currentHours = self._printWithLeadingZero(self.getHours())
    var currentMinutes = self._printWithLeadingZero(self.getMinutes())
    var currentSeconds = self._printWithLeadingZero(self.getSeconds())

    var offsetDateTime = self.currentYear + '-' + self._printWithLeadingZero(self.currentMonth) + '-' + self._printWithLeadingZero(self.currentDay) + 'T' + currentHours + ':' + currentMinutes + ':' + currentSeconds + self.offset
    return offsetDateTime
  }

  toISODate() {
    const self = this
    return self.currentYear + '-' + self._printWithLeadingZero(self.currentMonth) + '-' + self._printWithLeadingZero(self.currentDay)
  }

  _printWithLeadingZero(time) {
    if (time < 10) {
      time = '0' + time
    }
    return time
  }
}
