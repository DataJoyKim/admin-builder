class Actions {
    constructor(optionPanel, globalVariable) {
        this.globalVariable = globalVariable;
        this.optionPanel = optionPanel;
    }

    register(data) {}

    registerAction(actionName, argsParams, code) {
        window.VB.actions = window.VB.actions || {};

        if(argsParams) {
            const args = argsParams.split(",");
            window.VB.actions[actionName] = new Function(...args, code);
        }
        else {
            window.VB.actions[actionName] = new Function(code);
        }
    }

    actionOptions() {}

    initOptionPanel($panel, type) {
        $panel.empty();

        const optionInfo = this.actionOptions();

        this.optionPanel.init('action', optionInfo);

        this.optionPanelView($panel, this.optionPanel);

        let defaultValueObj = {};
        for(const info of optionInfo) {
            defaultValueObj[info.id] = info.defaultValue;
        }

        this.optionPanel.setOptionValue(defaultValueObj);
    }

    setOptions(options) {
        this.optionPanel.setOptionValue(options);
    }

    getOptions() {
        return this.optionPanel.getOptionValue();
    }

    optionPanelView($panel, optionPanel) {}
}