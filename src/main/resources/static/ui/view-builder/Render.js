import { RenderComponentFactory } from './RenderComponentFactory.js';

export class Render {
    constructor() {
        this.initQueue = [];
    }

    init(id, data) {
        this.initQueue = [];

        this.render(id, data);

        for (let initQ of this.initQueue) {
            initQ();
        }
    }

    component(viewData) {
        if (!viewData) {
            return $();
        }

        const frag = $(document.createDocumentFragment());

        for (let data of viewData) {
            let viewObject;
            let children;

            const componentEl = RenderComponentFactory.create(data, this);

            if(componentEl.instance != null) {
                frag.append(componentEl.instance.render(this.initQueue, data, componentEl.children));
            }
        }

        return frag;
    }

    render(id, data) {
        const frag = $(document.createDocumentFragment());

        const contentWrapper = $('<div>').addClass('content-wrapper');
        contentWrapper.append(this.component(data));
        frag.append(contentWrapper);

        $("#"+id).empty().append(frag);
    }
}