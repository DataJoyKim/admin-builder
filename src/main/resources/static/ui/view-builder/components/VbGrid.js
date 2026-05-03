class VbGrid extends ViewObject {
    constructor(optionPanel, grid) {
        super(optionPanel);
        this.optionPanel = optionPanel;
        this.grid = grid;
    }

    componentId() {
        return 'grid';
    }

    componentOptions() {
        return {
           id:this.componentId() + super.getComponentIdNumber(),
           width: "100%",
           height: "400px",
           control:false,
           inserting:false,
           editing:false,
           rowSelectAction:'',
           insertAction:'',
           updateAction:'',
           deleteAction:'',
           columns:[]
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        return $(`
            <div id="${options.id}">
            </div>
        `);
    }

    scriptRuntime(el, options) {
        let columns = options.columns.map(obj => ({...obj}));

        if(options.control) {
            columns.push({ type: "control", editButton: true, width: 30, modeSwitchButton: false });
        }

        let rowClickEvent = function(args) {
            if(options.rowSelectAction) {
                VB.doAction(options.rowSelectAction, args);
            }
        }

        let gridOptions = {};
        gridOptions.inserting = options.inserting;
        gridOptions.editing = options.editing;

        if(gridOptions.insertAction) {
            gridOptions.onItemInserting = function(args) {
                VB.doAction(options.insertAction, args);
            }
        }

        if(gridOptions.updateAction) {
            gridOptions.onItemUpdated = function(args) {
                VB.doAction(options.updateAction, args);
            }
        }

        if(gridOptions.deleteAction) {
            gridOptions.onItemDeleting = function(args) {
                VB.doAction(options.deleteAction, args);
            }
        }

        this.grid.init(
            options.id,
            options.width,
            options.height,
            columns,rowClickEvent,
            gridOptions
            );
    }

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let el = `
            <div id="${options.id}" class="component vb-item" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                Grid [${options.id}]
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
        $rowId.append(this.optionPanel.input('id',{label:'ID', size:'col-3'}));
        $panel.append($rowId);

        let $rowToggle = this.optionPanel.row();
        $rowToggle.append(this.optionPanel.toggle('control',{label:'Control 사용', size:'col-4'}));
        $rowToggle.append(this.optionPanel.toggle('inserting',{label:'생성버튼 사용', size:'col-4'}));
        $rowToggle.append(this.optionPanel.toggle('editing',{label:'변경버튼 사용', size:'col-4'}));
        $panel.append($rowToggle);

        let $rowAction = this.optionPanel.row();
        $rowAction.append(this.optionPanel.input('row-select-action',{label:'row 선택 action', size:'col-4'}));
        $panel.append($rowAction);

        let $rowAction2 = this.optionPanel.row();
        $rowAction2.append(this.optionPanel.input('insert-action',{label:'생성 action', size:'col-4'}));
        $rowAction2.append(this.optionPanel.input('update-action',{label:'변경 action', size:'col-4'}));
        $rowAction2.append(this.optionPanel.input('delete-action',{label:'삭제 action', size:'col-4'}));
        $panel.append($rowAction2);

        let $rowGrid = this.optionPanel.row();
        $rowGrid.append(this.optionPanel.input('width',{label:'넓이', size:'col-6'}));
        $rowGrid.append(this.optionPanel.input('height',{label:'높이', size:'col-6'}));
        $rowGrid.append(this.optionPanel.button('column-setting',{label:'컬럼 설정', btnLabel:'설정',size:'col-12', icon:'fas fa-cog'}));
        $panel.append($rowGrid);
    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.check('control',options.control);
        this.optionPanel.check('inserting',options.inserting);
        this.optionPanel.check('editing',options.editing);
        this.optionPanel.setValue('row-select-action',options.rowSelectAction);
        this.optionPanel.setValue('insert-action',options.insertAction);
        this.optionPanel.setValue('update-action',options.updateAction);
        this.optionPanel.setValue('delete-action',options.deleteAction);
        this.optionPanel.setValue('width',options.width);
        this.optionPanel.setValue('height',options.height);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('id',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.inputEvent('width',(e) => {
            let value = $(e.target).val();
            this.optionPanel.changeOptionValue($el, options, 'width', value);
            $el.css('width', value);
        });

        this.optionPanel.inputEvent('height',(e) => {
            let value = $(e.target).val();
            this.optionPanel.changeOptionValue($el, options, 'height', value);
            $el.css('height', value);
        });

        this.optionPanel.clickEvent('control',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'control', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('inserting',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'inserting', $(e.target).is(':checked'));
        });

        this.optionPanel.clickEvent('editing',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'editing', $(e.target).is(':checked'));
        });

        this.optionPanel.inputEvent('row-select-action',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'rowSelectAction', $(e.target).val());
        });

        this.optionPanel.inputEvent('insert-action',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'insertAction', $(e.target).val());
        });

        this.optionPanel.inputEvent('update-action',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'updateAction', $(e.target).val());
        });

        this.optionPanel.inputEvent('delete-action',(e) => {
            this.optionPanel.changeOptionValue($el, options, 'deleteAction', $(e.target).val());
        });

        this.optionPanel.clickEvent('column-setting',(e) => {
            const self = this;
            App.modalPopup.open('/console/view-jsgrid-column',{title:'컬럼설정 팝업',size:"modal-xl",messageId:'JSGRID_COLUMN_REQUEST'},{columns:options.columns});
            App.modalPopup.receiveParam('JSGRID_COLUMN_RESULT',function(data){
                if(data.columns) {
                    self.optionPanel.changeOptionValue($el, options, 'columns', data.columns);
                }
            });
        });
    }
}