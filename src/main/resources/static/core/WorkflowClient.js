import { showLoadingBar } from './LoadingBar.js';

export class WorkflowClient {
    constructor() {
        this.timeout = 10000;
    }

  execute(workflowName, messages, _success, _error) {
    let url = `/workflow/${workflowName}`;

    console.log('workflowClient.execute.request',{
        url:url,
        messages:messages
    });

    $.ajax({
      type: 'POST',
      url: url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(messages),
      timeout: this.timeout,
      beforeSend: function() {
        showLoadingBar(true);
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
        showLoadingBar(false);
      }
    });
  }
}