import de.bezier.data.sql.*;
import processing.serial.*;
import javax.swing.JOptionPane; 

final boolean debugPort = true; 

Serial myPort;
MySQL db;

String user = "root", pass = "root", database = "authenticationsys";
String f_id = "", name = "", adm = "", schl = "", stat = "", data = "";
int bal = 0, paid = 0, due= 0, disp = 0, n = 0,z = 5000;
String code = "", COMx = "COM3";
boolean elig = false;
PShape usr, sch, mon, ok, x, finger, usb, warn;

void setup(){
  size(700, 500);
  usr = loadShape("user.svg");
  sch = loadShape("school.svg");
  mon = loadShape("coins.svg");
  ok = loadShape("ok.svg");
  x = loadShape("x.svg");
  warn = loadShape("warning.svg");
  usb = loadShape("usb.svg");
  finger = loadShape("fingerprint.svg");
  selectPort();
  db = new MySQL( this, "localhost", database, user, pass );
   
  //search("2");

}


void draw(){
  background(0);
  if (disp == 0){
    scan();
  } else if (disp == 1){
    showInfo();
    //timeout(5);
  } else if (disp == 2){
    err();
  }
  
  port();
}

void serialEvent(Serial myPort){
  if (myPort.available() > 0){
    f_id = "";
    f_id = myPort.readStringUntil('\n');
    f_id = trim(f_id);
    search(f_id);
  }
  
  
}


void search(String s){
    if ( db.connect() ){
        db.query( "SELECT * FROM student_info WHERE student_id = '"+s+"';");
        println("querying");
        if (db.next() == true){
            name = db.getString("first_name")+ " "+db.getString("last_name");
            adm = db.getString("student_regno");
            schl = db.getString("school");
            println("read");
            db.query( "SELECT * FROM feebal WHERE student_id = '"+s+"';");
            if (db.next() == true){
              paid =db.getInt("feepaid");
              due = db.getInt("feedue");
              
            } else {
              println("no records found");
              
            }
            if (paid < (due*0.75)){
              stat = "Unauthorized";
              elig = false;
            } else {
              stat = "Authorized";
              elig = true;
            }
            
            disp = 1;
            data = adm+","+stat;
            myPort.write(data);
            myPort.write('\n');
        } else {
          println("no records found");
          disp = 2;
          myPort.write("no data, vnbv");
          myPort.write('\n');
        }
        n = millis();
    } else {
        // connection failed !
        println("error");
        disp = 2;
    }
}

void selectPort(){
  try{
    if(debugPort) printArray(Serial.list());
    int numPorts = Serial.list().length;
    if (numPorts != 0) {
      if (numPorts >= 2) {
        COMx = (String) JOptionPane.showInputDialog(null,"Select COM port","Select port",
        JOptionPane.QUESTION_MESSAGE,null,Serial.list(), Serial.list()[0]);
        if (COMx == null) exit();
        if (COMx.isEmpty()) exit();
      } else {
        COMx = Serial.list()[0];
      }
      myPort = new Serial(this, COMx, 9600);
      myPort.bufferUntil('\n');
    } else {
      JOptionPane.showMessageDialog(frame,"No COM port available");
      exit();
    }
  }
  catch (Exception e) {
    JOptionPane.showMessageDialog(frame,"COM port " + COMx + " is not available (maybe in use by another program)");
    println("Error:", e);
    exit();
   }
}

void showInfo(){
  rectMode(CENTER);
  textAlign(LEFT, CENTER);
  shapeMode(CENTER);
  fill(255);
  noStroke();
  textSize(20);
  text("Adm: " +adm, 220, 70);
  text("Name: " + name, 220, 110);
  text("School: "+ schl, 220, 220);
  text("Course: Computer Science", 220, 260);
  text("Paid: "+ paid + " ksh", 220, 350);
  text("Bal: "+ due + " ksh", 220, 390);
  text("Eligibility", 500, 340);
  
  usr.disableStyle();
  sch.disableStyle();
  mon.disableStyle();
  ok.disableStyle();
  x.disableStyle();
  warn.disableStyle();
  finger.disableStyle();
  fill(20,200, 20);
  shape(usr, 140, 100, 70, 70);
  fill(25,200, 250);
  shape(sch, 140, 250, 70, 70);
  fill(250,200, 20);
  shape(mon, 140, 380, 70, 70);
  if (elig){
    fill(25,200, 50);
    shape(ok, 550, 390, 50, 50);
  } else {
    fill(250,20, 50);
    shape(x, 550, 390, 50, 50);
  }
  
  if(z < millis() - n){
    disp = 0;
  }
}

void port(){
  fill(200,250, 200);
  usb.disableStyle();
  shape(usb, 25, 475, 30, 30);
  noFill();
  stroke(255);
  strokeWeight(1);
  textAlign(LEFT, CENTER);
  shapeMode(CENTER);
  rectMode(CORNER);
  //rect(5, 455, 155 ,40, 5);
  textSize(15);
  text("Port: "+ COMx, 50, 475);
}

void scan(){
  rectMode(CENTER);
  textAlign(LEFT, CENTER);
  shapeMode(CENTER);
  fill(25,200, 250);
  noStroke();
  textSize(20);
  text("Place your finger on the scanner", 220, 250);
  finger.disableStyle();
  shape(finger, 140, 250, 70, 70);;
}

void err(){
  rectMode(CENTER);
  textAlign(LEFT, CENTER);
  shapeMode(CENTER);
  fill(250,120, 20);
  noStroke();
  textSize(20);
  text("No records found", 220, 250);
  warn.disableStyle();
  shape(warn, 140, 250, 70, 70);
  if(1000 < millis() - n){
    disp = 0;
  }
}