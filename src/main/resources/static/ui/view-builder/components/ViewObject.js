export class ViewObject {
    render(initQueue, data, children) {
        let el = this.template(data, children);

        this.script(el, initQueue, data);

        return el;
    }

    template(data, children) {}
    script(el, initQueue, data) {}
    component(id, options) {}

    optionPanel($el, id) {
        let options = this.getOptions($el);

        $("#"+id).html(this.optionPanelView(options));

        this.optionPanelScript($el, options);

        this.optionPanelEvent($el, options);
    }

    optionPanelView(options) {}
    optionPanelScript($el, options) {}
    optionPanelEvent($el, options) {}

    setOptions($target, options) {
        $target.data('options', options);
    }

    getOptions($target) {
        return $target.data('options');
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

    componentDeleteBtn() {
        return `<button class="component-delete-btn">×</button>`;
    }

    getSizeOption(value) {
        let html = ``;
        for(let i=12; i>=1; i--) {
            let selected = '';
            if(value == `col-${i}`) {
                selected = 'selected';
            }

            html += `<option value="col-${i}" ${selected}>${i}</option>`;
        }

        return html;
    }

    optionSelect(id, label, size, options) {
        return `
            <div class="form-group ${size}">
               <label for="${id}">${label}</label>
               <select type="text" class="form-control rounded-0" id="${id}">
                   ${options}
               </select>
            </div>
        `;
    }

    optionButton(id, label, size, buttonLabel) {
        return `
            <div class="form-group ${size}">
               <label for="${id}">${label}</label>
               <div id="${id}" type="button" class="btn btn-default btn-sm form-control">
               <i class="fas fa-plus"></i>
               ${buttonLabel}
               </div>
            </div>
        `;
    }

    optionInput(id, label, size, value) {
        return `
            <div class="form-group ${size}">
                <label for="${id}">${label}</label>
                <input type="text" class="form-control rounded-0" id="${id}" spellcheck="false" autocomplete="off" value="${value}">
            </div>
        `;
    }
}