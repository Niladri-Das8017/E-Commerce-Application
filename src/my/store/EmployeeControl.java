package my.store;

import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeControl extends JFrame{
    public static JFrame employeeControlFrame;
    public static int eid;
    private JPanel employeeControlPanel;
    private JTextField nid;
    private JTextField nname;
    private JTextField nprice;
    private JComboBox ndep;
    private JComboBox ncat;
    private JTextField nstock;
    private JTextField noffer;
    private JButton insert;
    private JTextField id;
    private JTextField price;
    private JTextField stock;
    private JTextField offer;
    private JButton updateBtn;
    private JDateChooser nmfg;
    private JDateChooser nexp;
    private JPanel newStockPanel;
    private JDateChooser mfg;
    private JDateChooser exp;
    private JButton logOutBtn;
    private JButton stockBtn;
    private JButton browsButton;
    private JLabel imgLabel;
    private JButton empAccountBtn;
    private JLabel head;
    private Blob eBlob;
    private String s;

    public EmployeeControl() {


//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

//Connection
        Connect con =  new Connect();
        con.conn();

//myAccountBtn

        if(eid!=0) {
            //User Details
            try {
                PreparedStatement stmt = con.c.prepareStatement("select * from employee where e_id=?");
                stmt.setInt(1, eid);

                ResultSet rst = stmt.executeQuery();
                rst.next();

                head.setText("Hello "+rst.getString("e_fname"));

                eBlob = rst.getBlob("e_img");
        //Employee Image
                byte[] imByte = eBlob.getBytes(1, (int) eBlob.length());
                InputStream ist = new ByteArrayInputStream(imByte);
                BufferedImage uImg = ImageIO.read(ist);
                Image uImage = uImg;
                ImageIcon uIcon = new ImageIcon(uImage);
                empAccountBtn.setIcon(uIcon);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


//New Stock
        insert.addActionListener(e -> {
            try {

                PreparedStatement st1 = con.c.prepareStatement("select * from stock where item_id=?");

                int nID = Integer.parseInt(nid.getText());

                st1.setInt(1,nID);

                ResultSet rs1 = st1.executeQuery();

                if(rs1.next()) {

                    JOptionPane.showMessageDialog(null, "Item already exists.");

                }
                else {

                    PreparedStatement st = con.c.prepareStatement("insert into stock(item_id,item_name,item_price,item_department,item_category,item_stock,item_offer,item_mfg,item_exp,item_arrival,img) values(?,?,?,?,?,?,?,?,?,?,?)");

                    int nPrice = Integer.parseInt(nprice.getText());
                    int nStock = Integer.parseInt(nstock.getText());
                    int nOffer = Integer.parseInt(noffer.getText());
                    InputStream is = new FileInputStream(new File(s));


                    st.setInt(1, nID);
                    st.setString(2, nname.getText());
                    st.setInt(3, nPrice);
                    st.setString(4, (String) ndep.getSelectedItem());
                    st.setString(5, (String) ncat.getSelectedItem());
                    st.setInt(6, nStock);
                    st.setInt(7, nOffer);

                    st.setString(8, ((JTextField) nmfg.getDateEditor().getUiComponent()).getText());
                    st.setString(9, ((JTextField) nexp.getDateEditor().getUiComponent()).getText());

                    Date date = new Date(new java.util.Date().getTime());
                    st.setDate(10, date);
                    st.setBlob(11,is);

                    st.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success");
                }
                employeeControlFrame.setVisible(false);
                new EmployeeControl().employeeControl(eid);


            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
        logOutBtn.addActionListener(e -> {

            employeeControlFrame.setVisible(false);
            new LogIn().logIn();

        });

        //Update Stock
        updateBtn.addActionListener(e -> {
            try {

                PreparedStatement st2 = con.c.prepareStatement("select * from stock where item_id=?");

                int ID = Integer.parseInt(id.getText());

                st2.setInt(1,ID);

                ResultSet rs2 = st2.executeQuery();

                if(rs2.next()) {
                    PreparedStatement st3 = con.c.prepareStatement("UPDATE stock SET item_price=? ,item_stock= item_stock+? ,item_offer= ? ,item_mfg=? ,item_exp=?,item_arrival=? WHERE item_ID = ?;");

                    int Price = Integer.parseInt(price.getText());
                    int Stock = Integer.parseInt(stock.getText());
                    int Offer = Integer.parseInt(offer.getText());


                    st3.setInt(1, Price);
                    st3.setInt(2, Stock);
                    st3.setInt(3, Offer);

                    st3.setString(4, ((JTextField) mfg.getDateEditor().getUiComponent()).getText());
                    st3.setString(5, ((JTextField) exp.getDateEditor().getUiComponent()).getText());

                    Date date1 = new Date(new java.util.Date().getTime());
                    st3.setDate(6, date1);
                    st3.setInt(7,ID);

                    st3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success");



                }
                else {

                    JOptionPane.showMessageDialog(null,"Item Not Found");
                }
                employeeControlFrame.setVisible(false);
                new EmployeeControl().employeeControl(eid);


            }
            catch (Exception ex2){
                ex2.printStackTrace();
            }
        });
        stockBtn.addActionListener(e -> new ItemStock().itemStock());

//browsButton
        browsButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
            fileChooser.addChoosableFileFilter(filter);
            int result = fileChooser.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                imgLabel.setIcon(ResizeImage(path));
                s = path;
            }
            else if(result == JFileChooser.CANCEL_OPTION){
                System.out.println("No Data");
            }
        });

     //empAccountBtn......
        empAccountBtn.addActionListener(e -> {
            employeeControlFrame.setVisible(false);
            new EmpInfo().empInfo(eid);

        });
    }

//Method To Resize The ImageIcon......
    public ImageIcon ResizeImage(String imgPath){
        ImageIcon MyImage = new ImageIcon(imgPath);
        //Image img = MyImage.getImage();
        //Image newImage = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
        //ImageIcon image = new ImageIcon(newImage);
        return MyImage;
    }

    void employeeControl(int empId){
        eid = empId;
        employeeControlFrame = new JFrame("Employee Control");
        employeeControlFrame.setContentPane(new EmployeeControl().employeeControlPanel);
        employeeControlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeControlFrame.pack();
        employeeControlFrame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        nmfg = new JDateChooser();
        nexp = new JDateChooser();
        mfg = new JDateChooser();
        exp= new JDateChooser();
        empAccountBtn = new JButton();
        updateBtn = new JButton();
        insert= new JButton();
        browsButton = new JButton();
        stockBtn = new JButton();
        logOutBtn = new JButton();

        empAccountBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        browsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stockBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }
}
