import { ComponentFactory } from './ComponentFactory.js';

export class DropComponent {
    constructor() {
        this.componentFactory = ComponentFactory.instanceMap();

        window.componentIdMap = ComponentFactory.getComponentIdMap();
    }

    dropLayout(canvasId) {
        const layout = this.componentFactory['layout'];

        let $el = layout.component('layout', {});

        $("#"+canvasId).append($el);

        layout.dropComponent($el, this.componentFactory);
    }

    getComponentFactory() {
        return this.componentFactory;
    }
}