#define DEVICE_ID WiFi.macAddress()
#define GEIGER_PIN 5
#define LOG_PERIOD 15000 // Logging period in milliseconds
#define MAX_PERIOD 60000 // Maximum logging period
#define MESSAGE_MAX_LEN 256
#define WEBSOCKET_PATH_LENGTH 256

WebSocketClient websocketClient;
WiFiClient wifiClient;
unsigned long previousMillis = 0;

const unsigned long timeMultiplier = MAX_PERIOD / LOG_PERIOD;
const float radiationMultiplier = 0.0057;

const char* WIFI_SSID = "SSID";
const char* WIFI_PASS = "PASSWORD";

String connectionString = "/webservice/websocket/realtime/" + DEVICE_ID;
char* webserverHost = "echo.websocket.org";
const int webserverPort = 8080;
char websocketPath[WEBSOCKET_PATH_LENGTH];

unsigned long countsPerMinute = 0;
volatile unsigned long counts = 0;
