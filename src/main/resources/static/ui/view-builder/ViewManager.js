class ViewManager {
    static init(layoutId) {
        window.VB = window.VB || {};
        window.VB.utils = {
            grid: App.grid,
            modalPopup: App.modalPopup,
            popup: App.popup,
            httpClient: App.httpClient,
            workflowClient: App.workflowClient
        };
        window.VB.objectCode = App.objectCode;
        window.VB.actionExecutor = new ActionExecutor();
        window.VB.globalVariable = new GlobalVariable();
        window.VB.actionsFactory = new ActionsFactory(VB.globalVariable);
        window.VB.componentFactory = new ComponentFactory(new OptionPanel(), VB.utils);
        window.VB.viewDataLoader = new ViewDataLoader(layoutId)
        window.VB.dropComponent = new DropComponent(layoutId, VB.componentFactory);
    }
}