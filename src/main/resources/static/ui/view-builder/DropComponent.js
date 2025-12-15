import { ComponentFactory } from './ComponentFactory.js';

export class DropComponent {
    constructor() {
        this.componentFactory = ComponentFactory.instanceMap();

        window.componentIdMap = {
            "row":0,
            "button":0,
            "form":0,
            "card":0,
            "grid":0,
            "input":0,
            "card-body":0
        }
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