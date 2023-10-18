// Changed by Maksbier for Moped Projekt
// Originally created by:
// Created by Bodmer 17/3/20 as an example to the TFT_eSPI library:
// https://github.com/Bodmer/TFT_eSPI

#define DIAL_CENTRE_X 120
#define DIAL_CENTRE_Y 120

// Font attached to this sketch
#include "NotoSansBold36.h"
#include "Bahnschrift64.h"
#define FONT_MEDIUM NotoSansBold36
#define FONT_LARGE Bahnschrift64

// SERIAL 1 Arduino
#define RX1 13
#define TX1 12
// SERIAL 2 RPi
#define RX2 17
#define TX2 16

#include <TFT_eSPI.h>

TFT_eSPI tft = TFT_eSPI();
TFT_eSprite speedSprite = TFT_eSprite(&tft);
TFT_eSprite revsSprite = TFT_eSprite(&tft);
TFT_eSprite revsSpriteNeedle = TFT_eSprite(&tft);
TFT_eSprite imageSprite = TFT_eSprite(&tft);
uint16_t speedSpriteWidth = 0;

// Include the PNG decoder library, available via the IDE library manager
#include <PNGdec.h>

#include "moped.h"
#include "blinkerR.h"
#include "blinkerL.h"
#include "fernlicht.h"
#include "warnblinker.h"

PNG png;  // PNG decoder instance
#define MAX_IMAGE_WDITH 240
// Position variables must be global (PNGdec does not handle position coordinates)
int16_t xpos = 0;
int16_t ypos = 0;
int16_t screenWidth = 240;
int16_t screenHeight = 240;

// ANTONS
// 0 = start, 1 = normal
int mode = 0;

String incommingDataArd = "";
String incommingDataRasp = "";

bool sBlinkerLinks = false;
bool sBlinkerRechts = false;
bool sFernlicht = false;
bool sWarnblinker = false;
int sDrehzahl = 0;
int sTempo = 0;

// =======================================================================================
// Setup
// =======================================================================================
void setup() {
  Serial.begin(74880);
  Serial1.begin(74880, SERIAL_8N1, TX1, RX1);
  Serial2.begin(115200, SERIAL_8N1, TX2, RX2);

  tft.begin();
  tft.setRotation(90);
  tft.fillScreen(TFT_BLACK);



  // SPEED
  speedSprite.loadFont(Bahnschrift64);
  speedSpriteWidth = speedSprite.textWidth("777") + 10;
  speedSprite.createSprite(speedSpriteWidth, speedSprite.fontHeight());
  speedSprite.createSprite(speedSpriteWidth, speedSprite.fontHeight());
  speedSprite.fillSprite(TFT_BLACK);
  speedSprite.setTextColor(TFT_WHITE, TFT_BLACK, true);
  speedSprite.setTextDatum(MC_DATUM);
  speedSprite.setTextPadding(speedSpriteWidth);


  // REVS
  revsSprite.setColorDepth(8);
  revsSprite.createSprite(screenWidth, screenHeight / 2 - 28);
  //revsSprite.createSprite(screenWidth, screenHeight);
  //revsSprite.fillSprite(TFT_RED);
  revsSprite.fillSprite(TFT_BLACK);
  int16_t revsSpriteDialThickness = 20;
  revsSprite.drawArc(DIAL_CENTRE_X, DIAL_CENTRE_Y, screenWidth / 2, screenWidth / 2 - revsSpriteDialThickness, 110, 190, TFT_GREEN, TFT_BLACK);
  revsSprite.drawArc(DIAL_CENTRE_X, DIAL_CENTRE_Y, screenWidth / 2, screenWidth / 2 - revsSpriteDialThickness, 190, 220, TFT_YELLOW, TFT_BLACK);
  revsSprite.drawArc(DIAL_CENTRE_X, DIAL_CENTRE_Y, screenWidth / 2, screenWidth / 2 - revsSpriteDialThickness, 220, 250, TFT_RED, TFT_BLACK);
  // NEEDLE
  revsSpriteNeedle.setColorDepth(8);
  revsSpriteNeedle.createSprite(screenWidth, screenHeight / 2 - 28);
  revsSpriteNeedle.fillSprite(TFT_TRANSPARENT);


  // IMAGES
  imageSprite.createSprite(screenWidth, screenHeight);
  imageSprite.setColorDepth(8);
  //imageSprite.createSprite(screenWidth, screenHeight/2);
  imageSprite.fillSprite(TFT_TRANSPARENT);



  // SETUP DRAW

  int16_t rc = png.openFLASH((uint8_t *)moped, sizeof(moped), pngDraw);
  tft.startWrite();
  rc = png.decode(NULL, 0);
  tft.endWrite();
}

