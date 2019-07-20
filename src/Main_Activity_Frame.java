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
import java.text.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

public class Main_Activity_Frame extends javax.swing.JFrame {
    Connection conn=null;
    ResultSet rs =null;
    ResultSet rs1 =null;
    ResultSet rs2 =null;
    ResultSet rs3 =null;
    ResultSet rs4 =null;
    ResultSet rs5 =null;
    PreparedStatement pst=null;
    PreparedStatement pst1=null;
    PreparedStatement pst2=null;
    PreparedStatement pst3=null;
    PreparedStatement pst4=null;
    PreparedStatement pst5=null;
    
    
    /**
     * Creates new form Data_jframe
     */
    public Main_Activity_Frame() {
        initComponents();
        conn=javaconnect.ConnercrDb();
        Update_table();
        Fillcombo();
        dispose();
        FunctionControl.CurrentDate();
        //FunctionControl.AnchorLabel();
        student_search.setFocusable(rootPaneCheckingEnabled);
        
        
        //Deactivates Window Maximize button
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
        
        //Table Design Code Start
        student_info_table.getTableHeader().setFont(new Font("Segoe Ui", Font.BOLD, 12));
        student_info_table.getTableHeader().setOpaque(false);
        student_info_table.getTableHeader().setBackground(new Color(0,0,51));
        student_info_table.getTableHeader().setForeground(new Color(255,255,255));
        student_info_table.setRowHeight(25);
        
        fee_balance_table.getTableHeader().setFont(new Font("Segoe Ui", Font.BOLD, 12));
        fee_balance_table.getTableHeader().setOpaque(false);
        fee_balance_table.getTableHeader().setBackground(new Color(0,0,51));
        fee_balance_table.getTableHeader().setForeground(new Color(255,255,255));
        fee_balance_table.setRowHeight(25);
        
        exam_eligible_table.getTableHeader().setFont(new Font("Segoe Ui", Font.BOLD, 12));
        exam_eligible_table.getTableHeader().setOpaque(false);
        exam_eligible_table.getTableHeader().setBackground(new Color(0,0,51));
        exam_eligible_table.getTableHeader().setForeground(new Color(255,255,255));
        exam_eligible_table.setRowHeight(25);
        
        admin_signup_list.getTableHeader().setFont(new Font("Segoe Ui", Font.BOLD, 12));
        admin_signup_list.getTableHeader().setOpaque(false);
        admin_signup_list.getTableHeader().setBackground(new Color(0,0,51));
        admin_signup_list.getTableHeader().setForeground(new Color(255,255,255));
        admin_signup_list.setRowHeight(25);
        //Table Design Code End
        
    }
    
    public void comboselect(){

        }
    
