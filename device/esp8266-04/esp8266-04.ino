#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <ArduinoJson.h>
#include <Wire.h>
#include <BH1750FVI.h>
#include <time.h>
#include "config.h"
#include "functions.h"

void setup()
{
  pinMode(LED_GREEN_PIN, OUTPUT);
  pinMode(LED_RED_PIN, OUTPUT);
  ledStatus(LED_STATUS_RED);
  
  initSerial(115200);
  initWifi();
  Wire.begin(SDA_PIN, SCL_PIN);
  LightSensor.begin();
  
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

  ledStatus(LED_STATUS_GREEN);
}

void loop()
{
  unsigned long currentMillis = millis();
  char json[MESSAGE_MAX_LEN];

  if (WiFi.status() == WL_DISCONNECTED) 
  {
    ESP.restart();
  }
  
  if((currentMillis - previousMillis) >= SEND_INTERVAL)
  {
    previousMillis = currentMillis;

    if (wifiClient.connected())
    {
      ledStatus(LED_STATUS_OFF);
      createMessage(json);
      websocketClient.sendData(json);

      //Serial.println("Message sent: ");
      //Serial.println(json);
      ledStatus(LED_STATUS_GREEN);
    }
    else
    {
      ledStatus(LED_STATUS_RED);
	  ESP.restart();
    }
  }
}
