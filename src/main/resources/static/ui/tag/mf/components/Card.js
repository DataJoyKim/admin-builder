import { AbstractComponents } from './AbstractComponents.js';

export class Card extends AbstractComponents {
    render(){
        const id = super.getId();
        const size = this.getAttribute('size');
        const addClass = this.getAttribute('addClass');

        let className = 'card';
        if(size != null) {
            className += ' ' + size;
        }

        if(addClass) className += ' ' + addClass;

        if(this.hasAttribute('hidden'))  className += ' d-none';

        const div = document.createElement('div');
        div.className = className;
        div.id = id;

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
    }
}