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

window.getProductionPlanningByDate = function(date) {
  return new Promise(
    function(resolve, reject) {
      if (date === '2019-11-21') {
        resolve(
          [{
            "id": 7,
            "productionItem": {
              "id": 1,
              "product": {
                "name": "Dunkel"
              },
              "packaging": [{
                  "name": "Flasche 0.3L",
                  "capacity": 0.3,
                  "unit": "LITERS",
                  "duration": "2"
                },
                {
                  "name": "Kasten, 10er, blau",
                  "capacity": 10,
                  "unit": "UNITS",
                  "duration": "2"
                }
              ]
            },
            "subsequentPlannedNonproductiveStages": [],
            "updateTimestamp": {
              "offset": {
                "totalSeconds": 3600,
                "id": "+01:00",
                "rules": {
                  "transitions": [],
                  "transitionRules": [],
                  "fixedOffset": true
                }
              },
              "dayOfMonth": 20,
              "dayOfWeek": "WEDNESDAY",
              "dayOfYear": 324,
              "hour": 10,
              "minute": 37,
              "second": 46,
              "nano": 0,
              "year": 2019,
              "month": "NOVEMBER",
              "monthValue": 11
            },
            "plannedProductionDate": "2019-11-20",
            "plannedProductionTime": "2019-11-21T09:00+01:00",
            "quantity": 8,
            "unit": "LITERS",
            "estimatedProductionDuration": "52",
            "productionStageType": "PRODUCTIVE"
          }]
          //   {
          //   items: [
          //     {
          //       name: 'Bier',
          //       from: {
          //         hour: 8,
          //         minute: 30
          //       },
          //       till: {
          //         hour: 9,
          //         minute: 46
          //       }
          //     },
          //     {
          //       name: 'Eistee',
          //       from: {
          //         hour: 9,
          //         minute: 50
          //       },
          //       till: {
          //         hour: 10,
          //         minute: 22
          //       }
          //     },
          //     {
          //       name: 'Schwanenbräu',
          //       from: {
          //         hour: 10,
          //         minute: 45
          //       },
          //       till: {
          //         hour: 11,
          //         minute: 45
          //       }
          //     }
          //   ]
          // }

        )
      } else {
        resolve([])
      }
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
