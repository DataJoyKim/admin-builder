export class Codes extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');
        this.replaceWith('');
    }
}