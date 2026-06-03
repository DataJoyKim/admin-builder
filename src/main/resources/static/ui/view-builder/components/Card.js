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
           id:this.componentId() + super.getComponentIdNumber(),
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

        const cardEl = el.children(".card");

        if (children && children.cardHeader) {
            cardEl.children(".card-header").children(".card-tools").append(children.cardHeader);
        }

        if (children && children.cardChildren) {
            cardEl.append(children.cardChildren);
        }

        return el;
    }

    scriptRuntime(el, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let cardHeaderClass = ``;
        if(!options.useCardHeader) {
            cardHeaderClass = `d-none`;
        }

        let el = `
            <div id="${options.id}" class="component ${options.size} vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                 <div class="card">
                    <div class="card-header ${cardHeaderClass}">
                        <h3 class="card-title">${options.title}</h3>
                        <div class="card-tools vb-container">
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
                padding-left: 80px;
                min-width: 150px;
                width: auto;
                min-height: 30px;
                height: auto;
                border: 1px dashed #bbb;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: this.getElement($componentEl).cardToolsEl,
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
        super.addComponentByType(componentFactory, 'card-body', this.getElement($componentEl).cardEl);
    }

    getElement($el) {
        const cardEl = $el.children(".card");
        const cardHeaderEl = cardEl.children(".card-header");

        return {
            cardEl:cardEl,
            cardHeaderEl:cardHeaderEl,
            cardTitleEl:cardHeaderEl.children(".card-title"),
            cardToolsEl:cardHeaderEl.children(".card-tools")
        }
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));
        $panel.append(this.optionPanel.input('id',{label:'ID', size:'col-3'}));
        $panel.append(this.optionPanel.select('size',{label:'크기', size:'col-12', options:this.optionPanel.optionSize()}));
        $panel.append(this.optionPanel.toggle('header-use',{label:'Card Header 사용', size:'col-12'}));
        $panel.append(this.optionPanel.input('title-input',{label:'Card Header 제목', size:'col-12'}));
        $panel.append(this.optionPanel.button('button-add',{label:'Card 헤더 버튼 추가', size:'col-12', btnLabel:'추가',icon:'fas fa-plus'}));
        $panel.append(this.optionPanel.button('body-add',{label:'Card 컨텐츠 영역 추가', size:'col-12', btnLabel:'추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.setValue('title-input',options.title);
        this.optionPanel.setValue('size',options.size);
        this.optionPanel.check('header-use',options.useCardHeader);
    }

    optionPanelEvent($el, options, componentFactory) {
        const {cardEl, cardHeaderEl, cardTitleEl, cardToolsEl} = this.getElement($el);

        this.optionPanel.inputEvent('id',(e) => {
            super.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.changeEvent('size',(e) => {
            super.changeOptionValue($el, options, 'size', $(e.target).val());
            super.changeSize($el, options.size);
        });

        this.optionPanel.inputEvent('title-input',(e) => {
            super.changeOptionValue($el, options, 'title', $(e.target).val());
            cardTitleEl.text(options.title);
        });

        this.optionPanel.clickEvent('button-add',(e) => {
            super.addComponentByType(componentFactory, 'button', cardToolsEl);
        });

        this.optionPanel.clickEvent('body-add',(e) => {
            super.addComponentByType(componentFactory, 'card-body', cardEl);
        });

        this.optionPanel.clickEvent('header-use',(e) => {
            cardHeaderEl.removeClass('d-none');

            let value = $(e.target).is(':checked');
            super.changeOptionValue($el, options, 'useCardHeader', value);

            if(!value) {
                cardHeaderEl.addClass('d-none');
            }
        });
    }
}