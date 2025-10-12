export class ViewObject extends HTMLElement {
    id;

    connectedCallback() {
        this.id = this.getAttribute('id');

        this.render();
    }

    render() {}

    getId() {
        return this.id;
    }

    ready(readyEventName, _callback) {
        this.readyCount = 0;
        this.childTagCount = this.querySelectorAll('*').length;
        this.addEventListener(readyEventName, (event) => {
            _callback(event);

            this.readyCount++;
            if (this.readyCount === this.childTagCount) {
                this.remove();
            }
        });
    }

    readyComplete(readyEventName, message) {
        this.dispatchEvent(new CustomEvent(readyEventName, { bubbles: true, detail:message}));
    }
}