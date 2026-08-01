class Row extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'row';
    }

    componentOptions() {
        return {
           id:this.componentId() + super.getComponentIdNumber()
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
            <div id="${options.id}" class="row" >
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
            <div id="${options.id}" class="component row vb-item vb-container" data-type="${this.componentId()}">
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

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl,
            sortable: true
        }]
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));
        $panel.append(this.optionPanel.button('col-add',{label:'내용', size:'col-12', btnLabel:'열 추가',icon:'fas fa-plus'}));
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.clickEvent('col-add',(e) => {
          super.addComponentByType(componentFactory, 'col', $el);
        });
    }
}