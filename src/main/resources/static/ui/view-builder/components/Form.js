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
            <form id="${id}" class="component vb-form form">
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

    optionPanelEvent($el, options) {
        $(document).off("click", "#form-row-add").on("click", "#form-row-add", (e) => {
            //appendRow($el);
        });
    }
}