#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <DHT.h>
#include <ArduinoJson.h>
#include <time.h>
#include "config.h"
#include "functions.h"

void setup() 
{
  initSerial(115200);
  initWifi();
  dht.begin();

  delay(5000);
  
  connectionString.toCharArray(websocketPath, WEBSOCKET_PATH_LENGTH);
  websocketConnect(wifiClient);
  websocketHandshake(wifiClient, websocketClient);

  // Configuring time sync
  configTime(0, 0, "pool.ntp.org", "time.nist.gov");
  Serial.println("Waiting on time sync...");
  while (time(nullptr) < 1510644967) 
  {
    delay(10);
  }
}

void loop() 
{
  char json[MESSAGE_MAX_LEN];
  unsigned long currentMillis = millis();

  if (WiFi.status() == WL_DISCONNECTED) 
  {
    ESP.restart();
  }
  
  // Send message every second
  if ((currentMillis - previousMillis) >= DEVICE_SEND_INTERVAL) 
  {
    if (wifiClient.connected()) 
    {
      previousMillis = currentMillis;
      
      createMessage(json);
      websocketClient.sendData(json);
      
      Serial.println("Message sent: ");
      Serial.println(json);
    }
	else
	{
      ESP.restart();
	}
  }
}
