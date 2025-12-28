import { ViewObject } from './ViewObject.js';

export class Layout extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el = $(`
           <div class="layout" id="layout" style="min-height: 993px;" >
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
           <div class="layout wrapper vb-item" id="${id}" style="min-height: 993px;" data-type="layout">
           </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="layout"] {
                cursor: pointer;
            }
        `;
    }

    addComponent($el, componentFactory) {
    }

    createComponent(id, options, componentFactory) {
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["row","custom-html"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, super.getSortableType(allowedTypes));

        super.addComponentByType(componentFactory, "row", $el);
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.button('layout-row-add',{label:'Layout 내용', size:'col-12', btnLabel:'행 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.clickEvent('layout-row-add',(e) => {
          super.addComponentByType(componentFactory, 'row', $el);
        });
    }
}