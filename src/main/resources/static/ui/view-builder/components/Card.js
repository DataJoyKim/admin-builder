class Card extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'card';
    }

    componentOptions() {
        return {
           id:'card' + super.getComponentIdNumber(),
           size:"col-6",
           title:"Title",
           useCardHeader:true
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
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

        let el = $(`
            <div class="${options.size}">
                 <div id="${options.id}" class="card">
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

    scriptRuntime(el, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let cardHeaderClass = ``;
        if(!options.useCardHeader) {
            cardHeaderClass = `d-none`;
        }

        let el = `
            <div id="${id}" class="component ${options.size} vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                 <div class="card">
                    <div class="card-header ${cardHeaderClass}">
                        <h3 class="card-title">${options.title}</h3>
                        <div class="card-tools">
                        </div>
                    </div>
                 </div>
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] .card-tools {
                padding-left: 30px;
                min-width: 60px;
                width: auto;
                min-height: 30px;
                height: auto;
                border: 1px dashed #bbb;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl.find(".card-tools"),
            allowedComponentIds: ["button","custom-html"],
            sortable: true
        }]
    }

    componentSortableConfig($componentEl) {
        return [{
            element: $componentEl,
            sortableComponentIds: ["card-body"]
        }]
    }

    afterAddComponent(componentFactory, $el, $componentEl) {
        super.addComponentByType(componentFactory, 'card-body', $componentEl.find(".card"));
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('card-id',{label:'ID', size:'col-3'}));
        $panel.append(this.optionPanel.select('card-size',{label:'크기', size:'col-12', options:this.optionPanel.optionSize()}));
        $panel.append(this.optionPanel.toggle('card-header-use',{label:'Card Header 사용', size:'col-12'}));
        $panel.append(this.optionPanel.input('card-title-input',{label:'Card Header 제목', size:'col-12'}));
        $panel.append(this.optionPanel.button('card-body-add',{label:'Card 내용', size:'col-12', btnLabel:'컨텐츠 영역 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        $('#card-id').val(options.id);
        $('#card-header-use').prop('checked',options.useCardHeader);
        $('#card-title-input').val(options.title);
        $('#card-size').val(options.size);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('card-id',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.changeEvent('card-size',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'size', $(e.target).val());
            this.optionPanel.changeSize($el, options.size);
        });

        this.optionPanel.inputEvent('card-title-input',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'title', $(e.target).val());
            $el.find(".card-title").text(options.title);
        });

        this.optionPanel.clickEvent('card-body-add',(e) => {
            super.addComponentByType(componentFactory, 'card-body', $el.find(".card"));
        });

        this.optionPanel.clickEvent('card-header-use',(e) => {
            let value = $(e.target).is(':checked');
            this.optionPanel.changeOptionValue($el, options, 'useCardHeader', value);
            if(value) {
                $el.find(".card-header").removeClass('d-none');
            }
            else {
                $el.find(".card-header").addClass('d-none');
            }
        });
    }
}