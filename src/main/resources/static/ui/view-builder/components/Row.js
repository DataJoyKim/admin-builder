import { ViewObject } from './ViewObject.js';

export class Row extends ViewObject {
    constructor() {
        super();
    }

    componentId() {
        return 'row';
    }

    componentOptions() {
        return {
           id:'row' + super.getComponentIdNumber()
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
            <div id="${options.id}" class="row" >
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
            <div id="${id}" class="component row vb-item" data-type="${this.componentId()}">
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
                margin: 0 !important;
                border: 1px dashed #bbb;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl,
            allowedComponentIds: ["row","card","form","grid","input","button","custom-html"],
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