 
#include <Adafruit_Fingerprint.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x3F,16,2); //slave adress character = 16,2
SoftwareSerial mySerial(2, 3);

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);

uint16_t id;
const int buttonPin = A1;
const int ledPin = 13;

bool state = true, state2 = true; 
bool match = false;
int buttonState;
int z = 5;
int lastButtonState = LOW;
unsigned long lastDebounceTime = 0;  
unsigned long debounceDelay = 50;  
String data = "", nm = "", bal = "", stat = "", adm = "";

void setup()  
{
  Serial.begin(9600); 
  lcd.init();                      // initialize the lcd 
  lcd.backlight();  // For Yun/Leo/Micro/Zero/...
  delay(100);
  // set the data rate for the sensor serial port
  finger.begin(57600);
  lcd.clear();
  lcd.setCursor(0,0);
  if (finger.verifyPassword()) {
    lcd.print("Sensor OK");
  } else {
    lcd.print("No sensor");
    while (1) { delay(1); }
  }
  finger.getTemplateCount();
  
  lcd.setCursor(0,1);
  lcd.print("No " + String(finger.templateCount));
  pinMode(buttonPin, INPUT);
  pinMode(ledPin, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  digitalWrite(ledPin, state);
}

uint8_t readnumber(void) {
  uint8_t num = 0;
  finger.getTemplateCount();
  num = finger.templateCount;
  return ++num;
}

void readSerial(){
  String a = "";
  while (Serial.available()){
    a = Serial.readStringUntil('\n');
    data = a;
    stringSplit(data,',');
  }
  
}
void loop()                     // run over and over again
{
  if (state2){
    getFingerprintIDez();
    if (match){
      readSerial(); 
      
      lcd.clear();
      if (adm != ""){
        
        lcd.setCursor(0,0);
        lcd.print(adm);
        lcd.setCursor(0,1);
        lcd.print(stat);
        digitalWrite(4, HIGH);
      } else {
        lcd.setCursor(0,0);
        lcd.print(" Error");
        lcd.setCursor(0,1);
        lcd.print("  no data found");
        digitalWrite(5, HIGH);
      }
      
      delay(3000);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      match = false;
    }
//    lcd.clear();
//    lcd.setCursor(0,0);
//    lcd.print(nm);
    delay(50); 
  } else {
    enroll();
  }
  debounce();
 
   
}

void enroll(){
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Enroll Ready");
  id = readnumber();
  if (id == 0) {// ID #0 not allowed, try again!
     return;
  }
  delay(1000);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Enrolling ID #");
  lcd.setCursor(0,1);
  lcd.print(String(id));
  delay(1000);
  while (!  getFingerprintEnroll() );
  
}

uint8_t getFingerprintEnroll() {

  int p = -1;
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Place finger #" + String(id));
  lcd.setCursor(0,1);
  while (p != FINGERPRINT_OK) {
    lcd.clear();
    lcd.setCursor(0,1);
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      lcd.print("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      lcd.print("place finger");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      lcd.print("Com err");
      break;
    case FINGERPRINT_IMAGEFAIL:
      lcd.print("Imaging err");
      break;
    default:
      lcd.print("Unknown err");
      break;
    }
    debounce();
    if(state2){
      break;
    }
  }
  delay(1000);
  // OK success!
  lcd.clear();
  lcd.setCursor(0,1);
  p = finger.image2Tz(1);
  switch (p) {
    if(state2){
      break;
    }
    case FINGERPRINT_OK:
      lcd.print("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      lcd.print("Image messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      lcd.print("Com err");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      lcd.print("Features err");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      lcd.print("Feature err");
      return p;
    default:
      lcd.print("Unknown err");
      return p;
  }
  delay(1000);
  lcd.clear();
  lcd.print("Remove");
  delay(2000);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
    if(state2){
      break;
    }
  }
  p = -1;
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Place again");
  lcd.setCursor(0,1);
  while (p != FINGERPRINT_OK) {
    if(state2){
      break;
    }
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      lcd.print("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      Serial.print(".");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      lcd.print("Com err");
      break;
    case FINGERPRINT_IMAGEFAIL:
      lcd.print("Imaging err");
      break;
    default:
      lcd.print("Unknown err");
      break;
    }
  }

  // OK success!
  delay(1000);
  lcd.clear();
  lcd.setCursor(0,1);
  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
      lcd.print("Converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      lcd.print("Image messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      lcd.print("Com err");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      lcd.print("Features err");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      lcd.print("Features err");
      return p;
    default:
      lcd.print("Unknown err");
      return p;
  }
  
  // OK converted!
  delay(1000);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Creating #" + String(id));
  lcd.setCursor(0,1);
  p = finger.createModel();
  if (p == FINGERPRINT_OK) {
    lcd.print("matched!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    lcd.print("Com err");
    return p;
  } else if (p == FINGERPRINT_ENROLLMISMATCH) {
    lcd.print("not match");
    return p;
  } else {
    lcd.print("Unknown err");
    return p;
  }   
  delay(1000);
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("ID " + String(id));
  if(!state2){
      p = finger.storeModel(id);  //store model only if in enroll mode
  }
  
  lcd.setCursor(0,1);
  if (p == FINGERPRINT_OK) {
    lcd.print("Stored!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    lcd.print("Com err");
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    lcd.print("Loc err");
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    lcd.print("Write err");
    return p;
  } else {
    lcd.print("Unknown err");
    return p;
  }   
  debounce();
}

int getFingerprintIDez() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Scan mode");
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p == FINGERPRINT_OK) {
    Serial.println("Found a print match!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_NOTFOUND) {
    Serial.println("Did not find a match");
    lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Not registered");
  digitalWrite(6, HIGH);
  delay(3000);
  digitalWrite(6, LOW);
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }
  // found a match!
  
  
  unsigned int n = finger.fingerID;
  //lcd.print("Found ID #" + String(n));
  Serial.println(n);
  match = true;
  lcd.setCursor(0,1);
  unsigned int c = finger.confidence;
  //lcd.print(" confid  " + String(c));
  delay(200);
  return finger.fingerID; 
}

void debounce(){
  int reading = digitalRead(buttonPin);
  if (reading != lastButtonState) {
  
    lastDebounceTime = millis();
  }

  if ((millis() - lastDebounceTime) > debounceDelay) {

    if (reading != buttonState) {
      buttonState = reading;

      if (buttonState == HIGH) {
        state2 = !state2;
      }
    }
  }
  digitalWrite(ledPin, state2);

  // save the reading. Next time through the loop, it'll be the lastButtonState:
  lastButtonState = reading;

}

void stringSplit(String s, char a){
  int l = s.length();
  int c = 0;
  for (int j = 0; j < l; j++){
    if (s.charAt(j) == a){
      c++;
    }
  }
  String split[z];
  if (c != 0){
    for (int k = 0; k < c ; k++){
      if (k >= z){
        break;
      }
      int x = s.indexOf(a);
      l = s.length();
      split[k] = s.substring(0,x);
      s = s.substring(x+1,l);
      if (k == c-1){
        split[k+1] = s;
      }
    }

    adm = split[0];
    stat = split[1];
    
  } else {
    
  }
  

}
