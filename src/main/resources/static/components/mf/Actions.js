export class Actions extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');
        this.replaceWith('');
    }
}