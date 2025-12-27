import { ViewObject } from './ViewObject.js';

export class Button extends ViewObject {
    constructor() {
        super();
    }

    template(data, children) {
        let el =  $(`
            <button id="${data.id}" type="button" class="btn btn-default btn-sm" onclick="${data.action}()">
                <i class="${data.icon}"></i>
                ${data.label}
            </button>
        `);

        return el;
    }

    script(el, initQueue, data) {}

    componentTemplate(id, options) {
        let el = `
            <div id="${id}" class="component vb-item btn btn-default btn-sm" data-type="button">
                ${super.componentDeleteBtn()}
                <i class="${options.icon}"></i>
                <span class="button-label">${options.label}</span>
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

    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('button-id',{label:'ID', size:'col-3', value:options.id}));
        $panel.append(super.opComponent.input('button-icon',{label:'아이콘', size:'col-12', value:options.icon}));
        $panel.append(super.opComponent.input('button-text',{label:'라벨', size:'col-12', value:options.label}));
        $panel.append(super.opComponent.input('button-action',{label:'Action', size:'col-12', value:options.action}));
    }

    optionPanelScript($el, options) {}

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('button-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.inputEvent('button-text',(e) => {
            super.opComponent.changeOptionValue($el, options, 'label', $(e.target).val());
            $el.find(".button-label").text(options.label);
        });

        super.opComponent.inputEvent('button-icon',(e) => {
            $el.find("i").removeClass(options.icon);

            super.opComponent.changeOptionValue($el, options, 'icon', $(e.target).val());

            $el.find("i").addClass(options.icon);
        });

        super.opComponent.inputEvent('button-action',(e) => {
            super.opComponent.changeOptionValue($el, options, 'action', $(e.target).val());
        });
    }

    addComponent($el, componentFactory) {lo
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

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }
}