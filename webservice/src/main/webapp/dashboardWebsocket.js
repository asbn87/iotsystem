var wsRealtime;
var wsHistory;
var pathRealtime = "ws://" + location.hostname + (location.port ? ":" + location.port: "") + "/webservice/websocket/realtime/dashboard";
var pathHistory = "ws://" + location.hostname + (location.port ? ":" + location.port: "") + "/webservice/websocket/history";

var dhtTempCtx = document.getElementById('dht-temperature').getContext('2d');
var dhtHumCtx = document.getElementById('dht-humidity').getContext('2d');
var dallasTempCtx = document.getElementById('dallas-temperature').getContext('2d');
var radCtx = document.getElementById('radiation').getContext('2d');
var lightCtx = document.getElementById('light').getContext('2d');

var dhtTemperatureArray = [];
var dhtHumidityArray = [];
var dhtTimeArray = [];
var dallasTemperatureArray = [];
var dallasTimeArray = [];
var lightArray = [];
var lightTimeArray = [];
var radiationArray = [];
var radiationTimeArray = [];

var dhtTempDataColor = '#50EFDA';
var dhtHumDataColor = '#692D8D';
var dallasTempDataColor = '#D564AB';         
var radDataColor = '#FDB592';
var lightDataColor = '#FEE29E';

function updateHistory()
{
    var dhtTempChart = new Chart(dhtTempCtx,
    {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: 
        {
            labels: dhtTimeArray,
            datasets: 
            [{
                data: dhtTemperatureArray,
                type: 'line',
                pointRadius: 1,
                fill: false,
                lineTension: 0,
                borderWidth: 2,
                backgroundColor: dhtTempDataColor,
                borderColor: dhtTempDataColor,
                pointBorderColor: dhtTempDataColor,
                pointBackgroundColor: dhtTempDataColor,
                pointHoverBackgroundColor: dhtTempDataColor,
                pointHoverBorderColor: dhtTempDataColor
            }]
        },

        // Configuration options go here
        options: 
        {
            animation: false,
            scales:
            {
                yAxes: [{
                    gridLines: {
                      display: false,
                      color: '#FFF',
                    },
                    ticks: {
                        beginAtZero: true,
                        fontColor: '#FFF',
                        min: 0,
                        max: 40
                    }
                }],
              xAxes: [{
                    ticks: {
                        fontColor: '#FFF'
                    }
                }]
            },
            legend: 
            { 
                display: false 
            },
            title: 
            {
                display: true,
                text: 'Temperature( °C )',
                fontColor: '#FFF'
            }
        }
    });

    var dhtHumChart = new Chart(dhtHumCtx, 
    {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: 
        {
            labels: dhtTimeArray,
            datasets: 
            [{
                data: dhtHumidityArray,
                type: 'line',
                pointRadius: 0,
                fill: true,
                lineTension: 0,
                borderWidth: 2,
                backgroundColor: dhtHumDataColor,
                borderColor: dhtHumDataColor,
                pointBorderColor: dhtHumDataColor,
                pointBackgroundColor: dhtHumDataColor,
                pointHoverBackgroundColor: dhtHumDataColor,
                pointHoverBorderColor: dhtHumDataColor
            }]
        },

        // Configuration options go here
        options: 
        {
            animation: false,
            scales:
            {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontColor: '#FFF',
                        min: 0,
                        max: 100
                    }
                }],
              xAxes: [{
                    ticks: {
                        fontColor: '#FFF'
                    }
                }]
            },
            legend: 
            { 
                display: false 
            },
            title: 
            {
                display: true,
                text: 'Humidity( % )',
                fontColor: '#FFF'
            }
        }
    });

    var dallasTempChart = new Chart(dallasTempCtx, 
    {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: 
        {
            labels: dallasTimeArray,
            datasets: 
            [{
                data: dallasTemperatureArray,
                fontColor: '#FFF',
                type: 'line',
                pointRadius: 0,
                fill: true,
                lineTension: 0,
                borderWidth: 2,
                backgroundColor: dallasTempDataColor,
                borderColor: dallasTempDataColor,
                pointBorderColor: dallasTempDataColor,
                pointBackgroundColor: dallasTempDataColor,
                pointHoverBackgroundColor: dallasTempDataColor,
                pointHoverBorderColor: dallasTempDataColor
            }]
        },

        // Configuration options go here
        options: 
        {
            animation: false,
            scales:
            {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontColor: '#FFF',
                        min: 0,
                        max: 30
                    }
                }],
              xAxes: [{
                    ticks: {
                        fontColor: '#FFF'
                    }
                }]
            },
            legend: 
            {   
                display: false
            },
            title: 
            {
                display: true,
                text: 'Water Temperature( °C )',
                fontColor: '#FFF'
            }
        }
    });

    var radChart = new Chart(radCtx, 
    {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: 
        {
            labels: radiationTimeArray,
            datasets: 
            [{
                data: radiationArray,
                type: 'line',
                pointRadius: 0,
                fill: true,
                lineTension: 0,
                borderWidth: 2,
                backgroundColor: radDataColor,
                borderColor: radDataColor,
                pointBorderColor: radDataColor,
                pointBackgroundColor: radDataColor,
                pointHoverBackgroundColor: radDataColor,
                pointHoverBorderColor: radDataColor
            }]
        },

        // Configuration options go here
        options: 
        {
            animation: false,
            scales:
            {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontColor: '#FFF',
                        min: 0,
                        max: 1
                    }
                }],
              xAxes: [{
                    ticks: {
                        fontColor: '#FFF'
                    }
                }]
            },
            legend: 
            { 
                display: false 
            },
            title: 
            {
                display: true,
                text: 'Radiation( µSv/h )',
                fontColor: '#FFF'
            }
        }
    });                

    var lightChart = new Chart(lightCtx, 
    {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: 
        {
            labels: lightTimeArray,
            datasets: 
            [{
                data: lightArray,
                type: 'line',
                pointRadius: 0,
                fill: true,
                lineTension: 0,
                borderWidth: 2,
                backgroundColor: lightDataColor,
                borderColor: lightDataColor,
                pointBorderColor: lightDataColor,
                pointBackgroundColor: lightDataColor,
                pointHoverBackgroundColor: lightDataColor,
                pointHoverBorderColor: lightDataColor
            }]
        },

        // Configuration options go here
        options: 
        {
            animation: false,
            scales:
            {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontColor: '#FFF',
                        min: 0,
                        max: 5000
                    }
                }],
              xAxes: [{
                    ticks: {
                        fontColor: '#FFF'
                    }
                }]
            },
            legend: 
            { 
                display: false 
            },
            title: 
            {
                display: true,
                text: 'Light( lx )',
                fontColor: '#FFF'
            }
        }
    });                

    document.getElementById("dht-temperature").innerHTML = dhtTempChart;
    document.getElementById("dht-humidity").innerHTML = dhtHumChart;
    document.getElementById("dallas-temperature").innerHTML = dallasTempChart;
    document.getElementById("radiation").innerHTML = radChart;
    document.getElementById("light").innerHTML = lightChart;
}

