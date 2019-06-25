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
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
public class Admin_Login extends javax.swing.JFrame {
Connection conn=null;
ResultSet rs =null;
PreparedStatement pst=null;
    /**
     * Creates new form Login_iframe
     */
    public Admin_Login() {
        initComponents();
        conn=javaconnect.ConnercrDb();
    
    }

    //Function to resize image to Jlabel size

    private BufferedImage scaleimage(BufferedImage img, int w, int h, int type){
        BufferedImage resizedImage = new BufferedImage(w, h, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        return resizedImage;
    }
    //Function to resize image to Jlabel size end
    

    public void close(){
    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        admin_username = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        admin_password = new javax.swing.JPasswordField();
        admin_login = new javax.swing.JButton();
        usernamewarning = new javax.swing.JLabel();
        passwordwarning = new javax.swing.JLabel();
        showpassword = new javax.swing.JCheckBox();
        admin_signup = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        icon_label = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 153, 153))); // NOI18N
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
        });

        admin_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_usernameActionPerformed(evt);
            }
        });
        admin_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                admin_usernameKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 1, 12)); // NOI18N
        jLabel2.setText("Password");

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 1, 12)); // NOI18N
        jLabel1.setText("Username");

        admin_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_passwordActionPerformed(evt);
            }
        });
        admin_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                admin_passwordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                admin_passwordKeyReleased(evt);
            }
        });

        admin_login.setText("Log In");
        admin_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        admin_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                admin_loginActionPerformed(evt);
            }
        });
        admin_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                admin_loginKeyReleased(evt);
            }
        });

        usernamewarning.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        usernamewarning.setForeground(new java.awt.Color(255, 0, 0));

        passwordwarning.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        passwordwarning.setForeground(new java.awt.Color(255, 0, 0));

        showpassword.setText("Show Password");
        showpassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpasswordActionPerformed(evt);
            }
        });

        admin_signup.setText("Sign Up");
        admin_signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 8)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("UNIVERSITY OF ELDORET STUDENT ADDMISSION SYSTEM");

        icon_label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UoE_Dark.png"))); // NOI18N

        jLabel5.setText("to reset.");

        jLabel4.setText("Forgot password ?   Click");

        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("Here");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("X");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(icon_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernamewarning, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(admin_login, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(admin_signup, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(admin_password)
                    .addComponent(showpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(admin_username)
                    .addComponent(passwordwarning, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(icon_label, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(admin_username, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernamewarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(admin_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordwarning)
                .addGap(4, 4, 4)
                .addComponent(showpassword)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(admin_login)
                    .addComponent(admin_signup))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(360, 550));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void admin_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_admin_usernameActionPerformed

    private void admin_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_loginActionPerformed
        // TODO add your handling code here:
        
        //Check if fields are empty or not
        if(admin_username.getText().trim().isEmpty() && admin_password.getText().trim().isEmpty()){
            usernamewarning.setText("Username is empty");
            passwordwarning.setText("Password is empty");
        
        }
        else if(admin_username.getText().trim().isEmpty()){
            usernamewarning.setText("Username is empty");
        }
        else if(admin_password.getText().trim().isEmpty()){
            passwordwarning.setText("Password is empty");
        }
        else{
        
        
        String sql ="select * from admin_login where username=? and password=?";
        try{
        pst=conn.prepareStatement(sql);
        pst.setString(1,admin_username.getText());
        pst.setString(2,admin_password.getText());
        ////////////////////////////////////////////////
        ///////////////////
        rs=pst.executeQuery();
        if(rs.next()){
                JOptionPane.showMessageDialog(null, "Login Successfull");
                
                //conection with db for login is closed
                rs.close();
                pst.close();
                //conection with db for login is closed
                
                //function closes login window before opening data window
                close();
                //function closes login window before opening data window
                
                Main_Activity_Frame s =new Main_Activity_Frame();
                s.setVisible(true);
                }else{
                        JOptionPane.showMessageDialog(null, "Username or Password incorrect ");
                        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }   finally {
      
            try{
            rs.close();
            pst.close();
            }
            catch(Exception e){}
            }
        }
    }//GEN-LAST:event_admin_loginActionPerformed

    private void admin_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_passwordKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            
            
         //Check if fields are empty or not
        if(admin_username.getText().trim().isEmpty() && admin_password.getText().trim().isEmpty()){
            usernamewarning.setText("Username is empty");
            passwordwarning.setText("Password is empty");
        
        }
        else if(admin_username.getText().trim().isEmpty()){
            usernamewarning.setText("Username is empty");
        }
        else if(admin_password.getText().trim().isEmpty()){
            passwordwarning.setText("Password is empty");
        }
        else{
        
        String sql ="select * from admin_login where username=? and password=?";
        try{
        pst=conn.prepareStatement(sql);
        pst.setString(1,admin_username.getText());
        pst.setString(2,admin_password.getText());
        ////////////////////////////////////////////////
        ///////////////////
        rs=pst.executeQuery();
        if(rs.next()){
                JOptionPane.showMessageDialog(null, "Login Successfull");
                
                //conection with db for login is closed
                rs.close();
                pst.close();
                //conection with db for login is closed
                
                //function closes login window before opening data window
                close();
                //function closes login window before opening data window
                
                Main_Activity_Frame s =new Main_Activity_Frame();
                s.setVisible(true);
                }else{
                        JOptionPane.showMessageDialog(null, "Username or Password incorrect ");
                        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }   finally {
      
            try{
            rs.close();
            pst.close();
            }
            catch(Exception e){}
            }
        
            }
        }
    }//GEN-LAST:event_admin_passwordKeyPressed

    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1KeyReleased

    private void admin_passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_passwordKeyReleased
        // TODO add your handling code here:
        passwordwarning.setText("");
    }//GEN-LAST:event_admin_passwordKeyReleased

    private void admin_usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_usernameKeyReleased
        // TODO add your handling code here:
        usernamewarning.setText("");
    }//GEN-LAST:event_admin_usernameKeyReleased

    private void showpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpasswordActionPerformed
        // TODO add your handling code here:
        if(showpassword.isSelected()){
            admin_password.setEchoChar((char)0);
        }else {
            admin_password.setEchoChar('*');
            }
    }//GEN-LAST:event_showpasswordActionPerformed

    private void admin_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_admin_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_admin_passwordActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // UOE Icon resizing call

        BufferedImage originalImage = null;
        int type = 0;
        try{
            originalImage = ImageIO.read(new File("C:\\Users\\pc\\Documents\\NetBeansProjects\\Exam_Authentication_Sys\\src\\images\\UoE_Dark.png"));
            type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        
            }catch (IOException ex){
                Logger.getLogger(Admin_Login.class.getName()).log(Level.SEVERE, null, ex);
                }
        int h = icon_label.getHeight();
        int w = icon_label.getWidth();
        ImageIcon icon1 = new ImageIcon(scaleimage(originalImage, w, h, type));
        icon_label.setIcon(icon1);
    }//GEN-LAST:event_formWindowActivated

    private void admin_loginKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_admin_loginKeyReleased
        // TODO add your handling code here:
        System.exit(0);

    }//GEN-LAST:event_admin_loginKeyReleased

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel7MousePressed

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
            java.util.logging.Logger.getLogger(Admin_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin_Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton admin_login;
    private javax.swing.JPasswordField admin_password;
    private javax.swing.JButton admin_signup;
    private javax.swing.JTextField admin_username;
    private javax.swing.JLabel icon_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel passwordwarning;
    private javax.swing.JCheckBox showpassword;
    private javax.swing.JLabel usernamewarning;
    // End of variables declaration//GEN-END:variables
}
