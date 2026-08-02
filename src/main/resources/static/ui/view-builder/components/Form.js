class Form extends ViewObject {
    constructor(optionPanel) {
        super(optionPanel);
        this.optionPanel = optionPanel;
    }

    componentId() {
        return 'form';
    }

    componentOptions() {
        return {
            id:this.componentId() + super.getComponentIdNumber(),
            dataProvider:''
       };
    }

/* =======================================
 * Runtime Component Setting
 * ======================================= */
    renderRuntime(options, children) {
        let el = $(`
            <form id="${options.id}" class="form" dataProvider="${options.dataProvider}">
            </form>
        `);

        if (children) {
            el.append(children);
        }

        return el;
    }

    scriptRuntime(el, options) {
        // 폼 이벤트 생성
        $("#"+options.id).attr('dataProvider',options.dataProvider)
            .on('clearData', function(){

            })
            .on('getData', function(){
                return null;
            })
            .on('setData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
            })
            .on('newData', function(e, data){
            })
            .on('bindData', function(e, data){
                VB.globalVariable.setMessage(options.dataProvider, data);
            });
    }

/* =======================================
 * Builder Component Setting
 * ======================================= */
    renderBuilder(options) {
        let el = `
            <form id="${options.id}" class="component form vb-item vb-container" data-type="${this.componentId()}">
                ${super.componentDeleteBtn()}
                <div style="text-align: center;width:100%;">Form</div>
            </form>
        `;

        return $(el);
    }

    styleBuilder() {
        return `
            .vb-item[data-type="${this.componentId()}"] {
                padding: 10px;
                min-height: 80px;
                height: auto;
                border: 1px solid #ddd;
                background-color: #ffffff;
                border-radius: 8px;
            }
        `;
    }

    componentDropConfig($componentEl) {
        return [{
            element: $componentEl,
            sortable: true
        }]
    }

    afterAddComponent(componentFactory, $el, $componentEl) {
        super.addComponentByType(componentFactory, 'row', $componentEl);
    }

/* =======================================
 * Option Panel Setting
 * ======================================= */
    optionPanelView($panel, options) {
        $panel.append(this.optionPanel.input('component-id',{label:'컴포넌트명', size:'col-6', enabled:false}));
        $panel.append(this.optionPanel.input('id',{label:'ID', size:'col-3'}));
        $panel.append(this.optionPanel.input('dataProvider',{label:'dataProvider', size:'col-7'}));
        $panel.append(this.optionPanel.button('row-add',{label:'Form 내용', size:'col-12', btnLabel:'행 추가',icon:'fas fa-plus'}));

    }

    optionPanelScript($el, options) {
        this.optionPanel.setValue('component-id',this.componentId());
        this.optionPanel.setValue('id',options.id);
        this.optionPanel.setValue('dataProvider',options.dataProvider);
    }

    optionPanelEvent($el, options, componentFactory) {
        this.optionPanel.inputEvent('id',(e) => {
            super.changeOptionValue($el, options, 'id', $(e.target).val());
        });

        this.optionPanel.inputEvent('dataProvider',(e) => {
            super.changeOptionValue($el, options, 'dataProvider', $(e.target).val());
        });

        this.optionPanel.clickEvent('row-add',(e) => {
            super.addComponentByType(componentFactory, 'row', $el);
        });
    }
}