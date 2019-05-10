var ws;

function connect()
{   
    var host = document.location.host;
    
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/00:A0:C9:14:C8:29");

    ws.onmessage = function(event) {
        console.log(event.data);
    };
}

function send()
{
    var msg = {
        description: "Test sensor",
        dateTime: "2019-05-10 10:37:05",
        temperature: 23.2
      };

    ws.send(JSON.stringify(msg));
    console.log("Message sent.");
}