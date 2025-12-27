import { ViewObject } from './ViewObject.js';

export class Row extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el = $(`
            <div id="${data.id}" class="row" >
            </div>
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
            <div id="${id}" class="component row vb-item" data-type="row">
                ${super.componentDeleteBtn()}
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
        super.plusComponentIdNumber('row');

        let options = {
            id:'row' + super.getComponentIdNumber('row')
        }

        $el.append(this.createComponent(options.id, options, componentFactory));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.dropComponent($componentEl, componentFactory);

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["card","form","grid","input","button"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, super.getSortableType(allowedTypes));
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