package my.store;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LogIn{
    public static JFrame logInFrame;
    private JPanel logInPanel;
    private JTextField logInId;
    private JPasswordField logInPsw;
    private JButton logInButton;
    private JButton signUpButton;
    private JComboBox comboBox1;

    public LogIn() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    Connect con = new Connect();
                    con.conn();
                    String cb = (String)comboBox1.getSelectedItem();
                    //int ID = Integer.parseInt(logInId.getText());
                    //String PSW=logInPsw.getText();

                    //Manager Login
                    if (cb=="Manager") {
                        PreparedStatement st = con.c.prepareStatement("select * from manager where m_ph=? and m_psw=?");

                        st.setString(1,logInId.getText());
                        st.setString(2,new Hashing().hashing(logInPsw.getText()));

                        ResultSet rs = st.executeQuery();

                        if (rs.next()) {
                            logInFrame.setVisible(false);
                            new ManagerControl().managerControl(rs.getInt("m_id"));

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Login");
                        }
                    }

                    //Employee Login
                    if (cb=="Employee"){
                        PreparedStatement st = con.c.prepareStatement("select * from employee where e_ph=? and e_psw=?");

                        st.setString(1,logInId.getText());
                        st.setString(2,new Hashing().hashing(logInPsw.getText()));

                        ResultSet rs = st.executeQuery();

                        if (rs.next()) {
                            logInFrame.setVisible(false);
                            new EmployeeControl().employeeControl(rs.getInt("e_id"));

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Login");
                        }
                    }

                    //User Login
                    if (cb=="User"){
                        PreparedStatement st = con.c.prepareStatement("select * from user where u_ph=? and u_psw=?");

                        st.setString(1,logInId.getText());
                        st.setString(2,new Hashing().hashing(logInPsw.getText()));

                        ResultSet rs = st.executeQuery();

                        if (rs.next()) {
                            logInFrame.setVisible(false);
                            new UserHomePage().user(rs.getInt("u_id"));

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Login");
                        }
                    }



                }
                catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInFrame.setVisible(false);
                new SignUp().signUp();
            }
        });
    }

    void logIn() {
        logInFrame = new JFrame("LogIn");
        logInFrame.setContentPane(new LogIn().logInPanel);
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.pack();
        logInFrame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        signUpButton=new JButton();
        logInButton=new JButton();
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
