class Sheet {
    // Sheet.initSheet(sheetId, width, height, setting, columnInfo);
    static initSheet(sheetId, width, height, setting, columnInfo) {
        const sheet = new Sheet(sheetId, width, height, setting, columnInfo);
        window[sheetId] = sheet;
    }

    constructor(sheetId, width, height, setting, columnInfo) {
        window.SheetManager = window.SheetManager || {};
        this.agGrid = agGrid;

        this.SHEET_STATUS = {
            CREATE: 'C',
            UPDATE: 'U',
            DELETE: 'D'
        };

        this.COMBO_SHEET_STATUS = [
           {code:'', name:''},
           {code:this.SHEET_STATUS.CREATE, name:'입력'},
           {code:this.SHEET_STATUS.UPDATE, name:'수정'},
           {code:this.SHEET_STATUS.DELETE, name:'삭제'}
        ];

        this.init(sheetId, width, height, setting, columnInfo);
    }

    init(sheetId, width, height, setting, columnInfo) {
        // element 설정
        const sheetEl = $("#"+sheetId);
        sheetEl.addClass('ag-theme-quartz');
        sheetEl.css('width', width);
        sheetEl.css('height', height);

        const columns = [];

        // 시트 옵션
        if(setting.useDnd != undefined) {
            columns.push({field:'_dnd', label:'', type:'text', width:50, hide:!setting.useDnd, editable: false, align:'center', required:false, rowDrag: true});
        }

        if(setting.useSeq != undefined) {
            columns.push({field:'_seq', label:'번호', type:'text', width:60, hide:!setting.useSeq, editable: false, align:'center', required:false});
        }

        if(setting.useStatus != undefined) {
            columns.push({field:'_status', label:'상태', type:'combo', width:60, hide:!setting.useStatus, editable: false, align:'center', required:false,comboCodes:this.COMBO_SHEET_STATUS});
        }

        if(setting.useDelete != undefined) {
            columns.push({field:'_delete', label:'삭제', type:'check', width:60, hide:!setting.useDelete, editable: true, align:'center', required:false});
        }

        $.merge(columns, columnInfo);

        // 컬럼 변환
        const columnDefs = [];
        columns.forEach((column, index) => {
            const columnDef = {};
            if(column.type == 'check') {
                columnDef.cellDataType = 'boolean';
            }
            else if(column.type == 'combo') {
                const comboCodes = (column.comboCodes) ? column.comboCodes : [];

                columnDef.singleClickEdit = true;
                columnDef.cellEditorParams = {
                    values: comboCodes.map(item => item.code)
                }

                columnDef.valueFormatter = (params) => {
                    return this.getCodeLabel(comboCodes, params.value);
                }

                if(column.editable) {
                    columnDef.cellEditor = 'agSelectCellEditor'; // 편집 모드 시 콤보박스
                }
            }
            else if(column.type == 'date') {
                columnDef.cellDataType = 'dateString';
            }
            else {
                columnDef.cellDataType = column.type;
            }

            columnDef.field = column.field;
            columnDef.headerName = column.label;
            columnDef.width = column.width;
            columnDef.editable = column.editable;
            columnDef.cellStyle = {};
            columnDef.headerClass = 'ag-center-header';

            if(column.type == 'check') {
                columnDef.cellClass = 'align-'+column.align;
            }
            else {
                columnDef.cellStyle.textAlign = column.align;
            }

            if(column.required) {
                columnDef.headerClass = columnDef.headerClass + ' required-header';
            }

            if(column.hide != undefined) {
                columnDef.hide = column.hide;
                if(column.hide) {
                    columnDef.suppressColumnsToolPanel = true; // 컬럼 선택 패널에서도 숨김
                }
            }

            if(column.rowDrag != undefined) {
                columnDef.rowDrag = column.rowDrag;
            }

            const isLast = index === columns.length - 1;
            if(isLast) {
                //columnDef.flex = 1; // 마지막 컬럼은 크기 확장
            }

            columnDefs.push(columnDef);
        });

        // 시트 옵션 설정
        const originalDataMap = new WeakMap();

        const gridOptions = {
          columnDefs: columnDefs,
          rowData: [],
          stopEditingWhenCellsLoseFocus: true,
          localeText: {
            noRowsToShow: '조회된 데이터가 없습니다.'
          },
          rowSelection: 'single',
          //suppressRowClickSelection: true,
          onCellValueChanged: (event) => {
            const current = event.data;

            const original = originalDataMap.get(current);

            const changed = this.isChanged(current, original);

            if (current._delete) {
                // 신규 입력 row면 실제 삭제
                if (current._status === this.SHEET_STATUS.CREATE) {
                    event.api.applyTransaction({
                        remove: [current]
                    });

                    return;
                }

              current._status = this.SHEET_STATUS.DELETE;
            }
            else {
                if (current._status === this.SHEET_STATUS.CREATE) {
                    current._status = this.SHEET_STATUS.CREATE;
                }
                else {
                    current._status = changed ? this.SHEET_STATUS.UPDATE : '';
                }
            }

            event.api.refreshCells({
              rowNodes: [event.node],
              columns: ['_status']
            });
          }
        };

        // 드래그 앤 드랍 사용
        if(setting.useDnd != undefined) {
            gridOptions.animateRows = true;
            gridOptions.rowDragManaged = true;
        }

        // 시트 생성
       const sheet = this.agGrid.createGrid(document.querySelector('#'+sheetId), gridOptions);

       sheet.hideOverlay();

       this.sheetId = sheetId;
       this.setSheetMetadata(sheetId, {sheet:sheet, originalDataMap:originalDataMap, columns:columns});

       return sheet;
    }

