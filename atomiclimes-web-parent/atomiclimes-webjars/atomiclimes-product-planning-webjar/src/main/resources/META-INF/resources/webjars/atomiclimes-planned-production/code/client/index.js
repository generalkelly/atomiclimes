import $ from 'jquery'
import 'popper.js'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import './main.css'
import limesLogo from './atomiclimes.svg'
import AtomicLimesPlannedProduction from './atomiclimes-planned-production'
import './atomiclimes-ajax'
import 'animate.css/animate.min.css'


$(window).on('load', function() {
  $('.limesLogo').attr('src', limesLogo);
  var test = new AtomicLimesPlannedProduction('.planned-production')
})

window.calculate = function() {
  return new Promise(function(resolve, reject) {
    resolve({})
  })
}

window.getProductionItems = function() {
  return new Promise(function(resolve, reject) {

    resolve([{
      "id": 3,
      "product": {
        "name": "Bier"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 9,
      "product": {
        "name": "Dunkel"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 4,
      "product": {
        "name": "Eistee"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 11,
      "product": {
        "name": "Eistee, Blaubeere"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 10,
      "product": {
        "name": "Eistee, Pfirsich"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 6,
      "product": {
        "name": "Eistee, Zitrone"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 5,
      "product": {
        "name": "Kellerbier"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 7,
      "product": {
        "name": "Schlabbesebbel"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }, {
      "id": 8,
      "product": {
        "name": "Schwanenbräu"
      },
      "packaging": [{
        "name": "Flasche 0.3L",
        "capacity": 0.3,
        "unit": "LITERS",
        "duration": {
          "seconds": 15,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }, {
        "name": "Kasten, 10er",
        "capacity": 10.0,
        "unit": "UNITS",
        "duration": {
          "seconds": 2,
          "zero": false,
          "negative": false,
          "nano": 0,
          "units": ["SECONDS", "NANOS"]
        }
      }]
    }])
  })
}
