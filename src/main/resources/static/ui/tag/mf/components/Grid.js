import { AbstractComponents } from './AbstractComponents.js';

export class Grid extends AbstractComponents {
    render() {
        const id = super.getId();
        const width = this.getAttribute('width');
        const height = this.getAttribute('height');
        const control = this.getAttribute('control');
        const inserting = this.getAttribute('inserting');
        const editing = this.getAttribute('editing');
        const rowClickActionName = this.getAttribute('rowClickActionName');
        const insertActionName = this.getAttribute('insertActionName');
        const updateActionName = this.getAttribute('updateActionName');
        const deleteActionName = this.getAttribute('deleteActionName');

        let columns = this.createColumn(this.children);
        if(control && App.util.stringToBoolean(control)) {
            columns.push({ type: "control", editButton: true, width: 30, modeSwitchButton: false });
        }

        let options = {};

        if(inserting) {
            options.inserting = App.util.stringToBoolean(inserting);
        }

        if(editing) {
            options.editing = App.util.stringToBoolean(editing);
        }

        let rowClickEvent = function(args) {
            if(rowClickActionName) {
                let rowClickFunc = window[rowClickActionName];
                if(rowClickFunc) {
                    rowClickFunc(args);
                }
            }
        }

        if(insertActionName) {
            options.onItemInserting = function(args) {
                let insertFunc = window[insertActionName];
                if(insertFunc) {
                    insertFunc(args);
                }
            }
        }

        if(updateActionName) {
            options.onItemUpdated = function(args) {
                let updateFunc = window[updateActionName];
                if(updateFunc) {
                    updateFunc(args);
                }
            }
        }

        if(deleteActionName) {
            options.onItemDeleting = function(args) {
                let deleteFunc = window[deleteActionName];
                if(deleteFunc) {
                    deleteFunc(args);
                }
            }
        }

        // 렌더링 대상 생성
        const div = document.createElement('div');
        div.id = id;
        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);

        // 랜더링
        App.grid.init(id, width, height, columns,rowClickEvent,options);
    }

    createColumn(children) {
        let columns = new Array();

        for (const child of children) {
            let childTagName = child.tagName.toLowerCase();

            if(childTagName != super.getTagEl().gridColumn) {
                continue;
            }

            let column = {};
            column.name = child.getAttribute("id");
            column.title = child.getAttribute("label");
            column.type = child.getAttribute("type");

            let visible = child.getAttribute("visible");
            if(visible) column.visible = visible;

            let width = child.getAttribute("width");
            column.width = (width) ? width : '100px';

            let align = child.getAttribute("align");
            column.align = (align) ? align : 'center';

            let editing = child.getAttribute('editing');
            if(editing) column.editing = App.util.stringToBoolean(editing);

            let inserting = child.getAttribute('inserting');
            if(inserting) column.inserting = App.util.stringToBoolean(inserting);

            if(column.type == 'select') {
                let items = child.getAttribute('dataProvider');
                if(items) column.items = items;

                let valueField = child.getAttribute("valueField");
                column.valueField = (valueField) ? valueField : 'code';

                let textField = child.getAttribute("textField");
                column.textField = (textField) ? textField : 'name';
            }

            columns.push(column);
        }

        return columns;
    }
}