    public void close(){
    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
    private void Update_table(){
      
      try{
      String sql ="select student_regno as 'Registration No', first_name as 'First Name', last_name as 'Last Name', surname as 'Surname', date_of_input as 'Registration Date', school as 'School', gender as 'Gender' from student_info";
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
      String sql ="select student_id as 'No.', student_regno as 'Registration No', feedue as 'Fee Due',feepaid as 'Fee Paid', feebal as 'Fee Balance' from feebal";
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
      String sql ="select student_id as 'No.', student_regno as 'Registration No', feedue as 'Fee Due', feepaid as 'Fee Paid'  from feebal";
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
      
      //Admin Table fill starts
      
      try{
      String sql ="select admin_id as 'No.', username as 'Signed Up Admin' from admin_login";
      pst=conn.prepareStatement(sql);
      rs=pst.executeQuery();
      admin_signup_list.setModel(DbUtils.resultSetToTableModel(rs));
      
      }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
      
        } finally {
                try{
                rs.close();
                //conn.close();
                }
                catch(Exception e){}
                }
      //Admin Table fill ends
      
      
      
      try{
           try{
             
            String sql="select count(*) from student_info";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery(sql);
            
//            int p = Integer.parseInt(sql1);
//            int n = Integer.parseInt(sql2);
            
            if(rs.next()){
                String m =rs.getString("count(*)");
                Students_barNo.setText(m);
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        // Total Students Bar Fill End
        
        //Student Fee Count Fill Start
         try{
            String sql="select * from feebal";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery(sql);
            
            int q = 0;
            while(rs.next()){
                double pend = rs.getDouble("feepaid");
                double due = rs.getDouble("feedue");
                
                double cutPoint = due*0.75;
                if (pend >= cutPoint){
                    q++;
                }
            }
            ElligibleStudets_barNo.setText(Integer.toString(q));
                    
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
         //Elligible Students Bar Count Fill End
         
         
         //Total System Staff Fill Start
         try{
            
            String sql="select count(*) from admin_login";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery(sql);
            
//            int p = Integer.parseInt(sql1);
//            int n = Integer.parseInt(sql2);
            
            if(rs.next()){
                String m =rs.getString("count(*)");
                Staff_barNo.setText(m);
                }
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
      
        }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }
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
             JOptionPane.showMessageDialog(null, "Student image not available");
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
        menu_panel_btn4 = new javax.swing.JPanel();
        ind_4 = new javax.swing.JPanel();
        menu_label_btn4 = new javax.swing.JLabel();
        menu_panel_btn2 = new javax.swing.JPanel();
        ind_2 = new javax.swing.JPanel();
        menu_label_btn2 = new javax.swing.JLabel();
        menu_panel_btn3 = new javax.swing.JPanel();
        ind_3 = new javax.swing.JPanel();
        menu_label_btn3 = new javax.swing.JLabel();
        menu_panel_btn1 = new javax.swing.JPanel();
        ind_1 = new javax.swing.JPanel();
        menu_label_btn1 = new javax.swing.JLabel();
        menu_panel_btn5 = new javax.swing.JPanel();
        ind_7 = new javax.swing.JPanel();
        menu_label_btn5 = new javax.swing.JLabel();
        Account_DisplayPanel = new javax.swing.JPanel();
        user_label = new javax.swing.JLabel();
        account_label = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        searchStudent_panel = new javax.swing.JPanel();
        student_search = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        Students_barNo = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        Staff_barNo = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        ElligibleStudets_barNo = new javax.swing.JLabel();
        Main_Dynamic_Panel = new javax.swing.JPanel();
        System_Edit_Dashboard = new javax.swing.JPanel();
        Dashboard_Chart = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        male_count = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        female_count = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        total_count = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        cleared_fee = new javax.swing.JLabel();
        pending_fee = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        zero_fee = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        support_staff = new javax.swing.JLabel();
        admin_staff = new javax.swing.JLabel();
        all_staff = new javax.swing.JLabel();
        IsoCertified_Label2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        uoe_admin_pic = new javax.swing.JLabel();
        uoe_students_pic = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        viewAll = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        uoe_header_logo = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
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
        jButton5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        eligibility_status = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
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
        MiniHeader_Panel = new javax.swing.JPanel();
        AllStudent_UoeHeader = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        All_Student_Input_Panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        student_surname = new javax.swing.JTextField();
        student_firstname = new javax.swing.JTextField();
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
        studentid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        studentSupport_panel = new javax.swing.JPanel();
        registerunits = new javax.swing.JButton();
        IsoCertified_Label = new javax.swing.JLabel();
        formControl_panel = new javax.swing.JPanel();
        table_delete = new javax.swing.JButton();
        form_clear = new javax.swing.JButton();
        form_save = new javax.swing.JButton();
        table_update = new javax.swing.JButton();
        Graphical_Edit_Statistics = new javax.swing.JPanel();
        Statistics_Buttons_Panel = new javax.swing.JPanel();
        pie_chart = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        Statistics_Header = new javax.swing.JPanel();
        Statistics_MiniHeader = new javax.swing.JPanel();
        GraphicalStat_UoeHeader = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        Chart_ContentPanel = new javax.swing.JPanel();
        StatisticsChanger = new javax.swing.JPanel();
        male_female_pie = new javax.swing.JPanel();
        fee_StatsChart = new javax.swing.JPanel();
        IsoCertified_Label1 = new javax.swing.JLabel();
        Admin_Edit_Panel = new javax.swing.JPanel();
        Admin_List_Panel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        admin_signup_list = new javax.swing.JTable();
        admin_list_refresh = new javax.swing.JButton();
        Admin_Header = new javax.swing.JPanel();
        Admin_MiniHeader = new javax.swing.JPanel();
        AdminUoe_Header = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        Iso_Text = new javax.swing.JLabel();
        Admin_FormContrl_Panel = new javax.swing.JPanel();
        Privilage_Edit_Panel = new javax.swing.JPanel();
        account_privallage = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        privillage_update = new javax.swing.JButton();
        account_unblock = new javax.swing.JButton();
        account_block = new javax.swing.JButton();
        account_delete = new javax.swing.JButton();
        Admin_Autofill_Panel = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_employment_no = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        admin_gender_male = new javax.swing.JCheckBox();
        txt_last_name = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_email_address = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_mobile = new javax.swing.JTextField();
        admin_gender_female = new javax.swing.JCheckBox();
        Admin_privilage_Warning = new javax.swing.JLabel();
        Admin_block_Warning = new javax.swing.JLabel();
        ActionControl_Panel = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        close_pane_bar = new javax.swing.JMenuItem();
        log_out_bar = new javax.swing.JMenuItem();
        school_regulations = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        date = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("University of Eldoret Academic Registration System - U.o.E");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Menu_Panel.setBackground(new java.awt.Color(0, 0, 51));
        Menu_Panel.setPreferredSize(new java.awt.Dimension(220, 400));

        menu_panel_btn4.setBackground(new java.awt.Color(0, 0, 51));
        menu_panel_btn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_panel_btn4MousePressed(evt);
            }
        });

        ind_4.setBackground(new java.awt.Color(102, 34, 0));
        ind_4.setOpaque(false);
        ind_4.setPreferredSize(new java.awt.Dimension(4, 43));

        javax.swing.GroupLayout ind_4Layout = new javax.swing.GroupLayout(ind_4);
        ind_4.setLayout(ind_4Layout);
        ind_4Layout.setHorizontalGroup(
            ind_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_4Layout.setVerticalGroup(
            ind_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        menu_label_btn4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        menu_label_btn4.setForeground(new java.awt.Color(255, 255, 255));
        menu_label_btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/admin.png"))); // NOI18N
        menu_label_btn4.setText("Admin Panel");
        menu_label_btn4.setIconTextGap(10);

        javax.swing.GroupLayout menu_panel_btn4Layout = new javax.swing.GroupLayout(menu_panel_btn4);
        menu_panel_btn4.setLayout(menu_panel_btn4Layout);
        menu_panel_btn4Layout.setHorizontalGroup(
            menu_panel_btn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn4Layout.createSequentialGroup()
                .addComponent(ind_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(menu_label_btn4)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menu_panel_btn4Layout.setVerticalGroup(
            menu_panel_btn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn4Layout.createSequentialGroup()
                .addComponent(ind_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menu_panel_btn4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_label_btn4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu_panel_btn2.setBackground(new java.awt.Color(0, 0, 51));
        menu_panel_btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_panel_btn2MousePressed(evt);
            }
        });

        ind_2.setBackground(new java.awt.Color(102, 34, 0));
        ind_2.setOpaque(false);
        ind_2.setPreferredSize(new java.awt.Dimension(4, 43));

        javax.swing.GroupLayout ind_2Layout = new javax.swing.GroupLayout(ind_2);
        ind_2.setLayout(ind_2Layout);
        ind_2Layout.setHorizontalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_2Layout.setVerticalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        menu_label_btn2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        menu_label_btn2.setForeground(new java.awt.Color(255, 255, 255));
        menu_label_btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new student.png"))); // NOI18N
        menu_label_btn2.setText("Student Registration");
        menu_label_btn2.setIconTextGap(10);

        javax.swing.GroupLayout menu_panel_btn2Layout = new javax.swing.GroupLayout(menu_panel_btn2);
        menu_panel_btn2.setLayout(menu_panel_btn2Layout);
        menu_panel_btn2Layout.setHorizontalGroup(
            menu_panel_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn2Layout.createSequentialGroup()
                .addComponent(ind_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(menu_label_btn2)
                .addGap(0, 46, Short.MAX_VALUE))
        );
        menu_panel_btn2Layout.setVerticalGroup(
            menu_panel_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn2Layout.createSequentialGroup()
                .addComponent(ind_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menu_panel_btn2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_label_btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu_panel_btn3.setBackground(new java.awt.Color(0, 0, 51));
        menu_panel_btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_panel_btn3MousePressed(evt);
            }
        });

        ind_3.setBackground(new java.awt.Color(102, 34, 0));
        ind_3.setOpaque(false);
        ind_3.setPreferredSize(new java.awt.Dimension(4, 43));

        javax.swing.GroupLayout ind_3Layout = new javax.swing.GroupLayout(ind_3);
        ind_3.setLayout(ind_3Layout);
        ind_3Layout.setHorizontalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_3Layout.setVerticalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        menu_label_btn3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        menu_label_btn3.setForeground(new java.awt.Color(255, 255, 255));
        menu_label_btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/analysis.png"))); // NOI18N
        menu_label_btn3.setText("Graphical Statistics");
        menu_label_btn3.setIconTextGap(10);

        javax.swing.GroupLayout menu_panel_btn3Layout = new javax.swing.GroupLayout(menu_panel_btn3);
        menu_panel_btn3.setLayout(menu_panel_btn3Layout);
        menu_panel_btn3Layout.setHorizontalGroup(
            menu_panel_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(menu_label_btn3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menu_panel_btn3Layout.setVerticalGroup(
            menu_panel_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn3Layout.createSequentialGroup()
                .addComponent(ind_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menu_panel_btn3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_label_btn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu_panel_btn1.setBackground(new java.awt.Color(153, 102, 0));
        menu_panel_btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_panel_btn1MousePressed(evt);
            }
        });

        ind_1.setBackground(new java.awt.Color(102, 34, 0));
        ind_1.setPreferredSize(new java.awt.Dimension(4, 43));

        javax.swing.GroupLayout ind_1Layout = new javax.swing.GroupLayout(ind_1);
        ind_1.setLayout(ind_1Layout);
        ind_1Layout.setHorizontalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_1Layout.setVerticalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        menu_label_btn1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        menu_label_btn1.setForeground(new java.awt.Color(255, 255, 255));
        menu_label_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/daBoard.png"))); // NOI18N
        menu_label_btn1.setText("Dashboard");
        menu_label_btn1.setIconTextGap(10);

        javax.swing.GroupLayout menu_panel_btn1Layout = new javax.swing.GroupLayout(menu_panel_btn1);
        menu_panel_btn1.setLayout(menu_panel_btn1Layout);
        menu_panel_btn1Layout.setHorizontalGroup(
            menu_panel_btn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn1Layout.createSequentialGroup()
                .addComponent(ind_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(menu_label_btn1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menu_panel_btn1Layout.setVerticalGroup(
            menu_panel_btn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn1Layout.createSequentialGroup()
                .addComponent(ind_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menu_panel_btn1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_label_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        menu_panel_btn5.setBackground(new java.awt.Color(0, 0, 51));
        menu_panel_btn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_panel_btn5MousePressed(evt);
            }
        });

        ind_7.setBackground(new java.awt.Color(102, 34, 0));
        ind_7.setOpaque(false);
        ind_7.setPreferredSize(new java.awt.Dimension(4, 43));

        javax.swing.GroupLayout ind_7Layout = new javax.swing.GroupLayout(ind_7);
        ind_7.setLayout(ind_7Layout);
        ind_7Layout.setHorizontalGroup(
            ind_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        ind_7Layout.setVerticalGroup(
            ind_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        menu_label_btn5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        menu_label_btn5.setForeground(new java.awt.Color(255, 255, 255));
        menu_label_btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/about.png"))); // NOI18N
        menu_label_btn5.setText("About");
        menu_label_btn5.setIconTextGap(11);

        javax.swing.GroupLayout menu_panel_btn5Layout = new javax.swing.GroupLayout(menu_panel_btn5);
        menu_panel_btn5.setLayout(menu_panel_btn5Layout);
        menu_panel_btn5Layout.setHorizontalGroup(
            menu_panel_btn5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn5Layout.createSequentialGroup()
                .addComponent(ind_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(menu_label_btn5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menu_panel_btn5Layout.setVerticalGroup(
            menu_panel_btn5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_panel_btn5Layout.createSequentialGroup()
                .addComponent(ind_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menu_panel_btn5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_label_btn5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Account_DisplayPanel.setBackground(new java.awt.Color(0, 0, 51));
        Account_DisplayPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Account_DisplayPanelMouseDragged(evt);
            }
        });
        Account_DisplayPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Account_DisplayPanelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Account_DisplayPanelFocusLost(evt);
            }
        });
        Account_DisplayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Account_DisplayPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Account_DisplayPanelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Account_DisplayPanelMousePressed(evt);
            }
        });

        user_label.setFont(new java.awt.Font("Franklin Gothic Book", 0, 15)); // NOI18N
        user_label.setForeground(new java.awt.Color(179, 119, 0));
        user_label.setText("uss");

        account_label.setFont(new java.awt.Font("Dialog", 0, 8)); // NOI18N
        account_label.setForeground(new java.awt.Color(179, 119, 0));
        account_label.setText("acc");

        jLabel36.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(179, 119, 0));
        jLabel36.setText("USER");

        jLabel47.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(179, 119, 0));
        jLabel47.setText("||");

        javax.swing.GroupLayout Account_DisplayPanelLayout = new javax.swing.GroupLayout(Account_DisplayPanel);
        Account_DisplayPanel.setLayout(Account_DisplayPanelLayout);
        Account_DisplayPanelLayout.setHorizontalGroup(
            Account_DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Account_DisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(4, 4, 4)
                .addComponent(jLabel47)
                .addGap(2, 2, 2)
                .addGroup(Account_DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(user_label, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(account_label, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );
        Account_DisplayPanelLayout.setVerticalGroup(
            Account_DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Account_DisplayPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(Account_DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Account_DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user_label)
                        .addComponent(jLabel36))
                    .addComponent(jLabel47))
                .addGap(0, 0, 0)
                .addComponent(account_label)
                .addGap(2, 2, 2))
        );

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adminIcon_Main.png"))); // NOI18N

        jLabel53.setForeground(new java.awt.Color(179, 119, 0));
        jLabel53.setText("UoEAuth_System");

        jLabel56.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(179, 119, 0));
        jLabel56.setText("@");

        jLabel57.setForeground(new java.awt.Color(179, 119, 0));
        jLabel57.setText("Copywrite2019");

        jLabel59.setForeground(new java.awt.Color(179, 119, 0));
        jLabel59.setText("System V-1.01");

        javax.swing.GroupLayout Menu_PanelLayout = new javax.swing.GroupLayout(Menu_Panel);
        Menu_Panel.setLayout(Menu_PanelLayout);
        Menu_PanelLayout.setHorizontalGroup(
            Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu_panel_btn4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_panel_btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_panel_btn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_panel_btn5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menu_panel_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Menu_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Menu_PanelLayout.createSequentialGroup()
                        .addGroup(Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Menu_PanelLayout.createSequentialGroup()
                                .addComponent(jLabel53)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel56)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel57))
                            .addComponent(jLabel59))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Menu_PanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel51)
                        .addGap(85, 85, 85))))
            .addGroup(Menu_PanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(Account_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Menu_PanelLayout.setVerticalGroup(
            Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(0, 0, 0)
                .addComponent(Account_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(menu_panel_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menu_panel_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menu_panel_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menu_panel_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(menu_panel_btn5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                .addGroup(Menu_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(1, 1, 1)
                .addComponent(jLabel59)
                .addGap(30, 30, 30))
        );

        searchStudent_panel.setBackground(new java.awt.Color(102, 34, 0));
        searchStudent_panel.setPreferredSize(new java.awt.Dimension(1183, 35));

        student_search.setBackground(new java.awt.Color(230, 230, 230));
        student_search.setForeground(new java.awt.Color(153, 153, 153));
        student_search.setText("Search Student");
        student_search.setToolTipText("search for student using the student id, name or surname");
        student_search.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        student_search.setPreferredSize(new java.awt.Dimension(79, 10));
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                student_searchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                student_searchKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N

        jPanel16.setBackground(new java.awt.Color(102, 34, 0));
        jPanel16.setPreferredSize(new java.awt.Dimension(820, 18));

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Number of Students (All) :");

        Students_barNo.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        Students_barNo.setForeground(new java.awt.Color(0, 153, 153));
        Students_barNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Students_barNo.setText("555");

        jLabel72.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("||");
        jLabel72.setPreferredSize(new java.awt.Dimension(30, 15));

        jLabel73.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Number of Staff :");

        Staff_barNo.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        Staff_barNo.setForeground(new java.awt.Color(0, 153, 153));
        Staff_barNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Staff_barNo.setText("555");

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("||");
        jLabel75.setPreferredSize(new java.awt.Dimension(30, 15));

        jLabel76.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("Number of Elligible Candidates :");

        ElligibleStudets_barNo.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        ElligibleStudets_barNo.setForeground(new java.awt.Color(0, 153, 153));
        ElligibleStudets_barNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ElligibleStudets_barNo.setText("555");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel70)
                .addGap(18, 18, 18)
                .addComponent(Students_barNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel73)
                .addGap(18, 18, 18)
                .addComponent(Staff_barNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel76)
                .addGap(18, 18, 18)
                .addComponent(ElligibleStudets_barNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel70)
                        .addComponent(Students_barNo)
                        .addComponent(jLabel73)
                        .addComponent(Staff_barNo)
                        .addComponent(jLabel76)
                        .addComponent(ElligibleStudets_barNo))
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout searchStudent_panelLayout = new javax.swing.GroupLayout(searchStudent_panel);
        searchStudent_panel.setLayout(searchStudent_panelLayout);
        searchStudent_panelLayout.setHorizontalGroup(
            searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchStudent_panelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(student_search, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        searchStudent_panelLayout.setVerticalGroup(
            searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchStudent_panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(searchStudent_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(student_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        Main_Dynamic_Panel.setLayout(new java.awt.CardLayout());

        System_Edit_Dashboard.setBackground(new java.awt.Color(255, 255, 255));

        Dashboard_Chart.setBackground(new java.awt.Color(204, 204, 204));
        Dashboard_Chart.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));

        jLabel37.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 15)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Total Registered Students     ||");

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/totRegClipboard.png"))); // NOI18N

        jLabel46.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Male");

        male_count.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        male_count.setForeground(new java.awt.Color(255, 255, 255));
        male_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        male_count.setText("0.0");

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Female");

        female_count.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        female_count.setForeground(new java.awt.Color(255, 255, 255));
        female_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        female_count.setText("0.0");

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Total");

        total_count.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        total_count.setForeground(new java.awt.Color(255, 255, 255));
        total_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total_count.setText("0.0");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addGap(65, 65, 65)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(20, 20, 20)
                        .addComponent(male_count, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total_count, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(female_count, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(male_count))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(female_count))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(total_count))))
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(0, 102, 102));

        jLabel38.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 15)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Students Fee Count               ||");

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pendClearColored.png"))); // NOI18N

        jLabel54.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Cleared");

        jLabel55.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Pending");

        cleared_fee.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cleared_fee.setForeground(new java.awt.Color(255, 255, 255));
        cleared_fee.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cleared_fee.setText("0.0");

        pending_fee.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        pending_fee.setForeground(new java.awt.Color(255, 255, 255));
        pending_fee.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pending_fee.setText("0.0");

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Min Pay");

        zero_fee.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        zero_fee.setForeground(new java.awt.Color(255, 255, 255));
        zero_fee.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        zero_fee.setText("0.0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(65, 65, 65)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cleared_fee, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(pending_fee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(zero_fee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(cleared_fee))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(pending_fee))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(zero_fee)))
                    .addComponent(jLabel44))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(0, 102, 51));

        jLabel39.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 15)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Total System Staff                   ||");

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sysStaffBoss.png"))); // NOI18N

        jLabel60.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Support");

        jLabel61.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Admin");

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("All Staff");

        support_staff.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        support_staff.setForeground(new java.awt.Color(255, 255, 255));
        support_staff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        support_staff.setText("0.0");

        admin_staff.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_staff.setForeground(new java.awt.Color(255, 255, 255));
        admin_staff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        admin_staff.setText("0.0");

        all_staff.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        all_staff.setForeground(new java.awt.Color(255, 255, 255));
        all_staff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        all_staff.setText("0.0");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addGap(65, 65, 65)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(support_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(admin_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(all_staff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(support_staff))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(admin_staff))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(all_staff)))
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        IsoCertified_Label2.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        IsoCertified_Label2.setForeground(new java.awt.Color(0, 153, 153));
        IsoCertified_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IsoCertified_Label2.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        jPanel10.setBackground(new java.awt.Color(102, 34, 0));
        jPanel10.setPreferredSize(new java.awt.Dimension(350, 481));

        uoe_admin_pic.setBackground(new java.awt.Color(153, 153, 153));

        uoe_students_pic.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(uoe_admin_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uoe_students_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uoe_admin_pic, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(uoe_students_pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("University Contact Details");

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel34.setText("Mobile:");

        jLabel35.setText("Want to visit university website. ");

        jLabel63.setForeground(new java.awt.Color(0, 153, 153));
        jLabel63.setText("WWW.UOELD.AC.KE");

        jLabel64.setText("+254 788 232 004");

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel65.setText("Address:");

        jLabel66.setText("P.O Box 1125-30100 ; Eldoret, Kenya.");

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel67.setText("Email:");

        jLabel68.setForeground(new java.awt.Color(0, 153, 153));
        jLabel68.setText("info@uoeld.ac.ke");

        jLabel40.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel40.setText("||");

        jLabel41.setText("+254 774 249 552");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel67)
                            .addComponent(jLabel34))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel64)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel40)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel41))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel63)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40)
                        .addComponent(jLabel41)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jLabel68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jLabel35))
                .addContainerGap())
        );

        viewAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/view.png"))); // NOI18N
        viewAll.setText("View Parameters");
        viewAll.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        viewAll.setIconTextGap(10);
        viewAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAllActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refreshh.png"))); // NOI18N
        jButton6.setText("Refresh Summary Bar");
        jButton6.setIconTextGap(10);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewAll)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        uoe_header_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        uoe_header_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UoE_Dark.png"))); // NOI18N
        uoe_header_logo.setPreferredSize(new java.awt.Dimension(350, 105));

        jLabel80.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("SYSTEM DASHBOARD PANEL");
        jLabel80.setPreferredSize(new java.awt.Dimension(474, 60));

        jLabel81.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setText("Registration Graph Trend");
        jLabel81.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel82.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setText("Summary of Fee Status");
        jLabel82.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel83.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("Summary of Registered Students");
        jLabel83.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel84.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("System Staff Information");
        jLabel84.setPreferredSize(new java.awt.Dimension(190, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(uoe_header_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 589, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(uoe_header_logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout System_Edit_DashboardLayout = new javax.swing.GroupLayout(System_Edit_Dashboard);
        System_Edit_Dashboard.setLayout(System_Edit_DashboardLayout);
        System_Edit_DashboardLayout.setHorizontalGroup(
            System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                .addGroup(System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IsoCertified_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Dashboard_Chart, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        System_Edit_DashboardLayout.setVerticalGroup(
            System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(System_Edit_DashboardLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Dashboard_Chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(System_Edit_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IsoCertified_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)))
        );

        Main_Dynamic_Panel.add(System_Edit_Dashboard, "card5");

        All_Student_Edit_Panel.setPreferredSize(new java.awt.Dimension(1082, 596));

        database_tables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                database_tablesMouseClicked(evt);
            }
        });

        student_info_table.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
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
        student_info_table.setRowHeight(25);
        student_info_table.setSelectionBackground(new java.awt.Color(153, 102, 0));
        student_info_table.setShowHorizontalLines(false);
        student_info_table.getTableHeader().setReorderingAllowed(false);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(student_info_print)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(student_info_print)
                .addContainerGap())
        );

        database_tables.addTab("Student Info. Table", jPanel3);

        fee_balance_table.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
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
        fee_balance_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        fee_balance_table.setRowHeight(25);
        fee_balance_table.setSelectionBackground(new java.awt.Color(153, 102, 0));
        fee_balance_table.setShowHorizontalLines(false);
        fee_balance_table.getTableHeader().setReorderingAllowed(false);
        fee_balance_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fee_balance_tableMouseClicked(evt);
            }
        });
        fee_balance_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fee_balance_tableKeyReleased(evt);
            }
        });
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
                .addContainerGap(35, Short.MAX_VALUE))
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

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refreshh.png"))); // NOI18N
        jButton5.setText("Refresh");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton5.setIconTextGap(15);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        eligibility_status.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        eligibility_status.setText("Elg.Stat");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(eligibility_status, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(eligibility_status, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/notification.png"))); // NOI18N
        jLabel42.setText("Exam:");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete2.png"))); // NOI18N
        jButton8.setText("Clear");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton8.setIconTextGap(15);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(fee_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(6, 6, 6)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fee_balance_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        database_tables.addTab("Fee Balance Check", jPanel4);

        exam_eligible_table.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
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
        exam_eligible_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        exam_eligible_table.setRowHeight(25);
        exam_eligible_table.setSelectionBackground(new java.awt.Color(153, 102, 0));
        exam_eligible_table.setShowHorizontalLines(false);
        exam_eligible_table.getTableHeader().setReorderingAllowed(false);
        exam_eligible_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exam_eligible_tableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(exam_eligible_table);

        eligible_table_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        eligible_table_print.setText("Print");
        eligible_table_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eligible_table_print.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        eligible_table_print.setIconTextGap(20);
        eligible_table_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eligible_table_printActionPerformed(evt);
            }
        });

        eligible_table_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reset.png"))); // NOI18N
        eligible_table_reset.setText("Reset");
        eligible_table_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eligible_table_reset.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        eligible_table_reset.setIconTextGap(20);
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
        eligible_list_process.setIconTextGap(10);
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
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(eligible_list_process)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(min_fee_allowed, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(max_fee_payable))
                .addGap(0, 26, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(eligible_table_reset, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(eligible_table_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(eligible_table_reset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eligible_table_print)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        database_tables.addTab("Eligible Candidates", jPanel5);

        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

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
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mail_from, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(mail_to)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(mail_subject)))
                .addGap(8, 8, 8))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(8, 8, 8)
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
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(mail_attach_name, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mail_file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(send_mail, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mail_file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(file_attach, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mail_attach_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(send_mail)
                .addGap(18, 18, 18))
        );

        database_tables.addTab("Mail Student List", jPanel7);

        javax.swing.GroupLayout studentTables_panelLayout = new javax.swing.GroupLayout(studentTables_panel);
        studentTables_panel.setLayout(studentTables_panelLayout);
        studentTables_panelLayout.setHorizontalGroup(
            studentTables_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentTables_panelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(database_tables, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        studentTables_panelLayout.setVerticalGroup(
            studentTables_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentTables_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(database_tables, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );

        Header_Panel.setBackground(new java.awt.Color(0, 0, 51));
        Header_Panel.setPreferredSize(new java.awt.Dimension(0, 105));

        MiniHeader_Panel.setForeground(new java.awt.Color(255, 255, 255));
        MiniHeader_Panel.setPreferredSize(new java.awt.Dimension(0, 105));

        AllStudent_UoeHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AllStudent_UoeHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dark.png"))); // NOI18N

        javax.swing.GroupLayout MiniHeader_PanelLayout = new javax.swing.GroupLayout(MiniHeader_Panel);
        MiniHeader_Panel.setLayout(MiniHeader_PanelLayout);
        MiniHeader_PanelLayout.setHorizontalGroup(
            MiniHeader_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiniHeader_PanelLayout.createSequentialGroup()
                .addComponent(AllStudent_UoeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        MiniHeader_PanelLayout.setVerticalGroup(
            MiniHeader_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AllStudent_UoeHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        jLabel71.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("STUDENT REGISTRATION PANEL");

        jLabel74.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("View Student Balance");
        jLabel74.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel77.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("List Eligible Candidates");
        jLabel77.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel78.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("Mail Candidate Report");
        jLabel78.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel79.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("Add New Students");
        jLabel79.setPreferredSize(new java.awt.Dimension(190, 20));

        javax.swing.GroupLayout Header_PanelLayout = new javax.swing.GroupLayout(Header_Panel);
        Header_Panel.setLayout(Header_PanelLayout);
        Header_PanelLayout.setHorizontalGroup(
            Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Header_PanelLayout.createSequentialGroup()
                .addComponent(MiniHeader_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Header_PanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Header_PanelLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Header_PanelLayout.setVerticalGroup(
            Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MiniHeader_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Header_PanelLayout.createSequentialGroup()
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        All_Student_Input_Panel.setBackground(new java.awt.Color(102, 34, 0));
        All_Student_Input_Panel.setPreferredSize(new java.awt.Dimension(350, 500));

        jPanel2.setBackground(new java.awt.Color(102, 34, 0));

        student_surname.setBackground(new java.awt.Color(255, 255, 255));
        student_surname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        student_firstname.setBackground(new java.awt.Color(255, 255, 255));
        student_firstname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        student_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student_firstnameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Surname");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("First Name");

        date_of_input.setBackground(new java.awt.Color(255, 255, 255));
        date_of_input.setDateFormatString("yyyy-MM-dd");
        date_of_input.setMaxSelectableDate(new java.util.Date(1577829717000L));
        date_of_input.setMinSelectableDate(new java.util.Date(1262296909000L));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Date of Input");

        school.setBackground(new java.awt.Color(255, 255, 255));
        school.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        school.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "**Select Your School<", "SCIENCE", "ENGINEERING", "EDUCATION", "BUSINESS", "HOTEL & HOSPITALITY", "FORESTRY" }));
        school.setToolTipText("Select School");
        school.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        school.setPreferredSize(new java.awt.Dimension(140, 22));
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

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("School");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Gender");

        gender_male.setBackground(new java.awt.Color(102, 34, 0));
        buttonGroup1.add(gender_male);
        gender_male.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        gender_male.setForeground(new java.awt.Color(255, 255, 255));
        gender_male.setText("Male");
        gender_male.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gender_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gender_maleActionPerformed(evt);
            }
        });

        gender_female.setBackground(new java.awt.Color(102, 34, 0));
        buttonGroup1.add(gender_female);
        gender_female.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        gender_female.setForeground(new java.awt.Color(255, 255, 255));
        gender_female.setText("Female");
        gender_female.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gender_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gender_femaleActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Registration No");

        student_regno.setBackground(new java.awt.Color(255, 255, 255));
        student_regno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Last Name");

        student_lastname.setBackground(new java.awt.Color(255, 255, 255));
        student_lastname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(gender_female)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(gender_male)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(school, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_of_input, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student_regno, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(gender_female))
        );

        image_dsktp_pane.setBackground(new java.awt.Color(204, 204, 204));

        image_area.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        image_dsktp_pane.setLayer(image_area, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout image_dsktp_paneLayout = new javax.swing.GroupLayout(image_dsktp_pane);
        image_dsktp_pane.setLayout(image_dsktp_paneLayout);
        image_dsktp_paneLayout.setHorizontalGroup(
            image_dsktp_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(image_dsktp_paneLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(image_area, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        image_dsktp_paneLayout.setVerticalGroup(
            image_dsktp_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(image_dsktp_paneLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(image_area, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        imagepath.setBackground(new java.awt.Color(255, 255, 255));
        imagepath.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        image_attach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/attachment.png"))); // NOI18N
        image_attach.setText("Image");
        image_attach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        image_attach.setIconTextGap(10);
        image_attach.setMaximumSize(new java.awt.Dimension(117, 20));
        image_attach.setMinimumSize(new java.awt.Dimension(117, 20));
        image_attach.setPreferredSize(new java.awt.Dimension(90, 21));
        image_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                image_attachActionPerformed(evt);
            }
        });

        studentid.setBackground(new java.awt.Color(255, 255, 255));
        studentid.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Finger Id:");

        javax.swing.GroupLayout All_Student_Input_PanelLayout = new javax.swing.GroupLayout(All_Student_Input_Panel);
        All_Student_Input_Panel.setLayout(All_Student_Input_PanelLayout);
        All_Student_Input_PanelLayout.setHorizontalGroup(
            All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, All_Student_Input_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                        .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, All_Student_Input_PanelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(4, 4, 4)
                                        .addComponent(studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(imagepath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, All_Student_Input_PanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(image_attach, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)))
                .addGap(15, 15, 15))
        );
        All_Student_Input_PanelLayout.setVerticalGroup(
            All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(image_dsktp_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(All_Student_Input_PanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(image_attach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(All_Student_Input_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
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
                .addGap(8, 8, 8)
                .addComponent(registerunits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        IsoCertified_Label.setPreferredSize(new java.awt.Dimension(402, 30));

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
                    .addComponent(table_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
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
                .addComponent(All_Student_Input_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(studentSupport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formControl_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(studentTables_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(IsoCertified_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(Header_Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
        );
        All_Student_Edit_PanelLayout.setVerticalGroup(
            All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                .addComponent(Header_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(All_Student_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(All_Student_Edit_PanelLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(formControl_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(studentSupport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(studentTables_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IsoCertified_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(All_Student_Input_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Main_Dynamic_Panel.add(All_Student_Edit_Panel, "card2");

        Graphical_Edit_Statistics.setPreferredSize(new java.awt.Dimension(1182, 596));

        Statistics_Buttons_Panel.setBackground(new java.awt.Color(102, 34, 0));
        Statistics_Buttons_Panel.setPreferredSize(new java.awt.Dimension(350, 500));

        pie_chart.setText("Fee Payment Statistics");
        pie_chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pie_chartActionPerformed(evt);
            }
        });

        jButton1.setText("General Gender Ratio");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Student Registerd per Day");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Number of Students per School");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Refresh Charts");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setText("Gender Distribution per School");
        jButton7.setPreferredSize(new java.awt.Dimension(185, 25));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/barGraph.png"))); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pieChart.png"))); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bar_line.png"))); // NOI18N

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lineGraph.png"))); // NOI18N

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/chart-reload.png"))); // NOI18N

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistics.png"))); // NOI18N

        javax.swing.GroupLayout Statistics_Buttons_PanelLayout = new javax.swing.GroupLayout(Statistics_Buttons_Panel);
        Statistics_Buttons_Panel.setLayout(Statistics_Buttons_PanelLayout);
        Statistics_Buttons_PanelLayout.setHorizontalGroup(
            Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistics_Buttons_PanelLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pie_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        Statistics_Buttons_PanelLayout.setVerticalGroup(
            Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistics_Buttons_PanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pie_chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addGap(26, 26, 26)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(Statistics_Buttons_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        Statistics_Header.setBackground(new java.awt.Color(0, 0, 51));
        Statistics_Header.setPreferredSize(new java.awt.Dimension(350, 105));

        Statistics_MiniHeader.setPreferredSize(new java.awt.Dimension(0, 105));

        GraphicalStat_UoeHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GraphicalStat_UoeHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dark.png"))); // NOI18N
        GraphicalStat_UoeHeader.setPreferredSize(new java.awt.Dimension(317, 95));

        javax.swing.GroupLayout Statistics_MiniHeaderLayout = new javax.swing.GroupLayout(Statistics_MiniHeader);
        Statistics_MiniHeader.setLayout(Statistics_MiniHeaderLayout);
        Statistics_MiniHeaderLayout.setHorizontalGroup(
            Statistics_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistics_MiniHeaderLayout.createSequentialGroup()
                .addComponent(GraphicalStat_UoeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Statistics_MiniHeaderLayout.setVerticalGroup(
            Statistics_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GraphicalStat_UoeHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel85.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setText("GENERAL STATISTICS PANEL");
        jLabel85.setPreferredSize(new java.awt.Dimension(465, 60));

        jLabel86.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setText("Graphical Gender Ratio");
        jLabel86.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel87.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setText("Detailed Fee Payment");
        jLabel87.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel88.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("Student Ratio Per School");
        jLabel88.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel89.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel89.setText("Daily Registration Statistic");
        jLabel89.setPreferredSize(new java.awt.Dimension(190, 20));

        javax.swing.GroupLayout Statistics_HeaderLayout = new javax.swing.GroupLayout(Statistics_Header);
        Statistics_Header.setLayout(Statistics_HeaderLayout);
        Statistics_HeaderLayout.setHorizontalGroup(
            Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Statistics_HeaderLayout.createSequentialGroup()
                .addComponent(Statistics_MiniHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Statistics_HeaderLayout.createSequentialGroup()
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Statistics_HeaderLayout.setVerticalGroup(
            Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Statistics_MiniHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
            .addGroup(Statistics_HeaderLayout.createSequentialGroup()
                .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Statistics_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        StatisticsChanger.setBackground(new java.awt.Color(0, 153, 153));
        StatisticsChanger.setLayout(new java.awt.BorderLayout());

        male_female_pie.setBackground(new java.awt.Color(0, 153, 51));
        male_female_pie.setLayout(new java.awt.BorderLayout());

        fee_StatsChart.setBackground(new java.awt.Color(0, 153, 102));
        fee_StatsChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout Chart_ContentPanelLayout = new javax.swing.GroupLayout(Chart_ContentPanel);
        Chart_ContentPanel.setLayout(Chart_ContentPanelLayout);
        Chart_ContentPanelLayout.setHorizontalGroup(
            Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Chart_ContentPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(StatisticsChanger, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fee_StatsChart, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(male_female_pie, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        Chart_ContentPanelLayout.setVerticalGroup(
            Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Chart_ContentPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(Chart_ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Chart_ContentPanelLayout.createSequentialGroup()
                        .addComponent(male_female_pie, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fee_StatsChart, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(StatisticsChanger, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        IsoCertified_Label1.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        IsoCertified_Label1.setForeground(new java.awt.Color(0, 153, 153));
        IsoCertified_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IsoCertified_Label1.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        javax.swing.GroupLayout Graphical_Edit_StatisticsLayout = new javax.swing.GroupLayout(Graphical_Edit_Statistics);
        Graphical_Edit_Statistics.setLayout(Graphical_Edit_StatisticsLayout);
        Graphical_Edit_StatisticsLayout.setHorizontalGroup(
            Graphical_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Graphical_Edit_StatisticsLayout.createSequentialGroup()
                .addGap(0, 349, Short.MAX_VALUE)
                .addComponent(IsoCertified_Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(Statistics_Header, javax.swing.GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Graphical_Edit_StatisticsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Chart_ContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addGroup(Graphical_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Graphical_Edit_StatisticsLayout.createSequentialGroup()
                    .addComponent(Statistics_Buttons_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 832, Short.MAX_VALUE)))
        );
        Graphical_Edit_StatisticsLayout.setVerticalGroup(
            Graphical_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Graphical_Edit_StatisticsLayout.createSequentialGroup()
                .addComponent(Statistics_Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(Chart_ContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IsoCertified_Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(Graphical_Edit_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Graphical_Edit_StatisticsLayout.createSequentialGroup()
                    .addGap(0, 96, Short.MAX_VALUE)
                    .addComponent(Statistics_Buttons_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Main_Dynamic_Panel.add(Graphical_Edit_Statistics, "card4");

        Admin_Edit_Panel.setPreferredSize(new java.awt.Dimension(1082, 608));

        Admin_List_Panel.setBackground(new java.awt.Color(102, 34, 0));
        Admin_List_Panel.setPreferredSize(new java.awt.Dimension(340, 617));

        admin_signup_list.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        admin_signup_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        admin_signup_list.setIntercellSpacing(new java.awt.Dimension(0, 0));
        admin_signup_list.setName("ADMIN SIGN UP LIST"); // NOI18N
        admin_signup_list.setRowHeight(25);
        admin_signup_list.setSelectionBackground(new java.awt.Color(153, 102, 0));
        admin_signup_list.setShowVerticalLines(false);
        admin_signup_list.getTableHeader().setReorderingAllowed(false);
        admin_signup_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                admin_signup_listMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                admin_signup_listMousePressed(evt);
            }
        });
        admin_signup_list.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                admin_signup_listKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                admin_signup_listKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(admin_signup_list);

        admin_list_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refreshh.png"))); // NOI18N
        admin_list_refresh.setText("Refresh List");
        admin_list_refresh.setIconTextGap(15);
        admin_list_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_list_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Admin_List_PanelLayout = new javax.swing.GroupLayout(Admin_List_Panel);
        Admin_List_Panel.setLayout(Admin_List_PanelLayout);
        Admin_List_PanelLayout.setHorizontalGroup(
            Admin_List_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_List_PanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(Admin_List_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_List_PanelLayout.createSequentialGroup()
                        .addComponent(admin_list_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        Admin_List_PanelLayout.setVerticalGroup(
            Admin_List_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_List_PanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(admin_list_refresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Admin_Header.setBackground(new java.awt.Color(0, 0, 51));

        AdminUoe_Header.setForeground(new java.awt.Color(255, 255, 255));
        AdminUoe_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AdminUoe_Header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Dark.png"))); // NOI18N

        javax.swing.GroupLayout Admin_MiniHeaderLayout = new javax.swing.GroupLayout(Admin_MiniHeader);
        Admin_MiniHeader.setLayout(Admin_MiniHeaderLayout);
        Admin_MiniHeaderLayout.setHorizontalGroup(
            Admin_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminUoe_Header, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        Admin_MiniHeaderLayout.setVerticalGroup(
            Admin_MiniHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AdminUoe_Header, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        jLabel90.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("SYSTEM ADMINISTRATIVE  PANEL");
        jLabel90.setPreferredSize(new java.awt.Dimension(547, 60));

        jLabel91.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("View Recent Signups");
        jLabel91.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel92.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Assign User Privilages");
        jLabel92.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel93.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Block User Access");
        jLabel93.setPreferredSize(new java.awt.Dimension(190, 20));

        jLabel94.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel94.setText("Delete System Staff");
        jLabel94.setPreferredSize(new java.awt.Dimension(190, 20));

        javax.swing.GroupLayout Admin_HeaderLayout = new javax.swing.GroupLayout(Admin_Header);
        Admin_Header.setLayout(Admin_HeaderLayout);
        Admin_HeaderLayout.setHorizontalGroup(
            Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_HeaderLayout.createSequentialGroup()
                .addComponent(Admin_MiniHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_HeaderLayout.createSequentialGroup()
                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_HeaderLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Admin_HeaderLayout.setVerticalGroup(
            Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Admin_MiniHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Admin_HeaderLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Admin_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        Iso_Text.setFont(new java.awt.Font("DialogInput", 1, 10)); // NOI18N
        Iso_Text.setForeground(new java.awt.Color(0, 153, 153));
        Iso_Text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Iso_Text.setText("UNIVERSITY OF ELDORET IS ISO CERTIFIED UNDER NO. 1900200 **** *****");

        account_privallage.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        account_privallage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "**Select Privilage<*", "Admin", "Support Staff" }));
        account_privallage.setToolTipText("");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel24.setText("Set Privilage");

        privillage_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adminUpdate.png"))); // NOI18N
        privillage_update.setText("Update");
        privillage_update.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        privillage_update.setIconTextGap(40);
        privillage_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privillage_updateActionPerformed(evt);
            }
        });

        account_unblock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adminUnblock.png"))); // NOI18N
        account_unblock.setText("Unblock");
        account_unblock.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        account_unblock.setIconTextGap(40);
        account_unblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_unblockActionPerformed(evt);
            }
        });

        account_block.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adminBlock.png"))); // NOI18N
        account_block.setText("Block User");
        account_block.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        account_block.setIconTextGap(35);
        account_block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_blockActionPerformed(evt);
            }
        });

        account_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/adminDelette.png"))); // NOI18N
        account_delete.setText("Delete");
        account_delete.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        account_delete.setIconTextGap(40);
        account_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                account_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Privilage_Edit_PanelLayout = new javax.swing.GroupLayout(Privilage_Edit_Panel);
        Privilage_Edit_Panel.setLayout(Privilage_Edit_PanelLayout);
        Privilage_Edit_PanelLayout.setHorizontalGroup(
            Privilage_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Privilage_Edit_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Privilage_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(account_privallage, 0, 180, Short.MAX_VALUE)
                    .addComponent(account_block, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(account_unblock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(privillage_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(account_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Privilage_Edit_PanelLayout.setVerticalGroup(
            Privilage_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Privilage_Edit_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(account_privallage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(account_block)
                .addGap(18, 18, 18)
                .addComponent(account_unblock)
                .addGap(18, 18, 18)
                .addComponent(privillage_update)
                .addGap(25, 25, 25)
                .addComponent(account_delete)
                .addGap(23, 23, 23))
        );

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel29.setText("Mobile");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel25.setText("Gender");

        txt_employment_no.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        txt_employment_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employment_noActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Employment No");

        buttonGroup1.add(admin_gender_male);
        admin_gender_male.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_gender_male.setText("Male");
        admin_gender_male.setBorder(null);

        txt_last_name.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        txt_last_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_last_nameActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel30.setText("Email Address");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel26.setText("First Name");

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel27.setText("Last Name");

        txt_email_address.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        txt_email_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_email_addressActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel23.setText("Username");

        txt_first_name.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N

        txt_username.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N

        txt_mobile.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N

        buttonGroup1.add(admin_gender_female);
        admin_gender_female.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        admin_gender_female.setText("Female");
        admin_gender_female.setBorder(null);
        admin_gender_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_gender_femaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Admin_Autofill_PanelLayout = new javax.swing.GroupLayout(Admin_Autofill_Panel);
        Admin_Autofill_Panel.setLayout(Admin_Autofill_PanelLayout);
        Admin_Autofill_PanelLayout.setHorizontalGroup(
            Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_mobile)
                    .addComponent(txt_email_address)
                    .addComponent(txt_last_name)
                    .addComponent(txt_employment_no)
                    .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(admin_gender_male)
                            .addComponent(admin_gender_female))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        Admin_Autofill_PanelLayout.setVerticalGroup(
            Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_employment_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                        .addGroup(Admin_Autofill_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                                .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_last_name, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Admin_Autofill_PanelLayout.createSequentialGroup()
                        .addComponent(admin_gender_male)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(admin_gender_female)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Admin_FormContrl_PanelLayout = new javax.swing.GroupLayout(Admin_FormContrl_Panel);
        Admin_FormContrl_Panel.setLayout(Admin_FormContrl_PanelLayout);
        Admin_FormContrl_PanelLayout.setHorizontalGroup(
            Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Admin_Autofill_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Privilage_Edit_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        Admin_FormContrl_PanelLayout.setVerticalGroup(
            Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Admin_FormContrl_PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Admin_FormContrl_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Admin_Autofill_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Privilage_Edit_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        Admin_privilage_Warning.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        Admin_privilage_Warning.setForeground(new java.awt.Color(255, 0, 0));

        Admin_block_Warning.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        Admin_block_Warning.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout Admin_Edit_PanelLayout = new javax.swing.GroupLayout(Admin_Edit_Panel);
        Admin_Edit_Panel.setLayout(Admin_Edit_PanelLayout);
        Admin_Edit_PanelLayout.setHorizontalGroup(
            Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(Admin_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Admin_Edit_PanelLayout.createSequentialGroup()
                .addComponent(Admin_List_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_Edit_PanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Admin_privilage_Warning, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Admin_block_Warning, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_Edit_PanelLayout.createSequentialGroup()
                        .addComponent(Admin_FormContrl_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addComponent(Iso_Text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        Admin_Edit_PanelLayout.setVerticalGroup(
            Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Admin_Edit_PanelLayout.createSequentialGroup()
                .addComponent(Admin_Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(Admin_Edit_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Admin_Edit_PanelLayout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addComponent(Admin_block_Warning)
                        .addGap(0, 0, 0)
                        .addComponent(Admin_privilage_Warning)
                        .addGap(0, 0, 0)
                        .addComponent(Admin_FormContrl_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(Iso_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Admin_List_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)))
        );

        Main_Dynamic_Panel.add(Admin_Edit_Panel, "card3");

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

        log_out_bar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        log_out_bar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        log_out_bar.setText("Log Out");
        log_out_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                log_out_barActionPerformed(evt);
            }
        });
        jMenu1.add(log_out_bar);

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
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Main_Dynamic_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(searchStudent_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchStudent_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Main_Dynamic_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
            .addComponent(Menu_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1366, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void schoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schoolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_schoolActionPerformed

    private void student_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_student_firstnameActionPerformed

    private void student_info_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_info_tableMouseClicked
        table_delete.setEnabled(true);
        int row =student_info_table.getSelectedRow();
            String Table_click=(student_info_table.getModel().getValueAt(row, 0).toString());
            id = Table_click;
        try{
        
            String sql="select image from student_info where student_regno ='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            if(rs.next()){
                byte[]imagedata =rs.getBytes("image");
                //format =new ImageIcon(imagedata);
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                }
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }
        
        
        try{
            
            String sql="select * from student_info where student_regno='"+Table_click+"'";
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
                
                if (rs.getString("gender").equals("male")) {
                    gender_male.setSelected(true);
                    } else { 
                     gender_female.setSelected(true);
                    }
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
            if (school.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Error: No School Selected");
                } else {
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
            
            try{
            String sqlFee ="insert into feebal (student_id, student_regno, school, feedue, feepaid) values (?,?,?,?,?)";
            
            double x = 0;
            double y = 13100;
            pst1=conn.prepareStatement(sqlFee);
            pst1.setString(1, studentid.getText());
            pst1.setString(2, student_regno.getText());
            
            String valueFee=school.getSelectedItem().toString();
            pst1.setString(3, valueFee);
            
            pst1.setDouble(4, y);
            pst1.setDouble(5, x);
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }
        
                }
            pst.execute();
            pst1.execute();
            
            //Confirmation to save student dialog box
            
            String confirm="<html>"
                                + "<b> Student RegNo </b>   = "+student_regno.getText()+" <br>"
                                + "<b> First Name </b>      = "+student_firstname.getText()+"<br>"
                                + "<b> Surname </b>         = "+student_surname.getText()+"<br>"
                                + "<b> First Name </b>      = "+school.getSelectedItem()+""
                                + "</html>";
            JOptionPane optionpane = new JOptionPane();
            optionpane.setMessage(confirm);
            optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionpane.createDialog(null, "SAVE NEW STUDENT?");
            dialog.setVisible(true);
            //Confirmation to save student dialog box
            
            JOptionPane.showMessageDialog(null, "Saved");
            }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);}
        Update_table();
        
    }//GEN-LAST:event_form_saveActionPerformed

    private void table_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_deleteActionPerformed
//        String blank = "";
//        if(student_regno.equals(blank)){
//           JOptionPane.showMessageDialog(null, "No admission selected");
//       }else{
        //Delete confirmation Dialog
        int p = JOptionPane.showConfirmDialog(null, "Do You Want To Delete","Delete",JOptionPane.YES_NO_OPTION);
        if(p==0){
        
        String sql="delete from student_info where student_id =?";
        String sqlFee="delete from feebal where student_id =?";
        try{
        
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sqlFee);
            
            pst.setString(1, id);
            pst1.setString(1, id);
            
            pst.execute();
            pst1.execute();
            JOptionPane.showMessageDialog(null, "Deleted");
        
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }
        Update_table();
        
        }
       //}
    }//GEN-LAST:event_table_deleteActionPerformed

    private void table_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table_updateActionPerformed
        // TODO add your handling code here:
        
        try{
        
            if (school.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Error: No School Selected");
                } else {
                String value1= studentid.getText();
                String value2= student_regno.getText();
                String value3= student_firstname.getText();
                String value4= student_lastname.getText();
                String value5= student_surname.getText();
                String value6= (((JTextField)date_of_input.getDateEditor().getUiComponent()).getText());
                
                String value7= school.getSelectedItem().toString();
        
                String sql="update student_info set student_id='"+value1+"' , student_regno='"+value2+"' , first_name='"+value3+"' , last_name='"+value4+"' ,  surname='"+value5+"' , date_of_input='"+value6+"' , school='"+value7+"' where student_id='"+value1+"'";
                pst=conn.prepareStatement(sql);
                
                
//                try{
//                    String value1Fee= studentid.getText();
//                    String value2Fee= student_regno.getText();

                    String sqlFee="update feebal set student_id='"+value1+"' , student_regno='"+value2+"', school='"+value7+"' where student_id='"+value1+"'";
                    pst1=conn.prepareStatement(sqlFee);
//                    }catch(Exception e){
//                        JOptionPane.showMessageDialog(null, e);
//                        }
                
                JOptionPane.showMessageDialog(null, "Data Updated Succesfully");
                }
                pst.execute();
                pst1.execute();
                
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
                
                 if (rs.getString("gender").equals("male")) {
                    gender_male.setSelected(true);
                    } else { 
                     gender_female.setSelected(true);
                    }
                
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
                
                 if (rs.getString("gender").equals("male")) {
                    gender_male.setSelected(true);
                    } else { 
                     gender_female.setSelected(true);
                    }
                
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
                
                 if (rs.getString("gender").equals("male")) {
                    gender_male.setSelected(true);
                    } else { 
                     gender_female.setSelected(true);
                    }
                
                byte[]imagedata =rs.getBytes("image");
                ImageIcon format =new ImageIcon(ScaleImage(imagedata, image_area.getWidth(), image_area.getHeight()));
                image_area.setIcon(format);
                
                    }
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
         
         
         //Search on basis of Employment No  [For Admin Search]    
         try{
          
                String sql="select * from admin_login where employment_no = ?";
                
                pst=conn.prepareStatement(sql);
                pst.setString(1, student_search.getText());
                
                rs=pst.executeQuery();
                if(rs.next()){
                    
                String add1 =rs.getString("employment_no");
                txt_employment_no.setText(add1);
                  
                String add2 =rs.getString("first_name");
                txt_first_name.setText(add2);
                
                String add3 =rs.getString("last_name");
                txt_last_name.setText(add3);
                
                String add4 =rs.getString("username");
                txt_username.setText(add4);
                
                String add5 =rs.getString("email");
                txt_email_address.setText(add5);
                
                String add6 =rs.getString("mobile");
                txt_mobile.setText(add6);
               
                String add7 =rs.getString("account_type");
                account_privallage.setSelectedItem(add7);
                
                 if (rs.getString("gender").equals("male")) {
                    admin_gender_male.setSelected(true);
                    } else { 
                     admin_gender_female.setSelected(true);
                    }

                if(rs.getString("account_type").matches("block")){
                    Admin_block_Warning.setText("ACCOUNT BLOCKED");
                    }
                    else if(rs.getString("account_type").isEmpty()){
                    Admin_privilage_Warning.setText("USER PRIVILLAGE NOT SET");
                    }
                    else{String add9 =rs.getString("account_type");
                account_privallage.setSelectedItem(add9);
                    }
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
            
            String sql ="select * from feebal where student_id='"+Table_click+"'";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
            String add1=rs.getString("feedue");
            fee_due.setText(add1);
            String add2=rs.getString("feepaid");
            fee_paid.setText(add2);
//            String add5=rs.getString("feedue");
//            fee_paid.setText(add5);
            
            float bal1 = Float.parseFloat(add1); 
            float bal2 = Float.parseFloat(add2) ;
            float bal = bal1-bal2;
            String add3= Float.toString(bal);
            fee_balance.setText(add3);
            
            if(bal2 >= bal1*0.75){
                eligibility_status.setText("Authorized");
                eligibility_status.setForeground(new Color(0,255,0));
                }else{ 
                        eligibility_status.setText("Not Authorized"); 
                        eligibility_status.setForeground(new Color(255,0,0));}
           
            }
        
            }catch(SQLException e){
            
                JOptionPane.showMessageDialog(null, e);
            
                }
    }//GEN-LAST:event_button_feebal_viewActionPerformed

    private void eligible_list_processActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eligible_list_processActionPerformed
        
        String val1=min_fee_allowed.getText();
        String val2=max_fee_payable.getText();
        
        try{
            String sql = "select student_regno as 'Registration No', feepaid as 'Fee Paid' from feebal where feepaid between '"+val1+"' and '"+val2+"'";
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
        table_delete.setEnabled(true);
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        
             try{
            
            int row =student_info_table.getSelectedRow();
            String Table_click=(student_info_table.getModel().getValueAt(row, 0).toString());
            id = Table_click;
            
            String sql="select * from student_info where student_regno='"+Table_click+"'";
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
                
                if (rs.getString("gender").equals("male")) {
                    gender_male.setSelected(true);
                    } else { 
                     gender_female.setSelected(true);
                    }
                
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
            String URL = "http://portal.uoeld.ac.ke";
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
                        return new PasswordAuthentication("obi.projecttest@gmail.com", "0bi.peojectTest");
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

    private void log_out_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_out_barActionPerformed
        // TODO add your handling code here:
    int x = JOptionPane.showConfirmDialog(null, "Are you sure yu want to log out?","Log Out",JOptionPane.YES_NO_OPTION);
        if(x==0){
       
            close();
            Admin_Login login = new Admin_Login();
            login.setVisible(true);
       
        }else{
        
            }
    }//GEN-LAST:event_log_out_barActionPerformed

    private void close_pane_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close_pane_barActionPerformed
        // TODO add your handling code here:
        System.exit(0);

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

    private void txt_email_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_email_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_email_addressActionPerformed

    private void pie_chartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pie_chartActionPerformed
        
        try{
            String sql="select * from feebal";
            String sql1="select * from feebal";
            String sql2="select * from feebal";
            String sql3="select * from feebal";
            String sql4="select * from feebal";
            String sql5="select * from feebal";
            
            
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sql1);
            pst2=conn.prepareStatement(sql2);
            pst3=conn.prepareStatement(sql3);
            pst4=conn.prepareStatement(sql4);
            pst5=conn.prepareStatement(sql5);
            
            rs=pst.executeQuery(sql);
            rs1=pst1.executeQuery(sql1);
            rs2=pst2.executeQuery(sql2);
            rs3=pst3.executeQuery(sql3);
            rs4=pst4.executeQuery(sql4);
            rs5=pst5.executeQuery(sql5);
            
            
            int p=0;
            int o=0;
            int i=0;
            while(rs.next()){
                String graphSchool = rs.getString("school");
                double due = rs.getDouble("feedue");
                double paid = rs.getDouble("feepaid");
                if ("SCIENCE".equals(graphSchool) && due == paid){
                    p++;}else  if ("SCIENCE".equals(graphSchool) && paid >= due/4 && paid < due){
                            o++;}else  if ("SCIENCE".equals(graphSchool) && paid < due/4){
                                        i++;
                                        }
                }
            
            int m=0;
            int n=0;
            int c=0;
            while(rs1.next()){
                String graphSchool = rs1.getString("school");
                double due = rs1.getDouble("feedue");
                double paid = rs1.getDouble("feepaid");
                if ("ENGINEERING".equals(graphSchool) && due == paid){
                    m++;}else  if ("ENGINEERING".equals(graphSchool) && paid >= due/4 && paid < due){
                            n++;}else  if ("ENGINEERING".equals(graphSchool) && paid < due/4){
                                        c++;
                                        }
                }
            
            int q=0;
            int r=0;
            int w=0;
            while(rs2.next()){
                String graphSchool = rs2.getString("school");
                double due = rs2.getDouble("feedue");
                double paid = rs2.getDouble("feepaid");
                if ("EDUCATION".equals(graphSchool) && due == paid){
                    q++;}else  if ("EDUCATION".equals(graphSchool) && paid >= due/4 && paid < due){
                            r++;}else  if ("EDUCATION".equals(graphSchool) && paid < due/4){
                                        w++;
                                        }
                }
            
            int a=0;
            int b=0;
            int d=0;
            while(rs3.next()){
                String graphSchool = rs3.getString("school");
                double due = rs3.getDouble("feedue");
                double paid = rs3.getDouble("feepaid");
                if ("BUSINESS".equals(graphSchool) && due == paid){
                    a++;}else  if ("BUSINESS".equals(graphSchool) && paid >= due/4 && paid < due){
                            b++;}else  if ("BUSINESS".equals(graphSchool) && paid < due/4){
                                        d++;
                                        }
                }
            
            int e=0;
            int f=0;
            int j=0;
            while(rs4.next()){
                String graphSchool = rs4.getString("school");
                double due = rs4.getDouble("feedue");
                double paid = rs4.getDouble("feepaid");
                if ("HOTEL & HOSPITALITY".equals(graphSchool) && due == paid){
                    e++;}else  if ("HOTEL & HOSPITALITY".equals(graphSchool) && paid >= due/4 && paid < due){
                            f++;}else  if ("HOTEL & HOSPITALITY".equals(graphSchool) && paid < due/4){
                                        j++;
                                        }
                }
            
            int g=0;
            int h=0;
            int k=0;
            while(rs5.next()){
                String graphSchool = rs5.getString("school");
                double due = rs5.getDouble("feedue");
                double paid = rs5.getDouble("feepaid");
                if ("FORESTRY".equals(graphSchool) && due == paid){
                    g++;}else  if ("FORESTRY".equals(graphSchool) && paid >= due/4 && paid < due){
                            h++;}else  if ("FORESTRY".equals(graphSchool) && paid < due/4){
                                        k++;
                                        }
                }
        
        final String cleared = "CLEARED";        
        final String pending = "PENDING";
        final String minPay = "LOW PAY";
        final String science = "Science";        
        final String engineering = "Engineering";        
        final String education = "Education";        
        final String business = "Business"; 
        final String hotels = "Hotel & Hospitality"; 
        final String forestry = "Forestry"; 
        
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
        dataset.setValue( p , cleared , science );        
        dataset.setValue( m , cleared , engineering );        
        dataset.setValue( q , cleared , education ); 
        dataset.setValue( a , cleared , business );
        dataset.setValue( e , cleared , hotels );
        dataset.setValue( g , cleared , forestry );

        dataset.setValue( o , pending , science );        
        dataset.setValue( n , pending , engineering );       
        dataset.setValue( r , pending , education );        
        dataset.setValue( b , pending , business );
        dataset.setValue( f , pending , hotels );
        dataset.setValue( h , pending , forestry );
        
        dataset.setValue( i , minPay , science );        
        dataset.setValue( c , minPay , engineering );       
        dataset.setValue( w , minPay , education );        
        dataset.setValue( d , minPay , business );
        dataset.setValue( j , minPay , hotels );
        dataset.setValue( k , minPay , forestry );
                        
            JFreeChart barchart = ChartFactory.createBarChart("Bar Graph", "School", "Gender", dataset, PlotOrientation.VERTICAL, false,true,false);
            CategoryPlot bar = barchart.getCategoryPlot();
            bar.setRangeGridlinePaint(Color.ORANGE);
            
            
            ChartPanel barPanel = new ChartPanel(barchart);
            
            StatisticsChanger.removeAll();
            StatisticsChanger.add(barPanel, BorderLayout.CENTER);
            StatisticsChanger.validate();
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }                
                     
    }//GEN-LAST:event_pie_chartActionPerformed

    private void menu_panel_btn4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_panel_btn4MousePressed
        // menu btns press design:
        
        ActivateButtonColor(menu_panel_btn4);
        ind_4.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{menu_panel_btn5,menu_panel_btn1,menu_panel_btn3,menu_panel_btn2}, new JPanel[]{ind_7,ind_1,ind_3,ind_2});
        
        
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(Admin_Edit_Panel);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //student_search.setEnabled(true);
        student_search.setEditable(true);
        
        admin_gender_female.setEnabled(false);
        admin_gender_female.setForeground(Color.black);
        
        admin_gender_male.setEnabled(false);
        admin_gender_male.setForeground(Color.black);
    }//GEN-LAST:event_menu_panel_btn4MousePressed

    private void menu_panel_btn2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_panel_btn2MousePressed
        // menu btns press design:
        ActivateButtonColor(menu_panel_btn2);
        ind_2.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{menu_panel_btn5,menu_panel_btn4,menu_panel_btn3,menu_panel_btn1}, new JPanel[]{ind_7,ind_4,ind_3,ind_1});
        
         
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(All_Student_Edit_Panel);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //student_search.setEnabled(true);
        student_search.setEditable(true);
    }//GEN-LAST:event_menu_panel_btn2MousePressed

    private void menu_panel_btn3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_panel_btn3MousePressed
        // menu btns press design:
        ActivateButtonColor(menu_panel_btn3);
        ind_3.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{menu_panel_btn5,menu_panel_btn4,menu_panel_btn2,menu_panel_btn1}, new JPanel[]{ind_7,ind_4,ind_2,ind_1});
        
         
        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(Graphical_Edit_Statistics);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //student_search.setEnabled(false);
        student_search.setEditable(false);
    }//GEN-LAST:event_menu_panel_btn3MousePressed

    private void menu_panel_btn1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_panel_btn1MousePressed
        // menu btns press design:
        ActivateButtonColor(menu_panel_btn1);
        ind_1.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{menu_panel_btn5,menu_panel_btn4,menu_panel_btn3,menu_panel_btn2}, new JPanel[]{ind_7,ind_4,ind_3,ind_2});
        

        // remove panel
        Main_Dynamic_Panel.removeAll();
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        //add panel
        Main_Dynamic_Panel.add(System_Edit_Dashboard);
        Main_Dynamic_Panel.repaint();
        Main_Dynamic_Panel.revalidate();
        
        FunctionControl.Autofill();
        //student_search.setEnabled(false);
        student_search.setEditable(false);
                                                                
    }//GEN-LAST:event_menu_panel_btn1MousePressed

    private void menu_panel_btn5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_panel_btn5MousePressed
        // menu btns press design:
        ActivateButtonColor(menu_panel_btn5);
        ind_7.setOpaque(true);
        DeactivateButtonColor(new JPanel[]{menu_panel_btn1,menu_panel_btn4,menu_panel_btn3,menu_panel_btn2}, new JPanel[]{ind_1,ind_4,ind_3,ind_2});
        
         About_Frame about = new About_Frame();
         about.setVisible(true);
    }//GEN-LAST:event_menu_panel_btn5MousePressed

    private void txt_employment_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employment_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employment_noActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        // UOE Icon resizing call

        BufferedImage originalImage = null;
        BufferedImage originalImage2 = null;
        BufferedImage originalImage3 = null;
        int type = 0;
        int type2 = 0;
        int type3 = 0;
        
        //First instance of image resize starts
        try{
            originalImage = ImageIO.read(new File("C:\\Users\\pc\\Documents\\NetBeansProjects\\Exam_Authentication_Sys\\src\\images\\uoe_Admin.jpg"));
            type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        
            }catch (IOException ex){
                Logger.getLogger(Main_Activity_Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
        int h = uoe_admin_pic.getHeight();
        int w = uoe_admin_pic.getWidth();
        ImageIcon icon1 = new ImageIcon(FunctionControl.scaleimage(originalImage, w, h, type));
        uoe_admin_pic.setIcon(icon1);
        
        //First instance of image resize ends
        
      //
      //
      
      //Second instance of image resize starts
        try{
            originalImage2 = ImageIO.read(new File("C:\\Users\\pc\\Documents\\NetBeansProjects\\Exam_Authentication_Sys\\src\\images\\uoe_bar_Icon.jpg"));
            type2 = originalImage2.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage2.getType();
        
            }catch (IOException ex){
                Logger.getLogger(Main_Activity_Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
        int x = uoe_students_pic.getHeight();
        int y = uoe_students_pic.getWidth();
        ImageIcon icon2 = new ImageIcon(FunctionControl.scaleimage(originalImage2, y, x, type2));
        uoe_students_pic.setIcon(icon2);
        
        //Second instance of image resize ends
        
        //
        
        //Third instance of image resize starts
        try{
            originalImage3 = ImageIO.read(new File("C:\\Users\\pc\\Documents\\NetBeansProjects\\Exam_Authentication_Sys\\src\\images\\eldorate.png"));
            type3 = originalImage3.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage3.getType();
        
            }catch (IOException ex){
                Logger.getLogger(Main_Activity_Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
        int m = uoe_header_logo.getHeight();
        int n = uoe_header_logo.getWidth();
        
        int p = AllStudent_UoeHeader.getHeight();
        int q = AllStudent_UoeHeader.getWidth();
        
        int r = GraphicalStat_UoeHeader.getHeight();
        int s = GraphicalStat_UoeHeader.getWidth();
        
        int c = AdminUoe_Header.getHeight();
        int d = AdminUoe_Header.getWidth();
        ImageIcon icon3 = new ImageIcon(FunctionControl.scaleimage(originalImage3, n, m, type3));
        ImageIcon icon4 = new ImageIcon(FunctionControl.scaleimage(originalImage3, q, p, type3));
        ImageIcon icon5 = new ImageIcon(FunctionControl.scaleimage(originalImage3, s, r, type3));
        ImageIcon icon6 = new ImageIcon(FunctionControl.scaleimage(originalImage3, d, c, type3));
        
        uoe_header_logo.setIcon(icon3);
        AllStudent_UoeHeader.setIcon(icon4);
        GraphicalStat_UoeHeader.setIcon(icon5);
        AdminUoe_Header.setIcon(icon6);
        
        //Third instance of image resize ends
      
    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            String Gender = "select gender, count(*) from student_info group by gender";
            JDBCPieDataset Dataset = new JDBCPieDataset(javaconnect.ConnercrDb(),Gender);
            Dataset.executeQuery(Gender);
 
            
            JFreeChart chart = ChartFactory.createPieChart("Pie Chart", Dataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
            //PiePlot p = (PiePlot)chart.getPlot();
            
            ChartPanel panel = new ChartPanel(chart);
            
            male_female_pie.removeAll();
            male_female_pie.add(panel, BorderLayout.CENTER);
            male_female_pie.validate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try{
        String query = "select date_of_input, count(*) from student_info group by date_of_input";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createStackedAreaChart("Line Chart", "test1", "tst2", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);

        fee_StatsChart.removeAll();
        fee_StatsChart.add(panel, BorderLayout.CENTER);
        fee_StatsChart.validate();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_last_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_last_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_last_nameActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Chart_ContentPanel.removeAll();
        
        try{
        String query = "select date_of_input, count(*) from student_info group by date_of_input";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createStackedAreaChart("Line Chart", "test1", "tst2", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);

        fee_StatsChart.removeAll();
        fee_StatsChart.add(panel, BorderLayout.CENTER);
        fee_StatsChart.validate();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        
        
        try {
            String Gender = "select gender, count(*) from student_info group by gender";
            JDBCPieDataset Dataset = new JDBCPieDataset(javaconnect.ConnercrDb(),Gender);
            Dataset.executeQuery(Gender);
 
            
            JFreeChart chart = ChartFactory.createPieChart("Pie Chart", Dataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
            //PiePlot p = (PiePlot)chart.getPlot();
            
            ChartPanel panel = new ChartPanel(chart);
            
            male_female_pie.removeAll();
            male_female_pie.add(panel, BorderLayout.CENTER);
            male_female_pie.validate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
         try{
        String query = "select school, count(*) from student_info group by school";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createLineChart("Line Chart", "test1", "tst2", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);
        
        StatisticsChanger.removeAll();
        StatisticsChanger.add(panel, BorderLayout.CENTER);
        StatisticsChanger.validate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void viewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllActionPerformed
        // Total Reg Student Fill Start
         try{
             
            String sql="select count(*) from student_info";
            String sql1="select count(gender) from student_info where gender = 'male'";
            String sql2="select count(gender) from student_info where gender = 'female'";
            
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sql1);
            pst2=conn.prepareStatement(sql2);
            
            rs=pst.executeQuery(sql);
            rs1=pst1.executeQuery(sql1);
            rs2=pst2.executeQuery(sql2);
            
//            int p = Integer.parseInt(sql1);
//            int n = Integer.parseInt(sql2);
            
            if(rs.next() && rs1.next() && rs2.next()){
               
                int p =  rs1.getInt("count(gender)");
                male_count.setText(Integer.toString(p));
                
                int n = rs2.getInt("count(gender)");
                female_count.setText(Integer.toString(n));
                  
                String m =rs.getString("count(*)");
                total_count.setText(m);
              
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
        // Total Reg Student Fill End
        
        //Student Fee Count Fill Start
         try{
            
            String sql="select * from feebal";
            String sql1="select * from feebal";
            String sql2="select * from feebal";
            
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sql1);
            pst2=conn.prepareStatement(sql2);
            
            rs=pst.executeQuery(sql);
            rs1=pst1.executeQuery(sql1);
            rs2=pst2.executeQuery(sql2);
            
            int x = 0;
            while(rs.next()){
                double due = rs.getDouble("feedue");
                double paid = rs.getDouble("feepaid");
                if (due == paid){
                    x++;
                }
            }
            cleared_fee.setText(Integer.toString(x));
            
            
            int y = 0;
            while(rs1.next()){
                double pend = rs1.getDouble("feepaid");
                double due = rs1.getDouble("feedue");
                
                double min = due/4;
                if (pend >= min && pend < due){
                    y++;
                }
            }
            pending_fee.setText(Integer.toString(y));
            
            
            int z = 0;
            while(rs2.next()){
                double zero = rs2.getDouble("feepaid");
                double due = rs2.getDouble("feedue");
                
                double min = due/4;
                if (zero < min){
                    z++;
                }
            }
            zero_fee.setText(Integer.toString(z));
            
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
         //Student Fee Count Fill End
         
         
         //Total System Staff Fill Start
         try{
            
            String sql="select count(*) from admin_login";
            String sql1="select count(account_type) from admin_login where account_type = 'Admin'";
            String sql2="select count(account_type) from admin_login where account_type = 'Support Staff'";
            
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sql1);
            pst2=conn.prepareStatement(sql2);
            
            rs=pst.executeQuery(sql);
            rs1=pst1.executeQuery(sql1);
            rs2=pst2.executeQuery(sql2);
            
//            int p = Integer.parseInt(sql1);
//            int n = Integer.parseInt(sql2);
            
            if(rs.next() && rs1.next() && rs2.next()){
               
                int p =  rs1.getInt("count(account_type)");
                admin_staff.setText(Integer.toString(p));
                
                int n = rs2.getInt("count(account_type)");
                support_staff.setText(Integer.toString(n));
                  
                String m =rs.getString("count(*)");
                all_staff.setText(m);
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
         //Total System Staff Fill End
         FunctionControl.Autofill();
    }//GEN-LAST:event_viewAllActionPerformed

    private void admin_list_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_list_refreshActionPerformed
        // TODO add your handling code here:
        Update_table();
    }//GEN-LAST:event_admin_list_refreshActionPerformed

    private void admin_signup_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_admin_signup_listMouseClicked
        
        int row =admin_signup_list.getSelectedRow();
            String Table_click=(admin_signup_list.getModel().getValueAt(row, 0).toString());
        
        try{
            String sql="select * from admin_login where admin_id='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                
                String add1 =rs.getString("employment_no");
                txt_employment_no.setText(add1);
                
                String add2 =rs.getString("first_name");
                txt_first_name.setText(add2);
                  
                String add3 =rs.getString("last_name");
                txt_last_name.setText(add3);
                
                String add4 =rs.getString("email");
                txt_email_address.setText(add4);
                
                String add5 =rs.getString("mobile");
                txt_mobile.setText(add5);
                
                String add6 =rs.getString("username");
                txt_username.setText(add6);
               
                if(rs.getString("account_type").matches("block")){
                    Admin_block_Warning.setText("ACCOUNT BLOCKED");
                    account_privallage.setSelectedIndex(0);
                    }
                    else if(rs.getString("account_type").isEmpty() || rs.wasNull()){
                    Admin_privilage_Warning.setText("USER PRIVILLAGE NOT SET");
                    account_privallage.setSelectedIndex(0);
                    }
                    else{String add7 =rs.getString("account_type");
                account_privallage.setSelectedItem(add7);
                    }

                if (rs.getString("gender").equals("male")) {
                    admin_gender_male.setSelected(true);
                    } else { 
                     admin_gender_female.setSelected(true);
                    }
            }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        //Admin_privilage_Warning.setText("USER PRIVILLAGE NOT SET");
        }
    }//GEN-LAST:event_admin_signup_listMouseClicked

    private void admin_signup_listKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_signup_listKeyReleased
        
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        
             try{
            
            int row =admin_signup_list.getSelectedRow();
            String Table_click=(admin_signup_list.getModel().getValueAt(row, 0).toString());
            String sql="select * from admin_login where admin_id='"+Table_click+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                
                String add1 =rs.getString("employment_no");
                txt_employment_no.setText(add1);
                
                String add2 =rs.getString("first_name");
                txt_first_name.setText(add2);
                  
                String add3 =rs.getString("last_name");
                txt_last_name.setText(add3);
                
                String add4 =rs.getString("email");
                txt_email_address.setText(add4);
                
                String add5 =rs.getString("mobile");
                txt_mobile.setText(add5);
                
                String add6 =rs.getString("username");
                txt_username.setText(add6);
               
                 if(rs.getString("account_type").matches("block")){
                    Admin_block_Warning.setText("ACCOUNT BLOCKED");
                    account_privallage.setSelectedIndex(0);
                    }
                    else if(rs.getString("account_type").isEmpty()){
                    Admin_privilage_Warning.setText("USER PRIVILLAGE NOT SET");
                    account_privallage.setSelectedIndex(0);
                    }
                    else{String add7 =rs.getString("account_type");
                    account_privallage.setSelectedItem(add7);
                    }
                 
                 if (rs.getString("gender").equals("male")) {
                    admin_gender_male.setSelected(true);
                    } else { 
                     admin_gender_female.setSelected(true);
                    }
                }
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        }
            
      }
    }//GEN-LAST:event_admin_signup_listKeyReleased

    private void privillage_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privillage_updateActionPerformed
        // TODO add your handling code here:
        try{
        
                String value1= txt_username.getText();
                String value2= account_privallage.getSelectedItem().toString();
                
                int row =admin_signup_list.getSelectedRow();
                String Table_click=(admin_signup_list.getModel().getValueAt(row, 0).toString());
                String sql="update admin_login set username='"+value1+"' , account_type='"+value2+"' where admin_id='"+Table_click+"'";
                pst=conn.prepareStatement(sql);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Updated Succesfully");
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
    }//GEN-LAST:event_privillage_updateActionPerformed

    private void account_blockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_blockActionPerformed
        // TODO add your handling code here:
        
        try{
                int row =admin_signup_list.getSelectedRow();
                String Table_click=(admin_signup_list.getModel().getValueAt(row, 0).toString());
                String sql="update admin_login set account_type='block' where admin_id='"+Table_click+"'";
                pst=conn.prepareStatement(sql);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "User Blocked");
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
    }//GEN-LAST:event_account_blockActionPerformed

    private void account_unblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_unblockActionPerformed
        // TODO add your handling code here:
        try{
                int row =admin_signup_list.getSelectedRow();
                String Table_click=(admin_signup_list.getModel().getValueAt(row, 0).toString());
                String sql="update admin_login set account_type='"+""+"' where admin_id='"+Table_click+"' && account_type = 'block'";
                pst=conn.prepareStatement(sql);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "User Un-Blocked Successfully");
                
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
    }//GEN-LAST:event_account_unblockActionPerformed

    private void account_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_account_deleteActionPerformed
        
        //Delete confirmation Dialog
        int x = JOptionPane.showConfirmDialog(null, "Do You Want To Delete","Delete",JOptionPane.YES_NO_OPTION);
        if(x==0){
        
        String sql="delete from admin_login where employment_no =?";
        try{
        
            pst=conn.prepareStatement(sql);
            
            pst.setString(1, txt_employment_no.getText());
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Account Deleted");
        
            }catch(Exception e){
            
                JOptionPane.showMessageDialog(null, e);
                
                }
        Update_table();
        
        }
        
    }//GEN-LAST:event_account_deleteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        try{
        String query = "select school, count(*) from student_info group by school";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createLineChart("Line Chart", "test1", "tst2", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);
        
        StatisticsChanger.removeAll();
        StatisticsChanger.add(panel, BorderLayout.CENTER);
        StatisticsChanger.validate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void admin_signup_listKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_signup_listKeyPressed
        // TODO add your handling code here:
        Admin_block_Warning.setText("");
        Admin_privilage_Warning.setText("");
    }//GEN-LAST:event_admin_signup_listKeyPressed

    private void admin_signup_listMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_admin_signup_listMousePressed
        // TODO add your handling code here:
        Admin_block_Warning.setText("");
        Admin_privilage_Warning.setText("");
    }//GEN-LAST:event_admin_signup_listMousePressed

    private void admin_gender_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_gender_femaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_admin_gender_femaleActionPerformed

    private void Account_DisplayPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account_DisplayPanelMousePressed
        //Functions To set anchor label of account user type
            String userAccount = account_label.getText();
            String userName = user_label.getText();
            
            
            Change_Password c =new Change_Password();
                            c.setVisible(true);

        try{
            Change_Password.account_label_ChangeP.setText(userAccount);
            Change_Password.user_label_ChangeP.setText(userName);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
               }
        //Functions To set anchor label of account user type end
        
        //FunctionControl.AnchorLabel();
    }//GEN-LAST:event_Account_DisplayPanelMousePressed

    private void Account_DisplayPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account_DisplayPanelMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_Account_DisplayPanelMouseDragged

    private void Account_DisplayPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account_DisplayPanelMouseEntered
        // TODO add your handling code here:
        ChangePassButtonColor(Account_DisplayPanel);
    }//GEN-LAST:event_Account_DisplayPanelMouseEntered

    private void Account_DisplayPanelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Account_DisplayPanelFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_Account_DisplayPanelFocusGained

    private void Account_DisplayPanelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Account_DisplayPanelFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_Account_DisplayPanelFocusLost

    private void Account_DisplayPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Account_DisplayPanelMouseExited
        // TODO add your handling code here:
        ResetPassButtonColor(Account_DisplayPanel);
    }//GEN-LAST:event_Account_DisplayPanelMouseExited

    private void student_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_searchKeyPressed
        // TODO add your handling code here:
        Admin_block_Warning.setText("");
        Admin_privilage_Warning.setText("");
    }//GEN-LAST:event_student_searchKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Update_table();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Code to fill bar graph with gender ratio per school
        try{
            String sql="select * from student_info";
            String sql1="select * from student_info";
            String sql2="select * from student_info";
            String sql3="select * from student_info";
            String sql4="select * from student_info";
            String sql5="select * from student_info";
            
            
            pst=conn.prepareStatement(sql);
            pst1=conn.prepareStatement(sql1);
            pst2=conn.prepareStatement(sql2);
            pst3=conn.prepareStatement(sql3);
            pst4=conn.prepareStatement(sql4);
            pst5=conn.prepareStatement(sql5);
            
            rs=pst.executeQuery(sql);
            rs1=pst1.executeQuery(sql1);
            rs2=pst2.executeQuery(sql2);
            rs3=pst3.executeQuery(sql3);
            rs4=pst4.executeQuery(sql4);
            rs5=pst5.executeQuery(sql5);
            
            
            int p=0;
            int o=0;
            while(rs.next()){
                String graphSchool = rs.getString("school");
                String graphGender = rs.getString("gender");
                if ("SCIENCE".equals(graphSchool) && "male".equals(graphGender)){
                    p++;}else  if ("SCIENCE".equals(graphSchool) && "female".equals(graphGender)){
                            o++;
                            }
                }
            
            int m=0;
            int n=0;
            while(rs1.next()){
                String graphSchool = rs1.getString("school");
                String graphGender = rs1.getString("gender");
                if ("ENGINEERING".equals(graphSchool) && "male".equals(graphGender)){
                    m++;}else  if ("ENGINEERING".equals(graphSchool) && "female".equals(graphGender)){
                            n++;
                            }
                }
            
            int q=0;
            int r=0;
            while(rs2.next()){
                String graphSchool = rs2.getString("school");
                String graphGender = rs2.getString("gender");
                if ("EDUCATION".equals(graphSchool) && "male".equals(graphGender)){
                    q++;}else if ("EDUCATION".equals(graphSchool) && "female".equals(graphGender)){
                            r++;
                            }
                }
            
            int a=0;
            int b=0;
            while(rs3.next()){
                String graphSchool = rs3.getString("school");
                String graphGender = rs3.getString("gender");
                if ("BUSINESS".equals(graphSchool) && "male".equals(graphGender)){
                    a++;}else  if ("BUSINESS".equals(graphSchool) && "female".equals(graphGender)){
                            b++;
                            }
                }
            
            int e=0;
            int f=0;
            while(rs4.next()){
                String graphSchool = rs4.getString("school");
                String graphGender = rs4.getString("gender");
                if ("HOTEL & HOSPITALITY".equals(graphSchool) && "male".equals(graphGender)){
                    e++;}else  if ("HOTEL & HOSPITALITY".equals(graphSchool) && "female".equals(graphGender)){
                            f++;
                            }
                }
            
            int g=0;
            int h=0;
            while(rs5.next()){
                String graphSchool = rs5.getString("school");
                String graphGender = rs5.getString("gender");
                if ("FORESTRY".equals(graphSchool) && "male".equals(graphGender)){
                    g++;}else  if ("FORESTRY".equals(graphSchool) && "female".equals(graphGender)){
                            h++;
                            }
                }
        
        final String male = "MALE";        
        final String female = "FEMALE";    
        final String science = "Science";        
        final String engineering = "Engineering";        
        final String education = "Education";        
        final String business = "Business"; 
        final String hotels = "Hotel & Hospitality"; 
        final String forestry = "Forestry"; 
        
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
        dataset.setValue( p , male , science );        
        dataset.setValue( m , male , engineering );        
        dataset.setValue( q , male , education ); 
        dataset.setValue( a , male , business );
        dataset.setValue( e , male , hotels );
        dataset.setValue( g , male , forestry );

        dataset.setValue( o , female , science );        
        dataset.setValue( n , female , engineering );       
        dataset.setValue( r , female , education );        
        dataset.setValue( b , female , business );
        dataset.setValue( f , female , hotels );
        dataset.setValue( h , female , forestry );
                        
            JFreeChart barchart = ChartFactory.createBarChart("Bar Graph", "School", "Gender", dataset, PlotOrientation.VERTICAL, false,true,false);
            CategoryPlot bar = barchart.getCategoryPlot();
            bar.setRangeGridlinePaint(Color.ORANGE);
            
            
            ChartPanel barPanel = new ChartPanel(barchart);
            
            StatisticsChanger.removeAll();
            StatisticsChanger.add(barPanel, BorderLayout.CENTER);
            StatisticsChanger.validate();
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void fee_balance_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fee_balance_tableMouseClicked
        // TODO add your handling code here:
        table_delete.setEnabled(true);
        int row =fee_balance_table.getSelectedRow();
            String Table_click=(fee_balance_table.getModel().getValueAt(row, 0).toString());
            id = Table_click;
    }//GEN-LAST:event_fee_balance_tableMouseClicked

    private void database_tablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_database_tablesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_database_tablesMouseClicked

    private void exam_eligible_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exam_eligible_tableMouseClicked
        // TODO add your handling code here:
        table_delete.setEnabled(false);
    }//GEN-LAST:event_exam_eligible_tableMouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7MouseClicked

    private void fee_balance_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fee_balance_tableKeyReleased
        // TODO add your handling code here:
        table_delete.setEnabled(true);
        
        if (evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
        int row =fee_balance_table.getSelectedRow();
            String Table_click=(fee_balance_table.getModel().getValueAt(row, 0).toString());
            id = Table_click;
        }
    }//GEN-LAST:event_fee_balance_tableKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         Update_table();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        fee_due.setText("");
        fee_paid.setText("");
        fee_balance.setText("");
        eligibility_status.setText("");
    }//GEN-LAST:event_jButton8ActionPerformed

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
    
     private void ChangePassButtonColor(JPanel panel){
        panel.setBackground(new Color(0,0,77));
    
    }
     private void ResetPassButtonColor(JPanel panel){
        panel.setBackground(new Color(0,0,51));
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Account_DisplayPanel;
    private javax.swing.JMenuBar ActionControl_Panel;
    private javax.swing.JLabel AdminUoe_Header;
    private javax.swing.JPanel Admin_Autofill_Panel;
    private javax.swing.JPanel Admin_Edit_Panel;
    private javax.swing.JPanel Admin_FormContrl_Panel;
    private javax.swing.JPanel Admin_Header;
    private javax.swing.JPanel Admin_List_Panel;
    private javax.swing.JPanel Admin_MiniHeader;
    private javax.swing.JLabel Admin_block_Warning;
    private javax.swing.JLabel Admin_privilage_Warning;
    private javax.swing.JLabel AllStudent_UoeHeader;
    public static javax.swing.JPanel All_Student_Edit_Panel;
    private javax.swing.JPanel All_Student_Input_Panel;
    private javax.swing.JPanel Chart_ContentPanel;
    public static javax.swing.JPanel Dashboard_Chart;
    private javax.swing.JLabel ElligibleStudets_barNo;
    private javax.swing.JLabel GraphicalStat_UoeHeader;
    private javax.swing.JPanel Graphical_Edit_Statistics;
    private javax.swing.JPanel Header_Panel;
    private javax.swing.JLabel IsoCertified_Label;
    private javax.swing.JLabel IsoCertified_Label1;
    private javax.swing.JLabel IsoCertified_Label2;
    private javax.swing.JLabel Iso_Text;
    public static javax.swing.JPanel Main_Dynamic_Panel;
    private javax.swing.JPanel Menu_Panel;
    private javax.swing.JPanel MiniHeader_Panel;
    private javax.swing.JPanel Privilage_Edit_Panel;
    private javax.swing.JLabel Staff_barNo;
    private javax.swing.JPanel StatisticsChanger;
    private javax.swing.JPanel Statistics_Buttons_Panel;
    private javax.swing.JPanel Statistics_Header;
    private javax.swing.JPanel Statistics_MiniHeader;
    private javax.swing.JLabel Students_barNo;
    public static javax.swing.JPanel System_Edit_Dashboard;
    private javax.swing.JButton account_block;
    private javax.swing.JButton account_delete;
    public static javax.swing.JLabel account_label;
    private javax.swing.JComboBox<String> account_privallage;
    private javax.swing.JButton account_unblock;
    private javax.swing.JCheckBox admin_gender_female;
    private javax.swing.JCheckBox admin_gender_male;
    private javax.swing.JButton admin_list_refresh;
    public static javax.swing.JTable admin_signup_list;
    private javax.swing.JLabel admin_staff;
    private javax.swing.JLabel all_staff;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton button_feebal_view;
    private javax.swing.JLabel cleared_fee;
    private javax.swing.JMenuItem close_pane_bar;
    private javax.swing.JTabbedPane database_tables;
    public static javax.swing.JMenu date;
    private com.toedter.calendar.JDateChooser date_of_input;
    private javax.swing.JLabel eligibility_status;
    private javax.swing.JButton eligible_list_process;
    private javax.swing.JButton eligible_table_print;
    private javax.swing.JButton eligible_table_reset;
    private javax.swing.JTable exam_eligible_table;
    private javax.swing.JPanel fee_StatsChart;
    private javax.swing.JTextField fee_balance;
    private javax.swing.JPanel fee_balance_panel;
    private javax.swing.JTable fee_balance_table;
    private javax.swing.JTextField fee_due;
    private javax.swing.JTextField fee_paid;
    private javax.swing.JLabel female_count;
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
    private javax.swing.JPanel ind_4;
    private javax.swing.JPanel ind_7;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem log_out_bar;
    private javax.swing.JTextField mail_attach_name;
    private javax.swing.JTextField mail_file_attach;
    private javax.swing.JTextField mail_from;
    private javax.swing.JTextField mail_subject;
    private javax.swing.JTextField mail_to;
    private javax.swing.JLabel male_count;
    private javax.swing.JPanel male_female_pie;
    private javax.swing.JTextField max_fee_payable;
    private javax.swing.JLabel menu_label_btn1;
    private javax.swing.JLabel menu_label_btn2;
    private javax.swing.JLabel menu_label_btn3;
    private javax.swing.JLabel menu_label_btn4;
    private javax.swing.JLabel menu_label_btn5;
    private javax.swing.JPanel menu_panel_btn1;
    private javax.swing.JPanel menu_panel_btn2;
    private javax.swing.JPanel menu_panel_btn3;
    public static javax.swing.JPanel menu_panel_btn4;
    private javax.swing.JPanel menu_panel_btn5;
    private javax.swing.JTextField min_fee_allowed;
    private javax.swing.JLabel pending_fee;
    private javax.swing.JButton pie_chart;
    private javax.swing.JButton privillage_update;
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
    private javax.swing.JLabel support_staff;
    private javax.swing.JButton table_delete;
    private javax.swing.JButton table_update;
    private javax.swing.JLabel total_count;
    private javax.swing.JTextField txt_email_address;
    private javax.swing.JTextField txt_employment_no;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_last_name;
    private javax.swing.JTextField txt_mobile;
    private javax.swing.JTextField txt_username;
    private javax.swing.JLabel uoe_admin_pic;
    private javax.swing.JLabel uoe_header_logo;
    private javax.swing.JLabel uoe_students_pic;
    public static javax.swing.JLabel user_label;
    private javax.swing.JButton viewAll;
    private javax.swing.JLabel zero_fee;
    // End of variables declaration//GEN-END:variables

    Timer timer;
    private ImageIcon format =null;
    String filename=null;
    int s=0;
    byte[] person_image = null;
    private String gender;
    String attachment_path;
    String id ="";

}
