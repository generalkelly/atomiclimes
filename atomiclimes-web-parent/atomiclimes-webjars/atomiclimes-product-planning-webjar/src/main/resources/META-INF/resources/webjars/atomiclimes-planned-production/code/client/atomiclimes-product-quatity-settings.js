import $ from 'jquery'
import './atomiclimes-product-quatity-settings.css'

export default class AtomicLimesProductQuantitySettings {

  constructor(plannedProduction) {
    this.plannedProduction = plannedProduction;
    this.plannedProduction.quantity = 0;
    this.currentUnit = 0;
    this.units = ['l', 'hl']
    this.plannedProduction.unit = this.units[this.currentUnit];
    this.numerals = 0;
    this.decimals = 0;
    this.hasDecimals = false;
    this.numberOfLeadingZeros = 0;
    this.print = this.print.bind(this);
    this.numberWithSpaces = this.numberWithSpaces.bind(this);
  }

  show(node) {
    const self = this;
    var header = $("<div class=\"header\">Produktionsmenge</div>");
    var display = $("<div class=\"display\"></div>");
    var numberWithDecimals = $("<div class=\"numberWithDecimals\">" + self.numerals + "</div>");
    var unit = $("<div class=\"unit\">" + self.units[self.currentUnit] + "</div>");
    display.append(numberWithDecimals);
    display.append(unit);
    var pad = $("<div class=\"pad\"></div>");
    var row = $("<div class=\"row\"></div>");
    for (var i = 1; i <= 9; i++) {
      var number = $("<div class=\"number\">" + i + "</div>");
      row.append(number);
      if (i % 3 == 0) {
        pad.prepend(row);
        row = $("<div class=\"row\"></div>");
      }
    }
    var delimiter = $("<div class=\"delimiter\">.</div>");
    var zero = $("<div class=\"number\">0</div>");
    var del = $("<div class=\"del\">del</div>");
    row.append(delimiter);
    row.append(zero);
    row.append(del);
    pad.append(row);

    $(node).append(header);
    $(node).append(display);
    $(node).append(pad);

    $('.number').click(function() {
      var currentNumber = +($(this).text());
      if (self.hasDecimals) {
        self.decimals = self.decimals * 10 + currentNumber;
        if (self.decimals == 0 && currentNumber == 0) {
          self.numberOfLeadingZeros++;
        }
      } else {
        self.numerals = self.numerals * 10 + currentNumber;
      }
      self.print(numberWithDecimals);
    });



    delimiter.click(function() {
      self.hasDecimals = true;
      self.print(numberWithDecimals);
    });

    del.click(function() {
      if (self.hasDecimals) {
        if (self.numberOfLeadingZeros == 0 && self.decimals == 0) {
          self.hasDecimals = false;
        } else if (self.numberOfLeadingZeros != 0 && self.decimals == 0) {
          self.numberOfLeadingZeros--;
        } else {
          self.decimals = (self.decimals - self.decimals % 10) / 10;
        }
      } else {
        self.numerals = (self.numerals - self.numerals % 10) / 10;
      }
      self.print(numberWithDecimals);
    });
    unit.click(function() {
      self.currentUnit = (self.currentUnit + 1) % (self.units.length);
      unit.text(self.units[self.currentUnit]);
      self.plannedProduction.unit = self.units[self.currentUnit];
    });
  }

  print(node) {
    var leadingZeros = '';
    for (var i = 0; i < this.numberOfLeadingZeros; i++) {
      leadingZeros = leadingZeros + '0';
    }
    if (this.hasDecimals) {
      if (this.numberOfLeadingZeros == 0 && this.decimals == 0) {
        this.plannedProduction.quantity = this.numberWithSpaces(this.numerals + '.' + this.decimals);
      } else if (this.numberOfLeadingZeros != 0 && this.decimals == 0) {
        this.plannedProduction.quantity = this.numberWithSpaces(this.numerals + '.' + leadingZeros);
      } else {
        this.plannedProduction.quantity = this.numberWithSpaces(this.numerals + '.' + leadingZeros + this.decimals);
      }
    } else {
      this.plannedProduction.quantity = this.numberWithSpaces(this.numerals);
    }
    node.text(this.plannedProduction.quantity);
  }

  numberWithSpaces(number) {
    if (this.hasDecimals) {
      var parts = number.toString().split(".");
      parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, " ");
      return parts.join(".");
    } else {
      return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
  }

}
