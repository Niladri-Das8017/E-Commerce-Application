package my.store;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static JFrame testFrame;
    public static JPanel testPanel;

    public Test(){
        testPanel= new JPanel();
        testPanel.setLayout(new GridLayout(2,3));

        for(int i = 0;i<6;i++){
            Container container =new Container();
            container.setLayout(new BorderLayout());
            JButton btn = new JButton("Buy Now");
            JButton btn1 = new JButton("Add to Wish List");

            container.add(btn,BorderLayout.CENTER);
            container.add(btn1,BorderLayout.SOUTH);
            testPanel.add(container);
            testFrame.add(testPanel);

        }

    }

    public static void main(String[] args) {
        testFrame = new JFrame("Test");
        testFrame.setContentPane(new Test().testPanel);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.pack();
        testFrame.setVisible(true);
    }
}
