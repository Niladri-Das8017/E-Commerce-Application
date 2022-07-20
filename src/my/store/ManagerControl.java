package my.store;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerControl {
    public static JFrame managerControlFrame;
    public static int mid;
    private int empSearchId;
    private int userSearchId;
    private String eFname;
    private String eLname;
    private String uFname;
    private String uLname;
    private String ePh;
    private String uPh;
    private JPanel managerControlPanel;
    private JButton empList;
    private JButton userList;
    private JTextField emp;
    private JButton empSearch;
    private JTextField user;
    private JButton userSearch;
    private JButton stockList;
    private JButton logOut;
    private JButton empCloseButton;
    private JButton userCloseButton;
    private JPanel searchPanel;
    private JPanel empPanel;
    private JPanel userPanel;
    private JLabel eidLabel;
    private JLabel eFNameLabel;
    private JLabel eLNameLabel;
    private JLabel eAddLabel;
    private JLabel ePhLabel;
    private JLabel eEmailLabel;
    private JLabel eJoinLabel;
    private JLabel eSalLabel;
    private JScrollPane empScPane;
    private JLabel uAddLabel;
    private JLabel uJoinLabel;
    private JLabel uLNameLabel;
    private JLabel uEmailLabel;
    private JLabel uFNameLabel;
    private JLabel uIdLabel;
    private JLabel uPhLabel;
    private JButton deleteEmpButton;
    private JButton deleteUserButton;
    private JButton mAccBtn;
    private JScrollPane userScPane;
    private JButton ordersButton;
    private JButton bookingListButton;
    private JLabel uIconLabel;
    private JLabel eIconLabel;
    private JButton placeOrderButton;
    private JLabel head;
    private Blob mBlob;

    public ManagerControl() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

//Connection
        Connect con3 = new Connect();
        con3.conn();
//Initialization
        empPanel.setVisible(false);
        empScPane.setVisible(false);
        userScPane.setVisible(false);
        userPanel.setVisible(false);
        ordersButton.setVisible(false);
        empCloseButton.setVisible(false);
        userCloseButton.setVisible(false);
        deleteEmpButton.setVisible(false);
        deleteUserButton.setVisible(false);


//myAccountBtn

        if(mid!=0) {
            //User Details
            try {
                PreparedStatement stmt = con3.c.prepareStatement("select * from manager where m_id=?");
                stmt.setInt(1, mid);

                ResultSet rst = stmt.executeQuery();
                rst.next();

                head.setText("Hello "+rst.getString("m_fname"));

                mBlob = rst.getBlob("m_img");
                //Employee Image
                byte[] imByte = mBlob.getBytes(1, (int) mBlob.length());
                InputStream ist = new ByteArrayInputStream(imByte);
                BufferedImage uImg = ImageIO.read(ist);
                Image uImage = uImg;
                ImageIcon uIcon = new ImageIcon(uImage);
                mAccBtn.setIcon(uIcon);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

//Employee Search
        empSearch.addActionListener(e -> {
            try {

                PreparedStatement st4;
                if(emp.getText().length()==10){

                    st4 = con3.c.prepareStatement("select * from employee where e_ph=?");
                    st4.setString(1,emp.getText());

                }
                else {
                    st4 = con3.c.prepareStatement("select * from employee where e_id=?");

                    empSearchId = Integer.parseInt(emp.getText());
                    st4.setInt(1, empSearchId);
                }

                ResultSet rs3 = st4.executeQuery();

                if (rs3.next()){
                    int eId = rs3.getInt("e_id");
                    empSearchId = eId;
                    eFname = rs3.getString("e_fname");
                    eLname = rs3.getString("e_lname");
                    String eAdd = rs3.getString("e_add");
                    ePh = rs3.getString("e_ph");
                    String eEmail = rs3.getString("e_email");
                    String eJoinDate = rs3.getString("e_join_date");
                    String eSalary = rs3.getString("e_salary");
                    Blob eBlob = rs3.getBlob("e_img");
                    //User Image
                    byte[] imByte = eBlob.getBytes(1, (int) eBlob.length());
                    InputStream ist = new ByteArrayInputStream(imByte);
                    BufferedImage eImg = ImageIO.read(ist);
                    Image eImage = eImg;
                    ImageIcon eIcon = new ImageIcon(eImage);

                   // JOptionPane.showMessageDialog(null,"Emp Id: " + eId + "\n" + "First Name: " + eFname + "\n" + "Last Name: " + eLname +
                     //      "\n" + "Address: " + eAdd + "\n" + "Phone No.: " + ePh + "\n" + "Email: " + eEmail + "\n" + "Join Date: " + eJoinDate + "\n" + "Salary: " + eSalary);

                    empPanel.setVisible(true);
                    empScPane.setVisible(true);
                    empCloseButton.setVisible(true);
                    deleteEmpButton.setVisible(true);

                    eIconLabel.setIcon(eIcon);
                    eidLabel.setText(String.valueOf(eId));
                    eFNameLabel.setText(eFname);
                    eLNameLabel.setText(eLname);
                    eAddLabel.setText(eAdd);
                    ePhLabel.setText(ePh);
                    eEmailLabel.setText(eEmail);
                    eJoinLabel.setText(eJoinDate);
                    eSalLabel.setText(eSalary);


                }
                else {
                    JOptionPane.showMessageDialog(null,"No such employee");
                }

            }
            catch (Exception e5){
                e5.printStackTrace();
            }
        });

//User Search
        userSearch.addActionListener(e -> {
            try {
                PreparedStatement st5;

                if (user.getText().length()==10){

                    st5 = con3.c.prepareStatement("select * from user where u_ph=?");
                    st5.setString(1,user.getText());

                }

                else {
                    st5 = con3.c.prepareStatement("select * from user where u_id=?");

                    userSearchId = Integer.parseInt(user.getText());
                    st5.setInt(1, userSearchId);

                }

                ResultSet rs4 = st5.executeQuery();

                if (rs4.next()){
                    int uId = rs4.getInt("u_id");
                    userSearchId=uId;

                    uFname = rs4.getString("u_fname");
                    uLname = rs4.getString("u_lname");
                    String uAdd = rs4.getString("u_add");
                    uPh = rs4.getString("u_ph");
                    String uEmail = rs4.getString("u_email");
                    String uJoinDate = rs4.getString("u_join_date");
                    Blob uBlob = rs4.getBlob("u_img");
            //User Image
                    byte[] imByte = uBlob.getBytes(1, (int) uBlob.length());
                    InputStream ist = new ByteArrayInputStream(imByte);
                    BufferedImage uImg = ImageIO.read(ist);
                    Image uImage = uImg;
                    ImageIcon uIcon = new ImageIcon(uImage);

                    //JOptionPane.showMessageDialog(null,"User Id: " + uId + "\n" + "First Name: " + uFname + "\n" + "Last Name: " + uLname +
                     //       "\n" + "Address: " + uAdd + "\n" + "Phone No.: " + uPh + "\n" + "Email: " + uEmail + "\n" + "Join Date: " + uJoinDate);

                    userPanel.setVisible(true);

                    userScPane.setVisible(true);
                    ordersButton.setVisible(true);
                    userCloseButton.setVisible(true);
                    deleteUserButton.setVisible(true);

                    uIdLabel.setText(String.valueOf(uId));
                    uFNameLabel.setText(uFname);
                    uLNameLabel.setText(uLname);
                    uAddLabel.setText(uAdd);
                    uPhLabel.setText(uPh);
                    uEmailLabel.setText(uEmail);
                    uJoinLabel.setText(uJoinDate);
                    uIconLabel.setIcon(uIcon);
                }
                else {
                    JOptionPane.showMessageDialog(null,"No such user");
                }

            }
            catch (Exception e6){
                e6.printStackTrace();
            }
        });

//Employee List
        empList.addActionListener(e -> new EmployeeList().employeeList());

//User List
        userList.addActionListener(e -> new UserList().userList());

//Stock List
        stockList.addActionListener(e -> new ItemStock().itemStock());

//Log Out
        logOut.addActionListener(e -> {
            managerControlFrame.setVisible(false);
            new LogIn().logIn();
        });

//Close Button for both type of Search
        empCloseButton.addActionListener(e -> {
            empPanel.setVisible(false);
            empScPane.setVisible(false);
            empCloseButton.setVisible(false);
            deleteEmpButton.setVisible(false);
        });
        userCloseButton.addActionListener(e -> {
            userPanel.setVisible(false);
            userScPane.setVisible(false);
            userCloseButton.setVisible(false);
            ordersButton.setVisible(false);
            deleteUserButton.setVisible(false);
        });

//Delete Button For Both
        deleteEmpButton.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(null,"Sure? You want to delete this Account?", "Delete Account",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){
                Connect con = new Connect();
                con.conn();
                try{
                    PreparedStatement st = con.c.prepareStatement("update employee set e_ph=? where e_id=?");

                    st.setString(1,"C "+ePh);
                    st.setInt(2,empSearchId);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Account Disabled. The employee Cannot Log In again ");
                    empPanel.setVisible(false);
                    empScPane.setVisible(false);
                    empCloseButton.setVisible(false);
                    deleteEmpButton.setVisible(false);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }else if (result == JOptionPane.NO_OPTION){
                managerControlFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }


        });
        deleteUserButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null,"Sure? You want to delete this Account?", "Delete Account",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){

                Connect con = new Connect();
                con.conn();
                try{
                    PreparedStatement st = con.c.prepareStatement("update user set u_ph=? where u_id=?");

                    st.setString(1,"C "+uPh);
                    st.setInt(2,userSearchId);

                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Account Disabled. The user Cannot Log In again ");
                    userPanel.setVisible(false);
                    userScPane.setVisible(false);
                    userCloseButton.setVisible(false);
                    deleteUserButton.setVisible(false);

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }else if (result == JOptionPane.NO_OPTION){
                managerControlFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

    //mAccBtn........
        mAccBtn.addActionListener(e -> {

            managerControlFrame.setVisible(false);
            new ManagerInfo().managerInfo(mid);

        });
        ordersButton.addActionListener(e -> new BookingList().bookingList(true,userSearchId));
        bookingListButton.addActionListener(e -> new BookingList().bookingList(false,-1));
        placeOrderButton.addActionListener(e -> new PlaceOrder().placeOrder());
    }

    void managerControl(int managerId) {
        mid = managerId;
        managerControlFrame = new JFrame("Manager Control");
        managerControlFrame.setContentPane(new ManagerControl().managerControlPanel);
        managerControlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerControlFrame.pack();
        managerControlFrame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        mAccBtn = new JButton();
        userSearch = new JButton();
        empSearch = new JButton();
        userScPane = new JScrollPane();
        empScPane = new JScrollPane();
        mAccBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        empSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        userScPane.getVerticalScrollBar().setBackground(Color.BLACK);
        userScPane.getHorizontalScrollBar().setBackground(Color.BLACK);

        userScPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });
        userScPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });

        empScPane.getVerticalScrollBar().setBackground(Color.BLACK);
        empScPane.getHorizontalScrollBar().setBackground(Color.BLACK);

        empScPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });
        empScPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });
    }
}
