import $ from 'jquery'
import feather from 'feather-icons'
import './atomiclimes-plus-button.css'

export default class AtomicLimesPlusButton {
  constructor(config) {
    const self = this
    self._config = config
    self.button = $('<div class=' + self._config.class + ' buttonId=' + self._config.id + '>' + '<canvas/>' + feather.icons['plus'].toSvg({
      'stroke-width': 2,
      fill: 'white'
    }) + '</div>')

    if (Object.entries(self._config.style).length > 0) {
      self.button.css(self._config.style)
    }
    return self.button
  }

  set config(config) {
    const self = this
    self._config = config
  }

  get config() {
    const self = this
    return self._config
  }

  click(func) {
    const self = this
    self.button.click(func)
  }

  static delete(id) {
    if ($('.addItemButton').length > 0) {
      if (document.getElementsByClassName('addItemButton')[0].getAttribute('buttonId') !== id) {
        $('.addItemButton').fadeOut(100, function() {
          $('.addItemButton').remove()
        })
      }
    }
  }
}
