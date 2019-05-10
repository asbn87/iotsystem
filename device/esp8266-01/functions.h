#include <ESP8266WiFi.h>
#include <time.h>

// Package
struct Package {
  float temperature = 0.0;
  float humidity = 0.0;
  char* type = "temp/hum";

  String DateTime()
  {
    time_t now;
    struct tm ts;
    char buf[80];
    
    // Get current time
    time(&now);

    // Format time
    ts = *localtime(&now);
    ts.tm_hour += 2; // Local Summertime Western Europe +2
    strftime(buf, sizeof(buf), "%Y-%m-%d %H:%M:%S", &ts);
  
    return (String)buf;
  }
  
  void updateReadings() {
    temperature = dht.readTemperature();
    humidity = dht.readHumidity();
  }
};
struct Package data;

void initSerial(int baud)
{
  Serial.begin(baud);
  delay(2000);
  Serial.println("--Serial communication initiated--");
  Serial.println("Baud rate: "+ String(baud));
}

void initWifi()
{
  Serial.println("\n--Wifi communication initiated--\nPlease wait...");
  Serial.println("Connecting to: "+(String)WIFI_SSID);
  WiFi.mode(WIFI_STA);
  
  while(WiFi.status() != WL_CONNECTED)
  {
    WiFi.begin(WIFI_SSID, WIFI_PASS);
    delay(10000);
  }

  Serial.printf("\nConnected to Wifi Network %s \n", WIFI_SSID);
  Serial.print("IP-adress: ");
  Serial.println(WiFi.localIP());
}

void createMessage(char* json) {
  data.updateReadings();
  StaticJsonBuffer<MESSAGE_MAX_LEN> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["dateTime"] = data.DateTime();
  root["mac"] = DEVICE_ID;
  root["type"] = data.type;
  
  JsonObject& dht = root.createNestedObject("dht");
  ((std::isnan(data.temperature)) ? dht["temperature"] = NULL : dht["temperature"] = data.temperature);
  ((std::isnan(data.humidity)) ? dht["humidity"] = NULL : dht["humidity"] = data.humidity);

  root.printTo(json, MESSAGE_MAX_LEN);
}
