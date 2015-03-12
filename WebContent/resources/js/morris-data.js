$(function() {
	
	
          var options = {
              chart: {
                  renderTo: 'morris-area-chart',
                  type: 'line',
                  marginRight: 130,
                  marginBottom: 25
              },
              title: {
                  text: 'test',
                  x: -20 //center
              },
              subtitle: {
                  text: '',
                  x: -20
              },
              xAxis: {
                  categories: []
              },
              yAxis: {
                  title: {
                      text: 'test'
                  },
                  plotLines: [{
                      value: 0,
                      width: 1,
                      color: '#808080'
                  }]
              },
              tooltip: {
                  formatter: function() {
                          return '<b>'+ this.series.name +'</b><br/>'+
                          this.x +': '+ this.y;
                  }
              },
              legend: {
                  layout: 'vertical',
                  align: 'right',
                  verticalAlign: 'top',
                  x: -10,
                  y: 100,
                  borderWidth: 0
              },
              series: []
          }

          $.getJSON("http://localhost:8080/nepseData/web/testJsonMap", function(json) {
        	  options.xAxis.categories = json['category'];
        	  options.series[0] = {};
        	  options.series[0].name = json['name'];
        	  options.series[0].data = json['data'];
              chart = new Highcharts.Chart(options);
          });
	 
});
