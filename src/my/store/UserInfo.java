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

public class UserInfo {
    public static JFrame userInfoFrame;
    private JPanel userInfoPanel;
    private JButton myAccBtn;
    private JButton OKButton;
    private JButton backButton;
    private JButton logOutButton;
    private JLabel headerLabel;
    private JButton cancelButton;
    private JButton bookingHistoryButton;
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
    private JLabel idLabel;
    private int id;
    private String psw;
    private String fName;
    private String lName;
    private String add;
    private String ph;
    private String email;
    private Date date;
    private Blob aBlob;
    private String s;



    private static int uid =0;

    public UserInfo() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


//User Details......
        if(uid!=0) {
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

        //Booking History......
        bookingHistoryButton.addActionListener(e -> new BookingHistory().bookingHistory(uid));

//OK button.....
        OKButton.addActionListener(e -> {
            Connect con = new Connect();
            con.conn();
            try{
                PreparedStatement st = con.c.prepareStatement("update user set u_img=? where u_id=?");

                InputStream is = new FileInputStream(new File(s));
                st.setBlob(1,is);
                st.setInt(2,uid);

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
                if (addText.getText().length()>5) {
                    PreparedStatement st = con.c.prepareStatement("update user set u_add=? where u_id=?");

                    st.setString(1, addText.getText());
                    st.setInt(2, uid);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Address Updated");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Please Insert Valid Address");
                }

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
                if (mobileText.getText().matches("[0-9]+")&&mobileText.getText().length()==10) {

                    PreparedStatement st1 = con.c.prepareStatement("select * from user where u_ph=?");

                    st1.setString(1, mobileText.getText());

                    ResultSet rs1 = st1.executeQuery();

                    if (rs1.next()) {
                        JOptionPane.showMessageDialog(null, "Phone No. already exists.");

                    } else {


                        PreparedStatement st = con.c.prepareStatement("update user set u_ph=? where u_id=?");

                        st.setString(1, mobileText.getText());
                        st.setInt(2, uid);

                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Mobile No. Updated");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Please insert valid Mobile No");
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
                if(emailText.getText().contains("@")&&emailText.getText().contains(".com")) {
                    PreparedStatement st = con.c.prepareStatement("update user set u_email=? where u_id=?");

                    st.setString(1, emailText.getText());
                    st.setInt(2, uid);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Email Updated");

                }
                else{
                    JOptionPane.showMessageDialog(null,"Please insert a valid Email Id.");
                }

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
                    if (newPswField.getText().length()!=0) {
                        PreparedStatement st = con.c.prepareStatement("update user set u_psw=? where u_id=?");

                        st.setString(1, new Hashing().hashing(newPswField.getText()));
                        st.setInt(2, uid);

                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Password Changed");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Please insert a valid Password");
                    }

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
            userInfoFrame.setVisible(false);
            new LogIn().logIn();
        });

//Back......
        backButton.addActionListener(e -> {
            userInfoFrame.setVisible(false);
            new UserHomePage().user(uid);
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
            PreparedStatement st = con.c.prepareStatement("select * from user where u_id = ?");
            st.setInt(1, uid);

            ResultSet rs3 = st.executeQuery();
            rs3.next();

            id = rs3.getInt("u_id");
            psw = rs3.getString("u_psw");
            fName = rs3.getString("u_fname");
            lName = rs3.getString("u_lname");
            add = rs3.getString("u_add");
            ph = rs3.getString("u_ph");
            email = rs3.getString("u_email");
            date = rs3.getDate("u_join_date");
            aBlob = rs3.getBlob("u_img");

    //User Image....
            byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
            InputStream is = new ByteArrayInputStream(imageByte);
            BufferedImage img = ImageIO.read(is);
            Image image = img;
            ImageIcon icon = new ImageIcon(image);

            myAccBtn.setIcon(icon);

     //Labels....
            namelabel.setText(fName+" "+lName);
            idLabel.setText(String.valueOf(uid));
            addLabel.setText(add);
            mobLabel.setText(ph);
            emailLabel.setText(email);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    void userInfo(int userId) {
        uid=userId;
        userInfoFrame = new JFrame("User Info");
        userInfoFrame.setContentPane(new UserInfo().userInfoPanel);
        userInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInfoFrame.pack();
        userInfoFrame.setVisible(true);
    }
}
