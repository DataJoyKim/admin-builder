import { GlobalVariable } from '../GlobalVariable.js';

export class Actions {
    register(data) {}

    variableMessage() {
        return GlobalVariable.variable.message
    }

    registerAction(actionName, code) {
        window.vb = window.vb || {};
        window.vb.actions = window.vb.actions || {};
        window.vb.actions[actionName] = new Function(code);
    }
}