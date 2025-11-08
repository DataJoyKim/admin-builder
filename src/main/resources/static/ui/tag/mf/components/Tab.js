import { AbstractComponents } from './AbstractComponents.js';

export class Tab extends AbstractComponents {
    render() {
        const id = super.getId();

        const div = document.createElement('div');
        div.className = 'tab-pane fade';
        div.id = id;
        div.role = 'tabpanel';
        div.setAttribute('aria-labelledby',id + '-tab');

        if(this.hasAttribute('active')) {
            div.className += ' show active';
        }

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}