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

        if (children && children.htmlCardTools) {
            el.find(".card-tools").append(children.htmlCardTools);
        }

        if (children && children.htmlChild) {
            el.find(".card").append(children.htmlChild);
        }

        return el;
    }

    script(el, initQueue, data) {}

    component(id, options) {
        let btnDelete = super.componentDeleteBtn();
        let el = `
            <div id="${id}" class="component vb-card ${options.size}">
                ${btnDelete}
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

    optionPanelView(options) {
         let html = ``;
        html += super.optionSelect('card-size', '크기', 'col-12', super.getSizeOption(options.size));
        html += super.optionInput('card-title-input', 'Card 제목', 'col-12', options.title);
        html += super.optionButton('card-body-add', 'Card 내용', 'col-12', '컨텐츠 영역 추가');

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options) {
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
            //appendCardBody($el.find(".card"));
        });
    }
}