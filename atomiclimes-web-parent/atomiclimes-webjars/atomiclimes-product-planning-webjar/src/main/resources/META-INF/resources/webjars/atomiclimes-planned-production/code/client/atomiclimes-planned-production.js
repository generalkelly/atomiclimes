import $ from 'jquery'
import './production-item-chooser.css'
import 'bootstrap'
import feather from 'feather-icons'
import AtomicLimesTimePlanner from './atomiclimes-time-planner.js'
import AtomicLimesCalendarModal from './modals/atomiclimes-calendar-modal.js'
import AtomicLimesEventListenerHolder from './atomiclimes-eventlistenerholder.js'
import AtomicLimesEvent from './atomiclimes-event.js'
import AtomicLimesOffsetDateTime from './atomiclimes-offset-date-time.js'
import AtomicLimesButton, {
  CancelButton,
  CheckButton
} from './atomiclimes-buttons.js'

export default class AtomicLimesPlannedProduction {
  constructor(node) {
    const self = this
    self.container = $(node)
    self.container.addClass('atomicLimesContainer')
    AtomicLimesPlannedProduction.currentDate = new AtomicLimesOffsetDateTime(Date.now())
    AtomicLimesPlannedProduction.date = AtomicLimesPlannedProduction.currentDate.toISODate()
    var header = $('<div class="header atomicLimesMenuBar"></div>')

    var previousDay = $('<div class="changeDay previousDay">' + '<canvas/>' + feather.icons['chevron-left'].toSvg({
      class: 'backwards'
    }) + '</div>')
    previousDay.click(function() {
      self._changeDateByDays(-1)
    })
    var nextDay = $('<div class="changeDay nextDay">' + '<canvas/>' + feather.icons['chevron-right'].toSvg({
      class: 'backwards'
    }) + '</div>')
    nextDay.click(function() {
      self._changeDateByDays(1)
    })
    self.currentDay = $('<div class="currentDay"></div>')
    self._generateHeaderDate(AtomicLimesPlannedProduction.currentDate)

    header.append(previousDay)
    header.append(self.currentDay)
    header.append(nextDay)

    self.currentDay.click(function() {
      var calendarModal = new AtomicLimesCalendarModal(self)
      calendarModal.showCalendar()
    })

    AtomicLimesEventListenerHolder.addEventListener('selectedDateEvent', function(event) {
      AtomicLimesPlannedProduction.currentDate = new AtomicLimesOffsetDateTime(event.detail.year, event.detail.month, event.detail.day)
      AtomicLimesPlannedProduction.date = AtomicLimesPlannedProduction.currentDate.toISODate()
    })

    self.plannedProductionContainer = $('<div class="plannedProductionContentContainer"></div>')
    var menuFooter = $('<div class="menuFooter atomicLimesMenuBar"></div>')
    // var cancelButton = $('<div class = "cancelButton"></div>')
    // cancelButton.append('<canvas></canvas>')
    // cancelButton.append(feather.icons['x'].toSvg())
    var cancelButton = new CancelButton()

    // var saveButton = $('<div class = "saveButton"></div>')
    // saveButton.append('<canvas></canvas>')
    // saveButton.append(feather.icons['check'].toSvg())

    var saveButton = new CheckButton()

    menuFooter.append(cancelButton.node)
    cancelButton.hide()
    menuFooter.append(saveButton.node)
    saveButton.hide()

    AtomicLimesEventListenerHolder.addEventListener('changedPlannedProductionEvent', function(event) {
      self.plannedProductionOnDisplay = event.detail.plannedProduction
      cancelButton.show()
      saveButton.show()
    })

    AtomicLimesEventListenerHolder.addEventListener('acknowledgedPlannedProductionEvent', function(event) {
      cancelButton.hide()
      saveButton.hide()
    })

    cancelButton.click(function() {
      var acknowledgedPlannedProductionEvent = new AtomicLimesEvent('acknowledgedPlannedProductionEvent', {})
      acknowledgedPlannedProductionEvent.dispatch()
    })

    saveButton.click(function() {
      var acknowledgedPlannedProductionEvent = new AtomicLimesEvent('acknowledgedPlannedProductionEvent', {})
      acknowledgedPlannedProductionEvent.dispatch()
      if (window.saveOrUpdateProductionPlanning) {
        window.saveOrUpdateProductionPlanning(JSON.stringify(self.plannedProductionOnDisplay))
      }
    })

    this.container.append(header)
    this.container.append(self.plannedProductionContainer)
    this.container.append(menuFooter)

    self.timePlanner = new AtomicLimesTimePlanner(self.plannedProductionContainer)
    self.timePlanner.drawPlannedProduction(this.getProductionPlanningByDate(AtomicLimesPlannedProduction.currentDate))

    var width = this.container.width()

    this.container.css({
      position: 'absolute',
      top: '50%',
      left: '50%',
      'margin-left': (-width / 2) + 'px'
    })
  }

  _generateHeaderDate(date) {
    const self = this
    self.currentDay.text(date.toLocaleDateString())
  }

  getProductionPlanningByDate(date) {
    if (window.getProductionPlanningByDate) {
      return window.getProductionPlanningByDate(date.toISODate())
    } else {
      console.log('wicket function getProductionPlanningFor was not defined')
    }
  }

  _changeDateByDays(days) {
    const self = this
    AtomicLimesPlannedProduction.currentDate.setDate(AtomicLimesPlannedProduction.currentDate.getDate() + days)
    self._generateHeaderDate(AtomicLimesPlannedProduction.currentDate)
    AtomicLimesPlannedProduction.date = AtomicLimesPlannedProduction.currentDate.toISODate()
    self.timePlanner.drawPlannedProduction(self.getProductionPlanningByDate(AtomicLimesPlannedProduction.currentDate))
  }
}

AtomicLimesPlannedProduction.date = null
