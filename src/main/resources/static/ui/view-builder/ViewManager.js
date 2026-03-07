class ViewManager {
    static init() {
        window.App = window.App || {};
        window.App.ActionExecutor = ActionExecutor;
        window.App.GlobalVariable = GlobalVariable;
        window.App.ActionsFactory = ActionsFactory;
        window.App.ComponentFactory = ComponentFactory;
    }
}