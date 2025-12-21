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
            <div id="${id}" class="component vb-item vb-grid" data-type="grid">
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
                height: 300px;
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

    optionPanelView(options) {
        let html = ``;
        html += `<div class="row">`;
        html += super.optionInput('grid-width', '넓이', 'col-6', options.width);
        html += super.optionInput('grid-height', '높이', 'col-6', options.height);
        html += `</div>`;
        html += `
            <div class="form-group col-12">
               <label for="grid-columns">컬럼 설정</label>
               <div style="display: flex;justify-content: flex-end;"><a id="grid-create-column" style="cursor: pointer;">+create column</a></div>
               <div id="grid-columns"></div>
            </div>
        `;

        return html;
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
                            super.setOptions($el, options);
                        }
                    });
                },
                onItemUpdated:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.setOptions($el, options);
                },
                onItemInserting:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.setOptions($el, options);
                },
                onItemDeleting:(args) => {
                    options.columns = this.grid.getData('grid-columns');
                    super.setOptions($el, options);
                }
            }
        );

        this.grid.setData('grid-columns', options.columns);
    }

    optionPanelEvent($el, options, componentFactory) {
        $(document).off("click", "#grid-create-column").on("click", "#grid-create-column",  () => {
            this.grid.insertItem("grid-columns", {
                name: "Field",
                title: "Label",
                width: 100,
                type: "text"
            });

            options.columns = this.grid.getData('grid-columns');
            super.setOptions($el, options);
        });

        $("#grid-width").off("input").on("input",  (e) => {
            options.width = $(e.target).val();
            super.setOptions($el, options);
        });

        $("#grid-height").off("input").on("input",  (e) => {
            options.height =  $(e.target).val();
            super.setOptions($el, options);
        });
    }

    addComponent($el, componentFactory) {
        super.plusComponentIdNumber('grid');

        let options = {
            id:'grid' + super.getComponentIdNumber('grid'),
            width: "100%",
            height: "800px",
            columns:[]
        }


        $el.append(this.createComponent(options.id, options, componentFactory));
    }

    createComponent(id, options, componentFactory) {
        let $componentEl = this.component(id, options);
        super.setOptions($componentEl, options);

        return $componentEl;
    }

    dropComponent($el, componentFactory) {
    }
}