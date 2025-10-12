export class Col extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('id');
        const size = this.getAttribute('size');

        const div = document.createElement('div');
        div.className = ((size) ? size : 'col');

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}