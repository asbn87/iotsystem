var ws;

function connect()
{    
    ws = new WebSocket("ws://localhost:8080/webservice/websocket/realtime/dashboard");
    
    ws.onmessage = function(event) {
        document.getElementById("output").innerHTML = event.data;
        console.log(event.data);
    };
}
