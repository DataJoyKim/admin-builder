import { ViewObject } from './ViewObject.js';

export class Input extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
            <div class="form-group ${data.size}">
                <label for="${data.id}">${data.label}</label>
                <input type="text" class="form-control rounded-0" id="${data.id}" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >
            </div>
        `);

        return el;
    }

    script(el, initQueue, data) {}

    component(id, options) {
        let btnDelete = super.componentDeleteBtn();
        let el = `
            <div id="${id}" class="component vb-input form-group ${options.size}">
                ${btnDelete}
                <label class="vb-input-label" for="${id}-el">${options.label}</label>
                <input type="text" class="vb-input-box form-control rounded-0" id="${id}-el" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >
            </div>
        `;

        return $(el);
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionSelect('input-size', '크기', 'col-12', super.getSizeOption(options.size));
        html += super.optionInput('input-label', 'Input 라벨', 'col-12', options.label);

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options) {
        $("#input-size").off("change").on("change", (e) => {
            options.size = $(e.target).val();
            super.setOptions($el, options);
            super.changeSize($el, options.size);
        });

        $("#input-label").off("input").on("input", (e) => {
            options.label = $(e.target).val();
            super.setOptions($el, options);
            $el.find(".vb-input-label").text(options.label);
        });
    }
}