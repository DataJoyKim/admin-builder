class WorkflowClient {
    constructor() {
        this.timeout = 10000;
    }

    execute(workflowCode, requestBody, _success, _error) {
        let self = this;

        let requestMessage = this.createRequestMessage(workflowCode, requestBody);

        console.log('workflowClient.execute.'+workflowCode+'.request',requestMessage);

        $.ajax({
            type: 'POST',
            url: '/workflow',
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(requestMessage),
            timeout: this.timeout,
            beforeSend: function() {
                self.showLoadingBar();
            },
            success: function(response) {
                console.log('workflowClient.execute.'+workflowCode+'.response',response);
                if(!response.resultType){
                    alert('워크플로우 실행에 실패하였습니다.');
                }
                else if(response.resultType == 'SUCCESS') {
                    _success(response.contents);
                }
                else if(response.resultType == 'FAILURE') {
                    alert(response.message);
                }
                else {
                    alert('워크플로우 실행에 실패하였습니다.');
                }
            },
            error: function(error) {
                console.log('workflowClient.execute.error',error);
                if(error.responseJSON) {
                    let response = error.responseJSON;

                    if(response.resultType && response.resultType == 'FAILURE') {
                        _error(response.code, response.status, response.message, response.content);
                    }
                    else {
                        _error("E000", -2, response.message, null);
                    }
                }
                else {
                    _error("E000", -1, "에러가 발생되었습니다.", null);
                }
            },
            complete: function() {
                self.hideLoadingBar();
            }
        });
    }

    showLoadingBar() {
        App.loadingBar.show();
    }

    hideLoadingBar() {
        App.loadingBar.hide();
    }

    createRequestMessage(workflowCode,requestBody) {
        return {
            header:{
               workflowCode : workflowCode,
               objectCode : (App.objectCode) ? App.objectCode : '',
               localeCode : 'KO'
            },
            body:requestBody
        };
    }
}

window.App = window.App || {};
window.App.workflowClient = new WorkflowClient();