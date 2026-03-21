class OptionPanel {

    init(panelId) {
        this.panelId = panelId;
    }

    elementId(id) {
        return this.panelId + '-' + id;
    }

    setOptions($target, options) {
        $target.data('options', options);
    }

    getOptions($target) {
        return $target.data('options');
    }

    changeOptionValue($component, options, key, value) {
        options[key] = value;
        this.setOptions($component, options);
    }

    changeSize($target, newSize) {
        $target.removeClass(function(i, cls) {
            return (cls.match(/col-\d+/g) || []).join(' ');
        });
        $target.addClass(newSize);
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
        let disabled;
        if(option.enabled == undefined || option.enabled == null || option.enabled) {
            disabled = '';
        }
        else {
            disabled = 'readOnly';
        }

        return $(`
            <div class="form-group ${option.size}">
                <label for="${this.elementId(id)}">${option.label}</label>
                <input type="text" class="form-control rounded-0" id="${this.elementId(id)}" spellcheck="false" autocomplete="off" value="${option.value}" ${disabled}>
            </div>
        `);
    }

    toggle(id, option) {
        return $(`
            <div class="form-group ${option.size}">
                <label for="${this.elementId(id)}">${option.label}</label>
                <div class="custom-control custom-switch" style="transform: scale(1.5); transform-origin: left center;">
                    <input type="checkbox" class="custom-control-input" id="${this.elementId(id)}">
                    <label for="${this.elementId(id)}" class="custom-control-label" style="cursor: pointer;"></label>
                </div>
            </div>
        `);
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

    check(id,value) {
        $('#'+this.elementId(id)).prop('checked',value);
    }
}