import { AbstractComponents } from './AbstractComponents.js';

export class CardHeader extends AbstractComponents {
    render() {
        const id = super.getId();
        const title = this.getAttribute('title');

        const div = document.createElement('div');
        div.className = 'card-header';

        const titleEl = document.createElement('h3');
        titleEl.className = 'card-title';
        titleEl.textContent = title;
        div.appendChild(titleEl);

        const toolsEl = document.createElement('div');
        toolsEl.className = 'card-tools';
        while (this.firstChild) toolsEl.appendChild(this.firstChild);

        div.appendChild(toolsEl);
        this.replaceWith(div);
    }
}