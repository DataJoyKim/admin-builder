import { AbstractComponents } from './AbstractComponents.js';

export class Toggle extends AbstractComponents {
    render() {
        const id = super.getId();
        const placeholder = this.getAttribute('placeholder') || '';
        const label = this.getAttribute('label');
        const size = this.getAttribute('size');
        const enableAttr = this.getAttribute('enable');
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

        const checkDiv = document.createElement('div');
        checkDiv.className = 'custom-control custom-switch';
        checkDiv.style = 'transform: scale(1.5); transform-origin: left center;';

        const inputEl = document.createElement('input');
        inputEl.type = 'checkbox';
        inputEl.className = 'custom-control-input';
        inputEl.id = id;
        if (disabled) inputEl.disabled = true;
        checkDiv.appendChild(inputEl);

        const checkLabelEl = document.createElement('label');
        checkLabelEl.htmlFor  = id;
        checkLabelEl.className = 'custom-control-label';
        checkLabelEl.style = 'cursor: pointer;';
        checkDiv.appendChild(checkLabelEl);

        wrapper.appendChild(checkDiv);

        this.replaceWith(wrapper);
    }
}