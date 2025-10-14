class LoadingBar {
    constructor() {}

    render() {
        document.write('<div id="loading-bar" class="spinner-border text-primary" role="status" style="display:none;position: fixed;top: calc(50% - (58px / 2));right: calc(50% - (58px / 2));">');
        document.write('  <span class="sr-only" >Loading...</span>');
        document.write('</div>');
    }

    show() {
        $("#loading-bar").css('display','block');
    }

    hide() {
        $("#loading-bar").css('display','none');
    }
}

window.App = window.App || {};
window.App.loadingBar = new LoadingBar();