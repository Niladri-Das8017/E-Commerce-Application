package my.store;

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

public class EmpInfo {
    public static JFrame empInfoFrame;
    private JPanel empInfoPanel;
    private JButton myAccBtn;
    private JButton OKButton;
    private JButton backButton;
    private JButton logOutButton;
    private JLabel headerLabel;
    private JButton cancelButton;
    private JLabel namelabel;
    private JLabel mobLabel;
    private JLabel addLabel;
    private JLabel emailLabel;
    private JTextField addText;
    private JTextField emailText;
    private JTextField mobileText;
    private JButton updateAddressButton;
    private JButton updateMobile;
    private JButton updateEmail;
    private JPasswordField newPswField;
    private JPasswordField oldPswField;
    private JButton updatePasswordBtn;
    private JLabel deptLabel;
    private JLabel idLabel;
    private JLabel joinDateLabel;
    private JLabel salaryLabel;
    private int id;
    private String psw;
    private String fName;
    private String lName;
    private String add;
    private String ph;
    private String email;
    private Date date;
    private int salary;
    private String dept;


    private Blob aBlob;
    private String s;
    private static int eid;

    public EmpInfo() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


//User Details......
        if(eid!=0) {
            info();
        }

//Header Label......
        headerLabel.setText("Hello "+fName);


//Brows Image
        myAccBtn.addActionListener(e -> {

            OKButton.setVisible(true);
            cancelButton.setVisible(true);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
            fileChooser.addChoosableFileFilter(filter);
            int result = fileChooser.showSaveDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                myAccBtn.setIcon(ResizeImage(path));
                s = path;
            }
            else if(result == JFileChooser.CANCEL_OPTION){
                System.out.println("No Data");
            }
        });

//Cancel Button....
        cancelButton.addActionListener(e -> {
            OKButton.setVisible(false);
            cancelButton.setVisible(false);

            //Refresh.....
            info();
        });

//OK button.....
        OKButton.addActionListener(e -> {
            Connect con = new Connect();
            con.conn();
            try{
                PreparedStatement st = con.c.prepareStatement("update employee set e_img=? where e_id=?");

                InputStream is = new FileInputStream(new File(s));
                st.setBlob(1,is);
                st.setInt(2,eid);

                st.executeUpdate();

                OKButton.setVisible(false);
                cancelButton.setVisible(false);

                //Refresh.....
                info();

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });

        //Updates.......
        updateAddressButton.addActionListener(e -> {
            Connect con = new Connect();
            con.conn();
            try{
                PreparedStatement st = con.c.prepareStatement("update employee set e_add=? where e_id=?");

                st.setString(1,addText.getText());
                st.setInt(2,eid);

                st.executeUpdate();
                JOptionPane.showMessageDialog(null,"Address Updated");

                //Refresh.....
                info();

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });
        updateMobile.addActionListener(e -> {
            Connect con = new Connect();
            con.conn();
            try{

                PreparedStatement st1 = con.c.prepareStatement("select * from employee where e_ph=?");

                st1.setString(1,mobileText.getText());

                ResultSet rs1 = st1.executeQuery();

                if (rs1.next()) {
                    JOptionPane.showMessageDialog(null, "Phone No. already exists.");

                }
                else {


                    PreparedStatement st = con.c.prepareStatement("update employee set e_ph=? where e_id=?");

                    st.setString(1, mobileText.getText());
                    st.setInt(2, eid);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Mobile No. Updated");
                }

                //Refresh.....
                info();

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });
        updateEmail.addActionListener(e -> {
            Connect con = new Connect();
            con.conn();
            try{
                PreparedStatement st = con.c.prepareStatement("update employee set e_email=? where e_id=?");

                st.setString(1,emailText.getText());
                st.setInt(2,eid);

                st.executeUpdate();
                JOptionPane.showMessageDialog(null,"Email Updated");

                //Refresh.....
                info();

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });
        updatePasswordBtn.addActionListener(e -> {
            if(new Hashing().hashing(oldPswField.getText()).equals(psw)){

                Connect con = new Connect();
                con.conn();
                try{
                    PreparedStatement st = con.c.prepareStatement("update employee set e_psw=? where e_id=?");

                    st.setString(1,new Hashing().hashing(newPswField.getText()));
                    st.setInt(2,eid);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Password Changed");

                    //Refresh.....
                    info();

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
            else{
                JOptionPane.showMessageDialog(null,"Old Password did not matched");
            }
        });

//Log Out....
        logOutButton.addActionListener(e -> {
            empInfoFrame.setVisible(false);
            new LogIn().logIn();
        });

//Back......
        backButton.addActionListener(e -> {
            empInfoFrame.setVisible(false);
            new EmployeeControl().employeeControl(eid);
        });
    }

    //Method To Resize The ImageIcon
    public ImageIcon ResizeImage(String imgPath){
        ImageIcon MyImage = new ImageIcon(imgPath);
        //Image img = MyImage.getImage();
        //Image newImage = img.getScaledInstance(myAccBtn.getWidth(), myAccBtn.getHeight(),Image.SCALE_SMOOTH);
        //ImageIcon image = new ImageIcon(newImage);
        return MyImage;
    }

    //Get Info......
    void info(){

        //Connection
        Connect con = new Connect();
        con.conn();

        //Refresh.....
        addText.setText(null);
        mobileText.setText(null);
        emailText.setText(null);
        newPswField.setText(null);
        oldPswField.setText(null);

        try {
            PreparedStatement st = con.c.prepareStatement("select * from employee where e_id = ?");
            st.setInt(1, eid);

            ResultSet rs3 = st.executeQuery();
            rs3.next();

            id = rs3.getInt("e_id");
            psw = rs3.getString("e_psw");
            fName = rs3.getString("e_fname");
            lName = rs3.getString("e_lname");
            add = rs3.getString("e_add");
            ph = rs3.getString("e_ph");
            email = rs3.getString("e_email");
            date = rs3.getDate("e_join_date");
            salary = rs3.getInt("e_salary");
            dept = rs3.getString("e_dept");
            aBlob = rs3.getBlob("e_img");

            //User Image....
            byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
            InputStream is = new ByteArrayInputStream(imageByte);
            BufferedImage img = ImageIO.read(is);
            Image image = img;
            ImageIcon icon = new ImageIcon(image);

            myAccBtn.setIcon(icon);

            //Labels....
            namelabel.setText(fName+" "+lName);
            addLabel.setText(add);
            mobLabel.setText(ph);
            emailLabel.setText(email);
            idLabel.setText(String.valueOf(id));
            salaryLabel.setText(String.valueOf(salary));
            deptLabel.setText(dept);
            joinDateLabel.setText(String.valueOf(date));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    void empInfo(int empId) {
        eid=empId;
        empInfoFrame = new JFrame("Emp Info");
        empInfoFrame.setContentPane(new EmpInfo().empInfoPanel);
        empInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        empInfoFrame.pack();
        empInfoFrame.setVisible(true);
    }
}
