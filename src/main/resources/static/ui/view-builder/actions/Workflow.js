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
            {id:'resultHandler', label:'결과 처리', type:'sheet', size:'col-12', defaultValue:''},
            {id:'failureHandler', label:'실패 처리', type:'sheet', size:'col-12', defaultValue:''}
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
        const failureHandler = options.failureHandler;

        let message = `global.${this.globalVariable.variable.message}`;
        const requestMessagesSetting = JSON.stringify(requestMessages);

        // 함수 코드 생성
        let code = ``;

        // 요청메시지 코드 생성
        code += `
            let requestMessage = {};

            const requestMessagesSetting = ${requestMessagesSetting};

            for(const setting of requestMessagesSetting) {                
                const dataProvider = $("div[dataProvider='"+setting.messageId+"']");
                if(dataProvider[0]) {
                    try {
                        requestMessage[setting.messageId] = $("div[dataProvider='"+setting.messageId+"']").triggerHandler('getData');
                    } catch (e) {
                        alert(e.message);
                        return;
                    }
                }
                else {
                    const messageData = ${message}[setting.messageId];
                    requestMessage[setting.messageId] = ((messageData) ? messageData : [{}]);
                }
                
                if(setting.columnSet) {
                    const columnSet = JSON.parse(setting.columnSet);
                    let messageData = requestMessage[setting.messageId];
                    for(let messageDataObj of messageData) {
                        for(const col in columnSet) {
                            messageDataObj[col] = columnSet[col];
                        }
                    }
                }
            }
        `;

        // 확인창 메시지
        if(options.confirmMsg) {
            code += `
                if(!confirm('${options.confirmMsg}')) {
                    return;
                }
            `;
        }

        // 워크플로우 실행 코드 생성
        code += `
            VB.utils.workflowClient.execute('${workflowCode}',requestMessage,
        `;

        // 결과 코드 생성
        code += `function(response){`;

        // 결과 처리기 사용하기위한 변수 선언.
        code += `   const _data = response.contents;`;
        code += `   const _message = response.message;`;
        code += VB.actionHandlerScriptEngine.createScript(resultHandler);

        code += `},`;

        // 실패 코드 생성
        code += `function(code, status, message){ `;

        // 실패 처리기 사용하기위한 변수 선언.
        code += `   const _data = {code:code,status:status,message:message};`;
        code += `   const _message = '['+code+'] ' + message;`;
        code += VB.actionHandlerScriptEngine.createScript(failureHandler);

        code += `}`;

        // 워크플로우 end
        code += `
            );
        `;

        super.registerAction(name,argsName,code);
    }

    optionPanelView($panel, optionPanel) {
        $panel.append(optionPanel.sectionTitle('기본'));
        $panel.append(optionPanel.getHtml('workflowCode'));
        $panel.append(optionPanel.getHtml('confirmMsg'));

        $panel.append(optionPanel.sectionTitle('요청 메시지'));
        $panel.append(optionPanel.getHtml('requestMessages'));

        optionPanel.sheetScript('requestMessages', "100%", "200px",
            [
               {field:'messageId', label:'메시지ID', type:'text', width:180, hide:false, editable: true, align:'left', required:false},
                {field:'columnSet', label:'컬럼 설정', type:'text', width:350, hide:false, editable: true, align:'left', required:false}
           ]);

        $panel.append(optionPanel.sectionTitle('결과 처리'));
        $panel.append(optionPanel.getHtml('resultHandler'));
        VB.actionHandlerScriptEngine.createOptionSheet(optionPanel, 'resultHandler', "100%", "250px");

        $panel.append(optionPanel.sectionTitle('실패 처리'));
        $panel.append(optionPanel.getHtml('failureHandler'));
        VB.actionHandlerScriptEngine.createOptionSheet(optionPanel, 'failureHandler', "100%", "250px");
    }
}