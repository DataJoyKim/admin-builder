import { AbstractComponents } from './AbstractComponents.js';

export class TextInput extends AbstractComponents {
    render() {
        const id = super.getId();
        const placeholder = this.getAttribute('placeholder') || '';
        const label = this.getAttribute('label');
        const size = this.getAttribute('size');
        const enableAttr = this.getAttribute('enable');
        const elementType = this.getAttribute('type') || 'text';
        const hidden = this.getAttribute('hidden');

        // enable 속성 처리
        const enable = enableAttr ? enableAttr.toLowerCase() === 'true' : false;

        // 최종 disabled 여부
        const disabled = enable ? false : true;

        // form-group div 생성
        const wrapper = document.createElement('div');
        wrapper.className = 'form-group';
        if(size) wrapper.className += ' '+size;
        if(hidden && hidden == 'true')  wrapper.className += ' d-none';

        // label 생성
        if (label) {
            const labelEl = document.createElement('label');
            labelEl.textContent = label;
            labelEl.htmlFor  = id;
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