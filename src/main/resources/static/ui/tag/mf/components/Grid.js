import { AbstractComponents } from './AbstractComponents.js';

export class Grid extends AbstractComponents {
    render() {
        const id = super.getId();
        const width = this.getAttribute('width');
        const height = this.getAttribute('height');
        const control = this.getAttribute('control');
        const inserting = this.getAttribute('inserting');
        const editing = this.getAttribute('editing');

        let columns = this.createColumn(this.children);
        if(control && App.util.stringToBoolean(control)) {
            columns.push({ type: "control", editButton: true, width: 30, modeSwitchButton: false });
        }

        let options = {};
        if(inserting) {
            options.inserting = App.util.stringToBoolean(inserting);
        }

        if(editing) {
            options.inserting = App.util.stringToBoolean(editing);
        }

        App.grid.init(id, width, height, columns,
            function(args) {
                //TODO row 선택시 실행
            },
            options);

        const div = document.createElement('div');
        div.id = id;

        while (this.firstChild) div.appendChild(this.firstChild);
        this.replaceWith(div);
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
            column.width = child.getAttribute("width");

            this.setOptionColumn(child, column, 'editing');
            this.setOptionColumn(child, column, 'inserting');

            columns.push(column);
        }

        return columns;
    }

    setOptionColumn(tag, column, columnId) {
        let value = tag.getAttribute(columnId);
        if(value) {
            column[columnId] = value;
        }
    }
}