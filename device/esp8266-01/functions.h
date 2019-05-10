// Package
struct Package {
  float temperature = 0.0;
  float humidity = 0.0;
  char* description = "temp/hum";

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

void websocketConnect(WiFiClient& wifiClient)
{
  if (wifiClient.connect(webserverHost, webserverPort))
  {
    Serial.println("WebSocket connected.");
  }
  else 
  {
    Serial.println("WebSocket connection failed.");
    while(1)
    { 
      // Hang on failure
    }
  }
}

void websocketHandshake(WiFiClient& wifiClient, WebSocketClient& websocketClient)
{
  websocketClient.path = websocketPath;
  websocketClient.host = webserverHost;
  
  if (websocketClient.handshake(wifiClient))
  {
    Serial.println("WebSocket handshake successful");
  } 
  else
  {
    Serial.println("WebSocket handshake failed.");
    while(1) 
    {
      // Hang on failure
    }  
  }
}

void createMessage(char* json) {
  data.updateReadings();
  StaticJsonBuffer<MESSAGE_MAX_LEN> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["dateTime"] = data.DateTime();
  root["mac"] = DEVICE_ID;
  root["description"] = data.description;
  ((std::isnan(data.temperature)) ? root["temperature"] = NULL : root["temperature"] = data.temperature);
  ((std::isnan(data.humidity)) ? root["humidity"] = NULL : root["humidity"] = data.humidity);

  root.printTo(json, MESSAGE_MAX_LEN);
}
