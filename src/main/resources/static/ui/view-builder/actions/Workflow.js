import { Actions } from './Actions.js';

export class Workflow extends Actions {
    constructor() {
        super();
    }

    register(data) {
        const name = data.actionName;
        const workflowCode = data.workflowCode;
        const requestMessageId = data.workflowRequestMessageId;
        const responseMessageId = data.workflowResponseMessageId;
        const gridId = data.workflowResponseGridId;

        if (!name || !workflowCode) {
            return;
        }

        let message = `global.${super.variableMessage()}`;

        // 함수 코드 생성
        let code = ``;

        // 요청메시지 코드 생성
        code += `
            let requestMessage = {
                '${requestMessageId}': ${message}['${requestMessageId}']
            };
        `;

        // workflow 실행 코드 생성
        code += `
            App.workflowClient.execute('${workflowCode}',requestMessage,
        `;

        // 결과 코드 생성
        code += `function(response){`
        code += `   ${message}['${responseMessageId}'] = response['${responseMessageId}'];`;
        if(gridId) {
            code += `   App.grid.setData('${gridId}', ${message}['${responseMessageId}']);`;
        }
        //code += `   App.form.setData('${formId}', ${message}['${responseMessageId}'][0]);`;
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

        // 함수 생성
        window[name] = new Function(code);
    }
}