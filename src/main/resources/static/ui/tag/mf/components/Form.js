import { AbstractComponents } from './AbstractComponents.js';

export class Form extends AbstractComponents {
    render(){
        const id = super.getId();
        let size = this.getAttribute('size');

        let className = 'form';

        const form = document.createElement('form');
        form.id = id;
        form.className = className;
        if(size) {
            form.className += ' ' + size;
        }

        while (this.firstChild) form.appendChild(this.firstChild);
        this.replaceWith(form);
    }
}