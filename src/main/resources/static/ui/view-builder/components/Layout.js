import { ViewObject } from './ViewObject.js';

export class Layout extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
           <div class="layout" id="layout" style="min-height: 993px;" >
           </div>
        `);

        return el;
    }

    script(el, initQueue, data) {}

    componentTemplate(id, options) {
        let el = `
           <div class="layout vb-item vb-layout content-wrapper" id="${id}" style="min-height: 993px;" data-type="layout">
           </div>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="layout"] {
                cursor: pointer;
            }
        `;
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionButton('layout-row-add', 'Layout 내용', 'col-12', '행 추가');
        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $(document).off("click", "#layout-row-add").on("click", "#layout-row-add", (e) => {
            super.addComponentByType(componentFactory, 'row', $el);
        });
    }

    addComponent($el, componentFactory) {
    }

    createComponent(id, options, componentFactory) {
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-row"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, ".vb-row");

        super.addComponentByType(componentFactory, "row", $el);
    }
}