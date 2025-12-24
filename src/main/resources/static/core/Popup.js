class Popup {
    constructor() {}

    open(url, options = {}, params) {
      const settings = {
        title: options.title || '',
        width: options.width || 'modal-xl',
        height: options.height || '80vh'
      };

      window.open(url, settings.title, `width=${settings.width},height=${settings.height}`);
    }
}

window.App = window.App || {};
window.App.popup = new Popup();