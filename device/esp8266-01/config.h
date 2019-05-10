#define DEVICE_ID WiFi.macAddress()
#define DEVICE_SEND_INTERVAL 1000
#define DHT_PIN 2
#define DHT_TYPE DHT11
#define MESSAGE_MAX_LEN 256
#define WEBSOCKET_PATH_LENGTH 256

WebSocketClient websocketClient;
WiFiClient wifiClient;
unsigned long previousMillis = 0;

// WiFi settings
const static char* WIFI_SSID = "WIFI_SSID";
const static char* WIFI_PASS = "WIFI_PASS";

// Websocket settings
String connectionString = "/webservice/websocket/realtime/" + DEVICE_ID;
char* webserverHost = "echo.websocket.org";
const int webserverPort = 8080;
char websocketPath[WEBSOCKET_PATH_LENGTH];

// DHT11 Sensor
static DHT dht(DHT_PIN, DHT_TYPE);
