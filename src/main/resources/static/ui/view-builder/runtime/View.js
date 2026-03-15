class View {
    constructor(
        canvasId,
        objectCode,
        globalVariable,
        actionExecutor,
        actionsFactory,
        componentFactory,
        utils
        ) {
        this.canvasId = canvasId;
        this.objectCode = objectCode;
        this.globalVariable = globalVariable;
        this.actionExecutor = actionExecutor;
        this.actionsFactory = actionsFactory;
        this.componentFactory = componentFactory;
        this.utils = utils;
    }

    init() {
        this.utils.httpClient.get(`/pages/${this.objectCode}/definition`,{},
            function(response){
                let view;
                try {
                    view = JSON.parse(response.viewObjectContent.content);
                }
                catch(e) {
                    console.error("JSON 파싱 실패:", e.message)
                    alert("화면을 파싱하는데 실패하였습니다.");
                    return;
                }

                this.globalVariable.init();
                this.actionExecutor.init();

                const render = new Render(this.actionsFactory, this.componentFactory);
                render.init(this.canvasId, view, response.viewActions);
            },
            function(error){
                alert("화면을 불러오는데 실패하였습니다.");
            }
        );
    }
}