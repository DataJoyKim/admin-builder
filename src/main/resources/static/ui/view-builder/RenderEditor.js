import { ComponentFactory } from './ComponentFactory.js';

export class RenderEditor {
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

    render(id, data) {
        const contentWrapper = $('<div>')
            .addClass('wrapper')
            .attr('id', id);

        const layout = ComponentFactory.instance('layout');
        const layoutEl = layout.component('layout', {});

        layoutEl.append(this.component(data));
        contentWrapper.append(layoutEl);

        $("#"+id).replaceWith(contentWrapper);
    }

    component(viewData) {
        if (!viewData) {
            return $();
        }

        const frag = $(document.createDocumentFragment());

        for (let data of viewData) {
            const comp = ComponentFactory.instance(data.type);

            const componentEl = comp.createComponent(data.id, data, ComponentFactory.instanceMap());

            let children = null;
            if(data.children) {
                children = this.getChildren(children, data);
            }

            if(componentEl != null) {
                if(children) {
                    if(data.type == 'card') {
                        componentEl.find(".card-tools").append(children.cardHeader);
                        componentEl.find(".card").append(children.cardChildren);
                    }
                    else {
                        componentEl.append(children);
                    }
                }

                frag.append(componentEl);
            }
        }

        return frag;
    }

    getChildren(children, data) {
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
            return this.component(data.children);
        }
    }
}