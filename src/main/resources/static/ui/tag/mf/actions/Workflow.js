import { AbstractActions } from './AbstractActions.js';

export class Workflow extends AbstractActions {
    render() {
        const name = this.getAttribute('name');
        if (!name) {
            return;
        }

        const workflowName = this.getAttribute('workflowName');
        if (!workflowName) {
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
            httpClient.post('/workflow/${workflowName}',{}, requestMessage,
        `;

        let responseTag = this.createResponseTag(this.children);

        // 결과 코드 생성
        let resultEventTag = responseTag[super.getResultEventTagName()];
        let bindMessageTag = resultEventTag[super.getBindMessageTagName()];
        let scriptTag = resultEventTag[super.getScriptTagName()];

        let bindMessageId = bindMessageTag.getAttribute("id");
        let scriptFunctionName = scriptTag.getAttribute('name');

        code += `function(response){`
        code += `   ${messageVariableName}['${bindMessageId}'] = response['${bindMessageId}'];`;
        code += `   ${scriptFunctionName}();`;
        code += `},`;

        // 실패 코드 생성
        let faultEventTag = responseTag[super.getFaultEventTagName()];
        let faultScriptTag = faultEventTag[super.getScriptTagName()];
        let faultScriptFunctionName = faultScriptTag.getAttribute('name');

        code += `function(error){`;
        code += `   ${faultScriptFunctionName}();`;
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
            if(child.tagName.toLowerCase() == super.getResultEventTagName()
            || child.tagName.toLowerCase() == super.getFaultEventTagName()
            ) {
                responseTag[child.tagName.toLowerCase()] = {};
                for (const child2 of child.children) {
                    responseTag[child.tagName.toLowerCase()][child2.tagName.toLowerCase()] = child2;
                }
            }
        }

        return responseTag;
    }
}