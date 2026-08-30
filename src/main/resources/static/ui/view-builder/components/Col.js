class Col extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'col';
    }

    componentOptions() {
        return {
           id:this.componentId() + super.getComponentIdNumber(),
           size:'col-6'
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
            <div id="${options.id}" class="${options.size}" >
            </div>
        `);

        if (children) {
            el.append(children);
        }

        return el;
    }

    scriptRuntime(el, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let el = `
            <div id="${options.id}" class="component ${options.size} vb-item vb-container" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                background-color: white;
                padding: 5px;
                min-height: 50px;
                height: auto;
                margin: 0 !important;
                border: 1px dashed #bbb;
            }
        `;
    }

    componentContainerConfig($componentEl) {
        return [{
            element: $componentEl
        }]
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));
        $panel.append(this.optionPanel.select('size',{label:'크기', size:'col-6', options:this.optionPanel.optionSize()}));
        $panel.append(this.optionPanel.button('row-add',{label:'내용', size:'col-12', btnLabel:'행 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('size',options.size);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.changeEvent('size',(e) => {
            super.changeOptionValue($el, options, 'size', $(e.target).val());
            super.changeSize($el, options.size);
        });
        this.optionPanel.clickEvent('row-add',(e) => {
          super.addComponentByType(componentFactory, 'row', $el);
        });
    }
}