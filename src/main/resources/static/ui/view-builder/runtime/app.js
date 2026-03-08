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
        window.VB.module = new ModuleLoader(false);

        VB.module.load(function(){
            ViewManager.init();

            const view = new View();
            view.init();
        });
    });
});