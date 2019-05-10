#include <ESP8266WiFi.h>
#include <WebSocketClient.h>
#include <DHT.h>
#include <ArduinoJson.h>
#include "config.h"
#include "functions.h"

WebSocketClient webSocketClient;
WiFiClient client; // Use WiFiClient class to create TCP connections

void setup() {
  initSerial(115200);
  initWifi();
  dht.begin();

  delay(5000);
  
  // Connect to the websocket server
  Serial.println("\n--Connecting to websocket--");
  if (client.connect(host, port)) {
    Serial.println("Client connected");
  } else {
    Serial.println("Connection failed.");
    while(1) {}
  }

  // Handshake with the server
  webSocketClient.path = path;
  webSocketClient.host = host;
  
  if (webSocketClient.handshake(client)) {
    Serial.println("Handshake successful");
  } 
  else {
    Serial.println("Handshake failed.");
    while(1) {}
  }

  configTime(0, 0, "pool.ntp.org", "time.nist.gov");
  Serial.println("Waiting on time sync...");
  while (time(nullptr) < 1510644967) {
    delay(10);
  }
}

unsigned long lastMillis = 0;
void loop() {
  char json[MESSAGE_MAX_LEN];
  if (client.connected()) {

    // Send message every second
    if (millis() - lastMillis >= 1000) {
      createMessage(json);
      webSocketClient.sendData(json);
      Serial.println("Message sent: ");
      Serial.println(json);
      lastMillis = millis();
    }
  } 
  else {
    Serial.println("Client disconnected.");
    while (1) {}
  }
}
