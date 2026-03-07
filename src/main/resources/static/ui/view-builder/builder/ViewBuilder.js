class ViewBuilder {

    constructor() {
        this.canvasId = 'canvas';
        this.dropComponent = new DropComponent();
        this.viewDataLoader = new ViewDataLoader('layout');
    }

    init() {
        $(".component-item").draggable({ helper: "clone" });
        this.dropComponent.dropLayout(this.canvasId);
    }

    preview() {
        const render = new Render();
        render.init(this.canvasId, this.getViewData());
    }

    loadEditor(data) {
        const render = new RenderBuilder();
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