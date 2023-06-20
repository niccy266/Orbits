import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BallPanel extends JPanel{
  //making sections within the main window for controls and for drawing the balls
  private JPanel controlPanel = new JPanel();
  private DrawingPanel drawPanel = new DrawingPanel();
  private final int WINWIDTH = 1920/2, WINHEIGHT = 1040/2, CONTROL_HEIGHT = 40;
  
  //adding buttons to make more balls and to start and stop the animation
  private JButton addBall = new JButton ("Add Ball");
  private JButton start = new JButton ("Start Animation");
  private JButton stop = new JButton ("Stop Animation");
  
  //timer that triggers animation
  private Timer t;
  private final int DELAY = 20;
  
  //making checkboxed to turn on and off extra features
  private JCheckBox colourChange = new JCheckBox ("Animate Colours");
  private JCheckBox gravityCheck = new JCheckBox ("Gravity");
  private JCheckBox strechBalls = new JCheckBox ("Make Balls Stretch (WIP)");
  private boolean changeCol = false, stretch = false, gravity = false;
  
  //displaying the number of balls on the screen (max 20)
  private JLabel countLabel = new JLabel ("Balls: ");
  private JTextField countDisplay = new JTextField (2);
  
  //storing the balls on the screen and how many there are
  private Ball[] balls = new Ball[20];
  private int count;
  
  public BallPanel() { 
    //sets up the main window
    setPreferredSize(new Dimension(WINWIDTH, WINHEIGHT));
    //setBackground(Color.white);
    
    Ball.setPaneSize(WINWIDTH, WINHEIGHT - CONTROL_HEIGHT);
    
    //makes detector for all buttons, checkboxes and timer
    ButtonListener aListener = new ButtonListener();
    CheckBoxListener iListener = new CheckBoxListener();
    
    //adds button press detection to buttons
    addBall.addActionListener(aListener);
    start.addActionListener(aListener);
    stop.addActionListener(aListener);
    
    //sets up the timer
    t = new Timer(DELAY, aListener);
    
    //adds checkbox select detection to buttons
    colourChange.addItemListener(iListener);
    gravityCheck.addItemListener(iListener);
    strechBalls.addItemListener(iListener);
    
    //adds controls to control panel
    controlPanel.setPreferredSize(new Dimension(WINWIDTH, CONTROL_HEIGHT));
    controlPanel.add(addBall);
    controlPanel.add(countLabel);
    controlPanel.add(countDisplay);
    controlPanel.add(start);
    controlPanel.add(stop);
    controlPanel.add(colourChange);
    controlPanel.add(gravityCheck);
    controlPanel.add(strechBalls);
    
    //adds panels to window
    setLayout(new BorderLayout());
    add(drawPanel, BorderLayout.NORTH);
    add(controlPanel, BorderLayout.SOUTH);
  }
  
  public void paintComponent (Graphics g) {
    super.paintComponent(g);
  }
  
  public class ButtonListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) { 
      Object source = e.getSource();
      
      if (source == addBall){
        if (count < balls.length){
          //drawing a new ball
          balls[count++] = new Ball();
          //updating the ball counter
          countDisplay.setText(count + "");
          repaint();
        }
      } 
      else if (source == start) {
        t.start();
        for (int i = 0; i < count; i++) {
          balls[i].resetSpeed();
        }
      }
      else if (source == stop) {
        t.stop();
        for (int i = 0; i < count; i++) {
          balls[i].stop();
        }
      }
      else if (source == t){
        for (int i = 0; i < count; i++) {
          balls[i].move(gravity);
        }
        repaint();
      }
    }
  }
  
  public class CheckBoxListener implements ItemListener {
    
    public void itemStateChanged(ItemEvent e) { 
      JCheckBox source = (JCheckBox) e.getSource();
      
      if (source == strechBalls) stretch = source.isSelected();
      else if (source == colourChange) changeCol = source.isSelected();
      else if (source == gravityCheck) gravity = source.isSelected();
    }
    
  }
  
  private class DrawingPanel extends JPanel{
    
    public DrawingPanel () {
      setPreferredSize(new Dimension(WINWIDTH, WINHEIGHT - CONTROL_HEIGHT));
      System.out.println(WINWIDTH + " " + (WINHEIGHT - CONTROL_HEIGHT));
      setBackground(new Color(0,0,60));
    }
    
    public void paintComponent (Graphics g) {
      super.paintComponent(g);
      for (int i = 0; i < count; i++) {
        balls[i].draw(g, changeCol, stretch);
      }
    }
  }
}
