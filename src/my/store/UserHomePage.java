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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHomePage implements ActionListener {

    public static JFrame userHomeFrame;
    public static int uid=0;
    private JPanel userHomePanel= new JPanel();
    private JScrollPane scPane = new JScrollPane();
    private JPanel contentPanel=new JPanel();
    private JPanel headerPanel=new JPanel();
    private JPanel footerPanel=new JPanel();
    private JPanel menuPanel=new JPanel();


   // Header And Menu....
    private JButton myAccountBtn;
    private JButton logOutBtn;
    private JButton categoryBtn;
    private JButton allItemsBtn;
    private JButton topOffersBtn;
    private JButton groceryBtn;
    private JButton garmentsBtn;
    private JButton yourOrdersBtn;
    private JButton wishListBtn;
    private boolean flag = true;




    private int id;
    private String name;
    private int price;
    private String dept;
    private String cat;
    public static int stock;
    private int off;
    private Blob aBlob;
    private Blob uBlob;
    private  int limit;


    public UserHomePage() {

//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

//Connection Establish
        Connect con = new Connect();
        con.conn();

//userHomePanel
        userHomePanel.setBackground(new Color(0,0,0));
        userHomePanel.setLayout(new BorderLayout(20,10));


//Header Panel
        headerPanel.setBackground(new Color(0,0,0));
        headerPanel.setLayout(new BorderLayout());
        userHomePanel.add(headerPanel,"North");

        JLabel titleLabel = new JLabel("We Are Near To You",JLabel.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,16));
        titleLabel.setForeground(Color.ORANGE);
        headerPanel.add(titleLabel,BorderLayout.CENTER);

//Menu Panel
        menuPanel.setBackground(new Color(0,0,0));
        menuPanel.setLayout(new GridLayout(10,1,5,5));
        userHomePanel.add(menuPanel, BorderLayout.WEST);

//Category Button
        categoryBtn = new JButton();
        categoryBtn.setText("Shop By Category");
        categoryBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-expand-arrow-20.png"));
        categoryBtn.setBackground( new Color(0,0,0));
        categoryBtn.setForeground( new Color(255,51,51));
        categoryBtn.setBorderPainted(false);
        categoryBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                categoryBtn.setBackground(new Color(255,51,51));
                categoryBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                categoryBtn.setBackground(new Color(0,0,0));
                categoryBtn.setForeground(new Color(255,51,51));
            }
        });
        categoryBtn.addActionListener(this);


//All Items Button
        allItemsBtn = new JButton();
        allItemsBtn.setText("All Items");
        allItemsBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-shop-20.png"));
        allItemsBtn.setBackground(new Color(0,0,0));
        allItemsBtn.setForeground(new Color(82,191,161));
        allItemsBtn.setBorderPainted(false);
        allItemsBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                allItemsBtn.setBackground(new Color(82,191,161));
                allItemsBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                allItemsBtn.setBackground(new Color(0,0,0));
                allItemsBtn.setForeground(new Color(82,191,161));
            }
        });
        allItemsBtn.addActionListener(this);

//Top Offers Button
        topOffersBtn = new JButton();
        topOffersBtn.setText("Top Offers");
        topOffersBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-star-20.png"));
        topOffersBtn.setBackground(new Color(0,0,0));
        topOffersBtn.setForeground(new Color(82,191,161));
        topOffersBtn.setBorderPainted(false);
        topOffersBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                topOffersBtn.setBackground(new Color(82,191,161));
                topOffersBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                topOffersBtn.setBackground(new Color(0,0,0));
                topOffersBtn.setForeground(new Color(82,191,161));
            }
        });
        topOffersBtn.addActionListener(this);

//Grocery Button
        groceryBtn = new JButton();
        groceryBtn.setText("Grocery");
        groceryBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-grocery-bag-20.png"));
        groceryBtn.setBackground(new Color(0,0,0));
        groceryBtn.setForeground(new Color(82,191,161));
        groceryBtn.setBorderPainted(false);
        groceryBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                groceryBtn.setBackground(new Color(82,191,161));
                groceryBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                groceryBtn.setBackground(new Color(0,0,0));
                groceryBtn.setForeground(new Color(82,191,161));
            }
        });
        groceryBtn.addActionListener(this);

//Garments List Button
        garmentsBtn = new JButton();
        garmentsBtn.setText("Garments");
        garmentsBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-t-shirt-20.png"));
        garmentsBtn.setBackground(new Color(0,0,0));
        garmentsBtn.setForeground(new Color(82,191,161));
        garmentsBtn.setBorderPainted(false);
        garmentsBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                garmentsBtn.setBackground(new Color(82,191,161));
                garmentsBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                garmentsBtn.setBackground(new Color(0,0,0));
                garmentsBtn.setForeground(new Color(82,191,161));
            }
        });
        garmentsBtn.addActionListener(this);


