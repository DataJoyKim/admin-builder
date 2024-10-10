class Sheet {
    constructor() {}

      setSheetData(_id, _data) {
          $("#" + _id).jsGrid("option", "data", _data);
      }

      initSheet(_id, _width, _height, _fields, _rowClick) {
        $("#" + _id).jsGrid({
            height: _height,
            width: _width,
            sorting: true,
            paging: true,
            inserting: false,
            editing: true,
            fields: _fields,
            data: [],
            rowClick: function(args) {
                _rowClick(args);
            }
        });
      }
}