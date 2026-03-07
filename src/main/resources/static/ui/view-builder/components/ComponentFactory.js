class ComponentFactory {
    static instanceMap() {
        return {
                "button": new Button(),
                "form": new Form(),
                "grid": new jsGrid(),
                "row": new Row(),
                "card": new Card(),
                "card-body": new CardBody(),
                "input": new Input(),
                "layout": new Layout(),
                "custom-html": new CustomHtml()
            };
    }

    static instance(type) {
        const instanceMap = ComponentFactory.instanceMap();
        return instanceMap[type];
    }

    static getComponentIdMap() {
        let componentIdMap = {};

        const instanceMap = ComponentFactory.instanceMap();
        for(let key in instanceMap) {
            componentIdMap[key] = 0;
        }

        return componentIdMap;
    }
}