<!DOCTYPE html>
<html>
<head>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

<script language="javascript"> 
$(function () {
alert("test");
    $.getJSON('http://localhost:9999/nepseData/nepseData/service/historicData/MEGA', function (data) {

        // create the chart
        $('#container').highcharts('StockChart', {


            rangeSelector : {
                selected : 1
            },

            title : {
                text : 'AAPL Stock Price'
            },

            series : [{
                type : 'candlestick',
                name : 'AAPL Stock Price',
                data : data,
                dataGrouping : {
                    units : [
                        [
                            'week', // unit name
                            [1] // allowed multiples
                        ], [
                            'month',
                            [1, 2, 3, 4, 6]
                        ]
                    ]
                }
            }]
        });
    });
});

</script>

</head>

<body>

<script src="http://code.highcharts.com/stock/highstock.js"></script>
<script src="http://code.highcharts.com/stock/modules/exporting.js"></script>
<div id="container" style="height: 400px; min-width: 310px"></div>

</body>

</html>