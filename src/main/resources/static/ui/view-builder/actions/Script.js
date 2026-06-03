class Script extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'script', label:'script', type:'code', size:'col-12', defaultValue:''}
        ]
    }

    register(data) {
        const name = data.actionName;
        const argsName = data.argsName;
        const script = data.script;

        if (!name) {
            return;
        }

        super.registerAction(name,argsName,script);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.getHtml('script'));

        optionPanel.codeEditorScript('script', "100%", "800px");
    }
}