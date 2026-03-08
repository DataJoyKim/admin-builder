class Actions {
    register(data) {}

    variableMessage() {
        return VB.GlobalVariable.variable.message
    }

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