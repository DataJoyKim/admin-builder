class CardBody extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'card-body';
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
             <div class="card-body">
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
             <div id="${options.id}" class="component card-body vb-item vb-container" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                <div class="drop-area-label">
                    + Drag & Drop
                </div>
             </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 5px;
                min-height: 50px;
                height: auto;
                margin: 5px;
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
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
    }

    optionPanelEvent($el, options, componentFactory) {}
}