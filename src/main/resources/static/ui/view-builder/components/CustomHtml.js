import { ViewObject } from './ViewObject.js';

export class CustomHtml extends ViewObject {
    constructor() {
        super();
        this.contentEditor;
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        let el =  $(`
            <div id="${data.id}" class="${data.className}">
                ${data.htmlContent}
            </div>
        `);

        return el;
    }

    scriptRuntime(el, initQueue, data) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <div id="${id}" class="component vb-item ${options.className}" data-type="custom-html">
                ${super.componentDeleteBtn()}
                ${options.htmlContent}
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="custom-html"] {
                padding: 5px;
                min-height: 30px;
                height: auto;
                margin: 0 !important;
            }
        `;
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('custom-html');

        let options = {
            id:'custom-html' + super.getComponentIdNumber('custom-html'),
            className:'',
            htmlContent:'Html 내용 입력'
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
        $panel.append(super.opComponent.input('custom-html-id',{label:'ID', size:'col-5'}));
        $panel.append(super.opComponent.input('custom-html-class',{label:'class 명', size:'col-6'}));
        $panel.append($(`
            <div class="form-group col-12">
               <label for="custom-html-content">Html 내용</label>
                <textarea id="custom-html-content"></textarea>
            </div>
        `));
    }

    optionPanelScript($el, options) {
        $('#custom-html-id').val(options.id);
        $('#custom-html-class').val(options.className);

        let textarea = document.getElementById('custom-html-content');
        this.contentEditor = CodeMirror.fromTextArea(textarea, {
            lineNumbers: true,
            lineWrapping: true,
            theme: "darcula",
            mode: 'htmlmixed'
        });
        this.contentEditor.setSize("100%", "500px");
        this.contentEditor.setValue(options.htmlContent);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('custom-html-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.inputEvent('custom-html-class',(e) => {
            super.opComponent.changeOptionValue($el, options, 'className', $(e.target).val());
        });

        const opComponent = super.opComponent;
        const componentDeleteBtn = super.componentDeleteBtn();
        this.contentEditor.on("change", function(instance, changeObj) {
            const value = instance.getValue();
            opComponent.changeOptionValue($el, options, 'htmlContent', value);
            $el.html(componentDeleteBtn + value);
        });
    }
}