//Your Orders Button
        yourOrdersBtn = new JButton();
        yourOrdersBtn.setText("Your Orders");
        yourOrdersBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-grocery-store-20.png"));
        yourOrdersBtn.setBackground(new Color(0,0,0));
        yourOrdersBtn.setForeground(new Color(255,51,51));
        yourOrdersBtn.setBorderPainted(false);
        yourOrdersBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                yourOrdersBtn.setBackground(new Color(255,51,51));
                yourOrdersBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                yourOrdersBtn.setBackground(new Color(0,0,0));
                yourOrdersBtn.setForeground(new Color(255,51,51));
            }
        });
        yourOrdersBtn.addActionListener(this);

//Wish List Button
        wishListBtn = new JButton();
        wishListBtn.setText("Wish List");
        wishListBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-heart-20.png"));
        wishListBtn.setBackground(new Color(0,0,0));
        wishListBtn.setForeground(new Color(255,51,51));
        wishListBtn.setBorderPainted(false);
        wishListBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                wishListBtn.setBackground(new Color(255,51,51));
                wishListBtn.setForeground(new Color(0,0,0));
            }

            public void mouseExited(MouseEvent evt) {
                wishListBtn.setBackground(new Color(0,0,0));
                wishListBtn.setForeground(new Color(255,51,51));
            }
        });
        wishListBtn.addActionListener(this);
        limit();

//myAccountBtn

        if(uid!=0) {
            //User Details
            try {
                PreparedStatement stmt = con.c.prepareStatement("select * from user where u_id=?");
                stmt.setInt(1, uid);

                ResultSet rst = stmt.executeQuery();
                rst.next();

                JLabel head = new JLabel("Hello "+rst.getString("u_fname")+" "+rst.getString("u_lname"));
                head.setFont(new Font("Arial",Font.BOLD,14));
                head.setForeground(Color.cyan);
                head.setHorizontalAlignment(JLabel.CENTER);
                head.setHorizontalTextPosition( SwingConstants.CENTER);
                menuPanel.add(head);

                uBlob = rst.getBlob("u_img");

        //User Image
                byte[] imByte = uBlob.getBytes(1, (int) uBlob.length());
                InputStream ist = new ByteArrayInputStream(imByte);
                BufferedImage uImg = ImageIO.read(ist);
                Image uImage = uImg;
                ImageIcon uIcon = new ImageIcon(uImage);
                myAccountBtn = new JButton();
                myAccountBtn.setIcon(uIcon);
                headerPanel.add(myAccountBtn, BorderLayout.WEST);
                myAccountBtn.setBackground(new Color(0,0,0));
                myAccountBtn.setForeground(new Color(237, 235, 233));
                myAccountBtn.setBorderPainted(false);
                //myAccountBtn.setPreferredSize(new Dimension(64, 64));
                myAccountBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                myAccountBtn.addActionListener(this);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }



//Content Panel
        contentPanel.setBackground(new Color(32,31,30));
        contentPanel.setForeground(new Color(237,235,233));
        Border dTitle1 = BorderFactory.createTitledBorder("All Items");
        contentPanel.setBorder(dTitle1);
        ((TitledBorder) contentPanel.getBorder()).setTitleColor(new Color(237,235,233));

//Scroll Pane
        scPane.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scPane.getVerticalScrollBar().setUnitIncrement(2);
        Dimension d=new Dimension(600,400);
        scPane.setPreferredSize(d);
        scPane.setBackground(new Color(32,31,30));
        scPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        userHomePanel.add(scPane,"Center");

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


//footerPanel
        footerPanel.setBackground(new Color(0,0,0));
        footerPanel.setLayout(new BorderLayout());
        userHomePanel.add(footerPanel,"South");

//Calling Menu Function
        menu();


//logOutBtn
        logOutBtn = new JButton("Log Out");
        footerPanel.add(logOutBtn,"East");
        logOutBtn.setBackground(new Color(255,51,51));
        logOutBtn.setForeground(new Color(237,235,233));
        logOutBtn.setBorderPainted(true);
        logOutBtn.addActionListener(this);
        logOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


//Default Content Insertion
           content("select * from stock");




    }



