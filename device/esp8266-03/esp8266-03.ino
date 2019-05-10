#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include "config.h"
#include "functions.h"

WebSocketClient websocketClient;
WiFiClient wifiClient;

OneWire oneWire(ONE_WIRE_PIN);
DallasTemperature sensors(&oneWire);

void setup()
{
  Serial.begin(115200);
  sensors.begin();

  initWifi();
  delay(5000);
  websocketConnect(wifiClient);
  websocketHandshake(wifiClient, websocketClient);
}

void loop()
{
  sensors.requestTemperatures();
  float temperature = sensors.getTempCByIndex(TEMPSENSOR_1);

  if (wifiClient.connected())
  {
    char json[40];
    sprintf(json, "{\"deviceId\":%d,\"temperature\":%.2f}", DEVICE_ID, temperature);
    websocketClient.sendData(json);
  }
}
