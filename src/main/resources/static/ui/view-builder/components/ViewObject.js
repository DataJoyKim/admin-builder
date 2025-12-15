export class ViewObject {
    render(initQueue, data, children) {
        let el = this.template(data, children);

        this.script(el, initQueue, data);

        return el;
    }

    template(data, children) {}
    script(el, initQueue, data) {}

    component(id, options) {
        this.mountComponentStyle();

        let el = this.componentTemplate(id, options);

        return el;
    }

    componentTemplate(id, options) {}
    componentStyle() {}

    mountComponentStyle() {
        if (!this.componentStyle) return;

        const con = this.constructor;
        if (con.__componentStyleMounted) return; //중복방지

        const styleEl = document.createElement('style');
        styleEl.textContent = this.componentStyle();

        document.head.appendChild(styleEl);

        con.__componentStyleMounted = true; //중복방지
    }

    optionPanel($el, id, componentFactory) {
        let options = this.getOptions($el);

        $("#"+id).html(this.optionPanelView(options));

        this.optionPanelScript($el, options);

        this.optionPanelEvent($el, options, componentFactory);
    }

    optionPanelView(options) {}
    optionPanelScript($el, options) {}
    optionPanelEvent($el, options, componentFactory) {}

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

    addComponent($el, componentFactory) {}
    dropComponent($el, componentFactory) {}

    drop($el, allowedTypes, componentFactory) {
        let self = this;
        $el.droppable({
            greedy: true,
            drop: function (event, ui) {
                const type = ui.draggable.data("type");
                if (!allowedTypes.includes(type)) {
                    return;
                }

                self.addComponentByType(componentFactory, type.replace('component-',''), $el);
            },
        });
    }

    addComponentByType(componentFactory, type, $el) {
        componentFactory[type].addComponent($el, componentFactory);
    }

    sortable($el, items) {
        $el.sortable({
            items: items,
            containment: "parent",
            tolerance: "pointer",
            placeholder: "sortable-placeholder",
            start: function(event, ui){
                ui.placeholder.css({
                    height: ui.item.outerHeight(),
                    border: "1px dashed #bbb",
                    background: "#f0f0f0"
                });
            }
        });
    }

    plusComponentIdNumber(id) {
        let numberId = window.componentIdMap[id];
        window.componentIdMap[id] = numberId+1;
    }

    getComponentIdNumber(id) {
        return window.componentIdMap[id];
    }
}