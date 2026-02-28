import { ViewObject } from './ViewObject.js';

export class Layout extends ViewObject {
    constructor() {
        super();
    }

    componentId() {
        return 'layout';
    }

    componentOptions() {
        return {
           id:'layout' + super.getComponentIdNumber()
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
           <div class="layout" id="layout" style="min-height: 993px;" >
           </div>
        `);

        return el;
    }

    scriptRuntime(el, initQueue, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
           <div class="layout wrapper vb-item" id="${id}" style="min-height: 993px;" data-type="${this.componentId()}">
           </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                cursor: pointer;
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
        $panel.append(super.opComponent.button('layout-row-add',{label:'Layout 내용', size:'col-12', btnLabel:'행 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.clickEvent('layout-row-add',(e) => {
          super.addComponentByType(componentFactory, 'row', $el);
        });
    }
}