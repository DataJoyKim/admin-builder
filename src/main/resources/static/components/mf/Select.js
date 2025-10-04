export class Select extends HTMLElement {
    connectedCallback() {
        const id = this.getAttribute('elementId');
        const label = this.getAttribute('label');
        const enableAttr = this.getAttribute('enable');
        const useFirstItemAttr = this.getAttribute('useFirstItem');
        const firstItemValue = this.getAttribute('firstItemValue') || '';
        const firstItemLabel = this.getAttribute('firstItemLabel') || '';

        const enable = enableAttr ? enableAttr.toLowerCase() === 'true' : false;
        const useFirstItem = useFirstItemAttr ? useFirstItemAttr.toLowerCase() === 'true' : false;

        // form-group div 생성
        const wrapper = document.createElement('div');
        wrapper.className = 'form-group';

        // label 생성
        if (label) {
            const labelEl = document.createElement('label');
            labelEl.textContent = label;
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
            const optionEl = document.createElement('option');
            optionEl.value = firstItemValue;
            optionEl.textContent = firstItemLabel;
            selectEl.appendChild(optionEl);
        }

        wrapper.appendChild(selectEl);
        this.replaceWith(wrapper);
    }
}