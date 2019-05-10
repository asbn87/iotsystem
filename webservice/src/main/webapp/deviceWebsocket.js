var ws;
var temperatureValue = 0;
var humidityValue = 10;
var radiationValue = 0.05;
var lightValue = 10;

function connectTempsensor()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/00:A0:C9:14:C8:29");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function connectTempHum()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/00:AE:C9:14:C9:13");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function connectRadiation()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/55:BD:CC:14:FF:10");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function connectLight()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/33:BA:DD:04:EF:11");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function sendTemperature()
{   
    var msg = {
        description: "Temperature sensor",
        dateTime: "2019-05-10 10:37:05",
        temperature: temperatureValue++
      };

    ws.send(JSON.stringify(msg));
    console.log("Temperature message sent.");
}

function sendTempHum()
{   
    var msg = {
        description: "Temp/Hum sensor",
        dateTime: "2019-05-09 08:32:56",
        temperature: temperatureValue++,
        humidity: humidityValue++
      };

    ws.send(JSON.stringify(msg));
    console.log("Temp/Hum message sent.");
}

function sendRadiation()
{   
    var msg = {
        description: "Radiation sensor",
        dateTime: "2019-05-10 08:17:45",
        radiation: radiationValue += 0.01
      };

    ws.send(JSON.stringify(msg));
    console.log("Radiation message sent.");
}

function sendLight()
{   
    var msg = {
        description: "Light sensor",
        dateTime: "2019-05-08 16:47:33",
        light: lightValue += 10
      };

    ws.send(JSON.stringify(msg));
    console.log("Light message sent.");
}