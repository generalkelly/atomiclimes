export default class AtomicLimesEventListenerHolder {
  static get eventListenerHolder() {
    const self = this
    return self._eventListenerHolder
  }

  static addEventListener(name, listener) {
    const self = this
    if (self._eventListenerHolder === void(0)) {
      self._eventListenerHolder = {}
    }
    self._eventListenerHolder[name] = []
    self._eventListenerHolder[name].push(listener)
  }

  static on(event) {
    const self = this
    if (self._eventListenerHolder === void(0)) {
      self._eventListenerHolder = {}
    }
    for (let eventListener in self._eventListenerHolder[event.name]) {
      self._eventListenerHolder[event.name][eventListener](event)
    }
  }
}
