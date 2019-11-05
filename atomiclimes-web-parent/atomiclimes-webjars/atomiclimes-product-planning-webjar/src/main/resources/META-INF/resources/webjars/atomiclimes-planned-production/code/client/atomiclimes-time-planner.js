import SVG from 'svg.js'
import 'svg.easing.js'
import $ from 'jquery'
import 'jquery.nicescroll'
import TimePrinter from './time-printer.js'
import AtomicLimesModal from './atomiclimes-modal.js'
import feather from 'feather-icons'

var plannedProduction = {
  items: [{
      name: 'Bier',
      from: {
        hour: 8,
        minute: 30
      },
      till: {
        hour: 9,
        minute: 46
      }
    },
    {
      name: 'Eistee',
      from: {
        hour: 9,
        minute: 50
      },
      till: {
        hour: 10,
        minute: 22
      }
    },
    {
      name: 'Schwanenbräu',
      from: {
        hour: 10,
        minute: 45
      },
      till: {
        hour: 11,
        minute: 45
      }
    }
  ]
}

export default function AtomicLimesTimePlanner(node) {
  this.draw = null
  this.id = new UUID().toString()
  this.node = node
  this.config = {
    lineHeight: 90,
    lineWidth: 700,
    workingHours: {
      from: '08:00',
      till: '20:00'
    }
  }
  this.drawPlannedProduction(plannedProduction)
}

AtomicLimesTimePlanner.prototype._draw = function() {
  this.node.empty()
  this.node.append('<div id="drawing"></div>')
  this.node.niceScroll('#drawing', {
    cursorwidth: '2px',
    scrollspeed: 5,
    mousescrollstep: 5,
    smoothscroll: true
  })

  var outer = SVG('drawing').size(this.config.lineWidth, this.config.lineHeight * 24)
  this.draw = outer.nested()
  for (var i = 0; i <= 23; i++) {
    var yLevel = i * this.config.lineHeight
    var timePrinter = new TimePrinter()
    this.draw.text(timePrinter.printTime(i, 0)).font({
      size: 12,
    }).move(0, yLevel).font({
      leading: '0.5em'
    })
    this.draw.line(35, yLevel, this.config.lineWidth - 10, yLevel).stroke({
      width: 0.3
    })
    this.draw.text(timePrinter.printTime(i, 30)).font({
      size: 12,
    }).move(0, +yLevel + this.config.lineHeight / 2).font({
      leading: '0.5em'
    })
    this.draw.line(35, yLevel + this.config.lineHeight / 2, this.config.lineWidth - 10, yLevel + this.config.lineHeight / 2).stroke({
      width: 0.1,
    })
  }
}

AtomicLimesTimePlanner.prototype.drawPlannedProduction = function(plannedProduction) {
  this._draw()
  if (plannedProduction != null) {
    for (var item in plannedProduction.items) {
      var productionItem = plannedProduction.items[item]
      var predecessor
      var successor
      if (item > 0) {
        predecessor = plannedProduction.items[Number(item) - 1]
      } else {
        predecessor = null
      }
      if (item < plannedProduction.items.length - 1) {
        successor = plannedProduction.items[Number(item) + 1]
      } else {
        successor = null
      }
      this._drawItem(productionItem, predecessor, successor)
    }
  }
  this.draw.move(0, 10)
}

AtomicLimesTimePlanner.prototype._drawItem = function(productionItem, predecessor, successor) {
  new AtomicLimesProductionItem(this, productionItem, predecessor, successor)
}

AtomicLimesTimePlanner.prototype.configure = function(config) {
  this.config = config
}

AtomicLimesTimePlanner.prototype._getYOffsetByTime = function(hour, minute) {
  return hour * this.config.lineHeight + minute / 60 * this.config.lineHeight
}

function AtomicLimesPlusButton(yOffset, width, id) {
  this.button = $('<div class="addItemButton" buttonId=' + id + '>' + '<canvas/>' + feather.icons['plus'].toSvg({
    'stroke-width': 2,
    fill: 'white'
  }) + '</div>')

  this.button.css({
    position: 'absolute',
    marginLeft: 0,
    marginTop: 0,
    top: yOffset - 2,
    left: width / 2 + 20
  })

  return this.button
}

AtomicLimesPlusButton.prototype.click = function(func) {
  this.button.click(func)
}

AtomicLimesPlusButton.delete = function(id) {
  if ($('.addItemButton').length > 0) {
    if (document.getElementsByClassName('addItemButton')[0].getAttribute('buttonId') !== id) {
      $('.addItemButton').fadeOut(100, function() {
        $('.addItemButton').remove()
      })
    }
  }
}



