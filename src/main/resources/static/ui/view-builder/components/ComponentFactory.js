class ComponentFactory {
    constructor(optionPanel, utils) {
        this.optionPanel = optionPanel;
        this.utils = utils;
    }

    instanceMap() {
        return {
                "button": new Button(this.optionPanel, this.utils),
                "form": new Form(this.optionPanel),
                "grid": new VbGrid(this.optionPanel, this.utils.grid),
                "row": new Row(this.optionPanel),
                "card": new Card(this.optionPanel),
                "card-body": new CardBody(this.optionPanel),
                "input": new Input(this.optionPanel),
                "layout": new Layout(this.optionPanel),
                "custom-html": new CustomHtml(this.optionPanel)
            };
    }

    instance(type) {
        const instanceMap = this.instanceMap();
        return instanceMap[type];
    }

    getComponentIdMap() {
        let componentIdMap = {};

        const instanceMap = this.instanceMap();
        for(let key in instanceMap) {
            componentIdMap[key] = 0;
        }

        return componentIdMap;
    }
}