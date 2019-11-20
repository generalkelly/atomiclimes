import SVG from 'svg.js'
import 'svg.easing.js'
import $ from 'jquery'
import 'jquery.nicescroll'
import TimePrinter from './time-printer.js'
import AtomicLimesItemSelectModal from './modals/atomiclimes-item-select-modal.js'
import AtomicLimesPlusButton from './atomiclimes-plus-button.js'

export default function AtomicLimesTimePlanner(node) {
  const self = this
  self.draw = null
  self.date = null
  self.id = new UUID().toString()
  self.node = node
  self.config = {
    lineHeight: 90,
    lineWidth: 700,
    workingHours: {
      from: '08:00',
      till: '20:00'
    }
  }
  self._draw()
}

AtomicLimesTimePlanner.prototype._draw = function() {
  const self = this
  var drawing = $('<div id="drawing"></div>')
  drawing.empty()
  self.node.append(drawing)
  $(function() {
    self.node.niceScroll('#drawing', {
      cursorwidth: '2px',
      scrollspeed: 5,
      mousescrollstep: 2,
      smoothscroll: true
    })
  })
}

AtomicLimesTimePlanner.prototype.drawPlannedProduction = function(plannedProductionByDate) {
  const self = this
  var outer = SVG('drawing').size(this.config.lineWidth, this.config.lineHeight * 24)
  this.draw = outer.nested()
  for (var i = 0; i <= 23; i++) {
    var yLevel = i * this.config.lineHeight
    var timePrinter = new TimePrinter()
    this.draw.text(timePrinter.printTime(i, 0)).font({
      size: 12
    }).move(0, yLevel).font({
      leading: '0.5em'
    })
    this.draw.line(35, yLevel, this.config.lineWidth - 10, yLevel).stroke({
      width: 0.3
    })
    this.draw.text(timePrinter.printTime(i, 30)).font({
      size: 12
    }).move(0, +yLevel + this.config.lineHeight / 2).font({
      leading: '0.5em'
    })
    this.draw.line(35, yLevel + this.config.lineHeight / 2, this.config.lineWidth - 10, yLevel + this.config.lineHeight / 2).stroke({
      width: 0.1
    })
  }
  plannedProductionByDate.then(function(plannedProduction) {
    if (plannedProduction != null) {
      console.log(plannedProduction)
      for (var item in plannedProduction) {
        var productionItem = plannedProduction[item]
        var predecessor
        var successor
        if (item > 0) {
          predecessor = plannedProduction[Number(item) - 1]
        } else {
          predecessor = null
        }
        if (item < plannedProduction.length - 1) {
          successor = plannedProduction[Number(item) + 1]
        } else {
          successor = null
        }
        self._drawItem(productionItem, predecessor, successor)
      }
      if (Object.entries(plannedProduction).length === 0) {
        $('.addItemButton').remove()
        var plusButton = new AtomicLimesPlusButton({
          style: {
            position: 'absolute',
            top: '' + self.node.height() / 2 - 45 + 'px',
            left: '' + self.node.width() / 2 - 45 + 'px',
            height: '90px',
            width: '90px',
            borderRadius: '45px'
          }
        })
        plusButton.click(function() {
          var modal = new AtomicLimesItemSelectModal(predecessor, productionItem, self.date)
          modal.showSlideMenu()
          $('.addItemButton').remove()
        })
        self.node.append(plusButton)
      }
    }
    self.draw.move(0, 10)
  })
  if (self.node.getNiceScroll(0)) {
    self.node.getNiceScroll(0).doScrollTop(0, 0)
  }
}

AtomicLimesTimePlanner.prototype._drawItem = function(productionItem, predecessor, successor) {
  const self = this
  new AtomicLimesProductionItem(this, productionItem, predecessor, successor)
}

AtomicLimesTimePlanner.prototype.configure = function(config) {
  this.config = config
}

AtomicLimesTimePlanner.prototype._getYOffsetByTime = function(hour, minute) {
  return hour * this.config.lineHeight + minute / 60 * this.config.lineHeight
}

