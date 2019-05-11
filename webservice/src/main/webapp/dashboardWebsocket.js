var wsRealtime;
var wsHistory;
var pathRealtime = "ws://" + location.hostname + (location.port ? ":" + location.port: "") + "/webservice/websocket/realtime/dashboard";
var pathHistory = "ws://" + location.hostname + (location.port ? ":" + location.port: "") + "/webservice/websocket/history";

function connectRealtime()
{    
    wsRealtime = new WebSocket(pathRealtime);
    
    wsRealtime.onmessage = function(event)
    {
        var receivedObject = JSON.parse(event.data);
        var measurementString = receivedObject.description;
        measurementString += "<br>" + receivedObject.dateTime;
    
        if (receivedObject.hasOwnProperty("temperature"))
        {
            measurementString += "<br>Temperature: " + receivedObject.temperature + " °C";
        }

        if (receivedObject.hasOwnProperty("humidity"))
        {
            measurementString += "<br>Humidity: " + receivedObject.humidity + " %";
        }

        if (receivedObject.hasOwnProperty("radiation"))
        {
            measurementString += "<br>Radiation: " + receivedObject.radiation + " µSv/h";
        }

        if (receivedObject.hasOwnProperty("light"))
        {
            measurementString += "<br>Light: " + receivedObject.light + " lux";
        }

        document.getElementById(receivedObject.mac).innerHTML = measurementString;
    };
}

function connectHistory()
{
    console.log("Connecting to history websocket...");
    wsHistory = new WebSocket(pathHistory);
    console.log("Connected to history websocket.");

    wsHistory.onmessage = function(event)
    {
        document.getElementById("history").innerHTML = event.data;
        console.log(event.data);
    };
}