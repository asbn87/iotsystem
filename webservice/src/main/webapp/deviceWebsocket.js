var ws;

function connect()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/1");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function send()
{
    var msg = {
        temperature: 23.2
      };

    ws.send(JSON.stringify(msg));
    console.log("Message sent.");
}