// =======================================================================================
// Loop
// =======================================================================================

int16_t oldNeedleStart = 0;
bool arduinoReady = false;
void loop() {
  readSignal();

  if (mode == 0) {
    Serial.println("waiting for arduino");
    if (arduinoReady) {
      mode++;
      tft.fillScreen(TFT_BLACK);
    }
    delay(100);
  }
  if (mode == 1) {
    // REVS
    revsSprite.pushSprite(0, 0);
    int16_t revsSpriteNeedleWidht = 5;
    int16_t revsSpriteNeedleLenght = 40;
    int16_t needleStart = map(sDrehzahl, 0, 10000, 110, 250) - revsSpriteNeedleWidht / 2;
    revsSpriteNeedle.drawArc(DIAL_CENTRE_X, DIAL_CENTRE_Y, screenWidth / 2, screenWidth / 2 - revsSpriteNeedleLenght, oldNeedleStart, oldNeedleStart + revsSpriteNeedleWidht, TFT_TRANSPARENT, TFT_BLACK);
    revsSpriteNeedle.drawArc(DIAL_CENTRE_X, DIAL_CENTRE_Y, screenWidth / 2, screenWidth / 2 - revsSpriteNeedleLenght, needleStart, needleStart + revsSpriteNeedleWidht, TFT_WHITE, TFT_BLACK);
    oldNeedleStart = needleStart;
    revsSpriteNeedle.setTextColor(TFT_WHITE, TFT_TRANSPARENT);
    tft.setTextSize(1);
    //revs
    revsSpriteNeedle.setCursor(screenWidth / 2 - 20, 30);
    //revsSpriteNeedle.println("x1000");

    revsSpriteNeedle.pushSprite(0, 0, TFT_TRANSPARENT);
    // SPEED
    speedSprite.drawNumber(sTempo, speedSpriteWidth / 2, speedSprite.fontHeight() / 2);
    speedSprite.pushSprite(DIAL_CENTRE_X - speedSpriteWidth / 2, DIAL_CENTRE_Y - speedSprite.fontHeight() / 2);

    // IMAGES
    // BLINKER LINKS
    int16_t rc = png.openFLASH((uint8_t *)blinkerL, sizeof(blinkerL), pngDraw);
    xpos = 15;
    ypos = screenHeight / 2 - png.getHeight() / 2;
    if (sBlinkerLinks) {
      tft.startWrite();
      rc = png.decode(NULL, 0);
      tft.endWrite();
    } else {
      tft.fillRect(xpos, ypos, png.getWidth(), png.getHeight(), TFT_BLACK);
    }
    // BLINKER RECHTS
    rc = png.openFLASH((uint8_t *)blinkerR, sizeof(blinkerR), pngDraw);
    xpos = screenWidth - png.getWidth() - 15;
    ypos = screenHeight / 2 - png.getHeight() / 2;
    if (sBlinkerRechts) {
      tft.startWrite();
      rc = png.decode(NULL, 0);
      tft.endWrite();
    } else {
      tft.fillRect(xpos, ypos, png.getWidth(), png.getHeight(), TFT_BLACK);
    }
    // FERNLICHT
    rc = png.openFLASH((uint8_t *)fernlicht, sizeof(fernlicht), pngDraw);
    xpos = screenWidth / 2 + 15;
    ypos = screenHeight / 4 - png.getHeight() / 2;
    if (sFernlicht) {
      tft.startWrite();
      rc = png.decode(NULL, 0);
      tft.endWrite();
    } else {
      tft.fillRect(xpos, ypos, png.getWidth(), png.getHeight(), TFT_BLACK);
    }
    // WARNBLINKER
    rc = png.openFLASH((uint8_t *)warnblinker, sizeof(warnblinker), pngDraw);
    xpos = screenWidth / 2 - png.getWidth() / 2;
    ypos = screenHeight / 2 + png.getHeight() / 2;
    if (sWarnblinker) {
      tft.startWrite();
      rc = png.decode(NULL, 0);
      tft.endWrite();
    } else {
      tft.fillRect(xpos, ypos, png.getWidth(), png.getHeight(), TFT_BLACK);
    }


    // TEXT
    tft.setTextColor(TFT_WHITE, TFT_BLACK);
    tft.setTextSize(1);
    //kmh
    tft.setCursor(screenWidth / 2 + 5, screenHeight / 2 + 18, 2);
    tft.println("km/h");
    //revs
    tft.setCursor(screenWidth / 2 - 20, 30);
    //tft.println("x1000");

    tft.setCursor(12, 90, 2);
    tft.println("0");
    tft.setCursor(screenWidth - 20, 90, 2);
    tft.println("10");



    delay(50);
  }
}


