class Util {
    constructor() {}

    ifNull(v, rv) {
        return ((v) ? v : rv);
    }

    ynToBoolean(yn) {
        return (yn && yn == 'Y') ? true : false;
    }

    booleanToYn(value) {
        return (value && value == true) ? 'Y' : 'N';
    }

    stringToBoolean(value) {
        return (value && value.toLowerCase() == "true") ? true : false;
    }
}

window.App = window.App || {};
window.App.util = new Util();