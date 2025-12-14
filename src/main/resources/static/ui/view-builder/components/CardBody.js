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
             <div id="${id}" class="component vb-card-body card-body">
                ${btnDelete}
             </div>
        `;

        return $(el);
    }

    optionPanelView(options) {
        return ``;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options) {}
}