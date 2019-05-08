#define GEIGER_PIN 5
#define LOG_PERIOD 15000 // Logging period in milliseconds
#define MAX_PERIOD 60000 // Maximum logging period

volatile unsigned long counts = 0;
unsigned long cpm = 0;
unsigned long multiplier = MAX_PERIOD / LOG_PERIOD;
unsigned long previousMillis = 0;

void tube_impulse()
{
  counts++;
}

void setup()
{
  pinMode(GEIGER_PIN, INPUT_PULLUP);
  Serial.begin(9600);
  attachInterrupt(digitalPinToInterrupt(GEIGER_PIN), tube_impulse, FALLING);
}

void loop()
{
  unsigned long currentMillis = millis();

  if ((currentMillis - previousMillis) >= LOG_PERIOD)
  {
    previousMillis = currentMillis;
    cpm = counts * multiplier;

    Serial.print("Counts per minute: ");
    Serial.print(cpm);
    Serial.print("  (");
    Serial.print(cpm * 0.0057);
    Serial.println(" uSv/h)");

    counts = 0;
  }
}
