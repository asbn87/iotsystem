var wsRealtime;
var wsHistory;

function connectRealtime()
{    
    wsRealtime = new WebSocket("ws://localhost:8080/webservice/websocket/realtime/dashboard");
    
    wsRealtime.onmessage = function(event)
    {
        document.getElementById("realtime").innerHTML = event.data;
        console.log(event.data);
    };
}

function connectHistory()
{
    console.log("Connecting to history websocket...");
    wsHistory = new WebSocket("ws://localhost:8080/webservice/websocket/history");
    console.log("Connected to history websocket.");

    wsHistory.onmessage = function(event)
    {
        document.getElementById("history").innerHTML = event.data;
        console.log(event.data);
    };
}