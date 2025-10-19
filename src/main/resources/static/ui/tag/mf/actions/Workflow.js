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
        code += `function(response){`

        let resultEventTag = responseTag[super.getTagEl().resultEvent];
        if(resultEventTag) {
            for(const childTag of resultEventTag) {
                let tagContent = childTag.content;

                // Bind Message Tag
                if(childTag.name == super.getTagEl().bindMessage) {
                    let bindMessageId = tagContent.getAttribute("id");
                    code += `   ${messageVariableName}['${bindMessageId}'] = response['${bindMessageId}'];`;
                }
                // Script Tag
                else if(childTag.name == super.getTagEl().script) {
                    let scriptFunctionName = tagContent.getAttribute('name');
                    code += `   ${scriptFunctionName}();`;
                }
            }
        }

        code += `},`;

        // 실패 코드 생성
        code += `function(code, status, message){ `;
        code += ` let error = {code:code,status:status,message:message};`;

        let faultEventTag = responseTag[super.getTagEl().faultEvent];
        if(faultEventTag) {
            for(const childTag of faultEventTag) {
                let tagContent = childTag.content;

                // Script Tag
                if(childTag.name == super.getTagEl().script) {
                    let faultScriptFunctionName = tagContent.getAttribute('name');
                    code += `   ${faultScriptFunctionName}(error);`;
                }
            }
        }

        code += `}`;

        // workflow end
        code += `
            );
        `;

        this.logging('code', code);

        // 함수 생성
        window[name] = new Function(code);
    }

    createRequestMessageIds(children) {
        let messagesIds = new Array();

        for (const child of children) {
            if(child.tagName.toLowerCase() == super.getTagEl().message) {
                messagesIds.push(child.getAttribute('id'));
            }
        }

        return messagesIds;
    }

    createResponseTag(children) {
        let responseTag = new Array();

        for (const child of children) {
            let childTagName = child.tagName.toLowerCase();

            if(childTagName != super.getTagEl().resultEvent
            && childTagName != super.getTagEl().faultEvent) {
                continue;
            }

            let childArr = new Array();
            for (const childSub of child.children) {
                childArr.push({
                    name:childSub.tagName.toLowerCase(),
                    content:childSub
                });
            }

            responseTag[childTagName] = childArr;
        }

        return responseTag;
    }
}