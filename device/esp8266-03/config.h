#define DEVICE_ID WiFi.macAddress()
#define ONE_WIRE_PIN 5
#define TEMPSENSOR_1 0
#define MESSAGE_MAX_LEN 256
#define WEBSOCKET_PATH_LENGTH 256

WebSocketClient websocketClient;
WiFiClient wifiClient;

OneWire oneWire(ONE_WIRE_PIN);
DallasTemperature sensors(&oneWire);

const char* WIFI_SSID = "SSID";
const char* WIFI_PASS = "PASSWORD";

String connectionString = "/webservice/websocket/realtime/" + DEVICE_ID;
char* webserverHost = "echo.websocket.org";
const int webserverPort = 8080;
char websocketPath[WEBSOCKET_PATH_LENGTH];
