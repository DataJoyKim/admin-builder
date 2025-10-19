class Form {
    constructor() {}

    setData(id, data) {
        const formDiv = document.getElementById(id);
        if(!formDiv) {
            return;
        }

        // 모든 하위 요소 선택
        const allChildren = formDiv.querySelectorAll("*");

        // id가 있는 요소만 필터링하고 출력
        allChildren.forEach(child => {
          if (child.id) {
            $("#"+child.id).val(data[child.id]);
          }
        });
    }
}

window.App = window.App || {};
window.App.form = new Form();