import { ViewObject } from './ViewObject.js';

export class Input extends ViewObject {
    constructor() {
        super();
    }

    componentId() {
        return 'input';
    }

    componentOptions() {
        return {
           id:'input' + super.getComponentIdNumber(),
           size:'col-auto',
           width:'150px',
           label:''
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let style = ``;

        if(options.width) {
            style += `width:${options.width};`;
        }

        let el = $(`<div class="form-group ${options.size}" style="${style}"></div>`);

        if(options.label) {
            const labelEL = $(`<label for="${options.id}">${options.label}</label>`);
            el.append(labelEL);
        }

        const inputEl = $(`<input type="text" class="form-control rounded-0" id="${options.id}" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >`);
        el.append(inputEl);

        return el;
    }

    scriptRuntime(el, initQueue, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let style = ``;

        if(options.width) {
            style += `width:${options.width};`;
        }

        let el = $(`
            <div id="${id}" class="component form-group ${options.size} vb-item vb-input" style="${style}" data-type="${this.componentId()}">
            ${super.componentDeleteBtn()}
            </div>
        `);

        const hiddenLabel = (options.label) ? '' : 'hidden';

        const labelEL = $(`<label for="${id}-el" ${hiddenLabel}>${options.label}</label>`);
        el.append(labelEL);

        const inputEl = $(`<input type="text" class="form-control rounded-0" id="${id}-el" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >`);
        el.append(inputEl);

        return el;
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 10px;
                height: auto;
                margin: 0 !important;
            }
        `;
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('input-id',{label:'ID', size:'col-6'}));
        $panel.append(super.opComponent.select('input-size',{label:'크기', size:'col-6', options:super.opComponent.optionSize()}));
        $panel.append(super.opComponent.input('input-width',{label:'width', size:'col-6'}));
        $panel.append(super.opComponent.input('input-label',{label:'라벨', size:'col-6'}));
    }

    optionPanelScript($el, options) {
        $('#input-id').val(options.id);
        $('#input-size').val(options.size);
        $('#input-width').val(options.width);
        $('#input-label').val(options.label);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('input-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.changeEvent('input-size',(e) => {
            super.opComponent.changeOptionValue($el, options, 'size', $(e.target).val());
            super.opComponent.changeSize($el, options.size);
        });

        super.opComponent.inputEvent('input-width',(e) => {
            super.opComponent.changeOptionValue($el, options, 'width', $(e.target).val());
            $el.css('width',options.width);
        });

        super.opComponent.inputEvent('input-label',(e) => {
            super.opComponent.changeOptionValue($el, options, 'label', $(e.target).val());
            $el.find("label").text(options.label);
            $el.find("label").prop('hidden', (options.label) ? false : true);
        });
    }
}