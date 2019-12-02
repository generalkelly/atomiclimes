import $ from 'jquery'
import 'jquery.nicescroll'
import feather from 'feather-icons'
import './atomiclimes-modal.css'

export default class AtomicLimesModal {
  constructor(content) {
    // this.title = title
    this.content = content
    this.id = Math.random()
  }

  _createModal() {
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
      'class': 'modal-dialog'
    })
    var modalContent = $('<div></div>')
    modalContent.attr({
      'class': 'modal-content'
    })
    self.modalHeader = $('<div></div>')
    self.modalHeader.attr({
      'class': 'modal-header'
    })
    // self.modalTitle = $('<div></div>')
    // self.modalTitle.attr({
    //   'class': 'modal-title',
    //   'id': 'exampleModalLongTitle'
    // })
    self.modalBody = $('<div></div>')
    self.modalBody.attr({
      'class': 'modal-body'
    })
    self.modalFooter = $('<div></div>')
    self.modalFooter.attr({
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

    // self.modalTitle.text(this.title)
    // self.modalHeader.append(self.modalTitle)
    modalContent.append(self.modalHeader)
    modalContent.append(self.modalBody)
    self.modalFooter.append(dismissButton)
    self.modalFooter.append(saveButton)
    modalContent.append(self.modalFooter)
    modalDialog.append(modalContent)
    modal.append(modalDialog)
    return modal
  }
  submit() {}

  delete() {
    var modals = $('[name=atomiclimes-modal]')
    if (modals.length > 0) {
      modals.fadeOut(200)
      modals.remove()
    }
  }

  show() {
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

  close() {
    $(document.getElementById(this.id)).modal('hide')
    this.delete()
  }
}
