class GlobalVariable {
    constructor() {
        this.variable = {
            message : '_message',
            code : '_code'
        }
    }

    init() {
        window.global = {};

        for(const key in this.variable) {
            this.resister(this.variable[key], {});
        }
    }

    resister(k, v) {
        window.global[k] = v;
    }

    get(k) {
        return window.global[k];
    }

    getCode() {
        return window.global[this.variable.code];
    }

    setCode(codes) {
        return window.global[this.variable.code] = codes;
    }

    getMessage() {
        return window.global[this.variable.message];
    }

    setMessage(id, data) {
        window.global[this.variable.message][id] = data;
    }
}