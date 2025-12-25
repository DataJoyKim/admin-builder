import { ComponentFactory } from './ComponentFactory.js';
import { ActionsFactory } from './ActionsFactory.js';
import { GlobalVariable } from './GlobalVariable.js';

export class Render {
    constructor() {
        this.initQueue = [];
    }

    init(id, viewData, actionsData) {
        this.initQueue = [];

        this.registerGlobalVariable();

        this.registerActions(actionsData);

        this.render(id, viewData);

        for (let initQ of this.initQueue) {
            initQ();
        }
    }

    registerGlobalVariable() {
        GlobalVariable.init();
    }

    registerActions(data) {
        if(!data) {
            return;
        }

        for(const actionData of data) {
            const action = ActionsFactory.instance(actionData.type);

            action.register(actionData);
        }
    }

    render(id, data) {
        const contentWrapper = $('<div>')
            .addClass('wrapper')
            .attr('id', id);

        contentWrapper.append(this.component(data));

        $("#"+id).replaceWith(contentWrapper);
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