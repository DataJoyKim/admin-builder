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

        const containerConfig = this.componentContainerConfig($componentEl);
        for(const config of containerConfig) {
            this.container(config.element, config.allowedComponentIds, componentFactory);
        }

        return $componentEl;
    }

    // 컴포넌트가 자식(레이아웃 내부 요소, 카드 헤더/바디 등)을 받을 수 있는 영역을
    // 하나 이상 반환한다. allowedComponentIds가 없으면 모든 타입을 허용한다.
    componentContainerConfig($componentEl) { return []; }

    addComponentByType(componentFactory, type, $el) {
        componentFactory[type].addComponent($el, componentFactory);
    }

    // 팔레트에서 새 컴포넌트를 끌어와 놓는 것과, 캔버스 안에서 기존 컴포넌트를
    // 재정렬/이동하는 것을 SortableJS의 group(pull/put) 메커니즘 하나로 통합 처리한다.
    // (예전에는 같은 엘리먼트에 jQuery UI droppable + sortable을 각각 따로 걸었는데,
    // 그 조합이 중첩된 컨테이너 구조에서 불안정하게 동작하는 원인이었다.)
    container($el, allowedComponentIds, componentFactory) {
        const COMPONENT_PANEL_ID_PREFIX = 'component-';
        const self = this;

        Sortable.create($el[0], {
            group: {
                name: 'vb-components',
                pull: true,
                put: function(to, from, dragEl) {
                    const type = dragEl.dataset.type;
                    if(!type) return false;

                    const componentType = type.startsWith(COMPONENT_PANEL_ID_PREFIX)
                        ? type.replace(COMPONENT_PANEL_ID_PREFIX, '')
                        : type;

                    if(allowedComponentIds && !allowedComponentIds.includes(componentType)) {
                        return false;
                    }

                    return true;
                }
            },
            filter: '.component-delete-btn',
            preventOnFilter: true,
            animation: 150,
            ghostClass: 'drop-hover',
            onAdd: function(evt) {
                const item = evt.item;
                const type = item.dataset.type || '';

                // 팔레트에서 그대로 복제되어 들어온 노드인지 확인(아직 실제 컴포넌트로
                // 생성되지 않은 상태) - 맞다면 복제 노드는 지우고 실제 컴포넌트를 새로 만든다.
                if(type.startsWith(COMPONENT_PANEL_ID_PREFIX)) {
                    const componentType = type.replace(COMPONENT_PANEL_ID_PREFIX, '');
                    item.remove();
                    self.addComponentByType(componentFactory, componentType, $el);
                }
                // 기존 캔버스 컴포넌트를 다른 컨테이너로 옮긴 경우엔 DOM 이동만 일어나고
                // .data('options')는 그대로 유지되므로 별도 처리가 필요 없다.
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