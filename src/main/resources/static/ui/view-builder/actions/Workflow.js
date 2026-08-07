class Workflow extends Actions {
    constructor(optionPanel, globalVariable) {
        super(optionPanel, globalVariable);
        this.globalVariable = globalVariable;
    }

    actionOptions() {
        return [
            {id:'workflowCode', label:'워크플로우ID', type:'text', size:'col-6', defaultValue:''},
            {id:'confirmMsg', label:'확인창 메시지', type:'text', size:'col-6', defaultValue:''},
            {id:'requestMessages', label:'요청메시지 설정', type:'sheet', size:'col-12', defaultValue:''},
            {id:'resultHandler', label:'결과 처리', type:'sheet', size:'col-12', defaultValue:''}
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
        const resultHandler = options.resultHandler;

        let message = `global.${this.globalVariable.variable.message}`;
        const requestMessagesSetting = JSON.stringify(requestMessages);

        // 함수 코드 생성
        let code = ``;

        // 확인창 메시지
        if(options.confirmMsg) {
            code += `
                if(!confirm('${options.confirmMsg}')) {
                    return;
                }
            `;
        }

        // 요청메시지 코드 생성
        code += `
            let requestMessage = {};

            const requestMessagesSetting = ${requestMessagesSetting};

            for(const setting of requestMessagesSetting) {                
                const dataProvider = $("div[dataProvider='"+setting.messageId+"']");
                if(dataProvider[0]) {
                    requestMessage[setting.messageId] = $("div[dataProvider='"+setting.messageId+"']").triggerHandler('getData');
                }
                else {
                    const messageData = ${message}[setting.messageId];
                    requestMessage[setting.messageId] = ((messageData) ? messageData : [{}]);
                }
            }
        `;

        // 워크플로우 실행 코드 생성
        code += `
            VB.utils.workflowClient.execute('${workflowCode}',requestMessage,
        `;

        // 결과 코드 생성
        code += `function(response){`;
        code += `   let resultData = response.contents;`;
        code += `   let message = response.message;`;
        code += `   let resultType = response.resultType;`;

        code += `   if(resultType == 'FAILURE') { 
                        alert(message);
                        return;
                    }
                `;

        for(const handler of resultHandler) {
            const content = (handler.handlerContent) ? JSON.parse(handler.handlerContent) : {};

            if(handler.handlerType === 'ALERT') {
                const message = content.message;
                code += `
                    const _message = '${message}';
                    alert((_message) ? _message : message);
                `;
            }
            else if(handler.handlerType === 'ACTION') {
                const actionName = content.actionName;
                code += `VB.doAction('${actionName}', resultData);`;
            }
            else if(handler.handlerType === 'DATA_BINDING') {
                const messageId = content.messageId;
                const dataProvider = (content.dataProvider) ? content.dataProvider : content.messageId;
                code += `console.log('response',response);`;
                code += `$('div[dataProvider="${dataProvider}"]').trigger('bindData',[resultData['${messageId}']]); `;
            }
        }

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

        $panel.append(optionPanel.getHtml('confirmMsg'));

        $panel.append(optionPanel.getHtml('requestMessages'));

        optionPanel.sheetScript('requestMessages', "300px", "200px",
            [
               {field:'messageId', label:'메시지ID', type:'text', width:200, hide:false, editable: true, align:'left', required:false}
           ]);

        const HANDLER_TYPE = [
            {code:'ALERT', name:'얼럿'},
            {code:'ACTION', name:'액션'},
            {code:'DATA_BINDING', name:'데이터바인딩'}
        ]

        $panel.append(optionPanel.getHtml('resultHandler'));

        optionPanel.sheetScript('resultHandler', "500px", "200px",
            [
                {field:'orderNum', label:'순서', type:'text', width:50, hide:false, editable: true, align:'center', required:false},
                {field:'handlerType', label:'유형', type:'combo', width:100, hide:false, editable: true, align:'center', required:false, comboCodes:HANDLER_TYPE},
                {field:'handlerContent', label:'내용', type:'text', width:300, hide:false, editable: true, align:'left', required:false}
            ]);
    }
}