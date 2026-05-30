class GlobalVariable {
    constructor() {
        this.variable = {
            message : '_message'
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

    getMessage() {
        return window.global[this.variable.message];
    }

    setMessage(id, data) {
        window.global[this.variable.message][id] = data;
    }
}