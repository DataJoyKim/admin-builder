class Actions {
    register(data) {}

    variableMessage() {
        return App.GlobalVariable.variable.message
    }

    registerAction(actionName, argsParams, code) {
        window.App.actions = window.App.actions || {};

        if(argsParams) {
            const args = argsParams.split(",");
            window.App.actions[actionName] = new Function(...args, code);
        }
        else {
            window.App.actions[actionName] = new Function(code);
        }
    }
}