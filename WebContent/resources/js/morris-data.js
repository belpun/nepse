
$(function() {
	
          $(function () {
        	    $.getJSON('http://localhost:8080/nepseData/web/company/'+ companySymbol + '/candleStickChart', function (data) {

        	        // create the chart
        	        $('#candle-stick-chart').highcharts('StockChart', {


        	            rangeSelector : {
        	                selected : 1
        	            },

        	            title : {
        	                text : 'Candle Stick Chart'
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

	
          $(function () {
        	    $.getJSON('http://localhost:8080/nepseData/web/company/'+ companySymbol + '/companyClosingPrice', function (data) {

        	        // create the chart
        	        $('#stock-chart').highcharts('StockChart', {



        	            rangeSelector : {
        	                selected : 1
        	            },

        	            title : {
        	                text : 'Daily Price'
        	            },

        	            series : [{
        	                name : 'Daily Price',
        	                data : data,
        	                tooltip: {
        	                    valueDecimals: 2
        	                }
        	            }]
        	        });
        	    });
        	});
          
          $(function () {
        	    $.getJSON('http://localhost:8080/nepseData/web/company/'+ companySymbol +'/rsiCalculation', function (data) {

        	        // create the chart
        	        $('#morris-area-chart').highcharts('StockChart', {



        	            rangeSelector : {
        	                selected : 1
        	            },

        	            title : {
        	                text : '14 day RSI chart'
        	            },

        	            series : [{
        	            	type: 'areaspline',
        	                name : 'rsi',
        	                data : data,
        	                tooltip: {
        	                    valueDecimals: 2
        	                }
        	            }]
        	        });
        	    });
        	});
          
          $(function () {
        	  $.getJSON('http://localhost:8080/nepseData/web/company/' + companySymbol + '/simpleMovingAverage', function (data) {
        		  
        		  // create the chart
        		  $('#simpleMovingAverage-chart').highcharts('StockChart', {
        			  
        			  
        			  
        			  rangeSelector : {
        				  selected : 1
        			  },
        			  
        			  title : {
        				  text : 'Simple Moving Average(SMA) with 30 days'
        			  },
        			  
        			  series : [{
        				  type: 'areaspline',
        				  name : 'rsi',
        				  data : data,
        				  tooltip: {
        					  valueDecimals: 2
        				  }
        			  }]
        		  });
        	  });
          });
          
          $(function () {
        	  $.getJSON('http://localhost:8080/nepseData/web/company/' + companySymbol + '/exponentialMovingAverage', function (data) {
        		  
        		  // create the chart
        		  $('#exponentialMovingAverage-chart').highcharts('StockChart', {
        			  
        			  
        			  
        			  rangeSelector : {
        				  selected : 1
        			  },
        			  
        			  title : {
        				  text : 'Exponential Moving Average(EMA) with 30 days'
        			  },
        			  
        			  series : [{
        				  type: 'areaspline',
        				  name : 'rsi',
        				  data : data,
        				  tooltip: {
        					  valueDecimals: 2
        				  }
        			  }]
        		  });
        	  });
          });
          
});
                    
