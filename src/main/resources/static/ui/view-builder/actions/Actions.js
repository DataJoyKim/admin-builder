class Actions {
    constructor(globalVariable) {
        this.globalVariable = globalVariable;
    }

    register(data) {}

    registerAction(actionName, argsParams, code) {
        window.VB.actions = window.VB.actions || {};

        if(argsParams) {
            const args = argsParams.split(",");
            window.VB.actions[actionName] = new Function(...args, code);
        }
        else {
            window.VB.actions[actionName] = new Function(code);
        }
    }
}