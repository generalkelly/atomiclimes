export default function TimePrinter() {}

TimePrinter.prototype._print = function(number) {
  if (number < 10) {
    return '0' + number
  } else {
    return '' + number
  }
}

TimePrinter.prototype.printTime = function(hour, minute) {
  return this._print(hour) + ':' + this._print(minute)
};
