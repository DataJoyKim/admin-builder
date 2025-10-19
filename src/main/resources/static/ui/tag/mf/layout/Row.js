import { ViewObject } from '../ViewObject.js';

export class Row extends ViewObject {
    render() {
        let id = this.getAttribute('id');
        let size = this.getAttribute('size');
        let className = 'row';
        if(size != null) {
            className += ' ' + size;
        }

        const div = document.createElement('div');
        div.className = className;

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}