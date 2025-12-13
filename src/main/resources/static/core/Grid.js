class Grid {
    constructor() {}

      setData(_id, _data) {
          $("#" + _id).jsGrid("option", "data", _data);
      }

      getData(_id) {
          return $("#" + _id).jsGrid("option", "data");
      }

      insertItem(_id, data) {
          $("#"+_id).jsGrid("insertItem", data);
      }

        enableRowDrag(id, options) {
            const $tbody = $("#"+id+" .jsgrid-grid-body tbody");

            let initData = {
                helper: function (e, ui) {
                    ui.children().each(function () {
                        $(this).width($(this).width());
                    });
                    return ui;
                },
                update:function() {
                    const newData = [];

                    $("#"+id+" .jsgrid-grid-body tbody tr").each(function () {
                        newData.push($(this).data("JSGridItem"));
                    });

                    $("#"+id).jsGrid("option", "data", newData);

                    if(options.updateEvent != undefined) {
                        options.updateEvent();
                    }
                }
            }

            $tbody.sortable(initData);
        }

      init(_id, _width, _height, _fields, _rowClick, _options) {
        let initData = {
              height: _height,
              width: _width,
              sorting: false,
              paging: false,
              editing: false,
              inserting: false,
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
          }

        if(_options != undefined) {
            if(_options.sorting != undefined) {
                initData.sorting = _options.sorting;
            }

            if(_options.paging != undefined) {
                initData.paging = _options.paging;
            }

            if(_options.editing != undefined) {
                initData.editing = _options.editing;
            }

            if(_options.inserting != undefined) {
                initData.inserting = _options.inserting;
            }

            if(_options.onRefreshed != undefined) {
                initData.onRefreshed = _options.onRefreshed;
            }

            if(_options.loadData != undefined) {
                initData.controller = initData.controller || {};
                initData.controller.loadData = _options.loadData;
            }
        }

        $("#" + _id).jsGrid(initData);
      }
}

window.App = window.App || {};
window.App.grid = new Grid();