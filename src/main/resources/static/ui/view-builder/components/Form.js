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

    componentTemplate(id, options) {
        let el = `
            <form id="${id}" class="component form vb-item" data-type="form">
                ${super.componentDeleteBtn()}
                <div style="text-align: center;width:100%;">Form</div>
            </form>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="form"] {
                padding: 10px;
                min-height: 80px;
                height: auto;
                border: 1px solid #ddd;
                border-radius: 8px;
            }
        `;
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionInput('form-id', 'ID', 'col-3', options.id);
        html += super.optionButton('form-row-add', 'Form 내용', 'col-12', '행 추가');
        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $("#form-id").off("input").on("input", (e) => {
            options.id = $(e.target).val();
            super.setOptions($el, options);
        });

        $(document).off("click", "#form-row-add").on("click", "#form-row-add", (e) => {
            super.addComponentByType(componentFactory, 'row', $el);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('form');

        let options = {
            id:'form' + super.getComponentIdNumber('form')
        }

        let $componentEl = this.createComponent(options.id, options, componentFactory);
        $el.append($componentEl);

        super.addComponentByType(componentFactory, 'row', $componentEl);
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.dropComponent($componentEl, componentFactory);

        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["row"];

        super.drop($el, allowedTypes, componentFactory);
    }
}