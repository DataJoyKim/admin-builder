import { Render } from '/ui/view-builder/Render.js';
import { ComponentFactory } from '/ui/view-builder/ComponentFactory.js';
import { DropComponent } from '/ui/view-builder/DropComponent.js';


$(function () {
    $(".component-item").draggable({ helper: "clone" });

    window.dropComponent = new DropComponent();
    window.dropComponent.dropLayout($(".vb-layout"));
});

window.preview = function () {
    const render = new Render();

    render.init('canvas', getViewData());
};

$(document).on("click", ".vb-item", function(e) {
    e.stopPropagation();

    const $el = $(this);
    const type = $el.data("type");

    const componentEl = ComponentFactory.instance(type);

    componentEl.optionPanel($el, "options", window.dropComponent.getComponentFactory());
});

$(document).on("click", ".component-delete-btn", function(e) {
    e.stopPropagation();

    $(this).closest(".component").remove();
});

$(document).on("click", ".component", function(e) {
    e.stopPropagation();

    // 이전 선택 제거
    $(".component").removeClass("selected");
    $(".component-delete-btn").hide();

    // 현재 요소 선택
    $(this).addClass("selected");
    $(this).children(".component-delete-btn").show();
});