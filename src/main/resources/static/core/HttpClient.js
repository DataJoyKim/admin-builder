class HttpClient {
    constructor() {
        this.timeout = 10000;
    }

  get(_url, _requestParams, _success, _error) {
    let self = this;

    console.log('httpClient.get.'+_url+'.request',{
        url:_url,
        requestParams:_requestParams
    });

    $.ajax({
      type: 'GET',
      url: _url,
      data: _requestParams,
      timeout: this.timeout,
      beforeSend: function() {
        self.showLoadingBar();
      },
      success: function(response) {
        console.log('httpClient.get.'+_url+'.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        self.hideLoadingBar();
      }
    });
  }

  post(_url, _requestParams, _requestBody, _success, _error, options) {
    let self = this;

    console.log('httpClient.post.'+_url+'.request',{
        url:_url,
        requestParams:_requestParams,
        requestBody:_requestBody
    });

    if(_requestParams != undefined && _requestParams != null && _requestParams != '') {
        let i=0;
        for(let key in _requestParams) {
            let value = _requestParams[key];
            _url += (i > 0) ? "&" : "?" + key + "=" + value;
            i++;
        }
    }

    $.ajax({
      type: 'POST',
      url: _url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(_requestBody),
      timeout: this.timeout,
      async: (options) ? options.async : true,
      beforeSend: function() {
        self.showLoadingBar();
      },
      success: function(response) {
        console.log('httpClient.post.'+_url+'.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        self.hideLoadingBar();
      }
    });
  }

  put(_url, _requestParams, _requestBody, _success, _error) {
    let self = this;
    console.log('httpClient.put.'+_url+'.request',{
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
        self.showLoadingBar();
      },
      success: function(response) {
        console.log('httpClient.put.'+_url+'.response',response);

        _success(response);
      },
      error: function(error) {
        _error(error);
      },
      complete: function() {
        self.hideLoadingBar();
      }
    });
  }

  delete(_url, _requestParams, _success, _error) {
    let self = this;

    console.log('httpClient.delete.'+_url+'.request',{
        url:_url,
        requestParams:_requestParams
    });

    $.ajax({
      type: 'DELETE',
      url: _url,
      timeout: this.timeout,
      beforeSend: function() {
        self.showLoadingBar();
      },
      success: function(response) {
        console.log('httpClient.put.'+_url+'.response',response);

        _success(response);
      },
      error: function(error) {
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
window.App.httpClient = new HttpClient();