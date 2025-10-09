export class Layout extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');

        const div = document.createElement('div');
        div.className = 'content-wrapper';

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}