function _showLoadingBar(_show) {
    let display = (_show) ? 'block' : 'none';

    $("#loading-bar").css('display',display);
}

function _initLoadingBar() {
    document.write('<div id="loading-bar" class="spinner-border text-primary" role="status" style="display:none;position: fixed;top: calc(50% - (58px / 2));right: calc(50% - (58px / 2));">');
    document.write('  <span class="sr-only" >Loading...</span>');
    document.write('</div>');
}