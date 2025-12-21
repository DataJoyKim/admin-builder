import { ViewObject } from './ViewObject.js';

export class Card extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el = $(`
            <div class="${data.size}">
                 <div id="${data.id}" class="card">
                     <div class="card-header">
                          <h3 class="card-title">${data.title}</h3>
                          <div class="card-tools">
                          </div>
                     </div>
                 </div>
            </div>
        `);

        if (children && children.cardHeader) {
            el.find(".card-tools").append(children.cardHeader);
        }

        if (children && children.cardChildren) {
            el.find(".card").append(children.cardChildren);
        }

        return el;
    }

    script(el, initQueue, data) {}

    componentTemplate(id, options) {
        let el = `
            <div id="${id}" class="component vb-item vb-card ${options.size}" data-type="card">
                ${super.componentDeleteBtn()}
                 <div class="card">
                     <div class="vb-card-header card-header">
                          <h3 class="card-title">${options.title}</h3>
                          <div class="vb-card-tools card-tools">
                          </div>
                     </div>
                 </div>
            </div>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="card"] .card-tools {
                padding-left: 30px;
                min-width: 60px;
                width: auto;
                min-height: 30px;
                height: auto;
                border: 1px dashed #bbb;
            }
        `;
    }

    optionPanelView(options) {
         let html = ``;
        html += super.optionSelect('card-size', '크기', 'col-12', super.getSizeOption(options.size));
        html += super.optionInput('card-title-input', 'Card 제목', 'col-12', options.title);
        html += super.optionButton('card-body-add', 'Card 내용', 'col-12', '컨텐츠 영역 추가');

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $("#card-size").off("change").on("change", (e) => {
            options.size = $(e.target).val();
            super.setOptions($el, options);
            super.changeSize($el, options.size);
        });

        $("#card-title-input").off("input").on("input", (e) => {
            options.title = $(e.target).val();
            super.setOptions($el, options);
            $el.find(".card-title").text(options.title);
        });

        $(document).off("click", "#card-body-add").on("click", "#card-body-add", (e) => {
            super.addComponentByType(componentFactory, 'card-body', $el.find(".card"));
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('card');

        let options = {
            id:'card' + super.getComponentIdNumber('card'),
            size:"col-6",
            title:"Title"
        }

        let $componentEl = this.createComponent(options.id, options, componentFactory);
        $el.append($componentEl);

        super.addComponentByType(componentFactory, 'card-body', $componentEl.find(".card"));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.dropComponent($componentEl.find(".vb-card-tools"), componentFactory);

        super.sortable($componentEl, ".vb-card-body");

        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["component-button"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, ".vb-button");
    }
}