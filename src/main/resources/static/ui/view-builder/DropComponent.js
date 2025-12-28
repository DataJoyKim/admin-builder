import { ComponentFactory } from './ComponentFactory.js';

export class DropComponent {
    constructor() {
        this.layoutId = 'layout';
        this.componentFactory = ComponentFactory.instanceMap();

        window.componentIdMap = ComponentFactory.getComponentIdMap();
    }

    dropLayout(canvasId) {
        const layout = this.componentFactory[this.layoutId];

        let $el = layout.component(this.layoutId, {});

        $("#"+canvasId).append($el);

        layout.dropComponent($el, this.componentFactory);
    }

    getComponentFactory() {
        return this.componentFactory;
    }
}