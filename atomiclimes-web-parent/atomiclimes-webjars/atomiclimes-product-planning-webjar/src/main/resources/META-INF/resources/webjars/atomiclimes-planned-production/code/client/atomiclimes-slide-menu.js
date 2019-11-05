export default class AtomicLimesSlideMenu {
  constructor() {
    const self = this
    self.menuEntries = []
    self.currentEntry
  }

  addMenuEntry(node) {
    const self = this
    var indexOfLastEntry = self.menuEntries.length - 1
    var previousEntry = null
    var menuEntry = new MenuEntry(node)
    if (indexOfLastEntry !== -1) {
      previousEntry = self.menuEntries[indexOfLastEntry]
      previousEntry.next = menuEntry
      menuEntry.previous = previousEntry
    } else {
      self.currentEntry = menuEntry
    }
    self.menuEntries.push(menuEntry)
  }

  nextEntry() {
    const self = this
    self.currentEntry.hide(function() {
      this.menuEntry.hide(500)
    })
    self.currentEntry.next.show(function() {
      this.menuEntry.show(500)
    })
    self.currentEntry = self.currentEntry.next
  }

  previousEntry() {
    const self = this
    self.currentEntry.hide(function() {
      this.menuEntry.hide(500)
    })
    self.currentEntry.next.show(function() {
      this.menuEntry.show(500)
    })
    self.currentEntry = self.currentEntry.previous
  }
}

class MenuEntry {
  constructor(node) {
    const self = this
    self.menuEntry = $('<div></div>')
    menuEntry.attr({
      'class': 'menuEntry'
    })
    menuEntry.append(node)
    self.next = {}
    self.previous = {}
  }

  hide(hideFunction) {
    hideFunction()
  }

  show(showFunction) {
    showFunction()
  }
}
