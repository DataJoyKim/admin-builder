import { AbstractActions } from './AbstractActions.js';

export class Workflow extends AbstractActions {
    render() {
        const name = this.getAttribute('name');
        if (!name) {
            return;
        }

        const workflowCode = this.getAttribute('workflowCode');
        if (!workflowCode) {
            return;
        }

        // 함수 코드 생성
        let code = ``;

        // 요청메시지 코드 생성
        code += `
            let requestMessage = {};
        `;

        let requestMessageIds = this.createRequestMessageIds(this.children);
        let messageVariableName = super.getMessageVariableName();
        for(const messageId of requestMessageIds) {
            code += `
                requestMessage['${messageId}'] = ${messageVariableName}['${messageId}'];
            `;
        }

        // workflow 실행 코드 생성
        code += `
            App.workflowClient.execute('${workflowCode}',requestMessage,
        `;

        let responseTag = this.createResponseTag(this.children);

        // 결과 코드 생성
        let resultEventTag = responseTag[super.getResultEventTagName()];

        code += `function(response){`

        let bindMessageTags = resultEventTag[super.getBindMessageTagName()];
        for(const bindMessageTag of bindMessageTags) {
            let bindMessageId = bindMessageTag.getAttribute("id");
            code += `   ${messageVariableName}['${bindMessageId}'] = response['${bindMessageId}'];`;
        }

        let scriptTags = resultEventTag[super.getScriptTagName()];
        for(const scriptTag of scriptTags) {
            let scriptFunctionName = scriptTag.getAttribute('name');
            code += `   ${scriptFunctionName}();`;
        }

        code += `},`;

        // 실패 코드 생성
        let faultEventTag = responseTag[super.getFaultEventTagName()];

        code += `function(code, status, message){ `;
        code += ` let error = {code:code,status:status,message:message};`;

        let faultScriptTags = faultEventTag[super.getScriptTagName()];
        for(const faultScriptTag of faultScriptTags) {
            let faultScriptFunctionName = faultScriptTag.getAttribute('name');
            code += `   ${faultScriptFunctionName}(error);`;
        }

        code += `}`;

        // workflow end
        code += `
            );
        `;

        this.logging('code', code);

        // 함수 생성
        window[name] = new Function(code);

        // 컴포넌트 준비완료
        super.readyComplete();
    }

    createRequestMessageIds(children) {
        let messagesIds = new Array();

        for (const child of children) {
            if(child.tagName.toLowerCase() == super.getMessageTagName()) {
                messagesIds.push(child.getAttribute('id'));
            }
        }

        return messagesIds;
    }

    createResponseTag(children) {
        let responseTag = new Array();

        for (const child of children) {
            let childTagName = child.tagName.toLowerCase();

            if(childTagName != super.getResultEventTagName()
            && childTagName != super.getFaultEventTagName()) {
                continue;
            }

            responseTag[childTagName] = {};

            for (const child2 of child.children) {
                let childTagName2 = child2.tagName.toLowerCase();

                let childArr = responseTag[childTagName][childTagName2];
                if(!childArr) {
                    childArr = new Array();
                }

                childArr.push(child2);

                responseTag[childTagName][childTagName2] = childArr;
            }
        }

        return responseTag;
    }
}