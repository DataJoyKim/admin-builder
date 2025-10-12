import { AbstractData } from './AbstractData.js';

export class Code extends AbstractData {
    render() {
        const id = super.getId();
        if (!id) {
            return;
        }

        let codes = super.getCodeVariable();

        super.readyComplete();
    }
}