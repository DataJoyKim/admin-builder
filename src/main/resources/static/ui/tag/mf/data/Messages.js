import { AbstractData } from './AbstractData.js';

export class Messages extends AbstractData {
    render() {
        super.setMessageVariable({});

        super.readyComplete(super.getReadyEventName());
    }
}