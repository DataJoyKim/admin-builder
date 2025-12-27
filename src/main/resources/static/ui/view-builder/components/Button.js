import { ViewObject } from './ViewObject.js';

export class Button extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el =  $(`
            <button id="${data.id}" type="button" class="btn btn-default btn-sm" onclick="${data.action}()">
                <i class="${data.icon}"></i>
                ${data.label}
            </button>
        `);

        return el;
    }

    scriptRuntime(el, initQueue, data) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <div id="${id}" class="component vb-item btn btn-default btn-sm" data-type="button">
                ${super.componentDeleteBtn()}
                <i class="${options.icon}"></i>
                <span class="button-label">${options.label}</span>
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
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

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('button-id',{label:'ID', size:'col-3'}));
        $panel.append(super.opComponent.input('button-icon',{label:'아이콘', size:'col-12'}));
        $panel.append(super.opComponent.input('button-text',{label:'라벨', size:'col-12'}));
        $panel.append(super.opComponent.input('button-action',{label:'Action', size:'col-8'}));
        $panel.append(super.opComponent.button('button-action-popup',{size:'col-4', icon:'fas fa-search', btnLabel:'Action 검색'}));
    }

    optionPanelScript($el, options) {
        $('#button-id').val(options.id);
        $('#button-icon').val(options.icon);
        $('#button-text').val(options.label);
        $('#button-action').val(options.action);
    }

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

        super.opComponent.clickEvent('button-action-popup',(e) => {
            let objectCode = $("#objectCode").val();
            if(!objectCode) {
                alert('오브젝트를 입력해주세요');
                return;
            }

            App.modalPopup.open('/console/action',{title:'Action 팝업',messageId:'ACTION_REQUEST'},{objectCode:objectCode});
            App.modalPopup.receiveParam('ACTION_RESULT', function(data){
                if(data.actionName) {
                    $("#button-action").val(data.actionName);
                }
            });
        });
    }
}