import { OptionPanel } from '../OptionPanel.js';

export class ViewObject {
    constructor() {
        this._componentId = this.componentId();
    }

    componentId() {}

    componentOptions() {}

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    render(initQueue, options, children) {
        let el = this.renderRuntime(options, children);

        this.scriptRuntime(el, initQueue, options);

        return el;
    }

    renderRuntime(options, children) {}
    scriptRuntime(el, initQueue, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    component(id, options) {
        this.mountComponentStyle();

        let el = this.renderBuilder(id, options);

        return el;
    }

    renderBuilder(id, options) {}
    styleBuilder() {}

    mountComponentStyle() {
        if (!this.styleBuilder) return;

        const con = this.constructor;
        if (con.__componentStyleMounted) return; //중복방지

        const styleEl = document.createElement('style');
        styleEl.textContent = this.styleBuilder();

        document.head.appendChild(styleEl);

        con.__componentStyleMounted = true; //중복방지
    }

    componentDeleteBtn() {
        return `<button class="component-delete-btn">×</button>`;
    }

    addComponent($el, componentFactory) {
        this.plusComponentIdNumber();

        let options = this.componentOptions();

        let $componentEl = this.createComponent(options.id, options, componentFactory);
        $el.append($componentEl);

        this.afterAddComponent(componentFactory, $el, $componentEl);
    }

    afterAddComponent(componentFactory, $el, $componentEl) {}

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);

        this.opComponent.setOptions($componentEl, options);

        const dropConfig = this.componentDropConfig($componentEl);
        for(const config of dropConfig) {
            this.drop(config.element, config.allowedComponentIds, componentFactory);

            if(config.sortable) {
                this.sortable(config.element, this.createSortableIds(config.allowedComponentIds));
            }
        }

        const sortableConfig = this.componentSortableConfig($componentEl);
        for(const config of sortableConfig) {
            this.sortable(config.element, this.createSortableIds(config.sortableComponentIds));
        }

        return $componentEl;
    }

    componentDropConfig($componentEl) { return []; }

    componentSortableConfig($componentEl) { return []; }

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
        const dataType = $el.data('type');
        let connectWith;
        if(dataType == 'col' || dataType == 'row') {// col 과 row 간에 컴포넌트 이동가능
            connectWith = ".vb-item[data-type='col'], .vb-item[data-type='row']";
        }
        else { // 자신의 컴포넌트 유형만 허용
            connectWith = ".vb-item[data-type='"+dataType+"']";
        }

        $el.sortable({
            items: items,
            helper: "clone",
            connectWith: connectWith,
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

    plusComponentIdNumber() {
        let numberId = window.componentIdMap[this._componentId];
        window.componentIdMap[this._componentId] = numberId+1;
    }

    getComponentIdNumber() {
        return window.componentIdMap[this._componentId];
    }

    createSortableIds(allowedComponentIds) {
        return allowedComponentIds
                .map(componentId => `.vb-item[data-type="${componentId}"]`)
                .join(",");
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
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

    get opComponent() {
        return OptionPanel;
    }
}