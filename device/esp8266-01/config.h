const static char* WIFI_SSID = "wifi_ssid";
const static char* WIFI_PASS = "wifi_pass";
static char path[] = "/webservice/websocket/realtime/1";
static char host[] = "ip";
int port = 0;

// DHT11 Sensor
#define DHT_PIN 2
#define DHT_TYPE DHT11
static DHT dht(DHT_PIN, DHT_TYPE);
#define MESSAGE_MAX_LEN 256

#define DEVICE_ID 1;
