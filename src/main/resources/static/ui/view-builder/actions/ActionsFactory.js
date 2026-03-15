class ActionsFactory {
    constructor(globalVariable) {
        this.globalVariable = globalVariable;
    }

    instance(type) {
        switch (type) {
            case 'WORKFLOW': return new Workflow(this.globalVariable);
            case 'SCRIPT': return new Script(this.globalVariable);
            default: return null;
        }
    }
}