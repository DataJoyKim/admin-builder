import { AbstractComponents } from './AbstractComponents.js';

export class Form extends AbstractComponents {
    render(){
        const id = super.getId();

        let className = 'form';

        const div = document.createElement('div');
        div.id = id;
        div.className = className;

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}