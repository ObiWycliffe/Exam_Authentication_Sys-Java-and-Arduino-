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
import java.util.regex.*;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Main_Activity_Frame extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs =null;
    PreparedStatement pst=null;

    /**
     * Creates new form Data_jframe
     */
    public Main_Activity_Frame() {
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
                    Logger.getLogger(Main_Activity_Frame.class.getName()).log(Level.SEVERE, null, ex);
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
        Menu_Panel = new javax.swing.JPanel();
        btn_1 = new javax.swing.JPanel();
        ind_1 = new javax.swing.JPanel();
        admin_panel_btn = new javax.swing.JLabel();
        btn_2 = new javax.swing.JPanel();
        ind_2 = new javax.swing.JPanel();
        admin_panel_btn1 = new javax.swing.JLabel();
        btn_3 = new javax.swing.JPanel();
        ind_3 = new javax.swing.JPanel();
        admin_panel_btn2 = new javax.swing.JLabel();
        searchStudent_panel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        student_search = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Main_Dynamic_Panel = new javax.swing.JPanel();
        All_Student_Edit_Panel = new javax.swing.JPanel();
        studentTables_panel = new javax.swing.JPanel();
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
        Header_Panel = new javax.swing.JPanel();
        All_Student_Input_Panel = new javax.swing.JPanel();
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
        MiniHeader_Panel = new javax.swing.JPanel();
        studentSupport_panel = new javax.swing.JPanel();
        registerunits = new javax.swing.JButton();
        IsoCertified_Label = new javax.swing.JLabel();
        formControl_panel = new javax.swing.JPanel();
        table_delete = new javax.swing.JButton();
        form_clear = new javax.swing.JButton();
        form_save = new javax.swing.JButton();
        table_update = new javax.swing.JButton();
        Admin_Edit_Panel = new javax.swing.JPanel();
        Admin_List_Panel = new javax.swing.JPanel();
        Admin_MiniHeader = new javax.swing.JPanel();
        Admin_Header = new javax.swing.JPanel();
        Iso_Text = new javax.swing.JLabel();
        Admin_FormContrl_Panel = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField4 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        Current_Edit_Statistics = new javax.swing.JPanel();
        Statistics_Buttons_Panel = new javax.swing.JPanel();
        Statistics_MiniHeader = new javax.swing.JPanel();
        pie_chart = new javax.swing.JButton();
        Statistics_Header = new javax.swing.JPanel();
        Chart_ContentPanel = new javax.swing.JPanel();
        StatisticsChanger = new javax.swing.JPanel();
        IsoCertified_Label1 = new javax.swing.JLabel();
        ActionControl_Panel = new javax.swing.JMenuBar();
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

        Menu_Panel.setBackground(new java.awt.Color(0, 0, 51));

        btn_1.setBackground(new java.awt.Color(153, 102, 0));
        btn_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_1MousePressed(evt);
            }
        });

        ind_1.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout ind_1Layout = new javax.swing.GroupLayout(ind_1);
        ind_1.setLayout(ind_1Layout);
        ind_1Layout.setHorizontalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        ind_1Layout.setVerticalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        admin_panel_btn.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_panel_btn.setForeground(new java.awt.Color(255, 255, 255));
        admin_panel_btn.setText("Admin Panel");

        javax.swing.GroupLayout btn_1Layout = new javax.swing.GroupLayout(btn_1);
        btn_1.setLayout(btn_1Layout);
        btn_1Layout.setHorizontalGroup(
            btn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_1Layout.createSequentialGroup()
                .addComponent(ind_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(admin_panel_btn)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        btn_1Layout.setVerticalGroup(
            btn_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_1Layout.createSequentialGroup()
                .addComponent(ind_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btn_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(admin_panel_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_2.setBackground(new java.awt.Color(0, 0, 51));
        btn_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_2MousePressed(evt);
            }
        });

        ind_2.setOpaque(false);
        ind_2.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout ind_2Layout = new javax.swing.GroupLayout(ind_2);
        ind_2.setLayout(ind_2Layout);
        ind_2Layout.setHorizontalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        ind_2Layout.setVerticalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        admin_panel_btn1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_panel_btn1.setForeground(new java.awt.Color(255, 255, 255));
        admin_panel_btn1.setText("Student Registration");

        javax.swing.GroupLayout btn_2Layout = new javax.swing.GroupLayout(btn_2);
        btn_2.setLayout(btn_2Layout);
        btn_2Layout.setHorizontalGroup(
            btn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_2Layout.createSequentialGroup()
                .addComponent(ind_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(admin_panel_btn1)
                .addGap(0, 90, Short.MAX_VALUE))
        );
        btn_2Layout.setVerticalGroup(
            btn_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_2Layout.createSequentialGroup()
                .addComponent(ind_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btn_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(admin_panel_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_3.setBackground(new java.awt.Color(0, 0, 51));
        btn_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_3MousePressed(evt);
            }
        });

        ind_3.setOpaque(false);
        ind_3.setPreferredSize(new java.awt.Dimension(3, 43));

        javax.swing.GroupLayout ind_3Layout = new javax.swing.GroupLayout(ind_3);
        ind_3.setLayout(ind_3Layout);
        ind_3Layout.setHorizontalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        ind_3Layout.setVerticalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        admin_panel_btn2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_panel_btn2.setForeground(new java.awt.Color(255, 255, 255));
        admin_panel_btn2.setText("Statistics");

        javax.swing.GroupLayout btn_3Layout = new javax.swing.GroupLayout(btn_3);
        btn_3.setLayout(btn_3Layout);
        btn_3Layout.setHorizontalGroup(
            btn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(admin_panel_btn2)
                .addGap(0, 152, Short.MAX_VALUE))
        );
        btn_3Layout.setVerticalGroup(
            btn_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btn_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(admin_panel_btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout Menu_PanelLayout = new javax.swing.GroupLayout(Menu_Panel);
        Menu_Panel.setLayout(Menu_PanelLayout);
        Menu_PanelLayout.setHorizontalGroup(
            Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Menu_PanelLayout.setVerticalGroup(
            Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_PanelLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(btn_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(409, Short.MAX_VALUE))
        );

        searchStudent_panel.setBackground(new java.awt.Color(210, 188, 121));

        jToolBar1.setRollover(true);

        student_search.setBackground(new java.awt.Color(230, 230, 230));
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

        javax.swing.GroupLayout searchStudent_panelLayout = new javax.swing.GroupLayout(searchStudent_panel);
        searchStudent_panel.setLayout(searchStudent_panelLayout);
        searchStudent_panelLayout.setHorizontalGroup(
            searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchStudent_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(student_search, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        searchStudent_panelLayout.setVerticalGroup(
            searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchStudent_panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(student_search)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(searchStudent_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Main_Dynamic_Panel.setLayout(new java.awt.CardLayout());

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

        javax.swing.GroupLayout studentTables_panelLayout = new javax.swing.GroupLayout(studentTables_panel);
        studentTables_panel.setLayout(studentTables_panelLayout);
        studentTables_panelLayout.setHorizontalGroup(
            studentTables_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentTables_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(database_tables, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        studentTables_panelLayout.setVerticalGroup(
            studentTables_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentTables_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(database_tables, javax.swing.GroupLayout.PREFERRED_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );

        Header_Panel.setBackground(new java.awt.Color(0, 0, 51));
        Header_Panel.setPreferredSize(new java.awt.Dimension(0, 105));

        javax.swing.GroupLayout Header_PanelLayout = new javax.swing.GroupLayout(Header_Panel);
        Header_Panel.setLayout(Header_PanelLayout);
        Header_PanelLayout.setHorizontalGroup(
            Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Header_PanelLayout.setVerticalGroup(
            Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        All_Student_Input_Panel.setBackground(new java.awt.Color(210, 188, 121));

        jPanel2.setBackground(new java.awt.Color(210, 188, 121));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Registration Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 153, 153))); // NOI18N

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
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(student_regno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(student_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(date_of_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(school, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
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

        MiniHeader_Panel.setPreferredSize(new java.awt.Dimension(0, 105));

        javax.swing.GroupLayout MiniHeader_PanelLayout = new javax.swing.GroupLayout(MiniHeader_Panel);
        MiniHeader_Panel.setLayout(MiniHeader_PanelLayout);
        MiniHeader_PanelLayout.setHorizontalGroup(
            MiniHeader_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        MiniHeader_PanelLayout.setVerticalGroup(
            MiniHeader_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout All_Student_Input_PanelLayout = new javax.swing.GroupLayout(All_Student_Input_Panel);
        All_Student_Input_Panel.setLayout(All_Student_Input_PanelLayout);
        All_Student_Input_PanelLayout.setHorizontalGroup(
            All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                        .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(image_attach)
                            .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(MiniHeader_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
        );
        All_Student_Input_PanelLayout.setVerticalGroup(
            All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                .addComponent(MiniHeader_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                        .addComponent(image_attach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        studentSupport_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Support", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 153, 153))); // NOI18N

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

        javax.swing.GroupLayout studentSupport_panelLayout = new javax.swing.GroupLayout(studentSupport_panel);
        studentSupport_panel.setLayout(studentSupport_panelLayout);
        studentSupport_panelLayout.setHorizontalGroup(
            studentSupport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentSupport_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerunits)
                .addContainerGap())
        );
        studentSupport_panelLayout.setVerticalGroup(
            studentSupport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentSupport_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerunits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        IsoCertified_Label.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        IsoCertified_Label.setForeground(new java.awt.Color(0, 153, 153));
        IsoCertified_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IsoCertified_Label.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        formControl_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Control", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 153, 153))); // NOI18N

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

        javax.swing.GroupLayout formControl_panelLayout = new javax.swing.GroupLayout(formControl_panel);
        formControl_panel.setLayout(formControl_panelLayout);
        formControl_panelLayout.setHorizontalGroup(
            formControl_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formControl_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formControl_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(table_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(form_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(table_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(form_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        formControl_panelLayout.setVerticalGroup(
            formControl_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formControl_panelLayout.createSequentialGroup()
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

        javax.swing.GroupLayout All_Student_Edit_PanelLayout = new javax.swing.GroupLayout(All_Student_Edit_Panel);
        All_Student_Edit_Panel.setLayout(All_Student_Edit_PanelLayout);
        All_Student_Edit_PanelLayout.setHorizontalGroup(
            All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(All_Student_Input_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Header_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, All_Student_Edit_PanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IsoCertified_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                                .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(formControl_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(studentSupport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(studentTables_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, 0))
        );
        All_Student_Edit_PanelLayout.setVerticalGroup(
            All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                        .addComponent(Header_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(formControl_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(studentSupport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addComponent(studentTables_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(IsoCertified_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(All_Student_Input_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main_Dynamic_Panel.add(All_Student_Edit_Panel, "card2");

        Admin_List_Panel.setBackground(new java.awt.Color(210, 188, 121));

        javax.swing.GroupLayout Admin_MiniHeaderLayout = new javax.swing.GroupLayout(Admin_MiniHeader);
        Admin_MiniHeader.setLayout(Admin_MiniHeaderLayout);
        Admin_MiniHeaderLayout.setHorizontalGroup(
            Admin_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        Admin_MiniHeaderLayout.setVerticalGroup(
            Admin_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Admin_List_PanelLayout = new javax.swing.GroupLayout(Admin_List_Panel);
        Admin_List_Panel.setLayout(Admin_List_PanelLayout);
        Admin_List_PanelLayout.setHorizontalGroup(
            Admin_List_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Admin_MiniHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Admin_List_PanelLayout.setVerticalGroup(
            Admin_List_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_List_PanelLayout.createSequentialGroup()
                .addComponent(Admin_MiniHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 541, Short.MAX_VALUE))
        );

        Admin_Header.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout Admin_HeaderLayout = new javax.swing.GroupLayout(Admin_Header);
        Admin_Header.setLayout(Admin_HeaderLayout);
        Admin_HeaderLayout.setHorizontalGroup(
            Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 841, Short.MAX_VALUE)
        );
        Admin_HeaderLayout.setVerticalGroup(
            Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        Iso_Text.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        Iso_Text.setForeground(new java.awt.Color(0, 153, 153));
        Iso_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Iso_Text.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        jTextField5.setText("jTextField5");

        jLabel27.setText("Last Name");

        jLabel25.setText("Gender");

        buttonGroup1.add(jCheckBox2);
        jCheckBox2.setText("jCheckBox2");

        jButton3.setText("Delete");

        jLabel23.setText("Username");

        jButton2.setText("Update");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "**Select Privilage<*", "Admin", "Support Staff" }));

        jButton1.setText("Block User");

        jTextField3.setText("jTextField3");

        jLabel24.setText("Set Privilage");

        jTextField6.setText("jTextField6");

        jTextField1.setText("jTextField1");

        jLabel26.setText("First Name");

        jLabel30.setText("Email Address");

        buttonGroup1.add(jCheckBox1);
        jCheckBox1.setText("jCheckBox1");

        jTextField4.setText("jTextField4");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel29.setText("Mobile");

        jLabel4.setText("Employment No");

        jTextField2.setText("jTextField2");

        javax.swing.GroupLayout Admin_FormContrl_PanelLayout = new javax.swing.GroupLayout(Admin_FormContrl_Panel);
        Admin_FormContrl_Panel.setLayout(Admin_FormContrl_PanelLayout);
        Admin_FormContrl_PanelLayout.setHorizontalGroup(
            Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(366, 366, 366))
                    .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29)
                            .addComponent(jLabel4)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel30)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox2))))
                        .addGap(45, 45, 45)))
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        Admin_FormContrl_PanelLayout.setVerticalGroup(
            Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jCheckBox1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)))
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(24, 24, 24)
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(27, 27, 27)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout Admin_Edit_PanelLayout = new javax.swing.GroupLayout(Admin_Edit_Panel);
        Admin_Edit_Panel.setLayout(Admin_Edit_PanelLayout);
        Admin_Edit_PanelLayout.setHorizontalGroup(
            Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_Edit_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Admin_FormContrl_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_Edit_PanelLayout.createSequentialGroup()
                .addGap(0, 365, Short.MAX_VALUE)
                .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Iso_Text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 849, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Admin_Header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Admin_Edit_PanelLayout.createSequentialGroup()
                    .addComponent(Admin_List_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 843, Short.MAX_VALUE)))
        );
        Admin_Edit_PanelLayout.setVerticalGroup(
            Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_Edit_PanelLayout.createSequentialGroup()
                .addComponent(Admin_Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(Admin_FormContrl_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(Iso_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Admin_Edit_PanelLayout.createSequentialGroup()
                    .addComponent(Admin_List_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 65, Short.MAX_VALUE)))
        );

        Main_Dynamic_Panel.add(Admin_Edit_Panel, "card3");

        Statistics_Buttons_Panel.setBackground(new java.awt.Color(210, 188, 121));

        Statistics_MiniHeader.setPreferredSize(new java.awt.Dimension(0, 105));

        javax.swing.GroupLayout Statistics_MiniHeaderLayout = new javax.swing.GroupLayout(Statistics_MiniHeader);
        Statistics_MiniHeader.setLayout(Statistics_MiniHeaderLayout);
        Statistics_MiniHeaderLayout.setHorizontalGroup(
            Statistics_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Statistics_MiniHeaderLayout.setVerticalGroup(
            Statistics_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        pie_chart.setText("Pie Chart");
        pie_chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pie_chartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Statistics_Buttons_PanelLayout = new javax.swing.GroupLayout(Statistics_Buttons_Panel);
        Statistics_Buttons_Panel.setLayout(Statistics_Buttons_PanelLayout);
        Statistics_Buttons_PanelLayout.setHorizontalGroup(
            Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Statistics_MiniHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
            .addGroup(Statistics_Buttons_PanelLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(pie_chart)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        Statistics_Buttons_PanelLayout.setVerticalGroup(
            Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistics_Buttons_PanelLayout.createSequentialGroup()
                .addComponent(Statistics_MiniHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(pie_chart)
                .addGap(0, 443, Short.MAX_VALUE))
        );

        Statistics_Header.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout Statistics_HeaderLayout = new javax.swing.GroupLayout(Statistics_Header);
        Statistics_Header.setLayout(Statistics_HeaderLayout);
        Statistics_HeaderLayout.setHorizontalGroup(
            Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Statistics_HeaderLayout.setVerticalGroup(
            Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout StatisticsChangerLayout = new javax.swing.GroupLayout(StatisticsChanger);
        StatisticsChanger.setLayout(StatisticsChangerLayout);
        StatisticsChangerLayout.setHorizontalGroup(
            StatisticsChangerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 709, Short.MAX_VALUE)
        );
        StatisticsChangerLayout.setVerticalGroup(
            StatisticsChangerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Chart_ContentPanelLayout = new javax.swing.GroupLayout(Chart_ContentPanel);
        Chart_ContentPanel.setLayout(Chart_ContentPanelLayout);
        Chart_ContentPanelLayout.setHorizontalGroup(
            Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Chart_ContentPanelLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(StatisticsChanger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        Chart_ContentPanelLayout.setVerticalGroup(
            Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Chart_ContentPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(StatisticsChanger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        IsoCertified_Label1.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        IsoCertified_Label1.setForeground(new java.awt.Color(0, 153, 153));
        IsoCertified_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IsoCertified_Label1.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        javax.swing.GroupLayout Current_Edit_StatisticsLayout = new javax.swing.GroupLayout(Current_Edit_Statistics);
        Current_Edit_Statistics.setLayout(Current_Edit_StatisticsLayout);
        Current_Edit_StatisticsLayout.setHorizontalGroup(
            Current_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Current_Edit_StatisticsLayout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addGroup(Current_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Current_Edit_StatisticsLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(Chart_ContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(Statistics_Header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Current_Edit_StatisticsLayout.createSequentialGroup()
                        .addComponent(IsoCertified_Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(Current_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Current_Edit_StatisticsLayout.createSequentialGroup()
                    .addComponent(Statistics_Buttons_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 855, Short.MAX_VALUE)))
        );
        Current_Edit_StatisticsLayout.setVerticalGroup(
            Current_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Current_Edit_StatisticsLayout.createSequentialGroup()
                .addComponent(Statistics_Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Chart_ContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(IsoCertified_Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(Current_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Statistics_Buttons_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main_Dynamic_Panel.add(Current_Edit_Statistics, "card4");

        ActionControl_Panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        ActionControl_Panel.add(jMenu1);

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

        ActionControl_Panel.add(school_regulations);

        date.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
        date.setText("Date");
        date.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ActionControl_Panel.add(date);

        setJMenuBar(ActionControl_Panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Menu_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchStudent_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Main_Dynamic_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchStudent_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Main_Dynamic_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Menu_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1467, 748));
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
            
            student_search.setForeground(new Color(38, 38, 38));
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

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void pie_chartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pie_chartActionPerformed
        // TODO add your handling code here:
        /** DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("One", new Integer(10) );
        pieDataset.setValue("Two", new Integer(20) );
        pieDataset.setValue("Three", new Integer(30) );
        pieDataset.setValue("Four", new Integer(40) );
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart", pieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
        PiePlot p = (PiePlot)chart.getPlot();
        
        ChartFrame frame = new ChartFrame("Pie Chart", chart);
        frame.setVisible(true);
        frame.setSize(400,500); */
        
        DefaultCategoryDataset barchartdata = new DefaultCategoryDataset();
        barchartdata.setValue(20000, "Amount", "January");
        barchartdata.setValue(20000, "Amount", "February");
        barchartdata.setValue(20000, "Amount", "March");
        JFreeChart barchart = ChartFactory.createAreaChart("Contribution", "Monthly", "Amount", barchartdata, PlotOrientation.VERTICAL, false,true,false);
        CategoryPlot bar = barchart.getCategoryPlot();
        bar.setRangeGridlinePaint(Color.ORANGE);
        ChartPanel barPanel = new ChartPanel (barchart);
        
        Chart_ContentPanel.removeAll();
        StatisticsChanger.add(barPanel, BorderLayout.CENTER);
        Chart_ContentPanel.repaint();
        Chart_ContentPanel.validate();
        
    }//GEN-LAST:event_pie_chartActionPerformed

    private void btn_1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_1MousePressed
        // menu btns press design:
        ActivateButtonColor(btn_1);
        ind_1.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{btn_2,btn_3}, new JPanel[]{ind_2,ind_3});
        
        
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(Admin_Edit_Panel);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
    }//GEN-LAST:event_btn_1MousePressed

    private void btn_2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_2MousePressed
        // menu btns press design:
        ActivateButtonColor(btn_2);
        ind_2.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{btn_1,btn_3}, new JPanel[]{ind_1,ind_3});
        
         
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(All_Student_Edit_Panel);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
    }//GEN-LAST:event_btn_2MousePressed

    private void btn_3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_3MousePressed
        // menu btns press design:
        ActivateButtonColor(btn_3);
        ind_3.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{btn_1,btn_2}, new JPanel[]{ind_1,ind_2});
        
         
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(Current_Edit_Statistics);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
    }//GEN-LAST:event_btn_3MousePressed

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
            java.util.logging.Logger.getLogger(Main_Activity_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Activity_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Activity_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Activity_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Activity_Frame().setVisible(true);
            }
        });
    }
    
    
    private void ActivateButtonColor(JPanel panel){
        panel.setBackground(new Color(153,102,0));
    
    }
    private void DeactivateButtonColor(JPanel [] panel, JPanel [] indicators){
        for(int i=0; i<panel.length;i++){
            panel[i].setBackground(new Color(0,0,51));
            }
        for(int i=0; i<indicators.length;i++){
            indicators[i].setOpaque(false);
            }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar ActionControl_Panel;
    private javax.swing.JPanel Admin_Edit_Panel;
    private javax.swing.JPanel Admin_FormContrl_Panel;
    private javax.swing.JPanel Admin_Header;
    private javax.swing.JPanel Admin_List_Panel;
    private javax.swing.JPanel Admin_MiniHeader;
    private javax.swing.JPanel All_Student_Edit_Panel;
    private javax.swing.JPanel All_Student_Input_Panel;
    private javax.swing.JPanel Chart_ContentPanel;
    private javax.swing.JPanel Current_Edit_Statistics;
    private javax.swing.JPanel Header_Panel;
    private javax.swing.JLabel IsoCertified_Label;
    private javax.swing.JLabel IsoCertified_Label1;
    private javax.swing.JLabel Iso_Text;
    private javax.swing.JPanel Main_Dynamic_Panel;
    private javax.swing.JPanel Menu_Panel;
    private javax.swing.JPanel MiniHeader_Panel;
    private javax.swing.JPanel StatisticsChanger;
    private javax.swing.JPanel Statistics_Buttons_Panel;
    private javax.swing.JPanel Statistics_Header;
    private javax.swing.JPanel Statistics_MiniHeader;
    private javax.swing.JLabel admin_panel_btn;
    private javax.swing.JLabel admin_panel_btn1;
    private javax.swing.JLabel admin_panel_btn2;
    private javax.swing.JPanel btn_1;
    private javax.swing.JPanel btn_2;
    private javax.swing.JPanel btn_3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton button_feebal_view;
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
    private javax.swing.JPanel formControl_panel;
    private javax.swing.JButton form_clear;
    private javax.swing.JButton form_save;
    private javax.swing.JRadioButton gender_female;
    private javax.swing.JRadioButton gender_male;
    private javax.swing.JLabel image_area;
    private javax.swing.JButton image_attach;
    private javax.swing.JDesktopPane image_dsktp_pane;
    private javax.swing.JTextField imagepath;
    private javax.swing.JPanel ind_1;
    private javax.swing.JPanel ind_2;
    private javax.swing.JPanel ind_3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField mail_attach_name;
    private javax.swing.JTextField mail_file_attach;
    private javax.swing.JTextField mail_from;
    private javax.swing.JTextField mail_subject;
    private javax.swing.JTextField mail_to;
    private javax.swing.JTextField max_fee_payable;
    private javax.swing.JTextField min_fee_allowed;
    private javax.swing.JButton pie_chart;
    private javax.swing.JButton registerunits;
    private javax.swing.JComboBox<String> school;
    private javax.swing.JMenu school_regulations;
    private javax.swing.JPanel searchStudent_panel;
    private javax.swing.JButton send_mail;
    private javax.swing.JPanel studentSupport_panel;
    private javax.swing.JPanel studentTables_panel;
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
