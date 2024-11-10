    function _showLoadingBar(_show) {
        let display = (_show) ? 'block' : 'none';

        $("#loading-bar").css('display',display);
    }

    function _initLoadingBar() {
        document.write('<div id="loading-bar" class="spinner-border text-primary" role="status" style="display:none;position: fixed;top: calc(50% - (58px / 2));right: calc(50% - (58px / 2));">');
        document.write('  <span class="sr-only" >Loading...</span>');
        document.write('</div>');
    }

    function setCodeBox(id, codes) {
        let sHtml = '';
        for(let i=0; i<codes.length; i++) {
            sHtml += `<option value='${codes[i].id}'>${codes[i].name}</option>`;
        }

        $("#"+id).append(sHtml);
    }

    function isEmpty(v) {
        return (v == undefined || v == null || v == '');
    }