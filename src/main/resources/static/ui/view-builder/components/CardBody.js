import { ViewObject } from './ViewObject.js';

export class CardBody extends ViewObject {
    constructor() {
        super();
    }

    componentId() {
        return 'card-body';
    }

    componentOptions() {
        return {
           id:'cardBody' + super.getComponentIdNumber()
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
             <div class="card-body">
             </div>
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
             <div id="${id}" class="component card-body vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                <div class="drop-area-label">
                    + 여기에 컴포넌트를 추가해주세요.
                </div>
             </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 5px;
                min-height: 70px;
                height: auto;
                margin: 5px;
                border: 1px dashed #bbb;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl,
            allowedComponentIds: ["form","grid","input","button","custom-html"],
            sortable: true
        }]
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append('');
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {}
}