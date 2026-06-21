class Workflow extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'workflowCode', label:'워크플로우ID', type:'text', size:'col-6', defaultValue:''},
            {id:'requestMessages', label:'요청메시지 설정', type:'sheet', size:'col-12', defaultValue:''},
            {id:'responseMessageId', label:'응답메시지ID', type:'text', size:'col-6', defaultValue:''}
        ]
    }

    register(data) {
        const name = data.actionName;
        const argsName = data.argsName;
        const options = JSON.parse(data.contents);

        if (!name) {
            return;
        }

        const workflowCode = options.workflowCode;
        const requestMessages = options.requestMessages;
        const responseMessageId = options.responseMessageId;

        let message = `global.${this.globalVariable.variable.message}`;
        const requestMessagesSetting = JSON.stringify(requestMessages);

        // 함수 코드 생성
        let code = ``;

        // 요청메시지 코드 생성
        code += `
            let requestMessage = {};

            const requestMessagesSetting = ${requestMessagesSetting};

            for(const setting of requestMessagesSetting) {
                let messageData = ${message}[setting.messageId];
                requestMessage[setting.messageId] =  ((messageData) ? messageData : [{}]);
            }
        `;

        // 워크플로우 실행 코드 생성
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

        // 워크플로우 end
        code += `
            );
        `;

        super.registerAction(name,argsName,code);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.getHtml('workflowCode'));

        $panel.append(optionPanel.getHtml('requestMessages'));

        optionPanel.sheetScript('requestMessages', "300px", "200px",
            [
               {field:'messageId', label:'메시지ID', type:'text', width:200, hide:false, editable: true, align:'left', required:false}
           ]);

        $panel.append(optionPanel.getHtml('responseMessageId'));
    }
}