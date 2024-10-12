class WbSelect extends HTMLElement {
    connectedCallback() {
        let id = this.getAttribute('elementId');
        let label = this.getAttribute('label');
        let enable = this.getAttribute('enable');
        let useFirstItem = this.getAttribute('useFirstItem');
        let firstItemValue = this.getAttribute('firstItemValue');
        let firstItemLabel = this.getAttribute('firstItemLabel');

        enable = (enable == null) ? false : ((enable.toLowerCase() == "true") ? true : false);
        useFirstItem = (useFirstItem == null) ? false : ((useFirstItem.toLowerCase() == "true") ? true : false);

        let disabledHtml = '';
        if(!enable) {
            disabledHtml = ' disabled';
        }

        if(firstItemValue == null) {
            firstItemValue = '';
        }

        if(firstItemLabel == null) {
            firstItemLabel = '';
        }

        let html = '';

        html += `<div class="form-group">`;

        if(label != null) {
            html += `<label>${label}</label>`;
        }

        html += `<select id="${id}" class="form-control" ${disabledHtml}>`;

        if(useFirstItem) {
            html += `<option value="${firstItemValue}">${firstItemLabel}</option>`;
        }

        html += `</select>`;

        html += `</div>`;

        this.innerHTML = html;
    }
}

customElements.define('wb-select', WbSelect);