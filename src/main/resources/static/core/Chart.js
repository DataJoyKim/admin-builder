class Chart {

    /**
     * Chart 생성
     *
     * @param {string} id
     *        차트를 렌더링할 DOM Element의 ID
     *
     *        예:
     *        <div id="chart"></div>
     *
     *        new Chart("chart");
     */
    constructor(id) {
        this.element = document.getElementById(id);
        this.chart = echarts.init(this.element);
    }

    /**
     * ECharts 차트 옵션을 설정한다.
     *
     * @param {Object} option
     *        ECharts setOption 옵션
     *
     *        주요 옵션:
     *
     *        title
     *          - 차트 제목
     *
     *        tooltip
     *          - 마우스 hover 시 표시되는 정보
     *
     *        legend
     *          - 범례 설정
     *
     *        grid
     *          - 차트 영역 여백 설정
     *
     *        xAxis
     *          - X축 설정
     *
     *        yAxis
     *          - Y축 설정
     *
     *        series
     *          - 실제 차트 데이터 및 차트 타입
     *          - type: bar, line, pie 등
     *
     *        예:
     *        {
     *            title: {
     *                text: '매출 현황'
     *            },
     *            tooltip: {},
     *            xAxis: {
     *                type: 'category',
     *                data: ['1월', '2월', '3월']
     *            },
     *            yAxis: {
     *                type: 'value'
     *            },
     *            series: [{
     *                type: 'bar',
     *                data: [120, 200, 150]
     *            }]
     *        }
     */
    setOption(option) {
        this.chart.setOption(option);
    }

    /**
     * 차트 데이터를 설정한다.
     *
     */
    setData(data) {
        this.chart.setOption({series:data});
    }

    /**
     * 차트 크기를 다시 계산한다.
     *
     * @param {Object} [opts]
     *        ECharts resize 옵션
     *
     * @param {number} [opts.width]
     *        차트 너비
     *
     *        예:
     *        resize({ width: 500 })
     *
     * @param {number} [opts.height]
     *        차트 높이
     *
     *        예:
     *        resize({ height: 300 })
     *
     * @param {boolean} [opts.silent=false]
     *        resize 이벤트 발생 여부
     *
     *        true:
     *          resize 이벤트를 발생시키지 않음
     *
     *        false:
     *          resize 이벤트 발생
     */
    resize(opts) {
        this.chart.resize(opts);
    }
}