class ViewBuilder {

    constructor(
        canvasId,
        actionsFactory,
        componentFactory,
        dropComponent,
        viewDataLoader,
        ) {
        this.canvasId = canvasId;
        this.actionsFactory = actionsFactory;
        this.componentFactory = componentFactory;
        this.dropComponent = dropComponent;
        this.viewDataLoader = viewDataLoader;
        this.initPreviewState();

        this.actionPanel = {
            setOptionPanel: function($el, type) {
                const actions = actionsFactory.instance(type);
                actions.initOptionPanel($el, type);
            },
            setOptions: function(type, options) {
                const actions = actionsFactory.instance(type);
                actions.setOptions(options);
            },
            getOptions: function(type) {
                const actions = actionsFactory.instance(type);
                return actions.getOptions();
            }
        }
    }

    init() {
        // 팔레트 드래그 시작(오브젝트 코드 입력 여부 체크 포함)은
        // ComponentPanel의 각 카테고리 리스트(Sortable)에서 자체적으로 처리한다.
        this.dropComponent.dropLayout(this.canvasId);
    }

    preview(objectCode) {
        this.previewState.preview = true;
        this.previewState.data = this.getViewData();

        const view = new View('canvas', VB.globalVariable, VB.actionExecutor, this.actionsFactory, this.componentFactory, VB.utils);
        view.init(objectCode, this.previewState.data);
    }

    cancelPreview() {
        const render = new RenderBuilder(this.componentFactory);
        render.init(this.canvasId, this.previewState.data);

        this.initPreviewState();
    }

    loadEditor(data) {
        this.initPreviewState();

        const render = new RenderBuilder(this.componentFactory);
        if (data?.length) {
            render.init(this.canvasId, data);
        }
        else {
            this.initCanvas();
        }
    }

    setOptionPanel($el, type) {
        const componentEl = VB.componentFactory.instance(type);

        componentEl.initOptionPanel($el, "options", VB.componentFactory.instanceMap());
    }

    clearOptionPanel($el) {
        $el.empty();
    }

    initPreviewState() {
        this.previewState = {};
        this.previewState.preview = false;
        this.previewState.data = null;
    }

    initCanvas() {
        $("#"+this.canvasId).html('');
        this.dropComponent.dropLayout(this.canvasId);
    }

    getViewData() {
        return this.viewDataLoader.getData();
    }
}