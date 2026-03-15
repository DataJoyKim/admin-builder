class ViewBuilder {

    constructor(canvasId, actionsFactory, componentFactory, dropComponent, viewDataLoader) {
        this.canvasId = canvasId;
        this.actionsFactory = actionsFactory;
        this.componentFactory = componentFactory;
        this.dropComponent = dropComponent;
        this.viewDataLoader = viewDataLoader;
    }

    init() {
        $(".component-item").draggable({ helper: "clone" });
        this.dropComponent.dropLayout(this.canvasId);
    }

    preview() {
        const render = new Render(this.actionsFactory, this.componentFactory);
        render.init(this.canvasId, this.getViewData());
    }

    loadEditor(data) {
        const render = new RenderBuilder(this.componentFactory);
        if (data?.length) {
            render.init(this.canvasId, data);
        }
        else {
            this.initCanvas();
        }
    }

    initCanvas() {
        $("#"+this.canvasId).html('');
        this.dropComponent.dropLayout(this.canvasId);
    }

    getViewData() {
        return this.viewDataLoader.getData();
    }
}