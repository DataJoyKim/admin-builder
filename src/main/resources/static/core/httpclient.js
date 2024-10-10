class HttpClient {
    constructor() {}

  get(_params) {
    $.ajax({
      type: 'GET',
      url: _params.url,
      data: {},
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        _params.success(response);
      },
      error: function(error) {
        _params.error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }

  put(_params) {
    $.ajax({
      type: 'PUT',
      url: _params.url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(_params.data),
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        _params.success(response);
      },
      error: function(error) {
        _params.error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }

  post(_params) {
    $.ajax({
      type: 'POST',
      url: _params.url,
      dataType: 'json',
      contentType: 'application/json; charset=utf8',
      data: JSON.stringify(_params.data),
      beforeSend: function() {
        _showLoadingBar(true);
      },
      success: function(response) {
        _params.success(response);
      },
      error: function(error) {
        _params.error(error);
      },
      complete: function() {
        _showLoadingBar(false);
      }
    });
  }
}