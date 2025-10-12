import { AbstractData } from './AbstractData.js';

export class Codes extends AbstractData {
    render() {
        super.setCodeVariable({});

        super.readyComplete(super.getReadyEventName());
    }
}