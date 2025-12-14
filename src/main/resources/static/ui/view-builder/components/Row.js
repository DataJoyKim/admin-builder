import { ViewObject } from './ViewObject.js';

export class Row extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
            <div id="${data.id}" class="row" >
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
            <div id="${id}" class="component vb-row row" >
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