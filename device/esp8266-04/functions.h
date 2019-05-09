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
  if (wifiClient.connect(destinationHost, destinationPort))
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
  websocketClient.host = destinationHost;
  
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
