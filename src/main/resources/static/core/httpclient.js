class HttpClient {
    constructor() {
        this.timeout = 10000;
    }

  get(_url, _requestParams, _success, _error) {
    $.ajax({
      type: 'GET',
      url: _url,
      data: _requestParams,
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
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
    $.ajax({
      type: 'DELETE',
      url: _url,
      timeout: this.timeout,
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
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