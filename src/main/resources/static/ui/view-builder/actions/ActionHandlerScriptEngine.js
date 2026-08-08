class ActionHandlerScriptEngine  {
    constructor() {
        this.HANDLER_TYPE = [
            {code:'ACTION', name:'액션'},
            {code:'DATA_BINDING', name:'데이터바인딩'},
            {code:'ALERT', name:'얼럿'},
        ]
    }

    createScript(resultHandler) {
        let code = '';

        resultHandler.sort((a, b) => Number(a.orderNum) - Number(b.orderNum));

        for(const handler of resultHandler) {
            const content = (handler.handlerContent) ? JSON.parse(handler.handlerContent) : null;

            if(handler.handlerType === 'ALERT') {
                const message = (content) ? content.message : '';
                const messageVariable = '_message';
                code += `
                    const _inputMsg = '${message}';
                    alert((_inputMsg) ? _inputMsg : ${messageVariable});
                `;
            }
            else if(handler.handlerType === 'ACTION') {
                const actionName = content.actionName;
                const args = '_data';
                code += `VB.doAction('${actionName}', ${args});`;
            }
            else if(handler.handlerType === 'DATA_BINDING') {
                const messageId = content.messageId;
                const dataProvider = (content.dataProvider) ? content.dataProvider : content.messageId;
                const dataVariable = '_data';
                code += `$('div[dataProvider="${dataProvider}"]').trigger('bindData',[${dataVariable}['${messageId}']]); `;
            }
        }

        return code;
    }

    createOptionSheet(optionPanel, id, width, height) {
        return optionPanel.sheetScript(id, width, height,
            [
                {field:'orderNum', label:'순서', type:'text', width:80, hide:false, editable: true, align:'center', required:false},
                {field:'handlerType', label:'유형', type:'combo', width:130, hide:false, editable: true, align:'center', required:false, comboCodes:this.HANDLER_TYPE},
                {field:'handlerContent', label:'내용', type:'text', width:300, hide:false, editable: true, align:'left', required:false}
            ]);
    }
}