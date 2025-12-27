import { ViewObject } from './ViewObject.js';

export class Grid extends ViewObject {
    constructor(grid) {
        super();
        this.grid = grid;
    }

    template(data, children) {
        return $(`
            <div id="${data.id}">
            </div>
        `);
    }

    script(el, initQueue, data) {
        initQueue.push(() => {
            let columns = data.columns;
            columns.push({ type: "control", editButton: true, width: 30, modeSwitchButton: false });
            let rowClickEvent = function(args) {}

            let options = {};
            options.inserting = false;
            options.editing = false;

            this.grid.init(data.id, data.width,data.height,columns,rowClickEvent,options);
        });
    }

    componentTemplate(id, options) {
        let el = `
            <div id="${id}" class="component vb-item" data-type="grid">
                ${super.componentDeleteBtn()}
                Grid
            </div>
        `;

        return $(el);
    }

    componentStyle() {
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

    optionPanelView($panel, options) {
        let $row1 = super.opComponent.row();
        $row1.append(super.opComponent.input('grid-id',{label:'ID', size:'col-3', value:options.id}));
        $panel.append($row1);

        let $row2 = super.opComponent.row();
        $row2.append(super.opComponent.input('grid-width',{label:'넓이', size:'col-6', value:options.width}));
        $row2.append(super.opComponent.input('grid-height',{label:'높이', size:'col-6', value:options.height}));
        $panel.append($row2);

        $panel.append($(`
            <div class="form-group col-12">
               <label for="grid-columns">컬럼 설정</label>
               <div style="display: flex;justify-content: flex-end;"><a id="grid-create-column" style="cursor: pointer;">+create column</a></div>
               <div id="grid-columns"></div>
            </div>
        `));
    }

    optionPanelScript($el, options) {
        let isInit = true;

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
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('grid');

        let options = {
            id:'grid' + super.getComponentIdNumber('grid'),
            width: "100%",
            height: "400px",
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
}