import { ViewObject } from './ViewObject.js';

export class Form extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el = $(`
            <form id="${data.id}" class="form">
            </form>
        `);

        if (children) {
            el.append(children);
        }

        return el;
    }

    scriptRuntime(el, initQueue, data) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <form id="${id}" class="component form vb-item" data-type="form">
                ${super.componentDeleteBtn()}
                <div style="text-align: center;width:100%;">Form</div>
            </form>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="form"] {
                padding: 10px;
                min-height: 80px;
                height: auto;
                border: 1px solid #ddd;
                border-radius: 8px;
            }
        `;
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('form');

        let options = {
            id:'form' + super.getComponentIdNumber('form')
        }

        let $componentEl = this.createComponent(options.id, options, componentFactory);
        $el.append($componentEl);

        super.addComponentByType(componentFactory, 'row', $componentEl);
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.dropComponent($componentEl, componentFactory);

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["row"];

        super.drop($el, allowedTypes, componentFactory);
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('form-id',{label:'ID', size:'col-3'}));
        $panel.append(super.opComponent.button('form-row-add',{label:'Form 내용', size:'col-12', btnLabel:'행 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        $('#form-id').val(options.id);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('form-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.clickEvent('form-row-add',(e) => {
            super.addComponentByType(componentFactory, 'row', $el);
        });
    }
}