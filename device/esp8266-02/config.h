#define DEVICE_ID 2
#define GEIGER_PIN 5
#define LOG_PERIOD 15000 // Logging period in milliseconds
#define MAX_PERIOD 60000 // Maximum logging period

const unsigned long timeMultiplier = MAX_PERIOD / LOG_PERIOD;
const float radiationMultiplier = 0.0057;

const char* WIFI_SSID = "SSID";
const char* WIFI_PASS = "PASSWORD";

char websocketPath[] = "/webservice/websocket/realtime/2";
char destinationHost[] = "IP-ADDRESS";
const int destinationPort = 8080;
