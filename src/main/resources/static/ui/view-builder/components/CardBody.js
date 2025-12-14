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

    component(id, options) {
        let btnDelete = super.componentDeleteBtn();
        let el = `
             <div id="${id}" class="component vb-item vb-card-body card-body" data-type="card-body">
                ${btnDelete}
             </div>
        `;

        return $(el);
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

        let $componentEl = this.component(options.id, options);
        $el.append($componentEl);
        this.dropComponent($componentEl, componentFactory);

        super.setOptions($componentEl, options);
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-form","component-grid"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, ".vb-form, .vb-grid");
    }
}