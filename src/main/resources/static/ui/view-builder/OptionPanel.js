export class OptionPanel {

    static setOptions($target, options) {
        $target.data('options', options);
    }

    static getOptions($target) {
        return $target.data('options');
    }

    static changeOptionValue($component, options, key, value) {
        options[key] = value;
        OptionPanel.setOptions($component, options);
    }

    static changeSize($target, newSize) {
        $target.removeClass(function(i, cls) {
            return (cls.match(/col-\d+/g) || []).join(' ');
        });
        $target.addClass(newSize);
    }

    static getSize($target) {
        return $target.attr("class").split(/\s+/).find(cls => cls.startsWith("col-"));
    }

    static optionSize() {
        let html = `<option value="col-auto">col-auto</option>`;

        for(let i=12; i>=1; i--) {
            html += `<option value="col-${i}">col-${i}</option>`;
        }

        return html;
    }

    static select(id, option) {
        return $(`
            <div class="form-group ${option.size}">
               <label for="${id}">${option.label}</label>
               <select type="text" class="form-control rounded-0" id="${id}">
                   ${option.options}
               </select>
            </div>
        `);
    }

    static button(id, option) {
        let html = ``;
        html += `<div class="form-group ${option.size}">`;
        if(option.label) {
            html += `<label for="${id}">${option.label}</label>`;
        }
        html += `<button id="${id}" type="button" class="btn btn-default btn-sm form-control">`;
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

    static input(id, option) {
        return $(`
            <div class="form-group ${option.size}">
                <label for="${id}">${option.label}</label>
                <input type="text" class="form-control rounded-0" id="${id}" spellcheck="false" autocomplete="off" value="${option.value}">
            </div>
        `);
    }

    static toggle(id, option) {
        return $(`
            <div class="form-group ${option.size}">
                <label for="${id}">${option.label}</label>
                <div class="custom-control custom-switch" style="transform: scale(1.5); transform-origin: left center;">
                    <input type="checkbox" class="custom-control-input" id="${id}">
                    <label for="${id}" class="custom-control-label" style="cursor: pointer;"></label>
                </div>
            </div>
        `);
    }

    static row() {
        return $(`<div class="row"></div>`);
    }

    static clickEvent(id, _handler) {
        $(document).off("click", "#"+id).on("click", "#"+id, _handler);
    }

    static inputEvent(id, _handler) {
        $("#"+id).off("input").on("input",_handler);
    }

    static changeEvent(id, _handler) {
        $("#"+id).off("change").on("change",_handler);
    }
}