class HttpClient {
    constructor() {
        this.timeout = 10000;
    }

  get(_url, _requestParams, _success, _error) {
    console.log('httpClient.get.request',{
        url:_url,
        requestParams:_requestParams
    });

    $.ajax({
      type: 'GET',
      url: _url,
      data: _requestParams,
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        console.log('httpClient.get.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }

  post(_url, _requestParams, _requestBody, _success, _error) {
    console.log('httpClient.post.request',{
        url:_url,
        requestParams:_requestParams,
        requestBody:_requestBody
    });

    $.ajax({
      type: 'POST',
      url: _url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(_requestBody),
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        console.log('httpClient.post.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }

  put(_url, _requestParams, _requestBody, _success, _error) {
    console.log('httpClient.put.request',{
        url:_url,
        requestParams:_requestParams,
        requestBody:_requestBody
    });

    $.ajax({
      type: 'PUT',
      url: _url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(_requestBody),
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        console.log('httpClient.put.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }

  delete(_url, _requestParams, _success, _error) {
    console.log('httpClient.delete.request',{
        url:_url,
        requestParams:_requestParams
    });

    $.ajax({
      type: 'DELETE',
      url: _url,
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        console.log('httpClient.put.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }
}