package my.store;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItemStock implements ActionListener{
    public static JFrame stockFrame;
    private JScrollPane scPane=new JScrollPane();
    private JTable table=new JTable();
    private JPanel stockPanel=new JPanel();
    private JPanel footerPanel=new JPanel();
    private JPanel panel1=new JPanel();
    private JButton closeBtn=new JButton();


    public ItemStock() {

//Connection
        Connect con3 = new Connect();
        con3.conn();


//stockPanel

        String title="Stock";
        Border borderTitle = BorderFactory.createTitledBorder(title);
        stockPanel.setBorder(borderTitle);
        ((TitledBorder) stockPanel.getBorder()).setTitleJustification(TitledBorder.CENTER);
        ((TitledBorder) stockPanel.getBorder()).setTitleFont(new Font("Droid Sans Mono", Font.BOLD+Font.ITALIC, 18));
        ((TitledBorder) stockPanel.getBorder()).setTitleColor(new Color(237,235,233));
        stockPanel.setBackground(new Color(0,0,0));
        stockPanel.setLayout(new BorderLayout(20,10));


//Panel1
        panel1.setBackground(new Color(0,0,0));
        panel1.setForeground(new Color(237,235,233));
        Border pTitle1 = BorderFactory.createTitledBorder("Stock Details");
        panel1.setBorder(pTitle1);
        ((TitledBorder) panel1.getBorder()).setTitleColor(new Color(237,235,233));

        panel1.setLayout(new BorderLayout());


//Panel2
        footerPanel.setBackground(new Color(0,0,0));
        footerPanel.setForeground(new Color(237,235,233));
        footerPanel.setLayout(new BorderLayout());

//table
        table.setBackground(new Color(0,0,0));
        table.setForeground(new Color(174,192,81));


//Scroll Pane
        scPane.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scPane.getVerticalScrollBar().setUnitIncrement(2);
        Dimension d=new Dimension(800,600);
        scPane.setPreferredSize(d);
        scPane.setBackground(new Color(0,0,0));
        scPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        stockPanel.add(scPane,"Center");


        try {
            PreparedStatement st6 = con3.c.prepareStatement("select * from stock");

            ResultSet res = st6.executeQuery();


            table.setModel(DbUtils.resultSetToTableModel(res));
 //tHead
            JTableHeader tHead=table.getTableHeader();
            tHead.setBackground(new Color(220,53,69));
            tHead.setForeground(new Color(237,235,233));
            tHead.setFont(new Font("Arial",Font.BOLD,12));

            panel1.add(tHead,BorderLayout.NORTH);
            panel1.add(table,BorderLayout.CENTER);
            scPane.getViewport().add(panel1);
            res.close();
            st6.close();

        }
        catch (Exception ex){
            ex.printStackTrace();
        }


//Close Button
        closeBtn = new JButton("Cancel");
        stockPanel.add(footerPanel,BorderLayout.SOUTH);
        footerPanel.add(closeBtn,BorderLayout.EAST);
        closeBtn.setBackground(new Color(220,53,69));
        closeBtn.setForeground(new Color(237,235,233));
        closeBtn.setBorderPainted(false);
        closeBtn.addActionListener(this);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }


    void itemStock() {
        stockFrame = new JFrame("Stock");
        stockFrame.setContentPane(new ItemStock().stockPanel);
        stockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockFrame.pack();
        stockFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stockFrame.setVisible(false);
    }
}
