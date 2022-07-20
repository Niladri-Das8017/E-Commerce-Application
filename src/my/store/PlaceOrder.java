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

public class PlaceOrder implements ActionListener{
    public static JFrame placeOrderFrame;
    private int row;
    private JScrollPane scPane=new JScrollPane();
    private JPanel orderPanel=new JPanel();
    private JPanel footerPanel=new JPanel();
    private JPanel panel1=new JPanel();
    private JButton closeBtn=new JButton();
    private  JButton orderNowBtn;
    private boolean flag = true;


    public PlaceOrder() {
//Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(32, 31, 30));
        UI.put("Panel.background", new ColorUIResource(32, 31, 30));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));



//Connection
        Connect con3 = new Connect();
        con3.conn();


//bookingPanel

        String title="Place Order";
        Border borderTitle = BorderFactory.createTitledBorder(title);
        orderPanel.setBorder(borderTitle);
        ((TitledBorder) orderPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);
        ((TitledBorder) orderPanel.getBorder()).setTitleFont(new Font("Droid Sans Mono", Font.BOLD+Font.ITALIC, 18));
        ((TitledBorder) orderPanel.getBorder()).setTitleColor(new Color(237,235,233));
        orderPanel.setBackground(new Color(0,0,0));
        orderPanel.setLayout(new BorderLayout(20,10));


//Panel1
        panel1.setBackground(new Color(0,0,0));
        panel1.setForeground(new Color(237,235,233));
        Border pTitle1 = BorderFactory.createTitledBorder("StockOut Alert!");
        panel1.setBorder(pTitle1);
        ((TitledBorder) panel1.getBorder()).setTitleColor(Color.RED);




//Panel2
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
        orderPanel.add(scPane,"Center");
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


        try {

            JLabel item = new JLabel("Item", JLabel.CENTER);
            JLabel iId = new JLabel("Item Id", JLabel.CENTER);
            JLabel iName = new JLabel("Name", JLabel.CENTER);
            JLabel iDept = new JLabel("Department", JLabel.CENTER);
            JLabel iCat = new JLabel("Category", JLabel.CENTER);
            JLabel iStock = new JLabel("Stock", JLabel.CENTER);
            JLabel iPrice = new JLabel("Price", JLabel.CENTER);

            item.setFont(new Font("Arial", Font.BOLD, 16));
            iId.setFont(new Font("Arial", Font.BOLD, 16));
            iName.setFont(new Font("Arial", Font.BOLD, 16));
            iDept.setFont(new Font("Arial", Font.BOLD, 16));
            iCat.setFont(new Font("Arial", Font.BOLD, 16));
            iStock.setFont(new Font("Arial", Font.BOLD, 16));
            iPrice.setFont(new Font("Arial", Font.BOLD, 16));

            item.setForeground(Color.cyan);
            iId.setForeground(Color.cyan);
            iName.setForeground(Color.cyan);
            iDept.setForeground(Color.cyan);
            iCat.setForeground(Color.cyan);
            iStock.setForeground(Color.cyan);
            iPrice.setForeground(Color.cyan);

            panel1.add(item);
            panel1.add(iId);
            panel1.add(iName);
            panel1.add(iDept);
            panel1.add(iCat);
            panel1.add(iStock);
            panel1.add(iPrice);


            PreparedStatement st6 = con3.c.prepareStatement("select * from stock where item_stock<=10");
            ResultSet res;
            res = st6.executeQuery();

//Panel1 Grid Layout Settings...
            res.last();
            row = res.getRow();
            row++;
            panel1.setLayout(new GridLayout(row, 7,5,10));

            do {

                int id = res.getInt(1);
                String name = res.getString(2);
                int price = res.getInt(3);
                String dept = res.getString(4);
                String cat = res.getString(5);
                int stock = res.getInt(6);
                Blob aBlob = res.getBlob(11);

                //Icon
                byte[] imageByte = aBlob.getBytes(1, (int) aBlob.length());
                InputStream is = new ByteArrayInputStream(imageByte);
                BufferedImage img = ImageIO.read(is);
                Image image = img;
                ImageIcon icon = new ImageIcon(image);


                //Labels
                //JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
                JLabel idLabel = new JLabel(String.valueOf(id), JLabel.CENTER);
                JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                JLabel deptLabel = new JLabel(dept, JLabel.CENTER);
                JLabel catLabel = new JLabel(cat, JLabel.CENTER);
                JLabel priceLabel = new JLabel(String.valueOf(price), JLabel.CENTER);
                JLabel stockLabel = new JLabel(String.valueOf(stock), JLabel.CENTER);

                //Label Edit....
                idLabel.setForeground(Color.ORANGE);
                nameLabel.setForeground(Color.ORANGE);
                deptLabel.setForeground(Color.ORANGE);
                catLabel.setForeground(Color.ORANGE);
                stockLabel.setForeground(Color.RED);
                priceLabel.setForeground(Color.ORANGE);

                //orderNowBtn.....
                orderNowBtn = new JButton("Order Now");
                orderNowBtn.setIcon(icon);
                orderNowBtn.setVerticalTextPosition(JButton.BOTTOM);
                orderNowBtn.setHorizontalTextPosition(JButton.CENTER);
                orderNowBtn.setBackground(new Color(0,0,0));
                orderNowBtn.setForeground(new Color(255,0,0));
                orderNowBtn.setBorderPainted(false);
                orderNowBtn.addActionListener(this);
                orderNowBtn.setHorizontalAlignment(JButton.CENTER);
                orderNowBtn.setVerticalAlignment(JButton.CENTER);
                orderNowBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                orderNowBtn.setContentAreaFilled(false);
                orderNowBtn.addActionListener(this);
                flag= true;


                //Add...
                panel1.add(orderNowBtn);
                panel1.add(idLabel);
                panel1.add(nameLabel);
                panel1.add(deptLabel);
                panel1.add(catLabel);
                panel1.add(stockLabel);
                panel1.add(priceLabel);

                //orderNow Button Settings



                scPane.getViewport().add(panel1);

            } while (res.previous());

        } catch (Exception ex) {
            ex.printStackTrace();
        }



//Close Button
        closeBtn = new JButton("Cancel");
        orderPanel.add(footerPanel,BorderLayout.SOUTH);
        footerPanel.add(closeBtn,BorderLayout.EAST);
        closeBtn.setBackground(new Color(220,53,69));
        closeBtn.setForeground(new Color(237,235,233));
        closeBtn.setBorderPainted(true);
        closeBtn.addActionListener(this);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }


    void placeOrder(){
        placeOrderFrame = new JFrame("Place Order");
        placeOrderFrame.setContentPane(new PlaceOrder().orderPanel);
        placeOrderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        placeOrderFrame.pack();
        placeOrderFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==closeBtn){

            placeOrderFrame.setVisible(false);
        }
        else if (flag) {
            JOptionPane.showMessageDialog(null, "Order Placed");
            flag = false;
            placeOrderFrame.setVisible(false);
            new PlaceOrder().placeOrder();
        }


    }
}
