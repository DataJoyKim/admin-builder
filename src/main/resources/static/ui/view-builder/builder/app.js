// 컴포넌트 랜더링
$(function () {
    window.VB = window.VB || {};
    window.VB.version = '1.0.0';
    console.log(`version ${VB.version}`);

    window.VB.configs = {
        paths:{
            module:'/ui/view-builder',
            actions:'/actions',
            builder:'/builder',
            components:'/components',
            css:'/css',
            data:'/data',
            img:'/img',
            runtime:'/runtime'
        }
    };

    $.when(
        $.getScript(`${VB.configs.paths.module}/ModuleLoader.js`)
    )
    .done(function() {
        window.VB.module = new ModuleLoader(true);

        VB.module.load(function(){
            ViewManager.init();

            window.VB.builder = new ViewBuilder();
            window.VB.builder.init();
        });
    });
});

// 옵션패널
$(document).on("click", ".vb-item", function(e) {
    e.stopPropagation();

    const $el = $(this);
    const type = $el.data("type");

    const componentEl = VB.ComponentFactory.instance(type);

    componentEl.optionPanel($el, "options", VB.dropComponent.getComponentFactory());
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