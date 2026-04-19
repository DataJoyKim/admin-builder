class ComponentPanel {
    constructor() {}

    init(panelId) {
        let $panel = $("#"+panelId);

        let $layoutEl = this.details('레이아웃',false);

        $panel.append($layoutEl);

        let $containerEl = this.details('컨테이너',true);
        this.item($containerEl, 'card', 'Card', 'com-card.png');
        this.item($containerEl, 'form', 'Form', 'com-form.png');
        $panel.append($containerEl);

        let $sheetEl = this.details('시트 및 그리드',true);
        this.item($sheetEl, 'grid', 'jsGrid', 'com-grid.png');
        $panel.append($sheetEl);

        let $buttonEl = this.details('버튼',false);
        this.item($buttonEl, 'button', 'Button', 'com-button.png');
        $panel.append($buttonEl);

        let $inputEl = this.details('폼 입력 요소',false);
        this.item($inputEl, 'input', 'Input', 'com-input.png');
        $panel.append($inputEl);

        let $etcEl = this.details('기타',false);
        this.item($etcEl, 'html', 'Html', 'com-html.png');
        $panel.append($etcEl);
    }

    details(summary, open) {
        const openHtml = (open) ? 'open' : '';

        return $(`
            <details class="border rounded mb-2 shadow-sm" ${openHtml}>
                <summary class="p-2 bg-light fw-semibold">${summary}</summary>
                <div class="details-content">
                </div>
            </details>
        `);
    }

    item($details, componentId, label, imgFileNm) {
        $details.find('.details-content').append(this.component(componentId, label, imgFileNm));
    }

    component(componentId, label, imgFileNm) {
        return $(`
            <div class="component-item" data-type="component-${componentId}" >
                <img src="/dist/img/${imgFileNm}" width="40" height="30" >
                <label style="margin-left:10px">${label}</label>
            </div>
        `);
    }
}