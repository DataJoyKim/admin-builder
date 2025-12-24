export class GlobalVariable {
    static variable = {
        message : '_message'
    }

    static init() {
        window.global = {};

        for(const key in GlobalVariable.variable) {
            GlobalVariable.resister(GlobalVariable.variable[key], {});
        }
    }

    static resister(k, v) {
        window.global[k] = v;
    }

    static get(k) {
        return window.global[k];
    }
}