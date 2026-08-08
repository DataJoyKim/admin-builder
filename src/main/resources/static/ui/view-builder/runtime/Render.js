class Render {
    constructor(actionsFactory, componentFactory) {
        this.initQueue = [];
        this.actionsFactory = actionsFactory;
        this.componentFactory = componentFactory;
    }

    init(id, viewObject, viewData, actionsData) {
        this.initQueue = [];

        this.registerActions(actionsData);

        this.render(id, viewObject, viewData);

        for (let initQ of this.initQueue) {
            initQ();
        }
    }

    registerActions(data) {
        if(!data) {
            return;
        }

        for(const actionData of data) {
            const action = this.actionsFactory.instance(actionData.type);

            action.register(actionData);
        }
    }

    render(id, viewObject, data) {
        // Toolbar Rendering
        if(viewObject.useToolbar) {
            $(".toolbar-section").append(this.toolbar());
        }

        // View Object Content Rendering
        const contentWrapper = $('<div>')
            .addClass('wrapper')
            .attr('id', id);

        contentWrapper.append(this.component(data));

        $(".content-section").append(contentWrapper);
    }

    component(viewData) {
        if (!viewData) {
            return $();
        }

        const frag = $(document.createDocumentFragment());

        for (let data of viewData) {
            const componentEl = this.componentFactory.instance(data.type);

            let children = null;
            if(data.children) {
                children = this.getChildren(children, data);
            }

            if(componentEl != null) {
                const viewObject = componentEl.render(this.initQueue, data, children);
                frag.append(viewObject);
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

    toolbar() {
        return $(`
            <nav class="navbar navbar-expand  navbar-light toolbar">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#" role="button" data-toggle="tooltip" title="즐겨찾기" onClick="">
                            <i class="far fa-star"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" role="button" data-toggle="tooltip" title="가이드" onClick="">
                            <i class="fas fa-info"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" role="button" data-toggle="tooltip" title="공유하기" onClick="">
                            <i class="fas fa-share-alt"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" role="button" data-toggle="tooltip" title="새로고침" onClick="refresh()">
                            <i class="fas fa-redo-alt"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        `);
    }
}