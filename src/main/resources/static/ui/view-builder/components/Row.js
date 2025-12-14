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
            <div id="${id}" class="component vb-item vb-row row" data-type="row">
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
        super.plusComponentIdNumber('row');

        let options = {
            id:'row' + super.getComponentIdNumber('row')
        }

        let $componentEl = this.component(options.id, options);
        $el.append($componentEl);

        this.dropComponent($componentEl, componentFactory);

        super.setOptions($componentEl, options);
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-card", "component-input", "component-grid"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, ".vb-input, .vb-card, vb-grid");
    }
}