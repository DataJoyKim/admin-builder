import { ViewObject } from '../ViewObject.js';

export class Layout extends ViewObject {
    render() {
        const id = this.getAttribute('id');

        const div = document.createElement('div');
        div.className = 'wrapper';

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}