const static char* WIFI_SSID = "WIFI_SSID";
const static char* WIFI_PASS = "WIFI_PASS";
static char path[] = "/webservice/websocket/realtime/" + DEVICE_ID;
static char host[] = WiFi.localIP();
int port = 8080;

// DHT11 Sensor
#define DHT_PIN 2
#define DHT_TYPE DHT11
static DHT dht(DHT_PIN, DHT_TYPE);
#define MESSAGE_MAX_LEN 256

#define DEVICE_ID WiFi.macAddress()
