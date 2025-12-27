import { ViewObject } from './ViewObject.js';

export class CardBody extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el = $(`
             <div class="card-body">
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
             <div id="${id}" class="component card-body vb-item" data-type="card-body">
                ${super.componentDeleteBtn()}
             </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="card-body"] {
                padding: 5px;
                min-height: 100px;
                height: auto;
                margin: 2px 5px 2px 5px;
                border: 1px dashed #bbb;
            }
        `;
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('card-body');

        let options = {
            id:'cardBody' + super.getComponentIdNumber('card-body')
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
        let allowedTypes = ["form","grid","input","button"];

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