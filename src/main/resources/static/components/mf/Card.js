export class Card extends HTMLElement {
    connectedCallback() {
        let id = this.getAttribute('elementId');
        let size = this.getAttribute('size');
        let className = 'card';
        if(size != null) {
            className += ' ' + size;
        }

        const div = document.createElement('div');
        div.className = className;

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}