import { OptionPanel } from '../OptionPanel.js';

export class ViewObject {

    get opComponent() {
        return OptionPanel;
    }

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
        let options = this.opComponent.getOptions($el);

        let $panel = $("#"+id);

        $panel.empty();

        this.optionPanelView($panel, options);

        this.optionPanelScript($el, options);

        this.optionPanelEvent($el, options, componentFactory);
    }

    optionPanelView($panel, options) {}
    optionPanelScript($el, options) {}
    optionPanelEvent($el, options, componentFactory) {}

    componentDeleteBtn() {
        return `<button class="component-delete-btn">×</button>`;
    }

    addComponent($el, componentFactory) {}
    dropComponent($el, componentFactory) {}

    addComponentByType(componentFactory, type, $el) {
        componentFactory[type].addComponent($el, componentFactory);
    }

    drop($el, allowedTypes, componentFactory) {
        let self = this;

        $el.droppable({
            greedy: true,
            hoverClass: "drop-hover",
            drop: function (event, ui) {
                const type = ui.draggable.data("type");
                if(!type.includes('component-')) { // 좌측 패널의 컴포넌트만 Drop 허용
                    return;
                }

                const componentType = type.replace('component-','');
                if (!allowedTypes.includes(componentType)) {
                    return;
                }

                self.addComponentByType(componentFactory, componentType, $el);
            },
        });
    }

    sortable($el, items) {
        $el.sortable({
            items: items,
            helper: "clone",
            //containment: "parent",
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

    getSortableType(allowedTypes) {
        return allowedTypes
                .map(type => `.vb-item[data-type="${type}"]`)
                .join(",");
    }
}