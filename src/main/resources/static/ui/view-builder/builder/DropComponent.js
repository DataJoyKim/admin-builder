class DropComponent {
    constructor() {
        this.layoutId = 'layout';
        this.componentFactory = App.ComponentFactory.instanceMap();

        window.componentIdMap = App.ComponentFactory.getComponentIdMap();
    }

    dropLayout(canvasId) {
        const layout = this.componentFactory[this.layoutId];

        let componentEl = layout.createComponent(this.layoutId, {}, this.componentFactory);

        $("#"+canvasId).append(componentEl);

        layout.addComponentByType(this.componentFactory,'row', componentEl);
    }

    getComponentFactory() {
        return this.componentFactory;
    }
}