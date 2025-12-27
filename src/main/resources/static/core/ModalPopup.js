class ModalPopup {
    constructor() {
        this.POPUP_ID = 'popup';
    }

    open(url, options = {}, params) {
      const settings = {
        title: options.title || '',
        size: options.size || 'modal-xl',
        height: options.height || '80vh',
        messageId: "POPUP_REQUEST"
      };

      let $popup = $('#'+this.POPUP_ID);

      if ($popup.length === 0) {

        $popup = $('<div>')
          .addClass('modal fade')
          .attr({
            id: 'popup',
            tabindex: '-1',
            'aria-hidden': 'true'
          });

        const $dialog = $('<div>')
          .addClass('modal-dialog ' + settings.size);

        const $content = $('<div>')
          .addClass('modal-content');

        const $header = $('<div>')
          .addClass('modal-header');

        const $title = $('<h5>')
          .addClass('modal-title')
          .attr('id', 'popup-title');

        const $closeBtn = $('<button>')
          .addClass('close')
          .attr({
            type: 'button',
            'data-dismiss': 'modal'
          })
          .html('<span>&times;</span>');

        const $body = $('<div>')
          .addClass('modal-body p-0');

        const $iframe = $('<iframe>')
          .attr({ id: 'popup-frame' })
          .css({
            width: '100%',
            border: 0
          });

        // 조립
        $header.append($title, $closeBtn);
        $body.append($iframe);
        $content.append($header, $body);
        $dialog.append($content);
        $popup.append($dialog);

        $('body').append($popup);

        $popup.on('hidden.bs.modal', function () {
          $('#popup-frame').attr('src', 'about:blank');
        });
      }

      $('#popup-title').text(settings.title);
      $('#popup-frame')
         .off('load')
         .on('load', function () {
            this.contentWindow.postMessage(
                {
                    messageId:settings.messageId,
                    type: 'POPUP_REQUEST',
                    payload: params
                },
                '*'
            );
         })
        .css('height', settings.height)
        .attr('src', url);

      $popup.modal('show');
    }

    sendParamToParent(messageId, params) {
        window.parent.postMessage(
            {
                messageId:messageId,
                type: 'POPUP_RESULT',
                payload: params
            },
            '*'
        );
    }

    receiveParam(receiveMessageId, _callback) {
        if (this._messageHandler) {
            window.removeEventListener('message', this._messageHandler);
        }

        this._messageHandler = function (event) {
            const {messageId, type, payload } = event.data || {};

            if(receiveMessageId !== messageId) {
                return;
            }

            if (type === 'POPUP_RESULT' || type === 'POPUP_REQUEST') {
                _callback(payload);
            }
        };

        window.addEventListener('message', this._messageHandler);
    }

    close() {
        window.parent.$('#'+this.POPUP_ID).modal('hide');
    }
}

window.App = window.App || {};
window.App.modalPopup = new ModalPopup();