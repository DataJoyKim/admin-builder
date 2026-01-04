import { GlobalVariable } from '../GlobalVariable.js';

export class Actions {
    register(data) {}

    variableMessage() {
        return GlobalVariable.variable.message
    }

    registerAction(actionName, argsParams, code) {
        window.vb = window.vb || {};
        window.vb.actions = window.vb.actions || {};

        if(argsParams) {
            const args = argsParams.split(",");
            window.vb.actions[actionName] = new Function(...args, code);
        }
        else {
            window.vb.actions[actionName] = new Function(code);
        }
    }
}