function AtomicLimesProductionItem(atomiclimesTimePlanner, productionItem, predecessor, successor) {
  this.atomiclimesTimePlanner = atomiclimesTimePlanner
  this.id = new UUID().toString()
  var that = this
  var yOffset = atomiclimesTimePlanner._getYOffsetByTime(productionItem.from.hour, productionItem.from.minute)
  var height = atomiclimesTimePlanner._getYOffsetByTime(productionItem.till.hour, productionItem.till.minute) - yOffset
  var width = atomiclimesTimePlanner.config.lineWidth - 50
  var group = atomiclimesTimePlanner.draw.group()
  var rect = this._generateRectangle(width, height, '#b7f07b')

  var itemName = this._generateItemName(productionItem)
  var itemDuration = this._generateDuration(productionItem)

  group.add(rect)
  group.add(itemName)
  group.add(itemDuration)
  group.move(40, yOffset)

  var object = document.getElementById(this.id)
  var timer, lockTimer
  var touchduration = 800

  function Modal(predecessor, successor) {
    this.addedPlannedProduction = {
      productionItem: null,
      plannedProductionDate: null,
      quantity: null,
      unit: null
    }
    this.predecessor = predecessor
    this.successor = successor
  }

  Modal.prototype = new AtomicLimesModal('Please select a production item', 'inhalt')
  Modal.prototype.submit = function() {
    // TODO The promise needs a then which reprints the time planner with a save button to verify the new production planning
    console.log(this.addedPlannedProduction)
    $('.atomicLimesSelectContainer').addClass('animated fadeOutLeft')
    window.calculate(JSON.stringify(this.predecessor), JSON.stringify(this.addedPlannedProduction), JSON.stringify(this.successor))
  }
  Modal.prototype.generateProductionItem = function(item) {
    var productionItem = $('<div></div>')
    productionItem.attr({
      class: 'productionItemToSelect'
    })
    var title = $('<h5 class="optionProductName"></h5>')
    title.text(item.product.name)
    var productionItemPackaging = $('<ul></ul>')
    productionItemPackaging.attr({
      class: 'packagingToSelect'
    })
    productionItem.append(title)
    for (let packaging of item.packaging) {
      var packagingNode = $('<li></li>')
      packagingNode.text(packaging.name)
      productionItemPackaging.append(packagingNode)
    }
    productionItem.append(productionItemPackaging)
    return productionItem
  }
  Modal.prototype.showProdItems = function() {
    this.show()
    var self = this
    window.getProductionItems().then(function(json) {
      var container = $('<div></div>')
      container.attr({
        'class': 'atomicLimesSelectContainer'
      })
      var select = $('<div></div>')
      select.attr({
        'class': 'atomicLimesSelect'
      })
      for (let item of json) {
        var option = $('<div></div>')
        option.attr({
          'class': 'atomicLimesOption',
          'select': 'false'
        })
        option.click(function() {
          $('.atomicLimesOption').attr('select', 'false')
          $(this).attr('select', 'true')
          self.addedPlannedProduction.productionItem = item
        })
        option.append(self.generateProductionItem(item))
        select.append(option)
      }
      container.append(select)
      $('.modal-body').append(container)
    }).then(function() {
      $('.atomicLimesSelectContainer').niceScroll('.atomicLimesSelect', {
        cursorwidth: '5px',
        scrollspeed: 5,
        mousescrollstep: 5,
        smoothscroll: true
      })
    })
  }

  var onlongtouch = function() {
    if (!$('.addItemButton').length > 0) {
      var upperButton = new AtomicLimesPlusButton(yOffset - 2, width, that.id, predecessor, productionItem)
      var lowerButton = new AtomicLimesPlusButton(yOffset + height - 18, width, that.id, productionItem, successor)
      upperButton.click(function() {
        var modal = new Modal(predecessor, productionItem)
        modal.showProdItems()
      })
      lowerButton.click(function() {
        var modal = new Modal(productionItem, successor)
        modal.showProdItems()
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

  object.addEventListener('mousedown', touchstart, false)
  object.addEventListener('mouseup', touchend, false)

  document.addEventListener('click', function(event) {
    AtomicLimesPlusButton.delete(event.target.id)
  }, false)

  document.addEventListener('mouseup', touchend, false)
}

AtomicLimesProductionItem.prototype._generateRectangle = function(width, height, color) {
  var rect = this.atomiclimesTimePlanner.draw.rect(width, height)
  rect.radius(4)
  rect.fill(color)
  // var mousedown
  rect.addClass('item')
  rect.attr('id', this.id)
  return rect
}

AtomicLimesProductionItem.prototype._generateItemName = function(productionItem) {
  var itemName = this.atomiclimesTimePlanner.draw.text(productionItem.name)
  itemName.font({
    size: 12
  }).move(5, 0)
  return itemName
}

AtomicLimesProductionItem.prototype._generateDuration = function(productionItem) {
  var timePrinter = new TimePrinter()
  var itemDuration = this.atomiclimesTimePlanner.draw.text(timePrinter.printTime(productionItem.from.hour, productionItem.from.minute) + '-' + timePrinter.printTime(productionItem.till.hour, productionItem.till.minute))
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
    return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16)
  })
  return uuid
}
