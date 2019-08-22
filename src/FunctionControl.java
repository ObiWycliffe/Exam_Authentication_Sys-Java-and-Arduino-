
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pc
 */
public class FunctionControl {
    
    public static Connection conn=null;
    public static ResultSet rs =null;
    public static PreparedStatement pst=null;
    
    
    public static void Next_Number(){
    try{
            String sql="select max(student_id) as Next from student_info";
            //String sql="select student_id.NEXTVAL from student_info";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            //int n = 0;
            while(rs.next()){
                Integer n = rs.getInt("max(student_id)");
                Main_Activity_Frame.studentid.setText(n.toString());
            }
        }catch(Exception e){}
        }
    
    
    //Function to load Line graph on start of application
    public static void Autofill(){
        try{
        String query = "select date_of_input, count(*) from student_info group by date_of_input";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createLineChart("", "Registration Dates", "Number of Students", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);

        Main_Activity_Frame.Dashboard_Chart.removeAll();
        Main_Activity_Frame.Dashboard_Chart.add(panel, BorderLayout.CENTER);
        Main_Activity_Frame.Dashboard_Chart.validate();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
     }
    
    
    //Function to load Dashboard panel
    public static void LoadDash(){
                        Main_Activity_Frame.Main_Dynamic_Panel.removeAll();
                        Main_Activity_Frame.Main_Dynamic_Panel.add(DashView);
                        Main_Activity_Frame.Main_Dynamic_Panel.repaint();
                        Main_Activity_Frame.Main_Dynamic_Panel.validate();
                        
                        Autofill();
                        }
    
    //Function to Initiate date reading on Menu pane
    public static void CurrentDate(){
    Thread clock = new Thread(){
        public void run(){
            for(;;){
                
                Calendar cal = new GregorianCalendar();
                int day =cal.get(Calendar.DAY_OF_MONTH);
                int month =cal.get(Calendar.MONTH);
                int year =cal.get(Calendar.YEAR);
                Main_Activity_Frame.date.setText("Date: "+day+" / "+(month+1)+"  ["+year+"]");
    
//                int second = cal.get(Calendar.SECOND);
//                int minute = cal.get(Calendar.MINUTE);
//                int hour = cal.get(Calendar.HOUR);
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
    
    
   //Function to resize image to Jlabel size
    public static BufferedImage scaleimage(BufferedImage img, int w, int h, int type){
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
    

  /**
    //Function to Close open Jframe from Menu close button
    public void close(){
    WindowEvent winClosingEvent = new WindowEvent(WindowClose,WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    */
    
   public static void InitCharts(){
       
   try{
        String query = "select date_of_input, count(*) from student_info group by date_of_input";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createStackedAreaChart("Daily Registration", "Dates", "", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);

        Main_Activity_Frame.Stats_Chart.removeAll();
        Main_Activity_Frame.Stats_Chart.add(panel, BorderLayout.CENTER);
        Main_Activity_Frame.Stats_Chart.validate();
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        
        
        try {
            String Gender = "select gender, count(*) from student_info group by gender";
            JDBCPieDataset Dataset = new JDBCPieDataset(javaconnect.ConnercrDb(),Gender);
            Dataset.executeQuery(Gender);
 
            
            JFreeChart chart = ChartFactory.createPieChart("Gender", Dataset, true, true, true);
            //PiePlot p = (PiePlot)chart.getPlot();
            
            ChartPanel panel = new ChartPanel(chart);
            
            Main_Activity_Frame.male_female_pie.removeAll();
            Main_Activity_Frame.male_female_pie.add(panel, BorderLayout.CENTER);
            Main_Activity_Frame.male_female_pie.validate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
         try{
        String query = "select school, count(*) from student_info group by school";
        JDBCCategoryDataset Dataset = new JDBCCategoryDataset(javaconnect.ConnercrDb(), query);
        
        JFreeChart chart = ChartFactory.createLineChart("Number of Student per School", "", "Number of Students", Dataset, PlotOrientation.VERTICAL, false, true, true);
        BarRenderer renderer = null;
        CategoryPlot plot = null;
        renderer = new BarRenderer();
        
        ChartPanel panel = new ChartPanel(chart);
        
        Main_Activity_Frame.StatisticsChanger.removeAll();
        Main_Activity_Frame.StatisticsChanger.add(panel, BorderLayout.CENTER);
        Main_Activity_Frame.StatisticsChanger.validate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
   
   
   } 
    
    //Variables Defination:
    public static JPanel DashView = Main_Activity_Frame.System_Edit_Dashboard; 
    //private final FunctionControl WindowClose = this;
    
}
