import { Actions } from './Actions.js';

export class Script extends Actions {
    constructor() {
        super();
    }

    register(data) {
        const name = data.actionName;
        const script = data.script;

        if (!name) {
            return;
        }

        super.registerAction(name,script);
    }
}