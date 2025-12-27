export class ViewDataLoader {
    constructor(layoutId) {
        this.layoutId = layoutId;
    }

    serializeComponent($el) {
        if(!$el.hasClass('vb-item')) {
            return;
        }

        let data = $el.data('options');
        data.type= $el.data("type");

        const children = this.findDirectLogicalChildren($el)
                        .map(childEl =>
                            this.serializeComponent(childEl)
                        )
                        .filter(Boolean);

        if (children.length > 0) {
            data.children = children;
        }

        return data;
    }

    findDirectLogicalChildren($el) {
        let result = [];
        const viewData = this;

        $el.children().each(function () {
            const $child = $(this);

            if ($child.hasClass('component')) {
                result.push($child);
            }
            else {
                result = result.concat(viewData.findDirectLogicalChildren($child));
            }
        });

        return result;
    }

    getData() {
        const root = $("#"+this.layoutId);
        const json = [];
        const viewData = this;

        root.children(".component").each(function() {
            json.push(viewData.serializeComponent($(this)));
        });

        return json;
    }
}