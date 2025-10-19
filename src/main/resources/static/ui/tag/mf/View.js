export class View extends HTMLElement {
    connectedCallback() {
        console.log('view version 0.0.4');

        const div = document.createElement('div');
        div.className = 'view';

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}