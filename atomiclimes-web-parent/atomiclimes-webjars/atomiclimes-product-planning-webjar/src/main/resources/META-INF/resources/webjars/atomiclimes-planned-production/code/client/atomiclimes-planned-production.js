import $ from 'jquery'
import './production-item-chooser.css'
import 'jquery.nicescroll'
import 'bootstrap'
import feather from 'feather-icons'
import AtomicLimesTimePlanner from './atomiclimes-time-planner.js'
import AtomicLimesDateChooser from './atomiclimes-date-chooser.js'
import AtomicLimesModal from './atomiclimes-modal.js'

export default function AtomicLimesPlannedProduction(node) {
  this.container = $(node)
  this.container.addClass('atomicLimesContainer')
  var header = $('<div class="header atomicLimesMenuBar"></div>')

  var previousDay = $('<div class=changeDay>' + '<canvas/>' + feather.icons['chevron-left'].toSvg({
    class: 'backwards'
  }) + '</div>')
  var nextDay = $('<div class=changeDay>' + '<canvas/>' + feather.icons['chevron-right'].toSvg({
    class: 'backwards'
  }) + '</div>')
  var currentDay = $('<div class="nextDay">02.10.2019</div>')

  header.append(previousDay)
  header.append(currentDay)
  header.append(nextDay)
  header.click(function() {
    function Modal() {

    }
    Modal.prototype = new AtomicLimesModal('Select Date')
    Modal.prototype.showCalendar = function() {
      this.show()
      var dateChooser = new AtomicLimesDateChooser(31, 10, 2019, null)
      dateChooser.show('.modal-body')
    }
    var calendarModal = new Modal()
    calendarModal.showCalendar()
  })

  var plannedProductionContainer = $('<div class="plannedProductionContentContainer"></div>')
  var menuFooter = $('<div class="menuFooter atomicLimesMenuBar"></div>')
  var cancelButton = $('<div class = "cancelButton"></div>')
  cancelButton.append('<canvas></canvas>')
  cancelButton.append(feather.icons['x'].toSvg())

  var saveButton = $('<div class = "saveButton"></div>')
  saveButton.append('<canvas></canvas>')
  saveButton.append(feather.icons['check'].toSvg())

  menuFooter.append(cancelButton)
  menuFooter.append(saveButton)

  this.container.append(header)
  this.container.append(plannedProductionContainer)
  this.container.append(menuFooter)

  new AtomicLimesTimePlanner(plannedProductionContainer)

  var width = this.container.width()
  var height = this.container.height()

  this.container.css({
    position: 'absolute',
    top: '50%',
    left: '50%',
    'margin-left': (-width / 2) + 'px',
    // 'margin-top': (height / 2) + 'px'
    // margin: '' + (height / 2) + 'px' + ' 0 0 ' + (-width / 2) + 'px'
  })
}

AtomicLimesPlannedProduction.prototype.submit = function(preceedingPlannedProduction, addedPlannedProduction, subsequentPlannedProduction) {
  if (window.calculate) {
    calculate(JSON.stringify(preceedingPlannedProduction), JSON.stringify(addedPlannedProduction), JSON.stringify(subsequentPlannedProduction))
  } else {
    console.log('wicket function calculate was not defined')
  }
}

AtomicLimesPlannedProduction.prototype.getProductionPlanningByDate = function(date) {
  if (window.getProductionPlanningByDate) {
    getProductionPlanningByDate(JSON.stringify(date))
  } else {
    console.log('wicket function getProductionPlanningFor was not defined')
  }
}
