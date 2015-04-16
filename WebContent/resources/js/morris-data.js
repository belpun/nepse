
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
          
          
        	    $("#updateButton").click(function() {
        	       console.log("update button");
        	       loadSpinner();
        	       updateCompany();
        	    });
        	    
        	    
        	    
        	    function updateCompany(){
        			var redirectUrl = 'http://localhost:8080/nepseData/web/company/' + companySymbol + '/update';
				$.ajax({
					type : "GET",
					url : redirectUrl,
					dataType : "json",
					context : this,

					success : function(response) {
						if(response.successful) {
							 location.reload();
							 console.log("update succesfull");
						}
						
						 console.log("update completed");
					}
				})};
				
				

				// none, bounce, rotateplane, stretch, orbit, 
				// roundBounce, win8, win8_linear or ios
				var current_effect = 'bounce'; // 

				function loadSpinner() {
					run_waitMe(current_effect);
				}
				
				function run_waitMe(effect){
				$('html').waitMe({

				//none, rotateplane, stretch, orbit, roundBounce, win8, 
				//win8_linear, ios, facebook, rotation, timer, pulse, 
				//progressBar, bouncePulse or img
				effect: 'bounce',

				//place text under the effect (string).
				text: 'Please Wait',

				//background for container (string).
				bg: 'rgba(255,255,255,0.7)',

				//color for background animation and text (string).
				color: '#000',

				//change width for elem animation (string).
				sizeW: '',

				//change height for elem animation (string).
				sizeH: '',

				// url to image
				source: ''

				});
				}
					
});
                    
