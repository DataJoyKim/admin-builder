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

    componentTemplate(id, options) {
        let el = `
            <div id="${id}" class="component form-group ${options.size} vb-item vb-input" data-type="input">
                ${super.componentDeleteBtn()}
                <label for="${id}-el">${options.label}</label>
                <input type="text" class="form-control rounded-0" id="${id}-el" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >
            </div>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="row"] {
                padding: 5px;
                min-height: 80px;
                height: auto;
                margin: 0 !important;
                border: 1px dashed #bbb;
            }
        `;
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionInput('input-id', 'ID', 'col-3', options.id);
        html += super.optionSelect('input-size', '크기', 'col-12', super.getSizeOption(options.size));
        html += super.optionInput('input-label', 'Input 라벨', 'col-12', options.label);

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $("#input-id").off("input").on("input", (e) => {
            options.id = $(e.target).val();
            super.setOptions($el, options);
        });

        $("#input-size").off("change").on("change", (e) => {
            options.size = $(e.target).val();
            super.setOptions($el, options);
            super.changeSize($el, options.size);
        });

        $("#input-label").off("input").on("input", (e) => {
            options.label = $(e.target).val();
            super.setOptions($el, options);
            $el.find("label").text(options.label);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('input');

        let options = {
            id:'input' + super.getComponentIdNumber('input'),
            size:'col-3',
            label:'Label'
        }

        $el.append(this.createComponent(options.id, options, componentFactory));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }
}