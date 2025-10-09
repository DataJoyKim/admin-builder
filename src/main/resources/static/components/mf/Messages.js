export class Messages extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');
        this.replaceWith('');
    }
}