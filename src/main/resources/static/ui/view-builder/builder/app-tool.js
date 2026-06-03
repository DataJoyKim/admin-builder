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
        window.VB.module = new ModuleLoader(VB.version, VB.configs, true);

        VB.module.load(function(){
            ViewManager.init('layout');

            window.VB.builder = new ViewBuilder('canvas', VB.actionsFactory, VB.componentFactory, VB.dropComponent, VB.viewDataLoader);
        });
    });
});