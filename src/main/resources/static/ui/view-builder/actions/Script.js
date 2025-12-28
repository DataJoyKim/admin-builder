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

        // 함수 생성
        window[name] = new Function(script);
    }
}