function AtomicLimesProductionItem(atomiclimesTimePlanner, productionItem, predecessor, successor) {
  const self = this
  this.atomiclimesTimePlanner = atomiclimesTimePlanner
  this.id = new UUID().toString()
  var date = new Date(productionItem.plannedProductionTime)
  var duration = productionItem.estimatedProductionDuration
  var durationInMinutes = Math.floor(duration / 60)
  self.fromHour = date.getHours()
  self.fromMinute = date.getMinutes()
  self.tillHour = self.fromHour + Math.floor(durationInMinutes / 60) + Math.floor((self.fromMinute + durationInMinutes % 60) / 60)
  self.tillMinute = (self.fromMinute + durationInMinutes % 60) % 60
  var that = this
  var yOffset = atomiclimesTimePlanner._getYOffsetByTime(self.fromHour, self.fromMinute)
  var height = atomiclimesTimePlanner._getYOffsetByTime(self.tillHour, self.tillMinute) - yOffset
  var width = atomiclimesTimePlanner.config.lineWidth - 50
  var group = atomiclimesTimePlanner.draw.group()
  var rect = this._generateRectangle(width, height, '#b7f07b')

  var itemName = this._generateItemName(productionItem)
  var itemDuration = this._generateDuration(productionItem)

  group.add(rect)
  group.add(itemName)
  group.add(itemDuration)
  group.move(40, yOffset)

  // var object = document.getElementById(this.id)
  var timer, lockTimer
  var touchduration = 800

  var onlongtouch = function() {
    if (!$('.addItemButton').length > 0) {
      var upperButton = new AtomicLimesPlusButton({
        style: {
          position: 'absolute',
          marginLeft: 0,
          marginTop: 0,
          top: yOffset - 2,
          left: width / 2 + 20
        },
        id: that.id
      })
      var lowerButton = new AtomicLimesPlusButton({
        style: {
          position: 'absolute',
          marginLeft: 0,
          marginTop: 0,
          top: yOffset + height - 20,
          left: width / 2 + 20,
        },
        id: that.id
      })
      upperButton.click(function() {
        var modal = new AtomicLimesItemSelectModal(predecessor, productionItem)
        modal.showSlideMenu()
      })
      lowerButton.click(function() {
        var modal = new AtomicLimesItemSelectModal(productionItem, successor)
        modal.showSlideMenu()
      })
      upperButton.hide()
      lowerButton.hide()
      $('#drawing').append(upperButton)
      $('#drawing').append(lowerButton)
      $('.addItemButton').fadeIn(100)
    }
  }

  var touchstart = function(event) {
    AtomicLimesPlusButton.delete(this.id)
    event.preventDefault()
    if (lockTimer) {
      return
    }
    timer = setTimeout(onlongtouch, touchduration)
    lockTimer = true
  }

  var touchend = function() {
    if (timer) {
      clearTimeout(timer)
      lockTimer = false
    }
  }

  rect.on('mousedown', touchstart)
  rect.on('mouseup', touchend)


  document.addEventListener('click', function(event) {
    AtomicLimesPlusButton.delete(event.target.id)
  }, false)

  document.addEventListener('mouseup', touchend, false)
}

AtomicLimesProductionItem.prototype._generateRectangle = function(width, height, color) {
  var rect = this.atomiclimesTimePlanner.draw.rect(width, height)
  rect.radius(4)
  rect.fill(color)
  rect.addClass('item')
  rect.attr('id', this.id)
  return rect
}

AtomicLimesProductionItem.prototype._generateItemName = function(productionItem) {
  var itemName = this.atomiclimesTimePlanner.draw.text(productionItem.productionItem.product.name)
  itemName.font({
    size: 12
  }).move(5, 0)
  return itemName
}

AtomicLimesProductionItem.prototype._generateDuration = function(productionItem) {
  const self = this
  var timePrinter = new TimePrinter()
  var itemDuration = this.atomiclimesTimePlanner.draw.text(timePrinter.printTime(self.fromHour, self.fromMinute) + '-' + timePrinter.printTime(self.tillHour, self.tillMinute))
  itemDuration.font({
    size: 12
  }).move(5, 12 + 12 / 2)
  return itemDuration
}

var UUID = function() {
  this.dt = new Date().getTime()
}

UUID.prototype.toString = function() {
  var that = this
  var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = (that.dt + Math.random() * 16) % 16 | 0
    that.dt = Math.floor(that.dt / 16)
    return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
  })
  return uuid
}
