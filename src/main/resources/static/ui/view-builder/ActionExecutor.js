class ActionExecutor {
    static init() {
        window.VB.doAction = this.createDoAction();
    }

    static createDoAction() {
        const code = `
            window.VB.actions[actionName](...args);
        `;

        return new Function("actionName", "...args", code);
    }
}