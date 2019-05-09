var ws;

function log()
{   
    var host = document.location.host;
    ws = new WebSocket("ws://" + host + "/webservice/websocket/realtime/log");
}