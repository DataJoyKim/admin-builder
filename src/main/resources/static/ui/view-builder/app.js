import { Render } from '/ui/view-builder/Render.js';

$(function () {
    App.httpClient.get(`/pages/${App.objectCode}/definition`,{},
        function(response){
            let view;
            try {
                view = JSON.parse(response.content);
            }
            catch(e) {
                console.error("JSON 파싱 실패:", e.message)
                alert("화면을 파싱하는데 실패하였습니다.");
                return;
            }

            const render = new Render();
            render.init('canvas', view);
        },
        function(error){
            alert("화면을 불러오는데 실패하였습니다.");
        }
    );
});