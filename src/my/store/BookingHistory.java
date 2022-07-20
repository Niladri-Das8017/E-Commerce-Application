package my.store;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingHistory implements ActionListener{
    public static JFrame bookingHistoryFrame;
    public static int uid;
    private int row;
    private JScrollPane scPane=new JScrollPane();
    private JPanel bookingPanel=new JPanel();
    private JPanel footerPanel=new JPanel();
    private JPanel panel1=new JPanel();
    private JButton closeBtn=new JButton();


    public BookingHistory() {

//Connection
        Connect con3 = new Connect();
        con3.conn();


//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));




//bookingPanel

        String title="Booking History";
        Border borderTitle = BorderFactory.createTitledBorder(title);
        bookingPanel.setBorder(borderTitle);
        ((TitledBorder) bookingPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);
        ((TitledBorder) bookingPanel.getBorder()).setTitleFont(new Font("Droid Sans Mono", Font.BOLD+Font.ITALIC, 18));
        ((TitledBorder) bookingPanel.getBorder()).setTitleColor(new Color(237,235,233));
        bookingPanel.setBackground(new Color(0,0,0));
        bookingPanel.setLayout(new BorderLayout(20,10));


//Panel1
        panel1.setBackground(new Color(0,0,0));
        panel1.setForeground(new Color(237,235,233));
        Border pTitle1 = BorderFactory.createTitledBorder("Order Details");
        panel1.setBorder(pTitle1);
        ((TitledBorder) panel1.getBorder()).setTitleColor(new Color(237,235,233));




//Footer Panel
        footerPanel.setBackground(new Color(0,0,0));
        footerPanel.setForeground(new Color(237,235,233));
        footerPanel.setLayout(new BorderLayout());

//Scroll Pane
        scPane.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scPane.getVerticalScrollBar().setUnitIncrement(2);
        Dimension d=new Dimension(800,600);
        scPane.setPreferredSize(d);
        scPane.setBackground(new Color(0,0,0));
        scPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        bookingPanel.add(scPane,"Center");

        scPane.getVerticalScrollBar().setBackground(Color.BLACK);
        scPane.getHorizontalScrollBar().setBackground(Color.BLACK);

        scPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });
        scPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,191,161);
                this.thumbDarkShadowColor = new Color(4, 33, 12);
            }
        });


        if (uid!=0) {
            try {

                JLabel item = new JLabel("Item", JLabel.CENTER);
                JLabel iName = new JLabel("Name", JLabel.CENTER);
                JLabel iDept = new JLabel("Department", JLabel.CENTER);
                JLabel iCat = new JLabel("Category", JLabel.CENTER);
                JLabel iAmount = new JLabel("No. Of Products", JLabel.CENTER);
                JLabel iPrice = new JLabel("Total Price", JLabel.CENTER);
                JLabel iDate = new JLabel("Purchase Date", JLabel.CENTER);

                item.setFont(new Font("Arial", Font.BOLD, 16));
                iName.setFont(new Font("Arial", Font.BOLD, 16));
                iDept.setFont(new Font("Arial", Font.BOLD, 16));
                iCat.setFont(new Font("Arial", Font.BOLD, 16));
                iAmount.setFont(new Font("Arial", Font.BOLD, 16));
                iPrice.setFont(new Font("Arial", Font.BOLD, 16));
                iDate.setFont(new Font("Arial", Font.BOLD, 16));

                item.setForeground(Color.cyan);
                iName.setForeground(Color.cyan);
                iDept.setForeground(Color.cyan);
                iCat.setForeground(Color.cyan);
                iAmount.setForeground(Color.cyan);
                iPrice.setForeground(Color.cyan);
                iDate.setForeground(Color.cyan);



                PreparedStatement st6 = con3.c.prepareStatement("select * from booking1 where u_id=?");
                st6.setInt(1, uid);


                ResultSet res;
                res = st6.executeQuery();

                if (res.next()) {
//Panel1 Grid Layout Settings...
                    res.last();
                    row = res.getRow();
                    row++;
                    panel1.setLayout(new GridLayout(row, 7,5,10));
//Add Headers
                    panel1.add(item);
                    panel1.add(iName);
                    panel1.add(iDept);
                    panel1.add(iCat);
                    panel1.add(iAmount);
                    panel1.add(iPrice);
                    panel1.add(iDate);

                    do {

                        int iid = res.getInt(2);
                        // JOptionPane.showMessageDialog(null, iid);
                        int amount = res.getInt(4);
                        int price = res.getInt(5);
                        Date date = res.getDate(6);


                        PreparedStatement st7 = con3.c.prepareStatement("select * from stock where item_id=?");
                        st7.setInt(1, iid);

                        ResultSet res1 = st7.executeQuery();
                        res1.next();

                        String name = res1.getString(2);
                        String dept = res1.getString(4);
                        String cat = res1.getString(5);
                        Blob aBlob = res1.getBlob(11);

                        //Icon
                        byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                        InputStream is = new ByteArrayInputStream(imageByte);
                        BufferedImage img = ImageIO.read(is);
                        Image image = img;
                        ImageIcon icon = new ImageIcon(image);


                        //Labels
                        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
                        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                        JLabel deptLabel = new JLabel(dept, JLabel.CENTER);
                        JLabel catLabel = new JLabel(cat, JLabel.CENTER);
                        JLabel amountLabel = new JLabel(String.valueOf(amount), JLabel.CENTER);
                        JLabel priceLabel = new JLabel(String.valueOf(price), JLabel.CENTER);
                        JLabel dateLabel = new JLabel(String.valueOf(date), JLabel.CENTER);

                        //Label Edit....
                        nameLabel.setForeground(Color.ORANGE);
                        deptLabel.setForeground(Color.ORANGE);
                        catLabel.setForeground(Color.ORANGE);
                        amountLabel.setForeground(Color.ORANGE);
                        priceLabel.setForeground(Color.ORANGE);
                        dateLabel.setForeground(Color.ORANGE);

                        //Add...
                        panel1.add(iconLabel);
                        panel1.add(nameLabel);
                        panel1.add(deptLabel);
                        panel1.add(catLabel);
                        panel1.add(amountLabel);
                        panel1.add(priceLabel);
                        panel1.add(dateLabel);


                        scPane.getViewport().add(panel1);

                    } while (res.previous());
                }
                else {
                    JOptionPane.showMessageDialog(null,"No Orders Yet.");
                    scPane.getViewport().add(panel1);
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


//Close Button
        closeBtn = new JButton("Cancel");
        bookingPanel.add(footerPanel,BorderLayout.SOUTH);
        footerPanel.add(closeBtn,BorderLayout.EAST);
        closeBtn.setBackground(new Color(220,53,69));
        closeBtn.setForeground(new Color(237,235,233));
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(this);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }


    void bookingHistory(int userId) {
        uid=userId;
        bookingHistoryFrame = new JFrame("Your Orders");
        bookingHistoryFrame.setContentPane(new BookingHistory().bookingPanel);
        bookingHistoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookingHistoryFrame.pack();
        bookingHistoryFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bookingHistoryFrame.setVisible(false);
    }
}
