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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WishList implements ActionListener {
    public static JFrame wishListFrame;
    private JPanel wishListPanel = new JPanel();
    private static int uid;
    private int row;
    private JScrollPane scPane=new JScrollPane();
    private JPanel footerPanel=new JPanel();
    private JPanel panel1=new JPanel();
    private JButton closeBtn=new JButton();

    public WishList(){


//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


//WishList Panel
        wishListPanel.setBackground(new Color(0,0,0));
        wishListPanel.setLayout(new BorderLayout(20,10));

//Panel1
        panel1.setBackground(new Color(0,0,0));
        panel1.setForeground(new Color(237,235,233));
        String title="Wish List";
        Border borderTitle = BorderFactory.createTitledBorder(title);
        wishListPanel.setBorder(borderTitle);
        ((TitledBorder) wishListPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);
        ((TitledBorder) wishListPanel.getBorder()).setTitleFont(new Font("Droid Sans Mono", Font.BOLD+Font.ITALIC, 18));
        ((TitledBorder) wishListPanel.getBorder()).setTitleColor(Color.RED);



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
        wishListPanel.add(scPane,"Center");

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

        panel1.removeAll();
        initialize();

//Close Button
        closeBtn = new JButton("Close");
        wishListPanel.add(footerPanel,BorderLayout.SOUTH);
        footerPanel.add(closeBtn,BorderLayout.EAST);
        closeBtn.setBackground(new Color(220,53,69));
        closeBtn.setForeground(new Color(237,235,233));
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(this);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


    }

    void initialize(){

    //Connection
        Connect con = new Connect();
        con.conn();

        if (uid!=0) {
            try {

                JLabel item = new JLabel("Item", JLabel.CENTER);
                JLabel iName = new JLabel("Name", JLabel.CENTER);
                JLabel iPrice = new JLabel("Price", JLabel.CENTER);
                JLabel iDept = new JLabel("Department", JLabel.CENTER);
                JLabel iCat = new JLabel("Category", JLabel.CENTER);
                JLabel iOff = new JLabel("Offer", JLabel.CENTER);

                item.setFont(new Font("Arial", Font.BOLD, 16));
                iName.setFont(new Font("Arial", Font.BOLD, 16));
                iPrice.setFont(new Font("Arial", Font.BOLD, 16));
                iDept.setFont(new Font("Arial", Font.BOLD, 16));
                iCat.setFont(new Font("Arial", Font.BOLD, 16));
                iOff.setFont(new Font("Arial", Font.BOLD, 16));


                item.setForeground(new Color(82,191,161));
                iName.setForeground(new Color(82,191,161));
                iPrice.setForeground(new Color(82,191,161));
                iDept.setForeground(new Color(82,191,161));
                iCat.setForeground(new Color(82,191,161));
                iOff.setForeground(new Color(82,191,161));



                PreparedStatement st6 = con.c.prepareStatement("select * from wish_list where u_id=?");
                st6.setInt(1, uid);


                ResultSet res;
                res = st6.executeQuery();

                if (res.next()) {
                    //Panel1 Grid Layout Settings...
                    res.last();
                    row = res.getRow();
                    row++;
                    panel1.setLayout(new GridLayout(row, 5,5,10));
                    //Add Headers
                    panel1.add(item);
                    panel1.add(iName);
                    panel1.add(iPrice);
                    panel1.add(iDept);
                    panel1.add(iCat);
                    panel1.add(iOff);


                    do {

                        int iid = res.getInt(3);

                        PreparedStatement st7 = con.c.prepareStatement("select * from stock where item_id=?");
                        st7.setInt(1, iid);

                        ResultSet res1 = st7.executeQuery();
                        res1.next();

                        String name = res1.getString(2);
                        int price = res1.getInt(3);
                        String dept = res1.getString(4);
                        String cat = res1.getString(5);
                        int off = res1.getInt(7);
                        Blob aBlob = res1.getBlob(11);

                        //Icon
                        byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                        InputStream is = new ByteArrayInputStream(imageByte);
                        BufferedImage img = ImageIO.read(is);
                        Image image = img;
                        ImageIcon icon = new ImageIcon(image);


                        //Labels
                        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                        JLabel priceLabel = new JLabel(String.valueOf(price), JLabel.CENTER);
                        JLabel deptLabel = new JLabel(dept, JLabel.CENTER);
                        JLabel catLabel = new JLabel(cat, JLabel.CENTER);
                        JLabel offerLabel = new JLabel(String.valueOf(off), JLabel.CENTER);

                        //Label Edit....
                        nameLabel.setForeground(Color.ORANGE);
                        priceLabel.setForeground(Color.ORANGE);
                        deptLabel.setForeground(Color.ORANGE);
                        catLabel.setForeground(Color.ORANGE);
                        offerLabel.setForeground(Color.ORANGE);

                        //Buy Button
                        JButton buyBtn = new JButton("Buy Now");
                        buyBtn.setIcon(icon);
                        buyBtn.setVerticalTextPosition(JButton.BOTTOM);
                        buyBtn.setHorizontalTextPosition(JButton.CENTER);
                        buyBtn.setBackground(new Color(0,0,0));
                        buyBtn.setForeground(Color.YELLOW);
                        buyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        buyBtn.setActionCommand(String.valueOf(iid));
                        buyBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int iid = Integer.parseInt(e.getActionCommand());
                                //JOptionPane.showMessageDialog(null, iid);
                                try {
                                    PreparedStatement st2 = con.c.prepareStatement("select * from stock where item_id=? and item_stock>0");

                                    st2.setInt(1, iid);
                                    ResultSet rs2 = st2.executeQuery();

                                    if (rs2.next()) {

                                        wishListFrame.setVisible(false);
                                        new Booking().boooking(uid, iid);

                                    } else
                                        JOptionPane.showMessageDialog(null, "Out Of Stock");


                                } catch (Exception ex3) {
                                    ex3.printStackTrace();
                                }

                            }
                        });

                        //Remove Button
                        JButton removeBtn = new JButton("Remove");
                        removeBtn.setBackground(Color.RED);
                        removeBtn.setForeground(Color.WHITE);
                        removeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        removeBtn.setActionCommand(String.valueOf(iid));
                        removeBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    PreparedStatement st3 = con.c.prepareStatement("delete from wish_list where item_id=? and u_id=?");

                                    st3.setInt(1, iid);
                                    st3.setInt(2,uid);
                                    st3.executeUpdate();

                                    panel1.removeAll();
                                    initialize();

                                } catch (Exception ex3) {
                                    ex3.printStackTrace();
                                }
                            }
                        });

                        //Container
                        Container container = new Container();
                        container.setLayout(new BorderLayout());
                        container.add(buyBtn,BorderLayout.CENTER);
                        container.add(removeBtn,BorderLayout.SOUTH);

                        //Add...
                        panel1.add(container);
                        panel1.add(nameLabel);
                        panel1.add(priceLabel);
                        panel1.add(deptLabel);
                        panel1.add(catLabel);
                        panel1.add(offerLabel);


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

    }


    void wishList(int userId) {
        uid=userId;
        wishListFrame = new JFrame("Wish List");
        wishListFrame.setContentPane(new WishList().wishListPanel );
        wishListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wishListFrame.pack();
        wishListFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==closeBtn) {
            wishListFrame.setVisible(false);
            new UserHomePage().user(uid);
        }
    }
}
