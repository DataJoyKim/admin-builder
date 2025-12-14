import { ViewObject } from './ViewObject.js';

export class Form extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
            <form id="${data.id}" class="form">
            </form>
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
            <form id="${id}" class="component vb-item vb-form form" data-type="form">
                ${btnDelete}
            </form>
        `;

        return $(el);
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionButton('form-row-add', 'Form 내용', 'col-12', '행 추가');
        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $(document).off("click", "#form-row-add").on("click", "#form-row-add", (e) => {
            super.addComponentByType(componentFactory, 'row', $el);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('form');

        let options = {
            id:'form' + super.getComponentIdNumber('form')
        }

        let $componentEl = this.component(options.id, options);
        $el.append($componentEl);

        this.dropComponent($componentEl, componentFactory);

        super.addComponentByType(componentFactory, 'row', $componentEl);

        super.setOptions($componentEl, options);
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-row"];

        super.drop($el, allowedTypes, componentFactory);
    }
}