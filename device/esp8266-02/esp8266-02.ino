#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <ArduinoJson.h>
#include <time.h>
#include "config.h"
#include "functions.h"

WebSocketClient websocketClient;
WiFiClient wifiClient;
unsigned long previousMillis = 0;

void setup()
{
  pinMode(GEIGER_PIN, INPUT_PULLUP);
  initSerial(115200);
  attachInterrupt(digitalPinToInterrupt(GEIGER_PIN), tube_impulse, FALLING);
  initWifi();
  
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
  unsigned long currentMillis = millis();
  char json[MESSAGE_MAX_LEN];

  if ((currentMillis - previousMillis) >= LOG_PERIOD)
  {
    previousMillis = currentMillis;
    
    if (wifiClient.connected())
    {
      createMessage(json);
      websocketClient.sendData(json);
    }
    
    counts = 0;
  }
}
