class Sheet {
    constructor() {}

      setSheetData(_id, _data) {
          $("#" + _id).jsGrid("option", "data", _data);
      }

      getSheetData(_id) {
          return $("#" + _id).jsGrid("option", "data");
      }

      initSheet(_id, _width, _height, _fields, _rowClick, _options) {
        let sorting = false;
        let paging = false;
        let editing = false;
        let inserting = false;

        if(_options != undefined) {
            if(_options.sorting != undefined) {
                sorting = _options.sorting;
            }

            if(_options.paging != undefined) {
                paging = _options.paging;
            }

            if(_options.editing != undefined) {
                editing = _options.editing;
            }

            if(_options.inserting != undefined) {
                inserting = _options.inserting;
            }
        }

        $("#" + _id).jsGrid({
            height: _height,
            width: _width,
            sorting: sorting,
            paging: paging,
            editing: editing,
            inserting: inserting,
            fields: _fields,
            data: [],
            rowClick: function(args) {
                _rowClick(args);
            },
            rowDoubleClick: function(args) {
                 $("#" + _id).jsGrid("editItem", args.item);
            },
            onItemUpdated: function(args) {
                if(_options != undefined && _options.onItemUpdated != undefined) {
                    _options.onItemUpdated(args);
                }
            },
            onItemInserting: function(args) {
                if(_options != undefined && _options.onItemInserting != undefined) {
                    _options.onItemInserting(args);
                }
            },
            onItemDeleting: function(args) {
                if(_options != undefined && _options.onItemDeleting != undefined) {
                    _options.onItemDeleting(args);
                }
            }
        });
      }
}