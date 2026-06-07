class NewData extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'requestMessage', label:'요청메시지', type:'sheet', size:'col-12', defaultValue:''}
        ]
    }

    register(data) {
        const name = data.actionName;
        const argsName = data.argsName;
        const options = JSON.parse(data.contents);

        if (!name) {
            return;
        }

        const requestMessage = options.requestMessage;

        let code = ``;

        const messages = requestMessage.reduce((acc, item) => {
            if (!acc[item.messageId]) {
                acc[item.messageId] = [];
            }

            acc[item.messageId].push(item);

            return acc;
        }, {});

        for(const messageId in messages) {
            const msgObj = messages[messageId];

            let params = {};
            for(const msg of msgObj) {
                params[msg.columnName] = msg.value;
            }

            params = JSON.stringify(params);

            code += `$('div[dataProvider="${messageId}"]').trigger('newData',[${params}]);`;
        }

        super.registerAction(name,argsName,code);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.getHtml('requestMessage'));

        optionPanel.sheetScript('requestMessage', "90%", "400px",
            [
               {field:'messageId', label:'메시지ID', type:'text', width:150, hide:false, editable: true, align:'left', required:false},
               {field:'columnName', label:'컬럼', type:'text', width:150, hide:false, editable: true, align:'left', required:false},
               {field:'value', label:'값', type:'text', width:200, hide:false, editable: true, align:'left', required:false}
           ]);
    }
}