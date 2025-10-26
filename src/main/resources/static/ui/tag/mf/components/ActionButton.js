import { AbstractComponents } from './AbstractComponents.js';

export class ActionButton extends AbstractComponents {
    render() {
       const id = super.getId();
       const icon = this.getAttribute('icon');
       const actionName = this.getAttribute('actionName');
       const label = this.getAttribute('label');

       // button 생성
       const buttonEl = document.createElement('button');
       buttonEl.type = 'button';
       buttonEl.className = 'btn btn-default btn-sm';
       if(label) {
        buttonEl.textContent = label;
       }

       if (id) buttonEl.id = id;
       if (actionName) buttonEl.setAttribute('onclick', super.actionNameToFunction(actionName));

       // 아이콘 생성
       if (icon) {
           const iconEl = document.createElement('i');
           iconEl.className = icon;
           buttonEl.appendChild(iconEl);
       }

       this.replaceWith(buttonEl);
    }
}