    getSheetData() {
        const rows = [];
        this.getSheetObj().forEachNode(node => {
            rows.push(node.data);
        });

        return rows;
    }

    setSheetData(data) {
        const newRowData = [];

        let seq = 1;
        data.forEach(row => {
            const newRow = {};
            const columns = this.getSheetColumns();
            for(const column of columns) {
                newRow[column.field] = row[column.field];
            }

            newRow._seq = seq;
            newRow._delete = (row._delete) ? row._delete : false;
            newRow._status = (row._status) ? row._status : '';

            newRowData.push(newRow);

            this.getSheetOriginalData().set(newRow, structuredClone(newRow));
            seq++;
        });

        this.getSheetObj().setGridOption('rowData', newRowData);

        if (data.length === 0) {
            this.getSheetObj().showNoRowsOverlay();
        }
        else {
            this.getSheetObj().hideOverlay();
        }
    }

    addRowData(data) {
        if(data == undefined) {
            data = {};
        }

        const selectedNodes = this.getSheetObj().getSelectedNodes();

        const newRow = this.initData();
        newRow._status = this.SHEET_STATUS.CREATE;
        newRow._delete = false;
        newRow._seq = this.getSheetObj().getDisplayedRowCount() + 1;

        for(const key in data) {
            newRow[key] = data[key];
        }

        // 기본은 마지막
        let addIndex = this.getSheetObj().getDisplayedRowCount();

        // 선택된 행이 있으면 그 다음 위치
        if (selectedNodes.length > 0) {
            addIndex = selectedNodes[0].rowIndex + 1;
        }

        const result = this.getSheetObj().applyTransaction({
            add: [newRow],
            addIndex: addIndex
        });

        // 추가된 row 선택
        const addedRowNode = result.add[0];

        addedRowNode.setSelected(true);
    }

    getSaveData() {
        return [];
    }

    getSelectedRowData() {
        return this.getSheetObj().getSelectedRows()[0];
    }

    initData() {
        const columns = this.getSheetColumns();
        const data = {};
        for(const column of columns) {
            if(column.type == 'check') {
                data[column.field] = false;
            }
            else {
                data[column.field] = null;
            }
        }

        return data;
    }

    getSheetObj() {
        return window.SheetManager[this.sheetId].sheet;
    }

    getSheetOriginalData() {
        return window.SheetManager[this.sheetId].originalDataMap;
    }

    getSheetColumns() {
        return window.SheetManager[this.sheetId].columns;
    }

    setSheetMetadata(sheetId, data) {
        window.SheetManager[sheetId] = {};
        window.SheetManager[sheetId].originalDataMap =  data.originalDataMap;
        window.SheetManager[sheetId].columns =  data.columns;
        window.SheetManager[sheetId].sheet = data.sheet;
    }

// *****************************************
// Utils
// *****************************************
    isChanged(current, original) {
        // 신규 row는 비교 대상 아님
        if (current._status === this.SHEET_STATUS.CREATE) {
            return false;
        }

        // 원본 없으면 변경으로 처리
        if (!original) {
            return true;
        }

        const ignoreFields = ['_seq', '_status', '_delete'];
console.log('current',current);
console.log('original',original);
        return Object.keys(current).some(key => {

            if (ignoreFields.includes(key)) {
                return false;
            }
            return current[key] !== original[key];
        });
    }

    getCodeLabel(comboCode, code) {
        const found = comboCode.find(
            item => item.code === code
        );

        return found ? found.name : '';
    }
}