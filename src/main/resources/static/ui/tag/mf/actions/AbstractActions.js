import { ViewObject } from '../ViewObject.js';

export class AbstractActions extends ViewObject {
    getReadyEventName() {
        return 'mf-actions-ready';
    }
}