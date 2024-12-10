#include <Servo.h>

#define trigPin 2
#define echoPin 3

long duration;
int distance;

Servo myservo;

int calculateDistance() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;
  return distance;
}

void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  myservo.attach(4); // Actualizează pinul pentru servomotor
  Serial.begin(9600);
}

void loop() {
  int i;
  for (i = 15; i <= 165; i++) {
    myservo.write(i);
    delay(15);
    if (i % 10 == 0) {
      calculateDistance();
      Serial.print(i);
      Serial.print(",");
      Serial.print(distance);
      Serial.print(".");
    }
    delay(50);
  }
  for (i = 165; i >= 15; i--) {
    myservo.write(i);
    delay(15);
    if (i % 10 == 0) {
      calculateDistance();
      Serial.print(i);
      Serial.print(",");
      Serial.print(distance);
      Serial.print(".");
    }
    delay(50);
  }
}
