export class TextInput extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');
        const placeholder = this.getAttribute('placeholder') || '';
        const label = this.getAttribute('label');
        const enableAttr = this.getAttribute('enable');
        const elementType = this.getAttribute('elementType') || 'text';

        // enable 속성 처리
        const enable = enableAttr ? enableAttr.toLowerCase() === 'true' : false;

        // 최종 disabled 여부
        const disabled = enable ? false : true;

        // form-group div 생성
        const wrapper = document.createElement('div');
        wrapper.className = 'form-group';

        // label 생성
        if (label) {
            const labelEl = document.createElement('label');
            labelEl.textContent = label;
            wrapper.appendChild(labelEl);
        }

        // input 생성
        const inputEl = document.createElement('input');
        inputEl.type = elementType;
        inputEl.className = 'form-control rounded-0';
        if (id) inputEl.id = id;
        inputEl.placeholder = placeholder;
        inputEl.spellcheck = false;
        inputEl.autocomplete = 'off';
        inputEl.dataset.watch = true;
        if (disabled) inputEl.disabled = true;

        wrapper.appendChild(inputEl);
        this.replaceWith(wrapper);
    }
}