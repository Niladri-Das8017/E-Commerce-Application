package my.store;


import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading {
    public static JFrame loadingFrame;
    private boolean flag;
    private int uid;
    private String fName;
    private String lName;
    private JPanel loadingPanel;
    private JProgressBar progress = new JProgressBar();
    private  Timer timer;


    public Loading() {
    //Custom JOptionPane
        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(0,0,0));
        UI.put("Panel.background", new ColorUIResource(0,0,0));
        UI.put("OptionPane.messageForeground", Color.ORANGE);
        UI.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));


        // use JProgressBar#setUI(...) method
        progress.setUI(new ProgressCircleUI());
        progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));
        progress.setForeground(Color.ORANGE);
        progress.setBackground(new Color(0,0,0));

        timer = new Timer(50,new TimerListener());
        timer.start();

        loadingPanel = new JPanel();
        loadingPanel.setBackground(new Color(0,0,0));
        loadingPanel.add(progress);

    }

//Timer Action Listener
    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {

                int i = Math.min(101, progress.getValue() + 1);
                progress.setValue(i);

                if(uid!=0){
            //terminate
                if(i==101) {
                    //JOptionPane.showMessageDialog(null,"Done");
                    timer.stop();
                    if (flag) {

                        JOptionPane.showMessageDialog(null, "Paid", "Payment Status", JOptionPane.INFORMATION_MESSAGE);
                        loadingFrame.setVisible(false);
                        new UserHomePage().user(uid);

                    }
                    else  {


                        JOptionPane.showMessageDialog(null, "welcome "+fName+" "+lName+". Your user id is: "+uid, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);

                        loadingFrame.setVisible(false);
                        new LogIn().logIn();
                    }
                }

                    
                }

        }
    }





    void loading(boolean f,String fname,String lname,int id) {

        flag=f;
        uid=id;
        fName=fname;
        lName=lname;
        loadingFrame = new JFrame("Loading");
        loadingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadingFrame.getContentPane().add(new Loading().loadingPanel);
        loadingFrame.setSize(320, 240);
        loadingFrame.setLocationRelativeTo(null);
        loadingFrame.setVisible(true);

    }
}