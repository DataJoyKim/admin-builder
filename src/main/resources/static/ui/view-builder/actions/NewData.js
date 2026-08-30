class NewData extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'messageId', label:'메시지ID', type:'text', size:'col-6', defaultValue:''},
            {id:'messageColumns', label:'메시지컬럼 설정', type:'sheet', size:'col-12', defaultValue:''}
        ]
    }

    register(data) {
        const name = data.actionName;
        const argsName = data.argsName;
        const options = JSON.parse(data.contents);

        if (!name) {
            return;
        }

        const messageId = options.messageId;
        const messageColumns = options.messageColumns;

        let code = ``;

        let params = {};
        if(messageColumns) {
            for(const col of messageColumns) {
                params[col.columnName] = col.value;
            }
        }

        params = JSON.stringify(params);

        code += `$('div[dataProvider="${messageId}"]').trigger('newData',[${params}]);`;

        super.registerAction(name,argsName,code);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.sectionTitle('기본'));
        $panel.append(optionPanel.getHtml('messageId'));

        $panel.append(optionPanel.sectionTitle('메시지 컬럼'));
        $panel.append(optionPanel.getHtml('messageColumns'));

        optionPanel.sheetScript('messageColumns', "100%", "400px",
            [
               {field:'columnName', label:'컬럼', type:'text', width:200, hide:false, editable: true, align:'left', required:false},
               {field:'value', label:'값', type:'text', width:230, hide:false, editable: true, align:'left', required:false}
           ]);
    }
}