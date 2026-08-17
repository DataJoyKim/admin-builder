class TextEditor {
    // TextEditor.initTextEditor(id, width, height, setting);
    static initTextEditor(id, width, height, setting) {
        window[id] = new TextEditor(id, width, height, setting);
    }

    constructor(id, width, height, setting) {
        this.id = id;
        this.init(id, width, height, setting);
    }

    init(id, width, height, setting) {
        let options = {
            width: width,
            height: height,
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'underline', 'clear']],
                ['fontsize', ['fontsize']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['insert', ['link', 'picture']],
                ['view', ['codeview']]
            ]
        }

        if(setting) {
            if(setting.placeholder) {
                options.placeholder = setting.placeholder;
            }
        }

        $('#'+id).summernote(options);
    }

    getValue() {
        return $('#'+this.id).summernote('code')
    }

    setValue(value) {
        return $('#'+this.id).summernote('code',value)
    }
}