import { ViewObject } from '../ViewObject.js';

export class AbstractData extends ViewObject {
    getMessageVariable() {
        return window['_messages'];
    }

    setMessageVariable(value) {
        window['_messages'] = value;
    }

    getCodeVariable() {
        return window['_codes'];
    }

    setCodeVariable(value) {
        window['_codes'] = value;
    }

    getReadyEventName() {
        return 'mf-data-ready';
    }
}