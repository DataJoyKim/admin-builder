class View {
    constructor() {
    }

    init() {
        VB.utils.httpClient.get(`/pages/${VB.objectCode}/definition`,{},
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

                VB.GlobalVariable.init();
                VB.ActionExecutor.init();

                const render = new Render();
                render.init('canvas', view, response.viewActions);
            },
            function(error){
                alert("화면을 불러오는데 실패하였습니다.");
            }
        );
    }
}