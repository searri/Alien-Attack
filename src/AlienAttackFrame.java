/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Rick Sear
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AlienAttackFrame extends JFrame {
    static final long serialVersionUID = 1L;
    private static int gameFrameSize = 900;
    private AlienGraphicsPanel graphicsPanel;
    private JButton leftButton;
    private JButton rightButton;
    private JPanel controlPanel;
    
    public AlienAttackFrame() {
        //Buttons - initialize and add
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        ControlListener controlsListener = new ControlListener();
        controlPanel = new JPanel();
        leftButton.addActionListener(controlsListener);
        rightButton.addActionListener(controlsListener);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);

        //Initialize graphics panel
        graphicsPanel = new AlienGraphicsPanel(gameFrameSize);

        //Add panels to main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(graphicsPanel, BorderLayout.CENTER);   
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        //Housekeeping
        setTitle("Alien Attack [Alpha]");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public class ControlListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if(obj == leftButton) {
                System.out.println("LEFT");
            } else if(obj == rightButton) {
                System.out.println("RIGHT");
            } else {
                System.out.println("ERROR");
            }
        }

    }

}