//Content Insertion
    void content(String sql){

        Connect con = new Connect();
        con.conn();

        try {

            //Contents...
            PreparedStatement st = con.c.prepareStatement(sql);

            ResultSet rs3 = st.executeQuery();

            //Panel1 Grid Layout Settings...
            rs3.last();
            int rowCount = rs3.getRow();
            rs3.beforeFirst();

            while (rs3.next()) {

                id= rs3.getInt("item_id");
                name = rs3.getString("item_name");
                price = rs3.getInt("item_price");
                dept = rs3.getString("item_department");
                cat = rs3.getString("item_category");
                stock = rs3.getInt("item_stock");
                off = rs3.getInt("item_offer");
                aBlob = rs3.getBlob("img");

            //Button Settings
                //Icon
                byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                InputStream is=new ByteArrayInputStream(imageByte);
                BufferedImage img= ImageIO.read(is);
                Image image = img;
                ImageIcon icon =new ImageIcon(image);

                //Icon and Multilevel text
                JLabel bg = new JLabel(icon);
                JButton btn;
                btn = new JButton();
                btn.setLayout(new BorderLayout());
                btn.add(bg,BorderLayout.NORTH);
                btn.setPreferredSize(new Dimension(150,170));

                JLabel text1 = new JLabel();
                text1.setText(name);
                text1.setHorizontalAlignment(JLabel.CENTER);
                btn.add(text1,BorderLayout.CENTER);
                text1.setForeground(new Color(67, 149, 222));

                JLabel text2 = new JLabel();
                text2.setText( " Price: ₹"+price+" Offer: "+off+"%");
                text2.setHorizontalAlignment(JLabel.CENTER);
                btn.add(text2,BorderLayout.SOUTH);
                text2.setForeground(Color.YELLOW);


                //Further button settings
                btn.setBackground(new Color(32,31,30));
                btn.setForeground(new Color(237,235,233));
                btn.setBorderPainted(false);
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.setVerticalTextPosition(SwingConstants.BOTTOM);
                btn.setHorizontalTextPosition(SwingConstants.CENTER);
                btn.setActionCommand(String.valueOf(id));
                btn.addActionListener(this);

                btn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        btn.setBackground(new Color(0,0,0));
                    }

                    public void mouseExited(MouseEvent evt) {
                        btn.setBackground(new Color(32,31,30));
                    }
                });

                JButton wishListBtn = new JButton();

                try{
                    PreparedStatement st4 = con.c.prepareStatement("select * from wish_list where u_id = ? and item_id = ?");
                    st4.setInt(1,uid);
                    st4.setInt(2,id);

                    ResultSet rs4 = st4.executeQuery();

                    if (rs4.next()){
                        wishListBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-wish-list.png"));
                    }
                    else {
                        wishListBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-heart-20 blank.png"));
                    }

                }
                catch (Exception ex4){
                    ex4.printStackTrace();
                }

                wishListBtn.setBackground(new Color(32,31,30));
                wishListBtn.setForeground(new Color(255,255,255));
                wishListBtn.setBorderPainted(false);
                wishListBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                wishListBtn.setActionCommand(String.valueOf(id));
                wishListBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int iid = Integer.parseInt(e.getActionCommand());

                        //JOptionPane.showMessageDialog(null,iid+" Added To Wish List");
                        try {

                            PreparedStatement st6 = con.c.prepareStatement("select * from wish_list where u_id=? and item_id=?");
                            st6.setInt(1, uid);
                            st6.setInt(2, iid);

                            ResultSet res;
                            res = st6.executeQuery();

                            if (res.next()){
                                wishListBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-heart-20 blank.png"));
                                PreparedStatement st5 = con.c.prepareStatement("delete from wish_list where u_id=? and item_id=?");
                                st5.setInt(1, uid);
                                st5.setInt(2, iid);
                                st5.executeUpdate();

                                contentPanel.revalidate();

                            }
                            else {
                                limit();
                                if (limit<=10) {
                                    wishListBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-wish-list.png"));
                                    PreparedStatement st5 = con.c.prepareStatement("insert into wish_list(u_id,item_id) values(?,?)");
                                    st5.setInt(1, uid);
                                    st5.setInt(2, iid);
                                    st5.executeUpdate();

                                    contentPanel.revalidate();
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"Wish List Limit Reached");
                                }
                            }

                        }
                        catch (Exception ex5){
                            ex5.printStackTrace();
                        }

                    }
                });


                //Adding.....
                Container container = new Container();
                container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

                container.add(wishListBtn);
                container.add(btn);

                rowCount = rowCount/2;
                contentPanel.setLayout(new GridLayout(rowCount,2,20,20));
                contentPanel.add(container);
                scPane.getViewport().add(contentPanel);


            }

        } catch (Exception ex3) {
            System.out.println(ex3);
        }

    }


