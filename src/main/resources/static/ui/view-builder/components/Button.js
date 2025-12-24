import { ViewObject } from './ViewObject.js';

export class Button extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el =  $(`
            <div id="${data.id}" class="btn btn-default btn-sm" onclick="${data.action}()">
                <i class="${data.icon}"></i>
                ${data.label}
            </div>
        `);

        return el;
    }

    script(el, initQueue, data) {}

    componentTemplate(id, options) {
        let el = `
            <div id="${id}" type="button" class="component vb-item vb-button btn btn-default btn-sm" data-type="button">
                ${super.componentDeleteBtn()}
                <i class="${options.icon}"></i>
                <span class="vb-button-label">${options.label}</span>
            </div>
        `;

        return $(el);
    }

    componentStyle() {
        return `
            .vb-item[data-type="row"] {
                padding: 5px;
                min-height: 80px;
                height: auto;
                margin: 0 !important;
                border: 1px dashed #bbb;
            }
        `;
    }

    optionPanelView(options) {
        let html = ``;
        html += super.optionInput('button-id', 'ID', 'col-3', options.id);
        html += super.optionInput('button-icon', '버튼 아이콘', 'col-12', options.icon);
        html += super.optionInput('button-text', '버튼 라벨', 'col-12', options.label);
        html += super.optionInput('button-action', 'Action', 'col-12', options.action);

        return html;
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        $("#button-id").off("input").on("input", (e) => {
            options.id = $(e.target).val();
            super.setOptions($el, options);
        });

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

        $("#button-action").off("input").on("input", (e) => {
            options.action = $(e.target).val();
            super.setOptions($el, options);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('button');

        let options = {
            id:'button' + super.getComponentIdNumber('button'),
            label:'Button',
            icon:'fas fa-search',
            action:''
        }

        $el.append(this.createComponent(options.id, options, componentFactory));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }
}