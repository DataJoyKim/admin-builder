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

    ready(eventName, _callback) {
        this.readyCount = 0;
        this.childTagCount = this.querySelectorAll('*').length;
        this.addEventListener(eventName, (event) => {
            _callback(event);

            this.readyCount++;
            if (this.readyCount === this.childTagCount) {
                this.remove();
            }
        });
    }

    readyComplete(eventName, message) {
        this.dispatchEvent(new CustomEvent(eventName, { bubbles: true, detail:message}));
    }
}