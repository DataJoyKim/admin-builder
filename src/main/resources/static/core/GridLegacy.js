class GridLegacy {
    constructor(grid) {
        this.grid = grid;
    }

      setSheetData(_id, _data) {
          this.grid.setData(_id, _data);
      }

      getSheetData(_id) {
          return this.grid.getData(_id);
      }

      initSheet(_id, _width, _height, _fields, _rowClick, _options) {
        this.grid.init(_id, _width, _height, _fields, _rowClick, _options);
      }
}