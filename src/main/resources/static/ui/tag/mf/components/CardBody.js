import { AbstractComponents } from './AbstractComponents.js';

export class CardBody extends AbstractComponents {
    render() {
        const id = super.getId();

        const div = document.createElement('div');
        div.className = 'card-body';

        while (this.firstChild) div.appendChild(this.firstChild);

        this.replaceWith(div);
    }
}