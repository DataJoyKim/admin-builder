class ViewObject {
    constructor(optionPanel) {
        this.optionPanel = optionPanel;
        this._componentId = this.componentId();
    }

    componentId() {}

    componentOptions() {}

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    render(initQueue, options, children) {
        let el = this.renderRuntime(options, children);

        const init = () => this.scriptRuntime(el, options);
        initQueue.push(init);

        return el;
    }

    renderRuntime(options, children) {}
    scriptRuntime(el, options) {}

/* =======================================
 * Builder Component Setting
 * ======================================= */
    component(options) {
        this.mountComponentStyle();

        let el = this.renderBuilder(options);

        return el;
    }

    renderBuilder(options) {}
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

        let $componentEl = this.createComponent(options, componentFactory);
        $el.append($componentEl);

        this.afterAddComponent(componentFactory, $el, $componentEl);
    }

    afterAddComponent(componentFactory, $el, $componentEl) {}

    createComponent(options, componentFactory) {
        let $componentEl = this.component(options);
        this.setOptions($componentEl, options);

        const dropConfig = this.componentDropConfig($componentEl);
        for(const config of dropConfig) {
            this.drop(config.element, config.allowedComponentIds, componentFactory);

            if(config.sortable) {
                this.sortable(config.element);
            }
        }

        const sortableConfig = this.componentSortableConfig($componentEl);
        for(const config of sortableConfig) {
            this.sortable(config.element);
        }

        return $componentEl;
    }

    componentDropConfig($componentEl) { return []; }

    componentSortableConfig($componentEl) { return []; }

    addComponentByType(componentFactory, type, $el) {
        componentFactory[type].addComponent($el, componentFactory);
    }

    drop($el, allowedTypes, componentFactory) {
        const COMPONENT_PANEL_ID_PREFIX = 'component-';

        let self = this;

        $el.droppable({
            greedy: true,
            hoverClass: "drop-hover",
            drop: function (event, ui) {
                const type = ui.draggable.data("type");

                // 좌측 패널의 컴포넌트만 Drop 허용
                if(!type.includes(COMPONENT_PANEL_ID_PREFIX)) {
                    return;
                }

                const componentType = type.replace(COMPONENT_PANEL_ID_PREFIX,'');
                if(allowedTypes && !allowedTypes.includes(componentType)) {
                    return;
                }

                self.addComponentByType(componentFactory, componentType, $el);
            },
        });
    }

    sortable($el) {
        $el.sortable({
            items: ".vb-item",
            helper: "clone",
            connectWith: ".vb-container",
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

/* =======================================
 * Option Panel Setting
 * ======================================= */
    initOptionPanel($el, id, componentFactory) {
        let options = this.getOptions($el);

        let $panel = $("#"+id);

        $panel.empty();

        this.optionPanel.init(this._componentId, []);

        this.optionPanelView($panel, options);

        this.optionPanelScript($el, options);

        this.optionPanelEvent($el, options, componentFactory);
    }

    optionPanelView($panel, options) {}
    optionPanelScript($el, options) {}
    optionPanelEvent($el, options, componentFactory) {}
}