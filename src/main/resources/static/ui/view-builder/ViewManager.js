class ViewManager {
    static init() {
        window.VB = window.VB || {};
        window.VB.utils = {
            grid: App.grid,
            modalPopup: App.modalPopup,
            popup: App.popup,
            httpClient: App.httpClient
        };
        window.VB.objectCode = App.objectCode;
        window.VB.ActionExecutor = ActionExecutor;
        window.VB.GlobalVariable = GlobalVariable;
        window.VB.ActionsFactory = ActionsFactory;
        window.VB.ComponentFactory = ComponentFactory;
        window.VB.dropComponent = new DropComponent();
    }
}