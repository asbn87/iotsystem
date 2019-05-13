typedef enum 
{
  LED_STATUS_RED,
  LED_STATUS_GREEN,
  LED_STATUS_OFF
} LED_STATUS;

// Package
struct Package {
  uint16_t light = 0;
  char* description = "light";

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
    light = LightSensor.GetLightIntensity();
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
  WiFi.begin(WIFI_SSID, WIFI_PASS);
  
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi connected");  
  Serial.println("IP address: ");
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
  root["light"] = data.light;

  root.printTo(json, MESSAGE_MAX_LEN);
}

void ledStatus(LED_STATUS ledStatus)
{
  if (ledStatus == LED_STATUS_RED)
  {
    digitalWrite(LED_GREEN_PIN, LOW);
    digitalWrite(LED_RED_PIN, HIGH);
  }
  else
  {
    digitalWrite(LED_RED_PIN, LOW);
    digitalWrite(LED_GREEN_PIN, HIGH);
  }
}
