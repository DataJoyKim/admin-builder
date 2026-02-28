import { ViewObject } from './ViewObject.js';

export class Form extends ViewObject {
    constructor() {
        super();
    }

    componentId() {
        return 'form';
    }

    componentOptions() {
        return {
           id:'form' + super.getComponentIdNumber()
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
            <form id="${options.id}" class="form">
            </form>
        `);

        if (children) {
            el.append(children);
        }

        return el;
    }

    scriptRuntime(el, initQueue, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <form id="${id}" class="component form vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                <div style="text-align: center;width:100%;">Form</div>
            </form>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 10px;
                min-height: 80px;
                height: auto;
                border: 1px solid #ddd;
                background-color: #ffffff;
                border-radius: 8px;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl,
            allowedComponentIds: ["row","custom-html"],
            sortable: true
        }]
    }

    afterAddComponent(componentFactory, $el, $componentEl) {
        super.addComponentByType(componentFactory, 'row', $componentEl);
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