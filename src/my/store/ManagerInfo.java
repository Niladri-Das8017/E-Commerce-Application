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

public class ManagerInfo {

    public static JFrame managerInfoFrame;
    private JPanel managerInfoPanel;
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


    private Blob aBlob;
    private String s;
    private static int mid;

    public ManagerInfo() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


//User Details......
        if(mid!=0) {
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
                PreparedStatement st = con.c.prepareStatement("update manager set m_img=? where m_id=?");

                InputStream is = new FileInputStream(new File(s));
                st.setBlob(1,is);
                st.setInt(2,mid);

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
                PreparedStatement st = con.c.prepareStatement("update manager set m_add=? where m_id=?");

                st.setString(1,addText.getText());
                st.setInt(2,mid);

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

                PreparedStatement st1 = con.c.prepareStatement("select * from manager where m_ph=?");

                st1.setString(1,mobileText.getText());

                ResultSet rs1 = st1.executeQuery();

                if (rs1.next()) {
                    JOptionPane.showMessageDialog(null, "Phone No. already exists.");

                }
                else {


                    PreparedStatement st = con.c.prepareStatement("update manager set m_ph=? where m_id=?");

                    st.setString(1, mobileText.getText());
                    st.setInt(2, mid);

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
                PreparedStatement st = con.c.prepareStatement("update manager set m_email=? where m_id=?");

                st.setString(1,emailText.getText());
                st.setInt(2,mid);

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
                    PreparedStatement st = con.c.prepareStatement("update manager set m_psw=? where m_id=?");

                    st.setString(1,new Hashing().hashing(newPswField.getText()));
                    st.setInt(2,mid);

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
            managerInfoFrame.setVisible(false);
            new LogIn().logIn();
        });

//Back......
        backButton.addActionListener(e -> {
            managerInfoFrame.setVisible(false);
            new ManagerControl().managerControl(mid);
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
            PreparedStatement st = con.c.prepareStatement("select * from manager where m_id = ?");
            st.setInt(1, mid);

            ResultSet rs3 = st.executeQuery();
            rs3.next();

            id = rs3.getInt("m_id");
            psw = rs3.getString("m_psw");
            fName = rs3.getString("m_fname");
            lName = rs3.getString("m_lname");
            add = rs3.getString("m_add");
            ph = rs3.getString("m_ph");
            email = rs3.getString("m_email");
            date = rs3.getDate("m_join_date");
            salary = rs3.getInt("m_salary");
            aBlob = rs3.getBlob("m_img");

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
            joinDateLabel.setText(String.valueOf(date));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    void managerInfo(int managerId) {
        mid=managerId;
        managerInfoFrame = new JFrame("Manager Info");
        managerInfoFrame.setContentPane(new ManagerInfo().managerInfoPanel);
        managerInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerInfoFrame.pack();
        managerInfoFrame.setVisible(true);
    }
}
