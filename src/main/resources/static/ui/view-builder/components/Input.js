import { ViewObject } from './ViewObject.js';

export class Input extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el = $(`
            <div class="form-group ${data.size}">
                <label for="${data.id}">${data.label}</label>
                <input type="text" class="form-control rounded-0" id="${data.id}" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >
            </div>
        `);

        return el;
    }

    scriptRuntime(el, initQueue, data) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <div id="${id}" class="component form-group ${options.size} vb-item vb-input" data-type="input">
                ${super.componentDeleteBtn()}
                <label for="${id}-el">${options.label}</label>
                <input type="text" class="form-control rounded-0" id="${id}-el" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
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

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('input-id',{label:'ID', size:'col-3'}));
        $panel.append(super.opComponent.select('input-size',{label:'크기', size:'col-12', options:super.opComponent.optionSize()}));
        $panel.append(super.opComponent.input('input-label',{label:'라벨', size:'col-12'}));
    }

    optionPanelScript($el, options) {
        $('#input-size').val(options.id);
        $('#input-size').val(options.size);
        $('#input-size').val(options.label);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('input-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.changeEvent('input-size',(e) => {
            super.opComponent.changeOptionValue($el, options, 'size', $(e.target).val());
            super.opComponent.changeSize($el, options.size);
        });

        super.opComponent.inputEvent('input-label',(e) => {
            super.opComponent.changeOptionValue($el, options, 'label', $(e.target).val());
            $el.find("label").text(options.label);
        });
    }
}