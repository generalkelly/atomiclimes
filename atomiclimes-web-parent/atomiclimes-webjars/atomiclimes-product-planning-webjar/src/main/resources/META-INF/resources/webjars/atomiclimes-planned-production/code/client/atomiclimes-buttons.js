import $ from 'jquery'
import feather from 'feather-icons'

export default class AtomicLimesButton {

  constructor(conf) {
    const self = this
    self.config = conf
    self.n = $('<div></div>')
    self.n.append('<canvas></canvas>')
    self.n.attr({
      class: conf.class
    })
  }

  get node() {
    const self = this
    return self.n
  }

  set node(n) {
    const self = this
    self.n = n
  }

  click(callback) {
    const self = this
    self.n.click(callback)
  }

  show(callback) {
    const self = this
    self.symbol.show()
    if (callback instanceof Function) {
      callback()
    }
  }

  hide(callback) {
    const self = this
    self.symbol.hide()
    if (callback instanceof Function) {
      callback()
    }
  }
}

export class CheckButton extends AtomicLimesButton {

  constructor() {
    super({
      class: 'saveButton'
    })
    const self = this
    self.symbol = $(feather.icons['check'].toSvg())
    self.node.append(self.symbol)
  }

}

export class CancelButton extends AtomicLimesButton {

  constructor() {
    super({
      class: 'cancelButton'
    })
    const self = this
    self.symbol = $(feather.icons['x'].toSvg())
    self.node.append(self.symbol)
  }

}
