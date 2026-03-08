class ModuleLoader {
    constructor(edit) {
        this.commonModules = [
            `/ActionExecutor.js`,
            `/GlobalVariable.js`,
            `/ViewManager.js`
        ];

        this.beforeModules = [
            `${VB.configs.paths.actions}/Actions.js`,
            `${VB.configs.paths.components}/ViewObject.js`,
            `${VB.configs.paths.data}/ViewDataLoader.js`
        ];

        this.actionsModules = [
            `${VB.configs.paths.actions}/Script.js`,
            `${VB.configs.paths.actions}/Workflow.js`
        ];

        this.componentsModules = [
             `${VB.configs.paths.components}/Button.js`,
             `${VB.configs.paths.components}/Card.js`,
             `${VB.configs.paths.components}/CardBody.js`,
             `${VB.configs.paths.components}/CustomHtml.js`,
             `${VB.configs.paths.components}/Form.js`,
             `${VB.configs.paths.components}/VbGrid.js`,
             `${VB.configs.paths.components}/Input.js`,
             `${VB.configs.paths.components}/Layout.js`,
             `${VB.configs.paths.components}/Row.js`
        ];

        this.afterModules = [
            `${VB.configs.paths.actions}/ActionsFactory.js`,
            `${VB.configs.paths.components}/ComponentFactory.js`,
            `${VB.configs.paths.runtime}/Render.js`,
            `${VB.configs.paths.runtime}/View.js`
        ];

        if(edit) {
            const builderModule = [
                `${VB.configs.paths.builder}/DropComponent.js`,
                `${VB.configs.paths.builder}/OptionPanel.js`,
                `${VB.configs.paths.builder}/RenderBuilder.js`,
                `${VB.configs.paths.builder}/ViewBuilder.js`
            ];

            this.afterModules.push(...builderModule);
        }
    }

    load(_callback) {
        this.loadSequential(this.createModuleUrl(this.commonModules))
            .then(() => this.loadSequential(this.createModuleUrl(this.beforeModules)))
            .then(() => this.loadParallel(this.createModuleUrl(this.actionsModules)))
            .then(() => this.loadParallel(this.createModuleUrl(this.componentsModules)))
            .then(() => this.loadSequential(this.createModuleUrl(this.afterModules)))
            .done(_callback);
    }

    createModuleUrl(modulePath) {
        let modules = new Array();

        for(let module of modulePath) {
            modules.push(`${VB.configs.paths.module}${module}?v=${VB.version}`);
        }

        return modules;
    }

    loadSequential(urls) {
        let dfd = $.Deferred().resolve();

        urls.forEach(url => {
            dfd = dfd.then(() => $.getScript(url));
        });

        return dfd;
    }

    loadParallel(urls) {
        return $.when.apply($, urls.map(url => $.getScript(url)));
    }
}