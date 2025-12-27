import { Render } from '/ui/view-builder/Render.js';
import { RenderEditor } from '/ui/view-builder/RenderEditor.js';
import { DropComponent } from '/ui/view-builder/DropComponent.js';
import { ViewDataLoader } from '/ui/view-builder/ViewDataLoader.js';

export class ViewAppEditor {

    constructor() {
        this.dropComponent = new DropComponent();
        this.viewDataLoader = new ViewDataLoader('layout');
    }

    init() {
        $(".component-item").draggable({ helper: "clone" });
        this.dropComponent.dropLayout('canvas');
    }

    preview() {
        const render = new Render();
        render.init('canvas', this.getViewData());
    }

    loadEditor(data) {
        const render = new RenderEditor();
        if (data?.length) {
            render.init('canvas', data);
        } else {
            this.dropComponent.dropLayout('canvas');
        }
    }

    getViewData() {
        return this.viewDataLoader.getData();
    }
}