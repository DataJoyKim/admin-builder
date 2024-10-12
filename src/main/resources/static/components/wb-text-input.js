class WbTextInput extends HTMLElement {
    connectedCallback() {
        let id = this.getAttribute('elementId');
        let placeholder = this.getAttribute('placeholder');
        let label = this.getAttribute('label');
        let enable = this.getAttribute('enable');
        let elementType = this.getAttribute('elementType');

        if(placeholder == null) {
            placeholder = '';
        }

        if(elementType == null) {
            elementType = "text";
        }

        enable = (enable == null) ? false : ((enable.toLowerCase() == "true") ? true : false);

        let disabledHtml = '';
        if(!enable) {
            disabledHtml = ' disabled';
        }

        let html = '';

        html += `<div class="form-group">`;

        if(label != null) {
            html += `<label>${label}</label>`;
        }

        html += `<input type="${elementType}" class="form-control rounded-0" id="${id}" placeholder="${placeholder}" ${disabledHtml}>`;

        html += `</div>`;

        this.innerHTML = html;
    }
}

customElements.define('wb-text-input', WbTextInput);