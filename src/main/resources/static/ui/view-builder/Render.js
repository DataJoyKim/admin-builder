import { ComponentFactory } from './ComponentFactory.js';

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
            const componentEl = ComponentFactory.instance(data.type);

            let children = null;
            if(data.children) {
                children = this.component(data.children);

                children = this.expendChildren(children, data);
            }

            if(componentEl != null) {
                const viewObject = componentEl.render(this.initQueue, data, children);
                frag.append(viewObject);
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

    expendChildren(children, data) {
        if(data.type == 'card') {
            const header = [];
            const body = [];

            for (const child of data.children ?? []) {
                if (child.type === 'card-body') body.push(child);
                else header.push(child);
            }

            return {
                  cardHeader: this.component(header),
                  cardChildren: this.component(body)
              }
        }
        else {
            return children;
        }
    }
}