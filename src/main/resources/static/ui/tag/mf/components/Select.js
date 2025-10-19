import { AbstractComponents } from './AbstractComponents.js';

export class Select extends AbstractComponents {
    render() {
        const id = super.getId();
        const label = this.getAttribute('label');
        const size = this.getAttribute('size');
        const enableAttr = this.getAttribute('enable');
        const codeKind = this.getAttribute('dataProvider');
        const useFirstItemAttr = this.getAttribute('useFirstItem');
        const firstItemValue = this.getAttribute('firstItemValue') || '';
        const firstItemLabel = this.getAttribute('firstItemLabel') || '';

        const enable = enableAttr ? enableAttr.toLowerCase() === 'true' : false;
        const useFirstItem = useFirstItemAttr ? useFirstItemAttr.toLowerCase() === 'true' : false;

        // form-group div 생성
        const wrapper = document.createElement('div');
        wrapper.className = 'form-group';
        if(size) wrapper.className += ' '+size;

        // label 생성
        if (label) {
            const labelEl = document.createElement('label');
            labelEl.textContent = label;
            labelEl.htmlFor  = id;
            wrapper.appendChild(labelEl);
        }

        // select 생성
        const selectEl = document.createElement('select');
        selectEl.className = 'form-control';
        if (id) selectEl.id = id;
        selectEl.dataset.watch = true;
        if (!enable) selectEl.disabled = true;

        // 첫 번째 옵션 추가
        if (useFirstItem) {
            selectEl.appendChild(this.createOptionEl(firstItemValue, firstItemLabel));
        }

        // 코드 추가
        if (codeKind) {
            let codeVariable = super.getCodeVariable();
            let codes = codeVariable[codeKind];
            console.log('codes',codes);
            for(const code of codes) {
                selectEl.appendChild(this.createOptionEl(code.code, code.name));
            }
        }

        wrapper.appendChild(selectEl);
        this.replaceWith(wrapper);
    }

    createOptionEl(value, label) {
        const optionEl = document.createElement('option');
        optionEl.value = value;
        optionEl.textContent = label;

        return optionEl;
    }
}