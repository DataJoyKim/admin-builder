class Script extends Actions {
    constructor(globalVariable) {
        super(globalVariable);
        this.globalVariable = globalVariable;
    }

    register(data) {
        const name = data.actionName;
        const script = data.script;
        const argsName = data.argsName;

        if (!name) {
            return;
        }

        super.registerAction(name,argsName,script);
    }
}