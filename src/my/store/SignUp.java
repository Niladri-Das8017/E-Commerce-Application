package my.store;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUp {
    public static JFrame signUpFrame;
    public JPanel signUpPanel;
    private JTextField fName;
    private JTextField lName;
    private JTextField ph;
    private JTextField add;
    private JTextField email;
    private JPasswordField psword;
    private JPasswordField conPassword;
    private JPanel formPanel;
    private JButton register;
    private JButton backToLogIn;
    private JButton browsButton;
    private JLabel imgLabel;
    private JButton helpButton;
    private String s;

    public SignUp() {
//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect con2 = new Connect();
                    con2.conn();
                    String fname = fName.getText();
                    String lname = lName.getText();
                    String phone = ph.getText();
                    String address = add.getText();
                    String emailAdd = email.getText();
                    String psw = psword.getText();
                    String cpsw = conPassword.getText();

                    if (psw.equals(cpsw)){

                        PreparedStatement st1 = con2.c.prepareStatement("select * from user where u_ph=?");

                        st1.setString(1,phone);

                        ResultSet rs1 = st1.executeQuery();

                        if (rs1.next()) {
                            JOptionPane.showMessageDialog(null, "Phone No. already exists.");

                        }
                        else {

                        //Checking for Valid input.......
                            if (fname.matches("[a-zA-Z]+") && fname.length() > 2 && lname.matches("[a-zA-Z]+") && lname.length() > 2 &&
                                    phone.matches("[0-9]+") && phone.length()==10 &&
                                    emailAdd.contains("@") && emailAdd.contains(".com") && address.length()>5 &&
                                    psw.length()!=0 && cpsw.length()!=0 && s.length()!=0) {

                                PreparedStatement st2 = con2.c.prepareStatement("insert into user(u_fname, u_lname, u_ph, u_add, u_email, u_psw,u_join_date,u_img) values(?,?,?,?,?,?,?,?)");

                                InputStream is = new FileInputStream(new File(s));

                                st2.setString(1, fname);
                                st2.setString(2, lname);
                                st2.setString(3, phone);
                                st2.setString(4, address);
                                st2.setString(5, emailAdd);
                                st2.setString(6, new Hashing().hashing(psw));
                                Date date = new Date(new java.util.Date().getTime());
                                st2.setDate(7, date);
                                st2.setBlob(8, is);

                                st2.executeUpdate();

                                PreparedStatement st3 = con2.c.prepareStatement("select u_id from user where u_ph=?");
                                st3.setString(1, phone);

                                ResultSet rs2 = st3.executeQuery();
                                rs2.next();
                                int uid = rs2.getInt("u_id");


                                //JOptionPane.showMessageDialog(null, "welcome "+fname+" "+lname+". Your user id is: "+uid);

                                signUpFrame.setVisible(false);
                                new Loading().loading(false, fname, lname, uid);
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"Invalid input. Please check the instructions");
                            }

                        }

                    }
                    else
                        JOptionPane.showMessageDialog(null,"Password Confirmation Faild");

                }
                catch (Exception e3){
                    e3.printStackTrace();
                }

            }
        });
        backToLogIn.addActionListener(e -> {
            signUpFrame.setVisible(false);
            new LogIn().logIn();

        });
        browsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Docs\\Help.txt");
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //Method To Resize The ImageIcon
    public ImageIcon ResizeImage(String imgPath){
        ImageIcon MyImage = new ImageIcon(imgPath);
        //Image img = MyImage.getImage();
        //Image newImage = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
        //ImageIcon image = new ImageIcon(newImage);
        return MyImage;
    }

    void signUp(){
        signUpFrame = new JFrame("SignUp");
        signUpFrame.setContentPane(new SignUp().signUpPanel);
        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpFrame.pack();
        signUpFrame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        browsButton = new JButton();
        backToLogIn = new JButton();
        register = new JButton();

        browsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backToLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
