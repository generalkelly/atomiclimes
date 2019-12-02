import $ from 'jquery'
import feather from 'feather-icons'
import './atomiclimes-slide-menu.css'

export default class AtomicLimesSlideMenu {
  constructor(modal) {
    const self = this
    self.modal = modal
    self.menuEntries = []
    self.currentEntry = {}
    self.slideMenu = $('<div></div>')
    self.slideMenu.attr({
      'class': 'slideMenu'
    })
    self.menuHeader = $('<div></div>')
    self.menuHeader.attr({
      'class': 'menuHeader'
    })
    self.previousEntryButton = $(feather.icons["chevron-left"].toSvg({
      class: 'previousEntryButton'
    }))
    self.previousEntryButton.click(function() {
      self._previousEntry()
    })
    self.menuTitle = $('<div></div>')
    self.menuTitle.attr({
      class: 'menuTitle'
    })
    self.nextEntryButton = $(feather.icons["chevron-right"].toSvg({
      class: 'nextEntryButton'
    }))
    self.nextEntryButton.click(function() {
      self._nextEntry()
    })
    self.previousEntryButtonContainer = $('<div></div>')
    self.previousEntryButtonContainer.attr({
      class: 'entryButtonContainer'
    })
    self.previousEntryButtonContainer.append(self.previousEntryButton)
    self.nextEntryButtonContainer = $('<div></div>')
    self.nextEntryButtonContainer.attr({
      class: 'entryButtonContainer'
    })
    self.nextEntryButtonContainer.append(self.nextEntryButton)
    self.menuHeader.append(self.previousEntryButtonContainer)
    self.menuHeader.append(self.menuTitle)
    self.menuHeader.append(self.nextEntryButtonContainer)
    self.modal.modalBody.append(self.slideMenu)

  }

  addMenuEntry(node, title) {
    const self = this
    var indexOfLastEntry = self.menuEntries.length - 1
    var previousEntry = null
    var menuEntry = new MenuEntry(node, title)
    if (indexOfLastEntry !== -1) {
      previousEntry = self.menuEntries[indexOfLastEntry]
      previousEntry.next = menuEntry
      menuEntry.previous = previousEntry
      menuEntry.hide()
    } else {
      self.currentEntry = menuEntry
      menuEntry.show()
      self.modal.modalHeader.append(self.menuHeader)
    }
    self.menuEntries.push(menuEntry)
    self.slideMenu.append(menuEntry.menuEntry)
    self._printHeader()
  }

  _nextEntry() {
    const self = this
    self.currentEntry.hide()
    self.currentEntry.next.show()
    self.currentEntry = self.currentEntry.next
    self._printHeader()
  }


  _previousEntry() {
    const self = this
    self.currentEntry.hide()
    self.currentEntry.previous.show()
    self.currentEntry = self.currentEntry.previous
    self._printHeader()
  }

  _printHeader() {
    const self = this
    self.menuTitle.text(self.currentEntry.title)
    if (Object.keys(self.currentEntry.next).length !== 0) {
      self.nextEntryButton.show()
    } else {
      self.nextEntryButton.hide()
    }
    if (Object.keys(self.currentEntry.previous).length !== 0) {
      self.previousEntryButton.show()
    } else {
      self.previousEntryButton.hide()
    }
  }
}

class MenuEntry {
  constructor(node, title) {
    const self = this
    self.title = title
    self.menuEntry = $('<div></div>')
    self.menuEntry.attr({
      'class': 'menuEntry'
    })
    self.menuEntry.append(node)
    self.next = {}
    self.previous = {}
  }

  hide() {
    const self = this
    self.menuEntry.hide()
  }

  show() {
    const self = this
    self.menuEntry.fadeIn(500)
  }
}
