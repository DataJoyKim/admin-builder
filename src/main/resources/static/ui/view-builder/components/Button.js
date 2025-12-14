import { ViewObject } from './ViewObject.js';

export class Button extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el =  $(`
            <div id="${data.id}" class="btn btn-default btn-sm">
                <i class="${data.icon}"></i>
                ${data.label}
            </div>
        `);

        return el;
    }

    script(el, initQueue, data) {}

    component(id, options) {
        let btnDelete = super.componentDeleteBtn();
        let el = `
            <div id="${id}" type="button" class="component vb-item vb-button btn btn-default btn-sm" data-type="button">
                ${btnDelete}
                <i class="${options.icon}"></i>
                <span class="vb-button-label">${options.label}</span>
            </div>
        `;

        return $(el);
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionInput('button-icon', '버튼 아이콘', 'col-12', options.icon);
        html += super.optionInput('button-text', '버튼 라벨', 'col-12', options.label);

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $("#button-text").off("input").on("input", (e) => {
            options.label = $(e.target).val();
            super.setOptions($el, options);
            $el.find(".vb-button-label").text(options.label);
        });

        $("#button-icon").off("input").on("input", (e) => {
            $el.find("i").removeClass(options.icon);

            options.icon = $(e.target).val();
            super.setOptions($el, options);

            $el.find("i").addClass(options.icon);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('button');

        let options = {
            id:'button' + super.getComponentIdNumber('button'),
            label:'Button',
            icon:'fas fa-search'
        }

        let $componentEl = this.component(options.id, options);

        $el.append($componentEl);

        super.setOptions($componentEl, options);
    }

    dropComponent($el, componentFactory) {
    }
}