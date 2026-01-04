import { ViewObject } from './ViewObject.js';

export class Grid extends ViewObject {
    constructor(grid) {
        super();
        this.grid = grid;
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(data, children) {
        return $(`
            <div id="${data.id}">
            </div>
        `);
    }

    scriptRuntime(el, initQueue, data) {
        initQueue.push(() => {
            let columns = data.columns;
            if(data.control) {
                columns.push({ type: "control", editButton: true, width: 30, modeSwitchButton: false });
            }

            let rowClickEvent = function(args) {
                if(data.rowSelectAction) {
                    doAction(data.rowSelectAction, args);
                }
            }

            let options = {};
            options.inserting = data.inserting;
            options.editing = data.editing;

            this.grid.init(data.id, data.width,data.height,columns,rowClickEvent,options);
        });
    }

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(id, options) {
        let el = `
            <div id="${id}" class="component vb-item" data-type="grid">
                ${super.componentDeleteBtn()}
                Grid
            </div>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="grid"] {
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

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('grid');

        let options = {
            id:'grid' + super.getComponentIdNumber('grid'),
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
        }


        $el.append(this.createComponent(options.id, options, componentFactory));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);
        super.opComponent.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        let $rowId = super.opComponent.row();
        $rowId.append(super.opComponent.input('grid-id',{label:'ID', size:'col-3'}));
        $panel.append($rowId);

        let $rowToggle = super.opComponent.row();
        $rowToggle.append(super.opComponent.toggle('grid-control',{label:'Control 사용', size:'col-4'}));
        $rowToggle.append(super.opComponent.toggle('grid-inserting',{label:'생성버튼 사용', size:'col-4'}));
        $rowToggle.append(super.opComponent.toggle('grid-editing',{label:'변경버튼 사용', size:'col-4'}));
        $panel.append($rowToggle);

        let $rowAction = super.opComponent.row();
        $rowAction.append(super.opComponent.input('grid-row-select-action',{label:'row 선택 action', size:'col-4'}));
        $panel.append($rowAction);

        let $rowAction2 = super.opComponent.row();
        $rowAction2.append(super.opComponent.input('grid-insert-action',{label:'생성 action', size:'col-4'}));
        $rowAction2.append(super.opComponent.input('grid-update-action',{label:'변경 action', size:'col-4'}));
        $rowAction2.append(super.opComponent.input('grid-delete-action',{label:'삭제 action', size:'col-4'}));
        $panel.append($rowAction2);

        let $rowGrid = super.opComponent.row();
        $rowGrid.append(super.opComponent.input('grid-width',{label:'넓이', size:'col-6'}));
        $rowGrid.append(super.opComponent.input('grid-height',{label:'높이', size:'col-6'}));
        $panel.append($rowGrid);

        $panel.append($(`
            <div class="form-group col-12">
               <label for="grid-columns">컬럼 설정</label>
               <div style="display: flex;justify-content: flex-end;"><a id="grid-create-column" style="cursor: pointer;">+create column</a></div>
               <div id="grid-columns"></div>
            </div>
        `));
    }

    optionPanelScript($el, options) {
        $('#grid-id').val(options.id);
        $('#grid-control').prop('checked',options.control);
        $('#grid-inserting').prop('checked',options.inserting);
        $('#grid-editing').prop('checked',options.editing);
        $('#grid-row-select-action').val(options.rowSelectAction);
        $('#grid-insert-action').val(options.insertAction);
        $('#grid-update-action').val(options.updateAction);
        $('#grid-delete-action').val(options.deleteAction);
        $('#grid-width').val(options.width);
        $('#grid-height').val(options.height);

        this.grid.init('grid-columns', "100%","600px",
            [
                {width: 30, align: "center",
                    itemTemplate: function () {
                      return "<span class='drag-handle'>☰</span>";
                    }
                },
                {type: "control", editButton: false, width: 30, modeSwitchButton: false },
                {name: "name", title:"필드", type: "text", width: 100 },
                {name: "title", title:"컬럼명", type: "text", width: 100 },
                {name: "width", title:"크기", type: "text", width: 80 },
                {name: "type", title:"타입", type: "text", width: 100 }
            ],
            function(args) {},
            {
                inserting:false,
                editing:true,
                onRefreshed: () => {
                    this.grid.enableRowDrag('grid-columns', {
                        updateEvent: () => {
                            options.columns = this.grid.getData('grid-columns');
                            super.opComponent.setOptions($el, options);
                        }
                    });
                },
                onItemUpdated:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.opComponent.setOptions($el, options);
                },
                onItemInserting:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.opComponent.setOptions($el, options);
                },
                onItemDeleting:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.opComponent.setOptions($el, options);
                }
            }
        );

        this.grid.setData('grid-columns', options.columns);
    }

    optionPanelEvent($el, options, componentFactory) {
        super.opComponent.inputEvent('grid-id',(e) => {
            super.opComponent.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        super.opComponent.inputEvent('grid-width',(e) => {
            let value = $(e.target).val();
            super.opComponent.changeOptionValue($el, options, 'width', value);
            $el.css('width', value);
        });

        super.opComponent.inputEvent('grid-height',(e) => {
            let value = $(e.target).val();
            super.opComponent.changeOptionValue($el, options, 'height', value);
            $el.css('height', value);
        });

        super.opComponent.clickEvent('grid-create-column',(e) => {
            this.grid.insertItem("grid-columns", {
                name: "Field",
                title: "Label",
                width: 100,
                type: "text"
            });

            super.opComponent.changeOptionValue($el, options, 'columns', this.grid.getData('grid-columns'));
        });

        super.opComponent.clickEvent('grid-control',(e) => {
            super.opComponent.changeOptionValue($el, options, 'control', $(e.target).is(':checked'));
        });

        super.opComponent.clickEvent('grid-inserting',(e) => {
            super.opComponent.changeOptionValue($el, options, 'inserting', $(e.target).is(':checked'));
        });

        super.opComponent.clickEvent('grid-editing',(e) => {
            super.opComponent.changeOptionValue($el, options, 'editing', $(e.target).is(':checked'));
        });

        super.opComponent.inputEvent('grid-row-select-action',(e) => {
            super.opComponent.changeOptionValue($el, options, 'rowSelectAction', $(e.target).val());
        });

        super.opComponent.inputEvent('grid-insert-action',(e) => {
            super.opComponent.changeOptionValue($el, options, 'insertAction', $(e.target).val());
        });

        super.opComponent.inputEvent('grid-update-action',(e) => {
            super.opComponent.changeOptionValue($el, options, 'updateAction', $(e.target).val());
        });

        super.opComponent.inputEvent('grid-delete-action',(e) => {
            super.opComponent.changeOptionValue($el, options, 'deleteAction', $(e.target).val());
        });
    }
}