class VbSheet extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'sheet';
    }

    componentOptions() {
        return {
           id:'mySheet' + super.getComponentIdNumber(),
           dataProvider:'',
           width: "100%",
           height: "400px",
           useSeq: true,
           useStatus: true,
           useDelete: true,
           useDnd:false,
           columns:[]
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        return $(`
            <div id="${options.id}" dataProvider="${options.dataProvider}">
            </div>
        `);
    }

    scriptRuntime(el, options) {

        const columns = options.columns;
        for(const col of columns) {
            if(col.type == 'combo') {
                if(col.comboCodeName) {
                    let codes = VB.globalVariable.getCode()[col.comboCodeName];
                    if(!codes) {
                        codes = [];
                    }
                    col.comboCodes = codes;
                }
            }
        }

        // 시트 생성
        Sheet.initSheet(
            options.id,
            options.width,
            options.height,
            {
                useSeq:options.useSeq,
                useStatus:options.useStatus,
                useDelete:options.useDelete,
                useDnd:options.useDnd
            },
            options.columns
            );

        const sheet = window[options.id];

        // 시트 이벤트 생성
        $("#"+options.id).attr('dataProvider',options.dataProvider)
            .on('clearData', function(){

            })
            .on('getData', function(){
                return sheet.getSheetData();
            })
            .on('setData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
            })
            .on('newData', function(e, data){
                sheet.addRowData(data);
            })
            .on('bindData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
                sheet.setSheetData(data);
            })
            .on('selectedRowData', function(e, data){
                return sheet.getSelectedRowData();
            });
    }

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let el = `
            <div id="${options.id}" class="component vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                Sheet [${options.id}]
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                background-color: #ffffff;
                height: 400px;
                width: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                font-size: 18px;
                border-radius: 8px;
                border: 2px solid #ddd;
                box-shadow: 0 2px 3px rgba(0,0,0,0.2);
            }
        `;
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));

        let $rowId = this.optionPanel.row();
        $rowId.append(this.optionPanel.input('id',{label:'ID', size:'col-7'}));
        $panel.append($rowId);

        let $rowDataProvider = this.optionPanel.row();
        $rowDataProvider.append(this.optionPanel.input('dataProvider',{label:'dataProvider', size:'col-7'}));
        $panel.append($rowDataProvider);

        let $rowToggle = this.optionPanel.row();
        $rowToggle.append(this.optionPanel.toggle('useSeq',{label:'Seq컬럼', size:'col-3'}));
        $rowToggle.append(this.optionPanel.toggle('useStatus',{label:'상태컬럼', size:'col-3'}));
        $rowToggle.append(this.optionPanel.toggle('useDelete',{label:'삭제컬럼', size:'col-3'}));
        $rowToggle.append(this.optionPanel.toggle('useDnd',{label:'DnD컬럼', size:'col-3'}));
        $panel.append($rowToggle);

        let $rowGrid = this.optionPanel.row();
        $rowGrid.append(this.optionPanel.input('width',{label:'넓이', size:'col-6'}));
        $rowGrid.append(this.optionPanel.input('height',{label:'높이', size:'col-6'}));
        $rowGrid.append(this.optionPanel.button('column-setting',{label:'컬럼 설정', btnLabel:'설정',size:'col-12', icon:'fas fa-cog'}));
        $panel.append($rowGrid);
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.setValue('dataProvider',options.dataProvider);
        this.optionPanel.check('useSeq',options.useSeq);
        this.optionPanel.check('useStatus',options.useStatus);
        this.optionPanel.check('useDelete',options.useDelete);
        this.optionPanel.check('useDnd',options.useDnd);
        this.optionPanel.setValue('width',options.width);
        this.optionPanel.setValue('height',options.height);
        this.optionPanel.setValue('columns',options.columns);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('id',(e) => {
            super.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.inputEvent('dataProvider',(e) => {
            super.changeOptionValue($el, options, 'dataProvider', $(e.target).val());
        });

        this.optionPanel.inputEvent('width',(e) => {
            let value = $(e.target).val();
            super.changeOptionValue($el, options, 'width', value);
            $el.css('width', value);
        });

        this.optionPanel.inputEvent('height',(e) => {
            let value = $(e.target).val();
            super.changeOptionValue($el, options, 'height', value);
            $el.css('height', value);
        });

        this.optionPanel.clickEvent('useSeq',(e) => {
            super.changeOptionValue($el, options, 'useSeq', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('useStatus',(e) => {
            super.changeOptionValue($el, options, 'useStatus', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('useDelete',(e) => {
            super.changeOptionValue($el, options, 'useDelete', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('useDnd',(e) => {
            super.changeOptionValue($el, options, 'useDnd', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('column-setting',(e) => {
            const changeOptionValue = super.changeOptionValue;
            App.modalPopup.open('/console/view-sheet-column',{title:'컬럼설정 팝업',size:"modal-xl",messageId:'SHEET_COLUMN_REQUEST'},{columns:options.columns});
            App.modalPopup.receiveParam('SHEET_COLUMN_RESULT',function(data){
                if(data.columns) {
                    changeOptionValue($el, options, 'columns', data.columns);
                }
            });
        });
    }
}