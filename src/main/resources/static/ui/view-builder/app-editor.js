import { ComponentFactory } from '/ui/view-builder/ComponentFactory.js';
import { ViewAppEditor } from '/ui/view-builder/ViewAppEditor.js';

// 컴포넌트 랜더링
$(function () {
    window.App.vb = new ViewAppEditor();
    window.App.vb.init();
});

// 옵션패널
$(document).on("click", ".vb-item", function(e) {
    e.stopPropagation();

    const $el = $(this);
    const type = $el.data("type");

    const componentEl = ComponentFactory.instance(type);

    componentEl.optionPanel($el, "options", App.vb.dropComponent.getComponentFactory());
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