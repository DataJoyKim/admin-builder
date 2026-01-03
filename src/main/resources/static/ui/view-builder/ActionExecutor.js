export class ActionExecutor {
    static init() {
        window['doAction'] = this.createDoAction();
    }

    static createDoAction() {
        const code = `
            window.vb.actions[actionName]();
        `;

        return new Function("actionName", code);
    }
}