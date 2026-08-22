class BarLineChart extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'barline-chart';
    }

    componentOptions() {
        return {
           id:'myChart' + super.getComponentIdNumber(),
           dataProvider:'',
           width: "100%",
           height: "400px",
            title: '',
            titleLeft: '',
            titleTop: '',
            titleTextAlign: '',
            titleFontSize: '',
            direction: 'VERTICAL',
            staticCategory: '',
            seriesSetting: []
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        return $(`
            <div id="${options.id}" dataProvider="${options.dataProvider}" style="width: ${options.width}; height: ${options.height};">
            </div>
        `);
    }

    scriptRuntime(el, options) {
        const chart = new Chart(options.id);
        const chartOp = {};

        if(options.title) {
            chartOp.title = {};
            chartOp.title.text = options.title;

            if(options.titleLeft) {
                chartOp.title.left = options.titleLeft;
            }

            if(options.titleTop) {
                chartOp.title.top = options.titleTop;
            }

            if(options.titleTextAlign) {
                chartOp.title.textAlign = options.titleTextAlign;
            }

            if(options.titleFontSize) {
                chartOp.title.textStyle = {};
                chartOp.title.textStyle.fontSize = options.titleFontSize;
            }
        }

        let category;
        if(options.staticCategory) {
            category = options.staticCategory.split(',').map(v => v.trim());
        }

        chartOp.xAxis = {};
        chartOp.yAxis = {};

        if(options.direction === 'HORIZONTAL') { // 가로 bar
            chartOp.xAxis.type = 'value';
            chartOp.yAxis.type = 'category';
            if(category) {
                chartOp.yAxis.data = category;
            }
        }
        else { // 세로 bar
            chartOp.xAxis.type = 'category';
            if(category) {
                chartOp.xAxis.data = category;
            }
            chartOp.yAxis.type = 'value';
        }

        if(options.seriesSetting) {
            chartOp.series = options.seriesSetting;
        }

        chart.setOption(chartOp);

        // 시트 이벤트 생성
        $("#"+options.id).attr('dataProvider',options.dataProvider)
            .on('clearData', function(){
                chart.setData([]);
            })
            .on('setData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
            })
            .on('bindData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
                chart.setData(data);
            });
    }

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let el = `
            <div id="${options.id}" class="component vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                Bar/Line Chart [${options.id}]
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

        let $rowGrid = this.optionPanel.row();
        $rowGrid.append(this.optionPanel.input('width',{label:'넓이', size:'col-6'}));
        $rowGrid.append(this.optionPanel.input('height',{label:'높이', size:'col-6'}));
        $panel.append($rowGrid);

        let $op1Grid = this.optionPanel.row();
        $op1Grid.append(this.optionPanel.select('direction',{label:'방향', size:'col-6',
            options:`
                <option value="VERTICAL">세로</option>
                <option value="HORIZONTAL">가로</option>
            `}));
        $panel.append($op1Grid);

        let $op2Grid = this.optionPanel.row();
        $op2Grid.append(this.optionPanel.input('staticCategory',{label:'정적 카테고리', size:'col-12'}));
        $panel.append($op2Grid);

        let $op3Grid = this.optionPanel.row();
        $op3Grid.append(this.optionPanel.button('seriesSetting',{label:'Series 설정', btnLabel:'설정',size:'col-12', icon:'fas fa-cog'}));
        $panel.append($op3Grid);

        let $op4Grid = this.optionPanel.row();
        $op4Grid.append(this.optionPanel.input('title',{label:'제목', size:'col-6'}));
        $panel.append($op4Grid);

        let $op5Grid = this.optionPanel.row();
        $op5Grid.append(this.optionPanel.input('titleLeft',{label:'제목 Left', size:'col-6', placeholder:'left|center|right|10px|10%'}));
        $op5Grid.append(this.optionPanel.input('titleTop',{label:'제목 Top', size:'col-6',placeholder:'top|middle|bottom|10px|10%'}));
        $op5Grid.append(this.optionPanel.input('titleTextAlign',{label:'제목 TextAlign', size:'col-6',placeholder:'left|center|right'}));
        $op5Grid.append(this.optionPanel.input('titleFontSize',{label:'제목 FontSize', size:'col-6', placeholder:'15'}));
        $panel.append($op5Grid);
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.setValue('dataProvider',options.dataProvider);
        this.optionPanel.setValue('width',options.width);
        this.optionPanel.setValue('height',options.height);
        this.optionPanel.setValue('title',options.title);
        this.optionPanel.setValue('titleLeft',options.titleLeft);
        this.optionPanel.setValue('titleTop',options.titleTop);
        this.optionPanel.setValue('titleTextAlign',options.titleTextAlign);
        this.optionPanel.setValue('titleFontSize',options.titleFontSize);
        this.optionPanel.setValue('seriesSetting',options.seriesSetting);
        this.optionPanel.setValue('staticCategory',options.staticCategory);
        this.optionPanel.setValue('direction',options.direction);
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

        this.optionPanel.inputEvent('title',(e) => {
            super.changeOptionValue($el, options, 'title', $(e.target).val());
        });
        this.optionPanel.inputEvent('titleLeft',(e) => {
            super.changeOptionValue($el, options, 'titleLeft', $(e.target).val());
        });
        this.optionPanel.inputEvent('titleTop',(e) => {
            super.changeOptionValue($el, options, 'titleTop', $(e.target).val());
        });
        this.optionPanel.inputEvent('titleTextAlign',(e) => {
            super.changeOptionValue($el, options, 'titleTextAlign', $(e.target).val());
        });
        this.optionPanel.inputEvent('titleFontSize',(e) => {
            super.changeOptionValue($el, options, 'titleFontSize', $(e.target).val());
        });
        this.optionPanel.inputEvent('staticCategory',(e) => {
            super.changeOptionValue($el, options, 'staticCategory', $(e.target).val());
        });
        this.optionPanel.inputEvent('direction',(e) => {
            super.changeOptionValue($el, options, 'direction', $(e.target).val());
        });

        this.optionPanel.clickEvent('seriesSetting',(e) => {
            const changeOptionValue = super.changeOptionValue;
            App.modalPopup.open('/console/view-sheet-column',{title:'컬럼설정 팝업',size:"modal-xl",messageId:'SHEET_COLUMN_REQUEST'},{seriesSetting:options.seriesSetting});
            App.modalPopup.receiveParam('SHEET_COLUMN_RESULT',function(data){
                if(data.seriesSetting) {
                    changeOptionValue($el, options, 'seriesSetting', data.seriesSetting);
                }
            });
        });
    }
}