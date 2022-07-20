package my.store;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Booking {
    public static JFrame bookingFrame;
    public static int uid;
    public static int iid;
    private JPanel bookingPanel;
    private JButton cancelButton;
    private JSpinner spinner1;
    private JButton orderNowButton;
    private JLabel labelName;
    private JLabel labelDept;
    private JLabel labelCat;

    private String name;
    private int price;
    private String dept;
    private String cat;
    private static int stock;
    private int off;

    public Booking() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


        try {
            Connect con3 = new Connect();
            con3.conn();

            PreparedStatement st4 = con3.c.prepareStatement("select * from stock where item_id=?");

            st4.setInt(1,iid);

            ResultSet rs3 = st4.executeQuery();

            if (rs3.next()) {
                name = rs3.getString("item_name");
                price = rs3.getInt("item_price");
                dept = rs3.getString("item_department");
                cat = rs3.getString("item_category");
                stock = rs3.getInt("item_stock");
                off = rs3.getInt("item_offer");
            }
            else{

            }


        }
        catch (Exception e5){
            e5.printStackTrace();
        }

//Order Details
        labelName.setText(name);
        labelDept.setText(dept);
        labelCat.setText(cat);


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                bookingFrame.setVisible(false);

                new UserHomePage().user(uid);

            }
        });
        orderNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = (Integer) spinner1.getValue();

                double result =(double)price*(1- (double)off/100.0);
                int totalPrice = amount*(int)result;  //Offer Calculation


                if(amount<=0){
                    JOptionPane.showMessageDialog(null,"Amount must be at least 1");
                }
                else {
                    bookingFrame.setVisible(false);
                    new Payment().payment(uid, iid, amount, stock, totalPrice);
                }
            }
        });
    }

    void boooking( int id,int itid){

        uid=id;
        iid=itid;
        bookingFrame = new JFrame("Booking");
        bookingFrame.setContentPane(new Booking().bookingPanel);
        bookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookingFrame.pack();
        bookingFrame.setVisible(true);






    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        orderNowButton=new JButton();
        cancelButton=new JButton();

        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
