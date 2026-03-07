class ActionExecutor {
    static init() {
        window.App.doAction = this.createDoAction();
    }

    static createDoAction() {
        const code = `
            window.App.actions[actionName](...args);
        `;

        return new Function("actionName", "...args", code);
    }
}