void readSignal(void) {
  // READ ARDUINO
  if (Serial1.available() != 0) {
    incommingDataArd += Serial1.readStringUntil(';');
    Serial.print("data from Arduino: ");
    Serial.println(incommingDataArd);
    incommingDataArd.trim();

    // START to normal mode
    if (incommingDataArd.length() > 1 && arduinoReady == false) {
      arduinoReady = true;
    } else {
      // RELAY TO RASP
      if (arduinoReady) {
        Serial.print("ESP send data to Rasp: ");
        Serial.println(incommingDataArd + ";");

        Serial2.println(incommingDataArd + ";");
      }
    }
  }
  // READ RASP
  if (Serial2.available() != 0) {
    incommingDataRasp += Serial2.readStringUntil(';');
    incommingDataRasp.trim();
    incommingDataRasp += ";";
    Serial.print("data from RaspberryPi: ");
    Serial.println(incommingDataRasp);
  }
  /*
  if(Serial.available() != 0) {
    	incommingData += Serial.readString();
      Serial.print("data Arduino: ");
      Serial.println(incommingData);
  }
  */


  if (mode == 1) {
    Serial.print("ANALYZING ");
    int endOfCommandPos = incommingDataRasp.indexOf(";");
    if (endOfCommandPos > 0) {
      String command = incommingDataRasp.substring(0, endOfCommandPos);
      incommingDataRasp = incommingDataRasp.substring(endOfCommandPos + 1);
      Serial.print("command ");
      Serial.println(command);

      int i = 0;
      while (command.length() != 0) {
        Serial.println(command);
        int endOfTaskPos = command.indexOf(",");
        if (endOfTaskPos > 0) {
          String task = command.substring(0, endOfTaskPos);
          command = command.substring(endOfTaskPos + 1);
          int number = task.toInt();
          Serial.print("Task ");
          Serial.println(task);

          if (i == 0) {
            sTempo = number;
          }
          if (i == 1) {
            sDrehzahl = number;
          }
          if (i == 2) {
            if (number == 1) {
              sBlinkerLinks = true;
            } else {
              sBlinkerLinks = false;
            }
          }
          if (i == 3) {
            if (number == 1) {
              sBlinkerRechts = true;
            } else {
              sBlinkerRechts = false;
            }
          }
          if (i == 4) {
            if (number == 1) {
              sFernlicht = true;
            } else {
              sFernlicht = false;
            }
          }
          if (i == 5) {
            if (number == 1) {
              sWarnblinker = true;
            } else {
              sWarnblinker = false;
            }
          }

          i++;
        }
      }
    }
  }

  incommingDataArd = "";
  incommingDataRasp = "";

    /*
  Serial.println("read Signal");

  sBlinkerLinks = true;
  sBlinkerRechts = true;
  sFernlicht = true;
  sWarnblinker = true;

  sBlinkerLinks = !sBlinkerLinks;
  sBlinkerRechts = !sBlinkerRechts;
  sFernlicht = !sFernlicht;
  sWarnblinker = !sWarnblinker;
  
  sDrehzahl = random(0,10000);
  sTempo = random(0,190);
  */

    Serial.println("");
    Serial.println("----------------------");
    Serial.println("");
  }