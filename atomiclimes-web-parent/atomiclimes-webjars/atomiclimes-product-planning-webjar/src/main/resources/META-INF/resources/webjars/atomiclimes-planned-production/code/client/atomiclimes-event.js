import AtomicLimesEventListenerHolder from './atomiclimes-eventlistenerholder.js'

export default class AtomicLimesEvent {
  constructor(name, config) {
    const self = this
    self.name = name
    self.detail = config.detail
  }

  dispatch() {
    const self = this
    AtomicLimesEventListenerHolder.on(self)
  }
}
