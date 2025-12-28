import { ViewObject } from './ViewObject.js';

export class Card extends ViewObject {
    constructor() {
        super();
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let carHeaderHtml = ``;
        if(data.useCardHeader) {
            carHeaderHtml = `
               <div class="card-header">
                    <h3 class="card-title">${data.title}</h3>
                    <div class="card-tools">
                    </div>
               </div>
            `;
        }

        let el = $(`
            <div class="${data.size}">
                 <div id="${data.id}" class="card">
                     ${carHeaderHtml}
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

    scriptRuntime(el, initQueue, data) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let carHeaderHtml = ``;
        if(options.useCardHeader) {
            carHeaderHtml = `
                <div class="card-header">
                    <h3 class="card-title">${options.title}</h3>
                    <div class="card-tools">
                    </div>
                </div>
            `;
        }

        let el = `
            <div id="${id}" class="component ${options.size} vb-item" data-type="card">
                ${super.componentDeleteBtn()}
                 <div class="card">
                     ${carHeaderHtml}
                 </div>
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
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

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('card');

        let options = {
            id:'card' + super.getComponentIdNumber('card'),
            size:"col-6",
            title:"Title",
            useCardHeader:true
        }

        let $componentEl = this.createComponent(options.id, options, componentFactory);
        $el.append($componentEl);

        super.addComponentByType(componentFactory, 'card-body', $componentEl.find(".card"));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.dropComponent($componentEl.find(".card-tools"), componentFactory);

        super.sortable($componentEl, ".card-body");

        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
        let allowedTypes = ["button","custom-html"];

        super.drop($el, allowedTypes, componentFactory);

        super.sortable($el, super.getSortableType(allowedTypes));
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(super.opComponent.input('card-id',{label:'ID', size:'col-3'}));
        $panel.append(super.opComponent.select('card-size',{label:'크기', size:'col-12', options:super.opComponent.optionSize()}));
        $panel.append(super.opComponent.toggle('card-header-use',{label:'Card Header 사용', size:'col-12'}));
        $panel.append(super.opComponent.input('card-title-input',{label:'Card Header 제목', size:'col-12'}));
        $panel.append(super.opComponent.button('card-body-add',{label:'Card 내용', size:'col-12', btnLabel:'컨텐츠 영역 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        $('#card-id').val(options.id);
        $('#card-header-use').prop('checked',options.useCardHeader);
        $('#card-title-input').val(options.title);
        $('#card-size').val(options.size);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('card-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.changeEvent('card-size',(e) => {
            super.opComponent.changeOptionValue($el, options, 'size', $(e.target).val());
            super.opComponent.changeSize($el, options.size);
        });

        super.opComponent.inputEvent('card-title-input',(e) => {
            super.opComponent.changeOptionValue($el, options, 'title', $(e.target).val());
            $el.find(".card-title").text(options.title);
        });

        super.opComponent.clickEvent('card-body-add',(e) => {
            super.addComponentByType(componentFactory, 'card-body', $el.find(".card"));
        });

        super.opComponent.clickEvent('card-header-use',(e) => {
            let value = $(e.target).is(':checked');
            super.opComponent.changeOptionValue($el, options, 'useCardHeader', value);
            $el.find(".card-header").css('display',((value)?'block':'none'));
        });
    }
}