function connectRealtime()
{    
    wsRealtime = new WebSocket(pathRealtime);
    
    wsRealtime.onmessage = function(event)
    {
        var receivedObject = JSON.parse(event.data);
    
        if(receivedObject.mac === "5C:CF:7F:F0:B5:10") 
        {
            document.getElementById("dht-temperature-value").innerHTML = receivedObject.temperature;
            document.getElementById("dht-humidity-value").innerHTML = receivedObject.humidity;
        }
        
        else if(receivedObject.mac === "A0:20:A6:05:EA:87")
        {
            document.getElementById("dallas-temperature-value").innerHTML = receivedObject.temperature;
        }
        
        else if(receivedObject.mac === "60:01:94:4C:0E:99")
        {
            document.getElementById("light-value").innerHTML = receivedObject.light;
        }
        
        else if(receivedObject.mac === "A0:20:A6:05:EA:E2")
        {
            document.getElementById("radiation-value").innerHTML = receivedObject.radiation;
        }
    };
}

function connectHistory()
{
    console.log("Connecting to history websocket...");
    wsHistory = new WebSocket(pathHistory);
    console.log("Connected to history websocket.");
    

    wsHistory.onmessage = function(event)
    {
        var receivedObject = JSON.parse(event.data);
        dhtTemperatureArray = [];
        dhtHumidityArray = [];
        dhtTimeArray = [];
        dallasTemperatureArray = [];
        dallasTimeArray = [];
        lightArray = [];
        lightTimeArray = [];
        radiationArray = [];
        radiationTimeArray = [];

        receivedObject.transporters.forEach(updateHistoryData);

        function updateHistoryData(item)
        {
            var year = item.time.time.date.year;
            var month = item.time.time.date.month;
            var day = item.time.time.date.day;
            var hour = item.time.time.time.hour;
            var minute = item.time.time.time.minute;
            var second = item.time.time.time.second;
            var time = hour + ":" + minute + ":" + second;
            
            if(item.device.mac === "5C:CF:7F:F0:B5:10")
            {
                dhtTemperatureArray.push(item.temperature.temperature_C);
                dhtHumidityArray.push(item.humidity.humidity_pct);
                dhtTimeArray.push(time);
            }
            
            else if(item.device.mac === "A0:20:A6:05:EA:87")
            {
                dallasTemperatureArray.push(item.temperature.temperature_C);
                dallasTimeArray.push(time);
            }
            
            else if(item.device.mac === "60:01:94:4C:0E:99")
            {
                lightArray.push(item.light.lux);
                lightTimeArray.push(time);
            }
            
            else if(item.device.mac === "A0:20:A6:05:EA:E2")
            {
                radiationArray.push(item.radiation.siverts_uSv);
                radiationTimeArray.push(time);
            }
        }

        updateHistory();
    };
}