var ws;

function connect()
{    
    console.log("Connecting...");
    ws = new WebSocket("ws://localhost:8080/websocketTest/device/0");
    console.log("Connected!");
    
    ws.onmessage = function(event) {
        document.getElementById("output").innerHTML = event.data;
        console.log(event.data);
    };
}
