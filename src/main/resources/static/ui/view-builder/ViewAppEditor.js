import { Render } from '/ui/view-builder/Render.js';
import { RenderEditor } from '/ui/view-builder/RenderEditor.js';
import { DropComponent } from '/ui/view-builder/DropComponent.js';
import { ViewDataLoader } from '/ui/view-builder/ViewDataLoader.js';

export class ViewAppEditor {

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
        const render = new RenderEditor();
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