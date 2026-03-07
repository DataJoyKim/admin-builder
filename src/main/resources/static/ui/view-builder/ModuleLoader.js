class ModuleLoader {
    constructor(edit) {
        this.commonModules = [
            `/ActionExecutor.js`,
            `/GlobalVariable.js`,
            `/ViewManager.js`
        ];

        this.beforeModules = [
            `${App.configs.paths.actions}/Actions.js`,
            `${App.configs.paths.components}/ViewObject.js`,
            `${App.configs.paths.data}/ViewDataLoader.js`
        ];

        this.actionsModules = [
            `${App.configs.paths.actions}/Script.js`,
            `${App.configs.paths.actions}/Workflow.js`
        ];

        this.componentsModules = [
             `${App.configs.paths.components}/Button.js`,
             `${App.configs.paths.components}/Card.js`,
             `${App.configs.paths.components}/CardBody.js`,
             `${App.configs.paths.components}/CustomHtml.js`,
             `${App.configs.paths.components}/Form.js`,
             `${App.configs.paths.components}/jsGrid.js`,
             `${App.configs.paths.components}/Input.js`,
             `${App.configs.paths.components}/Layout.js`,
             `${App.configs.paths.components}/Row.js`
        ];

        this.afterModules = [
            `${App.configs.paths.actions}/ActionsFactory.js`,
            `${App.configs.paths.components}/ComponentFactory.js`,
            `${App.configs.paths.runtime}/Render.js`,
            `${App.configs.paths.runtime}/View.js`
        ];

        if(edit) {
            const builderModule = [
                `${App.configs.paths.builder}/DropComponent.js`,
                `${App.configs.paths.builder}/OptionPanel.js`,
                `${App.configs.paths.builder}/RenderBuilder.js`,
                `${App.configs.paths.builder}/ViewBuilder.js`
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
            modules.push(`${App.configs.paths.module}${module}?v=${App.version}`);
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