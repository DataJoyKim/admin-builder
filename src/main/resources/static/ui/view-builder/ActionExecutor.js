export class ActionExecutor {
    static init() {
        window['doAction'] = this.createDoAction();
    }

    static createDoAction() {
        const code = `
            window.vb.actions[actionName](...args);
        `;

        return new Function("actionName", "...args", code);
    }
}