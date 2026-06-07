class OptionPanel {
    constructor() {}

    init(panelId, optionInfo) {
        this.panelId = panelId;

        let metadata = {};
        for(const info of optionInfo) {
            metadata[info.id] = info;
        }

        this.metadata = metadata;
    }

    getHtml(id) {
        const info = this.metadata[id];
        if(info.type == 'text') {
            return this.input(info.id, {label:info.label, size:info.size});
        }
        else if(info.type == 'code') {
            return this.codeEditor(info.id, {label:info.label, size:info.size});
        }
        else if(info.type == 'sheet') {
            return this.sheet(info.id, {label:info.label, size:info.size});
        }
    }

    setOptionValue(options) {
        for(const id in this.metadata) {
            const info = this.metadata[id];

            if(info.type == 'text') {
                this.setValue(id, options[id]);
            }
            else if(info.type == 'code') {
                this.setCodeEditorValue(id, options[id]);
            }
            else if(info.type == 'sheet') {
                this.setSheetValue(id, options[id]);
            }
        }
    }

    getOptionValue() {
        let optionValue = {};
        for(const id in this.metadata) {
            const info = this.metadata[id];

            if(info.type == 'text') {
                optionValue[id] = this.getValue(id);
            }
            else if(info.type == 'code') {
                optionValue[id] = this.getCodeEditorValue(id);
            }
            else if(info.type == 'sheet') {
                optionValue[id] = this.getSheetValue(id);
            }
        }

        return optionValue;
    }

    elementId(id) {
        return this.panelId + '-' + id;
    }

    getSize($target) {
        return $target.attr("class").split(/\s+/).find(cls => cls.startsWith("col-"));
    }

    optionSize() {
        let html = `<option value="col-auto">col-auto</option>`;

        for(let i=12; i>=1; i--) {
            html += `<option value="col-${i}">col-${i}</option>`;
        }

        return html;
    }

    select(id, option) {
        return $(`
            <div class="form-group ${option.size}">
               <label for="${this.elementId(id)}">${option.label}</label>
               <select type="text" class="form-control rounded-0" id="${this.elementId(id)}">
                   ${option.options}
               </select>
            </div>
        `);
    }

    button(id, option) {
        let html = ``;
        html += `<div class="form-group ${option.size}">`;
        if(option.label) {
            html += `<label for="${this.elementId(id)}">${option.label}</label>`;
        }
        html += `<button id="${this.elementId(id)}" type="button" class="btn btn-default btn-sm form-control">`;
        if(option.icon) {
            html += `<i class="${option.icon}"></i>`;
        }
        if(option.btnLabel) {
            html += `${option.btnLabel}`;
        }
        html += `</button>`;
        html += `</div>`;

        return $(html);
    }

    input(id, option) {
        const formGroupEl = this.formGroup(option);

        formGroupEl.append(this.label(id, option));

        const inputEl = $(`<input type="text" class="form-control form-control-sm rounded-0" id="${this.elementId(id)}">`);
        inputEl.attr('spellcheck',false);
        inputEl.attr('autocomplete','off');
        inputEl.prop('readonly', !(option.enabled ?? true));

        if(option.value != undefined) {
            inputEl.val(option.value);
        }

        formGroupEl.append(inputEl);

        return formGroupEl;
    }

    codeEditor(id, option) {
        const formGroupEl = this.formGroup(option);

        formGroupEl.append(this.label(id, option));

        const textareaEl = $(`<textarea id="${id}"  ></textarea>`);

        formGroupEl.append(textareaEl);

        return formGroupEl;
    }

    codeEditorScript(id, width, height) {
        let textarea = document.getElementById(id);

        let codeEditor = CodeMirror.fromTextArea(textarea, {
            lineNumbers: true,
            lineWrapping: true,
            theme: "darcula",
            mode: "text/javascript",
            val: textarea.value
        });

        codeEditor.setSize(width, height);

        window['_codeEditor_'+id] = codeEditor;
    }

    sheet(id, option) {
        const formGroupEl = this.formGroup(option);

        formGroupEl.append(this.label(id, option));
        formGroupEl.append($(`<button type="button" class="btn btn-default btn-sm" onclick="_sheet_${id}.addRowData({})"><i class="fas fa-plus"></i><span>입력</span></button>`));

        const divEl = $(`<div id="${id}"  ></div>`);

        formGroupEl.append(divEl);

        return formGroupEl;
    }

    sheetScript(id, width, height, columns) {
        const sheet = new Sheet(id, width, height,{useSeq:false,useStatus:false,useDelete:true}, columns);
        window['_sheet_'+id] = sheet;
    }

    toggle(id, option) {
        const formGroupEl = this.formGroup(option);

        formGroupEl.append(this.label(id, option));

        const toggleEl = $(`
                <div class="custom-control custom-switch" style="transform: scale(1.5); transform-origin: left center;">
                    <input type="checkbox" class="custom-control-input" id="${this.elementId(id)}">
                    <label for="${this.elementId(id)}" class="custom-control-label" style="cursor: pointer;"></label>
                </div>
            `)

        formGroupEl.append(toggleEl);

        return formGroupEl;
    }

    formGroup(option) {
        return $(`<div class="form-group ${option.size}"></div>`);
    }

    label(id, option) {
        return $(`<label for="${this.elementId(id)}">${option.label}</label>`);
    }

    row() {
        return $(`<div class="row"></div>`);
    }

    clickEvent(id, _handler) {
        $(document).off("click", "#"+this.elementId(id)).on("click", "#"+this.elementId(id), _handler);
    }

    inputEvent(id, _handler) {
        $("#"+this.elementId(id)).off("input").on("input",_handler);
    }

    changeEvent(id, _handler) {
        $("#"+this.elementId(id)).off("change").on("change",_handler);
    }

    setValue(id,value) {
        $('#'+this.elementId(id)).val(value);
    }

    getValue(id) {
        return $('#'+this.elementId(id)).val();
    }

    getCodeEditorValue(id) {
        return window['_codeEditor_'+id].getValue();
    }

    setCodeEditorValue(id,value) {
        window['_codeEditor_'+id].setValue(value);
    }

    getSheetValue(id) {
         let columns = window['_sheet_'+id].getSheetData();

         return columns.map(({ _status, _delete, _seq, _dnd, ...rest }) => rest);
    }

    setSheetValue(id,value) {
        if(value) {
            for(let v of value) {
                v._status = "C";
            }
        }
        else {
            value = [];
        }

        window['_sheet_'+id].setSheetData(value);
    }

    check(id,value) {
        $('#'+this.elementId(id)).prop('checked',value);
    }
}