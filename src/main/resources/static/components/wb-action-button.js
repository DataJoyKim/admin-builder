class WbActionButton extends HTMLElement {
    connectedCallback() {
        let id = this.getAttribute('elementId');
        let icon = this.getAttribute('icon');
        let actionName = this.getAttribute('actionName');

        if(icon == null) {
            icon = 'fas fa-search';
        }

        let html = '';

        html += this.htmlButton(id, icon, actionName);

        this.innerHTML = html;
    }

    htmlButton(id, icon, actionName) {
        let iconHtml = '';
        if(icon != null) {
            iconHtml = '<i class="'+icon+'"></i>';
        }

        let onclickHtml = '';
        if(actionName != null ) {
            onclickHtml = 'onclick="' + actionName+ '"';
        }

        let idHtml = '';
        if(id != null) {
            idHtml = 'id="'+id+'"';
        }

        return '<button type="button" class="btn btn-default btn-sm" '+idHtml+' '+onclickHtml+'">'+iconHtml+'</button>';
    }
}

customElements.define('wb-action-button', WbActionButton);