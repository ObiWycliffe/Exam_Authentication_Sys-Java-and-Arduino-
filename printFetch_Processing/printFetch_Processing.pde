import de.bezier.data.sql.*;
import processing.serial.*;
import javax.swing.JOptionPane; 

final boolean debugPort = true; 

Serial myPort;

MySQL db;
String user = "root", pass = "root", database = "authenticationsys"; //exam
String f_id = "", name = "", adm = "", schl = "", stat = "";
String data = "";
int bal = 0, paid = 0, due= 0;
String code = "";
String COMx = "COM3";

void setup(){
  size(700,500);
  
  
  
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
   
   db = new MySQL( this, "localhost", database, user, pass );
   
   //search("2");
}

void draw(){
  background(10);
  textSize(20);
  textAlign(CENTER,CENTER);
  text(data,width/2,height/2);
  text("Port: "+COMx,width/2,height/4);
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
              stat = "Exm:Unauthorized";
            } else {
              stat = "Exm: Authorized";
            }
            adm = "-> "+adm;
            data = adm+","+stat;
            myPort.write(data);
            myPort.write('\n');
        } else {
          println("no records found");
          myPort.write(", no data found");
          myPort.write('\n');
        }
    } else {
        // connection failed !
        println("error");
    }
}