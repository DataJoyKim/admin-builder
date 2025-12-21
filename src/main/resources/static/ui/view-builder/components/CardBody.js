import { ViewObject } from './ViewObject.js';

export class CardBody extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
             <div class="card-body">
             </div>
        `);

        if (children) {
            el.append(children);
        }

        return el;
    }

    script(el, initQueue, data) {}

    componentTemplate(id, options) {
        let el = `
             <div id="${id}" class="component vb-item vb-card-body card-body" data-type="card-body">
                ${super.componentDeleteBtn()}
             </div>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="card-body"] {
                padding: 2px;
                min-height: 100px;
                height: auto;
                margin: 0 !important;
                border: 1px dashed #bbb;
            }
        `;
    }

    optionPanelView(options) {
        return ``;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {}

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

        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-form","component-grid"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, ".vb-form, .vb-grid");
    }
}