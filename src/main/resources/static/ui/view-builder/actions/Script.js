import { Actions } from './Actions.js';

export class Script extends Actions {
    constructor() {
        super();
    }

    register(data) {
        const name = data.actionName;
        const script = data.script;
        const argsName = data.argsName;

        if (!name) {
            return;
        }

        super.registerAction(name,argsName,script);
    }
}