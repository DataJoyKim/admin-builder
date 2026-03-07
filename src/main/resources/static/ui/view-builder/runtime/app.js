$(function () {
    window.App.version = '1.0.0';
    console.log(`version ${App.version}`);

    window.App.configs = {
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
        $.getScript(`${App.configs.paths.module}/ModuleLoader.js`)
    )
    .done(function() {
        window.App.module = new ModuleLoader(false);

        App.module.load(function(){
            ViewManager.init();

            const view = new View();
            view.init();
        });
    });
});