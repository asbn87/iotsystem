#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include "config.h"
#include "functions.h"

volatile unsigned long counts = 0;
unsigned long countsPerMinute = 0;
unsigned long previousMillis = 0;
  
WebSocketClient websocketClient;
WiFiClient wifiClient;

void tube_impulse()
{
  counts++;
}

void setup()
{
  pinMode(GEIGER_PIN, INPUT_PULLUP);
  Serial.begin(115200);
  attachInterrupt(digitalPinToInterrupt(GEIGER_PIN), tube_impulse, FALLING);

  initWifi();
  delay(5000);
  websocketConnect(wifiClient);
  websocketHandshake(wifiClient, websocketClient);
}

void loop()
{
  unsigned long currentMillis = millis();

  if ((currentMillis - previousMillis) >= LOG_PERIOD)
  {
    previousMillis = currentMillis;
    countsPerMinute = counts * timeMultiplier;
    float radiation = countsPerMinute * radiationMultiplier;
    
    if (wifiClient.connected())
    {
      char json[40];
      sprintf(json, "{\"deviceId\":%d,\"radiation\":%.2f}", DEVICE_ID, radiation);
      websocketClient.sendData(json);
    }
    
    Serial.printf("Radiation: %.2f uSv/h\n", radiation);
	
    counts = 0;
  }
}
