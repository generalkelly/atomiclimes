window.AtomicLimes = {
  Ajax: {
    ajax: function(attrs) {
      return new Promise(function(resolve, reject) {
        $.ajax({
          url: attrs.u,
          type: 'GET',
          cache: false,
          data: attrs,
          dataType: 'json',
          complete: function(json) {
            resolve(json)
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log('error', textStatus, errorThrown)
            reject()
          }
        })
      })
    }
  }
}
