var ws;

function connect()
{   
    var host = document.location.host;
    
    console.log("Connecting...");
    ws = new WebSocket("ws://" + host + "/websocketTest/device/1");
    console.log("Connected!");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function send()
{
    var msg = {
        value: "23.2",
        unit: "°C"
      };

    ws.send(JSON.stringify(msg));
    console.log("Message sent.");
}