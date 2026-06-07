class Workflow extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'workflowCode', label:'워크플로우ID', type:'text', size:'col-6', defaultValue:''},
            {id:'requestMessageId', label:'요청메시지ID', type:'text', size:'col-6', defaultValue:''},
            {id:'responseMessageId', label:'응답메시지ID', type:'text', size:'col-6', defaultValue:''}
        ]
    }

    register(data) {
        const name = data.actionName;
        const argsName = data.argsName;
        const options = JSON.parse(data.contents);

        const workflowCode = options.workflowCode;
        const requestMessageId = options.requestMessageId;
        const responseMessageId = options.responseMessageId;

        if (!name || !workflowCode) {
            return;
        }

        let message = `global.${this.globalVariable.variable.message}`;

        // 함수 코드 생성
        let code = ``;

        // 요청메시지 코드 생성
        code += `
            let messageData = ${message}['${requestMessageId}'];
            let requestMessage = {
                '${requestMessageId}': ((messageData) ? messageData : [{}])
            };
        `;

        // workflow 실행 코드 생성
        code += `
            VB.utils.workflowClient.execute('${workflowCode}',requestMessage,
        `;

        // 결과 코드 생성
        code += `function(response){`
        code += `   ${message}['${responseMessageId}'] = response['${responseMessageId}'];`;
        code += `   $('div[dataProvider="${responseMessageId}"]').trigger('bindData',[response['${responseMessageId}']]); `;
        code += `},`;

        // 실패 코드 생성
        code += `function(code, status, message){ `;
        code += `   let error = {code:code,status:status,message:message};`;
        code += `   alert('['+error.code+'] ' + error.message);`;
        code += `}`;

        // workflow end
        code += `
            );
        `;

        super.registerAction(name,argsName,code);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.getHtml('workflowCode'));
        $panel.append(optionPanel.getHtml('requestMessageId'));
        $panel.append(optionPanel.getHtml('responseMessageId'));
    }
}