class Util {
    constructor() {}

    ifNull(v, rv) {
        return ((v) ? v : rv);
    }

    ynToBoolean(yn) {
        return (yn && yn == 'Y') ? true : false;
    }

    booleanToYn(value) {
        return (value && value == true) ? 'Y' : 'N';
    }

    stringToBoolean(value) {
        return (value && value.toLowerCase() == "true") ? true : false;
    }

    setCodeBox(id, codes, options) {
        let codeBox = ``;
        if(options && options.useFirstItem) {
            let value = (options.firstItemValue) ? options.firstItemValue : '';
            let label = (options.firstItemLabel) ? options.firstItemLabel : '';

            codeBox = `<option value='${value}'>${label}</option>`;
        }

        for(let code of codes) {
            codeBox += `<option value='${code.code}'>${code.name}</option>`;
        }

        $("#"+id).html(codeBox);
    }


    check(id, v) {
        if(v) {
            $('#'+id).prop('checked', true);
        }
        else {
            $('#'+id).prop('checked', false);
        }
    }

    isCheck(id) {
        const toggle = document.getElementById(id);
        return toggle.checked;
    }
}

window.App = window.App || {};
window.App.util = new Util();