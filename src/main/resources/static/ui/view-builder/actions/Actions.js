import { GlobalVariable } from '../GlobalVariable.js';

export class Actions {
    register(data) {}

    variableMessage() {
        return GlobalVariable.variable.message
    }
}