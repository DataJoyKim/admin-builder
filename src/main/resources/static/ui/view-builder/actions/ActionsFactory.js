class ActionsFactory {
    constructor(optionPanel, globalVariable) {
        this.optionPanel = optionPanel;
        this.globalVariable = globalVariable;
        this._instanceMap = this.instanceMap();
    }

    instanceMap() {
        return {
            'WORKFLOW': new Workflow(this.optionPanel, this.globalVariable),
            'SCRIPT': new Script(this.optionPanel, this.globalVariable)
        };
    }

    instance(type) {
        return this._instanceMap[type];
    }
}