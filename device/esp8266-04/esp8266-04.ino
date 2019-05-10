#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <Wire.h>
#include <BH1750FVI.h>
#include "config.h"
#include "functions.h"

unsigned long previousMillis = 0;

WebSocketClient websocketClient;
WiFiClient wifiClient;

BH1750FVI LightSensor(BH1750FVI::k_DevModeContHighRes);

void setup()
{
  Serial.begin(115200);
  Wire.begin(SDA_PIN, SCL_PIN);
  LightSensor.begin();

  initWifi();
  delay(5000);
  websocketConnect(wifiClient);
  websocketHandshake(wifiClient, websocketClient);
}

void loop()
{
  unsigned long currentMillis = millis();
  
  if((currentMillis - previousMillis) >= SEND_INTERVAL)
  {
    previousMillis = currentMillis;

    uint16_t light = LightSensor.GetLightIntensity();
    Serial.printf("Light: %d lux\n", light);

    if (wifiClient.connected())
    {
      char json[40];
      sprintf(json, "{\"deviceId\":%d,\"light\":%d}", DEVICE_ID, light);
      websocketClient.sendData(json);
    }
  }
}
