import { Render } from '/ui/view-builder/Render.js';
import { RenderEditor } from '/ui/view-builder/RenderEditor.js';
import { ComponentFactory } from '/ui/view-builder/ComponentFactory.js';
import { DropComponent } from '/ui/view-builder/DropComponent.js';

// 컴포넌트 랜더링
$(function () {
    $(".component-item").draggable({ helper: "clone" });

    window.dropComponent = new DropComponent();
    window.dropComponent.dropLayout('canvas');
});

// 미리보기 랜더링
window.preview = function () {
    const render = new Render();

    render.init('canvas', getViewData());
};

// 미리보기 랜더링
window.loadEditor = function (data) {
    const render = new RenderEditor();
    console.log('data',data);

    render.init('canvas', data);
};

// 옵션패널
$(document).on("click", ".vb-item", function(e) {
    e.stopPropagation();

    const $el = $(this);
    const type = $el.data("type");

    const componentEl = ComponentFactory.instance(type);

    componentEl.optionPanel($el, "options", window.dropComponent.getComponentFactory());
});

// 컴포넌트 삭제
$(document).on("click", ".component-delete-btn", function(e) {
    e.stopPropagation();

    $(this).closest(".component").remove();
});

// 컴포넌트 선택 영역
$(document).on("click", ".component", function(e) {
    e.stopPropagation();

    // 이전 선택 제거
    $(".component").removeClass("selected");
    $(".component-delete-btn").hide();

    // 현재 요소 선택
    $(this).addClass("selected");
    $(this).children(".component-delete-btn").show();
});