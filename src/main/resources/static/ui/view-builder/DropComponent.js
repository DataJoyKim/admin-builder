import { ComponentFactory } from './ComponentFactory.js';

export class DropComponent {
    constructor() {
        this.layoutId = 'layout';
        this.componentFactory = ComponentFactory.instanceMap();

        window.componentIdMap = ComponentFactory.getComponentIdMap();
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