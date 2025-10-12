export class ActionButton extends HTMLElement {
    connectedCallback() {
       const id = this.getAttribute('id');
       const icon = this.getAttribute('icon') || 'fas fa-search';
       const actionName = this.getAttribute('actionName');

       // button 생성
       const buttonEl = document.createElement('button');
       buttonEl.type = 'button';
       buttonEl.className = 'btn btn-default btn-sm';
       if (id) buttonEl.id = id;
       if (actionName) buttonEl.setAttribute('onclick', actionName);

       // 아이콘 생성
       if (icon) {
           const iconEl = document.createElement('i');
           iconEl.className = icon;
           buttonEl.appendChild(iconEl);
       }

       this.replaceWith(buttonEl);
    }
}