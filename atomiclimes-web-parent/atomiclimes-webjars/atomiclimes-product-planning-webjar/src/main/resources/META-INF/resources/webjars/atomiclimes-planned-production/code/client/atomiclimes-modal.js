import $ from 'jquery'
import 'jquery.nicescroll'
import feather from 'feather-icons'

export default function AtomicLimesModal(title, content) {
  this.title = title
  this.content = content
  this.id = Math.random()
}

AtomicLimesModal.prototype._createModal = function() {
  var self = this
  var modal = $('<div></div>')
  modal.attr({
    'class': 'modal',
    'name': 'atomiclimes-modal',
    'id': this.id,
    'tabindex': -1,
    'role': 'dialog',
    'aria-labelledby': this.id,
    'aria-hidden': true
  })
  var modalDialog = $('<div></div>')
  modalDialog.attr({
    // 'class': 'modal-dialog modal-dialog-centered'
    'class': 'modal-dialog'
    // 'role': 'document'
  })
  var modalContent = $('<div></div>')
  modalContent.attr({
    'class': 'modal-content'
  })
  var modalHeader = $('<div></div>')
  modalHeader.attr({
    'class': 'modal-header'
  })
  var modalTitle = $('<h5></h5>')
  modalTitle.attr({
    'class': 'modal-title',
    'id': 'exampleModalLongTitle'
  })
  var modalBody = $('<div></div>')
  modalBody.attr({
    'class': 'modal-body'
  })
  var modalFooter = $('<div></div>')
  modalFooter.attr({
    'class': 'modal-footer'
  })
  // var dismissButton = $('<div></div>')
  var dismissButton = $('<div class = "cancelButton"></div>')
  dismissButton.append('<canvas></canvas>')
  dismissButton.append(feather.icons['x'].toSvg())
  dismissButton.attr({
    // 'class': 'btn btn-secondary',
    // 'type': 'button',
    'data-dismiss': 'modal'
  })

  var saveButton = $('<div class = "saveButton"></div>')
  saveButton.append('<canvas></canvas>')
  saveButton.append(feather.icons['check'].toSvg())
  saveButton.attr({
    // 'class': 'btn btn-primary',
    // 'type': 'button'
  })
  saveButton.click(function() {
    self.submit()
  })

  modalTitle.text(this.title)
  modalHeader.append(modalTitle)
  modalContent.append(modalHeader)
  modalContent.append(modalBody)
  modalFooter.append(dismissButton)
  modalFooter.append(saveButton)
  modalContent.append(modalFooter)
  modalDialog.append(modalContent)
  modal.append(modalDialog)
  return modal
}

AtomicLimesModal.prototype.submit = function() {

}

AtomicLimesModal.prototype.delete = function() {
  var modals = $('[name=atomiclimes-modal]')
  if (modals.length > 0) {
    modals.fadeOut(200)
    modals.remove()
  }
}

AtomicLimesModal.prototype.show = function() {
  this.delete()
  $('.planned-production').append(this._createModal())
  $(document.getElementById(this.id)).fadeIn(200)
  $(document.getElementById(this.id)).modal('show')
  $('.planned-production').addClass('after_modal_appended')
  $('.modal-backdrop').appendTo('.planned-production')
  $('.modal-backdrop').fadeIn(500)
  $('body').removeClass('modal-open')
  $('body').css('padding-right', '')
}



AtomicLimesModal.prototype.close = function() {
  $(document.getElementById(this.id)).modal('hide')
  this.delete()
}
