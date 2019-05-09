#include <ESP8266WiFi.h>
#include <WebSocketClient.h>

#define GEIGER_PIN 5
#define LOG_PERIOD 15000 // Logging period in milliseconds
#define MAX_PERIOD 60000 // Maximum logging period

volatile unsigned long counts = 0;
unsigned long cpm = 0;
unsigned long multiplier = MAX_PERIOD / LOG_PERIOD;
unsigned long previousMillis = 0;

const unsigned int deviceId = 2;
char path[] = "/webservice/websocket/realtime/2";
char host[] = "IP";
const char* ssid = "SSID";
const char* password = "PASSWORD";
  
WebSocketClient webSocketClient;
WiFiClient client;

void tube_impulse()
{
  counts++;
}

void setup()
{
  pinMode(GEIGER_PIN, INPUT_PULLUP);
  Serial.begin(9600);
  attachInterrupt(digitalPinToInterrupt(GEIGER_PIN), tube_impulse, FALLING);

  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  delay(5000);

  // Connect to the websocket server
  if (client.connect("192.168.0.97", 8080))
  {
    Serial.println("Connected");
  }
  else 
  {
    Serial.println("Connection failed.");
    while(1)
    {
      // Hang on failure
    }
  }

  // Handshake with the server
  webSocketClient.path = path;
  webSocketClient.host = host;
  if (webSocketClient.handshake(client))
  {
    Serial.println("Handshake successful");
  } 
  else
  {
    Serial.println("Handshake failed.");
    while(1) 
    {
      // Hang on failure
    }  
  }
}

void loop()
{
  unsigned long currentMillis = millis();

  if ((currentMillis - previousMillis) >= LOG_PERIOD)
  {
    previousMillis = currentMillis;
    cpm = counts * multiplier;
    float radiation = cpm * 0.0057;
    
    if (client.connected())
    {
      char json[40];    
      sprintf(json, "{\"deviceId\":%d,\"radiation\":%f}", deviceId, radiation);
      webSocketClient.sendData(json);
    }
    
    Serial.print("Counts per minute: ");
    Serial.print(cpm);
    Serial.print("  (");
    Serial.print(radiation);
    Serial.println(" uSv/h)");

    counts = 0;
  }
}
