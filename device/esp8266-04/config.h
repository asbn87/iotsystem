#define DEVICE_ID WiFi.macAddress()
#define SCL_PIN 4
#define SDA_PIN 5
#define SEND_INTERVAL 100
#define MESSAGE_MAX_LEN 256
#define WEBSOCKET_PATH_LENGTH 256

unsigned long previousMillis = 0;

WebSocketClient websocketClient;
WiFiClient wifiClient;

BH1750FVI LightSensor(BH1750FVI::k_DevModeContHighRes);

const char* WIFI_SSID = "SSID";
const char* WIFI_PASS = "PASSWORD";

String connectionString = "/webservice/websocket/realtime/" + DEVICE_ID;
char* webserverHost = "echo.websocket.org";
const int webserverPort = 8080;
char websocketPath[WEBSOCKET_PATH_LENGTH];
