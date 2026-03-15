class DropComponent {
    constructor(layoutId, componentFactory) {
        this.layoutId = layoutId;
        this.componentFactoryMap = componentFactory.instanceMap();

        window.componentIdMap = componentFactory.getComponentIdMap();
    }

    dropLayout(canvasId) {
        const layout = this.componentFactoryMap[this.layoutId];

        let componentEl = layout.createComponent(this.layoutId, {}, this.componentFactoryMap);

        $("#"+canvasId).append(componentEl);

        layout.addComponentByType(this.componentFactoryMap,'row', componentEl);
    }
}