class ActionExecutor {
    init() {
        window.VB.doAction = this.createDoAction();
    }

    createDoAction() {
        const code = `
            window.VB.actions[actionName](...args);
        `;

        return new Function("actionName", "...args", code);
    }
}