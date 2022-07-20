package my.store;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;

public class Payment {
    public static JFrame paymentFrame;
    public static int uid;
    public static int iid;
    public static int quantity;
    public static int stock;
    public static int totalPrice;
    private JPanel paymentPanel;
    private JLabel labelQuantity;
    private JLabel labelTotalPrice;
    private JButton cancelButton;
    private JButton payButton;

    public Payment() {


//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

        labelQuantity.setText(String.valueOf(quantity));
        labelTotalPrice.setText(String.valueOf(totalPrice));
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (quantity<=stock){

                    try {
                        Connect con = new Connect();
                        con.conn();

                        PreparedStatement st = con.c.prepareStatement("UPDATE stock SET item_stock=item_stock-? where item_id=?");

                        st.setInt(1,quantity);
                        st.setInt(2,iid);

                        st.executeUpdate();

                        PreparedStatement st1 = con.c.prepareStatement("insert into booking1(item_id,u_id,b_amount,b_price,b_date) values(?,?,?,?,?)");

                     //   JOptionPane.showMessageDialog(null,"1");
                        st1.setInt(1,iid);
                        st1.setInt(2,uid);
                        st1.setInt(3,quantity);
                        st1.setInt(4,totalPrice);
                        Date date = new Date( new java.util.Date().getTime());
                        st1.setDate(5,date);

                        st1.executeUpdate();
                       // JOptionPane.showMessageDialog(null,"2");

                    //Call Loading Page Animation
                        new Loading().loading(true,null,null,uid);
                        paymentFrame.setVisible(false);


                    }
                    catch (Exception e5){
                        e5.printStackTrace();
                    }


                }
                else {
                    JOptionPane.showMessageDialog(null, "Quantity Greater Than Stock");
                    paymentFrame.setVisible(false);
                    new Booking().boooking(uid,iid);
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentFrame.setVisible(false);
                new Booking().boooking(uid,iid);
            }
        });
    }

    void payment( int id,int itid, int am,int st,int itemPrice){

        uid=id;
        iid=itid;
        quantity=am;
        stock=st;
        totalPrice=itemPrice;
        paymentFrame = new JFrame("Payment");
        paymentFrame.setContentPane(new Payment().paymentPanel);
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentFrame.pack();
        paymentFrame.setVisible(true);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        cancelButton=new JButton();
        payButton=new JButton();

        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        payButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
