/*
  MOPED SENSOR
  Speed at 120kmh overflow after 119ms
  Revs at 12k overflow after 155ms

  !!! MIN LOOP SPEED 100ms;

  https://wolles-elektronikkiste.de/mpu6050-beschleunigungssensor-und-gyroskop
*/
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>

#define SpeedInput0 12
#define SpeedInput1 11
#define SpeedInput2 10

#define RevsInput0 9
#define RevsInput1 8
#define RevsInput2 7
#define RevsInput3 6
#define RevsInput4 5

#define resetInput 4

long loopStart; 
//long timeForLoopInMs = 100; !!! Production
long timeForLoopInMs = 100; 

Adafruit_MPU6050 mpu;

void setup() {
  delay(5000);
  Serial.begin(74880);
  //Serial.println("Moped Sensor Begin");

  digitalWrite(resetInput, LOW);

  // Try to initialize!
  if (!mpu.begin()) {
    //Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
  //Serial.println("MPU6050 Found!");
  mpu.setAccelerometerRange(MPU6050_RANGE_2_G);
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
  mpu.setCycleRate(MPU6050_CYCLE_1_25_HZ);

  Serial.println("sensor start;");
}

void loop() {
  loopStart = millis();

  bool speedValue0 = digitalRead(SpeedInput0);
  bool speedValue1 = digitalRead(SpeedInput1);
  bool speedValue2 = digitalRead(SpeedInput2);

  bool revsValue0 = digitalRead(RevsInput0);
  bool revsValue1 = digitalRead(RevsInput1);
  bool revsValue2 = digitalRead(RevsInput2);
  bool revsValue3 = digitalRead(RevsInput3);
  bool revsValue4 = digitalRead(RevsInput4);
  
  // calc
  int rawSpeedVal = 0;
  int rawRevsVal = 0;

  if(speedValue0) {
    rawSpeedVal += 1;
  }
  if(speedValue1) {
    rawSpeedVal += 2;
  }
  if(speedValue2) {
    rawSpeedVal += 4;
  }

  if(revsValue0) {
    rawRevsVal += 1;
  }
  if(revsValue1) {
    rawRevsVal += 2;
  }
  if(revsValue2) {
    rawRevsVal += 4;
  }
  if(revsValue3) {
    rawRevsVal += 8;
  }
  if(revsValue4) {
    rawRevsVal += 16;
  }

  // reset
  digitalWrite(resetInput, HIGH);
  delay(10);
  digitalWrite(resetInput, LOW);

  // Function contains Elements that are not valid for a ASW
  Serial.print(rawSpeedVal);
  Serial.print(",");
  Serial.print(rawRevsVal);
  Serial.println(",;");

  
  /*
  Serial.print("Speed: ");
  Serial.print(rawSpeedVal);
  Serial.print(",  ");
  Serial.print("Revs: ");
  Serial.println(rawRevsVal);

  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  /* Print out the values */
  /*
  Serial.print("Acceleration X: ");
  Serial.print(a.acceleration.x);
  Serial.print(", Y: ");
  Serial.print(a.acceleration.y);
  Serial.print(", Z: ");
  Serial.print(a.acceleration.z);
  Serial.println(" m/s^2");
  Serial.print("Rotation X: ");
  Serial.print(g.gyro.x);
  Serial.print(", Y: ");
  Serial.print(g.gyro.y);
  Serial.print(", Z: ");
  Serial.print(g.gyro.z);
  Serial.println(" rad/s");
  Serial.print("Temperature: ");
  Serial.print(temp.temperature);
  Serial.println(" degC");
  Serial.println("");


  */


  long milisTook = millis() - loopStart;
  if(milisTook < 0)  { // edgeCase Millis Overflow
    milisTook = 5;
  }
  long waitingMs = timeForLoopInMs - milisTook;
  /*
  Serial.print("Calc took: ");
  Serial.print(milisTook);
  Serial.print(" ms, waiting: ");

  Serial.print(waitingMs);
  Serial.println(" ms");
  */
  delay(waitingMs);  // delay in between reads for stability

  //delay(3000); // TEST
}
