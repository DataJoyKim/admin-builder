import { AbstractComponents } from './AbstractComponents.js';

export class Form extends AbstractComponents {
    render(){
        const id = super.getId();

        let className = 'form';

        const form = document.createElement('form');
        form.id = id;
        form.className = className;

        while (this.firstChild) form.appendChild(this.firstChild);
        this.replaceWith(form);
    }
}