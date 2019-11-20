import $ from 'jquery'

export default class AtomicLimesItemSelectContainer {
  constructor(addedPlannedProduction) {
    const self = this
    var itemSelectContainer = $('<div></div>')
    itemSelectContainer.attr({
      'class': 'atomicLimesSelectContainer'
    })
    window.getProductionItems().then(function(json) {
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
          addedPlannedProduction.productionItem = item
        })
        option.append(self.generateProductionItem(item))
        select.append(option)
      }
      itemSelectContainer.append(select)
    }).then(function() {
      $('.atomicLimesSelectContainer').niceScroll('.atomicLimesSelect', {
        cursorwidth: '5px',
        scrollspeed: 5,
        mousescrollstep: 5,
        smoothscroll: true
      })
    })
    return itemSelectContainer
  }

  generateProductionItem(item) {
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
}
