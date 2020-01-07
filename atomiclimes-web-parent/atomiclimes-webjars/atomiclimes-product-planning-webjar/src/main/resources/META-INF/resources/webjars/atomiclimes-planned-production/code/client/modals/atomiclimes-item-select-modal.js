import AtomicLimesModal from './atomiclimes-modal.js'
import AtomicLimesSlideMenu from './atomiclimes-slide-menu.js'
import AtomicLimesItemSelectContainer from './atomiclimes-item-select-container.js'
import AtomicLimesTimeChooser from './atomiclimes-time-selection.js'
import AtomicLimesProductQuantitySettings from './atomiclimes-product-quantity-settings.js'
import AtomicLimesPlannedProduction from '../atomiclimes-planned-production.js'
import AtomicLimesEvent from '../atomiclimes-event.js'

export default class AtomicLimesItemSelectModal extends AtomicLimesModal {
  constructor(predecessor, successor, config) {
    super()
    const self = this
    self.config = config
    self.addedPlannedProduction = {
      productionItem: null,
      plannedProductionDate: AtomicLimesPlannedProduction.date,
      plannedProductionTime: null,
      quantity: null,
      unit: null
    }
    self.predecessor = predecessor
    self.successor = successor
  }

  submit() {
    const self = this
    if (window.calculate) {
      self.config.submit.drawPlannedProduction(window.calculate(JSON.stringify(self.predecessor), JSON.stringify(self.addedPlannedProduction), JSON.stringify(self.successor), JSON.stringify(self.config.plannedProduction)).then(function(plannedProduction) {
        var changedPlannedProductionEvent = new AtomicLimesEvent('changedPlannedProductionEvent', {
          detail: {
            plannedProduction: plannedProduction
          }
        })
        changedPlannedProductionEvent.dispatch()
        return plannedProduction
      }))
    } else {
      console.log('wicket function calculate was not defined')
    }
    self.close()
  }

  showSlideMenu() {
    const self = this
    self.show()
    var slideMenu = new AtomicLimesSlideMenu(self)
    var itemSelectContainer = new AtomicLimesItemSelectContainer(self.addedPlannedProduction)
    var timeChooser = new AtomicLimesTimeChooser(8, 0, self.addedPlannedProduction)
    var productQuantitySettings = new AtomicLimesProductQuantitySettings(self.addedPlannedProduction)
    slideMenu.addMenuEntry(itemSelectContainer, 'Select Production Item')
    slideMenu.addMenuEntry(timeChooser, 'Select Production Time')
    slideMenu.addMenuEntry(productQuantitySettings, 'Enter Product Quantity')
  }
}