//Wish List Limit
    void limit(){

        Connect con = new Connect();
        con.conn();

        try {
            PreparedStatement st6 = con.c.prepareStatement("select * from wish_list where u_id=?");
            st6.setInt(1, uid);
            ResultSet res;
            res = st6.executeQuery();

            if (res.next()) {
                res.last();
                limit = res.getRow();
                limit++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void menu(){

    //Add Buttons On Menu Panel.....
        menuPanel.revalidate();
        menuPanel.repaint();
        menuPanel.add(categoryBtn);
        menuPanel.add(allItemsBtn);
        menuPanel.add(topOffersBtn);
        menuPanel.add(groceryBtn);
        menuPanel.add(garmentsBtn);
        menuPanel.add(yourOrdersBtn);
        menuPanel.add(wishListBtn);

    }

    void user(int userId) {
            uid=userId;
            userHomeFrame = new JFrame("User Home Page");
            userHomeFrame.setContentPane(new UserHomePage().userHomePanel);
            userHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            userHomeFrame.pack();
            userHomeFrame.setVisible(true);
        }


    @Override
    public void actionPerformed(ActionEvent e) {
//Log Out
        if(e.getSource()==logOutBtn){
            userHomeFrame.setVisible(false);
            new LogIn().logIn();

        }
//My Account
       else if (e.getSource()==myAccountBtn){
            //JOptionPane.showMessageDialog(null,uid);
            userHomeFrame.setVisible(false);
            new UserInfo().userInfo(uid);
        }


//Category Button
        else if (e.getSource()==categoryBtn){
            if (flag){
                categoryBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-collapse-arrow-20.png"));
                menuPanel.remove(allItemsBtn);
                menuPanel.remove(topOffersBtn);
                menuPanel.remove(groceryBtn);
                menuPanel.remove(garmentsBtn);
                menuPanel.validate();
                flag = false;
            }
            else{
                categoryBtn.setIcon(new ImageIcon("C:\\Users\\Niladri Das\\Desktop\\Java\\MyStore\\src\\my\\store\\Images\\icons8-expand-arrow-20.png"));
                menu();
                flag = true;
            }
        }

//All Items Button
        else if (e.getSource()==allItemsBtn){
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();
            Border dTitle1 = BorderFactory.createTitledBorder("All Items");
            contentPanel.setBorder(dTitle1);
            ((TitledBorder) contentPanel.getBorder()).setTitleColor(new Color(237,235,233));
            content("select * from stock");

        }
//Top Offers Button
        else if (e.getSource()==topOffersBtn){
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();
            Border dTitle1 = BorderFactory.createTitledBorder("Top Offers");
            contentPanel.setBorder(dTitle1);
            ((TitledBorder) contentPanel.getBorder()).setTitleColor(new Color(237,235,233));
            content("select * from stock where item_offer>= 10");

        }
//Grocery Button
        else if (e.getSource()==groceryBtn){
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();
            Border dTitle1 = BorderFactory.createTitledBorder("Grocery");
            contentPanel.setBorder(dTitle1);
            ((TitledBorder) contentPanel.getBorder()).setTitleColor(new Color(237,235,233));
            content("select * from stock where item_department = 'Grocery'");

        }

//Garments Button
        else if (e.getSource()==garmentsBtn){
            contentPanel.removeAll();
            contentPanel.revalidate();
            contentPanel.repaint();
            Border dTitle1 = BorderFactory.createTitledBorder("Garments");
            contentPanel.setBorder(dTitle1);
            ((TitledBorder) contentPanel.getBorder()).setTitleColor(new Color(237,235,233));
            content("select * from stock where item_department = 'Garments'");

        }

//Your Orders
        else if (e.getSource()==yourOrdersBtn){
            new BookingHistory().bookingHistory(uid);
        }

//Wish List
        else if(e.getSource()==wishListBtn){
            userHomeFrame.setVisible(false);
            new WishList().wishList(uid);
        }


//Item
        else {

                int iid = Integer.parseInt(e.getActionCommand());
                //JOptionPane.showMessageDialog(null, iid);

                Connect con2 = new Connect();
                con2.conn();
                try {
                    PreparedStatement st2 = con2.c.prepareStatement("select * from stock where item_id=? and item_stock>0");

                    st2.setInt(1, iid);
                    ResultSet rs2 = st2.executeQuery();

                    if (rs2.next()) {

                        userHomeFrame.setVisible(false);
                        new Booking().boooking(uid, iid);

                    } else
                        JOptionPane.showMessageDialog(null, "Out Of Stock");


                } catch (Exception ex3) {
                    ex3.printStackTrace();
                }


        }

    }

}