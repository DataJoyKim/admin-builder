class Input extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'input';
    }

    componentOptions() {
        return {
           id:this.componentId() + super.getComponentIdNumber(),
           size:'col-auto',
           width:'150px',
           label:''
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let style = ``;

        if(options.width) {
            style += `width:${options.width};`;
        }

        let el = $(`<div class="form-group ${options.size}" style="${style}"></div>`);

        if(options.label) {
            const labelEL = $(`<label for="${options.id}">${options.label}</label>`);
            el.append(labelEL);
        }

        const inputEl = $(`<input type="text" class="form-control rounded-0" id="${options.id}" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >`);
        el.append(inputEl);

        return el;
    }

    scriptRuntime(el, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let style = ``;

        if(options.width) {
            style += `width:${options.width};`;
        }

        let el = $(`
            <div id="${id}" class="component form-group ${options.size} vb-item vb-input" style="${style}" data-type="${this.componentId()}">
            ${super.componentDeleteBtn()}
            </div>
        `);

        const hiddenLabel = (options.label) ? '' : 'hidden';

        const labelEL = $(`<label for="${id}-el" ${hiddenLabel}>${options.label}</label>`);
        el.append(labelEL);

        const inputEl = $(`<input type="text" class="form-control rounded-0" id="${id}-el" placeholder="" spellcheck="false" autocomplete="off" data-watch="true" >`);
        el.append(inputEl);

        return el;
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 10px;
                height: auto;
                margin: 0 !important;
            }
        `;
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));
        $panel.append(this.optionPanel.input('id',{label:'ID', size:'col-6'}));
        $panel.append(this.optionPanel.select('size',{label:'크기', size:'col-6', options:this.optionPanel.optionSize()}));
        $panel.append(this.optionPanel.input('width',{label:'width', size:'col-6'}));
        $panel.append(this.optionPanel.input('label',{label:'라벨', size:'col-6'}));
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.setValue('size',options.size);
        this.optionPanel.setValue('width',options.width);
        this.optionPanel.setValue('label',options.label);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('id',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.changeEvent('size',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'size', $(e.target).val());
            this.optionPanel.changeSize($el, options.size);
        });

        this.optionPanel.inputEvent('width',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'width', $(e.target).val());
            $el.css('width',options.width);
        });

        this.optionPanel.inputEvent('label',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'label', $(e.target).val());
            $el.find("label").text(options.label);
            $el.find("label").prop('hidden', (options.label) ? false : true);
        });
    }
}