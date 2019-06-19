/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import java.util.regex.*;
import javax.imageio.ImageIO;

public class Student_Details extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs =null;
    PreparedStatement pst=null;

    /**
     * Creates new form Data_jframe
     */
    public Student_Details() {
        initComponents();
        conn=javaconnect.ConnercrDb();
        Update_table();
        Fillcombo();
        CurrentDate();
        student_search.setFocusable(rootPaneCheckingEnabled);
        //Table_Design();
    }
    
    
    
   /**  private void Table_Design() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        student_info_table.getTableHeader().setFont(new Font("segue UI", Font.BOLD, 12));
        student_info_table.getTableHeader().setOpaque(false);
        student_info_table.getTableHeader().setBackground(new Color(32,136,203));
        student_info_table.getTableHeader().setBackground(new Color(255,255,255));
        student_info_table.setRowHeight(25);
        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    **/
    
    public void comboselect(){

        if(school.getSelectedItem()=="ace"){
            Admin_Login l = new Admin_Login();
            l.setVisible(true);
        
            }
        
        }
    
    
    public void CurrentDate(){
    
    Thread clock = new Thread(){
        public void run(){
            for(;;){
                
                Calendar cal = new GregorianCalendar();
                int day =cal.get(Calendar.DAY_OF_MONTH);
                int month =cal.get(Calendar.MONTH);
                int year =cal.get(Calendar.YEAR);
                date.setText("Date: "+day+" / "+(month+1)+"  ["+year+"]");
    
//    int second = cal.get(Calendar.SECOND);
//    int minute = cal.get(Calendar.MINUTE);
//    int hour = cal.get(Calendar.HOUR);
//    txt_time.setText("Time "+hour+":"+(minute)+":"+second);
                   
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Student_Details.class.getName()).log(Level.SEVERE, null, ex);
                }
                
              }
            }
    
    
        };
    clock.start();
    
    }
    
    public void close(){
    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
  private void Update_table(){
      
      try{
      String sql ="select student_id as 'Student Id', student_regno as 'Registration No', first_name as 'First Name', last_name as 'Last Name', surname as 'Surname', date_of_input as 'Registration Date', school as 'School', gender as 'Gender', image as 'Pass Photo'  from student_info";
      pst=conn.prepareStatement(sql);
      rs=pst.executeQuery();
      student_info_table.setModel(DbUtils.resultSetToTableModel(rs));
      
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      
        } finally {
      
            try{
            rs.close();
            //conn.close();
            }
            catch(Exception e){}
            }
  
    //fee bal table start , needs to be on new jtable  
      try{
      String sql ="select student_id as 'Student Id', feedue as 'Fee Due',feepaid as 'Fee Paid', feebal as 'Fee Balance' from feebal";
      pst=conn.prepareStatement(sql);
      rs=pst.executeQuery();
      fee_balance_table.setModel(DbUtils.resultSetToTableModel(rs));
     
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      
        } finally {
      
            try{
            rs.close();
            //conn.close();
            }
            catch(Exception e){}
            }
      //fee bal table end, needs to be on new jtable 
      
      
      
      //elligible students table fill starts
      
      try{
      String sql ="select student_id as 'Student Id', feedue as 'Fee Due', feepaid as 'Fee Paid'  from feebal";
      pst=conn.prepareStatement(sql);
      rs=pst.executeQuery();
      exam_eligible_table.setModel(DbUtils.resultSetToTableModel(rs));
     
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      
        } finally {
      
            try{
            rs.close();
            //conn.close();
            }
            catch(Exception e){}
            }
      
      //elligible students table fill ends
  
  }
  
  //function to scale down image and resize to jlabel size
  
  private Image ScaleImage(byte[] image, int w, int h){
  
      BufferedImage resizedImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        try{
        Graphics2D imageGraphic = resizedImage.createGraphics();
        imageGraphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        //byte array conversion back to buffered image
        ByteArrayInputStream in = new ByteArrayInputStream(image);
        BufferedImage bImageFromConvert = ImageIO.read(in);
        //byte array conversion back to buffered image
        
        imageGraphic.drawImage(bImageFromConvert, 0, 0, w, h, null);
        
      //imageGraphic.drawBytes(image, 0, 0, w, h);
      imageGraphic.dispose();
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
            }
      
      return resizedImage;
  
        }
  
  
  private void Fillcombo(){
     /** try{
          String sql="select * from student_info";
          pst=conn.prepareStatement(sql);
          rs=pst.executeQuery();
          
          while(rs.next()){
              String first_name =rs.getString("first_name");
              school.addItem(first_name);
          
          
            }
          
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            } **/
  }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        table_delete = new javax.swing.JButton();
        form_clear = new javax.swing.JButton();
        form_save = new javax.swing.JButton();
        table_update = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        registerunits = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        close_pane = new javax.swing.JButton();
        student_search = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        student_surname = new javax.swing.JTextField();
        student_firstname = new javax.swing.JTextField();
        studentid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        date_of_input = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        school = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        gender_male = new javax.swing.JRadioButton();
        gender_female = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        student_regno = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        student_lastname = new javax.swing.JTextField();
        image_dsktp_pane = new javax.swing.JDesktopPane();
        image_area = new javax.swing.JLabel();
        imagepath = new javax.swing.JTextField();
        image_attach = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        database_tables = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        student_info_table = new javax.swing.JTable();
        student_info_print = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fee_balance_table = new javax.swing.JTable();
        fee_balance_panel = new javax.swing.JPanel();
        fee_balance = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fee_paid = new javax.swing.JTextField();
        fee_due = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        button_feebal_view = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        exam_eligible_table = new javax.swing.JTable();
        eligible_table_print = new javax.swing.JButton();
        eligible_table_reset = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        max_fee_payable = new javax.swing.JTextField();
        min_fee_allowed = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        eligible_list_process = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        send_mail = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        student_list_mail = new javax.swing.JTextArea();
        file_attach = new javax.swing.JButton();
        mail_file_attach = new javax.swing.JTextField();
        mail_attach_name = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        mail_from = new javax.swing.JTextField();
        mail_to = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        mail_subject = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        close_pane_bar = new javax.swing.JMenuItem();
        exit_app_bar = new javax.swing.JMenuItem();
        school_regulations = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        date = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("University of Eldoret Academic Registration System - U.o.E");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Edit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        table_delete.setBackground(new java.awt.Color(204, 0, 51));
        table_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/rubbish-bin.png"))); // NOI18N
        table_delete.setText("Delete");
        table_delete.setToolTipText("Delete selected data");
        table_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_deleteActionPerformed(evt);
            }
        });

        form_clear.setBackground(new java.awt.Color(255, 153, 0));
        form_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete1.png"))); // NOI18N
        form_clear.setText("Clear");
        form_clear.setToolTipText("clear input data");
        form_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        form_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form_clearActionPerformed(evt);
            }
        });

        form_save.setBackground(new java.awt.Color(102, 204, 0));
        form_save.setForeground(new java.awt.Color(0, 153, 204));
        form_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save.png"))); // NOI18N
        form_save.setText("Save");
        form_save.setToolTipText("Save input data into database");
        form_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        form_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form_saveActionPerformed(evt);
            }
        });

        table_update.setBackground(new java.awt.Color(0, 153, 204));
        table_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/update.png"))); // NOI18N
        table_update.setText("Update");
        table_update.setToolTipText("edit ad cahnge student details");
        table_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(table_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(form_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(form_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(form_save)
                .addGap(18, 18, 18)
                .addComponent(table_delete)
                .addGap(18, 18, 18)
                .addComponent(table_update)
                .addGap(18, 18, 18)
                .addComponent(form_clear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Support", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        registerunits.setBackground(new java.awt.Color(51, 51, 0));
        registerunits.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/students-cap.png"))); // NOI18N
        registerunits.setText("Register Unit");
        registerunits.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerunits.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                registerunitsMouseMoved(evt);
            }
        });
        registerunits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerunitsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerunits)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerunits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(210, 188, 121));

        jToolBar1.setRollover(true);

        close_pane.setText("Close");
        close_pane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close_pane.setFocusable(false);
        close_pane.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        close_pane.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        close_pane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_paneActionPerformed(evt);
            }
        });
        jToolBar1.add(close_pane);

        student_search.setBackground(new java.awt.Color(0, 0, 51));
        student_search.setForeground(new java.awt.Color(153, 153, 153));
        student_search.setText("Search Student");
        student_search.setToolTipText("search for student using the student id, name or surname");
        student_search.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        student_search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                student_searchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                student_searchFocusLost(evt);
            }
        });
        student_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_searchActionPerformed(evt);
            }
        });
        student_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                student_searchKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(student_search, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(student_search)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        jPanel12.setBackground(new java.awt.Color(210, 188, 121));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(210, 188, 121));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "STUDENT INFO.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N

        student_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_firstnameActionPerformed(evt);
            }
        });

        studentid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentidActionPerformed(evt);
            }
        });
        studentid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                studentidKeyTyped(evt);
            }
        });

        jLabel1.setText("Student Id");

        jLabel3.setText("Surname");

        jLabel2.setText("First Name");

        date_of_input.setDateFormatString("yyyy-mm-dd");
        date_of_input.setMinSelectableDate(new java.util.Date(946677709000L));

        jLabel11.setText("Date of Input");

        school.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "**Select Your School<", "SCIENCE", "ENGINEERING", "EDUCATION", "BUSINESS", "HOTEL & HOSPITALITY", "FORESTRY" }));
        school.setToolTipText("Select School");
        school.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        school.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                schoolPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        school.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schoolActionPerformed(evt);
            }
        });

        jLabel15.setText("School");

        jLabel16.setText("Gender");

        gender_male.setBackground(new java.awt.Color(210, 188, 121));
        buttonGroup1.add(gender_male);
        gender_male.setText("Male");
        gender_male.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gender_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gender_maleActionPerformed(evt);
            }
        });

        gender_female.setBackground(new java.awt.Color(210, 188, 121));
        buttonGroup1.add(gender_female);
        gender_female.setText("Female");
        gender_female.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gender_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gender_femaleActionPerformed(evt);
            }
        });

        jLabel21.setText("Registration No");

        jLabel22.setText("Last Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel22)
                        .addComponent(jLabel3)
                        .addComponent(jLabel11)
                        .addComponent(jLabel15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(40, 40, 40)))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(gender_male)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(gender_female)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(studentid)
                        .addGap(18, 18, 18))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(school, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_of_input, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(student_regno, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(student_firstname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(student_lastname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(student_surname, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(student_regno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(date_of_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(school, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gender_male)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gender_female)
                .addGap(52, 52, 52))
        );

        image_dsktp_pane.setBackground(new java.awt.Color(204, 204, 204));

        image_area.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        image_dsktp_pane.setLayer(image_area, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout image_dsktp_paneLayout = new javax.swing.GroupLayout(image_dsktp_pane);
        image_dsktp_pane.setLayout(image_dsktp_paneLayout);
        image_dsktp_paneLayout.setHorizontalGroup(
            image_dsktp_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, image_dsktp_paneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(image_area, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        image_dsktp_paneLayout.setVerticalGroup(
            image_dsktp_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(image_dsktp_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image_area, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        image_attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/attachment.png"))); // NOI18N
        image_attach.setText("Attach Image");
        image_attach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        image_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                image_attachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(image_attach)
                            .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 16, Short.MAX_VALUE))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(image_attach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
        );

        student_info_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        student_info_table.setToolTipText("Student Information Table");
        student_info_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        student_info_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                student_info_tableMouseClicked(evt);
            }
        });
        student_info_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                student_info_tableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                student_info_tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(student_info_table);

        student_info_print.setBackground(new java.awt.Color(102, 102, 102));
        student_info_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        student_info_print.setText("Print");
        student_info_print.setToolTipText("Print student details list");
        student_info_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        student_info_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_info_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(student_info_print)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(student_info_print)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        database_tables.addTab("Student Info. Table", jPanel3);

        fee_balance_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        fee_balance_table.setToolTipText("Bank Fee Update Table");
        jScrollPane2.setViewportView(fee_balance_table);

        fee_balance_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Press View to Check Selected Students' Fee Balance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 153, 153))); // NOI18N

        fee_balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fee_balanceActionPerformed(evt);
            }
        });

        jLabel10.setText("Parameter:");

        jLabel7.setText("FeePaid");

        button_feebal_view.setBackground(new java.awt.Color(255, 0, 153));
        button_feebal_view.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view.png"))); // NOI18N
        button_feebal_view.setText("View");
        button_feebal_view.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_feebal_view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_feebal_viewActionPerformed(evt);
            }
        });

        jLabel8.setText("FeeBal");

        jLabel9.setText("Payment:");

        jLabel6.setText("FeeDue");

        javax.swing.GroupLayout fee_balance_panelLayout = new javax.swing.GroupLayout(fee_balance_panel);
        fee_balance_panel.setLayout(fee_balance_panelLayout);
        fee_balance_panelLayout.setHorizontalGroup(
            fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fee_balance_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(23, 23, 23)
                .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_feebal_view)
                    .addGroup(fee_balance_panelLayout.createSequentialGroup()
                        .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(fee_balance_panelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(56, 56, 56))
                            .addGroup(fee_balance_panelLayout.createSequentialGroup()
                                .addComponent(fee_due)
                                .addGap(13, 13, 13)))
                        .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fee_paid, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(fee_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        fee_balance_panelLayout.setVerticalGroup(
            fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fee_balance_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(fee_balance_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fee_due, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fee_paid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fee_balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_feebal_view)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(fee_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fee_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(285, 285, 285))
        );

        database_tables.addTab("Fee Balance Check", jPanel4);

        exam_eligible_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        exam_eligible_table.setToolTipText("View Candidates Eligible for Examinations");
        jScrollPane3.setViewportView(exam_eligible_table);

        eligible_table_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        eligible_table_print.setText("Print");
        eligible_table_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eligible_table_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eligible_table_printActionPerformed(evt);
            }
        });

        eligible_table_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reset.png"))); // NOI18N
        eligible_table_reset.setText("Reset");
        eligible_table_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eligible_table_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eligible_table_resetActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "View Students' Fee Balance on Selection", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 11), new java.awt.Color(0, 153, 153))); // NOI18N

        max_fee_payable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                max_fee_payableActionPerformed(evt);
            }
        });
        max_fee_payable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                max_fee_payableKeyTyped(evt);
            }
        });

        min_fee_allowed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                min_fee_allowedKeyTyped(evt);
            }
        });

        jLabel14.setText("Max Fee Payable:");

        eligible_list_process.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/process-arrows.png"))); // NOI18N
        eligible_list_process.setText("Process");
        eligible_list_process.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eligible_list_process.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eligible_list_processActionPerformed(evt);
            }
        });

        jLabel12.setText("Set Range Values:");

        jLabel13.setText("Min Fee Allowed:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eligible_list_process)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(min_fee_allowed, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addComponent(max_fee_payable)))
                .addGap(0, 57, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(min_fee_allowed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(max_fee_payable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eligible_list_process)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(eligible_table_reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eligible_table_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(eligible_table_reset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eligible_table_print)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        database_tables.addTab("Eligible Candidates", jPanel5);

        send_mail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email.png"))); // NOI18N
        send_mail.setText("Send Mail");
        send_mail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        send_mail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_mailActionPerformed(evt);
            }
        });

        student_list_mail.setColumns(20);
        student_list_mail.setRows(5);
        student_list_mail.setToolTipText("Write Mail Area");
        jScrollPane5.setViewportView(student_list_mail);

        file_attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/attachment.png"))); // NOI18N
        file_attach.setText("Attach File");
        file_attach.setToolTipText("Attach File Document");
        file_attach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        file_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                file_attachActionPerformed(evt);
            }
        });

        mail_file_attach.setToolTipText("Attached File Path");
        mail_file_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mail_file_attachActionPerformed(evt);
            }
        });

        mail_attach_name.setToolTipText("Enter New Doccument's Name");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel20.setText("Rename:");

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setText("Subject:");

        mail_from.setToolTipText("Enter Your Email Address");
        mail_from.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mail_fromActionPerformed(evt);
            }
        });

        mail_to.setToolTipText("Enter Recipients Email Address");

        jLabel18.setText("To:");

        jLabel17.setText("From:");

        mail_subject.setToolTipText("Mail Subject");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(mail_subject, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mail_to, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mail_from, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(mail_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(mail_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mail_subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(send_mail, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(mail_attach_name, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(mail_file_attach))))
                .addGap(58, 58, 58))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mail_file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(file_attach))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mail_attach_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(31, 31, 31)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(send_mail)
                .addGap(23, 23, 23))
        );

        database_tables.addTab("Mail Student List", jPanel7);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(database_tables, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(database_tables, javax.swing.GroupLayout.PREFERRED_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        jPanel15.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );

        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/briefcase.png"))); // NOI18N
        jMenu1.setText("Action Panel");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/file.png"))); // NOI18N
        jMenuItem1.setText("Open File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        close_pane_bar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        close_pane_bar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        close_pane_bar.setText("Close");
        close_pane_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_pane_barActionPerformed(evt);
            }
        });
        jMenu1.add(close_pane_bar);

        exit_app_bar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exit_app_bar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        exit_app_bar.setText("Log Out");
        exit_app_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_app_barActionPerformed(evt);
            }
        });
        jMenu1.add(exit_app_bar);

        jMenuBar1.add(jMenu1);

        school_regulations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/question.png"))); // NOI18N
        school_regulations.setText("Help");
        school_regulations.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/information.png"))); // NOI18N
        jMenuItem3.setText("School Regulation");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        school_regulations.add(jMenuItem3);

        jMenuBar1.add(school_regulations);

        date.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
        date.setText("Date");
        date.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar1.add(date);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(83, 83, 83)))
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1394, 765));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void schoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schoolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_schoolActionPerformed

    private void student_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_student_firstnameActionPerformed

    private void student_info_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_info_tableMouseClicked
        
        int row =student_info_table.getSelectedRow();
            String Table_click=(student_info_table.getModel().getValueAt(row, 0).toString());
        
        try{
        
            String sql="select image from student_info where student_id ='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            if(rs.next()){
                byte[]imagedata =rs.getBytes("image");
                //format =new ImageIcon(imagedata);
                
                
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                
                image_area.setIcon(format);
                }
            
            }catch(Exception e){
                e.printStackTrace();
                }
        
        
        try{
            
            String sql="select * from student_info where student_id='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                
                String add1 =rs.getString("student_id");
                studentid.setText(add1);
                
                String add2 =rs.getString("student_regno");
                student_regno.setText(add2);
                  
                String add3 =rs.getString("first_name");
                student_firstname.setText(add3);
                
                String add4 =rs.getString("last_name");
                student_lastname.setText(add4);
                
                String add5 =rs.getString("surname");
                student_surname.setText(add5);
                
                Date add6 =rs.getDate("date_of_input");
                date_of_input.setDate(add6);
               
                String add7 =rs.getString("school");
                school.setSelectedItem(add7);

//                String add8 =rs.getString("gender");
//                buttonGroup1.isSelected(add8);
              
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_student_info_tableMouseClicked

    private void close_paneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_paneActionPerformed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_close_paneActionPerformed

    private void form_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form_saveActionPerformed
        // TODO add your handling code here:
        try{
            String sql ="insert into student_info (student_id, student_regno, first_name, last_name,  surname, date_of_input, school,  gender, image) values (?,?,?,?,?,?,?,?,?)";
            
            pst=conn.prepareStatement(sql);
            pst.setString(1, studentid.getText());
            pst.setString(2, student_regno.getText());
            pst.setString(3, student_firstname.getText());
            pst.setString(4, student_lastname.getText());
            pst.setString(5, student_surname.getText());
            
            pst.setString(6, ((JTextField)date_of_input.getDateEditor().getUiComponent()).getText());
           
            String value=school.getSelectedItem().toString();
            pst.setString(7, value);
            pst.setString(8, gender);
            pst.setBytes(9, person_image);
            
            pst.execute();
            
            //Confirmation to save student dialog box
            
            String confirm="<html> Student Id = "+studentid.getText()+" <br>"
                                + "First Name = "+student_firstname.getText()+"<br>"
                                + "Surname = "+student_surname.getText()+"</html>";
            JOptionPane optionpane = new JOptionPane();
            optionpane.setMessage(confirm);
            optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionpane.createDialog(null, "SAVE NEW STUDENT?");
            dialog.setVisible(true);
            //Confirmation to save student dialog box
            
            JOptionPane.showMessageDialog(null, "Saved");
        
        
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
    }//GEN-LAST:event_form_saveActionPerformed

    private void table_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_deleteActionPerformed
       
        //Delete confirmation Dialog
        int p = JOptionPane.showConfirmDialog(null, "Do You Want To Delete","Delete",JOptionPane.YES_NO_OPTION);
        if(p==0){
        
        String sql="delete from student_info where student_id =?";
        try{
        
            pst=conn.prepareStatement(sql);
            
            pst.setString(1, studentid.getText());
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted");
        
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
        }
        
    }//GEN-LAST:event_table_deleteActionPerformed

    private void table_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_updateActionPerformed
        // TODO add your handling code here:
        
        try{
        
                String value1= studentid.getText();
                String value2= student_regno.getText();
                String value3= student_firstname.getText();
                String value4= student_lastname.getText();
                String value5= student_surname.getText();
                String value6= (((JTextField)date_of_input.getDateEditor().getUiComponent()).getText());
                
                String value7= school.getSelectedItem().toString();
                //String value8= gender.getText() ;
                //byte[]imagedata value9= image_area.getBytes("image");
            
        
                String sql="update student_info set student_id='"+value1+"' , student_regno='"+value2+"' , first_name='"+value3+"' , last_name='"+value4+"' ,  surname='"+value5+"' , date_of_input='"+value6+"' , school='"+value7+"' where student_id='"+value1+"'";
                pst=conn.prepareStatement(sql);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Updated Succesfully");
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
        
    }//GEN-LAST:event_table_updateActionPerformed

    private void form_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form_clearActionPerformed
        // TODO add your handling code here:
        
        ((JTextField)date_of_input.getDateEditor().getUiComponent()).setText("");
        studentid.setText("");
        student_firstname.setText("");
        student_lastname.setText("");
        student_surname.setText("");
        student_regno.setText("");
        school.setSelectedItem("**Select Your School<");
        buttonGroup1.clearSelection();
        image_area.setIcon(format);
        
        
    }//GEN-LAST:event_form_clearActionPerformed

    private void student_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_searchKeyReleased
        
        //Search on basis of First Name
        try{
          
                String sql="select * from student_info where first_name = ?";
                
                pst=conn.prepareStatement(sql);
                pst.setString(1, student_search.getText());
                
                rs=pst.executeQuery();
                if(rs.next()){
                    
                String add1 =rs.getString("student_id");
                studentid.setText(add1);
                
                String add2 =rs.getString("student_regno");
                student_regno.setText(add2);
                  
                String add3 =rs.getString("first_name");
                student_firstname.setText(add3);
                
                String add4 =rs.getString("last_name");
                student_lastname.setText(add4);
                
                String add5 =rs.getString("surname");
                student_surname.setText(add5);
                
                Date add6 =rs.getDate("date_of_input");
                date_of_input.setDate(add6);
               
                String add7 =rs.getString("school");
                school.setSelectedItem(add7);

//                String add8 =rs.getString("gender");
//                gender.setText(add8);
                
                byte[]imagedata =rs.getBytes("image");
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                
                    }
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        
        
        
        //Search on basis of Surname
        try{
          
                String sql="select * from student_info where surname = ?";
                
                pst=conn.prepareStatement(sql);
                pst.setString(1, student_search.getText());
                
                rs=pst.executeQuery();
                if(rs.next()){
                    String add1 =rs.getString("student_id");
                studentid.setText(add1);
                
                String add2 =rs.getString("student_regno");
                student_regno.setText(add2);
                  
                String add3 =rs.getString("first_name");
                student_firstname.setText(add3);
                
                String add4 =rs.getString("last_name");
                student_lastname.setText(add4);
                
                String add5 =rs.getString("surname");
                student_surname.setText(add5);
                
                Date add6 =rs.getDate("date_of_input");
                date_of_input.setDate(add6);
               
                String add7 =rs.getString("school");
                school.setSelectedItem(add7);

//                String add8 =rs.getString("gender");
//                gender.setText(add8);
                
                byte[]imagedata =rs.getBytes("image");
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                
                    }
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }

                
                
          //Search on basis of Registration No      
         try{
          
                String sql="select * from student_info where student_regno = ?";
                
                pst=conn.prepareStatement(sql);
                pst.setString(1, student_search.getText());
                
                rs=pst.executeQuery();
                if(rs.next()){
                    String add1 =rs.getString("student_id");
                studentid.setText(add1);
                
                String add2 =rs.getString("student_regno");
                student_regno.setText(add2);
                  
                String add3 =rs.getString("first_name");
                student_firstname.setText(add3);
                
                String add4 =rs.getString("last_name");
                student_lastname.setText(add4);
                
                String add5 =rs.getString("surname");
                student_surname.setText(add5);
                
                Date add6 =rs.getDate("date_of_input");
                date_of_input.setDate(add6);
               
                String add7 =rs.getString("school");
                school.setSelectedItem(add7);

//                String add8 =rs.getString("gender");
//                gender.setText(add8);
                
                byte[]imagedata =rs.getBytes("image");
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                
                    }
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
    }//GEN-LAST:event_student_searchKeyReleased

    private void student_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_student_searchActionPerformed

    private void student_info_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_info_tableKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_student_info_tableKeyPressed

    private void student_info_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_info_printActionPerformed
        // TODO add your handling code here:
        
        MessageFormat header = new MessageFormat("Report Print");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        
        try{
        
            student_info_table.print(JTable.PrintMode.NORMAL, header, footer);
        
            }catch (java.awt.print.PrinterException e){
            
                System.err.format("Cannot print %s%n", e.getMessage());
            
                }
        
    }//GEN-LAST:event_student_info_printActionPerformed

    private void schoolPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_schoolPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        comboselect();
    }//GEN-LAST:event_schoolPopupMenuWillBecomeInvisible

    private void fee_balanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fee_balanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fee_balanceActionPerformed

    private void button_feebal_viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_feebal_viewActionPerformed
        // TODO add your handling code here:
         
             int row =fee_balance_table.getSelectedRow();
            String Table_click=(fee_balance_table.getModel().getValueAt(row, 0).toString());
            
        try{
            
            String sql ="select feedue, feepaid from feebal where student_id='"+Table_click+"'";

            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
            String add1=rs.getString("feedue");
            fee_due.setText(add1);
            String add2=rs.getString("feepaid");
            fee_paid.setText(add2);
            float bal1 = Float.parseFloat(add1); 
            float bal2 = Float.parseFloat(add2) ;
            float bal = bal1-bal2;
            String add3= Float.toString(bal);
            fee_balance.setText(add3);
            }
        
            }catch(SQLException e){
            
                JOptionPane.showMessageDialog(null, e);
            
                }
    }//GEN-LAST:event_button_feebal_viewActionPerformed

    private void eligible_list_processActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eligible_list_processActionPerformed
        
        String val1=min_fee_allowed.getText();
        String val2=max_fee_payable.getText();
        
        try{
            String sql = "select student_id as 'Student Id', feepaid as 'Fee Paid' from feebal where feepaid between '"+val1+"' and '"+val2+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            exam_eligible_table.setModel(DbUtils.resultSetToTableModel(rs));
        
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                }
        
    }//GEN-LAST:event_eligible_list_processActionPerformed

    private void eligible_table_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eligible_table_printActionPerformed
        
         MessageFormat header = new MessageFormat("Eligible Students Listing");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        
        try{
        
            exam_eligible_table.print(JTable.PrintMode.NORMAL, header, footer);
        
            }catch (java.awt.print.PrinterException e){
            
                System.err.format("Cannot print %s%n", e.getMessage());
            
                }
        
        
    }//GEN-LAST:event_eligible_table_printActionPerformed

    private void max_fee_payableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_max_fee_payableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_max_fee_payableActionPerformed

    private void student_info_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_info_tableKeyReleased

        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        
             try{
            
            int row =student_info_table.getSelectedRow();
            String Table_click=(student_info_table.getModel().getValueAt(row, 0).toString());
            String sql="select * from student_info where student_id='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                
               String add1 =rs.getString("student_id");
                studentid.setText(add1);
                
                String add2 =rs.getString("student_regno");
                student_regno.setText(add2);
                  
                String add3 =rs.getString("first_name");
                student_firstname.setText(add3);
                
                String add4 =rs.getString("last_name");
                student_lastname.setText(add4);
                
                String add5 =rs.getString("surname");
                student_surname.setText(add5);
                
                Date add6 =rs.getDate("date_of_input");
                date_of_input.setDate(add6);
               
                String add7 =rs.getString("school");
                school.setSelectedItem(add7);

//                String add8 =rs.getString("gender");
//                gender.setText(add8);
                
                byte[]imagedata =rs.getBytes("image");
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        
             }
        
    }//GEN-LAST:event_student_info_tableKeyReleased

    private void eligible_table_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eligible_table_resetActionPerformed
        
        Update_table(); //refresh table eligiblecandidates after serching
        
    }//GEN-LAST:event_eligible_table_resetActionPerformed

    private void registerunitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerunitsActionPerformed

        try{
            String URL = "";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null, e.getMessage());
                }
    }//GEN-LAST:event_registerunitsActionPerformed

    private void image_attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_image_attachActionPerformed
        
        JFileChooser chooser =new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename =f.getAbsolutePath();
        //String filename1=f.get();
        imagepath.setText(filename);
        
        try{
            File image =new File (filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum; (readNum=fis.read(buf))!=-1;){
            
                bos.write(buf,0,readNum);
                }
        
            person_image=bos.toByteArray();
        
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                }
        
    }//GEN-LAST:event_image_attachActionPerformed

    private void gender_maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gender_maleActionPerformed
        
        gender="male";
    }//GEN-LAST:event_gender_maleActionPerformed

    private void gender_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gender_femaleActionPerformed
        
        gender="female";
    }//GEN-LAST:event_gender_femaleActionPerformed

    private void send_mailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_mailActionPerformed
        
        //Check Mail Format .. AlphaNum@Alpha.com || abc@gmail.com
        String EMAIL_PATTERN = "^[a-zA-Z0-9]{1,20}@[a-zA-Z0-9]{1,20}.[a-zA-Z]{2,3}$";
        
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher regexMatcher = pattern.matcher(mail_from.getText());
        if(!regexMatcher.matches()){
                JOptionPane.showMessageDialog(null, "Email Format is not Correct");
                }else{
                         JOptionPane.showMessageDialog(null, "Email is Correct");
                     }
        
        
        
        
        
        String From=mail_from.getText();
        String To=mail_to.getText();
        String Subject=mail_subject.getText();
        String Mail_Text=student_list_mail.getText();
        
        //email array selection start - variable array of type string;
        String [] copyTo;
        copyTo = To.split(",");
        //email array selection end;
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.google.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.SSLSocket");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        Session session=Session.getDefaultInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("abc@gmail.com", "password");
                        }
                    }
                );
        for(int i=0; i<copyTo.length; i++){
        
        try{
        
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(From));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(copyTo[i]));
            message.setSubject(Subject);
            
            MimeBodyPart messageBodyPart= new MimeBodyPart();
            messageBodyPart.setText(Mail_Text);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
            //Code for attaching file:
            messageBodyPart = new MimeBodyPart();
            javax.activation.DataSource source = new FileDataSource(attachment_path);
            messageBodyPart.setDataHandler(new DataHandler(source));
            //Set name to attached file start:
            messageBodyPart.setFileName(mail_attach_name.getText());
             //Set name to attached file ends
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            
            Transport.send(message);
            
            JOptionPane.showMessageDialog(null, "message sent");
        
            }catch(Exception e){
               
            JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_send_mailActionPerformed

    private void mail_fromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mail_fromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mail_fromActionPerformed

    private void file_attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_attachActionPerformed
        
        JFileChooser chooser =new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        attachment_path =f.getAbsolutePath();
        //String filename1=f.get();
        mail_file_attach.setText(attachment_path);
        
        
    }//GEN-LAST:event_file_attachActionPerformed

    private void mail_file_attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mail_file_attachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mail_file_attachActionPerformed

    private void registerunitsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerunitsMouseMoved
        // TODO add your handling code here:
        
        // change cursor on hover mode
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        registerunits.setCursor(cursor);
    }//GEN-LAST:event_registerunitsMouseMoved

    private void min_fee_allowedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_min_fee_allowedKeyTyped
        
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || c== KeyEvent.VK_BACK_SPACE || c== KeyEvent.VK_DELETE)){
            
            getToolkit().beep();
            evt.consume();
        
            }
    }//GEN-LAST:event_min_fee_allowedKeyTyped

    private void max_fee_payableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_max_fee_payableKeyTyped
        
         char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || c== KeyEvent.VK_BACK_SPACE || c== KeyEvent.VK_DELETE)){
            
            getToolkit().beep();
            evt.consume();
        
            }
    }//GEN-LAST:event_max_fee_payableKeyTyped

    private void studentidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_studentidKeyTyped
        
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || c== KeyEvent.VK_BACK_SPACE || c== KeyEvent.VK_DELETE)){
            
            getToolkit().beep();
            evt.consume();
        
            }
    }//GEN-LAST:event_studentidKeyTyped

    private void studentidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentidActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        try{

            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"G:\\StuDy\\PDF Tutorial(s)\\Operating System Concepts.pdf");

        }catch(Exception e){

            JOptionPane.showMessageDialog(null, "Error");

        }

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void exit_app_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_app_barActionPerformed
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_exit_app_barActionPerformed

    private void close_pane_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_pane_barActionPerformed
        // TODO add your handling code here:
        close();

    }//GEN-LAST:event_close_pane_barActionPerformed

    private void student_searchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_student_searchFocusGained
        // Code for adding place-holder on student search button
        if(student_search.getText().trim().equals("Search Student")){
            student_search.setText("");
            
            student_search.setForeground(new Color(0,204,204));
            }
    }//GEN-LAST:event_student_searchFocusGained

    private void student_searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_student_searchFocusLost
        // Code for reseting place-holder on student search button
        if(student_search.getText().trim().equals("")){
            student_search.setText("Search Student");
            
            student_search.setForeground(new Color(153,153,153));
            }
    }//GEN-LAST:event_student_searchFocusLost

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Student_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Student_Details().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton button_feebal_view;
    private javax.swing.JButton close_pane;
    private javax.swing.JMenuItem close_pane_bar;
    private javax.swing.JTabbedPane database_tables;
    private javax.swing.JMenu date;
    private com.toedter.calendar.JDateChooser date_of_input;
    private javax.swing.JButton eligible_list_process;
    private javax.swing.JButton eligible_table_print;
    private javax.swing.JButton eligible_table_reset;
    private javax.swing.JTable exam_eligible_table;
    private javax.swing.JMenuItem exit_app_bar;
    private javax.swing.JTextField fee_balance;
    private javax.swing.JPanel fee_balance_panel;
    private javax.swing.JTable fee_balance_table;
    private javax.swing.JTextField fee_due;
    private javax.swing.JTextField fee_paid;
    private javax.swing.JButton file_attach;
    private javax.swing.JButton form_clear;
    private javax.swing.JButton form_save;
    private javax.swing.JRadioButton gender_female;
    private javax.swing.JRadioButton gender_male;
    private javax.swing.JLabel image_area;
    private javax.swing.JButton image_attach;
    private javax.swing.JDesktopPane image_dsktp_pane;
    private javax.swing.JTextField imagepath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField mail_attach_name;
    private javax.swing.JTextField mail_file_attach;
    private javax.swing.JTextField mail_from;
    private javax.swing.JTextField mail_subject;
    private javax.swing.JTextField mail_to;
    private javax.swing.JTextField max_fee_payable;
    private javax.swing.JTextField min_fee_allowed;
    private javax.swing.JButton registerunits;
    private javax.swing.JComboBox<String> school;
    private javax.swing.JMenu school_regulations;
    private javax.swing.JButton send_mail;
    private javax.swing.JTextField student_firstname;
    private javax.swing.JButton student_info_print;
    private javax.swing.JTable student_info_table;
    private javax.swing.JTextField student_lastname;
    private javax.swing.JTextArea student_list_mail;
    private javax.swing.JTextField student_regno;
    private javax.swing.JTextField student_search;
    private javax.swing.JTextField student_surname;
    public static javax.swing.JTextField studentid;
    private javax.swing.JButton table_delete;
    private javax.swing.JButton table_update;
    // End of variables declaration//GEN-END:variables

    Timer timer;
    private ImageIcon format =null;
    String filename=null;
    int s=0;
    byte[] person_image = null;
    private String gender;
    String attachment_path;

}
