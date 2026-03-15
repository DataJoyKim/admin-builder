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
           id:'input' + super.getComponentIdNumber(),
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
        $panel.append(this.optionPanel.input('input-id',{label:'ID', size:'col-6'}));
        $panel.append(this.optionPanel.select('input-size',{label:'크기', size:'col-6', options:this.optionPanel.optionSize()}));
        $panel.append(this.optionPanel.input('input-width',{label:'width', size:'col-6'}));
        $panel.append(this.optionPanel.input('input-label',{label:'라벨', size:'col-6'}));
    }

    optionPanelScript($el, options) {
        $('#input-id').val(options.id);
        $('#input-size').val(options.size);
        $('#input-width').val(options.width);
        $('#input-label').val(options.label);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('input-id',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.changeEvent('input-size',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'size', $(e.target).val());
            this.optionPanel.changeSize($el, options.size);
        });

        this.optionPanel.inputEvent('input-width',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'width', $(e.target).val());
            $el.css('width',options.width);
        });

        this.optionPanel.inputEvent('input-label',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'label', $(e.target).val());
            $el.find("label").text(options.label);
            $el.find("label").prop('hidden', (options.label) ? false : true);
        });
    }
}