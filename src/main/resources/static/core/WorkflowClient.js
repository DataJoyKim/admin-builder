class WorkflowClient {
    constructor() {
        this.timeout = 10000;
    }

    execute(workflowName, requestBody, _success, _error) {
        let self = this;

        let url = `/workflow/${workflowName}`;

        console.log('workflowClient.execute.request',{
            url:url,
            requestBody:requestBody
        });

        //TODO 요청메시지 정의 필요
        let requestMessage = requestBody;

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf8',
            data: JSON.stringify(requestMessage),
            timeout: this.timeout,
            beforeSend: function() {
                self.showLoadingBar();
            },
            success: function(response) {
                console.log('workflowClient.execute.response',response);
                _success(response);
            },
            error: function(error) {
                console.log('workflowClient.execute.error',error);
                _error(error);
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
}

window.App = window.App || {};
window.App.workflowClient = new WorkflowClient();