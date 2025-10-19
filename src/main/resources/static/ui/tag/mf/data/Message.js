import { AbstractData } from './AbstractData.js';

export class Message extends AbstractData {
    render() {
        const id = super.getId();
        if (!id) {
            return;
        }

        let message = super.getMessageVariable();

        message[id] = [{}];

        const parent = this.parentElement;
        if(!parent) {
            return;
        }
    }
}