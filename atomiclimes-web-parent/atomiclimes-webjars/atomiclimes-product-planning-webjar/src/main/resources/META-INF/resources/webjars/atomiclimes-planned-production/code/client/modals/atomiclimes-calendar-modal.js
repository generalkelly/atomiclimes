import AtomicLimesModal from './atomiclimes-modal.js'
import AtomicLimesDateChooser from '../atomiclimes-date-chooser.js'
import AtomicLimesPlannedProduction from '../atomiclimes-planned-production.js'

export default class AtomicLimesCalendarModal extends AtomicLimesModal {
  constructor(plannedProduction) {
    super('Select Date')
    const self = this
    self.plannedProduction = plannedProduction
  }

  showCalendar() {
    const self = this
    self.show()
    var dateChooser = new AtomicLimesDateChooser(31, 10, 2019, null)
    dateChooser.show('.modal-body')
    this.modalHeader.text('Select Production Date')
  }

  submit() {
    const self = this
    self.plannedProduction._generateHeaderDate(AtomicLimesPlannedProduction.currentDate)
    self.plannedProduction.timePlanner.drawPlannedProduction(self.plannedProduction.getProductionPlanningByDate(AtomicLimesPlannedProduction.currentDate))
    self.close()
  }
}
